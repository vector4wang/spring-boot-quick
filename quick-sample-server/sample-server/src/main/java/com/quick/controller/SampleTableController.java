package com.quick.controller;

import com.quick.entity.SampleTable;
import com.quick.service.SampleTableService;
import cn.hutool.core.lang.Assert;
import com.quick.common.base.rest.BaseResp;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.quick.common.base.rest.BaseController;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author vector4wang
 * @version ${cfg.version}
 * @since 2023-10-17
 */
@RestController
@RequestMapping(value = {"/sampleTable" },
        produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleTableController implements BaseController {

    @Autowired
    private SampleTableService sampleTableService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResp list(@RequestBody SampleTable page) {

        return success(sampleTableService.pageList(page));
    }


    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResp<SampleTable> detail(@PathVariable String id) {
        return success(sampleTableService.getById(id));
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResp<Boolean> save(@RequestBody SampleTable sampleTable) {
        sampleTableService.saveEntity(sampleTable);
        return success();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResp<Boolean> update(@PathVariable String id, @RequestBody SampleTable sampleTable) {
        Assert.notEmpty(id, "ID不能为空");
        sampleTableService.updateById(sampleTable);
        return success();
    }


    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResp<Boolean> delete(@PathVariable String id) {
        sampleTableService.delete(id);
        return success();
    }

}
