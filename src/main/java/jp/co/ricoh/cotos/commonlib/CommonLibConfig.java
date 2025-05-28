package jp.co.ricoh.cotos.commonlib;

import jakarta.persistence.EntityManager;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import lombok.extern.log4j.Log4j;

@Configuration
@Log4j
public class CommonLibConfig {
	@Bean
	public DBUtil loadDBUtil(ConfigurableApplicationContext context) {
		EntityManager entityManager = context.getBean(EntityManager.class);
		if (null == entityManager) {
			log.warn("EntityManager requires with DBUtil.");
			return null;
		}
		return new DBUtil(entityManager);
	}
}
