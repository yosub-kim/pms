package kr.co.kmac.pms.expertpool.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchEntity;
import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchParam;

public interface ExpertpoolSearchMapper {

	public int getTotalNumberOfEntries(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;

	public List<ExpertpoolSearchEntity> getExpertpoolSearchMainList(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;
	
	public List<ExpertpoolSearchEntity> getExpertpoolSearchJobClassAgg(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;

	public List<ExpertpoolSearchEntity> getExpertpoolSearchDeptAgg(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;

	public List<ExpertpoolSearchEntity> getExpertpoolSearchRestrictAgg(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;

	public List<ExpertpoolSearchEntity> getHRExpertpoolSearchMainList(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;
	
	public List<ExpertpoolSearchEntity> getHRExpertpoolSearchJobClassAgg(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;

	public List<ExpertpoolSearchEntity> getHRExpertpoolSearchDeptAgg(ExpertpoolSearchParam expertpoolSearchParam) throws DataAccessException;

	public HashMap<String, String> getExpertPoolSimpleInfo(String ssn) throws DataAccessException;
	
	public List<Map<String, Object>> searchExpertPool(Map<String, Object> map) throws DataAccessException;

}
