package kr.co.kmac.pms.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kmac.pms.test.model.TestModel;

@RestController
@RequestMapping("/test/*")
public class Hello2Controller {

	@RequestMapping(method = RequestMethod.POST, path = "/rest")
	public TestModel hello() {
		TestModel entity = new TestModel();
		entity.setAttach("asdasdfasdf");
		return entity;
	}
}
