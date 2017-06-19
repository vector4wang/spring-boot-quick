package com.quick.es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/6
 * Time: 19:35
 * Description:
 */
public class ESClientConfig {

    private static TransportClient transportClient;

    public static TransportClient getClient() throws UnknownHostException {
        if(transportClient==null){
            Settings settings = Settings.builder()
                    .put("cluster.name", "elasticsearch").build();
            transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.174.128"), 9300));

        }
        return transportClient;
    }
}
