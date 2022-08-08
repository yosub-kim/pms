package kr.co.kmac.pms.support.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface conferenceMapper {

	public List<Map<String, Object>> searchConferenceList(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getConferenceYearList() throws DataAccessException;
	
	public List<Map<String, Object>> searchConferenceDetailList(Map<String, Object> map) throws DataAccessException;
	
	public int insertConferenceDetailList(Map<String, Object> map) throws DataAccessException;
	
	public int updateConferenceDetailList(Map<String, Object> map) throws DataAccessException;
	
	public int deleteConferenceDetailList(Map<String, Object> map) throws DataAccessException;
	
	public int insertConferenceList(Map<String, Object> map) throws DataAccessException;
	
	public int updateConferenceList(Map<String, Object> map) throws DataAccessException;
	
	public int deleteConferenceList(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> conferenctExpect(Map<String, Object> map) throws DataAccessException;
	
}
