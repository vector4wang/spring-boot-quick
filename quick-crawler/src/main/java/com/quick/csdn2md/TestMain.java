package com.quick.csdn2md;

import java.io.IOException;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        String s = HTML2Md.convertHtml("<div id=\"article_content\" class=\"article_content tracking-ad\" data-mod=\"popu_307\" data-dsm=\"post\">\n" +
                "        <div class=\"markdown_views\"><h3 id=\"说在前头\"><a name=\"t0\" target=\"_blank\"></a>说在前头</h3>\n" +
                "\n" +
                "<p>　　有段时间没更新了，期间发生了很多事，这里就不一一赘述，有一个要说一下， 之前我放在github上的springbootquick代码结构我重新整理了一下，因为我觉得branch的方式去完善每个小功能不太直观，索性找个时间全都整理在了一起，方便大家观看 <br>\n" +
                "  <img src=\"http://upload-images.jianshu.io/upload_images/3167229-fbaa4651e13f1e68.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"目录结构\" title=\"\"> <br>\n" +
                "项目地址为<a href=\"https://github.com/vector4wang/spring-boot-quick\" target=\"_blank\">https://github.com/vector4wang/spring-boot-quick</a>　里面有些是我工作中用到的，有些是我在平时玩玩积累的，如果对你有帮助，点个星，fork一下，我会感激不尽，如果有问题，请及时沟通我，我会在第一时间改正，我的原则是“交流、互助、提升”~ <br>\n" +
                " 　　今天就我之前写的一个小虫子说一下代码里的随机应变，以下观点均数个人看法，如有雷同纯属巧合，如果你对我的看法有意见欢迎拍砖，如果觉得我的理解有点意思，那有劳，点个赞分享一下，我定感激不尽，好的，言归正传！</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h3 id=\"起因\"><a name=\"t1\" target=\"_blank\"></a>起因</h3>\n" +
                "\n" +
                "<blockquote>\n" +
                "  <p>世充日踧月迫，力尽计穷，悬首面缚，翘足可待。建德远来助虐，粮运阻绝，此是天丧之时。请固武牢，屯军氾水，随机应变，则易为克殄 —旧唐书·郭孝恪传(指随着情况的变化灵活机动地应付)</p>\n" +
                "</blockquote>\n" +
                "\n" +
                "<p>之前写的<a href=\"http://www.jianshu.com/p/d3533550b69c\" target=\"_blank\">领英爬虫</a>目标数据比较随意，即抓取一段时间之后，后面得到的数据都是老外的，这不是我想要的，于是上级给了一个办法， “通过百度搜索去抓”，即在百度搜索某一个姓氏或常用名，通过百度返回的列表再依次去领英里去抓，如图 <br>\n" +
                "<img src=\"http://upload-images.jianshu.io/upload_images/3167229-6a400227a520de53.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"列表.png\" title=\"\"> <br>\n" +
                "后来验证这是可行的，其实这里也是一个“随机应变”的情景，这条路走不通立马换条路，而且是可行的。 <br>\n" +
                "　　问题到这看似没有问题，但是用过百度的都知道，当你点击某一结果的时候，百度这边需要做一个重定向处理，即重新指定url,例如你点击百度链接<code>http://www.baidu.com/link?url=6pwgxIUI8VfssEgJKHAHjdHBD_55cW5E72arpGIV9lZoAxLsaqV3do9p9O01kuzb3s3FlJKImEFkFsvWkDWL8a</code>,经过重定向后就变成了<code>http://cn.linkedin.com/in/johnsonwangnz</code>，这一点htmlunit做的很好，自动获取重定向的页面，但是在防爬虫这一块，领英做的很好，这一点列一下我知道的限制</p>\n" +
                "\n" +
                "<ul>\n" +
                "<li>ip限制 一个ip对领英产生大量非人类行为的请求，他会对这个ip登录的账号做一个人机验证，比12306还好玩，大家可以试试，手动偷笑~</li>\n" +
                "<li>请求限制 当产生大量请求是，它会对请求直接拒绝~</li>\n" +
                "<li>账号限制 同一个ip或者ip所在的网段注册多个领英账号的时候，账号会出现各种繁琐的验证，没验证的直接禁掉！！！</li>\n" +
                "</ul>\n" +
                "\n" +
                "<p>目前我知道的就这几条。 <br>\n" +
                "好，因为是代码，请求的速度非常快，一秒通过百度指向领英跳转达到几十个，几分钟后代码就报错了，如下 <br>\n" +
                "<img src=\"http://upload-images.jianshu.io/upload_images/3167229-27c7b494a25438ea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"999.png\" title=\"\"> <br>\n" +
                "　　这啥啊，999感冒灵冲剂啊!!!查了下code码，原来是“访问被拒绝”，我得出的结果是领英那边检查出了大量的个人页面的请求，速度很快非人类所能达到，于是就禁止数据返回。 <br>\n" +
                "　　我心想这下完蛋了，这条路难道也走不通！！！急的我抓耳挠腮~，然后我起身转了一圈，喝了点水，突然灵光一闪，“我想要的就是个人中心的链接啊，我可以先拿到这些链接存起来作为种子url，然后再将以前的爬虫改造一下，通过这些url去抓啊！” <br>\n" +
                "于是，我将这些错误catch住，通过简单的正则来判断是否为拥有个人中心的链接地址，如下：</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<pre class=\"prettyprint\" name=\"code\"><code class=\"language-java hljs  has-numbering\"><span class=\"hljs-keyword\">final</span> Pattern r = Pattern.compile(<span class=\"hljs-string\">\"in/[\\\\u4e00-\\\\u9fa5-0-9-a-z-A-z%]*\"</span>);\n" +
                "\n" +
                "\n" +
                "\n" +
                "Matcher m = r.matcher(e.getMessage());\n" +
                "String seed = <span class=\"hljs-string\">\"\"</span>;\n" +
                "<span class=\"hljs-keyword\">if</span> (m.find()) {\n" +
                "    seed = m.group();\n" +
                "    seed = seed.substring(<span class=\"hljs-number\">3</span>, seed.length());\n" +
                "}\n" +
                "System.out.println(seed);</code><ul class=\"pre-numbering\" style=\"opacity: 0;\"><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li></ul><div class=\"save_code tracking-ad\" data-mod=\"popu_249\" style=\"display: none;\"><a href=\"javascript:;\" target=\"_blank\"><img src=\"http://static.blog.csdn.net/images/save_snippets.png\"></a></div><ul class=\"pre-numbering\"><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li></ul></pre>\n" +
                "\n" +
                "<p>结果为 <br>\n" +
                "<img src=\"http://upload-images.jianshu.io/upload_images/3167229-5791ebe1225b51ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"seed.png\" title=\"\"> <br>\n" +
                "没错，白色就是种子url，最后在直接访问<a href=\"http://www.linkedin.com/in/jingshi-wang-056b6245/\" target=\"_blank\">http://www.linkedin.com/in/jingshi-wang-056b6245/</a> 就可以获取这个人一些职业信息啦~~~ <br>\n" +
                "<img src=\"http://upload-images.jianshu.io/upload_images/3167229-ddce44080aca774a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"职业档案.png\" title=\"\"></p>\n" +
                "\n" +
                "<p>最终的是酱紫 <br>\n" +
                "<img src=\"http://upload-images.jianshu.io/upload_images/3167229-ed83801ae81395b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"结构.png\" title=\"\"> <br>\n" +
                "为什么领英爬虫还要回写种子DB，因为要将抓过的url打个标记抓过了，整体就这么简单。</p>\n" +
                "\n" +
                "<p>现在种子抓了100多万了，领英这块抓了8万多，现在暂停了，因为帐号这块的问题还没有解决~~~，啊哈哈哈哈</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h3 id=\"后记\"><a name=\"t2\" target=\"_blank\"></a>后记</h3>\n" +
                "\n" +
                "<p>代码我放在了github上，<a href=\"https://github.com/vector4wang/spring-boot-quick/blob/master/quick-crawler/src/main/java/com/quick/baidu/BaiduCrawlerUrl.java\" target=\"_blank\">点我</a>,其实这里并不是说代码的这一块的，而是如何对代码反馈出来的结果做出灵活的应对以致快速总结出解决方案，这不就是对代码的“随机应变”吗~~</p>\n" +
                "\n" +
                "<p>欢迎大家浏览我的个人博客<a href=\"http://vector4wang.tk\" target=\"_blank\">http://vector4wang.tk</a></p></div>\n" +
                "        <script type=\"text/javascript\">\n" +
                "            $(function () {\n" +
                "                $('pre.prettyprint code').each(function () {\n" +
                "                    var lines = $(this).text().split('\\n').length;\n" +
                "                    var $numbering = $('<ul></ul>').addClass('pre-numbering').hide();\n" +
                "                    $(this).addClass('has-numbering').parent().append($numbering);\n" +
                "                    for (i = 1; i <= lines; i++) {\n" +
                "                        $numbering.append($('<li></li>').text(i));\n" +
                "                    };\n" +
                "                    $numbering.fadeIn(1700);\n" +
                "                });\n" +
                "            });\n" +
                "        </script>\n" +
                "   \n" +
                "</div>", "UTF-8");
        System.out.println(s);
    }
}
