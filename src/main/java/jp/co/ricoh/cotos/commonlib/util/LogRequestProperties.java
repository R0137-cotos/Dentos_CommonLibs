package jp.co.ricoh.cotos.commonlib.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.log.request")
public class LogRequestProperties {

	/**
	 * APIリクエストログ出力有無
	 */
	private boolean outputLog;

	/**
	 * APIリクエストのリスト最大出力数
	 */
	private int maxCount;
}
