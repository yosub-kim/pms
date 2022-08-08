package kr.co.kmac.pms.schedule.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import lombok.Data;

@Data
public class Schedule extends DateInfo {

	private List<ScheduleDetail> scheduleDetailList;
	
	public String getDateString2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, this.getYear());
		cal.set(Calendar.MONTH, this.getMonth() - 1);
		cal.set(Calendar.DATE, this.getDay());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal.getTime());
	}}