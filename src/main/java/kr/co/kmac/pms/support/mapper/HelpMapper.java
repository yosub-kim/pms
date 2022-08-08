package kr.co.kmac.pms.support.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HelpMapper {

	public int doSystemFaqQuestionWrite(Map<String, Object> map) throws DataAccessException;

	public int doSystemFaqRegist(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> getSystemFaqList(Map<String, Object> map) throws DataAccessException;

	public int doSystemFaqDelete(Map<String, Object> map) throws DataAccessException;

	public int doSystemFaqEdit(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> getSystemFaqFieldList(Map<String, Object> map) throws DataAccessException;

}
