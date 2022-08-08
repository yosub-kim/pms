package kr.co.kmac.pms.schedule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.kmac.pms.common.util.MailService;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.mapper.PersonalScheduleMapper;

@Service
@Transactional
public class PersonalScheduleService {

	@Autowired
	private PersonalScheduleMapper personalScheduleMapper;
	@Autowired
	private MailService mail;
	
	public String shareTitle = "";
	public String shareContent = "";
	public ArrayList shareEmail = new ArrayList();
	public ArrayList shareSSN = new ArrayList();

	
	public int createPersonalSchedule(ScheduleDetail scheduleDetail) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("gridData", scheduleDetail.getGridData());
		map.put("type", scheduleDetail.getType());
		map.put("content", scheduleDetail.getContent());
		map.put("place", scheduleDetail.getPlace());
		map.put("sdate", scheduleDetail.getSdate());
		map.put("stime", scheduleDetail.getStime());
		map.put("edate", scheduleDetail.getEdate());
		map.put("etime", scheduleDetail.getEtime());
		map.put("customerName", scheduleDetail.getCustomerName());
		int a = personalScheduleMapper.createPersonalSchedule(scheduleDetail);
		if(!scheduleDetail.getGridData().equals("")) {
			a = shareListSave(map,"C","N");
			shareContent += "<p style=\"font-size:15px;\">참석자 : ";
			for(int i=0;i<shareSSN.size();i++) {
				Map<String, Object> ssnMap = new HashMap<String, Object>();
				ssnMap.put("OWNERSSN", shareSSN.get(i));
				Map<String, Object> ownerInfo = personalScheduleMapper.searchOwnerInfo(ssnMap);
				shareContent += ownerInfo.get("name")+" ";
			}
			shareContent += "<p style=\"font-size:12px;font-weight:bold;margin-top:10px\">* 초대된 일정은 스케줄관리 - 개인일정에 표시됩니다.</p></div>";
			if(a > 0) {
				mail.sendMail(shareTitle,shareContent,shareEmail);
			}
		}
		
