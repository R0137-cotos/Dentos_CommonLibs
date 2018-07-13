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

import jp.co.ricoh.cotos.commonlib.dto.parameter.FileUploadParameter;
import jp.co.ricoh.cotos.commonlib.entity.estimation.AttachedFile;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.estimation.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

/**
 * ファイルアップダウンロード共通クラス
 */
@Component
public class FileUpDownload {

	@Autowired
	AttachedFileRepository attachedFileRepository;

	@Autowired
	CheckUtil checkUtil;

	@Autowired
	AppProperties appProperties;

	public List<AttachedFile> uploadFile(FileUploadParameter parameter) throws ErrorCheckException, IOException {
		File baseDir = new File(appProperties.getFileProperties().getUploadFileDir());
		Files.createDirectories(baseDir.toPath());
		List<AttachedFile> attachedFileList = new ArrayList<>();

		parameter.getMultipartFileList().stream().forEach(multipartFile -> {
			// チェック処理
			if (null == multipartFile) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileInfoNotFoundError"));
			}

			if (!existsFileSizeMaxOver(multipartFile)) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileSizeOverError", new String[] { "ファイルサイズ" }));
			}

			if (!existsMatchExtension(multipartFile)) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileExtensionNotMatchError"));
			}

			if (!existsFileNmSizeMaxOver(multipartFile)) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileSizeOverError", new String[] { "ファイル名サイズ" }));
			}

			// 添付ファイル情報登録
			AttachedFile attachedFile = createAttachedFile(multipartFile, baseDir, parameter.getUserComment());
			attachedFileRepository.save(attachedFile);
			attachedFile.setFilePhysicsName(attachedFile.getId() + "_" + attachedFile.getFileName());
			attachedFile.setSavedPath(baseDir.getPath() + "/" + attachedFile.getFilePhysicsName());
			attachedFileRepository.save(attachedFile);
			attachedFileList.add(attachedFile);
		});

		// ファイル削除処理
		deleteFile(parameter.getAttachedFileIdList());

		// ファイルアップロード
		attachedFileList.stream().forEach(attachedFile -> {
			// ファイル保存
			File uploadFile = new File(baseDir, attachedFile.getFilePhysicsName());
			try (OutputStream out = Files.newOutputStream(uploadFile.toPath())) {
				StreamUtils.copy(attachedFile.getMultipartFile().getInputStream(), out);
			} catch (IOException e) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileUploadFailed", new String[] { uploadFile.getAbsolutePath() }));
			}
		});

		return attachedFileList;
	}

	/**
	 * ファイルダウンロード
	 * 
	 * @param attachedFileId
	 *            添付ファイルID
	 * @return
	 * @throws ErrorCheckException
	 * @throws IOException
	 */
	public ResponseEntity<InputStream> downloadFile(Long attachedFileId) throws ErrorCheckException, IOException {
		// チェック処理
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
		header.setContentDispositionFormData("filename", attachedFile.getFileName());

		InputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileDownloadFailed", new String[] { file.getAbsolutePath() }));
		}

		return new ResponseEntity<>(stream, header, HttpStatus.OK);
	}

	/**
	 * ファイル削除
	 * 
	 * @param attachedFileIdList
	 *            添付ファイルIDリスト
	 * @throws ErrorCheckException
	 */
	public void deleteFile(List<Long> attachedFileIdList) throws ErrorCheckException, IOException {
		List<String> fileNmList = new ArrayList<>();

		if (null != attachedFileIdList) {
			attachedFileIdList.stream().forEach(attachedFileId -> {
				// チェック処理
				AttachedFile attachedFile = attachedFileRepository.findOne(attachedFileId);
				if (null == attachedFile) {
					throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileAttachedFileNotFoundError", new String[] { "削除" }));
				}

				File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + attachedFile.getFilePhysicsName());
				if (!file.exists()) {
					throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileNotFoundError", new String[] { file.getAbsolutePath() }));
				}

				// 添付ファイル情報削除
				fileNmList.add(file.getName());
				attachedFileRepository.delete(attachedFile);
			});

			fileNmList.stream().forEach(fileNm -> {
				// ファイル削除
				File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + fileNm);
				try {
					Files.delete(file.toPath());
				} catch (IOException e) {
					throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileDeleteFailed", new String[] { file.getAbsolutePath() }));
				}
			});
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
	 * @param baseDir
	 *            ディレクトリ
	 * @param userComment
	 *            ユーザーコメント
	 * @return 添付ファイル情報
	 */
	private AttachedFile createAttachedFile(MultipartFile multipartFile, File baseDir, String userComment) {
		AttachedFile attachedFile = new AttachedFile();
		attachedFile.setId(0);
		attachedFile.setFileName(multipartFile.getName());
		attachedFile.setFileSize(multipartFile.getSize());
		attachedFile.setContentType(multipartFile.getContentType());
		attachedFile.setUserComment(userComment);
		attachedFile.setMultipartFile(multipartFile);
		return attachedFile;
	}
}
