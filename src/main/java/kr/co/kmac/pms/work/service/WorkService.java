package kr.co.kmac.pms.work.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.work.mapper.WorkMapper;

@Service
@Transactional
public class WorkService {
	
	@Autowired
	private WorkMapper workMapper;
	
	public List<Map<String, Object>> myWorkList(Map<String, Object> map) {
		return workMapper.myWorkList(map);
	}
	
	public List<Map<String, Object>> getSanctiontPresentList(Map<String, Object> map) {
		return workMapper.getSanctiontPresentList(map);
	}
	
	public Map<String, Object> getSanctiontPresentCount(Map<String, Object> map) {
		return workMapper.getSanctiontPresentCount(map);
	}
}
