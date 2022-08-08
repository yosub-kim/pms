package kr.co.kmac.pms.common.mapper;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface CommonMapper {

	
	public Map<String, Object> getUserInfo(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> getApprovalType(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> getGroupList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> getCodeList(Map<String, Object> map) throws DataAccessException;
}
