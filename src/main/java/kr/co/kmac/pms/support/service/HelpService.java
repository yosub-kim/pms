package kr.co.kmac.pms.support.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.common.mapper.CommonMapper;
import kr.co.kmac.pms.common.util.MailService;
import kr.co.kmac.pms.support.mapper.HelpMapper;

@Service
@Transactional
public class HelpService {

	@Autowired
	private HelpMapper helpmapper;
	@Autowired
	private MailService mail;
	@Autowired
	private CommonMapper commonmapper;
	
	public int doSystemFaqQuestionWrite(Map<String, Object> map) throws Exception {
		int result = this.helpmapper.doSystemFaqQuestionWrite(map);
		if(result > 0) {
			Map<String, Object> UserInfo = commonmapper.getUserInfo(map);
			String title = (String)UserInfo.get("name")+"님의 시스템 FAQ 질문이 도착했습니다.";
			String subject = "제목:"+(String)map.get("subject")+"<br>";
			String writer = "작성자:"+(String)map.get("SSN")+(String)UserInfo.get("name")+"<br>";
			
			String content = subject + writer + (String)map.get("content");
			ArrayList email = new ArrayList();
			email.add("cooking@kmac.co.kr");
			mail.sendMail(title,content,email);
		}
		return result;
	}
	
	public int doSystemFaqRegist(Map<String, Object> map) throws Exception {
		if(map.get("CRUD").equals("C")) {
			return this.helpmapper.doSystemFaqRegist(map);
		}else {
			return this.helpmapper.doSystemFaqEdit(map);
		}
	}
	
	public List<Map<String, Object>> getSystemFaqList(Map<String, Object> map) throws Exception {
		return this.helpmapper.getSystemFaqList(map);
	}
	
	public int doSystemFaqDelete(Map<String, Object> map) throws Exception {
		return this.helpmapper.doSystemFaqDelete(map);
	}
	
	public List<Map<String, Object>> getSystemFaqFieldList(Map<String, Object> map) throws Exception {
		return this.helpmapper.getSystemFaqFieldList(map);
	}
}
