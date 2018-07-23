package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Entity
@Data
@Table(name = "mail_template_master")
public class MailTemplateMaster {

	public enum ServiceCategory {

		見積, 契約, 手配;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ServiceCategory> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum ProcessCategory {

		承認依頼, 承認依頼差戻, 承認, 作業依頼, 作業依頼差戻, 作業完了, お知らせeメールアドレス登録申請完了連絡, 業務連絡, 契約業務依頼, 契約業務依頼差戻, 契約業務回答, 契約期間満了事前通知, 見積有効期限通知, サービス開始案内通知, 更新可否回答期限通知, 解約期限通知, 解約手続き漏れ防止通知, 変更結果通知, 契約自動更新通知;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ProcessCategory> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_template_master_seq")
	@SequenceGenerator(name = "mail_template_master_seq", sequenceName = "mail_template_master_seq", allocationSize = 1)
	private long id;

	/**
	 * 商品マスタ
	 */
	@ManyToOne
	private Product product;

	/**
	 * メール件名
	 */
	private String mailSubject;

	/**
	 * メール本文
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String mailBody;

	/**
	 * サービスカテゴリー
	 */
	@Enumerated(EnumType.STRING)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリー
	 */
	@Enumerated(EnumType.STRING)
	private ProcessCategory processCategory;

	/**
	 * 送信元メールアドレス
	 */
	private String sendFromMailAddress;
}
