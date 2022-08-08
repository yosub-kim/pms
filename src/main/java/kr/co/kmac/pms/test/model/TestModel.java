package kr.co.kmac.pms.test.model;

import lombok.Data;
	
@Data
public class TestModel {

	private String projectCode;
	private String projectName;
	private String projectTypeCode;
	private String projectDetailCode;
	private String businessTypeCode;
	private String processTypeCode;
	private String cioTypeCode;
	private String runningDivCode;
	private String runningDeptCode;
	private String industryTypeCode;
	private String preStartDate;
	private String preEndDate;
	private String realStartDate;
	private String realEndDate;
	private String projectState;
	private String endGubun;
	private String costOver;
	private String payCostOver;
	private String etcCostOver;
	private String businessTypeCodeName;
	private String runningDivCodeName;
	private String runningDeptCodeName;
	private String pmSsn;
	private String plSsn;
	private String runningPUCode;
	private String boardArticleCount;
	private String delayState;
	private String adminOpen;

	private String pmname;
	private String boardArticleCountQM;

	private String attach;
	private String parentProjectCode;
	private String customerName;
	private String customerCode;
	private String businessFunctionType;

	private String attachFileCnt;
	private String hashTags;

}