		return a;
	}

	public int createPersonalSchedule(List<ScheduleDetail> scheduleDetailList) throws Exception {
		int a = 0;
		int shareCheck = 0;
		for (ScheduleDetail scheduleDetail : scheduleDetailList) {
			a = personalScheduleMapper.createPersonalSchedule(scheduleDetail);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gridData", scheduleDetail.getGridData());
			map.put("type", scheduleDetail.getType());
			map.put("content", scheduleDetail.getContent());
			map.put("place", scheduleDetail.getPlace());
			map.put("sdate", scheduleDetail.getSdate());
			map.put("stime", scheduleDetail.getStime());
			map.put("edate", scheduleDetail.getEdate());
			map.put("etime", scheduleDetail.getEtime());
			map.put("customerName", scheduleDetail.getCustomerName());
			if(!scheduleDetail.getGridData().equals("")) {
				a = shareListSave(map,"C","Y");
				shareCheck = 1;
			}
		}
		if(a > 0 && shareCheck > 0) {
			shareContent += "<p style=\"font-size:15px;\">참석자 : ";
			for(int i=0;i<shareSSN.size();i++) {
				Map<String, Object> ssnMap = new HashMap<String, Object>();
				ssnMap.put("OWNERSSN", shareSSN.get(i));
				Map<String, Object> ownerInfo = personalScheduleMapper.searchOwnerInfo(ssnMap);
				shareContent += ownerInfo.get("name")+" ";
			}
			shareContent += "<p style=\"font-size:12px;font-weight:bold;margin-top:10px\">* 초대된 일정은 스케줄관리 - 개인일정에 표시됩니다.</p></div>";
			
			if(a > 0) {
				mail.sendMail(shareTitle,shareContent,shareEmail);
			}
		}
		return a;
	}

	public List<ScheduleDetail> getPersonalScheduleByMonth(ScheduleSearchParam searchParam) {
		return personalScheduleMapper.getPersonalScheduleByMonth(searchParam);
	}
	
	public List<ScheduleDetail> getPersonalScheduleSearchByMonth(ScheduleSearchParam searchParam){
		return personalScheduleMapper.getPersonalScheduleSearchByMonth(searchParam);
	}

	public List<ScheduleDetail> getPersonalScheduleByDate(ScheduleSearchParam searchParam) {
		return personalScheduleMapper.getPersonalScheduleByDate(searchParam);
	}

	public ScheduleDetail getPersonalSchedule(ScheduleSearchParam searchParam) {
		if (searchParam.getType() != null && searchParam.getType().equals("프로젝트")) {
			return personalScheduleMapper.getPersonalScheduleByProject(searchParam);
		} else if (searchParam.getType() != null && searchParam.getType().equals("고객정보")) {
			return personalScheduleMapper.getPersonalScheduleBycustomer_pickers(searchParam);
		} else {
			return personalScheduleMapper.getPersonalSchedule(searchParam);
		}
	}

	public int updatePersonalSchedule(ScheduleDetail scheduleDetail) throws Exception {
		int a = personalScheduleMapper.updatePersonalSchedule(scheduleDetail);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gridData", scheduleDetail.getGridData());
		shareListSave(map,"U","N");
		return a;
	}

	public int removePersonalSchedule(Map<String, Object> searchParam) {
		return personalScheduleMapper.removePersonalSchedule(searchParam);
	}
	public int shareListDelete(Map<String, Object> searchParam) {
		return personalScheduleMapper.shareListDelete(searchParam);
	}
	public int shareListSave(Map<String, Object> map, String CRUD, String MultiYN) throws Exception {
		int idx = 0;
		int flag = 0;
		String ssn = SessionUtils.getSsn();
		Map<String, Object> idxOrigin = null;
		Map<String, Object> ownerInfo = null;
		ArrayList Email = new ArrayList();
		ArrayList SSNLIST = new ArrayList();
		
		map.put("OWNERSSN", ssn);
		if(map.get("idx") == null || map.get("idx") == "") {
			idxOrigin = personalScheduleMapper.searchScheduleIDX(map);
			idx =  (int) idxOrigin.get("IDX");
			map.put("idx", idx);
		}
		
		flag = personalScheduleMapper.shareListDelete(map);
		ObjectMapper objectMapper = new ObjectMapper();
		// JSON Array String -> List
		List<Map<String, Object>> ListData = new ArrayList<Map<String, Object>>();
		
			if(map.get("gridData") != null && !map.get("gridData").equals("")) {
				ListData = objectMapper.readValue((String)map.get("gridData"), new TypeReference<List<Map<String, Object>>>() {});
			}
	

		
		ownerInfo = personalScheduleMapper.searchOwnerInfo(map);
		String title =  (String) ownerInfo.get("deptName")+"_"+(String) ownerInfo.get("name")+" 님께서 일정에 초대하였습니다.";
		String content = "<div><p style=\"font-size:15px;\">일정 : "+map.get("type")+"_"+map.get("content") +"</p>" ;
		content += "<p style=\"font-size:15px;\">장소 : "+map.get("place")+"</p>";
		content += "<p style=\"font-size:15px;\">일시 : "+map.get("sdate")+" "+map.get("stime")+" ~ "+map.get("edate")+" "+map.get("etime")+"</p>";
		if(map.get("customerName") != "" && map.get("customerName") != null) {
			content += "<p style=\"font-size:15px;\">고객사 : "+map.get("customerName")+"</p>";
		}
		
		shareTitle = title;
		shareContent = content;
		
		for(int i=0;i<ListData.size();i++) {
			Map<String, Object> data = ListData.get(i);
			data.put("idx", map.get("idx"));
			data.put("pms_User_Id", map.get("pms_User_Id"));
			data.put("OWNERSSN", ssn);
			flag = personalScheduleMapper.shareListInsert(data);
			Email.add(data.get("EMAIL"));
			SSNLIST.add(data.get("SSN"));
		}
		shareEmail = Email;
		shareSSN = SSNLIST;
		return flag;
	}
	public List<Map<String, Object>> searchShareList(Map<String, Object> map) {
		return personalScheduleMapper.searchShareList(map);
	}
	
	public List<Map<String, Object>> searchScheduleList(Map<String, Object> map) {
		return personalScheduleMapper.searchScheduleList(map);
	}
	
	public Map<String, Object> checkSchedule(Map<String, Object> map) {
		return personalScheduleMapper.checkSchedule(map);
	}
}
