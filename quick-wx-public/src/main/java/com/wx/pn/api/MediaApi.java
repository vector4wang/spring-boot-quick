package com.wx.pn.api;

import com.alibaba.fastjson.JSON;
import com.wx.pn.api.config.ApiConfig;
import com.wx.pn.api.enums.MediaType;
import com.wx.pn.api.enums.ResultType;
import com.wx.pn.api.model.BaseResponse;
import com.wx.pn.api.response.DownloadMediaResponse;
import com.wx.pn.api.response.UploadMediaResponse;
import com.wx.pn.api.utils.NetWorkCenter;
import com.wx.pn.api.utils.StreamUtil;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author vector
 * @date: 2018/11/9 0009 11:29
 */
public class MediaApi extends BaseApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApiConfig apiConfig;

    public MediaApi(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    /**
     * 上传资源，会在微信服务器上保存3天，之后会被删除
     *
     * @param type 资源类型
     * @param file 需要上传的文件
     * @return 响应对象
     */
    public UploadMediaResponse uploadMedia(MediaType type, File file) {
        if (Objects.isNull(file)) {
            return null;
        }
        UploadMediaResponse response;
        String url =
                String.format("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s", apiConfig.getAccessToken(), type.toString());
        List<File> files = new ArrayList<File>() {{
            add(file);
        }};
        BaseResponse resp = NetWorkCenter.post(url, null, files);
        ResultType resultType = ResultType.get(resp.getErrcode());
        if (!Objects.equals(resultType, ResultType.SUCCESS)) {
            logger.error("file [{}] uploadMedia err: {}", file.getName(), resultType.getDescription());
            return null;
        }
        response = JSON.parseObject(resp.getErrmsg(), UploadMediaResponse.class);
        return response;
    }

    /**
     * 下载资源
     *
     * @param mediaId 微信提供的资源唯一标识
     * @return 响应对象
     */
    public DownloadMediaResponse downloadMedia(String mediaId) {
        String url = String.format("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s", apiConfig.getAccessToken(), mediaId);
        return download(url);
    }

    /**
     * 下载文件通用方法
     *
     * @param url
     * @return
     */
    public DownloadMediaResponse download(String url) {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(NetWorkCenter.CONNECT_TIMEOUT).setConnectTimeout(NetWorkCenter.CONNECT_TIMEOUT).setSocketTimeout(NetWorkCenter.CONNECT_TIMEOUT).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpGet get = new HttpGet(url);
        DownloadMediaResponse response = new DownloadMediaResponse();
        try {
            CloseableHttpResponse r = client.execute(get);
            if (HttpStatus.SC_OK == r.getStatusLine().getStatusCode()) {
                InputStream inputStream = r.getEntity().getContent();
                Header[] headers = r.getHeaders("Content-disposition");
                if (null != headers && 0 != headers.length) {
                    Header length = r.getHeaders("Content-Length")[0];
                    response.setContent(inputStream, Integer.valueOf(length.getValue()));
                    response.setFileName(headers[0].getElements()[0].getParameterByName("filename").getValue());
                } else {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    StreamUtil.copy(inputStream, out);
                    String json = out.toString();
                    response = JSON.parseObject(json, DownloadMediaResponse.class);
                }
            }
        } catch (IOException e) {
            logger.error("IO处理异常", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("异常", e);
            }
        }
        return response;
    }
}
