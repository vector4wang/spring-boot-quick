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
            Elements select = element.select(".dp-highlighter");
            if (select.size() > 0) {
                for (Element item : select) {
                    String s = "```\n\t" + item.text() + "\n```";
                    line = new MDLine(MDLine.MDLineType.None, 0, "");
                    line.append(s);
                    lines.add(line);
                }
            } else {
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
        Elements code = element.select("code");
        for (Element item : code) {
            String s = "```\n\t" + item.text() + "\n```";
            MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
            line.append(s);
            lines.add(line);
        }

//    System.out.println(element);
//    System.out.println(lines);
    }
}