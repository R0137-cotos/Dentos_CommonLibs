package jp.co.ricoh.cotos.commonlib.logic.fileupdownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.common.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@Component
public class FileUpDownload {

	@Autowired
	AttachedFileRepository attachedFileRepository;

	@Autowired
	CheckUtil checkUtil;

	@Autowired
	AppProperties appProperties;

	/**
	 * ファイルアップロード
	 * 
	 * @param file
	 *            ファイル情報
	 * @return 添付ファイル情報
	 * @throws IOException
	 */
	public AttachedFile fileUpload(MultipartFile file) throws ErrorCheckException, IOException {
		// チェック処理
		if (null == file) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileInfoNotFoundError"));
		}

		if (!existsFileSizeMaxOver(file)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileSizeOverError", new String[] { "ファイルサイズ" }));
		}

		if (!existsMatchExtension(file)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileExtensionNotMatchError"));
		}

		if (!existsFileNmSizeMaxOver(file)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileSizeOverError", new String[] { "ファイル名サイズ" }));
		}

		File baseDir = new File(appProperties.getFileProperties().getUploadFileDir());
		Files.createDirectories(baseDir.toPath());

		// 添付ファイル情報登録
		AttachedFile attachedFile = createAttachedFile(file);
		attachedFileRepository.save(attachedFile);
		attachedFile.setFilePhysicsName(attachedFile.getId() + "_" + file.getName());
		attachedFile.setSavedPath(baseDir.getPath() + "/" + attachedFile.getFilePhysicsName());
		attachedFileRepository.save(attachedFile);

		// ファイル保存
		File uploadFile = new File(baseDir, attachedFile.getFilePhysicsName());
		try (OutputStream out = Files.newOutputStream(uploadFile.toPath())) {
			StreamUtils.copy(file.getInputStream(), out);
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileUploadError", new String[] { uploadFile.getAbsolutePath() }));
		}

		return attachedFile;
	}

	/**
	 * ファイルダウンロード
	 * 
	 * <pre>
	 * ・引数のダウンロードファイル名ファイル名が指定されていない場合は、添付ファイル情報の物理ファイル名をダウンロード時のファイル名にする。
	 * </pre>
	 * 
	 * @param attachedFileId
	 *            添付ファイルID
	 * @param downloadFileNm
	 *            ダウンロードファイル名
	 * @return ファイル情報
	 * @throws IOException
	 */
	public ResponseEntity<InputStream> downloadFile(Long attachedFileId, String downloadFileNm) throws IOException {
		AttachedFile attachedFile = attachedFileRepository.findOne(attachedFileId);
		if (null == attachedFile) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileAttachedFileNotFoundError", new String[] { "ダウンロード" }));
		}

		File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + attachedFile.getFilePhysicsName());
		if (!file.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileNotFoundError", new String[] { file.getAbsolutePath() }));
		}

		// ファイルダウンロード
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", attachedFile.getContentType());
		header.setContentLength(attachedFile.getFileSize());
		String fileNm = downloadFileNm == null ? attachedFile.getFilePhysicsName() : downloadFileNm;
		header.setContentDispositionFormData("filename", fileNm);

		InputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileDownloadError", new String[] { file.getAbsolutePath() }));
		}

		return new ResponseEntity<>(stream, header, HttpStatus.OK);
	}

	/**
	 * ファイル削除
	 * 
	 * @param attachedFileId
	 *            添付ファイルID
	 * @throws IOException
	 */
	public void deleteFile(Long attachedFileId) throws ErrorCheckException, IOException {
		AttachedFile attachedFile = attachedFileRepository.findOne(attachedFileId);
		if (null == attachedFile) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileAttachedFileNotFoundError", new String[] { "削除" }));
		}

		File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + attachedFile.getFilePhysicsName());
		if (!file.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileNotFoundError", new String[] { file.getAbsolutePath() }));
		}

		attachedFileRepository.delete(attachedFile);

		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileDeleteError", new String[] { file.getAbsolutePath() }));
		}
	}

	/**
	 * ファイルサイズが最大を超えていないか確認
	 *
	 * @param multipartFile
	 *            ファイル情報
	 * @return チェック結果
	 */
	private boolean existsFileSizeMaxOver(MultipartFile multipartFile) {
		return !(multipartFile.getSize() > appProperties.getFileProperties().getFileMaxSize());
	}

	/**
	 * 拡張子が設定可能なものか確認
	 *
	 * @param multipartFile
	 *            ファイル情報
	 * @return チェック結果
	 */
	private boolean existsMatchExtension(MultipartFile multipartFile) {
		List<String> extensionList = appProperties.getFileProperties().getExtension();
		if (extensionList.isEmpty()) {
			return true;
		}

		String fileExtension = findExtension(multipartFile.getName());
		return extensionList.stream().filter(ext -> ext.equals(fileExtension)).collect(Collectors.toList()).size() > 0;
	}

	/**
	 * ファイル名サイズが最大を超えていないか確認
	 *
	 * @param multipartFile
	 *            ファイル情報
	 * @return チェック結果
	 */
	private boolean existsFileNmSizeMaxOver(MultipartFile multipartFile) {
		return !(multipartFile.getName().length() > appProperties.getFileProperties().getFileNmMaxSize());
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

	/**
	 * 添付ファイル情報生成
	 *
	 * @param multipartFile
	 *            ファイル情報
	 * @return 添付ファイル情報
	 */
	private AttachedFile createAttachedFile(MultipartFile multipartFile) {
		AttachedFile attachedFile = new AttachedFile();
		attachedFile.setId(0);
		attachedFile.setFilePhysicsName(multipartFile.getName());
		attachedFile.setFileSize(multipartFile.getSize());
		attachedFile.setContentType(multipartFile.getContentType());
		attachedFile.setSavedPath(multipartFile.getName());
		return attachedFile;
	}
}
