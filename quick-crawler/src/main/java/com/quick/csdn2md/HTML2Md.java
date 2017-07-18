package com.quick.csdn2md;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class HTML2Md {
    private static int indentation = -1;
    private static boolean orderedList = false;

    public static void main(String[] args) throws IOException {
        String html = "<div id=\"article_content\" class=\"article_content tracking-ad\" data-mod=\"popu_307\" data-dsm=\"post\">\n" +
                "        <div class=\"markdown_views\"><h2 id=\"前言\"><a name=\"t0\" target=\"_blank\"></a>前言</h2>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"http://blog.csdn.net/qqhjqs/article/details/46963495\" target=\"_blank\">Maven系列（一）Maven的简介与使用</a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/qqhjqs/article/details/47045585\" target=\"_blank\">Maven系列（二）无Maven不项目—使用Eclipse快速搭建Maven项目 </a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/qqhjqs/article/details/53495535\" target=\"_blank\">Maven系列（三）Maven给不同的环境打包 </a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/qqhjqs/article/details/51594583\" target=\"_blank\">Maven系列（四）Maven热部署 </a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/qqHJQS/article/details/52950147\" target=\"_blank\">Maven系列（五）CentOS7搭建最新GitLab </a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/qqHJQS/article/details/53561541\" target=\"_blank\">Maven系列（六）配合GitLab持续集成（CI）</a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/qqhjqs/article/details/72722156\" target=\"_blank\">Maven系列(七)assembly打包-程序和依赖jar包分开化</a></li>\n" +
                "</ul>\n" +
                "\n" +
                "<p>上一篇介绍的是“assembly打包-程序和依赖jar包分开化”的配置方法， 这一篇就来介绍下如何多环境的配置，这里请看清楚，是“程序和依赖jar包分开化+多环境”跟之前的不太一样哦。</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h2 id=\"需要修改的配置\"><a name=\"t1\" target=\"_blank\"></a>需要修改的配置</h2>\n" +
                "\n" +
                "<p>项目的目录结构 <br>\n" +
                "<img src=\"http://upload-images.jianshu.io/upload_images/3167229-b9316fad6897592f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"工程结构.png\" title=\"\"></p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h3 id=\"pom\"><a name=\"t2\" target=\"_blank\"></a>pom</h3>\n" +
                "\n" +
                "<p>添加<code>profile</code>配置，我这里同样配置了三种环境</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<pre class=\"prettyprint\" name=\"code\"><code class=\"language-xml hljs  has-numbering\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">profiles</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">profile</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">id</span>&gt;</span>local<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">id</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">properties</span>&gt;</span>\n" +
                "                <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">env</span>&gt;</span>local<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">env</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">properties</span>&gt;</span>\n" +
                "            <span class=\"hljs-comment\">&lt;!-- 如果不指定ID，默认是本地环境--&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">activation</span>&gt;</span>\n" +
                "                <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">activeByDefault</span>&gt;</span>true<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">activeByDefault</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">activation</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">profile</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">profile</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">id</span>&gt;</span>test<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">id</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">properties</span>&gt;</span>\n" +
                "                <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">env</span>&gt;</span>test<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">env</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">properties</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">profile</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">profile</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">id</span>&gt;</span>product<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">id</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">properties</span>&gt;</span>\n" +
                "                <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">env</span>&gt;</span>product<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">env</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">properties</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">profile</span>&gt;</span>\n" +
                "    <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">profiles</span>&gt;</span></code><ul class=\"pre-numbering\" style=\"opacity: 0;\"><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li><li>12</li><li>13</li><li>14</li><li>15</li><li>16</li><li>17</li><li>18</li><li>19</li><li>20</li><li>21</li><li>22</li><li>23</li><li>24</li></ul><div class=\"save_code tracking-ad\" data-mod=\"popu_249\"><a href=\"javascript:;\" target=\"_blank\"><img src=\"http://static.blog.csdn.net/images/save_snippets.png\"></a></div><ul class=\"pre-numbering\"><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li><li>12</li><li>13</li><li>14</li><li>15</li><li>16</li><li>17</li><li>18</li><li>19</li><li>20</li><li>21</li><li>22</li><li>23</li><li>24</li></ul></pre>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h3 id=\"packagexml\"><a name=\"t3\" target=\"_blank\"></a>package.xml</h3>\n" +
                "\n" +
                "<p>新增了两处</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<pre class=\"prettyprint\" name=\"code\"><code class=\"language-xml hljs  has-numbering\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">fileSets</span>&gt;</span>\n" +
                "        <span class=\"hljs-comment\">&lt;!--需要包含的文件与输出的路径--&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">directory</span>&gt;</span>src/main/bin<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">directory</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">outputDirectory</span>&gt;</span>bin/<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">outputDirectory</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">directory</span>&gt;</span>src/main/resources<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">directory</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">outputDirectory</span>&gt;</span>/<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">outputDirectory</span>&gt;</span>\n" +
                "            <span class=\"hljs-comment\">&lt;!-- 去除需要多环境配置的文件--&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">excludes</span>&gt;</span>\n" +
                "                <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">exclude</span>&gt;</span>application.properties<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">exclude</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">excludes</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "        <span class=\"hljs-comment\">&lt;!--多环境配置--&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "            <span class=\"hljs-comment\">&lt;!--${env} 可以获取打包命令里的参数--&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">directory</span>&gt;</span>src/main/resources/env/${env}/<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">directory</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">outputDirectory</span>&gt;</span>/<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">outputDirectory</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">directory</span>&gt;</span>${project.build.directory}<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">directory</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">outputDirectory</span>&gt;</span>/<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">outputDirectory</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">includes</span>&gt;</span>\n" +
                "                <span class=\"hljs-tag\">&lt;<span class=\"hljs-title\">include</span>&gt;</span>*.jar<span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">include</span>&gt;</span>\n" +
                "            <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">includes</span>&gt;</span>\n" +
                "        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">fileSet</span>&gt;</span>\n" +
                "    <span class=\"hljs-tag\">&lt;/<span class=\"hljs-title\">fileSets</span>&gt;</span></code><ul class=\"pre-numbering\" style=\"opacity: 0;\"><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li><li>12</li><li>13</li><li>14</li><li>15</li><li>16</li><li>17</li><li>18</li><li>19</li><li>20</li><li>21</li><li>22</li><li>23</li><li>24</li><li>25</li><li>26</li><li>27</li><li>28</li></ul><div class=\"save_code tracking-ad\" data-mod=\"popu_249\"><a href=\"javascript:;\" target=\"_blank\"><img src=\"http://static.blog.csdn.net/images/save_snippets.png\"></a></div><ul class=\"pre-numbering\"><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li><li>12</li><li>13</li><li>14</li><li>15</li><li>16</li><li>17</li><li>18</li><li>19</li><li>20</li><li>21</li><li>22</li><li>23</li><li>24</li><li>25</li><li>26</li><li>27</li><li>28</li></ul></pre>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h2 id=\"多环境打包测试\"><a name=\"t4\" target=\"_blank\"></a>多环境打包测试</h2>\n" +
                "\n" +
                "<table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "  <th>环境</th>\n" +
                "  <th>命令</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody><tr>\n" +
                "  <td>本地</td>\n" +
                "  <td><code>mvn clean package -P local</code></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "  <td>测试</td>\n" +
                "  <td><code>mvn clean package -P test</code></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "  <td>生产</td>\n" +
                "  <td><code>mvn clean package -P product</code></td>\n" +
                "</tr>\n" +
                "</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h2 id=\"后记\"><a name=\"t5\" target=\"_blank\"></a>后记</h2>\n" +
                "\n" +
                "<ul>\n" +
                "<li>关于assembly打包，mybatis的xml访问不了的问题已经解决了，注意配置<code>mybatis.mapperLocations=classpath:mapper/*.xml</code></li>\n" +
                "<li>maven的功能之强大到你无法想象，我之前的一系列文章对我所接触到的maven所有用法都有较详细的配置说明</li>\n" +
                "<li>以后有可能会开始尝试使用gradle打包</li>\n" +
                "</ul>\n" +
                "\n" +
                "<p>欢迎浏览<a href=\"http://vector4wang.tk/2017/06/24/Maven%E7%B3%BB%E5%88%97-%E5%85%AB-assembly%E6%89%93%E5%8C%85-%E7%A8%8B%E5%BA%8F%E5%92%8C%E4%BE%9D%E8%B5%96jar%E5%8C%85%E5%88%86%E5%BC%80%E5%8C%96-%E5%A4%9A%E7%8E%AF%E5%A2%83/\" target=\"_blank\">我的博客</a> <br>\n" +
                "代码在<a href=\"https://github.com/vector4wang/spring-boot-quick\" target=\"_blank\">Github</a></p></div>\n" +
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
                "</div>";

        String s = convertHtml4csdn(html, "UTF-8");
        System.out.println(s);
    }

    private static String convertHtml4csdn(String html, String charset) {
        Document doc = Jsoup.parse(html, charset);
        Element content = doc.select("#article_content").get(0);
        content.select(".dp-highlighter").remove();
        Document parse = Jsoup.parse(content.html());
        return getTextContent(parse);
    }


    public static String convert(String theHTML, String baseURL) {
        Document doc = Jsoup.parse(theHTML, baseURL);

        return parseDocument(doc);
    }

    public static String convert(URL url, int timeoutMillis) throws IOException {
        Document doc = Jsoup.parse(url, timeoutMillis);

        return parseDocument(doc);
    }

    public static String convertHtml(String html, String charset) throws IOException {
        Document doc = Jsoup.parse(html, charset);

        return parseDocument(doc);
    }

    public static String convertFile(File file, String charset) throws IOException {
        Document doc = Jsoup.parse(file, charset);

        return parseDocument(doc);
    }

    public static void htmlToJekyllMd(String htmlPath, String mdPath, String charset) {
        try {
            List<File> fileList = FilesUtil.getAllFiles(htmlPath, "html");
            for (File file : fileList) {
                String mdName = file.getAbsolutePath().replace(htmlPath, mdPath).replace("html", "md");
                String hmPath = mdName.substring(0, mdName.lastIndexOf("/")) + "/";
                String separator = System.getProperty("line.separator");
                String head = "---" + separator +
                        "layout: post" + separator +
                        "title: \"" + file.getName() + "\"" + separator +
                        "description: \"" + file.getName() + "\"" + separator +
                        "category: pages\"" + separator +
                        "tags: [blog]\"" + separator +
                        "--- " + separator +
                        "{% include JB/setup %}" + separator
                        + separator;
                FilesUtil.isExist(hmPath);
                String parsedText = convertFile(file, charset);
                Calendar calendar = Calendar.getInstance();
                String dateName = DateUtil.dateToShortString(calendar.getTime());
                String newName = dateName + "-" + hmPath.replace(mdPath, "").replace("/", "-") + "-" + file.getName();
                String mmName = (hmPath + newName.replace("html", "md")).replaceAll("\\s*", "");
                FilesUtil.newFile(mmName, head + parsedText, charset);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void htmlToHexoMd(String htmlPath, String mdPath, String charset) {
        try {
            List<File> fileList = FilesUtil.getAllFiles(htmlPath, "html");
            for (File file : fileList) {
                String mdName = file.getAbsolutePath().replace(htmlPath, mdPath).replace("html", "md");
                String hmPath = mdName.substring(0, mdName.lastIndexOf("/")) + "/";
                String separator = System.getProperty("line.separator");
                String[] strings = hmPath.replace(mdPath, "").split("/");
                Calendar calendar = Calendar.getInstance();
                String dateName = DateUtil.dateToShortString(calendar.getTime());
                String dateString = DateUtil.dateToLongString(calendar.getTime());
                StringBuilder blog = new StringBuilder();
                StringBuilder categories = new StringBuilder();
                Map<String, String> stringMap = new TreeMap<String, String>();
                for (String value : strings) {
                    stringMap.put(value, value);
                }
                for (String tag : stringMap.keySet()) {
                    blog.append(" - ").append(tag).append(separator);
                }
                categories.append(strings[0]);
                String head = "---" + separator +
                        "layout: post" + separator +
                        "title: \"" + file.getName().replace(".html", "").split("-")[0] + "\"" + separator +
                        "date: " + dateString + separator +
                        "categories: " + categories + separator +
                        "tags: " + separator +
                        blog.toString() +
                        "--- " + separator +
                        separator;
                FilesUtil.isExist(hmPath);
                String parsedText = HTML2Md.convertFile(file, "utf-8");
                String newName = dateName + "-" + hmPath.replace(mdPath, "").replace("/", "-") + "-" + file.getName();
                String mmName = (hmPath + newName.replace("html", "md")).replaceAll("\\s*", "");
                FilesUtil.newFile(mmName, head + parsedText, charset);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String parseDocument(Document dirtyDoc) {
        indentation = -1;

        String title = dirtyDoc.title();

        Whitelist whitelist = Whitelist.relaxed();
        Cleaner cleaner = new Cleaner(whitelist);

        Document doc = cleaner.clean(dirtyDoc);
        doc.outputSettings().escapeMode(EscapeMode.xhtml);

        if (!title.trim().equals("")) {
            return "# " + title + "\n\n" + getTextContent(doc);
        } else {
            return getTextContent(doc);
        }
    }

    private static String getTextContent(Element element) {
        ArrayList<MDLine> lines = new ArrayList<MDLine>();
        List<Node> children = element.childNodes();
        for (Node child : children) {
            if (child instanceof TextNode) {
                TextNode textNode = (TextNode) child;
                MDLine line = getLastLine(lines);
                if (line.getContent().equals("")) {
                    if (!textNode.isBlank()) {
                        line.append(textNode.text().replaceAll("#", "/#").replaceAll("\\*", "/\\*"));
                    }
                } else {
                    line.append(textNode.text().replaceAll("#", "/#").replaceAll("\\*", "/\\*"));
                }

            } else if (child instanceof Element) {
                Element childElement = (Element) child;
                processElement(childElement, lines);
            } else {
                System.out.println();
            }
        }

        int blankLines = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).toString().trim();
            if (line.equals("")) {
                blankLines++;
            } else {
                blankLines = 0;
            }
            if (blankLines < 2) {
                result.append(line);
                if (i < lines.size() - 1) {
                    result.append("\n");
                }
            }
        }

        return result.toString();
    }

    private static void processElement(Element element, ArrayList<MDLine> lines) {
        Tag tag = element.tag();

        String tagName = tag.getName();
        if (tagName.equals("div")) {
            div(element, lines);
        } else if (tagName.equals("p")) {
            p(element, lines);
        } else if (tagName.equals("br")) {
            br(lines);
        } else if (tagName.matches("^h[0-9]+$")) {
            h(element, lines);
        } else if (tagName.equals("strong") || tagName.equals("b")) {
            strong(element, lines);
        } else if (tagName.equals("em")) {
            em(element, lines);
        } else if (tagName.equals("hr")) {
            hr(lines);
        } else if (tagName.equals("a")) {
            a(element, lines);
        } else if (tagName.equals("img")) {
            img(element, lines);
        } else if (tagName.equals("code")) {
            code(element, lines);
        } else if (tagName.equals("ul")) {
            ul(element, lines);
        } else if (tagName.equals("ol")) {
            ol(element, lines);
        } else if (tagName.equals("li")) {
            li(element, lines);
        } else if (tagName.equals("pre")) {
            // 个人添加
            pre(element, lines);
        } else {
            MDLine line = getLastLine(lines);
            line.append(getTextContent(element));
        }
    }


    private static MDLine getLastLine(ArrayList<MDLine> lines) {
        MDLine line;
        if (lines.size() > 0) {
            line = lines.get(lines.size() - 1);
        } else {
            line = new MDLine(MDLine.MDLineType.None, 0, "");
            lines.add(line);
        }

        return line;
    }

    private static void div(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        String content = getTextContent(element);
        if (!content.equals("")) {
            if (!line.getContent().trim().equals("")) {
                lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
                lines.add(new MDLine(MDLine.MDLineType.None, 0, content));
                lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
            } else {
                if (!content.trim().equals(""))
                    line.append(content);
            }
        }
    }

    private static void p(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        if (!line.getContent().trim().equals(""))
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, getTextContent(element)));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        if (!line.getContent().trim().equals(""))
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void br(ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        if (!line.getContent().trim().equals(""))
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void h(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        if (!line.getContent().trim().equals(""))
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));

        int level = Integer.valueOf(element.tagName().substring(1));
        switch (level) {
            case 1:
                lines.add(new MDLine(MDLine.MDLineType.Head1, 0, getTextContent(element)));
                break;
            case 2:
                lines.add(new MDLine(MDLine.MDLineType.Head2, 0, getTextContent(element)));
                break;
            default:
                lines.add(new MDLine(MDLine.MDLineType.Head3, 0, getTextContent(element)));
                break;
        }

        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void strong(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        line.append("**");
        line.append(getTextContent(element));
        line.append("**");
    }

    private static void em(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        line.append("*");
        line.append(getTextContent(element));
        line.append("*");
    }

    private static void hr(ArrayList<MDLine> lines) {
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.HR, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void a(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        line.append("[");
        line.append(getTextContent(element));
        line.append("]");
        line.append("(");
        String url = element.attr("href");
        line.append(url);
        String title = element.attr("title");
        if (!title.equals("")) {
            line.append(" \"");
            line.append(title);
            line.append("\"");
        }
        line.append(")");
    }

    private static void img(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);

        line.append("![");
        String alt = element.attr("alt");
        line.append(alt);
        line.append("]");
        line.append("(");
        String url = element.attr("src");
        line.append(url);
        String title = element.attr("title");
        if (!title.equals("")) {
            line.append(" \"");
            line.append(title);
            line.append("\"");
        }
        line.append(")");
    }

    private static void code(Element element, ArrayList<MDLine> lines) {
//    System.out.println(element);
//    System.out.println(lines);
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "    ");

        line.append(getTextContent(element).replace("\n", "    "));
        lines.add(line);
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void ul(Element element, ArrayList<MDLine> lines) {
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        indentation++;
        orderedList = false;
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        line.append(getTextContent(element));
        lines.add(line);
        indentation--;
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void ol(Element element, ArrayList<MDLine> lines) {
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        indentation++;
        orderedList = true;
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        line.append(getTextContent(element));
        lines.add(line);
        indentation--;
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void li(Element element, ArrayList<MDLine> lines) {
        MDLine line;
        if (orderedList) {
            line = new MDLine(MDLine.MDLineType.Ordered, indentation,
                    getTextContent(element));
        } else {
            line = new MDLine(MDLine.MDLineType.Unordered, indentation,
                    getTextContent(element));
        }
        lines.add(line);
    }

    private static void pre(Element element, ArrayList<MDLine> lines) {
//        System.out.println(element);
        Elements code = element.select("pre");
        for (Element item : code) {
            item.select(".pre-numbering").remove();
            String s = "```\n\t" + item.text() + "\n```";
            MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
            line.append(s);
            lines.add(line);
        }

//    System.out.println(element);
//    System.out.println(lines);
    }
}