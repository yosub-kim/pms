package kr.co.kmac.pms.project.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.project.domain.ProjectSearchEntity;
import kr.co.kmac.pms.project.domain.ProjectSearchParam;

public interface ProjectSearchMapper {

	public int getTotalNumberOfEntries(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchMainList(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchBizTypeAgg(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchStateAgg(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchDivAgg(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchDeptAgg(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchCustomerAgg(ProjectSearchParam projectSearchParam) throws DataAccessException;

	public List<ProjectSearchEntity> getProjectSearchProjectTypeAgg(ProjectSearchParam projectSearchParam) throws DataAccessException;

}
