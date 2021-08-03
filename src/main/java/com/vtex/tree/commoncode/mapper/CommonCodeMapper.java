package com.vtex.tree.commoncode.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Mapper
public interface CommonCodeMapper {

	int insertCommonCode(Map<String, String> param) throws Exception;

	int codeDuplicationCheck(String code);

	int getTotalCommonCode();

	List<Map<String, String>> selectCommonCodeList(Map<String, Object> param, RowBounds rowBounds);

	int updateCommonCode(Map<String, String> param);

	int insertDetailCode(Map<String, String> param) throws Exception;

	List<Map<String, Object>> selectDetailCode(Map<String, Object> param, RowBounds rowBounds);

	int deleteCommonCode(Map<String, String> param);

	int getTotalDetailCode(String code);

	Map<String, Object> selectOneDetailCode(String detailCode);

	int updateDetailCode(Map<String, String> param);

	Map<String, String> selectOneCommonCode(String code);

	int detailCodeDuplicatioCheck(String code);

	int deleteDetailCode(Map<String, String> param) throws Exception;

}
