package jp.co.ricoh.cotos.commonlib.log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.catalina.util.URLEncoder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CotosLogTests {

	@LocalServerPort
	private int port;

	static ConfigurableApplicationContext context;

	private String loadTopURL() {
		return "http://localhost:" + port + "/";
	}

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		context.stop();
	}

	@Test
	@Transactional
	public void ログ出力_認証情報あり() throws Exception {
		RestTemplate rest = initRest("シングルユーザID:MOM社員ID:鍵");
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/log?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCodeValue());
		Assert.assertEquals("正常終了", "test", response.getBody());
	}

	@Test
	@Transactional
	public void ログ出力_認証情報なし() throws Exception {
		RestTemplate rest = initRest("::");
		try {
			rest.getForEntity(loadTopURL() + "test/api/log?isSuccess=true&hasBody=false", String.class);
			Assert.fail("正常終了してしまった。");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("正常終了", 401, e.getStatusCode().value());
		}

	}

	private RestTemplate initRest(final String header) {
		RestTemplate rest = new RestTemplate();
		if (null != header) {
			rest.setInterceptors(Stream.concat(rest.getInterceptors().stream(), Arrays.asList(new ClientHttpRequestInterceptor() {
				@Override
				public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
					System.out.println("initRest Start");
					System.out.println(request.getURI());
					System.out.println(request.getMethod());
					request.getHeaders().add("Authentication", "Bearer " + new URLEncoder().encode(header, Charset.forName("UTF-8")));
					System.out.println(request.getHeaders());
					System.out.println("initRest End");
					return execution.execute(request, body);
				}
			}).stream()).collect(Collectors.toList()));
		}
		return rest;
	}
}
