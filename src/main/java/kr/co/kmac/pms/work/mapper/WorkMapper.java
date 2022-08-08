package kr.co.kmac.pms.work.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface WorkMapper {

	
	public List<Map<String, Object>> myWorkList(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getSanctiontPresentList(Map<String, Object> map) throws DataAccessException;
	
	public Map<String, Object> getSanctiontPresentCount(Map<String, Object> map) throws DataAccessException;
}
