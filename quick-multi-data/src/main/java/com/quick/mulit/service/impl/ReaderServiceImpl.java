package com.quick.mulit.service.impl;

import com.quick.mulit.entity.secondary.Reader;
import com.quick.mulit.mapper.secondary.ReaderMapper;
import com.quick.mulit.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/8/22
 * Time: 19:15
 * Description:
 */
@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public Reader getReader(int i) {
        return readerMapper.selectByPrimaryKey(i);
    }
}
