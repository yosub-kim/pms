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

import kr.co.kmac.pms.support.service.conferenceService;


@Controller
@RequestMapping("/support/conference/*")
public class conferenceController{

	@Autowired
	private conferenceService conferenceservice;


	
	
	@GetMapping("/conferenceList")
	public String conferenceList(HttpServletRequest req,Model model) {
		List<Map<String, Object>> yearList = conferenceservice.getConferenceYearList();
		model.addAttribute("yearList", yearList);
		return "/support/conferenceList";
	}
	
	@GetMapping("/conferenceDetail")
	public String conferenceDetail(HttpServletRequest req,Model model, @RequestParam String CONFER_IDX) {
		model.addAttribute("CONFER_IDX", CONFER_IDX);
		return "/support/conferenceDetail";
	}

	
	
	@PostMapping("/searchConferenceList")
	@ResponseBody
	public ResponseEntity<?> searchConferenceList(HttpServletRequest req,@RequestBody Map<String, Object> map) throws IOException {
		List<Map<String, Object>> Resultmap = conferenceservice.searchConferenceList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/saveConferenceList")
	@ResponseBody
	public ResponseEntity<?> saveConferenceList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		HashMap<String, String> hmData = new HashMap<String, String>();
		System.out.println("map =====>"+map);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("data =====>"+map.get("data"));
		List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
		System.out.println("data =====>"+data.get(0).get("CRUD"));
		int Result = conferenceservice.saveConferenceList(data);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, HttpStatus.OK);
	}
	
	@PostMapping("/searchConferenceDetailList")
	@ResponseBody
	public ResponseEntity<?> searchConferenceDetailList(HttpServletRequest req,@RequestBody Map<String, Object> map) throws IOException {
		List<Map<String, Object>> Resultmap = conferenceservice.searchConferenceDetailList(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/saveConferenceDetailList")
	@ResponseBody
	public ResponseEntity<?> saveConferenceDetailList(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		HashMap<String, String> hmData = new HashMap<String, String>();
		System.out.println("map =====>"+map);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("data =====>"+map.get("data"));
		List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
		System.out.println("data =====>"+data.get(0).get("CRUD"));
		int Result = conferenceservice.saveConferenceDetailList(data);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(hmData, HttpStatus.OK);
	}
	
	@PostMapping("/conferenctExpect")
	@ResponseBody
	public ResponseEntity<?> conferenctExpect(HttpServletRequest req,@RequestBody Map<String, Object> map) throws IOException {
		List<Map<String, Object>> Resultmap = conferenceservice.conferenctExpect(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
}
