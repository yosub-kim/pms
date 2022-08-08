package kr.co.kmac.pms.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.mapper.CompanyScheduleMapper;

@Service
@Transactional
public class CompanyScheduleService {

	@Autowired
	private CompanyScheduleMapper companyScheduleMapper;

	public int createCompanySchedule(ScheduleDetail scheduleInfo) {
		return companyScheduleMapper.createCompanySchedule(scheduleInfo);
	}

	public List<ScheduleDetail> getCompanyScheduleByMonth(ScheduleSearchParam searchParam) {
		return companyScheduleMapper.getCompanyScheduleByMonth(searchParam);
	}
	
	public List<ScheduleDetail> getPersonalScheduleByDate(ScheduleSearchParam searchParam) {
		return companyScheduleMapper.getCompanyScheduleByDate(searchParam);
	}


	public ScheduleDetail getCompanySchedule(ScheduleSearchParam searchParam) {
		return companyScheduleMapper.getCompanySchedule(searchParam);
	}

	public int updateCompanySchedule(ScheduleDetail scheduleInfo) {
		return companyScheduleMapper.updateCompanySchedule(scheduleInfo);
	}

	public int removeCompanySchedule(ScheduleSearchParam searchParam) {
		return companyScheduleMapper.removeCompanySchedule(searchParam);
	}

}
