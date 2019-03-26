package jp.co.ricoh.cotos.commonlib.security.complement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 補完対象となるエンティティ/DTOに付与するアノテーション 補完対象に関連するエンティティとリポジトリから補完元を取得する
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CotosComplementTarget {

	Class<?> entity();

	Class<?> repository();
}