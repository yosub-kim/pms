package kr.co.kmac.pms.approval.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ApprovalSearchEntity {

	// - 메인 탭 : 결재유형, 제목, 조직단위, 기안자, 기안일, 진행상태

	private String approvalType;
	private String approvalName;
	private String subject;
	private String registerDept;
	private String registerDeptName;
	private String registerName;
	private Date registerDate;

	private String state;
	private String present;

	private int cnt;
}
