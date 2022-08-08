package kr.co.kmac.pms.approval.domain;

import kr.co.kmac.pms.common.domain.PagingInfo;
import lombok.Data;

@Data
public class ApprovalSearchParam extends PagingInfo {

	// - 좌측 탭 : 결재유형, 조직단위별, 진행 상태별, 기안일자 별 조회 기능 추가(날짜 기능 맨 위에 추가),
	// - 검색 탭 : 제목, 내용 (부하가 심할 시 제외), 기안자

	private String[] approvalType;
	private String[] registerDept;
	private String[] state;

	private String registerStartDate;
	private String registerEndDate;

	private String keyword;

	private String userSsn;
	private String[] userAuthority;
	private String userAuthority1;
	private String userAuthority2;
	private String userAuthority3;

}
