package kr.co.kmac.pms.common.controller;

import java.nio.charset.Charset;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmac.pms.common.service.CommonService;
import kr.co.kmac.pms.common.util.SessionUtils;

@Controller
@RequestMapping("/common/*")
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	
	@PostMapping("/getGroupList")
	@ResponseBody
	public ResponseEntity<?> getGeoupList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		List<Map<String, Object>> Resultmap = commonService.getGroupList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/getCodeList")
	@ResponseBody
	public ResponseEntity<?> getCodeList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		List<Map<String, Object>> Resultmap = commonService.getCodeList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
}
