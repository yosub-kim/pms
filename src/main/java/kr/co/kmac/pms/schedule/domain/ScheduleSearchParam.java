package kr.co.kmac.pms.schedule.domain;

import lombok.Data;

@Data
public class ScheduleSearchParam {

	private String ssn;
	private String jobClass;
	private String dept;
	private String type;
	private int year;
	private int month;
	private int day;
	private String date;
	private String prevDate;
	private String nextDate;
	private String userId;

	private int seq;
	private long idx;

	private String secretYN;

	private String keyword;
	private boolean readonly;

	private String startHour;
	private String startMin;
	private String endHour;
	private String endMin;
	private String content;
	private String customerName;
	
	private int startYear; 
	private int startMonth; 
	private int endYear;
	private int endMonth;
	
	private int holiday;
	
	private String email;
	
}
