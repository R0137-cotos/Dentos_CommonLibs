package jp.co.ricoh.cotos.commonlib.fileupdownload;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.fileupdownload.FileUpDownload;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestFileUpDownload {

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
			context.stop();
		}
	}

	@Test
	@Transactional
	public void ファイルアップロード_ダウンロード() throws Exception {

		アップロードディレクトリファイル削除();

		String fileNm = "testFile1.xlsx";
		String retFileNm = fileUpDownload.fileUpload(ファイル情報作成(fileNm));
		ファイルダウンロード(fileNm);
		Assert.assertEquals("作成されたファイルパスが正しいこと", ".\\build\\testTemp\\testFile1.xlsx", retFileNm);
	}

	@Test
	@Transactional
	public void ファイル削除() throws Exception {

		アップロードディレクトリファイル削除();

		File file = ファイルコピー("/src/test/resources/attachmentFiles/testFile1.xlsx", "1_testFile1_copy.xlsx");
		fileUpDownload.deleteFile("1_testFile1_copy.xlsx");
		Assert.assertTrue("ファイルが存在しないこと", !file.exists());
	}

	@Test
	@Transactional
	public void ファイルアップロードエラー() throws Exception {
		try {
			fileUpDownload.fileUpload(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00104", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象のファイル情報が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		try {
			fileUpDownload.fileUpload(ファイル情報作成("testFile2.xlsx"));
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00106", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象ファイルのファイルサイズが最大サイズを超えています。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		try {
			fileUpDownload.fileUpload(ファイル情報作成("filename.aaa"));
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00105", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象ファイルの拡張子が設定可能な拡張子ではありません。", e.getErrorInfoList().get(0).getErrorMessage());
		}

		try {
			fileUpDownload.fileUpload(ファイル情報作成("abcdefghijk.txt"));
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00106", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "アップロード対象ファイルのファイル名サイズが最大サイズを超えています。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	@Transactional
	public void ファイルダウンロードエラー() throws Exception {
		try {
			fileUpDownload.downloadFile("not_found.xlsx");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "指定されたファイルが存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	@Transactional
	public void ファイル削除エラー() throws Exception {
		try {
			fileUpDownload.deleteFile("not_found.xlsx");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "指定されたファイルが存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
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
		return new MockMultipartFile(file.getName(), file.getName(), "multipart/form-data", stream);
	}

	private void ファイルダウンロード(String compareFileNm) {
		String path = new File(".").getAbsoluteFile().getParent();
		File compareFile = new File(path + "/src/test/resources/attachmentFiles/" + compareFileNm);
		try {
			InputStream stream = fileUpDownload.downloadFile(compareFileNm).getBody();
			File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/output_" + compareFileNm);
			try (OutputStream out = Files.newOutputStream(file.toPath())) {
				StreamUtils.copy(stream, out);
			}
			Assert.assertTrue("ファイル内容が一致していること", Arrays.equals(Files.readAllBytes(file.toPath()), Files.readAllBytes(compareFile.toPath())));

			// 作成した一時ファイルを削除
			file.delete();
		} catch (Exception e) {
			Assert.fail("異常終了した");
		}
	}

	/**
	 * ファイルコピー
	 * 
	 * @param copyFileNm
	 *            コピー元ファイル
	 * @param fileNm
	 *            ファイル名
	 * @return ファイル
	 * @throws Exception
	 */
	private File ファイルコピー(String copyFileNm, String fileNm) throws Exception {
		String path = new File(".").getAbsoluteFile().getParent();
		File copyFile = new File(path + copyFileNm);
		File baseDir = new File(appProperties.getFileProperties().getUploadFileDir());
		Files.createDirectories(baseDir.toPath());
		File file = new File(baseDir, fileNm);
		try (OutputStream out = Files.newOutputStream(file.toPath())) {
			StreamUtils.copy(Files.newInputStream(copyFile.toPath()), out);
		}
		Assert.assertTrue("ファイルが存在すること", file.exists());
		return file;
	}

	private void アップロードディレクトリファイル削除() {
		File dir = new File(appProperties.getFileProperties().getUploadFileDir());
		if (null != dir.listFiles()) {
			List<File> fileList = Arrays.asList(dir.listFiles());
			fileList.stream().forEach(file -> file.delete());
		}
	}
}
