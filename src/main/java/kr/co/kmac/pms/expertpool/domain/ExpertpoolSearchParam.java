package kr.co.kmac.pms.expertpool.domain;

import kr.co.kmac.pms.common.domain.PagingInfo;
import lombok.Data;

@Data
public class ExpertpoolSearchParam extends PagingInfo {
//[인력pool]
//	- 좌측 탭 : 구분(상근(JOBCLASS A, B)/상임/RA/엑스퍼트/산업계강사/대학교수), 부서,  투입제한인력(누르면 조회되도록)
//	- 검색 탭 : 프로젝트 명으로 과거 투입했던 인력 조회, 고객사 명으로 투입했던 프로젝트 인력 조회, 전문분야/스킬로 인력 조회, 이름으로 조회
//	- 메인 탭 : 구분, 부서, 성명, 직위, 연락처(상근일경우 내선, 아닐 경우 휴대폰번호), 이메일


	private String[] jobClass;
	private String[] dept;
	private String[] restrictUser;

	private String keyword;
	
	private String consultantChk;
	
}
