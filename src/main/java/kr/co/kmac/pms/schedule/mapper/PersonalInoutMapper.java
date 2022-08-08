package kr.co.kmac.pms.schedule.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.schedule.domain.PersonInout;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;

public interface PersonalInoutMapper {

	public int createPersonalInout(PersonInout personInout) throws DataAccessException;

	public PersonInout getPersonalInout(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getPersonalInoutByMonth(ScheduleSearchParam searchParam) throws DataAccessException;

	public int updatePersonalInout(PersonInout personInout) throws DataAccessException;

	public int removePersonalInout(ScheduleSearchParam searchParam) throws DataAccessException;

}
