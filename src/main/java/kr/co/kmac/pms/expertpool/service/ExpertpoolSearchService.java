package kr.co.kmac.pms.expertpool.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchEntity;
import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchParam;
import kr.co.kmac.pms.expertpool.mapper.ExpertpoolSearchMapper;

@Service
@Transactional(readOnly = true)
public class ExpertpoolSearchService {

	@Autowired
	private ExpertpoolSearchMapper expertpoolSearchMapper;

	public ExpertpoolSearchParam setSearchMetaInfo(ExpertpoolSearchParam searchParam) {
		int totalNumberOfEntries = this.expertpoolSearchMapper.getTotalNumberOfEntries(searchParam);
		searchParam.setTotalNumberOfEntries(totalNumberOfEntries);
		searchParam.setTotalNumberOfPages(
				(int) Math.ceil((double) totalNumberOfEntries / (double) searchParam.getPagingNumberPer()));

		return searchParam;
	}

	public List<ExpertpoolSearchEntity> getExpertpoolSearchMainList(ExpertpoolSearchParam searchParam) {
		return this.expertpoolSearchMapper.getExpertpoolSearchMainList(searchParam);
	}

	public List<ExpertpoolSearchEntity> getExpertpoolSearchJobClassAgg(ExpertpoolSearchParam searchParam) {
		return this.expertpoolSearchMapper.getExpertpoolSearchJobClassAgg(searchParam);
	}

	public List<ExpertpoolSearchEntity> getExpertpoolSearchDeptAgg(ExpertpoolSearchParam searchParam) {
		return this.expertpoolSearchMapper.getExpertpoolSearchDeptAgg(searchParam);
	}

	public List<ExpertpoolSearchEntity> getExpertpoolSearchRestrictAgg(ExpertpoolSearchParam searchParam) {
		return this.expertpoolSearchMapper.getExpertpoolSearchRestrictAgg(searchParam);
	}
	
	public List<ExpertpoolSearchEntity> getHRExpertpoolSearchMainList(ExpertpoolSearchParam searchParam){
		return this.expertpoolSearchMapper.getHRExpertpoolSearchMainList(searchParam);
	}
	
	public List<ExpertpoolSearchEntity> getHRExpertpoolSearchJobClassAgg(ExpertpoolSearchParam searchParam) {
		return this.expertpoolSearchMapper.getHRExpertpoolSearchJobClassAgg(searchParam);
	}

	public List<ExpertpoolSearchEntity> getHRExpertpoolSearchDeptAgg(ExpertpoolSearchParam searchParam) {
		return this.expertpoolSearchMapper.getHRExpertpoolSearchDeptAgg(searchParam);
	}
	
	public HashMap<String, String> getExpertPoolSimpleInfo(String ssn) throws DataAccessException {
		return this.expertpoolSearchMapper.getExpertPoolSimpleInfo(ssn);
	}

	public List<Map<String, Object>> searchExpertPool(Map<String, Object> map) {
		return this.expertpoolSearchMapper.searchExpertPool(map);
	}
}
