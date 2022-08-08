package kr.co.kmac.pms.board.service;
import kr.co.kmac.pms.board.mapper.BoardMapper;
import kr.co.kmac.pms.common.mapper.CommonMapper;
import kr.co.kmac.pms.common.util.MailService;
import kr.co.kmac.pms.common.util.SessionUtils;
import org.mortbay.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whois.whoisSMS;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private MailService mail;
	
	public List<Map<String, Object>> getCeoTalkList(Map<String, Object> map) {
		return this.boardMapper.getCeoTalkList(map);
	}
	
	public Map<String, Object> getBoardTotalCNT(Map<String, Object> map) {
		return this.boardMapper.getBoardTotalCNT(map);
	}
	
	public int doCeoTalkWrite(Map<String, Object> map) {
		int result = this.boardMapper.doCeoTalkWrite(map);
		Map<String, Object> UserInfo = null;
		if(result > 0) {
			
			String Title = "\"CEO 두드림\" 구성원 고충이 등록되었습니다.";
			String Content = "<p><운영방법></p>";
			Content += "<p>- 작성자와 CEO만 해당 내용 확인 가능 ※ 경영기획센터를 거치지 않음 </p>";
			Content += "<p>- 고충 접수 시, 100% 답변 진행 (답변기한은 사안의 시급성에 따라 판단) </p>";
			Content += "<p>- 관련 내용에 대한 조사가 필요시, 철저한 작성자 비밀보호를 원칙으로 답변에 필요한 내용만을 \r\n"
					+ "   CEO의 지시하에 경영기획센터장 또는 팀장이 조사할 수 있음 </p>";
			Content += "<a href=\"https://pmsrenewal.kmac.co.kr/_new/board/CeoTalk\">고충 확인하기 : CEO 두드림(바로가기)</a>";
			UserInfo = this.commonMapper.getUserInfo(map);
			
			ArrayList Email = new ArrayList();
			Email.add("cooking@kmac.co.kr");
			try {
				//mail.sendMail(Title,Content,Email, UserInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String mobileNo = StringUtil.replace((String)UserInfo.get("mobileNo"), "-", "");
			whoisSMS sms = new whoisSMS();
			sms.login("kmac4u", "kmacno1!@#");
			//sms.setUtf8();
			String sms_msg = "\"CEO 두드림\" 글이 등록되었습니다.";
			try {
				sms.setParams("01023706642", "02-3786-0642", new String(sms_msg.getBytes(), "8859_1"),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()), "");
				sms.emmaSend();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		
		return result;
	}
	
	public Map<String, Object> getCeoTalkDetail(Map<String, Object> map) {
		return this.boardMapper.getCeoTalkDetail(map);
	}
	
	public List<Map<String, Object>> getCeoTalkComment(Map<String, Object> map) {
		return this.boardMapper.getCeoTalkComment(map);
	}
	
	public Map<String, Object> getCeoBoardTotalCommentCNT(Map<String, Object> map) {
		return this.boardMapper.getCeoBoardTotalCommentCNT(map);
	}

	public int doCeoTalkCommentWrite(Map<String, Object> map) {
		int result = this.boardMapper.doCeoTalkCommentWrite(map);
		
		if(result > 0 && SessionUtils.getSsn().equals("A000006")) {
			Map<String, Object> CeotalkDetail = this.boardMapper.getCeoTalkDetail(map);
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("SSN", CeotalkDetail.get("WRITERID"));
			Map<String, Object> UserInfo = null;
			String Title = "\"CEO 두드림\" 답변이 등록되었습니다.";
			String Content = "<p><운영방법></p>";
			Content += "<p>- 작성자와 CEO만 해당 내용 확인 가능 ※ 경영기획센터를 거치지 않음 </p>";
			Content += "<p>- 고충 접수 시, 100% 답변 진행 (답변기한은 사안의 시급성에 따라 판단) </p>";
			Content += "<p>- 관련 내용에 대한 조사가 필요시, 철저한 작성자 비밀보호를 원칙으로 답변에 필요한 내용만을 \r\n"
					+ "   CEO의 지시하에 경영기획센터장 또는 팀장이 조사할 수 있음 </p>";
			Content += "<a href=\"https://pmsrenewal.kmac.co.kr/_new/board/CeoTalk\">답변 확인하기 : CEO 두드림(바로가기)</a>";
			UserInfo = this.commonMapper.getUserInfo(searchMap);
			
			ArrayList Email = new ArrayList();
			Email.add(UserInfo.get("email"));
			String mobileNo = StringUtil.replace((String)UserInfo.get("mobileNo"), "-", "");
			try {
				//mail.sendMail(Title,Content,Email);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			whoisSMS sms = new whoisSMS();
			sms.login("kmac4u", "kmacno1!@#");
			//sms.setUtf8();
			String sms_msg = "\"CEO 두드림\" 답변이 등록되었습니다.";
			try {
				sms.setParams(mobileNo, "02-3786-0642", new String(sms_msg.getBytes(), "8859_1"),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()), "");
				sms.emmaSend();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return result;
	}
	public int doDeleteBoard(Map<String, Object> map) {
		return this.boardMapper.doDeleteBoard(map);
	}
	
	public int doCeoTalkUpdate(Map<String, Object> map) {
		return this.boardMapper.doCeoTalkUpdate(map);
	}
	
	public int deleteCeoTalkComment(Map<String, Object> map) {
		return this.boardMapper.deleteCeoTalkComment(map);
	}
}

