package kr.co.kmac.pms.schedule.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import kr.co.kmac.pms.common.util.DateUtils;
import kr.co.kmac.pms.common.util.GoogleApi;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.domain.summary.ScheduleSummaryCount;
import kr.co.kmac.pms.schedule.service.PersonalScheduleService;
import kr.co.kmac.pms.schedule.service.SummaryScheduleService;


@Controller
@RequestMapping("/schedule/personal/*")
public class PersonalScheduleController extends ScheduleSearchController {

	@Autowired
	private PersonalScheduleService personalScheduleService;

	@Autowired
	private SummaryScheduleService summaryScheduleService;
	
	@Autowired
	private GoogleApi googleapi;
	
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
					if(("Y").equals(detail.getHolidayYN())) {
						tmpDate = DateUtils.getOffsetDate(tmpDate, temp, "yyyy-MM-dd");
						int dayTemp = DateUtils.getDayOfWeek(tmpDate);
						if((dayTemp == 7) || (dayTemp == 1)) 
						{System.out.println("holiday exception");}
						else {
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
				//throw new Exception("## no insertion");
			}

			return new ResponseEntity<>(hmData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(hmData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/list")
	public String getPersonalScheduleByMonth(ScheduleSearchParam searchParam, Model model) throws IOException {
		if (searchParam.getYear() == 0) {
			searchParam.setYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
		}
		if (searchParam.getMonth() == 0) {
			searchParam.setMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
		}
		
		if (("undefined").equals(searchParam.getKeyword())) {
			searchParam.setKeyword("");
		}else {
			searchParam.setKeyword(searchParam.getKeyword());
		}
		
		searchParam.setSsn(SessionUtils.getSsn());
		searchParam.setJobClass(SessionUtils.getJobclass());
		List<ScheduleDetail> scheduleDetailList = this.personalScheduleService.getPersonalScheduleByMonth(searchParam);
		ScheduleSummaryCount count = getScheduleCountByMonth(scheduleDetailList);
		Map<Integer, List<ScheduleDetail>> sMap = getScheduleByMonth(scheduleDetailList);
		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(searchParam);

		model.addAttribute("searchParam", searchParam);
		model.addAttribute("count", count);
		model.addAttribute("mainList", getScheduleMyMonth(sMap, hMap, searchParam));
		return "/schedule/PersonalScheduleList";
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
		}else {
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
		List<ScheduleDetail> scheduleSearchDetailList = this.personalScheduleService.getPersonalScheduleSearchByMonth(searchParam);

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
		}else {
			searchParam.setKeyword(searchParam.getKeyword());
		}

		List<ScheduleDetail> scheduleDetailList = this.personalScheduleService.getPersonalScheduleByMonth(searchParam);
		System.out.println("scheduleDetailList ==>"+scheduleDetailList);
		ScheduleSummaryCount count = getScheduleCountByMonth(scheduleDetailList);
		Map<Integer, List<ScheduleDetail>> sMap = getScheduleByMonth(scheduleDetailList);
		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(searchParam);
		System.out.println("sMap ==>"+sMap);
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("count", count);
		model.addAttribute("mainList", getScheduleMyMonth(sMap, hMap, searchParam));
		return "/schedule/PersonalScheduleList";
	}

	@GetMapping(value = "/detail")
	@ResponseBody
	public ResponseEntity<?> getScheduleDetail(@RequestParam("idx") long idx, @RequestParam("type") String type,
			boolean readonly, String ssn) {
		HashMap<String, Object> hmData = new HashMap<String, Object>();

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
		hmData.put("ssn", searchParam.getSsn());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, headers, HttpStatus.OK);
	}

	@GetMapping(value = "/date")
	@ResponseBody
	public ResponseEntity<?> ScheduleDetailByDate(@RequestParam("date") String date, @RequestParam("ssn") String ssn,
			@RequestParam("keyword") String keyword) throws Exception {

		HashMap hmData = new HashMap();

		ScheduleSearchParam searchParam = new ScheduleSearchParam();
		Calendar dateCal = DateUtils.getCalendar(date, "yyyyMMdd");
		searchParam.setYear(dateCal.get(Calendar.YEAR));
		searchParam.setMonth(dateCal.get(Calendar.MONTH) + 1);
		searchParam.setDay(dateCal.get(Calendar.DAY_OF_MONTH));
		searchParam.setSsn(ssn);
		searchParam.setKeyword(keyword);

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
	@PostMapping("/searchShareList")
	@ResponseBody
	public ResponseEntity<?> searchShareList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		 Cookie[] cookies = req.getCookies();
		 for(int i = 0; i < cookies.length; i++) {
			 if(cookies[i].getName().equals("pms_User_Id")) {
				map.put("pms_User_Id",cookies[i].getValue());
			}
		}
		
		List<Map<String, Object>> Resultmap = personalScheduleService.searchShareList(map);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/google" , method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> google(HttpServletRequest req,@RequestParam(required = false) String state) throws IOException {
		com.google.api.services.calendar.Calendar service =  googleapi.getCalendarService();
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		
		System.out.println("state ==>"+state);
		
		Map<String,Object> Resultmap = new HashMap<>(); 
		/*
		System.out.println("map ==>"+map);
		System.out.println("year ==>"+map.get("year"));
		System.out.println("month ==>"+map.get("month"));
		map.put("SSN", SessionUtils.getSsn());
		
		List<Map<String, Object>> scheduleList = personalScheduleService.searchScheduleList(map);
		System.out.println("scheduleList ==>"+scheduleList);
		CalendarList calendarList = service.calendarList().list().setPageToken(null).execute();
			
		List<CalendarListEntry> Resultmap = calendarList.getItems();
		String calendarId = Resultmap.get(0).getId();
		for(int i=0;i<scheduleList.size();i++) {
			boolean flag = googleapi.checkEvent(calendar, service, calendarId, String.valueOf(scheduleList.get(i).get("idx")));
			System.out.println(flag);
			if(flag) {
				googleapi.updateEvent(calendar,service,calendarId,scheduleList.get(i));
			}else {
				googleapi.insertEvent(calendar,service,calendarId,scheduleList.get(i));
			}
		}
		*/
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/googleAsync2" , method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> googleAsync2(HttpServletRequest req,@RequestParam(required = false) String state,@RequestParam(required = false) String code) throws IOException {
		com.google.api.services.calendar.Calendar service = googleapi.getCalendarService();
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		
		System.out.println("state ==>"+state);
		Map<String,Object> map = new HashMap<>();
		String year = state.split("_")[0];
		String month = state.split("_")[1];
		map.put("year", year);
		map.put("month", month);
		//Map<String,Object> Resultmap = new HashMap<>(); 
		
		
		System.out.println("map ==>"+map);
		System.out.println("year ==>"+map.get("year"));
		System.out.println("month ==>"+map.get("month"));
		map.put("SSN", SessionUtils.getSsn());
		
		List<Map<String, Object>> scheduleList = personalScheduleService.searchScheduleList(map);
		System.out.println("scheduleList ==>"+scheduleList);
		CalendarList calendarList = service.calendarList().list().setPageToken(null).execute();
			
		List<CalendarListEntry> Resultmap = calendarList.getItems();
		String calendarId = Resultmap.get(0).getId();
		for(int i=0;i<scheduleList.size();i++) {
			boolean flag = googleapi.checkEvent(calendar, service, calendarId, String.valueOf(scheduleList.get(i).get("idx")));
			System.out.println(flag);
			if(flag) {
				googleapi.updateEvent(calendar,service,calendarId,scheduleList.get(i));
			}else {
				googleapi.insertEvent(calendar,service,calendarId,scheduleList.get(i));
			}
		}
		
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/googleAsync" , method = {RequestMethod.GET, RequestMethod.POST})
	public String googleAsync(HttpServletRequest req,@RequestParam(required = false) String state,@RequestParam(required = false) String code,Model model) throws IOException {
		String	access_token = googleapi.getAccessTokenJsonData(code);
		System.out.println("access_token ====>"+access_token);
		
		model.addAttribute("ssn", SessionUtils.getSsn());
		model.addAttribute("access_token", access_token);
		model.addAttribute("state", state);
		model.addAttribute("code", code);
		return "/schedule/googleAsync";
	}
	
	@PostMapping("/searchScheduleList")
	@ResponseBody
	public ResponseEntity<?> searchScheduleList(HttpServletRequest req,@RequestBody Map<String, Object> map) throws IOException {
		List<Map<String, Object>> Resultmap = personalScheduleService.searchScheduleList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	@PostMapping("/checkSchedule")
	@ResponseBody
	public ResponseEntity<?> checkSchedule(HttpServletRequest req,@RequestBody Map<String, Object> map) throws IOException {
		map.put("SSN", SessionUtils.getSsn());
		Map<String, Object> Resultmap = personalScheduleService.checkSchedule(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
}
