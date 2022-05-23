package com.quick.crypt.test.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;


@Slf4j
public class BaseServiceImpl<T> implements BaseService<T> {


    @Autowired
    private BaseMapper<T> mapper;

    public BaseMapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(T entity) throws MessageException {
        return mapper.insert(entity);
    }

    @Override
    public int update(T entity) throws MessageException {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int delete(Object key) throws MessageException {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteByExample(BeanCriteria example) throws MessageException {
        return mapper.deleteByExample(example);
    }

    @Override
    public int updateAll(T entity) throws MessageException {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) throws MessageException {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(BeanCriteria example) {
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<T> selectByPage(PageInfo<T> page, BeanCriteria example) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<T> list = mapper.selectByExample(example);
        return new PageInfo<T>(list);
    }

    @Override
    public PageInfo<T> selectByPage(PageInfo<T> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<T> list = mapper.selectAll();
        return new PageInfo<T>(list);
    }

    @Override
    public List<T> getByProperty(String property, Object value, Class<T> clazz) {
        BeanCriteria beanCriteria = new BeanCriteria(clazz);
        BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
        if (property != null && !"".equals(property)) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(property, clazz);
                Method getter = pd.getReadMethod();
                if (getter != null) {
                    criteria.andEqualTo(property, value);
                } else {
                    return null;
                }
            } catch (IntrospectionException e) {
                log.warn(clazz.getName() + " has no property of " + property);
            }
        }
        return this.selectByExample(beanCriteria);
    }


    @Override
    public int updateByExampleSelective(T record, Object example) {
        return mapper.updateByExampleSelective(record, example);
    }

}
