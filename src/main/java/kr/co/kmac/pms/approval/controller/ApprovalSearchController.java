package kr.co.kmac.pms.approval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kmac.pms.approval.domain.ApprovalSearchParam;
import kr.co.kmac.pms.approval.service.ApprovalSearchService;
import kr.co.kmac.pms.common.util.DateUtils;

@Controller
@RequestMapping("/approval/*")
public class ApprovalSearchController {

	@Autowired
	private ApprovalSearchService approvalSearchService;

	@GetMapping("/searchList")
	public String getApprovalSearch(ApprovalSearchParam searchParam, Model model) {
		searchParam.setUserSsn("A000007");
		if (searchParam.getRegisterStartDate() == null || searchParam.equals("")) {
			searchParam.setRegisterStartDate(DateUtils.getDateFormat("yyyy")+"-01-01");
		}

		model.addAttribute("searchParam", approvalSearchService.setSearchMetaInfo(searchParam));
		model.addAttribute("mainList", approvalSearchService.getApprovalSearchMainList(searchParam));

		// 아래는 agg
		model.addAttribute("approvalTypeAgg", approvalSearchService.getApprovalSearchApprovalTypeAgg(searchParam));
		model.addAttribute("registerDeptAgg", approvalSearchService.getApprovalSearchRegisterDeptAgg(searchParam));
		model.addAttribute("stateAgg", approvalSearchService.getApprovalSearchStateAgg(searchParam));

		return "/approval/ApprovalSearchList";
	}

}
