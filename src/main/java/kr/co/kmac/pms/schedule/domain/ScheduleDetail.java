package kr.co.kmac.pms.schedule.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDetail implements Cloneable {

	private int year;
	private int month;
	private int day;
	private int seq;
	private long idx;
	private String ssn;
	private String startHour;
	private String startMin;
	private String endHour;
	private String endMin;
	private String type;
	private String content;
	private String customerName;
	private String relationUser;
	private String place;
	private String secretYN;

	private String googleSyncId;
	private String workType;

	private Date createDate;

	private String sdate;
	private String stime;
	private String edate;
	private String etime;
	private String multiYN;
	private String holidayYN;
	private String MYSCHEDULE;
	private String name;
	private String deptname;
	private String ShareCheck;
	private String gridData;
	
	/*
	 * 재택근무 > 휴가 > 외근 > 내근 > 프로젝트 > 고객정보
	 */
	public String getIconCodeShort() {
		String res = "";
		if (this.getType() == null) {
			res = "ico-1";
		} else if (this.getType().equals("외부일정") || (this.workType != null && this.workType.equals("E"))) {// [외부일정]
			res = "ico-1";// 외부일정
		} else if (this.getType().equals("내부일정") || (this.workType != null && this.workType.equals("I"))) {// [내부일정]
			res = "ico-2";// 내부일정
		} else if (this.getType().equals("교육참석")) {// [교육참석]
			res = "ico-3" + "";// 교육참석
		} else if (this.getType().equals("Up-day")) {// [Up-day]
			res = "ico-4";// 재택근무
		} else if (this.getType().equals("프로젝트")) {// [프로젝트]
			res = "ico-5";// 프로젝트 투입
		} else if (this.getType().equals("PJT게시판")) {// [프로젝트]
			res = "ico-5";// 프로젝트 투입
		} else if (this.getType().equals("휴가")) {// [휴가]
			res = "ico-6";// 휴가
		} else if (this.getType().equals("개인휴무")) {// 개인휴무
			res = "ico-6";// 휴가
		} else if (this.getType().equals("고객정보")) {// [고객정보]
			res = "ico-7";// 고객정보
		} else {
			res = "ico-1";
		}
		return res;
	}

	public String getIconCode() {
		return getIconCodeShort() + " select";
	}

	public String getDateString() {
		String res = "";
		res = res + this.getYear() + "년 ";
		res = res + this.getMonth() + "월 ";
		res = res + this.getDay() + "일 ";
		if (this.getStartHour() != null && !"".equals(getStartHour().trim()))
			res = res + this.getStartHour() + "시 ";
		if (this.getStartMin() != null && !"".equals(getStartMin().trim()))
			res = res + this.getStartMin() + "분 ~ ";
		if (this.getEndHour() != null && !"".equals(getEndHour().trim()))
			res = res + this.getEndHour() + "시 ";
		if (this.getEndMin() != null && !"".equals(getEndMin().trim()))
			res = res + this.getEndMin() + "분";

		return res;
	}

	public String getDateString2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, this.year);
		cal.set(Calendar.MONTH, this.month - 1);
		cal.set(Calendar.DATE, this.day);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal.getTime());
	}

	public String getTimeString() {
		return this.getStartHour() + ":" + this.getStartMin() + " ~ " + this.getEndHour() + ":" + this.getEndMin();
	}

	public String getEndTimeString() {
		if (this.getEndHour() == null)
			return "";
		else
			return this.getEndHour() + ":" + this.getEndMin();
	}

	public String getStartTimeString() {
		if (this.getStartHour() == null)
			return "";
		else
			return this.getStartHour() + ":" + this.getStartMin();
	}

	@Override
	public ScheduleDetail clone() throws CloneNotSupportedException {
		return (ScheduleDetail) super.clone();
	}
}