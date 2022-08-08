package kr.co.kmac.pms.project.domain;

import kr.co.kmac.pms.common.domain.PagingInfo;
import lombok.Data;

@Data
public class ProjectSearchParam extends PagingInfo {

	private String[] projectState;
	private String[] businessTypeCode;
	private String[] runningDivCode;
	private String[] runningDeptCode;
	private String[] customerCode;
	private String[] projectTypeCode;

	private String keyword;
	private String realStartDate;
	private String realEndDate;

}
