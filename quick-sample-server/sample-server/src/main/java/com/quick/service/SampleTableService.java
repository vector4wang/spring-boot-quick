package com.quick.service;

import com.quick.entity.SampleTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vector4wang
 * @since 2023-10-17
 * @version ${cfg.version}
 */
public interface SampleTableService extends IService<SampleTable> {


    List<SampleTable> pageList(SampleTable sampleTable);

    boolean saveEntity(SampleTable sampleTable);

    void delete(String id);


}
