package jp.co.ricoh.cotos.commonlib;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;

@Component
public class TestTools {

	public <T> String findNullProperties(T entity) throws Exception {
		Map<String, String> entityMap = BeanUtils.describe(entity);
		Optional<String> propertyName = BeanUtils.describe(entity).keySet().stream().filter(key -> entityMap.get(key) == null).findFirst();

		return propertyName.isPresent() ? propertyName.get() : null;
	}

	/**
	 * エンティティクラスのフィールドの設定値に null が含まれるか
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void assertColumnsNotNull(EntityBaseMaster entity) throws Exception {
		Assert.assertTrue(hasNullColumn(entity) == false);
	}
	
	public void assertColumnsNotNull(EntityBase entity) throws Exception {
		Assert.assertTrue(hasNullColumn(entity) == false);
	}

	/**
	 * エンティティクラスのフィールドの設定値に null が含まれるかどうかを判定する
	 * ただしフィールドの型がリスト、エンティティクラスだった場合、判定処理をスキップする
	 *
	 * @param entity
	 *            エンティティ
	 * @return boolean 判定結果（true：フィールドの設定値が null の項目を含む false：フィールドの設定値はすべて null
	 *         以外である）
	 * @throws Exception
	 */
	public boolean hasNullColumn(EntityBaseMaster entity) throws Exception {
		for (Field field : entity.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType() == List.class)
				continue;
			if (field.getType() == EntityBaseMaster.class)
				continue;
			if (field.get(entity) == null)
				return true;
		}
		return false;
	}
	
	public boolean hasNullColumn(EntityBase entity) throws Exception {
		for (Field field : entity.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType() == List.class)
				continue;
			if (field.getType() == EntityBase.class)
				continue;
			if (field.get(entity) == null)
				return true;
		}
		return false;
	}
}
