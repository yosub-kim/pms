package kr.co.kmac.pms.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.kmac.pms.common.util.DateUtils;
import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.Schedule;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.service.CompanyScheduleService;
import kr.co.kmac.pms.schedule.service.SummaryScheduleService;

@SpringBootTest
class CompanyScheduleServiceTest {

	@Autowired
	private CompanyScheduleService companyScheduleService;

	@Autowired
	private SummaryScheduleService summaryScheduleService;

	// @Test
	void testgetCompanyScheduleByMonth() {
//		ScheduleSearchParam param = new ScheduleSearchParam();
//		param.setYear(2021);
//		param.setMonth(10);
//		param.setDay(1);
//		Map<Integer, List<ScheduleDetail>> map = companyScheduleService.getCompanyScheduleByMonth(param);
//
//		System.out.println(map);
	}

	@Test
	void testgetCompanyScheduleByMonth2() {
//		ScheduleSearchParam param = new ScheduleSearchParam();
//		param.setYear(2021);
//		param.setMonth(10);
//		param.setDay(1);
//
//		Map<Integer, List<ScheduleDetail>> sMap = this.companyScheduleService.getCompanyScheduleByMonth(param);
//		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(param);
//
//		ArrayList<List<Schedule>> arrMonth = new ArrayList<List<Schedule>>();
//		Schedule[] weeks = new Schedule[]{ new Schedule(), new Schedule(), new Schedule(), new Schedule(), new Schedule(),
//				new Schedule(), new Schedule() };
//
//		Calendar c = new GregorianCalendar(param.getYear(), param.getMonth() - 1, 1);
//		for (int iDay = 1; iDay <= c.getActualMaximum(Calendar.DAY_OF_MONTH); iDay++) {
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setYear(c.get(Calendar.YEAR));
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setMonth(c.get(Calendar.MONTH) + 1);
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setDay(c.get(Calendar.DATE));
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)]
//					.setDayOfTheWeek(DateUtils.getDayOfWeekToENGShort(c.get(Calendar.DAY_OF_WEEK)));
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)]
//					.setDayOfTheWeekKor(DateUtils.getDayOfWeekToKorShort(c.get(Calendar.DAY_OF_WEEK)));
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setHoliday(hMap.get(new Integer(iDay)) != null);
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setHolidayName(
//					hMap.get(new Integer(iDay)) != null ? hMap.get(new Integer(iDay)).get(0).getHName() : null);
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)]
//					.setToday(c.get(Calendar.DATE) == Integer.parseInt(DateUtils.getDateFormat("dd")));
//			weeks[(c.get(Calendar.DAY_OF_WEEK) - 1)].setScheduleDetailList(sMap.get(new Integer(iDay)));
//
//			int wom1 = c.get(Calendar.WEEK_OF_MONTH);
//
//			c.set(Calendar.DATE, iDay + 1);
//			if (wom1 != c.get(Calendar.WEEK_OF_MONTH)) {
//				arrMonth.add(Arrays.asList(weeks));
//				weeks = new Schedule[] { new Schedule(), new Schedule(), new Schedule(), new Schedule(), new Schedule(),
//						new Schedule(), new Schedule() };
//			}
//		}
//
//		System.out.println(arrMonth);
	}

}
