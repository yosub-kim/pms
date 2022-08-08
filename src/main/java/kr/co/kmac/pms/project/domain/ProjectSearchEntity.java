package kr.co.kmac.pms.project.domain;


import lombok.Data;

@Data
public class ProjectSearchEntity {

	private String projectCode;
	private String projectName;
	private String projectTypeCode;
	private String projectTypeCodeName;
	private String projectState;
	private String projectStateName;

	private String preStartDate;
	private String preEndDate;
	private String realStartDate;
	private String realEndDate;

	private String businessTypeCode;
	private String businessTypeCodeName;
	private String runningDivCode;
	private String runningDivCodeName;
	private String runningDeptCode;
	private String runningDeptCodeName;

	private String customerName;
	private String customerCode;

	private String hashTags;
	private String attachFileCnt;

	private int cnt;
	private boolean checked;
}
