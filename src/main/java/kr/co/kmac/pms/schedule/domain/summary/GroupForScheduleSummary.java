package kr.co.kmac.pms.schedule.domain.summary;

import java.util.List;

import lombok.Data;

@Data
public class GroupForScheduleSummary {

	private String groupId;
	private String groupName;
	private String labelName;
	private int groupSeq;
	private int userCount;
	private List<UserForScheduleSummary> userForScheduleSummaries;

}