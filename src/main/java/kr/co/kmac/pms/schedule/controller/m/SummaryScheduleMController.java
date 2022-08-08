package kr.co.kmac.pms.schedule.controller.m;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kmac.pms.common.util.DateUtils;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.schedule.domain.DateInfo;
import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.domain.summary.GroupForScheduleSummary;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummary;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummaryCount;
import kr.co.kmac.pms.schedule.domain.summary.UserForScheduleSummary;
import kr.co.kmac.pms.schedule.service.SummaryScheduleService;

@Controller
@RequestMapping("/schedule/m/summary/*")
public class SummaryScheduleMController {

	@Autowired
	private SummaryScheduleService summaryScheduleService;

	@GetMapping("/list")
	public String getScheduleSummary(ScheduleSearchParam searchParam, Model model) throws Exception {
		
		if (searchParam.getDate() == null || "".equals(searchParam.getDate())) {
			searchParam.setDate(DateUtils.getDateFormat("yyyy-MM-dd"));
			searchParam.setYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
			searchParam.setMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
			searchParam.setDay(Integer.parseInt(DateUtils.getDateFormat("dd")));
		} else {
			searchParam.setYear(Integer.parseInt(searchParam.getDate().substring(0, 4)));
			searchParam.setMonth(Integer.parseInt(searchParam.getDate().substring(5, 7)));
			searchParam.setDay(Integer.parseInt(searchParam.getDate().substring(8, 10)));
		}
		
		
		Calendar searchCalendar = DateUtils.getCalendar(searchParam.getDate(), "yyyy-MM-dd");
		searchCalendar.add(Calendar.DATE, 7);
		searchParam.setNextDate(DateUtils.getYyyymmdd(searchCalendar, "yyyy-MM-dd"));
		searchCalendar.add(Calendar.DATE, -14);
		searchParam.setPrevDate(DateUtils.getYyyymmdd(searchCalendar, "yyyy-MM-dd"));

		if (searchParam.getJobClass() == null) {
			searchParam.setJobClass("A");
		}

		List<ScheduleSummary> scheduleSummaryList = this.summaryScheduleService.getScheduleWeeklySummary(searchParam);

		ScheduleSummaryCount count = new ScheduleSummaryCount();
		for (ScheduleSummary scheduleSummary : scheduleSummaryList) {
			count.addCount(scheduleSummary);
		}
		searchParam.setSsn(SessionUtils.getSsn());
		searchParam.setJobClass(SessionUtils.getJobclass());
		searchParam.setEmail(SessionUtils.getEmail());
		
		searchParam.setDept(SessionUtils.getDept());
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("weekDays", getCurrentWeekList(searchParam));
		model.addAttribute("mainList", getScheduleConvert(scheduleSummaryList));
		model.addAttribute("count", count);

		return "/schedule/m/SummaryScheduleList";
	}

	/* -------------------------------------------------------------------- */

	private List<GroupForScheduleSummary> getScheduleConvert(List<ScheduleSummary> scheduleSummaryList) {

		GroupForScheduleSummary tgg = null;
		UserForScheduleSummary tuu = null;
		List<UserForScheduleSummary> tuuList = null;
		List<ScheduleSummary> tssList = null;
		List<GroupForScheduleSummary> tggList = new ArrayList<GroupForScheduleSummary>();

		String chkGroupId = "";
		String chkSsn = "";
		for (ScheduleSummary summary : scheduleSummaryList) {

			if (!chkGroupId.equals(summary.getLabelName())) {
				chkGroupId = summary.getLabelName();// flag data set
				tuuList = new ArrayList<UserForScheduleSummary>();// 목록 리스트 리셋

				tgg = new GroupForScheduleSummary();
				tgg.setGroupId(summary.getGroupId());
				tgg.setGroupName(summary.getGroupName());
				tgg.setLabelName(summary.getLabelName());
				tgg.setGroupSeq(summary.getGroupSeq());
				tggList.add(tgg);
			}

			if (!chkSsn.equals(summary.getSsn())) {
				chkSsn = summary.getSsn();// flag data set
				tssList = new ArrayList<ScheduleSummary>();// 개인 일정 리스트 리셋

				tuu = new UserForScheduleSummary();
				tuu.setGroupId(summary.getGroupId());
				tuu.setGroupName(summary.getGroupName());
				tuu.setLabelName(summary.getLabelName());
				tuu.setSsn(summary.getSsn());
				tuu.setUserName(summary.getUserName());
				tuu.setPosName(summary.getPosName());
				tuu.setScheduleSummaryList(tssList);

				tuuList.add(tuu);
				tgg.setUserForScheduleSummaries(tuuList);
			}
			tssList.add(summary);
		}

		return tggList;
	}

	private List<DateInfo> getCurrentWeekList(ScheduleSearchParam searchParam) throws Exception {
		List<DateInfo> weekDay = new ArrayList<DateInfo>();
		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(searchParam);

		String[] dateStrInWeek = DateUtils.getDaysOfWeek(searchParam.getDate(), "yyyy-MM-dd");
		for (String dateStr : dateStrInWeek) {
			Calendar c = DateUtils.getCalendar(dateStr, "yyyyMMdd");
			DateInfo dateInfo = new DateInfo();
			dateInfo.setYear(c.get(Calendar.YEAR));
			dateInfo.setMonth(c.get(Calendar.MONTH) + 1);
			dateInfo.setDay(c.get(Calendar.DATE));
			dateInfo.setDayOfTheWeek(DateUtils.getDayOfWeekToENGShort(c.get(Calendar.DAY_OF_WEEK)));
			dateInfo.setDayOfTheWeekKor(DateUtils.getDayOfWeekToKorShort(c.get(Calendar.DAY_OF_WEEK)));
			dateInfo.setToday(DateUtils.getYyyymmdd(c).equals(DateUtils.getNowDate()));
			dateInfo.setHoliday(hMap.get(new Integer(c.get(Calendar.DATE))) != null);
			dateInfo.setHolidayName(hMap.get(new Integer(c.get(Calendar.DATE))) != null
					? hMap.get(new Integer(new Integer(c.get(Calendar.DATE)))).get(0).getHName()
					: null);
			weekDay.add(dateInfo);
		}

		return weekDay;
	}

}
