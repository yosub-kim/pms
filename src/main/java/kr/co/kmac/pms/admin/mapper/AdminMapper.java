package kr.co.kmac.pms.admin.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AdminMapper {
	
	public List<Map<String, Object>> searchAllMenu(Map<String, Object> map) throws DataAccessException;
	
	public int insertMenuList(Map<String, Object> map) throws DataAccessException;
	
	public int updateMenuList(Map<String, Object> map) throws DataAccessException;
	
	public int deleteMenuList(Map<String, Object> map) throws DataAccessException;
	
	public int deleteMenuRole(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> searchRoleList(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> searchRoleMenuList(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> managementDoubleSaleList(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getAdminUserList() throws DataAccessException;
	
	public int saveAccountConfirmYN(Map<String, Object> map) throws DataAccessException;
}
