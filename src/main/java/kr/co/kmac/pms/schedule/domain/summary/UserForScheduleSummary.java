package kr.co.kmac.pms.schedule.domain.summary;

import java.util.List;

import lombok.Data;

@Data
public class UserForScheduleSummary {

	private String groupId;
	private String groupName;
	private String labelName;

	private String ssn;
	private String userName;
	
	private String posName;

	private List<ScheduleSummary> scheduleSummaryList;

}
