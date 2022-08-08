package kr.co.kmac.pms.schedule.domain;

import lombok.Data;

@Data
public class DateInfo {

	private int year;
	private int month;
	private int day;
	private String dayOfTheWeek;
	private String dayOfTheWeekKor;
	private boolean isHoliday;
	private boolean isToday;
	private String holidayName;

}