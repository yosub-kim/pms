package kr.co.kmac.pms.admin.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.kmac.pms.admin.service.AdminService;
import kr.co.kmac.pms.common.util.JsonConvert;
import kr.co.kmac.pms.common.util.SessionUtils;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private JsonConvert jsonconvert;
	
	@GetMapping("/menuList")
	public String menuList(HttpServletRequest req, Model model) {
		
		return "/admin/menuList";
	}
	@GetMapping("/menuRoleList")
	public String menuRoleList(HttpServletRequest req, Model model) {
		return "/admin/menuRoleList";
	}
	@GetMapping("/managementDoubleSales")
	public String managementDoubleSales(HttpServletRequest req, Model model) {
		List<Map<String, Object>> AdminUserList = adminService.getAdminUserList();
		boolean adminFlag = false;
		String userSSN = SessionUtils.getSsn();
		String userDept = SessionUtils.getDept();
		for(int i=0;i<AdminUserList.size();i++) {
			String checkSsn = (String)AdminUserList.get(i).get("SSN");
			if(userSSN.equals(checkSsn)) {
				adminFlag = true;
				break;
			}
		}
		
		if(userDept.equals("9382")) {
			adminFlag = true;
		}
		model.addAttribute("adminFlag", adminFlag);
		return "/admin/managementDoubleSales";
	}
	@PostMapping("/searchAllMenu")
	@ResponseBody
	public ResponseEntity<?> searchAllMenu(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
	
		List<Map<String, Object>> Resultmap = adminService.searchAllMenu(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/saveMenuList")
	@ResponseBody
	public ResponseEntity<?> saveMenuList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		HashMap<String, String> hmData = new HashMap<String, String>();
		System.out.println("map =====>"+map);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("data =====>"+map.get("data"));
		List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
		System.out.println("data =====>"+data.get(0).get("CRUD"));
		int Result = adminService.saveMenuList(data);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, HttpStatus.OK);
	}
	
	@PostMapping("/searchRoleList")
	@ResponseBody
	public ResponseEntity<?> searchRoleList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
	
		List<Map<String, Object>> Resultmap = adminService.searchRoleList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/searchRoleMenuList")
	@ResponseBody
	public ResponseEntity<?> searchRoleMenuList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
	
		List<Map<String, Object>> Resultmap = adminService.searchRoleMenuList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/managementDoubleSaleList")
	@ResponseBody
	public ResponseEntity<?> managementDoubleSaleList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		List<Map<String, Object>> Resultmap = adminService.managementDoubleSaleList(map);
		List<Map<String, Object>> AdminUserList = adminService.getAdminUserList();
		boolean adminFlag = false;
		String userSSN = SessionUtils.getSsn();
		String userDept = SessionUtils.getDept();
		for(int i=0;i<AdminUserList.size();i++) {
			String checkSsn = (String)AdminUserList.get(i).get("SSN");
			if(userSSN.equals(checkSsn)) {
				adminFlag = true;
				break;
			}
		}
		
		if(userDept.equals("9382")) {
			adminFlag = true;
		}
		if(Resultmap.size() > 0) {
			Resultmap.get(0).put("adminFlag", adminFlag);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/saveAccountConfirmYN")
	@ResponseBody
	public ResponseEntity<?> saveAccountConfirmYN(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		int result = adminService.saveAccountConfirmYN(map);
		Map<String, Object> Resultmap = new HashMap<String, Object>();
		Resultmap.put("result", result);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
}