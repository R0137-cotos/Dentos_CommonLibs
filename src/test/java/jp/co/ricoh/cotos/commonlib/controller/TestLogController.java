package jp.co.ricoh.cotos.commonlib.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@Data
@RestController
@RequestMapping("/test/api")
public class TestLogController {

	@RequestMapping(method = RequestMethod.GET, path = "/log")
	public String get() {
		return "test";
	}
}
