package com.quick.service.impl;

import com.quick.entity.SampleTable;
import com.quick.mapper.SampleTableMapper;
import com.quick.service.SampleTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author vector4wang
 * @version ${cfg.version}
 * @since 2023-10-17
 */
@Service
public class SampleTableServiceImp extends ServiceImpl<SampleTableMapper, SampleTable> implements SampleTableService {

    @Override
    public boolean saveEntity(SampleTable sampleTable) {
        this.saveOrUpdate(sampleTable);
        return true;
    }

    @Override
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    public List<SampleTable> pageList(SampleTable sampleTable) {
        return this.list();
    }
}
