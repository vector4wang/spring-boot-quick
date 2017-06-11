package com.quick.baidu;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/6/11 0011.
 */
public class Test {
    public static void main(String[] args) throws IOException {

        WebClient client = new WebClient(BrowserVersion.CHROME);
        Page page = client.getPage("https://cas.baidu.com/?action=image&key=1497149653");
        InputStream contentAsStream = page.getWebResponse().getContentAsStream();

        IOUtils.copy(contentAsStream,new FileOutputStream(new File("d:\\bd.png")));

    }
}
