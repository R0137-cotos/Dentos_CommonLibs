package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "communication")
public class Communication extends EntityBase {

	public enum CommunicationCategory {

		見積, 契約, 手配, 請求書送付, 資料送付;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<CommunicationCategory> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum ProcessCategory {

		承認依頼, 承認依頼差戻, 承認, 作業依頼, 作業依頼差戻, 作業完了, 添付メール送信, パスワードメール送信, 添付メール送信失敗, パスワードメール送信失敗, 添付メール送信完了, メール送信完了;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "communication_seq")
	@SequenceGenerator(name = "communication_seq", sequenceName = "communication_seq", allocationSize = 1)
	@ApiModelProperty(value = "コミュニケーションID", required = true, position = 1)
	private long id;

	/**
	 * 伝達者MoM社員ID
	 */
	@NotEmpty(message = "{RequestFrom}{NotEmptyError}:{RequestFrom}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@ApiModelProperty(value = "伝達者MoM社員ID", required = true, position = 2, allowableValues = "range[0,255]")
	private String requestFrom;

	/**
	 * 被伝達者MoM社員ID
	 */
	@NotEmpty(message = "{RequestTo}{NotEmptyError}:{RequestTo}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@ApiModelProperty(value = "被伝達者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String requestTo;

	/**
	 * 対象文書番号
	 */
	@NotEmpty(message = "{targetDocNumber}{NotEmptyError}:{targetDocNumber}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@ApiModelProperty(value = "対象文書番号", required = true, position = 4, allowableValues = "range[0,255]")
	private String targetDocNumber;

	/**
	 * 対象文書キー
	 */
	@NotEmpty(message = "{TargetDocKey}{NotEmptyError}:{TargetDocKey}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@ApiModelProperty(value = "対象文書キー", required = true, position = 5, allowableValues = "range[0,255]")
	private String targetDocKey;

	/**
	 * 対象文書画面URL
	 */
	@NotEmpty(message = "{TargetDocUrl}{NotEmptyError}:{TargetDocUrl}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@ApiModelProperty(value = "対象文書画面URL", required = true, position = 6, allowableValues = "range[0,255]")
	private String targetDocUrl;

	/**
	 * コミュニケーションカテゴリー
	 */
	@NotEmpty(message = "{CommunicationCategory}{NotEmptyError}:{CommunicationCategory}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "コミュニケーションカテゴリー", required = true, position = 7)
	private CommunicationCategory communicationCategory;

	/**
	 * 処理カテゴリー
	 */
	@NotEmpty(message = "{ProcessCategory}{NotEmptyError}:{ProcessCategory}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "処理カテゴリー", required = true, position = 8)
	private ProcessCategory processCategory;

	/**
	 * ステータスの設定タイミング 依頼中： 申請者が依頼ボタンを押下したタイミング 受付済： 作業者が作業受付を行ったタイミング
	 * (作業依頼のみ使用するステータス) 完了： 承認者(作業者）が依頼(作業)に対する承認(完了)ボタンを押下したタイミング
	 */
	public enum Status {

		依頼中, 受付済, 完了;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<Status> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	/**
	 * ステータス
	 */
	@NotEmpty(message = "{CommunicationStatus}{NotEmptyError}:{CommunicationStatus}{NotEmptyErrorMsg}")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "ステータス", required = true, position = 9)
	private Status status;

	/**
	 * お客様名
	 */
	@ApiModelProperty(value = "お客様名", required = false, position = 10, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 需要場所名
	 */
	@ApiModelProperty(value = "需要場所名", required = false, position = 11, allowableValues = "range[0,255]")
	private String demandName;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 12, allowableValues = "range[0,255]")
	private String communicationComment;

	/**
	 * 伝達日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "伝達日時", required = false, position = 13)
	private Date communicatedAt;

	/**
	 * タイトル
	 */
	@ApiModelProperty(value = "タイトル", required = false, position = 14, allowableValues = "range[0,255]")
	private String taskTitle;

	/**
	 * 作成日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "作成日", required = false, position = 15)
	private Date taskCreatedAt;

	/**
	 * 企業名
	 */
	@ApiModelProperty(value = "企業名", required = false, position = 16, allowableValues = "range[0,255]")
	private String taskCompanyName;

	/**
	 * 事務所名
	 */
	@ApiModelProperty(value = "事務所名", required = false, position = 17, allowableValues = "range[0,255]")
	private String taskOfficeName;

	/**
	 * 販売店名
	 */
	@ApiModelProperty(value = "販売店名", required = false, position = 18, allowableValues = "range[0,255]")
	private String taskSalesName;

	/**
	 * 担当SAのMoM社員ID
	 */
	@ApiModelProperty(value = "担当SAMoM社員ID", required = false, position = 19, allowableValues = "range[0,255]")
	private String taskPicEmpId;

	/**
	 * 追加編集者のMoM社員IDリスト
	 */
	@Transient
	@ApiModelProperty(hidden = true)
	private String[] addedEditEmpIdList;

	@PrePersist
	public void prePersist() {
		this.communicatedAt = new Date();
	}
}
