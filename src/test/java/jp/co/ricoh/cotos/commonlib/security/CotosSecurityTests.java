package jp.co.ricoh.cotos.commonlib.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.entity.master.DummyUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.SuperUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.Domain;
import jp.co.ricoh.cotos.commonlib.repository.master.DummyUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.SuperUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.UrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "test.context.id = CotosSecurityTests")
public class CotosSecurityTests {

	public String momAuth = "NO_AUTHORITIES";

	private static final String WITHIN_PERIOD_HAS_MOM_AUTH_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb21BdXRoIjoie1wiMDNcIjp7XCIyMjEwXCI6XCIwMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI1MFwifSxcIjA1XCI6e1wiMjIxMFwiOlwiMDBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNTBcIn0sXCIwN1wiOntcIjIyMTBcIjpcIjAwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIzMFwiOlwiMTBcIixcIjIyMDBcIjpcIjUwXCJ9LFwiMDFcIjp7XCIyMjEwXCI6XCIwMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI1MFwifSxcIjA0XCI6e1wiMjIxMFwiOlwiMDBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNTBcIn0sXCIwNlwiOntcIjIyMTBcIjpcIjAwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIzMFwiOlwiMTBcIixcIjIyMDBcIjpcIjUwXCJ9LFwiMDJcIjp7XCIyMjEwXCI6XCIwMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI1MFwifX0iLCJvcmlnaW4iOiJodHRwczovL2Rldi5jb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjAxMTI1IiwibW9tRW1wSWQiOiIwMDIyOTc0NiIsImV4cCI6MjUzNDAyMjY4Mzk5LCJhcHBsaWNhdGlvbklkIjoiY290b3NfZGV2In0.DVREQfy-8H2hOAX44ktBfi8IVKB45I43dinEN_a8I5E";

	private static final String WITHIN_PERIOD_MOM_AUTH_IS_NO_AUTHORITIES_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb21BdXRoIjoiTk9fQVVUSE9SSVRJRVMiLCJvcmlnaW4iOiJodHRwczovL2Rldi5jb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjAxMTI1IiwibW9tRW1wSWQiOiIwMDIyOTc0NiIsImV4cCI6MjUzNDAyMjY4Mzk5LCJhcHBsaWNhdGlvbklkIjoiY290b3NfZGV2In0.U3dw2g8fW4491FfQ4Tzo-ekEe73ukP2Deaxz9tw1vyw";

	private static final String WITHIN_PERIOD_MOM_AUTH_IS_NULL_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJodHRwczovL2Rldi5jb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjAxMTI1IiwibW9tRW1wSWQiOiIwMDIyOTc0NiIsImV4cCI6MjUzNDAyMjY4Mzk5LCJhcHBsaWNhdGlvbklkIjoiY290b3NfZGV2In0.OT-pRq6L-LfZOU_yaec7GhhTasROqt4qN1PWzIrntNk";

	private static final String WITHIN_PERIOD_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InNpZCIsIm1vbUVtcElkIjoibWlkIiwiZXhwIjoyNTM0MDIyNjgzOTksImFwcGxpY2F0aW9uSWQiOiJjb3Rvc19kZXYifQ.qJBFsMJFZcLdF7jWwEafZSOQfmL1EqPVDcRuz6WvsCI";

	private static final String WITHIN_PERIOD_JWT_SUPER_USER = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb21BdXRoIjoie1wiMDZcIjp7XCIyMjMwXCI6XCIxMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMTBcIjpcIjAwXCIsXCIyMjAwXCI6XCI3MFwifSxcIjA1XCI6e1wiMjIzMFwiOlwiMTBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjEwXCI6XCIwMFwiLFwiMjIwMFwiOlwiNzBcIn0sXCIwMlwiOntcIjIyMzBcIjpcIjEwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIxMFwiOlwiMDBcIixcIjIyMDBcIjpcIjcwXCJ9LFwiMDFcIjp7XCIyMjMwXCI6XCIxMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMTBcIjpcIjAwXCIsXCIyMjAwXCI6XCI3MFwifSxcIjAzXCI6e1wiMjIzMFwiOlwiMTBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjEwXCI6XCIwMFwiLFwiMjIwMFwiOlwiNzBcIn0sXCIwNFwiOntcIjIyMzBcIjpcIjEwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIxMFwiOlwiMDBcIixcIjIyMDBcIjpcIjcwXCJ9LFwiMDdcIjp7XCIyMjMwXCI6XCIxMFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMTBcIjpcIjAwXCIsXCIyMjAwXCI6XCI3MFwifX0iLCJvcmlnaW4iOiJodHRwczovL2Rldi5jb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjkwMTE0OSIsIm1vbUVtcElkIjoiMDA1MDA3ODQiLCJleHAiOjI1MzQwMjI2ODM5OSwiYXBwbGljYXRpb25JZCI6ImNvdG9zX2RldiJ9.laSviNxIvxCQnOk2lLLtVtwmvlmYR6K8Nvgq34ZHsyY";

