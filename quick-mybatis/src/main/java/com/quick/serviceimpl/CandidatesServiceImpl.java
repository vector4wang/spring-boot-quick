package com.quick.serviceimpl;

import com.quick.entity.Candidates;
import com.quick.mapper.CandidatesMapper;
import com.quick.service.CandidatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxc on 2017/5/13.
 */
@Service
public class CandidatesServiceImpl implements CandidatesService {

    @Autowired
    private CandidatesMapper candidatesMapper;

    @Override
    public Candidates findById(String id) {
        return candidatesMapper.selectByPrimaryKey(id);
    }

}
