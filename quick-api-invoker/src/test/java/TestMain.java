import cn.hutool.http.HttpUtil;
import com.quick.api.invoker.AdapterContextFactor;
import com.quick.api.invoker.Main;
import com.quick.api.invoker.enums.HttpMethod;
import com.quick.api.invoker.executor.AbstractExecutorAdapter;
import com.quick.api.invoker.executor.bo.ExecutorConfig;
import com.quick.api.invoker.model.AdapterClassModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@Slf4j
public class TestMain {


    @Test
    public void getTest() throws ClassNotFoundException {
        AbstractExecutorAdapter executorAdapter = (AbstractExecutorAdapter) AdapterContextFactor.getAdapter("HttpExecutorAdapter", Class.forName("com.quick.api.invoker.executor.HttpExecutorAdapter"));
        String host = "https://getweather.market.alicloudapi.com";
        String path = "/lundear/weather1d";
        String targetUrl = host + path;

        ExecutorConfig config = new ExecutorConfig();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        String appcode = "8852ff1c52e84e7595d1425bc99813c2";
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, Object> querys = new HashMap<>();
        querys.put("areaCode", "370100");
        querys.put("areaCn", "西安");
        querys.put("ip", "ip");
        querys.put("lng", "lng");
        querys.put("lat", "lat");
        querys.put("needalarm", "needalarm");
        querys.put("need3hour", "need3hour");
        querys.put("needIndex", "needIndex");
        querys.put("needObserve", "needObserve");

        config.setHeader(headers);


        String resp = executorAdapter.execute(targetUrl, HttpMethod.GET, querys, config);
        System.out.println(resp);

    }


    @Test
    public void postTest() throws ClassNotFoundException {

        AdapterClassModel adapterClassModel = new AdapterClassModel();
        adapterClassModel.setClassFqn("com.quick.api.invoker.executor.HttpExecutorAdapter");
        adapterClassModel.setClassName("HttpExecutorAdapter");
        adapterClassModel.setLoadType(1);
        adapterClassModel.setAdapterType(1);

//        AbstractExecutorAdapter executorAdapter = (AbstractExecutorAdapter) AdapterContextFactor.getAdapter("HttpExecutorAdapter", Class.forName("com.quick.api.invoker.executor.HttpExecutorAdapter"));
        AbstractExecutorAdapter executorAdapter = AdapterContextFactor.getAdapter(adapterClassModel);


        String host = "https://jisuhuilv.market.alicloudapi.com";
        String path = "/exchange/convert";
        String targetUrl = host + path;
        String appcode = "8852ff1c52e84e7595d1425bc99813c2";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, Object> param = new HashMap<>();
        param.put("amount", "10");
        param.put("from", "CNY");
        param.put("to", "USD");

        targetUrl = HttpUtil.urlWithForm(targetUrl, param, Charset.defaultCharset(), true);

        ExecutorConfig config = new ExecutorConfig();
        config.setHeader(headers);
        String resp = executorAdapter.execute(targetUrl, HttpMethod.GET, null, config);

        log.info("postTest: {}", resp);
    }

    @Test
    public void localJavaTest() throws ClassNotFoundException {

    }

}
