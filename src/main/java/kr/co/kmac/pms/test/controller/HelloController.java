package kr.co.kmac.pms.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kmac.pms.test.model.TestModel;

@Controller
@RequestMapping("/test/*")
public class HelloController {

	//@RequestMapping(method = RequestMethod.GET, path = "/hello")
	@GetMapping("/hello")
	public String hello() {
		TestModel entity = new TestModel();
		
		return "/test/hello";
	}
}
