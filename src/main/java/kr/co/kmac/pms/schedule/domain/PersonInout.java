package kr.co.kmac.pms.schedule.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PersonInout extends ScheduleDetail implements Cloneable {

	private String approvalSsn;
	private Date approvalDate;
	private String approvalYN;
	private String userId;

	@Override
	public PersonInout clone() throws CloneNotSupportedException {
		return (PersonInout) super.clone();
	}

}