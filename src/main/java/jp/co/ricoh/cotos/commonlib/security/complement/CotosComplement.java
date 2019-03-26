package jp.co.ricoh.cotos.commonlib.security.complement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller内メソッドのパラメーターに付与するためのアノテーション MoM権限によりフィルタリングされた情報を補完するために使用する
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CotosComplement {
}