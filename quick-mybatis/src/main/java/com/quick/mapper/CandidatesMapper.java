package com.quick.mapper;

import com.quick.entity.Candidates;
import com.quick.entity.CandidatesExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CandidatesMapper {
    int countByExample(CandidatesExample example);

    int deleteByExample(CandidatesExample example);

    int deleteByPrimaryKey(String id);

    int insert(Candidates record);

    int insertSelective(Candidates record);

    List<Candidates> selectByExampleWithBLOBs(CandidatesExample example);

    List<Candidates> selectByExample(CandidatesExample example);

    Candidates selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Candidates record, @Param("example") CandidatesExample example);

    int updateByExampleWithBLOBs(@Param("record") Candidates record, @Param("example") CandidatesExample example);

    int updateByExample(@Param("record") Candidates record, @Param("example") CandidatesExample example);

    int updateByPrimaryKeySelective(Candidates record);

    int updateByPrimaryKeyWithBLOBs(Candidates record);

    int updateByPrimaryKey(Candidates record);
}