package kr.co.kmac.pms.approval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.approval.domain.ApprovalSearchEntity;
import kr.co.kmac.pms.approval.domain.ApprovalSearchParam;
import kr.co.kmac.pms.approval.mapper.ApprovalSearchMapper;

@Service
@Transactional(readOnly = true)
public class ApprovalSearchService {

	@Autowired
	private ApprovalSearchMapper approvalSearchMapper;

	public ApprovalSearchParam setSearchMetaInfo(ApprovalSearchParam searchParam) {
		// 나중에 시간내서 권한은 access control list 형태로 변경 필요
		// approvalSearchParam.setUserAuthority(this.approvalSearchMapper.getApprovalSearchAuthority(approvalSearchParam.getUserSsn()));
		searchParam.setUserAuthority1(this.approvalSearchMapper.getSearchRule_GSM_BM_SA(searchParam.getUserSsn()));
		searchParam.setUserAuthority2(this.approvalSearchMapper.getSearchRule_GSM_SA(searchParam.getUserSsn()));
		searchParam.setUserAuthority3(this.approvalSearchMapper.getSearchRule_GSM_SALARY_SA(searchParam.getUserSsn()));

		int totalNumberOfEntries = this.approvalSearchMapper.getTotalNumberOfEntries(searchParam);
		searchParam.setTotalNumberOfEntries(this.approvalSearchMapper.getTotalNumberOfEntries(searchParam));
		searchParam.setTotalNumberOfPages((int) Math.ceil((double) totalNumberOfEntries / (double) searchParam.getPagingNumberPer()));
		return searchParam;
	}

	public List<ApprovalSearchEntity> getApprovalSearchMainList(ApprovalSearchParam approvalSearchParam) {
		return this.approvalSearchMapper.getApprovalSearchMainList(approvalSearchParam);
	}

	// 아래는 agg
	public List<ApprovalSearchEntity> getApprovalSearchApprovalTypeAgg(ApprovalSearchParam approvalSearchParam) {
		return this.approvalSearchMapper.getApprovalSearchApprovalTypeAgg(approvalSearchParam);
	}

	public List<ApprovalSearchEntity> getApprovalSearchStateAgg(ApprovalSearchParam approvalSearchParam) {
		return this.approvalSearchMapper.getApprovalSearchStateAgg(approvalSearchParam);
	}

	public List<ApprovalSearchEntity> getApprovalSearchRegisterDeptAgg(ApprovalSearchParam approvalSearchParam) {
		return this.approvalSearchMapper.getApprovalSearchRegisterDeptAgg(approvalSearchParam);
	}

}
