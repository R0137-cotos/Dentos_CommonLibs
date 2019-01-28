package jp.co.ricoh.cotos.commonlib.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.auth")
public class AuthProperties {

	private List<String> throughURIs;

	private List<String> corsOrigins;

	@Autowired
	private JwtProperties jwtProperties;

	@Autowired
	private HeadersProperties headersProperties;
}
