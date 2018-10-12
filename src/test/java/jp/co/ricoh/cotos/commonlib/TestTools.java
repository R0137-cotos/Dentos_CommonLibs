package jp.co.ricoh.cotos.commonlib;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TestTools {

	public <T> String findNullProperties(T entity) throws Exception {
		Map<String, String> entityMap = BeanUtils.describe(entity);
		Optional<String> propertyName = BeanUtils.describe(entity).keySet().stream().filter(key -> entityMap.get(key) == null).findFirst();

		return propertyName.isPresent() ? propertyName.get() : null;
	}
}
