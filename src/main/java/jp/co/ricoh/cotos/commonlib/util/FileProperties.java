package jp.co.ricoh.cotos.commonlib.util;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * ファイル
 */
@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.file")
public class FileProperties {
	/**
	 * アップロードディレクトリ
	 */
	String uploadFileDir;

	/**
	 * ファイル最大サイズ(バイト指定)
	 */
	Long fileMaxSize;

	/**
	 * 設定可能拡張子
	 */
	List<String> extension;

	/**
	 * ファイル名最大サイズ
	 */
	Long fileNmMaxSize;
}
