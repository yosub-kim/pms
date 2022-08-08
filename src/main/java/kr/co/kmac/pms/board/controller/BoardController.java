package kr.co.kmac.pms.board.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmac.pms.board.service.BoardService;
import kr.co.kmac.pms.common.util.SessionUtils;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/CeoTalk" , method = {RequestMethod.GET, RequestMethod.POST})
	public String CeoTalk(HttpServletRequest req, Model model,@RequestParam(required = false) String keyword,@RequestParam(required = false) String pagingPage,@RequestParam(required = false) String keyfield) {
		Map<String, Object> map = new HashMap<String, Object>();
		String SSN = SessionUtils.getSsn();
		map.put("SSN", SSN);
		map.put("BBSID", "CEOTALK");
		map.put("keyword", keyword);
		map.put("keyfield", keyfield);
		
		if(pagingPage == null || pagingPage.equals("")) {
			pagingPage = "1";
		}
		map.put("pagingPage", pagingPage);
		System.out.println("CeoTalk ==>"+map);
		List<Map<String, Object>> CeoTalkList = boardService.getCeoTalkList(map);
		Map<String, Object> getBoardTotalCNT = boardService.getBoardTotalCNT(map);
		model.addAttribute("keyword",keyword);
		model.addAttribute("keyfield",keyfield);
		model.addAttribute("CeoTalkList",CeoTalkList);
		model.addAttribute("getBoardTotalCNT",getBoardTotalCNT);
		model.addAttribute("pagingPage",pagingPage);
		return "/board/CeoTalk";
	}
	
	@GetMapping("/CeoTalkWrite")
	public String CeoTalkWrite(HttpServletRequest req, Model model, @RequestParam(required = false) String SEQ, @RequestParam(required = false) String saveMode) {
		String SSN = SessionUtils.getSsn();
		Map<String, Object> CeoTalkDetail = null;
		if(saveMode.equals("UPDATE")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SEQ", SEQ);
			map.put("saveMode", saveMode);
			map.put("SSN", SSN);
			CeoTalkDetail = boardService.getCeoTalkDetail(map);
		}
		model.addAttribute("CeoTalkDetail",CeoTalkDetail);
		model.addAttribute("saveMode",saveMode);
		return "/board/CeoTalkWrite";
	}
	
	@GetMapping("/CeoTalkDetail")
	public String CeoTalkDetail(HttpServletRequest req, Model model, @RequestParam String SEQ) {
		System.out.println("CeoTalkDetail ===>"+SEQ);
		Map<String, Object> map = new HashMap<String, Object>();
		String SSN = SessionUtils.getSsn();
		map.put("SSN", SSN);
		map.put("SEQ", SEQ);
		Map<String, Object> CeoTalkDetail = boardService.getCeoTalkDetail(map);
		List<Map<String, Object>> CeoTalkComment = boardService.getCeoTalkComment(map);
		Map<String, Object> CeoBoardTotalCommentCNT = boardService.getCeoBoardTotalCommentCNT(map);
		model.addAttribute("CeoTalkDetail",CeoTalkDetail);
		model.addAttribute("CeoTalkComment",CeoTalkComment);
		model.addAttribute("CeoBoardTotalCommentCNT",CeoBoardTotalCommentCNT);
		return "/board/CeoTalkDetail";
	}
	
	@PostMapping("/doCeoTalkWrite")
	@ResponseBody
	public ResponseEntity<?> CeoTalkDoWrite(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		map.put("SSN", SessionUtils.getSsn());
		System.out.println("boardWrite ==>"+map);
		String saveMode =  (String)map.get("saveMode");
		int Resultmap = 0;
		if(saveMode.equals("INSERT")) {
			Resultmap = boardService.doCeoTalkWrite(map);
		}
		if(saveMode.equals("UPDATE")) {
			Resultmap = boardService.doCeoTalkUpdate(map);
		}
		
		System.out.println("resultmap ===>"+Resultmap);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/doCeoTalkCommentWrite")
	@ResponseBody
	public ResponseEntity<?> CeoTalkCommentWrite(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		map.put("SSN", SessionUtils.getSsn());
		System.out.println("boardWrite ==>"+map);
		int Resultmap = boardService.doCeoTalkCommentWrite(map);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	
	@PostMapping("/doDeleteBoard")
	@ResponseBody
	public ResponseEntity<?> doDeleteBoard(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		map.put("SSN", SessionUtils.getSsn());
		System.out.println("boardWrite ==>"+map);
		int Resultmap = boardService.doDeleteBoard(map);
		System.out.println("resultmap ===>"+Resultmap);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
	@PostMapping("/deleteCeoTalkComment")
	@ResponseBody
	public ResponseEntity<?> deleteCeoTalkComment(HttpServletRequest req,@RequestBody Map<String, Object> map, Model model) {
		map.put("SSN", SessionUtils.getSsn());
		System.out.println("deleteCeoTalkComment ==>"+map);
		int Resultmap = boardService.deleteCeoTalkComment(map);
		System.out.println("resultmap ===>"+Resultmap);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(Resultmap, headers, HttpStatus.OK);
	}
}
