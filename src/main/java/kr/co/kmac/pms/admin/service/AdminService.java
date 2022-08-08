package kr.co.kmac.pms.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.admin.mapper.AdminMapper;
import kr.co.kmac.pms.common.util.IdGenerator;

@Service
@Transactional(readOnly = true)
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private IdGenerator idgenerator;
	
	public List<Map<String, Object>> searchAllMenu(Map<String, Object> map) {
		return this.adminMapper.searchAllMenu(map);
	}
	
	public int saveMenuList(List<Map<String, Object>> map) {
		int result = 0;
		
		for(int i=0;i<map.size();i++) {
			String CRUD = (String)map.get(i).get("CRUD");
			switch (CRUD) {
			case "C":
				String MENUNUM = idgenerator.generate(idgenerator.PREFIX_MENU);
				System.out.println("MENUNUM  ===>"+MENUNUM);
				map.get(i).put("MENUNUM",MENUNUM);
				result = this.adminMapper.insertMenuList(map.get(i));
				break;
			case "U":
				result = this.adminMapper.updateMenuList(map.get(i));
				break;
			case "D":
				result = this.adminMapper.deleteMenuList(map.get(i));
				if(result > 0) {
					result = this.adminMapper.deleteMenuRole(map.get(i));
				}
				break;
			}	
		}
		return result;
	}
	
	public List<Map<String, Object>> searchRoleList(Map<String, Object> map) {
		return this.adminMapper.searchRoleList(map);
	}
	
	public List<Map<String, Object>> searchRoleMenuList(Map<String, Object> map) {
		return this.adminMapper.searchRoleMenuList(map);
	}
	
	public List<Map<String, Object>> managementDoubleSaleList(Map<String, Object> map) {
		return this.adminMapper.managementDoubleSaleList(map);
	}
	
	public List<Map<String, Object>> getAdminUserList() {
		return this.adminMapper.getAdminUserList();
	}
	
	public int saveAccountConfirmYN(Map<String, Object> map) {
		return this.adminMapper.saveAccountConfirmYN(map);
	}
}
