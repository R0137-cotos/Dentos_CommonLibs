package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadParameter {

	/**
	 * ファイル情報リスト
	 */
	private List<MultipartFile> multipartFileList;

	/**
	 * 添付ファイルIDリスト
	 */
	private List<Long> attachedFileIdList;

	/**
	 * ユーザーコメント
	 */
	private String userComment;
}
