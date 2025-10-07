package jp.co.ricoh.cotos.commonlib.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 請求書出力用RestTemplate
 */
@Configuration
public class RestTemplateInvoice {

	private final RestTemplate restTemplate;

	public RestTemplateInvoice() {
		int timeout = 180 * 1000; // 180秒

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(timeout);
		requestFactory.setReadTimeout(timeout);

		this.restTemplate = new RestTemplate(requestFactory);
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

}
