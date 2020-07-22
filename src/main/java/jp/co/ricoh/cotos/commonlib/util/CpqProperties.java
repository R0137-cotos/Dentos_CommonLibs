package jp.co.ricoh.cotos.commonlib.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.cpq")
public class CpqProperties {
	/**
	 * CPQのログインユーザー
	 */
	String user;
	/**
	 * CPQのログインパスワード
	 */
	String password;
	/**
	 * CPQの環境情報（本番：ricoh、ステージングor開発：ricohtest1）
	 */
	String environment;
	/**
	 * CPQのドメイン情報
	 */
	String url;
	/**
	 * CPQのコマース(プロセス）情報
	 */
	String resoure;
	/**
	 * 契約更新時にコールするAPI情報
	 */
	String updateAssets;
	/**
	 * 契約更新時にコールするAPI情報
	 */
	String createReconfigUrl;
	/**
	 * 見積コピー時にコールするAPI情報
	 */
	String saveTransaction;
	/**
	 * 見積コピー時にコールするAPI情報
	 */
	String copyTransaction;

}
