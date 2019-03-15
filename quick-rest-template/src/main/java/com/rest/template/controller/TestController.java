package com.rest.template.controller;

import com.rest.template.model.TestDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 测试接口
 */
@RestController
public class TestController {

    /**
     * get方法测试
     *
     * @return
     */
    @GetMapping("testGet")
    public TestDTO testGet() {
        TestDTO TestDTO = new TestDTO();
        TestDTO.setId(1);
        TestDTO.setName("get");
        return TestDTO;
    }

    /**
     * post方法
     *
     * @return
     */
    @PostMapping("testPost")
    public TestDTO testPost(@RequestBody TestDTO testDTO) {
        testDTO.setName("from server " + testDTO.getName());
        return testDTO;
    }


    /**
     * post 方法传值
     *
     * @param id
     * @param name
     * @return
     */
    @PostMapping("testPostParam")
    public String testPostParam(@RequestParam("id") String id, @RequestParam("name") String name) {
        System.out.println("Post id:" + id);
        System.out.println("Post name:" + name);
        return "post succ";
    }


    /**
     * post 方法传值
     *
     * @param id
     * @param name
     * @return
     */
    @PutMapping("testPut")
    public String testPut(@RequestParam("id") String id, @RequestParam("name") String name) {
        System.out.println("put id:" + id);
        System.out.println("put name:" + name);
        return "del succ";
    }


    /**
     * del方法传值
     *
     * @param id
     * @return
     */
    @DeleteMapping("testDel")
    public String testDel(@RequestParam("id") String id) {
        System.out.println("del id:" + id);
        return "del succ";
    }

}