package com.quick.baza;

import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/30
 * Time: 14:12
 * Description:
 */
public class TestMain {
    final static WebClient client = new WebClient();
    public static void main(String[] args) {
        client.addRequestHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.2.2; Droid4X-WIN Build/JDQ39E)");
        client.addRequestHeader("Authorization", "Basic ZGZkZjQ2Y2ZhMGNlYzJlOThhODgyY2QyZjJmODRkMWE6Yzg4NjlmZGE5ZTliNzdkMmY3OTEwNmQ0ODg2MTI1MzU=");
//        client.
    }
}
