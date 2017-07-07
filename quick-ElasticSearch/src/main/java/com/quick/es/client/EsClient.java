package com.quick.es.client;


import com.quick.es.config.ESClientConfig;
import com.quick.es.util.DataFactory;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
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
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class EsClient {

    public final static String INDEX_TEST = "yzresume_test";
    public final static String TYPE_TEST = "yzresume_test";

    public final static String INDEX = "data_quality_resume";
    public final static String TYPE = "yzresume";

    public static void main(String[] args) {
        try {
//            createCluterName(INDEX);
            // TODO
//            createMapping();
//            createIndex();
            search();
//            putDocument();
//            deleteDocument();
            


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteDocument() throws UnknownHostException, ExecutionException, InterruptedException {
        TransportClient client = ESClientConfig.getClient();
        DeleteResponse deleteResponse = client.prepareDelete(INDEX_TEST, TYPE_TEST, "a79a03d9-a4f3-4b94-9c62-579c9777d853").execute().get();
        System.out.println(deleteResponse.status().getStatus());
    }

    private static void putDocument() throws IOException {


        TransportClient client = ESClientConfig.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex(INDEX_TEST, TYPE_TEST)
                .setSource(jsonBuilder()
                        .startObject()
                        .field("candidate_id", "1283901280938120938210938")
                        .field("company", "八爪网络")
                        .field("id", "sdgdfsgdfgdsf")
                        .field("location_id", "432")
                        .field("mobile", "18255062124")
                        .field("owner_id", "jalkdjfklasjfla;sjfkl;sd")
                        .field("real_name", "mrs down")
                        .field("source_updated", new Date())
                        .field("title", "java")
                        .field("updated", new Date())
                        .field("year_of_experience", 2)
                        .endObject())
        );
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("插入失败");
        }

    }

    private static void createMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                        .field("yzresume")
                            .startObject()
                                .field("properties")
                                .startObject()
                                .field("company").startObject().field("type", "text").endObject()
                                .field("id").startObject().field("type", "keyword").endObject()
                                .field("location_id").startObject().field("type", "long").endObject()
                                .field("mobile").startObject().field("type", "keyword").endObject()
                                .field("owner_id").startObject().field("type", "text").endObject()
                                .field("real_name").startObject().field("type", "string").endObject()
                                .field("year_of_experience").startObject().field("type", "integer").endObject()
                                .field("title").startObject().field("type", "text").endObject()
                                .field("updated").startObject().field("type", "date").endObject()
                                .field("source_updated").startObject().field("type", "date").endObject()
                                .field("candidate_id").startObject().field("type", "keyword").endObject()
                                .endObject()
                            .endObject()
                    .endObject();

            System.out.println(mapping.string());

//            PutMappingRequest source = Requests.putMappingRequest(INDEX).type(TYPE).source(mapping);
//            TransportClient client = ESClientConfig.getClient();
//            client.admin().indices().preparePutMapping(INDEX)
//                    .setType(TYPE).setSource(mapping).get();
//            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static List<String> getSearchResult(String title,String company,int location_id,int length) throws UnknownHostException {
        List<String> result = new ArrayList<>();
        SearchResponse scrollResp = ESClientConfig.getClient()
                .prepareSearch("data_quality_resume")
                .setTypes("resume") //
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", title))
                        .must(QueryBuilders.termQuery("location_id", 432)))
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

//        String[] fid = {"a7990254-20c1-468a-a196-3d15beed1f70","a79a0444-ce18-4b90-acb4-3b0897fa2982","a79c0384-6eb4-47c6-96f7-6fed5f95847b","a79a03cc-7588-4d3c-ba79-a4149cddde83",
//                "a79203e6-f288-4de0-b454-bc887b895991","a79203e6-dae5-4971-898b-fd1c5303a1f1","a79203e6-f111-465e-b6c7-aad80bf09af9"};
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder
//                .must(QueryBuilders.matchQuery("title", "cto"))
//                .mustNot(QueryBuilders.matchQuery("owner_id","a7850376-9bf7-4ad0-9bfd-997806dac036"));
////
//        List<String> strings = Arrays.asList(fid);
//        strings.forEach(t->queryBuilder.mustNot(QueryBuilders.matchQuery("talent_id",t)));

//        QueryBuilders.boolQuery().should(QueryBuilders.matchPhraseQuery("title", "产品经理").slop(2))
//                .should(QueryBuilders.matchQuery("title", "java"))
//                .should(QueryBuilders.matchQuery("title", "android"));

//        BoolQueryBuilder mobile = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("mobile", "13072130095");

        // QueryBuilders.matchPhraseQuery("title","产品经理").slop(5)

