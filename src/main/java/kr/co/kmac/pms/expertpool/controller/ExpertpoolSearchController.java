package kr.co.kmac.pms.expertpool.controller;

import java.nio.charset.Charset;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchEntity;
import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchParam;
import kr.co.kmac.pms.expertpool.service.ExpertpoolSearchService;

@Controller
@RequestMapping("/expertpool/*")
public class ExpertpoolSearchController {

	@Autowired
	private ExpertpoolSearchService expertpoolSearchService;

	@GetMapping("/searchList")
	public String getExpertpoolSearch(ExpertpoolSearchParam searchParam, Model model) {

		// 상임, RA 체크
		if(SessionUtils.getJobclass().equals("J") || SessionUtils.getJobclass().equals("N"))
			searchParam.setConsultantChk(SessionUtils.getJobclass());
		
		model.addAttribute("searchParam", expertpoolSearchService.setSearchMetaInfo(searchParam));
		model.addAttribute("mainList", expertpoolSearchService.getExpertpoolSearchMainList(searchParam));
		
		List<ExpertpoolSearchEntity> jobClassAgg = expertpoolSearchService.getExpertpoolSearchJobClassAgg(searchParam);
		if (searchParam.getJobClass() != null && searchParam.getJobClass().length > 0) {
			for (ExpertpoolSearchEntity entity : jobClassAgg) {
				for (int i = 0; i < searchParam.getJobClass().length; i++) {
					if (entity.getJobClass().equals(searchParam.getJobClass()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ExpertpoolSearchEntity> deptAgg = expertpoolSearchService.getExpertpoolSearchDeptAgg(searchParam);
		if (searchParam.getDept() != null && searchParam.getDept().length > 0) {
			for (ExpertpoolSearchEntity entity : deptAgg) {
				for (int i = 0; i < searchParam.getDept().length; i++) {
					if (entity.getDept().equals(searchParam.getDept()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ExpertpoolSearchEntity> restrictAgg = expertpoolSearchService.getExpertpoolSearchRestrictAgg(searchParam);
		if (searchParam.getRestrictUser() != null && searchParam.getRestrictUser().length > 0) {
			for (ExpertpoolSearchEntity entity : restrictAgg) {
				for (int i = 0; i < searchParam.getRestrictUser().length; i++) {
					if (entity.getRestrictUser().equals(searchParam.getRestrictUser()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}

		model.addAttribute("jobClassAgg", jobClassAgg);
		model.addAttribute("deptAgg", deptAgg);
		model.addAttribute("restrictAgg", restrictAgg);

		return "/expertpool/ExpertpoolSearchList";
	}
	
	@GetMapping("/restrict/searchList")
	public String getRestrictExpertpoolSearch(ExpertpoolSearchParam searchParam, Model model) {

		model.addAttribute("searchParam", expertpoolSearchService.setSearchMetaInfo(searchParam));

		model.addAttribute("mainList", expertpoolSearchService.getExpertpoolSearchMainList(searchParam));

		List<ExpertpoolSearchEntity> jobClassAgg = expertpoolSearchService.getExpertpoolSearchJobClassAgg(searchParam);
		if (searchParam.getJobClass() != null && searchParam.getJobClass().length > 0) {
			for (ExpertpoolSearchEntity entity : jobClassAgg) {
				for (int i = 0; i < searchParam.getJobClass().length; i++) {
					if (entity.getJobClass().equals(searchParam.getJobClass()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		
		List<ExpertpoolSearchEntity> restrictAgg = expertpoolSearchService.getExpertpoolSearchRestrictAgg(searchParam);
		if (searchParam.getRestrictUser() != null && searchParam.getRestrictUser().length > 0) {
			for (ExpertpoolSearchEntity entity : restrictAgg) {
				for (int i = 0; i < searchParam.getRestrictUser().length; i++) {
					if (entity.getRestrictUser().equals(searchParam.getRestrictUser()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}

		model.addAttribute("jobClassAgg", jobClassAgg);
		model.addAttribute("restrictAgg", restrictAgg);

		return "/expertpool/RestrictExpertpoolSearchList";
	}
	
	@GetMapping("/hr/searchList")
	public String getHRExpertPoolSearch(ExpertpoolSearchParam searchParam, Model model) {
		
		// 상근/상임/ra count
		searchParam.setConsultantChk("A");
		
		model.addAttribute("searchParam", expertpoolSearchService.setSearchMetaInfo(searchParam));
		model.addAttribute("mainList", expertpoolSearchService.getHRExpertpoolSearchMainList(searchParam));
		
		List<ExpertpoolSearchEntity> jobClassAgg = expertpoolSearchService.getHRExpertpoolSearchJobClassAgg(searchParam);
		if (searchParam.getJobClass() != null && searchParam.getJobClass().length > 0) {
			for (ExpertpoolSearchEntity entity : jobClassAgg) {
				for (int i = 0; i < searchParam.getJobClass().length; i++) {
					if (entity.getJobClass().equals(searchParam.getJobClass()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ExpertpoolSearchEntity> deptAgg = expertpoolSearchService.getHRExpertpoolSearchDeptAgg(searchParam);
		if (searchParam.getDept() != null && searchParam.getDept().length > 0) {
			for (ExpertpoolSearchEntity entity : deptAgg) {
				for (int i = 0; i < searchParam.getDept().length; i++) {
					if (entity.getDept().equals(searchParam.getDept()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		
		model.addAttribute("jobClassAgg", jobClassAgg);
		model.addAttribute("deptAgg", deptAgg);

		return "/expertpool/HRExpertpoolSearchList";
	}
	
	@PostMapping("/searchExpertPool")
	@ResponseBody
	public ResponseEntity<?> searchExpertPool(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		 Cookie[] cookies = req.getCookies();
		 for(int i = 0; i < cookies.length; i++) {
			 if(cookies[i].getName().equals("pms_User_Id")) {
				map.put("pms_User_Id",cookies[i].getValue());
			}
		}
		
		List<Map<String, Object>> Resultmap = expertpoolSearchService.searchExpertPool(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	

}