	private static final String WITHOUT_PERIOD_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb21BdXRoIjoie1wiMDNcIjp7XCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNzBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjEwXCI6XCIwMFwifSxcIjAyXCI6e1wiMjIzMFwiOlwiMTBcIixcIjIyMDBcIjpcIjcwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIxMFwiOlwiMDBcIn0sXCIwNVwiOntcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI3MFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMTBcIjpcIjAwXCJ9LFwiMDdcIjp7XCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNzBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjEwXCI6XCIwMFwifSxcIjAxXCI6e1wiMjIzMFwiOlwiMTBcIixcIjIyMDBcIjpcIjcwXCIsXCIyMjIwXCI6XCIwMFwiLFwiMjIxMFwiOlwiMDBcIn0sXCIwNlwiOntcIjIyMzBcIjpcIjEwXCIsXCIyMjAwXCI6XCI3MFwiLFwiMjIyMFwiOlwiMDBcIixcIjIyMTBcIjpcIjAwXCJ9LFwiMDRcIjp7XCIyMjMwXCI6XCIxMFwiLFwiMjIwMFwiOlwiNzBcIixcIjIyMjBcIjpcIjAwXCIsXCIyMjEwXCI6XCIwMFwifX0iLCJvcmlnaW4iOiJodHRwczovL2Rldi5jb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InUwMjkwMTE0OSIsIm1vbUVtcElkIjoiMDA1MDA3ODQiLCJleHAiOjI1MzQwMjI2OCwiYXBwbGljYXRpb25JZCI6ImNvdG9zX2RldiJ9.u2tE3LF_iaSJv5mIu870k7VKmq7hkIPMYY8oa72njOc";

	private static final String FALSIFICATION_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InNpZCIsIm1vbUVtcElkIjoibWlkIiwiZXhwIjoyNTM0MDIyNjgzOTksImFwcGxpY2F0aW9uSWQiOiJjb3Rvc19kZXYifQA.qJBFsMJFZcLdF7jWwEafZSOQfmL1EqPVDcRuz6WvsCI";

	@SpyBean
	MomAuthorityService momAuthorityService;

	@Autowired
	UrlAuthMasterRepository urlAuthMasterRepository;

	@Autowired
	SuperUserMasterRepository superUserMasterRepository;

	@Autowired
	MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	DummyUserMasterRepository dummyUserMasterRepository;

	@LocalServerPort
	private int port;

	static ConfigurableApplicationContext context;

	private static boolean dataLoaded = false;

