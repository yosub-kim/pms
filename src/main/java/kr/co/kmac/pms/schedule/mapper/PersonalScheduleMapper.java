package kr.co.kmac.pms.schedule.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;

public interface PersonalScheduleMapper {

	public int createPersonalSchedule(ScheduleDetail scheduleInfo) throws DataAccessException;

	public ScheduleDetail getPersonalSchedule(ScheduleSearchParam searchParam) throws DataAccessException;
	public ScheduleDetail getPersonalScheduleByProject(ScheduleSearchParam searchParam) throws DataAccessException;
	public ScheduleDetail getPersonalScheduleBycustomer_pickers(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getPersonalScheduleByDate(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getPersonalScheduleByMonth(ScheduleSearchParam searchParam) throws DataAccessException;
	
	public List<ScheduleDetail> getPersonalScheduleSearchByMonth(ScheduleSearchParam searchParam) throws DataAccessException;

	public int updatePersonalSchedule(ScheduleDetail scheduleInfo) throws DataAccessException;

	public int removePersonalSchedule(Map<String, Object> searchParam) throws DataAccessException;
	
	public Map<String, Object> searchScheduleIDX(Map<String, Object> map) throws DataAccessException;
	
	public int shareListDelete(Map<String, Object> map) throws DataAccessException;
	
	public int shareListInsert(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> searchShareList(Map<String, Object> map) throws DataAccessException;

	public Map<String, Object> searchOwnerInfo(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> searchScheduleList(Map<String, Object> map) throws DataAccessException;
	
	public Map<String, Object> checkSchedule(Map<String, Object> map) throws DataAccessException;
}
