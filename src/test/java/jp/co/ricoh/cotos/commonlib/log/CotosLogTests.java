package jp.co.ricoh.cotos.commonlib.log;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "test.context.id = CotosLogTests")
public class CotosLogTests {

	@SpyBean
	MomAuthorityService momAuthorityService;

	@Autowired
	HeadersProperties headersProperties;

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
		// MoM権限マップをMockにより差し替え
		Mockito.doReturn(new HashMap<ActionDiv, Map<AuthDiv, AuthLevel>>()).when(momAuthorityService).searchAllMomAuthorities(Mockito.anyString());

		RestTemplate rest = initRest("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb21BdXRoIjoie1wiMDNcIjp7XCIyMjEwXCI6XCIwMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI1MFwifSxcIjA1XCI6e1wiMjIxMFwiOlwiMDBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNTBcIn0sXCIwN1wiOntcIjIyMTBcIjpcIjAwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIzMFwiOlwiMTBcIixcIjIyMDBcIjpcIjUwXCJ9LFwiMDFcIjp7XCIyMjEwXCI6XCIwMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI1MFwifSxcIjA0XCI6e1wiMjIxMFwiOlwiMDBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNTBcIn0sXCIwNlwiOntcIjIyMTBcIjpcIjAwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIzMFwiOlwiMTBcIixcIjIyMDBcIjpcIjUwXCJ9LFwiMDJcIjp7XCIyMjEwXCI6XCIwMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI1MFwifX0iLCJvcmlnaW4iOiJodHRwczovL2Rldi5jb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjAxMTI1IiwibW9tRW1wSWQiOiIwMDIyOTc0NiIsImV4cCI6MjUzNDAyMjY4Mzk5LCJhcHBsaWNhdGlvbklkIjoiY290b3NfZGV2In0.DVREQfy-8H2hOAX44ktBfi8IVKB45I43dinEN_a8I5E");
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/log?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCode().value());
		Assert.assertEquals("正常終了", "test", response.getBody());
	}

	@Test
	@Transactional
	public void ログ出力_認証情報なし() throws Exception {
		RestTemplate rest = initRest("");
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
					request.getHeaders().add(headersProperties.getAuthorization(), "Bearer " + header);
					return execution.execute(request, body);
				}
			}).stream()).collect(Collectors.toList()));
		}
		return rest;
	}
}
