package kr.co.kmac.pms.approval.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.approval.domain.ApprovalSearchEntity;
import kr.co.kmac.pms.approval.domain.ApprovalSearchParam;

public interface ApprovalSearchMapper {

	public int getTotalNumberOfEntries(ApprovalSearchParam approvalSearchParam) throws DataAccessException;

	//public String[] getApprovalSearchAuthority(String loginSsn) throws DataAccessException;
	public String getSearchRule_GSM_BM_SA(String loginSsn) throws DataAccessException;
	public String getSearchRule_GSM_SA(String loginSsn) throws DataAccessException;
	public String getSearchRule_GSM_SALARY_SA(String loginSsn) throws DataAccessException; 

	// 메인리스트
	public List<ApprovalSearchEntity> getApprovalSearchMainList(ApprovalSearchParam approvalSearchParam) throws DataAccessException;

	// 아래는 agg
	public List<ApprovalSearchEntity> getApprovalSearchApprovalTypeAgg(ApprovalSearchParam approvalSearchParam) throws DataAccessException;

	public List<ApprovalSearchEntity> getApprovalSearchStateAgg(ApprovalSearchParam approvalSearchParam) throws DataAccessException;

	public List<ApprovalSearchEntity> getApprovalSearchRegisterDeptAgg(ApprovalSearchParam approvalSearchParam) throws DataAccessException;

}