	private String loadTopURL() {
		return "http://localhost:" + port + "/";
	}

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		if (!dataLoaded) {
			context.getBean(DBConfig.class).clearData();
			context.getBean(DBConfig.class).initTargetTestData("repository/master/superUserMaster.sql");
			context.getBean(DBConfig.class).initTargetTestData("repository/master/urlAuthMaster.sql");
			context.getBean(DBConfig.class).initTargetTestData("repository/master/dummyUserMaster.sql");
			dataLoaded = true;
		}
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void 認証_トークンなしGET() {
		RestTemplate rest = initRest(null);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1", String.class);
			Assert.fail("正常終了しない");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	public void 認証_トークンなしPOST() {
		RestTemplate rest = initRest(null);
		try {
			rest.postForEntity(loadTopURL() + "test/api/test", null, String.class);
			Assert.fail("正常終了しない");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認証_トークンあり_オリジンなし_正常_通常ユーザー() throws Exception {

		RestTemplate rest = initRest(WITHIN_PERIOD_HAS_MOM_AUTH_JWT);
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCode().value());
		Assert.assertEquals("正常終了", "u0201125,00229746,https://dev.cotos.ricoh.co.jp,cotos_dev," + WITHIN_PERIOD_HAS_MOM_AUTH_JWT + ",false,true", response.getBody());
	}

	@Test
	@Transactional
	public void 認証_トークンあり_オリジンなし_正常_スーパーユーザー() throws Exception {

		RestTemplate rest = initRest(WITHIN_PERIOD_JWT_SUPER_USER);
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCode().value());
		Assert.assertEquals("正常終了", "u02901149,00500784,https://dev.cotos.ricoh.co.jp,cotos_dev," + WITHIN_PERIOD_JWT_SUPER_USER + ",true,true", response.getBody());
	}

	@Test
	@Transactional
	public void 認証_トークンあり_オリジンなし_正常_通常ユーザー_MoM権限取得() throws Exception {

		RestTemplate rest = initRest(WITHIN_PERIOD_MOM_AUTH_IS_NULL_JWT);
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCode().value());
		Assert.assertEquals("正常終了", "u0201125,00229746,https://dev.cotos.ricoh.co.jp,cotos_dev," + WITHIN_PERIOD_MOM_AUTH_IS_NULL_JWT + ",false,true", response.getBody());
	}

