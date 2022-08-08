package kr.co.kmac.pms.expertpool.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ExpertpoolSearchEntity {

	// [인력pool]
	// - 좌측 탭 : 구분(상근(JOBCLASS A, B)/상임/RA/엑스퍼트/산업계강사/대학교수), 부서, 투입제한인력(누르면 조회되도록)
	// - 검색 탭 : 프로젝트 명으로 과거 투입했던 인력 조회, 고객사 명으로 투입했던 프로젝트 인력 조회, 전문분야/스킬로 인력 조회,
	// 이름으로 조회
	// - 메인 탭 : 구분, 부서, 성명, 직위, 연락처(상근일경우 내선, 아닐 경우 휴대폰번호), 이메일

	private String jobClass;
	private String jobClassName;

	private String company;
	private String dept;
	private String deptName;

	private String companyposition;
	private String companyPositionName;

	private String ssn;
	private String name;

	private String email, mobileNo, telNo, companyTelNo;

	private String consultingMajor;

	private String restrictUser, absence;

	private String createrName;
	private Date createdDate;
	private Date modifiedDate;
	
	private int cnt;
	private boolean checked;
	private String restrictContents;
	private String emplNo;
	private String gender;
	private String uid;
	private String hobong;
}
