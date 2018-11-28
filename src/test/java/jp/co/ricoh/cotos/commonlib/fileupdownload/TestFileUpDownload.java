package jp.co.ricoh.cotos.commonlib.fileupdownload;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

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

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.fileupdownload.FileUpDownload;
import jp.co.ricoh.cotos.commonlib.repository.common.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestFileUpDownload {

	@Autowired
	AttachedFileRepository attachedFileRepository;

	@Autowired
	FileUpDownload fileUpDownload;

	@Autowired
	AppProperties appProperties;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).initTargetTestData("sql/fileupdownload/deleteAttachedFile.sql");
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).initTargetTestData("sql/fileupdownload/deleteAttachedFile.sql");
			context.stop();
		}
	}

	@Test
	@WithMockCustomUser
	public void ファイルアップロード_ダウンロード() throws Exception {

		アップロードディレクトリファイル削除();

		String fileNm = "testFile1.xlsx";
		AttachedFile attachedFile = fileUpDownload.fileUpload(ファイル情報作成(fileNm));
		ファイルダウンロード(attachedFile.getId(), fileNm);
	}

	@Test
	@WithMockCustomUser
	public void ファイル削除() throws Exception {

		テストデータ作成();
		アップロードディレクトリファイル削除();

		File file = ファイルコピー("/src/test/resources/attachmentFiles/testFile1.xlsx", "1_testFile1_delete.xlsx");
		fileUpDownload.deleteFile(11L);
		Assert.assertTrue("添付ファイル情報が存在しないこと", !attachedFileRepository.exists(11L));
		Assert.assertTrue("ファイルが存在しないこと", !file.exists());
	}

	@Test
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
	public void ファイルダウンロードエラー() throws Exception {
		テストデータ作成();
		try {
			fileUpDownload.downloadFile(13L, "test.text");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00108", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "ダウンロード対象の添付ファイル情報が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
		try {
			fileUpDownload.downloadFile(12L, "test.text");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "指定されたファイルが存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void ファイル削除エラー() throws Exception {
		テストデータ作成();
		try {
			fileUpDownload.deleteFile(13L);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00108", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "削除対象の添付ファイル情報が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
		try {
			fileUpDownload.deleteFile(12L);
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

	private void ファイルダウンロード(Long attachedFileId, String compareFileNm) {
		String path = new File(".").getAbsoluteFile().getParent();
		File compareFile = new File(path + "/src/test/resources/attachmentFiles/" + compareFileNm);
		try {
			InputStream stream = fileUpDownload.downloadFile(attachedFileId, compareFileNm).getBody();
			File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + attachedFileId + "_output." + ファイル拡張子取得(compareFileNm));
			try (OutputStream out = Files.newOutputStream(file.toPath())) {
				StreamUtils.copy(stream, out);
			}
			Assert.assertTrue("ファイル内容が一致していること", Arrays.equals(Files.readAllBytes(file.toPath()), Files.readAllBytes(compareFile.toPath())));
			AttachedFile attachedFileDb = attachedFileRepository.findOne(attachedFileId);
			Assert.assertEquals("物理ファイル名が正しく登録されていること", attachedFileId + "_" + compareFileNm, attachedFileDb.getFilePhysicsName());

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

	/**
	 * ファイル拡張子取得
	 * 
	 * @param fileName
	 *            ファイル名
	 * @return 拡張子
	 */
	private String ファイル拡張子取得(String fileName) {
		if (null != fileName) {
			int point = fileName.lastIndexOf(".");
			if (point != -1) {
				return fileName.substring(point + 1);
			}
		}
		return null;
	}

	private void テストデータ作成() {
		context.getBean(DBConfig.class).initTargetTestData("sql/fileupdownload/testAttachedFileInsert.sql");
	}
}
