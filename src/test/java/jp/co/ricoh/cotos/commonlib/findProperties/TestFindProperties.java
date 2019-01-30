package jp.co.ricoh.cotos.commonlib.findProperties;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestFindProperties {

	@Autowired
	AppProperties appProperties;

	@Autowired
	Environment environment;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@Test
	public void プロパティ取得() {
		Assert.assertEquals("DB設定：ドライバーが正しく取得されること", "oracle.jdbc.OracleDriver", appProperties.getDatasourceProperties().getDriverClassName());
		Assert.assertEquals("DB設定：URLが正しく取得されること", "jdbc:oracle:thin:@dev-db.cotos.ricoh.co.jp:1521/pdb1", appProperties.getDatasourceProperties().getUrl());
		Assert.assertEquals("DB設定：ユーザー名が正しく取得されること", "cotos_ci_commonlibs", appProperties.getDatasourceProperties().getUsername());
		Assert.assertEquals("DB設定：パスワードが正しく取得されること", "cotos_ci_commonlibs", appProperties.getDatasourceProperties().getPassword());
		Assert.assertEquals("ファイル設定：アップロードディレクトリが正しく取得されること", "./build/testTemp", appProperties.getFileProperties().getUploadFileDir());
		Assert.assertEquals("ファイル設定：ファイル最大サイズが正しく取得されること", (Long) 616110L, appProperties.getFileProperties().getFileMaxSize());
		List<String> extension = Arrays.asList("xlsx", "txt");
		Assert.assertEquals("ファイル設定：設定可能拡張子が正しく取得されること", extension, appProperties.getFileProperties().getExtension());
		Assert.assertEquals("ファイル設定：ファイル名最大サイズが正しく取得されること", (Long) 14L, appProperties.getFileProperties().getFileNmMaxSize());
		Assert.assertEquals("検索条件:上限数が正しく取得されること", 5, appProperties.getSearchProperties().getLimitSize());
		List<String> throughURIs = Arrays.asList("/dummy", "/swagger-ui.html", "/dummy2");
		Assert.assertEquals("権限:URL情報が正しく取得されること", throughURIs, appProperties.getAuthProperties().getThroughURIs());
		Assert.assertEquals("権限:CORS設定が正しく取得されること", null, appProperties.getAuthProperties().getCorsOrigins());
		Assert.assertEquals("権限:jwt用秘密鍵が正しく取得されること", "cotosSecretKey", appProperties.getAuthProperties().getJwtProperties().getSecretKey());
		Assert.assertEquals("権限:jwt用期限切れ時間が正しく取得されること", (Long) 10L, appProperties.getAuthProperties().getJwtProperties().getTillExpired());
		Assert.assertEquals("権限:jwt用MoM社員IDが正しく取得されること", "momEmpId", appProperties.getAuthProperties().getJwtProperties().getClaimsProperties().getMomEmpId());
		Assert.assertEquals("権限:jwt用シングルユーザーIDが正しく取得されること", "singleUserId", appProperties.getAuthProperties().getJwtProperties().getClaimsProperties().getSingleUserId());
		Assert.assertEquals("権限:jwt用originが正しく取得されること", "origin", appProperties.getAuthProperties().getJwtProperties().getClaimsProperties().getOrigin());
		Assert.assertEquals("権限:ヘッダーMoM社員IDが正しく取得されること", "headers_mom_emp_id", appProperties.getAuthProperties().getHeadersProperties().getMomEmpId());
		Assert.assertEquals("権限:ヘッダーシングルユーザーIDが正しく取得されること", "headers_single_user_id", appProperties.getAuthProperties().getHeadersProperties().getSingleUserId());
		Assert.assertEquals("権限:ヘッダーアプリケーションIDが正しく取得されること", "headers_applicationId", appProperties.getAuthProperties().getHeadersProperties().getApplicationId());
		Assert.assertEquals("権限:ヘッダーパスが正しく取得されること", "headers_pass", appProperties.getAuthProperties().getHeadersProperties().getPass());
		Assert.assertEquals("権限:ヘッダー承認情報が正しく取得されること", "X-Cotos-Authorization", appProperties.getAuthProperties().getHeadersProperties().getAuthorization());
		Assert.assertEquals("権限:ヘッダー画面表示用ユーザー権限要否が正しく取得されること", "X-Cotos-Require-Disp-Authorize", appProperties.getAuthProperties().getHeadersProperties().getRequireDispAuthorize());
		Assert.assertEquals("権限:ヘッダー画面表示用ユーザー権限が正しく取得されること", "X-Cotos-Disp-Authorization", appProperties.getAuthProperties().getHeadersProperties().getDispAuthorization());
		Assert.assertEquals("権限:ヘッダーコンテントタイプが正しく取得されること", "Content-type", appProperties.getAuthProperties().getHeadersProperties().getContentType());
		Assert.assertEquals("外部参照:MoM承認権限用URLが正しく取得されること", "http://165.96.254.183:10110/jmo/services/KengenService", appProperties.getRemoteMomProperties().getUrl());
		Assert.assertEquals("外部参照:MoM承認権限用リテラルIDが正しく取得されること", "CSP", appProperties.getRemoteMomProperties().getRelatedid());
		Assert.assertEquals("filenameが取得できること", "filename", appProperties.getAuthProperties().getHeadersProperties().getFilename());
	}
}
