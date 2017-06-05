package com.quick.linkedin.service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.quick.linkedin.model.LinkedInModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by bd2 on 2017/3/13.
 */
@Component
public class CrawlLinkedService {

    private Logger logger = Logger.getLogger(CrawlLinkedService.class);



    public void crawlLinkedIn(String name, String password, String pointUrl) {
        String url = "https://www.linkedin.com/uas/login";
        String perUrl = "http://www.linkedin.com/in";

        List<String> publicIdentifiers = new ArrayList<String>();
        try {

            WebClient webClient = new WebClient();// 创建WebClient

            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setTimeout(5000);

            webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            // 获取页面
            HtmlPage page = webClient.getPage(url); // 打开linkedin
            // 获得name为"session_key"的html元素
            HtmlElement usernameEle = page.getElementByName("session_key");
            // 获得id为"session_password"的html元素
            HtmlElement passwordEle = (HtmlElement) page.getElementById("session_password-login");
            usernameEle.focus(); // 设置输入焦点
            usernameEle.type(name);
            passwordEle.focus(); // 设置输入焦点

            passwordEle.type(password);

            // 获得name为"submit"的元素

            HtmlElement submitEle = page.getElementByName("signin");

            // 点击“登陆”
            page = submitEle.click();
            WebResponse webResponse = page.getWebResponse();
            int statusCode = webResponse.getStatusCode();

            if (HttpStatus.SC_OK == statusCode) {
                System.out.println("登录成功");
                Document doc = Jsoup.parse(page.asXml());
                Elements elements = doc.select("code");
                // 获取登录人的个人中心链接地址

                for (Element item : elements) {
                    String codeContent = item.text();
                    if (codeContent.startsWith("{")) {
                        JSONObject json = JSONObject.fromObject(codeContent);
                        if (json.containsKey("included")) {
                            JSONArray jsonArray = JSONArray.fromObject(json.get("included"));
                            if (jsonArray.size() > 0) {
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                                    if (jsonObject.containsKey("publicIdentifier")) {
                                        String publicIdentifier = jsonObject.get("publicIdentifier").toString();
                                        if (!publicIdentifiers.contains(publicIdentifier)) {
                                            publicIdentifiers.add(publicIdentifier);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                if (!StringUtils.isEmpty(pointUrl)) {
                    publicIdentifiers.add(1, pointUrl);
                }
                System.out.println(publicIdentifiers);
//                int flag = 0;
                while (!publicIdentifiers.isEmpty()) {
//                    if (flag == 1) {
//                        break;
//                    }
//                    flag++;
                    String temp = publicIdentifiers.get(1);
                    String uniqueUrl = perUrl + "/" + temp;//"/qiuxuan-zhang-041ba48b";
                    WebClient wc = new WebClient();
                    URL link = new URL(uniqueUrl);
                    logger.debug(link.toString());
                    WebRequest request = new WebRequest(link);
                    ////设置请求报文头里的User-Agent字段
                    request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
                    //wc.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
                    //wc.addRequestHeader和request.setAdditionalHeader功能应该是一样的。选择一个即可。
                    //其他报文头字段可以根据需要添加
                    wc.getCookieManager().setCookiesEnabled(true);//开启cookie管理
                    wc.getOptions().setJavaScriptEnabled(false);//开启js解析。对于变态网页，这个是必须的
                    wc.getOptions().setCssEnabled(false);//开启css解析。对于变态网页，这个是必须的。
                    wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
                    wc.getOptions().setThrowExceptionOnScriptError(false);
                    wc.getOptions().setTimeout(10000);
                    //设置cookie。如果你有cookie，可以在这里设置
                    Set<Cookie> cookies = webClient.getCookieManager().getCookies();
                    wc.getCookieManager().setCookiesEnabled(true);
                    for (Cookie item : cookies) {
                        wc.getCookieManager().addCookie(item);
                    }
                    HtmlPage uniquePage = null;
                    try {
                        uniquePage = wc.getPage(request);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        continue;
                    }
                    WebResponse identResponse = uniquePage.getWebResponse();
                    if (uniquePage == null) {
                        System.out.println("采集 " + url + " 失败!!!");
                        return;
                    }
                    String content = identResponse.getContentAsString();//网页内容保存在content里
                    if (content == null) {
                        System.out.println("采集 " + url + " 失败!!!");
                        return;
                    }
                    logger.debug(content);
                    Document parse = Jsoup.parse(content);
                    Elements uniqueElements = parse.select("code");
                    JSONArray experience = new JSONArray();
                    JSONArray educations = new JSONArray();
                    JSONArray schools = new JSONArray();
                    JSONArray skills = new JSONArray();
                    JSONObject selfInfo = new JSONObject();

                    JSONObject commonDate = new JSONObject();
                    JSONObject rangeDate = new JSONObject();

                    for (Element uniqueItem : uniqueElements) {
                        String itemTxt = uniqueItem.text();
                        if (itemTxt.startsWith("{")) {
                            logger.debug(itemTxt);
                            JSONObject json = JSONObject.fromObject(itemTxt);
                            if (!json.containsKey("request")) {
                                logger.info(json);
                                JSONArray jsonArray = JSONArray.fromObject(json.get("included"));
                                if (jsonArray.size() > 0) {
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        try {
                                            JSONObject jobject = JSONObject.fromObject(jsonArray.get(i));
                                            if (jobject.containsKey("$type")) {
                                                String typeStr = jobject.getString("$type");
                                                if (typeStr.equals("com.linkedin.common.Date")) {
                                                    commonDate.accumulate(jobject.getString("$id"), jobject);
                                                } else if (typeStr.equals("com.linkedin.voyager.common.DateRange")) {
                                                    rangeDate.accumulate(jobject.getString("$id"), jobject);
                                                } else if (typeStr.equals("com.linkedin.voyager.identity.shared.MiniProfile")) {
                                                    // 他人信息摘要（包括自己）
                                                    String publicIdentifier = jobject.getString("publicIdentifier");
                                                    if (!publicIdentifiers.contains(publicIdentifier)) {
                                                        publicIdentifiers.add(publicIdentifier);
                                                    }
                                                } else if (typeStr.equals("com.linkedin.voyager.identity.profile.Skill")) {
                                                    // 个人技能
                                                    skills.add(jobject.getString("name"));
                                                } else if (typeStr.equals("com.linkedin.voyager.identity.profile.Profile")) {
                                                    // 个人信息
                                                    jobject.remove("supportedLocales");
                                                    jobject.remove("versionTag");
                                                    jobject.remove("pictureInfo");
                                                    jobject.remove("industryUrn");
                                                    jobject.remove("$type");
                                                    jobject.remove("defaultLocale");
                                                    jobject.remove("$deletedFields");
                                                    jobject.remove("entityUrn");
                                                    jobject.remove("location");
                                                    jobject.remove("miniProfile");
                                                    jobject.remove("backgroundImage");
                                                    jobject.remove("state");
                                                    selfInfo = jobject;
                                                } else if (typeStr.equals("com.linkedin.voyager.entities.shared.MiniSchool")) {
                                                    // 学校
                                                    jobject.remove("$deletedFields");
                                                    jobject.remove("objectUrn");
                                                    jobject.remove("entityUrn");
                                                    jobject.remove("trackingId");
                                                    jobject.remove("$type");
                                                    jobject.remove("logo");
                                                    schools.add(jobject);
                                                } else if (typeStr.equals("com.linkedin.voyager.identity.profile.Education")) {
                                                    // 教育
                                                    jobject.remove("$deletedFields");
                                                    jobject.remove("entityUrn");
                                                    jobject.remove("schoolUrn");
                                                    jobject.remove("$type");
                                                    jobject.remove("courses");
                                                    String dateStr = buildRangeDate(commonDate,rangeDate,jobject);
                                                    jobject.accumulate("rangeDate", dateStr);
                                                    jobject.remove("degreeUrn");
                                                    jobject.remove("timePeriod");
                                                    educations.add(jobject);
                                                } else if (typeStr.equals("com.linkedin.voyager.identity.profile.Position")) {
                                                    // 公司
                                                    jobject.remove("$deletedFields");
                                                    jobject.remove("entityUrn");
                                                    String dateStr = buildRangeDate(commonDate,rangeDate,jobject);
                                                    jobject.accumulate("rangeDate", dateStr);
                                                    jobject.remove("timePeriod");
                                                    jobject.remove("company");
                                                    jobject.remove("companyUrn");
                                                    jobject.remove("$type");
                                                    experience.add(jobject.toString());
                                                }
                                            }
                                        } catch (Exception e) {
                                            logger.error(e.getMessage());
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    try {
                        LinkedInModel model = new LinkedInModel();
                        model.setExperiences(experience.toString());
                        model.setEducations(educations.toString());
                        model.setEducations(schools.toString());
                        model.setSkills(skills.toString());
                        if (selfInfo.containsKey("firstName")) {
                            model.setFirstName(selfInfo.getString("firstName"));
                        }
                        if (selfInfo.containsKey("lastName")) {
                            model.setLastName(selfInfo.getString("lastName"));
                        }
                        if (selfInfo.containsKey("industryName")) {
                            model.setIndustryName(selfInfo.getString("industryName"));
                        }

                        if (selfInfo.containsKey("headline")) {
                            model.setHeadline(selfInfo.getString("headline"));
                        }
                        if (selfInfo.containsKey("locationName")) {
                            model.setAddress(selfInfo.getString("locationName"));
                        }
                        model.setUniqueUrl(temp);
                        System.out.println("抓取用户‘"+model + "’数据");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    publicIdentifiers.remove(temp);
                }
            } else {
                System.out.println("登录失败");
            }
        } catch (FailingHttpStatusCodeException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 重建日期范围
     *
     * @param commonDate
     * @param rangeDate
     * @param elementObject
     * @return
     */
    public static String buildRangeDate(JSONObject commonDate,JSONObject rangeDate, JSONObject elementObject) {
        if(!elementObject.containsKey("timePeriod")){
            return "";
        }
        String timeId = elementObject.getString("timePeriod");
        JSONObject jsonObject = rangeDate.getJSONObject(timeId);
        String start = "";
        String end = "";
        if (jsonObject.containsKey("startDate")) {
            start = jsonObject.getString("startDate");
        } else {
            return "";
        }
        JSONObject startDate = commonDate.getJSONObject(start);
        StringBuffer startStr = new StringBuffer(startDate.getString("year"));
        if (startDate.containsKey("month")) {
            startStr.append("-" + startDate.getString("month"));
        }
        if (startDate.containsKey("month") && startDate.containsKey("day")) {
            startStr.append("-" + startDate.getString("day"));
        }

        if (jsonObject.containsKey("endDate")) {
            end = jsonObject.getString("endDate");
        } else {
            return startStr.toString();
        }
        JSONObject endDate = commonDate.getJSONObject(end);
        StringBuffer endStr = new StringBuffer(endDate.getString("year"));

        if (endDate.containsKey("month")) {
            endStr.append("-" + endDate.getString("month"));
        }
        if (endDate.containsKey("month") && startDate.containsKey("day")) {
            endStr.append("-" + endDate.getString("day"));
        }
        return startStr.toString() + "至" + endStr.toString();
    }
}
