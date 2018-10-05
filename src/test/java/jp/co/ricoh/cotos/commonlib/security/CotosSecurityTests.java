package jp.co.ricoh.cotos.commonlib.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.catalina.util.URLEncoder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
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

import jp.co.ricoh.cotos.commonlib.dto.parameter.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.SuperUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.Id;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ParameterType;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.SuperUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.UrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.ActionDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CotosSecurityTests {

	@SpyBean
	MomAuthorityService momAuthorityService;

	@Autowired
	UrlAuthMasterRepository urlAuthMasterRepository;

	@Autowired
	SuperUserMasterRepository superUserMasterRepository;

	@Autowired
	MvEmployeeMasterRepository mvEmployeeMasterRepository;

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

	private static boolean isH2() {
		return "org.h2.Driver".equals(context.getEnvironment().getProperty("spring.datasource.driverClassName"));
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		context.stop();
	}

	@Test
	public void 認証_鍵なしGET() {
		RestTemplate rest = initRest(null);
		try {
			rest.getForEntity(loadTopURL() + "test/api/test/1", String.class);
			Assert.fail("正常終了しない");
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("アクセス不可であること", 401, e.getStatusCode().value());
		}
	}

	@Test
	public void 認証_鍵なしPOST() {
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
	public void 認可_許可_GET() throws Exception {
		RestTemplate rest = initRest("シングルユーザID:MOM社員ID:鍵");
		ResponseEntity<String> response = rest.getForEntity(loadTopURL() + "test/api/test/1?isSuccess=true&hasBody=false", String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCodeValue());
		Assert.assertEquals("正常終了", "シングルユーザID,MOM社員ID,APIを呼ぶことが出来るアプリケーション名", response.getBody());
	}

	@Test
	@Transactional
	public void 認可_拒否_GET() throws Exception {
		RestTemplate rest = initRest("シングルユーザID:MOM社員ID:鍵");
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
		RestTemplate rest = initRest("シングルユーザID:MOM社員ID:鍵");

		TestEntity entity = new TestEntity();
		entity.setTest("test");

		ResponseEntity<String> response = rest.postForEntity(loadTopURL() + "test/api/test?isSuccess=true&hasBody=true", entity, String.class);
		Assert.assertEquals("正常終了", 200, response.getStatusCodeValue());
		Assert.assertEquals("正常終了", "シングルユーザID,MOM社員ID,APIを呼ぶことが出来るアプリケーション名", response.getBody());
	}

	@Test
	@Transactional
	public void 認可_拒否_POST() throws Exception {
		RestTemplate rest = initRest("シングルユーザID:MOM社員ID:鍵");
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
		Assert.assertEquals("アクセス可能であること", 200, response.getStatusCodeValue());
		Assert.assertEquals("コンテンツが取得できていること", context.getBean(TestSecurityController.class).getSwaggerBody(), response.getBody());
	}

	@Test
	@Transactional
	public void 正常_MoM権限_編集_すべて() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.すべて).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_編集_東西() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.東西).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_編集_自顧客() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自顧客).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_編集_配下() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00220552"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_編集_自社() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00220309"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_編集_地域() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00220552"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_編集_自顧客() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自顧客).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00220309"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_編集_配下() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00220552"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限が無いこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_編集_自社() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00623100"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限が無いこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_編集_地域() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));
		authParam.setMvEmployeeMasterList(new ArrayList<MvEmployeeMaster>());
		authParam.getMvEmployeeMasterList().add(mvEmployeeMasterRepository.findOne("00623100"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限が無いこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_編集_不可() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.編集);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_承認_すべて() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.すべて).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_承認_東西() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.東西).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_承認_配下() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00220552"));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_承認_自社() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00220552"));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限_承認_地域() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00220552"));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertTrue("対象の権限があること", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_承認_不可() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.不可).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_承認_自顧客() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自顧客).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(new MvEmployeeMaster());

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_承認_配下() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.配下).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00220309"));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_承認_自社() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.自社).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00623100"));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 異常_MoM権限_承認_地域() throws Exception {

		if (isH2())
			return;

		Mockito.doReturn(AuthLevel.地域).when(momAuthorityService).searchMomAuthority(Mockito.anyString(), Mockito.any(), Mockito.any());

		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setActorMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00623100"));
		authParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findOne("00229692"));

		boolean result = momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
		Assert.assertFalse("対象の権限がないこと", result);

		Mockito.reset(momAuthorityService);
	}

	@Test
	@Transactional
	public void 正常_MoM権限を取得できること() throws Exception {

		if (isH2())
			return;

		AuthLevel result = momAuthorityService.searchMomAuthority("u0200757", ActionDiv.更新, AuthDiv.見積_契約_手配);
		Assert.assertEquals("正常にMoM権限情報を取得できること", AuthLevel.自社, result);
	}

	@Test
	@Transactional
	public void 正常_URL権限種別マスターを取得できること() throws Exception {

		UrlAuthMaster urlAuthMaster = new UrlAuthMaster();

		// ID 設定
		Id urlAuthMasterId = new Id();
		urlAuthMasterId.setUrlPattern("/test");
		urlAuthMasterId.setMethod(HttpMethod.GET);
		urlAuthMasterId.setServiceCategory(ServiceCategory.見積);
		urlAuthMaster.setId(urlAuthMasterId);

		// 必須項目設定
		urlAuthMaster.setRequireAuthorize(1);
		urlAuthMaster.setExistsDb(1);
		urlAuthMaster.setParamType(ParameterType.path);
		urlAuthMaster.setActionDiv(ActionDiv.照会);
		urlAuthMasterRepository.save(urlAuthMaster);

		// データ取得
		List<UrlAuthMaster> result = urlAuthMasterRepository.findByIdMethodAndIdServiceCategoryOrderByIdUrlPatternAsc(urlAuthMasterId.getMethod(), urlAuthMasterId.getServiceCategory());

		Assert.assertEquals("正常に取得できること", 1, result.size());
	}

	@Test
	@Transactional
	public void 正常_スーパーユーザーマスターを取得できること() throws Exception {

		// ID 設定
		SuperUserMaster superUserMaster = new SuperUserMaster();
		superUserMaster.setMomEmployeeId("test");
		superUserMasterRepository.save(superUserMaster);

		// データ取得
		SuperUserMaster result = superUserMasterRepository.findOne(superUserMaster.getMomEmployeeId());

		Assert.assertEquals("正常に取得できること", "test", result.getMomEmployeeId());
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
