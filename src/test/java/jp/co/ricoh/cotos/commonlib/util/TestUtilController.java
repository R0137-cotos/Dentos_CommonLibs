package jp.co.ricoh.cotos.commonlib.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;

@RestController
@RequestMapping("/utilTest/api")
public class TestUtilController {

	@Autowired
	CheckUtil checkUtil;

	private ParamterCheckResult createParameterCheckResult(BindingResult result) {
		ParamterCheckResult paramterCheckResult = new ParamterCheckResult();
		if (result == null)
			return paramterCheckResult;
		try {
			checkUtil.notOmittedCheckEntity(result);
		} catch (ErrorCheckException ex) {
			paramterCheckResult.setErrorInfoList(ex.getErrorInfoList());
			return paramterCheckResult;
		}
		return paramterCheckResult;
	}

	private String loadTopURL(int localServerPort) {
		return "http://localhost:" + localServerPort + "/";
	}

	private RestTemplate initRest(final String header, final HeadersProperties headersProperties) {
		RestTemplate rest = new RestTemplate();
		if (null != header) {
			rest.setInterceptors(Stream.concat(rest.getInterceptors().stream(), Arrays.asList(new ClientHttpRequestInterceptor() {
				@Override
				public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
					System.out.println("initRest Start");
					System.out.println(request.getURI());
					System.out.println(request.getMethod());
					request.getHeaders().add(headersProperties.getAuthorization(), "Bearer " + header);
					System.out.println(request.getHeaders());
					System.out.println("initRest End");
					return execution.execute(request, body);
				}
			}).stream()).collect(Collectors.toList()));
		}
		return rest;
	}

	/**
	 * パラメータチェックの URL を取得
	 *
	 * @param entity
	 *            パラメータチェック対象のエンティティ
	 * @param localServerPort
	 *            ポート番号
	 * @return String パラメータチェックの URL
	 */
	public String getParamterCheckUrl(Object entity, int localServerPort) {
		final String API_ROOT_HEAD = "utilTest/api/ParameterCheck/";
		final String API_ROOT_END = "?isSuccess=true&hasBody=false";
		String entityName = entity.getClass().getSimpleName().replaceAll(".java", "");
		return loadTopURL(localServerPort) + API_ROOT_HEAD + entityName + API_ROOT_END;
	}

	public ParamterCheckResult callParameterCheck(Object entity, HeadersProperties headersProperties, int localServerPort) {
		String WITHIN_PERIOD_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjkwMTE0OSIsIm1vbUVtcElkIjoiMDA1MDA3ODQiLCJleHAiOjI1MzQwMjI2ODM5OSwiYXBwbGljYXRpb25JZCI6ImNvdG9zX2RldiJ9.CfYPwuIzMGmp1ZnCTHkzNrjd5_EXNVjIBzDXAfn6NPg";
		RestTemplate rest = initRest(WITHIN_PERIOD_JWT, headersProperties);
		ResponseEntity<ParamterCheckResult> parameterCheckResult = rest.postForEntity(getParamterCheckUrl(entity, localServerPort), entity, ParamterCheckResult.class);
		return parameterCheckResult.getBody();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/ParameterCheck/Contract")
	public ParamterCheckResult callParamterCheck(@RequestBody @Validated Contract entity, BindingResult result) {
		return createParameterCheckResult(result);
	}

}
