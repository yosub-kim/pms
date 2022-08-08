package kr.co.kmac.pms.schedule.domain.summary;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.kmac.pms.common.util.DateUtils;
import lombok.Data;

@Data
public class ScheduleSummary {

	private String ssn;
	private String userId;
	private String userName;
	private String posName;
	private String posCode;
	private String groupId;
	private String groupParentId;
	private String groupName;
	private String labelName;

	private int ordSeq;
	private int groupSeq;

	private int year;
	private int month;
	private int day;
	private int holiday;

	private int t1;// [내부일정]
	private int t2;// [외부일정]
	private int t3;// [고객정보]
	private int t4;// [프로젝트]
	private int t5;// [교육참석]
	private int t6;// [휴가]
	private int t7;// [Up-day]
	private int t8;// [개인휴무]

	private int totalCount;

	/*
	 * 순서 변경 재택근무 > 휴가 > 외근 > 내근 > 프로젝트 > 고객정보
	 * 
	 * 재택> 휴가> 외부> 내부> 고객정보>교육참석>프로젝트 yellow> pink> blue> purple> gray> green>
	 * orange 1개일 경우 기존 컬럼..2개이상일 경우 컬럼 조합 div class="circle [색상코드]_b [색상코드_a”
	 */
	public String getIconCode() {
		String[] t = { "circle", "_a", "_b", "group" };

		List<String> tList = new ArrayList<String>();
		String returnVal = "";

		if(totalCount > 1) {
			if (t7 > 0) {// [Up-day] - 노랑
				tList.add("yellow");
				if (!"".equals(returnVal))
					returnVal = "ico-4 select";// 재택근무
			}
	
			if (t6 > 0 || t8 > 0) {// [휴가], 개인휴무 - 핑크
				tList.add("pink");
				if (!"".equals(returnVal))
					returnVal = "ico-6 select";// 휴가
			}
	
			if (t2 > 0) {// [외부일정] - 파랑
				tList.add("blue");
				if (!"".equals(returnVal))
					returnVal = "ico-1 select";// 사업관리
			}
	
			if (t1 > 0) {// [내부일정] - 보라
				tList.add("purple");
				if (!"".equals(returnVal))
					returnVal = "ico-2 select";// 사업관리
			}
	
			if (t3 > 0) {// [고객정보] - 회색
				tList.add("gray");
				if (!"".equals(returnVal))
					returnVal = "ico-7 select";// 고객정보
			}
	
			if (t5 > 0) {// [교육참석] - 녹색
				tList.add("green");
				if (!"".equals(returnVal))
					returnVal = "ico-3 select";// 교육참석
			}
	
			if (t4 > 0) {// [프로젝트] - 주황
				tList.add("orange");
				if (!"".equals(returnVal))
					returnVal = "ico-5 select";// 프로젝트 투입
			}
	
			if (totalCount > 1) {
				returnVal = returnVal + " group select ";// 여러개
			}
	
			if (tList.size() >= 2) {
				returnVal = "circle " + tList.get(0) + "_b" + " " + tList.get(1) + "_a" + " group ";
			} else {
				if (t7 > 0) {// [Up-day]
					return returnVal + "ico-4 select";// 재택근무
				} 
				
				else if (t6 > 0) {// [휴가]
					return returnVal + "ico-6 select";// 휴가
				} else if (t8 > 0) {// 개인휴무
					return returnVal + "ico-6 select";// 휴가
				} 
				
				else if (t2 > 0) {// [외부일정]
					return returnVal + "ico-1 select";// 사업관리
				} else if (t1 > 0) {// [내부일정]
					return returnVal + "ico-2 select";// 사업관리
				} 
				
				else if (t4 > 0) {// [프로젝트]
					return returnVal + "ico-5 select";// 프로젝트 투입
				} 
				
				else if (t3 > 0) {// [고객정보]
					return returnVal + "ico-7 select";// 고객정보
				} 
				
				else if (t5 > 0) {// [교육참석]
					return returnVal + "ico-3 select";// 교육참석
				}
			}
			
		}else {
			String over1 = "";
			if (totalCount > 1) {
				returnVal = "group select ";// 여러개
			}

			if (t7 > 0) {// [Up-day]
				return returnVal + "ico-4 select";// 재택근무
			} 
			
			else if (t6 > 0) {// [휴가]
				return returnVal + "ico-6 select";// 휴가
			} else if (t8 > 0) {// 개인휴무
				return returnVal + "ico-6 select";// 휴가
			} 
			
			else if (t2 > 0) {// [외부일정]
				return returnVal + "ico-1 select";// 사업관리
			} else if (t1 > 0) {// [내부일정]
				return returnVal + "ico-2 select";// 사업관리
			} 
			
			else if (t4 > 0) {// [프로젝트]
				return returnVal + "ico-5 select";// 프로젝트 투입
			} 
			
			else if (t3 > 0) {// [고객정보]
				return returnVal + "ico-7 select";// 고객정보
			} 
			
			else if (t5 > 0) {// [교육참석]
				return returnVal + "ico-3 select";// 교육참석
			}
		}
		return returnVal;
	}

	public String getDateStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, this.year);
		cal.set(Calendar.MONTH, this.month - 1);
		cal.set(Calendar.DATE, this.day);

		return DateUtils.getYyyymmdd(cal);
	}

	public String getIdStr() {
		return this.getDateStr();
	}

}
