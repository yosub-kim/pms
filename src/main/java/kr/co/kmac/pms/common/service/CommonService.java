package kr.co.kmac.pms.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.common.mapper.CommonMapper;
import kr.co.kmac.pms.project.domain.ProjectSearchEntity;
import kr.co.kmac.pms.project.domain.ProjectSearchParam;
import kr.co.kmac.pms.project.mapper.ProjectSearchMapper;

@Service
@Transactional(readOnly = true)
public class CommonService {
	@Autowired
	private CommonMapper commonMapper;
	
	public Map<String, Object> getUserInfo(Map<String, Object> map) {
		return this.commonMapper.getUserInfo(map);
	}
	
	public List<Map<String, Object>> getApprovalType(Map<String, Object> map) {
		return this.commonMapper.getApprovalType(map);
	}
	
	public List<Map<String, Object>> getGroupList(Map<String, Object> map) {
		return this.commonMapper.getGroupList(map);
	}
	
	public List<Map<String, Object>> getCodeList(Map<String, Object> map) {
		return this.commonMapper.getCodeList(map);
	}
}
