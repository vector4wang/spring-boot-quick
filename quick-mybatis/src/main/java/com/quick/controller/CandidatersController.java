package com.quick.controller;

import com.quick.entity.Candidates;
import com.quick.service.CandidatesService;
import com.quick.utils.BaseResp;
import com.quick.utils.ResultStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangxc on 2017/5/13.
 */
@RestController
@RequestMapping("/api")
public class CandidatersController {

    @Resource
    private CandidatesService candidatesService;

    @RequestMapping("/candidate/{id}")
    public BaseResp<Candidates> getById(@PathVariable("id")String id){
        return new BaseResp<>(ResultStatus.SUCCESS,candidatesService.findById(id));
    }

}
