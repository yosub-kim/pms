package kr.co.kmac.pms.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.project.domain.ProjectSearchEntity;
import kr.co.kmac.pms.project.domain.ProjectSearchParam;
import kr.co.kmac.pms.project.mapper.ProjectSearchMapper;

@Service
@Transactional(readOnly = true)
public class ProjectSearchService {

	@Autowired
	private ProjectSearchMapper projectSearchMapper;

	public ProjectSearchParam setSearchMetaInfo(ProjectSearchParam searchParam) {
		searchParam.setTotalNumberOfEntries(this.projectSearchMapper.getTotalNumberOfEntries(searchParam));
		return searchParam;
	}

	public List<ProjectSearchEntity> getProjectSearchMainList(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchMainList(searchParam);
	}

	public List<ProjectSearchEntity> getProjectSearchBizTypeAgg(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchBizTypeAgg(searchParam);
	}

	public List<ProjectSearchEntity> getProjectSearchStateAgg(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchStateAgg(searchParam);
	}

	public List<ProjectSearchEntity> getProjectSearchDivAgg(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchDivAgg(searchParam);
	}

	public List<ProjectSearchEntity> getProjectSearchDeptAgg(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchDeptAgg(searchParam);
	}

	public List<ProjectSearchEntity> getProjectSearchCustomerAgg(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchCustomerAgg(searchParam);
	}

	public List<ProjectSearchEntity> getProjectSearchProjectTypeAgg(ProjectSearchParam searchParam) {
		return this.projectSearchMapper.getProjectSearchProjectTypeAgg(searchParam);
	}

}
