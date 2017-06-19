package com.quick.es.client;


import com.quick.es.config.ESClientConfig;
import com.quick.es.entity.IndustryPosition;
import com.quick.es.util.DataFactory;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class EsClient {

    public final static String INDEX = "world";
    public final static String TYPE = "city";

    public static void main(String[] args) {
        try {
            // 连接ES
            //connectionES();

            // 创建索引 curl -XPUT 'localhost:9200/customer?pretty'
            // createIndex();

            // 创建mapping
//             createMapping();

            // 插入数据
//             putDocument();

            // 查询
//            search();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void putDocument() throws IOException {


        TransportClient client = ESClientConfig.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex(INDEX, TYPE, "1")
                .setSource(jsonBuilder()
                        .startObject()
                            .field("id", "1")
                            .field("name", "Kabul")
                            .field("countryCode", "AFG")
                            .field("district", "Kabol")
                            .field("population", "1780000")
                            .field("insertDate", new Date())
                        .endObject())
                );
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("插入失败");
        }

    }

    private static void connectionES() throws UnknownHostException {
        TransportClient client = ESClientConfig.getClient();
        System.out.println(client.connectedNodes().size());
    }

    private static void createMapping() throws IOException {
        TransportClient client = ESClientConfig.getClient();
        XContentBuilder xContentBuilder = jsonBuilder()
                .startObject()
                .field("properties")
                .startObject()
                .field("id").startObject().field("type", "integer").endObject()
                .field("name").startObject().field("type", "text").endObject()
                .field("countryCode").startObject().field("type", "text").endObject()
                .field("district").startObject().field("type", "text").endObject()
                .field("population").startObject().field("type", "integer").endObject()
                .field("insertDate").startObject().field("type", "date").endObject()

                .endObject()
                .endObject();
        PutMappingResponse putMappingResponse = client.admin().indices()
                .preparePutMapping(INDEX)
                .setType("city2")
                .setSource(xContentBuilder)
                .get();

//        .setSource("{\n" +
//                "    \"city\": {\n" +
//                "      \"_all\": {\n" +
//                "        \"enabled\": false\n" +
//                "      },\n" +
//                "      \"properties\": {\n" +
//                "        \"id\": {\n" +
//                "          \"type\": \"integer\"\n" +
//                "        },\n" +
//                "        \"name\": {\n" +
//                "          \"type\": \"text\"\n" +
//                "        },\n" +
//                "        \"countryCode\": {\n" +
//                "          \"type\": \"text\"\n" +
//                "        },\n" +
//                "        \"district\": {\n" +
//                "          \"type\": \"text\"\n" +
//                "        },\n" +
//                "        \"population\": {\n" +
//                "          \"type\": \"integer\"\n" +
//                "        },\n" +
//                "        \"insertDate\": {\n" +
//                "          \"type\": \"date\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }")


        System.out.println(xContentBuilder.string());


    }



    public static void createCluterName(String indices) {
        TransportClient client = null;
        try {
            client = ESClientConfig.getClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.admin().indices().prepareCreate(indices).execute().actionGet();
        client.close();
    }

    private static void search() throws UnknownHostException {
        TransportClient client = ESClientConfig.getClient();


//        GetResponse response = client.prepareGet("world", "city", "AVx9ZrO1r3OKqmuL07ZS")
//                .setOperationThreaded(false)
//                .get();
//        System.out.println(response.getSourceAsString());

//        QueryBuilder qb = QueryBuilders.matchAllQuery();// 全部
//        QueryBuilder qb = QueryBuilders.matchQuery("name", "ar");
//        QueryBuilder qb = QueryBuilders.multiMatchQuery("herat","name","district"); // OR 多个字段
//        QueryBuilder qb = QueryBuilders.rangeQuery("population").gte(100000).lt(250000); // RANGER
//        QueryBuilder qb = QueryBuilders.termQuery("name","herat").boost(10);// 准确
//        QueryBuilder qb = QueryBuilders.fuzzyQuery("name","ar");// 模糊查询
//        QueryBuilder qb = QueryBuilders.termsQuery(); // 含有多个词条

//        QueryBuilder qb = QueryBuilders.matchQuery("name","ar").operator(Operator.AND);
//        QueryBuilder qb1 = QueryBuilders.matchQuery("name", "Qandahar");
        //                .must(QueryBuilders.termQuery("title","android"))
//                .mustNot(QueryBuilders.fuzzyQuery("location","广东"))
//                .must(QueryBuilders.matchQuery("degree","本科"))
//                .mustNot(QueryBuilders.matchQuery("company","腾讯"))// 不在这家公司
//                .mustNot(QueryBuilders.matchQuery("id","9F5AE922-344A-4036-84EF-ADB45292A2B7"))
//                .mustNot(QueryBuilders.matchQuery("id","a55d0234-3a76-4962-bab6-0b563e81ac1a"))
                /*.must(QueryBuilders.rangeQuery("yearofexperience").gte(3).lte(5))*/
        System.out.println(client.nodeName());
        long start = System.currentTimeMillis();


        SearchResponse scrollResp1 = client
                .prepareSearch(INDEX)
                .setTypes(TYPE) //
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("name", "Kabul")))
//                        .must(QueryBuilders.matchQuery("location_id", 432)))
                .setSize(1)
                .get();


        SearchHits searchHits1 = scrollResp1.getHits();
        long end = System.currentTimeMillis();
        //共搜到:" + searchHits.getTotalHits() + "条结果!共
        System.out.println("耗时" + (double) (end - start) / 1000 + "s。");
        //遍历结果
        for (SearchHit hit : searchHits1) {
            System.out.println(hit.getSourceAsString());
        }

    }

    private static void createIndex() throws UnknownHostException {
        TransportClient client = ESClientConfig.getClient();
        CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate("world").execute().actionGet();
        System.out.println(createIndexResponse.isShardsAcked());
        client.close();
    }

}