package kr.co.kmac.pms.schedule.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.kmac.pms.common.util.DateUtils;
import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.Schedule;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummaryCount;

public class ScheduleSearchController {

	protected List<List<Schedule>> getScheduleMyMonth(Map<Integer, List<ScheduleDetail>> sMap,
			Map<Integer, List<Holiday>> hMap, ScheduleSearchParam searchParam) {

		List<List<Schedule>> arrMonth = new ArrayList<List<Schedule>>();
		Schedule[] weeks = new Schedule[] { new Schedule(), new Schedule(), new Schedule(), new Schedule(),
				new Schedule(), new Schedule(), new Schedule() };
		Calendar c = new GregorianCalendar(searchParam.getYear(), searchParam.getMonth() - 1, 1);
		int temp = 0;
		
		for (int iDay = 1; iDay <= c.getActualMaximum(Calendar.DAY_OF_MONTH); iDay++) {
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setYear(c.get(Calendar.YEAR));
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setMonth(c.get(Calendar.MONTH) + 1);
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setDay(c.get(Calendar.DATE));
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setDayOfTheWeek(DateUtils.getDayOfWeekToENGShort(c.get(Calendar.DAY_OF_WEEK)));
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setDayOfTheWeekKor(DateUtils.getDayOfWeekToKorShort(c.get(Calendar.DAY_OF_WEEK)));
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setHoliday(hMap.get(new Integer(iDay)) != null);
			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setHolidayName(hMap.get(new Integer(iDay)) != null ? hMap.get(new Integer(iDay)).get(0).getHName() : null);
			// today check
			if(searchParam.getMonth() == Integer.parseInt(DateUtils.getDateFormat("MM"))) 
				weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setToday(c.get(Calendar.DATE) == Integer.parseInt(DateUtils.getDateFormat("dd")));
			else 
				weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setToday(c.get(Calendar.DATE) == 00);

			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setScheduleDetailList(sMap.get(new Integer(iDay)));

			int wom1 = c.get(Calendar.WEEK_OF_MONTH);
			c.set(Calendar.DATE, iDay + 1);
			if (wom1 != c.get(Calendar.WEEK_OF_MONTH)) {
				// 2월 check
				if(searchParam.getMonth() == 2 && temp > wom1) break;
				arrMonth.add(Arrays.asList(weeks));
				weeks = new Schedule[] { new Schedule(), new Schedule(), new Schedule(), new Schedule(), new Schedule(),
						new Schedule(), new Schedule() };
			}
			temp = wom1;
		}

		return arrMonth;
	}

	public ScheduleSummaryCount getScheduleCountByMonth(List<ScheduleDetail> scheduleDetailList) {
		ScheduleSummaryCount count = new ScheduleSummaryCount();

		for (ScheduleDetail scheduleDetail : scheduleDetailList) {
			if (scheduleDetail.getType() == null) {
				count.setT1(count.getT1() + 1);
			} else if (scheduleDetail.getType().equals("내부일정")) {
				count.setT1(count.getT1() + 1);
			} else if (scheduleDetail.getType().equals("외부일정")) {
				count.setT2(count.getT2() + 1);
			} else if (scheduleDetail.getType().equals("고객정보")) {
				count.setT3(count.getT3() + 1);
			} else if (scheduleDetail.getType().equals("프로젝트")) {
				count.setT4(count.getT4() + 1);
			} else if (scheduleDetail.getType().equals("교육참석")) {
				count.setT5(count.getT5() + 1);
			} else if (scheduleDetail.getType().equals("휴가")) {
				count.setT6(count.getT6() + 1);
			} else if (scheduleDetail.getType().equals("Up-day")) {
				count.setT7(count.getT7() + 1);
			} else if (scheduleDetail.getType().equals("개인휴무")) {
				count.setT8(count.getT8() + 1);
			}
		}
		return count;
	}

	public Map<Integer, List<ScheduleDetail>> getScheduleByMonth(List<ScheduleDetail> scheduleDetailList) {

		Map<Integer, List<ScheduleDetail>> resMap = new HashMap<Integer, List<ScheduleDetail>>();

		List<ScheduleDetail> tmpList = new ArrayList<ScheduleDetail>();
		int day = 0;
		for (ScheduleDetail sd : scheduleDetailList) {
			if (day == 0 || day == sd.getDay()) {
				tmpList.add(sd);
				day = sd.getDay();
			} else {
				resMap.put(new Integer(day), tmpList);
				tmpList = new ArrayList<ScheduleDetail>();
				tmpList.add(sd);
				day = sd.getDay();
			}
		}
		resMap.put(new Integer(day), tmpList);

		return resMap;
	}

}
