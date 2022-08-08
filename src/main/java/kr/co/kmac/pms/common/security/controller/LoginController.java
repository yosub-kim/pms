package kr.co.kmac.pms.common.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class LoginController {

//	@PostMapping(value = "/login")
//	public String login(@RequestBody Map<String, Object> param, Model model) {
//		model.addAttribute("id", (String) param.get("id"));
//		model.addAttribute("pw", (String) param.get("pw"));
//		return "/login";
//	}

	@GetMapping(value = "/login_")
	public String login(@RequestParam(required = false) String id, String pw, Model model,HttpServletRequest request) {
//		if(id == null || id == "") {
//			request.getSession().invalidate();
//			return "/sessionOut";
//		}
		model.addAttribute("id", "lokal07");
		model.addAttribute("pw", "1111");
		return "/login_";
	}

	@GetMapping(value = "/afterLogin")
	public String login() {
		return "/forward";
	}

	@GetMapping(value = "/afterLogout")
	@ResponseBody
	public ResponseEntity<HashMap<String, String>> logout() {
		HashMap<String, String> hmData = new HashMap<String, String>();
		hmData.put("result", "SUCCESS");
		return new ResponseEntity<>(hmData, HttpStatus.OK);
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/afterLogout";
	}

}
