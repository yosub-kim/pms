package kr.co.kmac.pms.schedule.controller;

import java.nio.charset.Charset;
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
import kr.co.kmac.pms.schedule.domain.Holiday;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.service.CompanyScheduleService;
import kr.co.kmac.pms.schedule.service.SummaryScheduleService;

@Controller
@RequestMapping("/schedule/company/*")
public class CompanyScheduleController extends ScheduleSearchController {

	@Autowired
	private CompanyScheduleService companyScheduleService;

	@Autowired
	private SummaryScheduleService summaryScheduleService;

	@PostMapping(value = "/create")
	public ResponseEntity<?> createPersonalSchedule(@RequestBody ScheduleDetail detail) {

		HashMap<String, String> hmData = new HashMap<String, String>();

		try {
			String[] stime = detail.getStime().split(":");
			String[] etime = detail.getEtime().split(":");
			detail.setStartHour(stime[0]);
			detail.setStartMin(stime[1]);
			detail.setEndHour(etime[0]);
			detail.setEndMin(etime[1]);

			Calendar startCal = DateUtils.getCalendar(detail.getSdate(), "yyyy-MM-dd");
			detail.setYear(startCal.get(Calendar.YEAR));
			detail.setMonth(startCal.get(Calendar.MONTH) + 1);
			detail.setDay(startCal.get(Calendar.DAY_OF_MONTH));

			int res = companyScheduleService.createCompanySchedule(detail);

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

	@GetMapping("/list")
	public String getCompanyScheduleByMonth(ScheduleSearchParam searchParam, Model model) {
		if (searchParam.getYear() == 0) {
			searchParam.setYear(Integer.parseInt(DateUtils.getDateFormat("yyyy")));
		}
		if (searchParam.getMonth() == 0) {
			searchParam.setMonth(Integer.parseInt(DateUtils.getDateFormat("MM")));
		}

		List<ScheduleDetail> scheduleDetailList = this.companyScheduleService.getCompanyScheduleByMonth(searchParam);

		Map<Integer, List<ScheduleDetail>> sMap = getScheduleByMonth(scheduleDetailList);
		Map<Integer, List<Holiday>> hMap = this.summaryScheduleService.getHolidayMapByMonth(searchParam);
		
		searchParam.setDept(SessionUtils.getDept());
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("mainList", getScheduleMyMonth(sMap, hMap, searchParam));

		return "/schedule/CompanyScheduleList";
	}

	@GetMapping(value = "/detail")
	@ResponseBody
	public ResponseEntity<?> getScheduleDetail(@RequestParam("idx") int idx) {
		HashMap<String, ScheduleDetail> hmData = new HashMap<String, ScheduleDetail>();

		ScheduleSearchParam searchParam = new ScheduleSearchParam();
		searchParam.setIdx(idx);

		ScheduleDetail detail = companyScheduleService.getCompanySchedule(searchParam);
		hmData.put("detail", detail);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, headers, HttpStatus.OK);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<?> updatePersonalSchedule(@RequestBody ScheduleDetail detail) {

		HashMap<String, String> hmData = new HashMap<String, String>();

		try {
			String[] stime = detail.getStime().split(":");
			String[] etime = detail.getEtime().split(":");
			detail.setStartHour(stime[0]);
			detail.setStartMin(stime[1]);
			detail.setEndHour(etime[0]);
			detail.setEndMin(etime[1]);

			Calendar startCal = DateUtils.getCalendar(detail.getSdate(), "yyyy-MM-dd");
			detail.setYear(startCal.get(Calendar.YEAR));
			detail.setMonth(startCal.get(Calendar.MONTH) + 1);
			detail.setDay(startCal.get(Calendar.DAY_OF_MONTH));

			int res = companyScheduleService.updateCompanySchedule(detail);

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

		ScheduleSearchParam searchParam = new ScheduleSearchParam();
		searchParam.setIdx(idx);

		HashMap<String, String> hmData = new HashMap<String, String>();

		try {
			int res = companyScheduleService.removeCompanySchedule(searchParam);

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