//        MatchPhraseQueryBuilder slop = QueryBuilders.matchPhraseQuery("title", "技术总监").slop(2);

        SearchResponse scrollResp1 = client
                .prepareSearch(INDEX)
                .setTypes(TYPE) //
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("title", "产品设计").slop(2)))
                .setSize(100)
                .addSort(SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER))
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

        // 创建索引和文档
        List<String> jsonData = DataFactory.getInitJsonData();
        for (int i = 0; i < jsonData.size(); i++) {
            IndexResponse indexResponse = client.prepareIndex("world", "city").setSource(jsonData.get(i)).get();
            System.out.println(indexResponse.status().name());
        }
//            GetResponse response = client.prepareGet("testcase", "candidates", "00176455-5a00-48b0-9e8d-a6a3011d470d").execute().actionGet();
//            System.out.println(response.getSourceAsString());
        client.close();

        String str = "\u000B更新时间：2016-01-25 范大伟 3-4年工作经验 | 男 |  25岁（1990年4月2日） |  未婚 |  177cm |  中共党员 \u000B(ID:300353072) 居住地： 上海-浦东新区 电　话： 13817366531（手机） E-mail： 402286552@qq.com \u0014402286552@qq.com\u0015 最近工作 [ 1 年] 公　司： 加拿大KFS国际建筑师事务所 行　业： 建筑/建材/工程 职　位： 市场总监 学历 学　历： 本科 专　业： 工程管理 学　校： 同济大学 目前薪资： 年薪 15-20万 人民币 自我评价 从工作伊始，专注于建筑行业市场方面工作，参与执行了集团公司旗下新公司的成立、组建、开业、市场调研、业务拓展全过程。对团队的组建有一定的心得体会，从一个人做市场起步，到成功组建功能齐备的市场部和销售部，熟悉各个环节之间的关联和流程。带领团队研究出符合市场需求的方案；根据市场调研反馈及时调整产品的价格。建立公司网站，在工作中与各大主流媒体以及公关公司保持良好的关系。英语口语流利，和外籍同事、客户交流无障碍。 求职意向 到岗时间： 一周以内 工作性质： 全职 希望行业： 建筑/建材/工程，房地产 目标地点： 上海 期望薪资： 月薪 10000-14999 目标职能： 市场/营销/拓展经理，商务经理，大客户管理 求职状态： 目前正在找工作 工作经验 2015 /1--至今：加拿大KFS国际建筑师事务所(少于50人) [ 1 年] 所属行业： 建筑/建材/工程 市场部 市场总监 1.制定年度市场营销计划和年度预算；\u000B2.制定品牌推广策略并落实；\u000B3.拓展新项目及维护客户关系；\u000B4.沟通协调项目进度；\u000B5.结合公司品牌推广和项目，策划并执行了两部关于建筑师的音乐剧：《河谷三号的故事》、《寻找安贝尔》，在各大剧院上演。 2014 /6--2015 /1：悉地国际(1000-5000人) [ 7个月] 所属行业： 建筑/建材/工程 公共建筑事业部-都市综合体子产品管理部 产品业务拓展经理 1.开拓新项目及维护重要的客户关系；\u000B2.建立大客户体系并进行有效管理；\u000B3.建立子产品分类体系，对不同类别的客户提供有针对性的应对措施；\u000B4.分析子产品经营数据，掌握市场动态；\u000B5.与区域和城市市场团队及资源团队密切配合，促使项目的落实；\u000B6.配合副总裁对公建项目及设计团队进行协调和管理。 2013 /8--2014 /6：上海方大建筑设计事务所(150-500人) [ 10个月] 所属行业： 建筑/建材/工程 市场部 区域市场经理 1.负责项目的商务洽谈及合同落实；\u000B2.负责公司集团客户及重大客户（万科、德信地产、正大、中梁集团等）的关系维护及项目的深度挖掘；\u000B3.所负责项目的回款工作；\u000B4.与设计师密切联系，与开发商进行沟通；\u000B5.兼管公司的品牌推广与宣传。 2012 /2--2013 /7：上海金汤旺河文化发展有限公司(150-500人) [ 1 年5个月] 所属行业： 房地产 市场部 市场部经理 负责集团旗下亚欧巄森林城堡乐园的整体市场计划和拓展； \u000B包括销售资料开发、公司网站创建以及销售的管理； \u000B与各大合作伙伴，商会、协会建立良好的合作关系；\u000B与总裁共同开展旗下旅游地产的营销策划；\u000B从公司筹建到正式运营全程参与并担任重要角色。 汇报对象： 总裁 下属人数： 12 证 明 人： Vicky Jia 工作业绩： 带领团队根据市场情况开发销售资料，包括brochure、三折页、名片设计，建立网站以及销售ppt的制作；\u000B研究产品，根据市场行情作出合理的产品价格调整，并及时向各地区销售人员公布；\u000B帮助销售团队与各大合作伙伴建立良好的合作关系；\u000B策划乐园的第一届跨国夏季森林音乐会暨开幕仪式，为乐园的宣传造势。 教育经历 2013 /12--至今 同济大学 工程管理 本科 2009 /9--2012 /6 上海第二工业大学 建筑经济管理 大专 主要包括会计、建筑CAD、金融学、管理学、房屋构造、建筑材料、工程招标与合同管理、市场营销、工程造价的确定与控制、建筑工程项目管理、建筑工程概预算等课程。了解房地产行业与建筑行业的知识以及营销等经济知识。\u000B在校期间连续三年获得校级奖学金，并被评为校级优秀学生干部。 培训经历 2014 /2--2014 /6： EMBA总裁班教授戴剑老师 EMBA市场营销培训 语言能力 英语（熟练）： 听说（熟练），读写（熟练） 英语等级： 英语四级 FanDavid 3-4 years| Male |  25 Years old(1990/4/2) |  Unmarried |  177cm |  Party Member \u000B(ID:300353072) Residency: Shanghai-Pudong Telephone number: 13817366531(Mobile Phone) E-mail: 402286552@qq.com \u0014402286552@qq.com\u0015 The latest work [ 1 year] Company: K. F. Stone Design International Inc. Canada Industry: Architectural Services/Building Materials/Construction Job Title: Marketing Director Education Degree: Bachelor Major: Engineering Management School: Tongji University Current Salary: Annual Salary 150,000-200,000 RMB Self Assessment Involved in executing the whole program of founding,structuring,opening,marketing investigation and business expanding of new company subsidiary to the Group Company.\u000BExpertise in forming a team and familiar with the inner operation and the association about marketing and sales.\u000BLeader in a team to research the programs that face up to markets and modulate the price in a flexible and timely manner.\u000BKeep a good communication with the main media and Public relations firm.\u000BFluent in English. Career Objective I can start: within 1 week Employment Type: Full-time Desired Industry: Architectural Services/Building Materials/Construction，Real Estate Desired Location: Shanghai Desired Salary: Monthly Salary 10,000-14,999 Desired Position: Marketing / BD Manager，Business Manager，Key Account Management Current Situation: I'm looking for jobs now Work Experience 2015/1--Present:K. F. Stone Design International Inc. Canada(<50 people) [ 1 year] Industry: Architectural Services/Building Materials/Construction Marketing Department Marketing Director 1.Set up the annual marketing plan and budget．\u000B2.Achieve the contract value target set by company.\u000B3.Develop and maintain the relationship with key accounts.\u000B4.Develop relationship with project management firms, design firms, LDI and other business partners.\u000B5.Attend marketing events to pull through business opportunities. 2014/6--2015/1:CCDI Group(5000-10000 people) [ 7month] Industry: Architectural Services/Building Materials/Construction Management Department of HOPSCA Business Develop Manager 1.Develop new project and keep available the relationship with key clients;\u000B2.Analyze the marketing data of sub-productions,focus on watching the marketing trends;\u000B3.Supply the professional assistance to the operators;\u000B4.Classify the sub-productions and offer relative measures to different requirements of clients;\u000B5.Work with the team of marketing and sources of other regions and cities to promote the implementation of the project. 2013/8--2014/6:Shanghai Fangda Architecture Design Co.,Ltd(150-500 people) [ 10month] Industry: Architectural Services/Building Materials/Construction Marketing Department Regional Marketing Manager 1.Be responsible for business negotiations and settling contract;\u000B2.Maintain a sound and good relationship with the key clients and tract the potential projects;\u000B3.Check out the payment of the projects;\u000B4.Communicate with architect and Property Developers closely;\u000B5.Currently in charge of the promotion and publicity of the brands. 2012/2--2013/7:Shanghai Jintangwang Culture Development Co.,Ltd(150-500 people) [ 1 year and 5month] Industry: Real Estate Marketing Department Marketing Manager 1.Take charge of the market planning and development of Tourist Real Estate;\u000B2.Develop and manage the sales resources & set up company website and partnership with Association of Home Builders,etc;\u000B3.Work with President together to carry out the marketing planning. Education 2013/12--Present Tongji University Engineering Management Bachelor 2009/9--2012/6 Shanghai Second Polytechnic University Construction Economics and Management Associate Training 2014/2--2014/6: Professor Dai Jian of CEIBS EMBA Marketing Training Language Skills English(Very Good ): Listening&Speaking(Very Good)，Reading&Writing(Very Good) English Grade: CET 4 \f";
    }


}
