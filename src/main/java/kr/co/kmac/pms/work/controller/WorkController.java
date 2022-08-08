package kr.co.kmac.pms.work.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmac.pms.common.service.CommonService;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.work.service.WorkService;

@Controller
@RequestMapping("/work/*")
public class WorkController {

	@Autowired
	private WorkService workService;

	@Autowired
	private CommonService commonService;

	@GetMapping("/myWorkList")
	public String myWorkList(HttpServletRequest req, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SSN",SessionUtils.getSsn()) ;
		List<Map<String, Object>> Resultmap = workService.myWorkList(map);
		System.out.println("Resultmap ==>"+Resultmap);
		model.addAttribute("list",Resultmap);
		return "/work/myWorkList";
	}
	
	@GetMapping("/SanctiontPresentList")
	public String SanctiontPresentList(HttpServletRequest req, Model model,@RequestParam Map<String, Object> map) {
		map.put("SSN",SessionUtils.getSsn());
		map.put("userId",SessionUtils.getSsn());
		List<Map<String, Object>> ApprovalList = commonService.getApprovalType(map);
		
		System.out.println("map ====>"+map);

		String endDate = null;
		String startDate = null;
		endDate = (String)map.get("endDate");
		startDate = (String)map.get("startDate");
		Calendar cal = Calendar.getInstance();
		String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(endDate == null || endDate.equals("")) {
			endDate = sdf.format(cal.getTime());
			map.put("endDate",endDate);
		}
		cal.add(cal.YEAR, -1); //일년 전
		if(startDate == null || startDate.equals("")) {
			startDate = sdf.format(cal.getTime());
			map.put("startDate",startDate);
		}
		String getGeoupList = (String)map.get("getGeoupList");
		if(getGeoupList == null || getGeoupList.equals("")) {
			getGeoupList = "2022";
		}
		String pagingPage = (String)map.get("pagingPage");
		if(pagingPage == null || pagingPage.equals("")) {
			pagingPage = "1";
		}
		map.put("pagingPage", pagingPage);
		List<Map<String, Object>> Resultmap = workService.getSanctiontPresentList(map);
		
		Map<String, Object> TotalCnt = workService.getSanctiontPresentCount(map);
		System.out.println("endDate ==>"+endDate);
		System.out.println("startDate ==>"+startDate);
		System.out.println("Resultmap ==>"+Resultmap);
		model.addAttribute("TotalCnt",TotalCnt);
		model.addAttribute("pagingPage",pagingPage);
		model.addAttribute("divCode",map.get("divCode"));
		model.addAttribute("ApprovalList",ApprovalList);
		model.addAttribute("list",Resultmap);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("getGeoupList",getGeoupList);
		model.addAttribute("approvalType",map.get("approvalType"));
		model.addAttribute("name",map.get("name"));
		model.addAttribute("projectName",map.get("projectName"));
		model.addAttribute("ing",map.get("ing"));
		return "/work/SanctiontPresentList";
	}
	
	

}