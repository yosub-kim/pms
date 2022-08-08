package kr.co.kmac.pms.schedule.controller.m;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmac.pms.common.util.DateUtils;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.service.ExpertpoolSearchService;
import kr.co.kmac.pms.schedule.controller.ScheduleSearchController;
import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummaryCount;
import kr.co.kmac.pms.schedule.service.PersonalScheduleService;
import kr.co.kmac.pms.schedule.service.SummaryScheduleService;

@Controller
@RequestMapping("/schedule/m/personal/*")
public class PersonalScheduleMController extends ScheduleSearchController {

	@Autowired
	private PersonalScheduleService personalScheduleService;

	@Autowired
	private SummaryScheduleService summaryScheduleService;

	@Autowired
	private ExpertpoolSearchService expertpoolSearchService;

	@PostMapping(value = "/create")
	public ResponseEntity<?> createSchedule(@RequestBody ScheduleDetail detail) {

		HashMap<String, String> hmData = new HashMap<String, String>();

		try {
			detail.setSsn(SessionUtils.getSsn());
			String[] stime = detail.getStime().split(":");
			String[] etime = detail.getEtime().split(":");
			detail.setStartHour(stime[0]);
			detail.setStartMin(stime[1]);
			detail.setEndHour(etime[0]);
			detail.setEndMin(etime[1]);
			detail.setSecretYN(detail.getSecretYN() == null ? "N" : detail.getSecretYN());

			Calendar startCal = DateUtils.getCalendar(detail.getSdate(), "yyyy-MM-dd");
			detail.setYear(startCal.get(Calendar.YEAR));
			detail.setMonth(startCal.get(Calendar.MONTH) + 1);
			detail.setDay(startCal.get(Calendar.DAY_OF_MONTH));

			int res = 0;
			if (detail.getMultiYN() != null && detail.getMultiYN().equals("Y")) {
				int dateDiff = DateUtils.dateDiff(detail.getSdate(), detail.getEdate(), "DAY");
				List<ScheduleDetail> detailList = new ArrayList<ScheduleDetail>();
				String tmpDate = detail.getSdate();
				int temp = 0;
				for (int i = 0; i <= dateDiff; i++) {
					if (("Y").equals(detail.getHolidayYN())) {
						tmpDate = DateUtils.getOffsetDate(tmpDate, temp, "yyyy-MM-dd");
						int dayTemp = DateUtils.getDayOfWeek(tmpDate);
						if ((dayTemp == 7) || (dayTemp == 1)) {
							System.out.println("holiday exception");
						} else {
							ScheduleDetail t = detail.clone();
							detailList.add(t);
						}
						startCal.add(Calendar.DATE, 1);
						detail.setYear(startCal.get(Calendar.YEAR));
						detail.setMonth(startCal.get(Calendar.MONTH) + 1);
						detail.setDay(startCal.get(Calendar.DAY_OF_MONTH));

						temp = 1;
					} else {
						ScheduleDetail t = detail.clone();
						detailList.add(t);

						startCal.add(Calendar.DATE, 1);
						detail.setYear(startCal.get(Calendar.YEAR));
						detail.setMonth(startCal.get(Calendar.MONTH) + 1);
						detail.setDay(startCal.get(Calendar.DAY_OF_MONTH));
					}
				}
				res = personalScheduleService.createPersonalSchedule(detailList);
			} else {
				res = personalScheduleService.createPersonalSchedule(detail);
			}

			hmData.put("result", "SUCCESS");

			if (res < 1) {
				throw new Exception("## no insertion");
			}

			return new ResponseEntity<>(hmData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(hmData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/new")
	public String goCreateSchedule(@RequestParam(value = "date", required = false, defaultValue = "0") String date,
			@RequestParam(value = "idx", required = false, defaultValue = "0") long idx,
			@RequestParam(value = "type", required = false) String type, Model model) {
		ScheduleDetail detail = null;
		if (date.equals("0")) {
			detail = ScheduleDetail.builder()
					.year(Integer.parseInt(DateUtils.getDateFormat("yyyy")))
					.month(Integer.parseInt(DateUtils.getDateFormat("MM")))
					.day(Integer.parseInt(DateUtils.getDateFormat("dd"))).build();
		} else {
			detail = ScheduleDetail.builder()
					.year(Integer.parseInt(date.substring(0, 4)))
					.month(Integer.parseInt(date.substring(4, 6)))
					.day(Integer.parseInt(date.substring(6, 8))).build();
		}
		if (idx > 0) {
			ScheduleSearchParam searchParam = new ScheduleSearchParam();
			searchParam.setIdx(idx);
			searchParam.setType(type);
			searchParam.setReadonly(false);
			searchParam.setSsn(SessionUtils.getSsn());
			detail = personalScheduleService.getPersonalSchedule(searchParam);
		}
		ScheduleSearchParam searchParam2 = new ScheduleSearchParam();
		searchParam2.setSsn(SessionUtils.getSsn());
		searchParam2.setJobClass(SessionUtils.getJobclass());
		searchParam2.setEmail(SessionUtils.getEmail());
		
		model.addAttribute("searchParam", searchParam2);
		model.addAttribute("detail", detail);

		return "/schedule/m/PersonalScheduleInsert";
	}

	@GetMapping("/list")
	public String getPersonalScheduleByMonth(ScheduleSearchParam searchParam, Model model) {
		if (searchParam.getYear() == 0) {
			searchParam.setYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
		}
		if (searchParam.getMonth() == 0) {
			searchParam.setMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
		}

		if (("undefined").equals(searchParam.getKeyword())) {
			searchParam.setKeyword("");
		} else {
			searchParam.setKeyword(searchParam.getKeyword());
		}

		searchParam.setSsn(SessionUtils.getSsn());
		searchParam.setJobClass(SessionUtils.getJobclass());
		searchParam.setEmail(SessionUtils.getEmail());
		searchParam.setReadonly(false);

		List<ScheduleDetail> scheduleDetailList = this.personalScheduleService.getPersonalScheduleByMonth(searchParam);
		ScheduleSummaryCount count = getScheduleCountByMonth(scheduleDetailList);
		Map<Integer, List<ScheduleDetail>> sMap = getScheduleByMonth(scheduleDetailList);
		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(searchParam);

		model.addAttribute("searchParam", searchParam);
		model.addAttribute("count", count);
		model.addAttribute("mainList", getScheduleMyMonth(sMap, hMap, searchParam));
		return "/schedule/m/PersonalScheduleList";
	}

	@GetMapping("/search/list")
	public String getPersonalScheduleSearchByMonth(ScheduleSearchParam searchParam, Model model) {

		if (searchParam.getStartYear() == 0) {
			searchParam.setStartYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
		}
		if (searchParam.getStartMonth() == 0) {
			searchParam.setStartMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
		}
		if (searchParam.getEndYear() == 0) {
			searchParam.setEndYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
		}
		if (searchParam.getEndMonth() == 0) {
			searchParam.setEndMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
		}
		if (("undefined").equals(searchParam.getKeyword())) {
			searchParam.setKeyword("");
		} else {
			searchParam.setKeyword(searchParam.getKeyword());
		}
		if (searchParam.getYear() == 0) {
			searchParam.setYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
		}
		if (searchParam.getMonth() == 0) {
			searchParam.setMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
		}

		searchParam.setSsn(SessionUtils.getSsn());
		searchParam.setJobClass(SessionUtils.getJobclass());
		List<ScheduleDetail> scheduleSearchDetailList = this.personalScheduleService
				.getPersonalScheduleSearchByMonth(searchParam);

		model.addAttribute("searchParam", searchParam);
		model.addAttribute("mainList", scheduleSearchDetailList);
		return "/schedule/PersonalScheduleSearchList";
	}

	@GetMapping("/listview")
	public String getPersonalScheduleByMonth(@RequestParam("year") String year, @RequestParam("month") String month,
			@RequestParam("ssn") String ssn, String keyword, Model model) {
		ScheduleSearchParam searchParam = new ScheduleSearchParam();
		searchParam.setYear(Integer.parseInt(year));
		searchParam.setMonth(Integer.parseInt(month));
		searchParam.setKeyword(keyword);
		searchParam.setSsn(ssn);
		searchParam.setSecretYN("N");
		searchParam.setReadonly(true);

		if (("undefined").equals(searchParam.getKeyword())) {
			searchParam.setKeyword("");
		} else {
			searchParam.setKeyword(searchParam.getKeyword());
		}

		List<ScheduleDetail> scheduleDetailList = this.personalScheduleService.getPersonalScheduleByMonth(searchParam);
		ScheduleSummaryCount count = getScheduleCountByMonth(scheduleDetailList);
		Map<Integer, List<ScheduleDetail>> sMap = getScheduleByMonth(scheduleDetailList);
		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(searchParam);

		model.addAttribute("searchParam", searchParam);
		model.addAttribute("count", count);
		model.addAttribute("mainList", getScheduleMyMonth(sMap, hMap, searchParam));

		return "/schedule/PersonalScheduleList";
	}

	@GetMapping(value = "/detail")
	@ResponseBody
	public ResponseEntity<?> getScheduleDetail(@RequestParam("idx") long idx, @RequestParam("type") String type,
			boolean readonly, String ssn) {
		HashMap<String, ScheduleDetail> hmData = new HashMap<String, ScheduleDetail>();

		ScheduleSearchParam searchParam = new ScheduleSearchParam();
		searchParam.setIdx(idx);
		searchParam.setType(type);
		searchParam.setReadonly(readonly);
		if (searchParam.isReadonly()) {
			searchParam.setSsn(ssn);
		} else {
			searchParam.setSsn(SessionUtils.getSsn());
		}

		ScheduleDetail detail = personalScheduleService.getPersonalSchedule(searchParam);
		hmData.put("detail", detail);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, headers, HttpStatus.OK);
	}

	@GetMapping(value = "/date")
	@ResponseBody
	public ResponseEntity<?> ScheduleDetailByDate(@RequestParam("date") String date, @RequestParam("ssn") String ssn)
			throws Exception {

		HashMap hmData = new HashMap();

		ScheduleSearchParam searchParam = new ScheduleSearchParam();
		Calendar dateCal = DateUtils.getCalendar(date, "yyyyMMdd");
		searchParam.setYear(dateCal.get(Calendar.YEAR));
		searchParam.setMonth(dateCal.get(Calendar.MONTH) + 1);
		searchParam.setDay(dateCal.get(Calendar.DAY_OF_MONTH));
		searchParam.setSsn(ssn);

		List<ScheduleDetail> mainList = personalScheduleService.getPersonalScheduleByDate(searchParam);

		HashMap<String, Integer> typeMap = new HashMap<>();
		for (ScheduleDetail scheduleDetail : mainList) {
			if (typeMap.containsKey(scheduleDetail.getIconCodeShort().replaceAll("-", ""))) {
				int cnt = typeMap.get(scheduleDetail.getIconCodeShort().replaceAll("-", "")) + 1;
				typeMap.replace(scheduleDetail.getIconCodeShort().replaceAll("-", ""), new Integer(cnt));
			} else {
				typeMap.put(scheduleDetail.getIconCodeShort().replaceAll("-", ""), new Integer(1));
			}
		}

		hmData.put("mainList", mainList);
		hmData.put("typeMap", typeMap);
		hmData.put("userInfo", expertpoolSearchService.getExpertPoolSimpleInfo(ssn));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, headers, HttpStatus.OK);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<?> updateSchedule(@RequestBody ScheduleDetail detail) {
		HashMap<String, String> hmData = new HashMap<String, String>();

		try {
			detail.setSsn(SessionUtils.getSsn());
			String[] stime = detail.getStime().split(":");
			String[] etime = detail.getEtime().split(":");
			detail.setStartHour(stime[0]);
			detail.setStartMin(stime[1]);
			detail.setEndHour(etime[0]);
			detail.setEndMin(etime[1]);
			detail.setSecretYN(detail.getSecretYN() == null ? "N" : detail.getSecretYN());

			Calendar startCal = DateUtils.getCalendar(detail.getSdate(), "yyyy-MM-dd");
			detail.setYear(startCal.get(Calendar.YEAR));
			detail.setMonth(startCal.get(Calendar.MONTH) + 1);
			detail.setDay(startCal.get(Calendar.DAY_OF_MONTH));

			int res = personalScheduleService.updatePersonalSchedule(detail);

			hmData.put("result", "SUCCESS");

			if (res < 1) {
				throw new Exception("## no update");
			}

			return new ResponseEntity<>(hmData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(hmData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> deleteSchedule(@RequestParam("idx") int idx) {

		Map<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("idx",idx);

		HashMap<String, String> hmData = new HashMap<String, String>();

		try {
			int res = personalScheduleService.removePersonalSchedule(searchParam);

			hmData.put("result", "SUCCESS");

			if (res < 1) {
				throw new Exception("## no delete");
			}

			return new ResponseEntity<>(hmData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(hmData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
