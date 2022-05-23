package com.quick.crypt.test.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {
	/**
	 * 主键查询
	 * 
	 * @param key
	 * @return
	 */
	T selectByKey(Object key);

	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return
	 */
	int save(T entity) throws MessageException;

	/**
	 * 主键删除
	 * 
	 * @param key
	 * @return
	 */
	int delete(Object key) throws MessageException;

	/**
	 * 条件删除
	 *
	 * @param example
	 * @return
	 */
	int deleteByExample(BeanCriteria example) throws MessageException;

	/**
	 * 更新所有字段
	 * 
	 * @param entity
	 * @return
	 */
	int updateAll(T entity) throws MessageException;

	/**
	 * 更新选中字段
	 * 
	 * @param entity
	 * @return
	 */
	int updateNotNull(T entity) throws MessageException;

	/**
	 * 条件查询
	 * 
	 * @param example
	 * @return
	 */
	List<T> selectByExample(BeanCriteria example);

	/**
	 * 分页查询，带条件
	 * 
	 * @param page
	 * @param example
	 * @return
	 */
	PageInfo<T> selectByPage(PageInfo<T> page, BeanCriteria example);

	/**
	 * 分页查询，不带条件
	 * 
	 * @param page
	 * @return
	 */
	PageInfo<T> selectByPage(PageInfo<T> page);

	int update(T entity) throws MessageException;

	List<T> getByProperty(String property, Object value,Class<T> clazz);
	/**
	 * @Description: 条件更新
	 * @Author: 01397368-hq
	 * @Date: 2020/11/25 16:30
	 */
	int updateByExampleSelective(T record, Object example);

}
