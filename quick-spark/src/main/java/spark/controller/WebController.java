package spark.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spark.service.WordCountService;
import spark.util.BaseResp;
import spark.util.ResultStatus;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/13
 * Time: 18:02
 * Description:
 */
@RestController
    @RequestMapping("/spark")
public class WebController {
    private Logger logger = Logger.getLogger(WebController.class);

    @Resource
    private WordCountService wordCountService;

    @RequestMapping("/wordCount")
    @ResponseBody
    public BaseResp<Map<String, Integer>> wordCount(){

        logger.info("start submit spark tast...");
        Map<String, Integer> counts = null;
        try {
            counts = wordCountService.run();
        } catch (FileNotFoundException e) {
            return new BaseResp<>(ResultStatus.error_record_not_found);
        }

        return new BaseResp<Map<String, Integer>>(ResultStatus.SUCCESS,counts);
    }

    

    @RequestMapping("/hello")
    public BaseResp<String> pring(){
        return new BaseResp<String>(ResultStatus.SUCCESS,"hihi");
    }
}
