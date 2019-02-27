package com.wx.pn.api.config;

import com.alibaba.fastjson.JSON;
import com.wx.pn.api.enums.ChangeType;
import com.wx.pn.api.exception.WeixinException;
import com.wx.pn.api.handler.ApiConfigChangeHandle;
import com.wx.pn.api.handler.GetTokenResponse;
import com.wx.pn.api.response.GetJsApiTicketResponse;
import com.wx.pn.api.utils.NetWorkCenter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ApiConfig extends Observable implements Serializable {


	private static final Logger LOG = LoggerFactory.getLogger(ApiConfig.class);
	/**
	 * 这里定义token正在刷新的标识，想要达到的目标是当有一个请求来获取token，发现token已经过期（我这里的过期逻辑是比官方提供的早100秒），然后开始刷新token
	 * 在刷新的过程里，如果又继续来获取token，会先把旧的token返回，直到刷新结束，之后再来的请求，将获取到新的token
	 * 利用AtomicBoolean实现原理：
	 * 当请求来的时候，检查token是否已经过期（7100秒）以及标识是否已经是true（表示已经在刷新了，还没刷新完），过期则将此标识设为true，并开始刷新token
	 * 在刷新结束前再次进来的请求，由于标识一直是true，而会直接拿到旧的token，由于我们的过期逻辑比官方的早100秒，所以旧的还可以继续用
	 * 无论刷新token正在结束还是出现异常，都在最后将标识改回false，表示刷新工作已经结束
	 */
	private final AtomicBoolean tokenRefreshing = new AtomicBoolean(false);
	private final AtomicBoolean jsRefreshing = new AtomicBoolean(false);

	private final String appid;
	private final String secret;
	private final String token;
	private String accessToken;
	private String jsApiTicket;
	private boolean enableJsApi;
	private long jsTokenStartTime;
	private long weixinTokenStartTime;



	/**
	 * 构造方法一，实现同时获取access_token。不启用jsApi
	 *
	 * @param appid  公众号appid
	 * @param secret 公众号secret
	 */
	public ApiConfig(String appid, String secret, String token) {
		this(appid, secret, token,false);
	}

	/**
	 * 构造方法二，实现同时获取access_token，启用jsApi
	 *
	 * @param appid       公众号appid
	 * @param secret      公众号secret
	 * @param enableJsApi 是否启动js api
	 */
	public ApiConfig(String appid, String secret, String token, boolean enableJsApi) {
		this.appid = appid;
		this.secret = secret;
        this.token = token;
		this.enableJsApi = enableJsApi;
		long now = System.currentTimeMillis();
		initToken(now);
		if (enableJsApi)
			initJSToken(now);
	}

	public String getAppid() {
		return appid;
	}

	public String getSecret() {
		return secret;
	}

    /**
     * 注意此token与accessToken不一样
     * @return
     */
    public String getToken() {
        return this.token;
    }

	public String getAccessToken() {
		long now = System.currentTimeMillis();
		long time = now - this.weixinTokenStartTime;
		try {
			/*
			 * 判断优先顺序：
			 * 1.官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
			 * 2.刷新标识判断，如果已经在刷新了，则也直接跳过，避免多次重复刷新，如果没有在刷新，则开始刷新
			 */
			if (time > 7100000 && this.tokenRefreshing.compareAndSet(false, true)) {
				LOG.debug("准备刷新token.............");
				initToken(now);
			}
		} catch (Exception e) {
			LOG.warn("刷新Token出错.", e);
			//刷新工作出现有异常，将标识设置回false
			this.tokenRefreshing.set(false);
		}
		return accessToken;
	}

	public String getJsApiTicket() {
		if (enableJsApi) {
			long now = System.currentTimeMillis();
			try {
				//官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
				if (now - this.jsTokenStartTime > 7100000 && this.jsRefreshing.compareAndSet(false, true)) {
					getAccessToken();
					initJSToken(now);
				}
			} catch (Exception e) {
				LOG.warn("刷新jsTicket出错.", e);
				//刷新工作出现有异常，将标识设置回false
				this.jsRefreshing.set(false);
			}
		} else {
			jsApiTicket = null;
		}
		return jsApiTicket;
	}

	public boolean isEnableJsApi() {
		return enableJsApi;
	}

	public void setEnableJsApi(boolean enableJsApi) {
		this.enableJsApi = enableJsApi;
		if (!enableJsApi)
			this.jsApiTicket = null;
	}

	/**
	 * 添加配置变化监听器
	 *
	 * @param handle 监听器
	 */
	public void addHandle(final ApiConfigChangeHandle handle) {
		super.addObserver(handle);
	}

	/**
	 * 移除配置变化监听器
	 *
	 * @param handle 监听器
	 */
	public void removeHandle(final ApiConfigChangeHandle handle) {
		super.deleteObserver(handle);
	}

	/**
	 * 移除所有配置变化监听器
	 */
	public void removeAllHandle() {
		super.deleteObservers();
	}

	/**
	 * 初始化微信配置，即第一次获取access_token
	 *
	 * @param refreshTime 刷新时间
	 */
	private void initToken(final long refreshTime) {
		LOG.debug("开始初始化access_token........");
		//记住原本的时间，用于出错回滚
		final long oldTime = this.weixinTokenStartTime;
		this.weixinTokenStartTime = refreshTime;
		String url =
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret="
						+ this.secret;
		NetWorkCenter.get(url, null, (resultCode, resultJson) -> {
            if (HttpStatus.SC_OK == resultCode) {
                GetTokenResponse response = JSON.parseObject(resultJson, GetTokenResponse.class);
                LOG.debug("获取access_token:{}", response.getAccessToken());
                if (null == response.getAccessToken()) {
                    //刷新时间回滚
                    weixinTokenStartTime = oldTime;
                    throw new WeixinException(
                            "微信公众号token获取出错，错误信息:" + response.getErrcode() + "," + response.getErrmsg());
                }
                accessToken = response.getAccessToken();
                //设置通知点
                setChanged();
                notifyObservers(new ConfigChangeNotice(appid, ChangeType.ACCESS_TOKEN, accessToken));
            }
        });
		//刷新工作做完，将标识设置回false
		this.tokenRefreshing.set(false);
	}

	/**
	 * 初始化微信JS-SDK，获取JS-SDK token
	 *
	 * @param refreshTime 刷新时间
	 */
	private void initJSToken(final long refreshTime) {
		LOG.debug("初始化 jsapi_ticket........");
		//记住原本的时间，用于出错回滚
		final long oldTime = this.jsTokenStartTime;
		this.jsTokenStartTime = refreshTime;
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
		NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
			@Override
			public void onResponse(int resultCode, String resultJson) {
				if (HttpStatus.SC_OK == resultCode) {
					GetJsApiTicketResponse response = JSON.parseObject(resultJson, GetJsApiTicketResponse.class);
					LOG.debug("获取jsapi_ticket:{}", response.getTicket());
					if (StringUtils.isBlank(response.getTicket())) {
						//刷新时间回滚
						jsTokenStartTime = oldTime;
						throw new WeixinException(
								"微信公众号jsToken获取出错，错误信息:" + response.getErrcode() + "," + response.getErrmsg());
					}
					jsApiTicket = response.getTicket();
					//设置通知点
					setChanged();
					notifyObservers(new ConfigChangeNotice(appid, ChangeType.JS_TOKEN, jsApiTicket));
				}
			}
		});
		//刷新工作做完，将标识设置回false
		this.jsRefreshing.set(false);
	}
}
