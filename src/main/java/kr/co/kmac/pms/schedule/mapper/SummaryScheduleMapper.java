package kr.co.kmac.pms.schedule.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummary;

public interface SummaryScheduleMapper {

	public List<ScheduleSummary> getScheduleMonthlySummary(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleSummary> getScheduleWeeklySummary(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<Holiday> getHolidayByMonth(ScheduleSearchParam searchParam) throws DataAccessException;

}
