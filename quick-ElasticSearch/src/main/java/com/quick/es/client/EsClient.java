package com.quick.es.client;


import com.quick.es.config.ESClientConfig;
import com.quick.es.entity.IndustryPosition;
import com.quick.es.util.DataFactory;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
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
import java.util.List;
import java.util.Random;

public class EsClient {

    public final static String INDEX = "testcase2";
    public final static String TYPE = "candidates";

    public static void main(String[] args) {
        try {
//            createCluterName(INDEX);
            // TODO
//            createMapping();
//            createIndex();
//            search();
            ESClientConfig.getClient();
            for(int i=0; i<10000; i++){

                System.out.println("==================================================================================================");
                List<IndustryPosition> industryPositions = new ArrayList<>();
                industryPositions.add(new IndustryPosition("","java"));
                industryPositions.add(new IndustryPosition("","android"));
                industryPositions.add(new IndustryPosition("","ios"));
                industryPositions.add(new IndustryPosition("","c++"));
                industryPositions.add(new IndustryPosition("","javascript"));
                industryPositions.add(new IndustryPosition("","产品"));
                long start = System.currentTimeMillis();
                qualityResume(industryPositions, "中软", null).forEach(item-> System.out.println(item.toString()));
                long end = System.currentTimeMillis();
                System.out.println("耗时" + (double) (end - start) / 1000 + "s。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("properties")
                    .startObject()
                    .field("resume_id").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("address").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("created").startObject().field("index", "not_analyzed").field("type", "date").endObject()
                    .field("mobile").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("real_name").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("type").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("year_of_experience").startObject().field("index", "not_analyzed").field("type", "integer").endObject()
                    .field("content").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("company").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .field("updated").startObject().field("index", "not_analyzed").field("type", "date").endObject()
                    .field("title").startObject().field("index", "not_analyzed").field("type", "string").endObject()
                    .endObject()
                    .endObject();

            PutMappingRequest source = Requests.putMappingRequest(INDEX).type(TYPE).source(mapping);
            TransportClient client = ESClientConfig.getClient();
            client.admin().indices().putMapping(source).actionGet();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static List<String> qualityResume(List<IndustryPosition> industryPositions, String company, String address) throws UnknownHostException {

        List<String> result = new ArrayList<>();
        int size = industryPositions.size();
        if(size==1){
            IndustryPosition industryPosition = industryPositions.get(0);
            result = getSearchResult(industryPosition.getPosition(), company, address, 5);
        }else if(size==2){
            IndustryPosition industryPosition = industryPositions.get(0);
            List<String> searchResult = getSearchResult(industryPosition.getPosition(), company, address, 3);
            IndustryPosition industryPosition1 = industryPositions.get(1);
            List<String> searchResult1 = getSearchResult(industryPosition1.getPosition(), company, address, 2);
            result.addAll(searchResult);
            result.addAll(searchResult1);
        }else if(size==3){
            IndustryPosition industryPosition = industryPositions.get(0);
            List<String> searchResult = getSearchResult(industryPosition.getPosition(), company, address, 3);
            IndustryPosition industryPosition1 = industryPositions.get(1);
            List<String> searchResult1 = getSearchResult(industryPosition1.getPosition(), company, address, 1);
            IndustryPosition industryPosition2 = industryPositions.get(2);
            List<String> searchResult2 = getSearchResult(industryPosition2.getPosition(), company, address, 1);
            result.addAll(searchResult);
            result.addAll(searchResult1);
            result.addAll(searchResult2);
        }else if(size==4){
            IndustryPosition industryPosition = industryPositions.get(0);
            List<String> searchResult = getSearchResult(industryPosition.getPosition(), company, address, 2);
            IndustryPosition industryPosition1 = industryPositions.get(1);
            List<String> searchResult1 = getSearchResult(industryPosition1.getPosition(), company, address, 1);
            IndustryPosition industryPosition2 = industryPositions.get(2);
            List<String> searchResult2 = getSearchResult(industryPosition2.getPosition(), company, address, 1);
            IndustryPosition industryPosition3 = industryPositions.get(3);
            List<String> searchResult3 = getSearchResult(industryPosition3.getPosition(), company, address, 1);
            result.addAll(searchResult);
            result.addAll(searchResult1);
            result.addAll(searchResult2);
            result.addAll(searchResult3);
        }else if (size>=5){
            IndustryPosition industryPosition = industryPositions.get(0);
            List<String> searchResult = getSearchResult(industryPosition.getPosition(), company, address, 1);
            IndustryPosition industryPosition1 = industryPositions.get(1);
            List<String> searchResult1 = getSearchResult(industryPosition1.getPosition(), company, address, 1);
            IndustryPosition industryPosition2 = industryPositions.get(2);
            List<String> searchResult2 = getSearchResult(industryPosition2.getPosition(), company, address, 1);

            List<Integer> temp = new ArrayList<>();
            for(int i=0;i<1000;i++){
                int i1 = new Random().nextInt(size);
                if(i1>2){
                    temp.add(i1);
                    if(temp.size()>=2)break;
                }
            }

            IndustryPosition industryPosition11 = industryPositions.get(temp.get(0));
            List<String> searchResult11 = getSearchResult(industryPosition11.getPosition(), company, address, 1);
            IndustryPosition industryPosition22 = industryPositions.get(temp.get(1));
            List<String> searchResult22 = getSearchResult(industryPosition22.getPosition(), company, address, 1);
            result.addAll(searchResult);
            result.addAll(searchResult1);
            result.addAll(searchResult2);
            result.addAll(searchResult11);
            result.addAll(searchResult22);
        }else{}

        return result;
    }

    public static List<String> getSearchResult(String title,String company,String address,int length) throws UnknownHostException {
        List<String> result = new ArrayList<>();
        SearchResponse scrollResp = ESClientConfig.getClient()
                .prepareSearch("data_quality_resume")
                .setTypes("resume") //
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", title))
//                        .must(QueryBuilders.matchQuery("degree", "本科"))
                        .mustNot(QueryBuilders.matchQuery("company", company)))
//                        .must(QueryBuilders.rangeQuery("year_of_experience").gte(2).lte(11)))
                .addSort(SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER))
                .setSize(length).get();
        SearchHits searchHits = scrollResp.getHits();
        for(SearchHit item:searchHits){
            result.add(item.getSourceAsString());
        }
        return result;
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
                .prepareSearch("data_quality_resume")
                .setTypes("resume") //
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", "java"))
//                        .must(QueryBuilders.rangeQuery("year_of_experience").gte(2).lte(11))
                        .mustNot(QueryBuilders.matchQuery("company", "秦皇岛易达软件有限公司")))
                .setSize(2)
                .addSort(SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER))
                .get();
        SearchResponse scrollResp2 = client
                .prepareSearch("data_quality_resume")
                .setTypes("resume") //
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", "ios")))
                .addSort(SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER))
                .setSize(2).get();
        SearchResponse scrollResp3 = client
                .prepareSearch("data_quality_resume")
                .setTypes("resume") //
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", "android")))
                .addSort(SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER))
                .setSize(2).get();


        SearchHits searchHits1 = scrollResp1.getHits();
        SearchHits searchHits2 = scrollResp2.getHits();
        SearchHits searchHits3 = scrollResp3.getHits();
        long end = System.currentTimeMillis();
        //共搜到:" + searchHits.getTotalHits() + "条结果!共
        System.out.println("耗时" + (double) (end - start) / 1000 + "s。");
        //遍历结果
        for (SearchHit hit : searchHits1) {
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("++++++++++++++++++++++++++");
        for (SearchHit hit : searchHits2) {
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("++++++++++++++++++++++++++");
        for (SearchHit hit : searchHits3) {
            System.out.println(hit.getSourceAsString());
        }
//        System.out.println(searchHits1.toString());
//        System.out.println(searchHits2.toString());
//        System.out.println(searchHits3.toString());

    }

    private static void createIndex() throws UnknownHostException {
        TransportClient client = ESClientConfig.getClient();

        // 创建索引和文档
        List<String> jsonData = DataFactory.getInitJsonData();
        for (int i = 0; i < jsonData.size(); i++) {
            IndexResponse indexResponse = client.prepareIndex("world", "city").setSource(jsonData.get(i)).get();
            System.out.println(indexResponse.status().name());
        }
//            GetResponse response = client.prepareGet("testcase", "candidates", "00176455-5a00-48b0-9e8d-a6a3011d470d").execute().actionGet();
//            System.out.println(response.getSourceAsString());
        client.close();
    }

}