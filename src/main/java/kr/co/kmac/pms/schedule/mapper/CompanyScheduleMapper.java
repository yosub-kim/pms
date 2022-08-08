package kr.co.kmac.pms.schedule.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;

public interface CompanyScheduleMapper {

	public int createCompanySchedule(ScheduleDetail scheduleInfo) throws DataAccessException;

	public ScheduleDetail getCompanySchedule(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getCompanyScheduleByDate(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getCompanyScheduleByMonth(ScheduleSearchParam searchParam) throws DataAccessException;

	public int updateCompanySchedule(ScheduleDetail scheduleInfo) throws DataAccessException;

	public int removeCompanySchedule(ScheduleSearchParam searchParam) throws DataAccessException;

}
