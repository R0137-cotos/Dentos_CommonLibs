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

import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@Component
public class FileUpDownload {

	@Autowired
	CheckUtil checkUtil;

	@Autowired
	AppProperties appProperties;

	/**
	 * ファイルアップロード
	 * 
	 * @param file
	 *            ファイル情報
	 * @return ファイルパス
	 * @throws IOException
	 */
	public void fileUpload(MultipartFile file) throws ErrorCheckException, IOException {
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

		// ファイル保存
		File baseDir = new File(appProperties.getFileProperties().getUploadFileDir());
		Files.createDirectories(baseDir.toPath());
		File uploadFile = new File(baseDir, file.getName());
		try (OutputStream out = Files.newOutputStream(uploadFile.toPath())) {
			StreamUtils.copy(file.getInputStream(), out);
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileUploadError", new String[] { uploadFile.getAbsolutePath() }));
		}
	}

	/**
	 * ファイルダウンロード
	 * 
	 * @param fileName
	 *            ファイル名
	 * @return ファイル情報
	 * @throws IOException
	 */
	public ResponseEntity<InputStream> downloadFile(String fileName) throws IOException {
		File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + fileName);
		if (!file.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileNotFoundError", new String[] { file.getAbsolutePath() }));
		}

		// ファイルダウンロード
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "multipart/form-data");
		header.setContentDispositionFormData("filename", fileName);

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
	 * @param fileName
	 *            ファイル名
	 * @throws IOException
	 */
	public void deleteFile(String fileName) throws ErrorCheckException, IOException {
		File file = new File(appProperties.getFileProperties().getUploadFileDir() + "/" + fileName);
		if (!file.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "FileNotFoundError", new String[] { file.getAbsolutePath() }));
		}

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
}
