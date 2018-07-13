package jp.co.ricoh.cotos.commonlib.fileupdownload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.dto.parameter.FileUploadParameter;
import jp.co.ricoh.cotos.commonlib.entity.estimation.AttachedFile;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.fileupdownload.FileUpDownload;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("fail")
public class TestFileUpDownloadFail {

	@Autowired
	DBUtil dbUtil;

	@Autowired
	FileUpDownload fileUpDownload;

	@Autowired
	AppProperties appProperties;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfigFileUpDownload.class).clearData();
			context.stop();
		}
	}

	private static boolean isH2() {
		return "org.h2.Driver".equals(context.getEnvironment().getProperty("spring.datasource.driverClassName"));
	}

	@Test
	@Transactional
	public void ファイルアップロード_エラー() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		// ファイル情報なし
		try {
			List<MultipartFile> multipartFileList = new ArrayList<>();
			multipartFileList.add(null);
			ファイルアップロード(multipartFileList, null, "テストコメント");
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00110", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象のファイル情報が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		// ファイルサイズ
		try {
			List<MultipartFile> multipartFileList = Arrays.asList(ファイル情報作成("testFile3.csv"));
			ファイルアップロード(multipartFileList, null, "テストコメント");
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00112", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象ファイルのファイルサイズが最大サイズを超えています。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		// 拡張子
		try {
			List<MultipartFile> multipartFileList = Arrays.asList(ファイル情報作成("filename.aaa"));
			ファイルアップロード(multipartFileList, null, "テストコメント");
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00111", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象ファイルの拡張子が設定可能な拡張子ではありません。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		// ファイル名サイズ
		try {
			List<MultipartFile> multipartFileList = Arrays.asList(ファイル情報作成("abcdefghijk.txt"));
			ファイルアップロード(multipartFileList, null, "テストコメント");
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00112", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象ファイルのファイル名サイズが最大サイズを超えています。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	@Transactional
	public void ファイルダウンロード_エラー() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();

		// 添付ファイル情報なし
		try {
			fileUpDownload.downloadFile(999L);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00113", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "ダウンロード対象の添付ファイル情報が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		// ファイル存在しない
		try {
			fileUpDownload.downloadFile(10L);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/1_output_default_.txt");
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", file.getAbsolutePath() + "が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	@Transactional
	public void ファイル削除_エラー() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();

		// 添付ファイル情報なし
		try {
			List<Long> attachedFileIdList = Arrays.asList(999L);
			fileUpDownload.deleteFile(attachedFileIdList);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00113", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "削除対象の添付ファイル情報が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		// ファイル存在しない
		try {
			List<Long> attachedFileIdList = Arrays.asList(10L);
			fileUpDownload.deleteFile(attachedFileIdList);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/1_output_default_.txt");
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", file.getAbsolutePath() + "が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	private void テストデータ作成() {
		dbUtil.execute("sql/fileupdownload/testAttachedFileInsert.sql", new HashMap<>());
	}

	/**
	 * ファイル情報作成
	 * 
	 * @param fileNm
	 *            ファイル名
	 * @return ファイル情報
	 * @throws Exception
	 */
	private MockMultipartFile ファイル情報作成(String fileNm) throws Exception {
		String path = new File(".").getAbsoluteFile().getParent();
		File file = new File(path + "/src/test/resources/attachmentFiles/" + fileNm);
		InputStream stream = Files.newInputStream(file.toPath());
		return new MockMultipartFile(file.getName(), stream);
	}

	/**
	 * ファイルアップロード
	 * 
	 * @param multipartFileList
	 *            ファイル情報リスト
	 * @param attachedFileIdList
	 *            添付ファイルIDリスト
	 * @param userComment
	 *            ユーザーコメント
	 * @return 添付ファイル情報リスト
	 * @throws Exception
	 */
	private List<AttachedFile> ファイルアップロード(List<MultipartFile> multipartFileList, List<Long> attachedFileIdList, String userComment) throws ErrorCheckException, IOException {
		FileUploadParameter parameter = new FileUploadParameter();
		parameter.setMultipartFileList(multipartFileList);
		parameter.setAttachedFileIdList(attachedFileIdList);
		parameter.setUserComment(userComment);
		return fileUpDownload.uploadFile(parameter);
	}
}
