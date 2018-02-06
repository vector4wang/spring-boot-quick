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
                "\n" +
                "<p><span style=\"font-size:14px\">前段时间接触了websocket，具体的就不介绍了，他就是与后台建立长连接，完成信息的发送与接受，有兴趣看我之前的blog或者google一下！之前完成了单聊与多聊的功能，分别实用tomcat7和tomcat8实现！那时候再想做个你画我猜的小游戏，但没有时间！现在终于抽出时间做了一个，有点简陋，看效果（代码是在在tomcat8的单聊与群聊基础上添加的）</span></p>\n" +
                "<p><span style=\"font-size:14px\"><img src=\"http://img.blog.csdn.net/20151104235956446?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast\" alt=\"\" align=\"left\" height=\"427\" width=\"680\"></span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\"><br>\n" +
                "</span></p>\n" +
                "<p><span style=\"font-size:14px\">要选择对放姓名，才可以绘制，对方实时显示，但是有个问题，对方的画板，不连续，因为不是以直线的方式绘制，而是已点的方式绘制（其实就是（x-1,y-1）lineTo(x,y)），传过去的是个坐标，现在就只想到这个方法</span></p>\n" +
                "<p></p>\n" +
                "<div class=\"dp-highlighter bg_javascript\"><div class=\"bar\"><div class=\"tools\"><b>[javascript]</b> <a href=\"#\" class=\"ViewSource\" title=\"view plain\" onclick=\"dp.sh.Toolbar.Command('ViewSource',this);return false;\" target=\"_blank\">view plain</a><span data-mod=\"popu_168\"> <a href=\"#\" class=\"CopyToClipboard\" title=\"copy\" onclick=\"dp.sh.Toolbar.Command('CopyToClipboard',this);return false;\" target=\"_blank\">copy</a><div style=\"position: absolute; left: 758px; top: 954px; width: 18px; height: 18px; z-index: 99;\"><embed id=\"ZeroClipboardMovie_1\" src=\"http://static.blog.csdn.net/scripts/ZeroClipboard/ZeroClipboard.swf\" loop=\"false\" menu=\"false\" quality=\"best\" bgcolor=\"#ffffff\" width=\"18\" height=\"18\" name=\"ZeroClipboardMovie_1\" align=\"middle\" allowscriptaccess=\"always\" allowfullscreen=\"false\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" flashvars=\"id=1&amp;width=18&amp;height=18\" wmode=\"transparent\"></div></span><span data-mod=\"popu_169\"> <a href=\"#\" class=\"PrintSource\" title=\"print\" onclick=\"dp.sh.Toolbar.Command('PrintSource',this);return false;\" target=\"_blank\">print</a></span><a href=\"#\" class=\"About\" title=\"?\" onclick=\"dp.sh.Toolbar.Command('About',this);return false;\" target=\"_blank\">?</a></div></div><ol start=\"1\" class=\"dp-c\"><li class=\"alt\"><span><span>&lt;span&nbsp;style=</span><span class=\"string\">\"font-size:14px;\"</span><span>&gt;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"keyword\">var</span><span>&nbsp;coordArry&nbsp;=&nbsp;o.data.split(</span><span class=\"string\">\"_\"</span><span>);&nbsp;&nbsp;</span></span></li><li class=\"\"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"keyword\">var</span><span>&nbsp;x&nbsp;=&nbsp;coordArry[0];&nbsp;&nbsp;</span></span></li><li class=\"alt\"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"keyword\">var</span><span>&nbsp;y&nbsp;=&nbsp;coordArry[1];&nbsp;&nbsp;</span></span></li><li class=\"\"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;oGC.lineWidth&nbsp;=&nbsp;1;&nbsp;&nbsp;&nbsp;&nbsp;</span></li><li class=\"alt\"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;oGC.moveTo(x-1,y-1);&nbsp;&nbsp;</span></li><li class=\"\"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;oGC.lineTo(x,y);&nbsp;&nbsp;</span></li><li class=\"alt\"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;oGC.stroke();&lt;/span&gt;&nbsp;&nbsp;</span></li></ol></div><pre name=\"code\" class=\"javascript\" style=\"display: none;\">&lt;span style=\"font-size:14px;\"&gt;                                var coordArry = o.data.split(\"_\");\n" +
                "\t\t\t\tvar x = coordArry[0];\n" +
                "\t\t\t\tvar y = coordArry[1];\n" +
                "\t\t\t\toGC.lineWidth = 1;\t\n" +
                "\t\t\t\toGC.moveTo(x-1,y-1);\n" +
                "\t\t\t\toGC.lineTo(x,y);\n" +
                "\t\t\t\toGC.stroke();&lt;/span&gt;</pre><span style=\"font-size:14px\">使用的是HTML5 的canvas，绘制直线的代码如下：</span>\n" +
                "<p></p>\n" +
                "<p></p>\n" +
                "<div class=\"dp-highlighter bg_javascript\"><div class=\"bar\"><div class=\"tools\"><b>[javascript]</b> <a href=\"#\" class=\"ViewSource\" title=\"view plain\" onclick=\"dp.sh.Toolbar.Command('ViewSource',this);return false;\" target=\"_blank\">view plain</a><span data-mod=\"popu_168\"> <a href=\"#\" class=\"CopyToClipboard\" title=\"copy\" onclick=\"dp.sh.Toolbar.Command('CopyToClipboard',this);return false;\" target=\"_blank\">copy</a><div style=\"position: absolute; left: 758px; top: 1175px; width: 18px; height: 18px; z-index: 99;\"><embed id=\"ZeroClipboardMovie_2\" src=\"http://static.blog.csdn.net/scripts/ZeroClipboard/ZeroClipboard.swf\" loop=\"false\" menu=\"false\" quality=\"best\" bgcolor=\"#ffffff\" width=\"18\" height=\"18\" name=\"ZeroClipboardMovie_2\" align=\"middle\" allowscriptaccess=\"always\" allowfullscreen=\"false\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" flashvars=\"id=2&amp;width=18&amp;height=18\" wmode=\"transparent\"></div></span><span data-mod=\"popu_169\"> <a href=\"#\" class=\"PrintSource\" title=\"print\" onclick=\"dp.sh.Toolbar.Command('PrintSource',this);return false;\" target=\"_blank\">print</a></span><a href=\"#\" class=\"About\" title=\"?\" onclick=\"dp.sh.Toolbar.Command('About',this);return false;\" target=\"_blank\">?</a></div></div><ol start=\"1\" class=\"dp-c\"><li class=\"alt\"><span><span>&lt;span&nbsp;style=</span><span class=\"string\">\"font-size:14px;\"</span><span>&gt;</span><span class=\"keyword\">var</span><span>&nbsp;c=document.getElementById(</span><span class=\"string\">\"myCanvas\"</span><span>);&nbsp;&nbsp;</span></span></li><li class=\"\"><span><span class=\"keyword\">var</span><span>&nbsp;cxt=c.getContext(</span><span class=\"string\">\"2d\"</span><span>);&nbsp;&nbsp;</span></span></li><li class=\"alt\"><span>cxt.moveTo(10,10);&nbsp;&nbsp;</span></li><li class=\"\"><span>cxt.lineTo(150,50);&nbsp;&nbsp;</span></li><li class=\"alt\"><span>cxt.lineTo(10,50);&nbsp;&nbsp;</span></li><li class=\"\"><span>cxt.stroke();&lt;/span&gt;&nbsp;&nbsp;</span></li></ol></div><pre name=\"code\" class=\"javascript\" style=\"display: none;\">&lt;span style=\"font-size:14px;\"&gt;var c=document.getElementById(\"myCanvas\");\n" +
                "var cxt=c.getContext(\"2d\");\n" +
                "cxt.moveTo(10,10);\n" +
                "cxt.lineTo(150,50);\n" +
                "cxt.lineTo(10,50);\n" +
                "cxt.stroke();&lt;/span&gt;</pre><span style=\"font-size:14px\">关于这个可以去W3C去学，那里看起来简单，但是非常的基础，非常的重要</span>\n" +
                "<p></p>\n" +
                "<p><span style=\"font-size:14px\">你画我猜就是把本地画笔的<span style=\"font-size:14px\">实时</span>坐标发送给对方，然后根据坐标再绘制出来，思路很简单，就是如何处理这些坐标</span></p>\n" +
                "<p><span style=\"font-size:14px\">代码没有什么改变，就是后台加了个类型，前台加个判断，就ok了，代码不要钱了这次，上传到<a target=\"_blank\" href=\"https://github.com/VBMHJQS/tomcat8-websocket\">GITHUB</a>了！</span></p>\n" +
                "<p><span style=\"font-size:14px\">对于以上提到的问题，以后有时间再改，马上双十一了，天天加班啊。，。。。。<br>\n" +
                "</span></p>\n" +
                "   \n" +
                "</div>";

        String s = convertHtml4csdn(html, "UTF-8");
        System.out.println(s);
    }

    public static String convertHtml4csdn(String html, String charset) {
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
            item.select("span").remove();
            String s = "```\n\t" + item.text() + "\n```";
            MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
            line.append(s);
            lines.add(line);
            lines.add(new MDLine(MDLine.MDLineType.None, 0, "\n\t"));
        }

//    System.out.println(element);
//    System.out.println(lines);
    }
}