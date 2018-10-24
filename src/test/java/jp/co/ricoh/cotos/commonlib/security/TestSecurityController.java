package jp.co.ricoh.cotos.commonlib.security;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationDetail;
import lombok.Data;

@Data
@RestController
@RequestMapping("/test/api")
public class TestSecurityController {

	private String swaggerBody = "swagger";

	@RequestMapping(method = RequestMethod.GET, path = "/test/{id}")
	@Transactional
	public String get() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return userInfo.getSingleUserId() + "," + userInfo.getMomEmployeeId() + "," + userInfo.getOrigin() + "," + userInfo.getJwt();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/test")
	@Transactional
	public String post() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return userInfo.getSingleUserId() + "," + userInfo.getMomEmployeeId() + "," + userInfo.getOrigin() + "," + userInfo.getJwt();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/test")
	@Transactional
	public String put() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return userInfo.getSingleUserId() + "," + userInfo.getMomEmployeeId() + "," + userInfo.getOrigin() + "," + userInfo.getJwt();
	}

	@GetMapping(path = "/swagger-ui.html")
	public String swagger() {
		return swaggerBody;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/xxx")
	public BindingResult callEntityValidation( @RequestBody @Validated EstimationDetail parameter, BindingResult result){
		
		// checkutil (BindingResult)
		
		
		
		return result;
	}
	
	
	
}
