package com.wx.pn.api;

import com.wx.pn.api.config.ApiConfig;
import com.wx.pn.api.enums.ResultType;
import com.wx.pn.api.model.BaseResponse;
import com.wx.pn.api.model.Menu;
import com.wx.pn.api.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vector
 * @date: 2018/11/8 0008 17:09
 * <p>
 * 菜单的相关操作
 */
public class MenuApi extends BaseApi {
    private static final Logger logger = LoggerFactory.getLogger(MenuApi.class);

    private ApiConfig apiConfig;

    public MenuApi(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    /**
     * 创建菜单
     *
     * @param menu
     * @return
     */
    public ResultType createMenu(Menu menu) {
        BeanUtil.requireNonNull(menu, "menu is null");
        String url = BASE_API_URL;
        if (BeanUtil.isNull(menu.getMatchrule())) {
            //普通菜单
            logger.info("创建普通菜单.....");
            url += "cgi-bin/menu/create?access_token=" + apiConfig.getAccessToken();
        } else {
            //个性化菜单
            logger.info("创建个性化菜单.....");
            url += "cgi-bin/menu/addconditional?access_token=" + apiConfig.getAccessToken();
        }
        BaseResponse baseResponse = executePost(url, menu.toJsonString());
        return ResultType.get(baseResponse.getErrcode());
    }

    /**
     * 删除所有菜单，包括个性化菜单
     *
     * @return 调用结果
     */
    public ResultType deleteMenu() {
        logger.debug("删除菜单.....");
        String url = BASE_API_URL + "cgi-bin/menu/delete?access_token=" + apiConfig.getAccessToken();
        BaseResponse response = executeGet(url);
        return ResultType.get(response.getErrcode());
    }


}
