package kr.co.kmac.pms.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kmac.pms.common.util.DateUtils;
import kr.co.kmac.pms.project.domain.ProjectSearchEntity;
import kr.co.kmac.pms.project.domain.ProjectSearchParam;
import kr.co.kmac.pms.project.service.ProjectSearchService;

@Controller
@RequestMapping("/project/*")
public class ProjectSearchController {

	@Autowired
	private ProjectSearchService projectSearchService;

	@GetMapping("/searchList")
	public String getProjectSearch(ProjectSearchParam searchParam, Model model) {

		if (searchParam.getRealStartDate() == null || searchParam.equals("")) {
			//searchParam.setRealStartDate(DateUtils.getDateFormat("yyyy") + "-01-01");
			searchParam.setRealStartDate("2021-01-01");
		}
		model.addAttribute("searchParam", projectSearchService.setSearchMetaInfo(searchParam));

		model.addAttribute("mainList", projectSearchService.getProjectSearchMainList(searchParam));

		List<ProjectSearchEntity> bizTypeAgg = projectSearchService.getProjectSearchBizTypeAgg(searchParam);
		if (searchParam.getBusinessTypeCode() != null && searchParam.getBusinessTypeCode().length > 0) {
			for (ProjectSearchEntity entity : bizTypeAgg) {
				for (int i = 0; i < searchParam.getBusinessTypeCode().length; i++) {
					if (entity.getBusinessTypeCode().equals(searchParam.getBusinessTypeCode()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ProjectSearchEntity> stateAgg = projectSearchService.getProjectSearchStateAgg(searchParam);
		if (searchParam.getProjectState() != null && searchParam.getProjectState().length > 0) {
			for (ProjectSearchEntity entity : stateAgg) {
				for (int i = 0; i < searchParam.getProjectState().length; i++) {
					if (entity.getProjectState().equals(searchParam.getProjectState()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ProjectSearchEntity> projectTypeAgg = projectSearchService.getProjectSearchProjectTypeAgg(searchParam);
		if (searchParam.getProjectTypeCode() != null && searchParam.getProjectTypeCode().length > 0) {
			for (ProjectSearchEntity entity : projectTypeAgg) {
				for (int i = 0; i < searchParam.getProjectTypeCode().length; i++) {
					if (entity.getProjectTypeCode().equals(searchParam.getProjectTypeCode()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ProjectSearchEntity> deptAgg = projectSearchService.getProjectSearchDeptAgg(searchParam);
		if (searchParam.getRunningDeptCode() != null && searchParam.getRunningDeptCode().length > 0) {
			for (ProjectSearchEntity entity : deptAgg) {
				for (int i = 0; i < searchParam.getRunningDeptCode().length; i++) {
					if (entity.getRunningDeptCode().equals(searchParam.getRunningDeptCode()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}

		model.addAttribute("bizTypeAgg", bizTypeAgg);
		model.addAttribute("stateAgg", stateAgg);
		model.addAttribute("projectTypeAgg", projectTypeAgg);
		model.addAttribute("runningDeptAgg", deptAgg);

		// model.addAttribute("runningDivAgg",
		// projectSearchService.getProjectSearchDivAgg(searchParam));
		// model.addAttribute("customerAgg",
		// projectSearchService.getProjectSearchCustomerAgg(searchParam));

		return "/project/ProjectSearchList";
	}

}
