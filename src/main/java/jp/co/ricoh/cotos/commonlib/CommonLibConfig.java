package jp.co.ricoh.cotos.commonlib;

import javax.persistence.EntityManager;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import jp.co.ricoh.cotos.commonlib.util.DBUtil;
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

	/**
	 * ValidationのメッセージをUTF-8で管理します。
	 * 
	 * @return
	 */
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
		bean.setBasename("classpath:Messages");
		bean.setDefaultEncoding("UTF-8");
		return bean;
	}
}
