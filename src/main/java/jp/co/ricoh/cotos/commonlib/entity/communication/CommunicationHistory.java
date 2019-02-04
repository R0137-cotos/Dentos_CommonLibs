package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.WorkflowType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * コミュニケーション履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "communication_history")
public class CommunicationHistory extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "communication_history_seq")
	@SequenceGenerator(name = "communication_history_seq", sequenceName = "communication_history_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * サービスカテゴリ
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "サービスカテゴリ", required = true, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 2)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリー
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "処理カテゴリー", required = true, allowableValues = "承認依頼(\"1\"), 承認依頼取消(\"2\"), 承認依頼差戻(\"3\"), 承認(\"4\"), 作業依頼(\"5\"), 作業完了(\"6\"), キャンセル手続き(\"7\"), キャンセル手続き中止(\"8\"), 解約手続き(\"9\"), 解約手続き中止(\"10\")", example = "1", position = 3)
	private ProcessCategory processCategory;

	/**
	 * 対象文書キー
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "対象文書キー<br />コミュニケーションの対象情報を一意に特定するためのキー情報を格納<br />" //
			+ "見積⇒見積ID<br />" //
			+ "契約⇒契約ID<br />" //
			+ "手配⇒手配業務ID", required = true, position = 4, allowableValues = "range[0,255]") //
	private String targetDocKey;

	/**
	 * ワークフロー種別
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "ワークフロー種別", required = true, allowableValues = "承認フロー(\"1\"), タスクフロー(\"2\")", example = "1", position = 5)
	private WorkflowType workflowType;

	/**
	 * 承認対象種別
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "承認対象種別<br />" //
			+ "承認フロー⇒新規/情報変更/プラン変更/キャンセル/解約/作業完了報告<br />" //
			+ "タスクフロー⇒非承認", required = true, allowableValues = "新規(\"1\"), 情報変更(\"2\"), プラン変更(\"3\"), キャンセル(\"4\"), 解約(\"5\"), 作業完了報告(\"6\"), 非承認(\"7\")", example = "1", position = 6) //
	private ApprovalTargetType approvalTargetType;

	/**
	 * 対象文書画面URL
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 1000)
	@ApiModelProperty(value = "対象文書画面URL<br />Home画面でレコードクリック時の画面遷移先URLを格納<br />" //
			+ "見積⇒対象文書キーで特定される見積情報画面のURL<br />" //
			+ "契約⇒対象文書キーで特定される契約情報画面のURL<br />" //
			+ "手配⇒対象文書キーで特定される手配業務情報画面のURL", required = true, position = 7, allowableValues = "range[0,1000]") //
	private String targetDocUrl;

	/**
	 * 依頼者
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "依頼者<br />" //
			+ "ワークフローの起点となったユーザーのMoM社員IDを設定", required = true, position = 8, allowableValues = "range[0,255]") //
	private String requestOriginId;

	/**
	 * 伝達者
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "伝達者<br />" //
			+ "ユーザー識別子としてMoM社員IDを設定", required = true, position = 9, allowableValues = "range[0,255]") //
	private String requestFromId;

	/**
	 * 被伝達者
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "被伝達者<br />" //
			+ "ユーザー識別子としてMoM社員IDを設定", required = true, position = 10, allowableValues = "range[0,255]") //
	private String requestToId;

	/**
	 * 被伝達者候補
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "被伝達者候補<br />" //
			+ "ユーザー識別子としてMoM社員IDを設定", required = false, position = 11, allowableValues = "range[0,255]") //
	private String requestToCandidateId;

	/**
	 * 対象文書番号
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "対象文書番号<br />" //
			+ "見積⇒見積番号を設定<br />" //
			+ "契約/手配⇒契約番号を設定", required = true, position = 12, allowableValues = "range[0,255]")
	private String targetDocNumber;

	/**
	 * 対象文書番号枝番
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象文書番号枝番<br />" //
			+ "見積⇒見積番号枝番を設定<br />" //
			+ "契約/手配⇒契約番号枝番を設定", required = true, position = 13, allowableValues = "range[0,99]")
	private int targetDocBranchNumber;

	/**
	 * 顧客名
	 */
	@NotEmpty
	@Size(max = 255)
	@Column(nullable = false)
	@ApiModelProperty(value = "顧客名", required = true, position = 14, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 商品グループ名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品グループ名<br />" //
			+ "商品グループマスタの商品グループ名を設定", required = false, position = 15, allowableValues = "range[0,255]", readOnly = true) //
	private String productGrpName;

	/**
	 * 件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "件名<br />" + "見積⇒見積の案件名を設定<br />" //
			+ "契約⇒契約の案件名を設定<br />" //
			+ "手配⇒手配業務タイプマスタの手配業務タイプ名を設定", required = false, position = 16, allowableValues = "range[0,255]") //
	private String title;

	/**
	 * コメント
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "コメント", required = false, position = 17, allowableValues = "range[0,255]")
	private String communicationComment;

	/**
	 * 伝達日時
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "伝達日時", required = true, position = 18, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date communicatedAt;

	/**
	 * 商品グループマスタID
	 */
	@ApiModelProperty(value = "商品グループマスタID", required = false, position = 19, allowableValues = "range[0,9999999999999999999]")
	private Long productGrpMasterId;

	/**
	 * 依頼者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "依頼者氏名", required = false, position = 20, allowableValues = "range[0,255]", readOnly = true)
	private String requestOriginName;

	/**
	 * 伝達者
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "伝達者", required = false, position = 21, allowableValues = "range[0,255]", readOnly = true)
	private String requestFromName;

	/**
	 * 被伝達者
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "被伝達者", required = false, position = 22, allowableValues = "range[0,255]", readOnly = true)
	private String requestToName;

	/**
	 * 被伝達者候補
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "被伝達者候補", required = false, position = 23, allowableValues = "range[0,255]", readOnly = true)
	private String requestToCandidateName;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.communicatedAt = super.getCreatedAt();
	}
}
