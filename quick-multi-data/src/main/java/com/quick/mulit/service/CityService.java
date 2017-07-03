package com.quick.mulit.service;

import com.quick.mulit.datasource.TargetDataSource;
import com.quick.mulit.entity.City;
import com.quick.mulit.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Service
public class CityService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // MyBatis的Mapper方法定义接口
    @Autowired
    private CityMapper cityMapper;

    @TargetDataSource(name="ds2")
    public List<City> likeName(String name){
        return cityMapper.likeName(name);
    }

    public List<City> likeNameByDefaultDataSource(String name){
        return cityMapper.likeName(name);
    }

    /**
     * @Author: wangxc
     * @Description:
     * @param
     * @return: java.util.List<com.quick.mulit.entity.City>
     * @Date: 下午 10:54 2017/6/21 0021
     */
    public List<City> getList(){

        String sql = "SELECT ID, `Name`, CountryCode, District, Population , InsertDate FROM city";
        return (List<City>) jdbcTemplate.query(sql, new RowMapper<City>(){

            @Override
            public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                City city = new City();
                city.setCountryCode(rs.getString("CountryCode"));
                city.setDistrict(rs.getString("District"));
                city.setInsertDate(rs.getTimestamp("InsertDate"));
                city.setName(rs.getString("Name"));
                city.setPopulation(rs.getInt("Population"));
                city.setId(rs.getInt("ID"));
                return city;
            }

        });
    }


    @TargetDataSource(name="ds2")
    public List<City> getListByDs2(){
        String sql = "SELECT ID,NAME,SCORE_SUM,SCORE_AVG, AGE   FROM STUDENT";
        return (List<City>) jdbcTemplate.query(sql, new RowMapper<City>(){

            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student stu = new Student();
                stu.setId(rs.getInt("ID"));
                stu.setAge(rs.getInt("AGE"));
                stu.setName(rs.getString("NAME"));
                stu.setSumScore(rs.getString("SCORE_SUM"));
                stu.setAvgScore(rs.getString("SCORE_AVG"));
                return stu;
            }

        });

}
