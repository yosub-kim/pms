package kr.co.kmac.pms.support.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.support.mapper.conferenceMapper;

@Service
@Transactional
public class conferenceService {

	@Autowired
	private conferenceMapper conferencemapper;


	public List<Map<String, Object>> searchConferenceList(Map<String, Object> map) {
		return conferencemapper.searchConferenceList(map);
	}
	
	public List<Map<String, Object>> getConferenceYearList() {
		return conferencemapper.getConferenceYearList();
	}
	
	public int saveConferenceList(List<Map<String, Object>> map) {
		int result = 0;
		
		for(int i=0;i<map.size();i++) {
			String CRUD = (String)map.get(i).get("CRUD");
			switch (CRUD) {
			case "C":
				result = this.conferencemapper.insertConferenceList(map.get(i));
				break;
			case "U":
				result = this.conferencemapper.updateConferenceList(map.get(i));
				break;
			case "D":
				result = this.conferencemapper.deleteConferenceList(map.get(i));
				break;
			}	
		}
		return result;
	}
	
	
	public List<Map<String, Object>> searchConferenceDetailList(Map<String, Object> map) {
		return conferencemapper.searchConferenceDetailList(map);
	}
	
	public int saveConferenceDetailList(List<Map<String, Object>> map) {
		int result = 0;
		
		for(int i=0;i<map.size();i++) {
			String CRUD = (String)map.get(i).get("CRUD");
			switch (CRUD) {
			case "C":
				map.get(i).put("SSN", SessionUtils.getSsn());
				result = this.conferencemapper.insertConferenceDetailList(map.get(i));
				break;
			case "U":
				map.get(i).put("SSN", SessionUtils.getSsn());
				result = this.conferencemapper.updateConferenceDetailList(map.get(i));
				break;
			case "D":
				result = this.conferencemapper.deleteConferenceDetailList(map.get(i));
				break;
			}	
		}
		return result;
	}
	
	public List<Map<String, Object>> conferenctExpect(Map<String, Object> map) {
		return conferencemapper.conferenctExpect(map);
	}
}