	@Test
	@Transactional
	public void 認証_トークンあり_異常_有効期限切れ() throws Exception {
		RestTemplate rest = initRest(WITHOUT_PERIOD_JWT);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認証_トークンあり_異常_改竄() throws Exception {
		RestTemplate rest = initRest(FALSIFICATION_JWT);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認証_トークンあり_異常_MoM権限がnull() throws Exception {
		RestTemplate rest = initRest(FALSIFICATION_JWT);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認証_トークンあり_異常_MoM権限無し() throws Exception {
		RestTemplate rest = initRest(WITHIN_PERIOD_JWT);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認証_トークンあり_異常_momAuthがNO_AUTHORITIES() throws Exception {
		RestTemplate rest = initRest(WITHIN_PERIOD_MOM_AUTH_IS_NO_AUTHORITIES_JWT);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認可_許可_GET() throws Exception {

		// MoM権限マップをMockにより差し替え
		Mockito.doReturn(new HashMap<ActionDiv, Map<AuthDiv, AuthLevel>>()).when(momAuthorityService).searchAllMomAuthorities(Mockito.anyString());

		RestTemplate rest = initRest(WITHIN_PERIOD_HAS_MOM_AUTH_JWT);
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCode().value());
		Assert.assertEquals("正常終了", "u0201125,00229746,https://dev.cotos.ricoh.co.jp,cotos_dev," + WITHIN_PERIOD_HAS_MOM_AUTH_JWT + ",false,true", response.getBody());
	}

	@Test
	@Transactional
	public void 認可_拒否_GET() throws Exception {

		// MoM権限マップをMockにより差し替え
		Mockito.doReturn(new HashMap<ActionDiv, Map<AuthDiv, AuthLevel>>()).when(momAuthorityService).searchAllMomAuthorities(Mockito.anyString());

		RestTemplate rest = initRest(WITHIN_PERIOD_HAS_MOM_AUTH_JWT);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=false&hasBody=false", String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("認可拒否されること", 403, e.getStatusCode().value());
		}
	}

	@Test
	@Transactional
	public void 認可_許可_POST() throws Exception {

		// MoM権限マップをMockにより差し替え
		Mockito.doReturn(new HashMap<ActionDiv, Map<AuthDiv, AuthLevel>>()).when(momAuthorityService).searchAllMomAuthorities(Mockito.anyString());

		RestTemplate rest = initRest(WITHIN_PERIOD_HAS_MOM_AUTH_JWT);

		TestEntity entity = new TestEntity();
		entity.setTest("test");

		ResponseEntity<String> response = rest.postForEntity(loadTopURL() + "test/api/test?isSuccess=true&hasBody=true", entity, String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCode().value());
		Assert.assertEquals("正常終了", "u0201125,00229746,https://dev.cotos.ricoh.co.jp,cotos_dev," + WITHIN_PERIOD_HAS_MOM_AUTH_JWT + ",false,true", response.getBody());
	}

	@Test
	@Transactional
	public void 認可_拒否_POST() throws Exception {

		// MoM権限マップをMockにより差し替え
		Mockito.doReturn(new HashMap<ActionDiv, Map<AuthDiv, AuthLevel>>()).when(momAuthorityService).searchAllMomAuthorities(Mockito.anyString());

		RestTemplate rest = initRest(WITHIN_PERIOD_HAS_MOM_AUTH_JWT);
		TestEntity entity = new TestEntity();
		entity.setTest("test");
		try {
			rest.postForEntity(loadTopURL() + "test/api/test?isSuccess=false&hasBody=true", entity, String.class);
			Assert.fail("正常終了");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("認可拒否されること", 403, e.getStatusCode().value());
		}
	}

	@Test
	public void 指定されたURLには未認証でアクセス可能なこと() {
		RestTemplate rest = initRest(null);
		val response = rest.getForEntity(loadTopURL() + "test/api/swagger-ui.html", String.class);
		Assert.assertEquals("アクセス可能であること", 200, response.getStatusCode().value());
		Assert.assertEquals("コンテンツが取得できていること", context.getBean(TestSecurityController.class).getSwaggerBody(), response.getBody());
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.すべて)
	@Transactional
	public void 正常_MoM権限_編集_すべて() throws Exception {

		Mockito.doReturn(AuthLevel.すべて).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.東西)
	@Transactional
	public void 正常_MoM権限_編集_東西() throws Exception {

		Mockito.doReturn(AuthLevel.東西).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自顧客)
	@Transactional
	public void 正常_MoM権限_編集_自顧客() throws Exception {

		Mockito.doReturn(AuthLevel.自顧客).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00229692").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	@Transactional
	public void 正常_MoM権限_編集_配下() throws Exception {

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00488631").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自社)
	@Transactional
	public void 正常_MoM権限_編集_自社() throws Exception {

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00220309").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 正常_MoM権限_編集_地域() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00673662").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00469821").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 正常_MoM権限_編集_地域_退職者混在() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00673662").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("99999999").orElse(null));
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00469821").orElse(null));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 正常_MoM権限_承認_直接指定() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setManualApprover(true);

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.照会, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 正常_MoM権限_参照_承認者() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setManualApprover(true);

		List<MvEmployeeMaster> approverList = new ArrayList<>();
		MvEmployeeMaster approver1 = new MvEmployeeMaster();
		approver1.setMomEmployeeId("00220552");
		approverList.add(approver1);
		authParam.setApproverMvEmployeeMasterList(approverList);

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.照会, AuthDiv.見積_契約_手配, AccessType.参照);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.照会, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 正常_MoM権限_編集_次回承認者() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setManualApprover(true);

		MvEmployeeMaster nextApprover = new MvEmployeeMaster();
		nextApprover.setMomEmployeeId("00220552");
		authParam.setNextApproverMvEmployeeMaster(nextApprover);

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.照会, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.照会, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 正常_MoM権限_編集_次回代理承認者() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00500784").get());
		authParam.setManualApprover(true);

		MvEmployeeMaster nextApprover = new MvEmployeeMaster();
		nextApprover.setMomEmployeeId("00220552");
		authParam.setNextApproverMvEmployeeMaster(nextApprover);

		MvEmployeeMaster nextSubApprover = new MvEmployeeMaster();
		nextApprover.setMomEmployeeId("00500784");
		authParam.setNextSubApproverMvEmployeeMaster(nextSubApprover);

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.照会, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.照会, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 正常_MoM権限_編集_前回承認者() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());

		MvEmployeeMaster prevApprover = new MvEmployeeMaster();
		prevApprover.setMomEmployeeId("00220552");
		authParam.setPrevApproverMvEmployeeMaster(prevApprover);

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.照会, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.照会, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 正常_MoM権限_編集_前回代理承認者() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00500784").get());

		MvEmployeeMaster prevApprover = new MvEmployeeMaster();
		prevApprover.setMomEmployeeId("00220552");
		authParam.setPrevApproverMvEmployeeMaster(prevApprover);

		MvEmployeeMaster prevSubApprover = new MvEmployeeMaster();
		prevSubApprover.setMomEmployeeId("00500784");
		authParam.setPrevSubApproverMvEmployeeMaster(prevSubApprover);

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.照会, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自顧客)
	@Transactional
	public void 異常_MoM権限_編集_自顧客() throws Exception {

		Mockito.doReturn(AuthLevel.自顧客).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00220309").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	@Transactional
	public void 異常_MoM権限_編集_配下() throws Exception {

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00220552").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限が無いこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自社)
	@Transactional
	public void 異常_MoM権限_編集_自社() throws Exception {

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00623100").orElse(null));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限が無いこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 異常_MoM権限_編集_地域() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findById("00623100").orElse(null));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限が無いこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 異常_MoM権限_編集_不可() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.すべて)
	@Transactional
	public void 正常_MoM権限_承認_すべて() throws Exception {

		Mockito.doReturn(AuthLevel.すべて).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.東西)
	@Transactional
	public void 正常_MoM権限_承認_東西() throws Exception {

		Mockito.doReturn(AuthLevel.東西).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	@Transactional
	public void 正常_MoM権限_承認_配下() throws Exception {

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00231268").get());
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("01732221").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自社)
	@Transactional
	public void 正常_MoM権限_承認_自社() throws Exception {

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229746").get());
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("00231268").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 正常_MoM権限_承認_地域() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00231268").get());
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229746").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 正常_MoM権限_承認_代理承認者() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setNextSubApproverMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.不可)
	@Transactional
	public void 異常_MoM権限_承認_不可() throws Exception {

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自顧客)
	@Transactional
	public void 異常_MoM権限_承認_自顧客() throws Exception {

		Mockito.doReturn(AuthLevel.自顧客).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	@Transactional
	public void 異常_MoM権限_承認_配下() throws Exception {

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220309").get());
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("00229692").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自社)
	@Transactional
	public void 異常_MoM権限_承認_自社() throws Exception {

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("00231268").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 異常_MoM権限_承認_地域() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00220552").get());
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("00231268").get());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	@Transactional
	public void 異常_MoM権限_承認_地域_退職者() throws Exception {

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findById("00623100").orElse(null));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findById("99999999").orElse(null));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配)
	@Transactional
	public void 正常_MoM権限を取得できること() throws Exception {

		AuthLevel result = momAuthorityService.searchMomAuthority("u0200757", ActionDiv.更新, AuthDiv.見積_契約_手配);
		Assert.assertEquals("正常にMoM権限情報を取得できること", AuthLevel.自社, result);
	}

	@Test
	@Transactional
	public void 正常_MoM権限マップを取得できること() throws Exception {

		Map<ActionDiv, Map<AuthDiv, AuthLevel>> result = momAuthorityService.searchAllMomAuthorities("u0200757");
		Assert.assertNotNull("正常にMoM権限マップを取得できること", result);
	}

	@Test
	@Transactional
	public void 正常_MoM権限マップを取得できないこと() throws Exception {

		Map<ActionDiv, Map<AuthDiv, AuthLevel>> result = momAuthorityService.searchAllMomAuthorities("test");
		Assert.assertNull("正常にMoM権限マップを取得できないこと", result);
	}

	@Test
	@Transactional
	public void 正常_URL権限種別マスターを取得できること() throws Exception {

		List<UrlAuthMaster> result = urlAuthMasterRepository.findByIdMethodAndIdDomainOrderByIdUrlPatternAsc(HttpMethod.GET, Domain.estimation);

		Assert.assertEquals("正常に取得できること", 1, result.size());
	}

	@Test
	@Transactional
	public void 正常_スーパーユーザーマスターを取得できること() throws Exception {

		SuperUserMaster result = superUserMasterRepository.findById(1L).get();

		Assert.assertEquals("正常に取得できること", "MOM_EMPLOYEE_ID", result.getUserId());
	}

	@Test
	@Transactional
	public void 正常_ダミーユーザーマスターを取得できること() throws Exception {

		DummyUserMaster result = dummyUserMasterRepository.findById(1L).get();

		Assert.assertEquals("正常に取得できること", "COTOS_BATCH_USER", result.getUserId());
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
					request.getHeaders().add(headersProperties.getAuthorization(), "Bearer " + header);
					System.out.println(request.getHeaders());
					System.out.println("initRest End");
					return execution.execute(request, body);
				}
			}).stream()).collect(Collectors.toList()));
		}
		return rest;
	}
}
