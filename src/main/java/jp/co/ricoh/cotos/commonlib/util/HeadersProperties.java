package jp.co.ricoh.cotos.commonlib.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.auth.headers")
public class HeadersProperties {

	private String momEmpId;

	private String singleUserId;

	private String applicationId;

	private String pass;

	private String authorization;

	private String requireDispAuthorize;

	private String dispAuthorization;
	
	private String contentType;
	
	private String filename;
}
