package jp.co.ricoh.cotos.commonlib.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.auth.jwt")
public class JwtProperties {

	private String secretKey;

	private Long tillExpired;

	@Autowired
	private ClaimsProperties claimsProperties;
	
	boolean activateNewAuthentication;
}
