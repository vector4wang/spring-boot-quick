package com.quick.service.impl;

import com.quick.entity.Sample;
import com.quick.mapper.SampleMapper;
import com.quick.service.CtKeyPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * api key配置池 服务实现类
 * </p>
 *
 * @author vector4wang
 * @since 2023-09-27
 */
@Service
public class CtKeyPoolServiceImp extends ServiceImpl<SampleMapper, Sample> implements CtKeyPoolService {

}
