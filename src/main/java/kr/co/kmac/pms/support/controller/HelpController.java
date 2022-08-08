package kr.co.kmac.pms.support.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.support.service.HelpService;
import kr.co.kmac.pms.support.service.conferenceService;


@Controller
@RequestMapping("/support/help/*")
public class HelpController{

	@Autowired
	private HelpService helpservice;

	@GetMapping("/systemFaqAnswer")
	public String systemFaqAnswer(HttpServletRequest req,Model model) {
		return "/support/help/SystemFaqAnswer";
	}
	@GetMapping("/systemFaqQuestion")
	public String systemFaqQuestion(HttpServletRequest req,Model model) {
		return "/support/help/SystemFaqQuestion";
	}
	@GetMapping("/systemSuggestion")
	public String systemSuggestion(HttpServletRequest req,Model model) {
		return "/support/help/SystemSuggestion";
	}
	@GetMapping("/systemFaq")
	public String systemFaq(HttpServletRequest req,Model model) {
		return "/support/help/SystemFaq";
	}
	@GetMapping("/systemFaqRegist")
	public String systemFaqRegist(HttpServletRequest req,Model model,@RequestParam(required = false) String seq) throws Exception {
		System.out.println("systemFaqRegist ===>"+seq);
		String CRUD = "C";
		Map<String, Object> FaqDetail = null;
		if(seq != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("seq", seq);
			FaqDetail = helpservice.getSystemFaqList(map).get(0);
			CRUD = "U";
		}
		model.addAttribute("seq",seq);
		model.addAttribute("CRUD",CRUD);
		model.addAttribute("FaqDetail",FaqDetail);
		return "/support/help/SystemFaqRegist";
	}
	
	@PostMapping("/doSystemFaqQuestionWrite")
	@ResponseBody
	public ResponseEntity<?> doSystemFaqQuestionWrite(HttpServletRequest req,@RequestBody Map<String, Object> map) throws Exception {
		map.put("SSN", SessionUtils.getSsn());
		int Resultmap = helpservice.doSystemFaqQuestionWrite(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/doSystemFaqRegist")
	@ResponseBody
	public ResponseEntity<?> doSystemFaqRegist(HttpServletRequest req,@RequestBody Map<String, Object> map) throws Exception {
		map.put("SSN", SessionUtils.getSsn());
		System.out.println("doSystemFaqRegist ==>"+map);
		int Resultmap = helpservice.doSystemFaqRegist(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/getSystemFaqList")
	@ResponseBody
	public ResponseEntity<?> getSystemFaqList(HttpServletRequest req,@RequestBody Map<String, Object> map) throws Exception {
		map.put("SSN", SessionUtils.getSsn());
		System.out.println("getSystemFaqList ==>"+map);
		List<Map<String, Object>> Resultmap = helpservice.getSystemFaqList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/doSystemFaqDelete")
	@ResponseBody
	public ResponseEntity<?> doSystemFaqDelete(HttpServletRequest req,@RequestBody Map<String, Object> map) throws Exception {
		int Resultmap = helpservice.doSystemFaqDelete(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	@PostMapping("/getSystemFaqFieldList")
	@ResponseBody
	public ResponseEntity<?> getSystemFaqFieldList(HttpServletRequest req,@RequestBody(required = false) Map<String, Object> map) throws Exception {
		List<Map<String, Object>> Resultmap = helpservice.getSystemFaqFieldList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
}
