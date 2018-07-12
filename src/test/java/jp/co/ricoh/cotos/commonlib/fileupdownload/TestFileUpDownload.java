package jp.co.ricoh.cotos.commonlib.fileupdownload;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.dto.parameter.FileUploadParameter;
import jp.co.ricoh.cotos.commonlib.entity.estimation.AttachedFile;
import jp.co.ricoh.cotos.commonlib.logic.fileupdownload.FileUpDownload;
import jp.co.ricoh.cotos.commonlib.repository.estimation.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestFileUpDownload {

	@Autowired
	DBUtil dbUtil;

	@Autowired
	FileUpDownload fileUpDownload;

	@Autowired
	AttachedFileRepository attachedFileRepository;

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
	@WithMockCustomUser
	public void ファイルアップロード単一_削除ファイルなし_ダウンロード() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();
		アップロードディレクトリファイル削除();

		String fileNm = "testFile1.xlsx";
		String userComment = "テストコメント";
		List<MultipartFile> multipartFileList = Arrays.asList(ファイル情報作成(fileNm));
		List<AttachedFile> attachedFileList = ファイルアップロード(multipartFileList, null, userComment);
		ファイルダウンロード単一(attachedFileList.get(0).getId(), fileNm, userComment);
	}

	@Test
	@Transactional
	@WithMockCustomUser
	public void ファイルアップロード単一_削除ファイル単一_ダウンロード() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();
		アップロードディレクトリファイル削除();

		// ファイルコピー
		String path = new File(".").getAbsoluteFile().getParent();
		File copyFile = new File(path + "/src/test/resources/attachmentFiles/testFile1.xlsx");
		File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/1_testFile1_delete.xlsx");
		try (OutputStream out = Files.newOutputStream(file.toPath())) {
			StreamUtils.copy(Files.newInputStream(copyFile.toPath()), out);
		}
		Assert.assertTrue("ファイルが存在すること", file.exists());

		String fileNm = "testFile2.txt";
		String userComment = "テストコメント2";
		List<MultipartFile> multipartFileList = Arrays.asList(ファイル情報作成(fileNm));
		List<Long> attachedFileIdList = Arrays.asList(11L);
		List<AttachedFile> attachedFileList = ファイルアップロード(multipartFileList, attachedFileIdList, userComment);
		ファイルダウンロード単一(attachedFileList.get(0).getId(), fileNm, userComment);

		Assert.assertTrue("添付ファイル情報が存在しないこと", !attachedFileRepository.exists(3L));
		Assert.assertTrue("ファイルが存在しないこと", !file.exists());
	}

	@Test
	@Transactional
	@WithMockCustomUser
	public void ファイルアップロード複数_削除ファイル複数_ダウンロード() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();
		アップロードディレクトリファイル削除();

		// ファイルコピー
		String path = new File(".").getAbsoluteFile().getParent();
		File copyFile1 = new File(path + "/src/test/resources/attachmentFiles/testFile1.xlsx");
		File file1 = new File(appProperties.getFileProperties().getUploadFileDir() + "/2_testFile2_delete.txt");
		try (OutputStream out = Files.newOutputStream(file1.toPath())) {
			StreamUtils.copy(Files.newInputStream(copyFile1.toPath()), out);
		}
		File copyFile2 = new File(path + "/src/test/resources/attachmentFiles/testFile3.csv");
		File file2 = new File(appProperties.getFileProperties().getUploadFileDir() + "/3_testFile3_delete.csv");
		try (OutputStream out = Files.newOutputStream(file2.toPath())) {
			StreamUtils.copy(Files.newInputStream(copyFile2.toPath()), out);
		}
		Assert.assertTrue("ファイルが存在すること", file1.exists());
		Assert.assertTrue("ファイルが存在すること", file2.exists());

		String fileNm1 = "testFile3.csv";
		String fileNm2 = "testFile4.docx";
		List<MultipartFile> multipartFileList = Arrays.asList(ファイル情報作成(fileNm1), ファイル情報作成(fileNm2));
		List<Long> attachedFileIdList = Arrays.asList(12L, 13L);
		List<AttachedFile> attachedFileList = ファイルアップロード(multipartFileList, attachedFileIdList, "テストコメント3");
		ファイルダウンロード単一(attachedFileList.get(0).getId(), fileNm1, "テストコメント3");
		ファイルダウンロード単一(attachedFileList.get(1).getId(), fileNm2, "テストコメント3");

		Assert.assertTrue("添付ファイル情報が存在しないこと", !attachedFileRepository.exists(12L));
		Assert.assertTrue("ファイルが存在しないこと", !file1.exists());
		Assert.assertTrue("添付ファイル情報が存在しないこと", !attachedFileRepository.exists(13L));
		Assert.assertTrue("ファイルが存在しないこと", !file2.exists());
	}

	@Test
	@Transactional
	public void ファイル削除() throws Exception {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();
		アップロードディレクトリファイル削除();

		// ファイルコピー
		String path = new File(".").getAbsoluteFile().getParent();
		File copyFile = new File(path + "/src/test/resources/attachmentFiles/testFile1.xlsx");
		File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/1_testFile1_copy.xlsx");
		try (OutputStream out = Files.newOutputStream(file.toPath())) {
			StreamUtils.copy(Files.newInputStream(copyFile.toPath()), out);
		}
		Assert.assertTrue("ファイルが存在すること", file.exists());

		List<Long> attachedFileIdList = Arrays.asList(14L);
		fileUpDownload.deleteFile(attachedFileIdList);

		Assert.assertTrue("添付ファイル情報が存在しないこと", !attachedFileRepository.exists(6L));
		Assert.assertTrue("ファイルが存在しないこと", !file.exists());
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
		return new MockMultipartFile(file.getName(), file.getName(), "multipart/form-data", stream);
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
	private List<AttachedFile> ファイルアップロード(List<MultipartFile> multipartFileList, List<Long> attachedFileIdList, String userComment) throws Exception {
		FileUploadParameter parameter = new FileUploadParameter();
		parameter.setMultipartFileList(multipartFileList);
		parameter.setAttachedFileIdList(attachedFileIdList);
		parameter.setUserComment(userComment);
		return fileUpDownload.uploadFile(parameter);
	}

	/**
	 * ファイルダウンロード単一
	 * 
	 * @param attachedFileId
	 *            添付ファイルID
	 * @param compareFileNm
	 *            比較ファイル名
	 * @param userComment
	 *            ユーザーコメント
	 */
	private void ファイルダウンロード単一(long attachedFileId, String compareFileNm, String userComment) {
		String path = new File(".").getAbsoluteFile().getParent();
		File compareFile = new File(path + "/src/test/resources/attachmentFiles/" + compareFileNm);
		try {
			InputStream stream = fileUpDownload.downloadFile(attachedFileId).getBody();
			File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + attachedFileId + "_output." + findExtension(compareFileNm));
			try (OutputStream out = Files.newOutputStream(file.toPath())) {
				StreamUtils.copy(stream, out);
			}
			Assert.assertTrue("ファイル内容が一致していること", Arrays.equals(Files.readAllBytes(file.toPath()), Files.readAllBytes(compareFile.toPath())));
			AttachedFile attachedFileDb = attachedFileRepository.findOne(attachedFileId);
			Assert.assertEquals("ファイル名が正しく登録されていること", compareFileNm, attachedFileDb.getFileName());
			Assert.assertEquals("物理ファイル名が正しく登録されていること", attachedFileId + "_" + compareFileNm, attachedFileDb.getFilePhysicsName());
			Assert.assertEquals("ファイル名が正しく登録されていること", userComment, attachedFileDb.getUserComment());

			// 作成した一時ファイルを削除
			file.delete();
		} catch (Exception e) {
			Assert.fail("異常終了した");
		}
	}

	/**
	 * ファイル拡張子取得
	 * 
	 * @param fileName
	 *            ファイル名
	 * @return 拡張子
	 */
	private String findExtension(String fileName) {
		if (null != fileName) {
			int point = fileName.lastIndexOf(".");
			if (point != -1) {
				return fileName.substring(point + 1);
			}
		}
		return null;
	}

	private void アップロードディレクトリファイル削除() {
		File dir = new File(appProperties.getFileProperties().getUploadFileDir());
		List<File> fileList = Arrays.asList(dir.listFiles());
		fileList.stream().forEach(file -> file.delete());
	}
}
