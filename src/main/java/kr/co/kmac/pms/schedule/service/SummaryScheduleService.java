package kr.co.kmac.pms.schedule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummary;
import kr.co.kmac.pms.schedule.mapper.SummaryScheduleMapper;
import kr.co.kmac.pms.schedule.repository.ScheduleTestRepository;

@Service
@Transactional
public class SummaryScheduleService {

	@Autowired
	private SummaryScheduleMapper scheduleSearchMapper;

	@Autowired
	private ScheduleTestRepository scheduleRepository;

	public List<ScheduleSummary> getScheduleMonthlySummary(ScheduleSearchParam searchParam) {
		return this.scheduleSearchMapper.getScheduleMonthlySummary(searchParam);
	}
	
	public List<ScheduleSummary> getScheduleWeeklySummary(ScheduleSearchParam searchParam) {
		return this.scheduleSearchMapper.getScheduleWeeklySummary(searchParam);
	}

	public List<Holiday> getHolidayListByMonth(ScheduleSearchParam searchParam) {
		return this.scheduleSearchMapper.getHolidayByMonth(searchParam);
	}

	public Map<Integer, List<Holiday>> getHolidayMapByMonth(ScheduleSearchParam searchParam) {
		List<Holiday> holidays = this.scheduleSearchMapper.getHolidayByMonth(searchParam);

		Map<Integer, List<Holiday>> resMap = new HashMap<Integer, List<Holiday>>();

		List<Holiday> tmpList = new ArrayList<Holiday>();
		int day = 0;
		for (Holiday sd : holidays) {
			if (day == 0 || day == sd.getHday()) {
				tmpList.add(sd);
				day = sd.getHday();
			} else {
				resMap.put(new Integer(day), tmpList);
				tmpList = new ArrayList<Holiday>();
				tmpList.add(sd);
				day = sd.getHday();
			}
		}
		resMap.put(new Integer(day), tmpList);

		return resMap;
	}

//	@Transactional(propagation = Propagation.REQUIRED)
//	public List<Holiday> test1() {
//		return scheduleRepository.getHolidayListByMonth("2021", "09");
//	}
//
//	@Transactional(propagation = Propagation.REQUIRED)
//	public int test2() {
//		return scheduleRepository.numOfCustomer("0601013");
//	}
}
