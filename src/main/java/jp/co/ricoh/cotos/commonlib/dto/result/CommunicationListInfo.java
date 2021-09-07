package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.WorkflowType;
import lombok.Data;

/**
 * コミュニケーションをリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class CommunicationListInfo {

	@Id
	@ApiModelProperty(value = "連番", required = true, position = 1)
	private long seqNo;

	@ApiModelProperty(value = "コミュニケーションID", required = true, position = 2)
	private long id;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = true, allowableValues = "見積(\"1\"), 契約(\"2\")", example = "1", position = 3)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリー
	 */
	@ApiModelProperty(value = "処理カテゴリー", required = true, allowableValues = "承認依頼(\"1\"), 承認依頼取消(\"2\"), 承認依頼差戻(\"3\"), 承認(\"4\"), 作業依頼(\"5\"), 作業完了(\"6\"), キャンセル手続き(\"7\"), キャンセル手続き中止(\"8\"), 解約手続き(\"9\"), 解約手続き中止(\"10\")", example = "1", position = 4)
	private ProcessCategory processCategory;

	/**
	 * 対象文書キー
	 */
	@ApiModelProperty(value = "対象文書キー<br />コミュニケーションの対象情報を一意に特定するためのキー情報を格納<br />" //
			+ "見積⇒見積ID<br />" //
			+ "契約⇒契約ID", required = true, position = 5, allowableValues = "range[0,255]") //
	private String targetDocKey;

	/**
	 * ワークフロー種別
	 */
	@ApiModelProperty(value = "ワークフロー種別", required = true, allowableValues = "承認フロー(\"1\"), タスクフロー(\"2\")", example = "1", position = 6)
	private WorkflowType workflowType;

	/**
	 * 承認対象種別
	 */
	@ApiModelProperty(value = "承認対象種別<br />" //
			+ "承認フロー⇒新規/情報変更/契約変更/キャンセル/解約/作業完了報告/契約更新<br />" //
			+ "タスクフロー⇒非承認", required = true, allowableValues = "新規(\"1\"), 情報変更(\"2\"), 契約変更(\"3\"), キャンセル(\"4\"), 解約(\"5\"), 作業完了報告(\"6\"), 非承認(\"7\"), 売上指示(\"8\"), 売上計上(\"9\"), 承認済差戻(\"10\"), 契約更新(\"11\")", example = "1", position = 7) //
	private ApprovalTargetType approvalTargetType;

	/**
	 * 対象文書画面URL
	 */
	@ApiModelProperty(value = "対象文書画面URL<br />" //
			+ "見積⇒対象文書キーで特定される見積情報画面のURL<br />" //
			+ "契約⇒対象文書キーで特定される契約情報画面のURL", required = true, position = 8, allowableValues = "range[0,1000]") //
	private String targetDocUrl;

	/**
	 * 依頼者
	 */
	@ApiModelProperty(value = "依頼者", required = true, position = 9, allowableValues = "range[0,255]") //
	private String requestOriginId;

	/**
	 * 伝達者
	 */
	@ApiModelProperty(value = "伝達者", required = true, position = 10, allowableValues = "range[0,255]") //
	private String requestFromId;

	/**
	 * 被伝達者
	 */
	@ApiModelProperty(value = "被伝達者", required = true, position = 11, allowableValues = "range[0,255]") //
	private String requestToId;

	/**
	 * 被伝達者候補
	 */
	@ApiModelProperty(value = "被伝達者候補", required = false, position = 12, allowableValues = "range[0,255]") //
	private String requestToCandidateId;

	/**
	 * 対象文書番号
	 */
	@ApiModelProperty(value = "対象文書番号<br />" //
			+ "見積⇒見積番号を設定<br />" //
			+ "契約⇒契約番号を設定", required = true, position = 13, allowableValues = "range[0,255]")
	private String targetDocNumber;

	/**
	 * 対象文書番号枝番
	 */
	@ApiModelProperty(value = "対象文書番号枝番<br />" //
			+ "見積⇒見積番号枝番を設定<br />" //
			+ "契約⇒契約番号枝番を設定", required = true, position = 14, allowableValues = "range[0,99]")
	private int targetDocBranchNumber;

	/**
	 * 顧客名
	 */
	@ApiModelProperty(value = "顧客名", required = true, position = 15, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 商品グループ名
	 */
	@ApiModelProperty(value = "商品グループ名<br />" //
			+ "商品グループマスタの商品グループ名を設定", required = false, position = 16, allowableValues = "range[0,255]") //
	private String productGrpName;

	/**
	 * 件名
	 */
	@ApiModelProperty(value = "件名<br />"
			+ "見積⇒見積の案件名を設定<br />" //
			+ "契約⇒契約の案件名を設定", required = false, position = 17, allowableValues = "range[0,255]") //
	private String title;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 18, allowableValues = "range[0,255]")
	private String communicationComment;

	/**
	 * 伝達日時
	 */
	@ApiModelProperty(value = "伝達日時", required = true, position = 19, readOnly = true)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "Asia/Tokyo")
	private Date communicatedAt;

	/**
	 * 商品グループマスタID
	 */
	@ApiModelProperty(value = "商品グループマスタID", required = false, position = 20, allowableValues = "range[0,9223372036854775807]")
	private Long productGrpMasterId;

	/**
	 * 依頼者氏名
	 */
	@ApiModelProperty(value = "依頼者氏名", required = false, position = 21, allowableValues = "range[0,255]", readOnly = true)
	private String requestOriginName;

	/**
	 * 伝達者
	 */
	@ApiModelProperty(value = "伝達者", required = false, position = 22, allowableValues = "range[0,255]", readOnly = true)
	private String requestFromName;

	/**
	 * 被伝達者
	 */
	@ApiModelProperty(value = "被伝達者", required = false, position = 23, allowableValues = "range[0,255]", readOnly = true)
	private String requestToName;

	/**
	 * 被伝達者候補
	 */
	@ApiModelProperty(value = "被伝達者候補", required = false, position = 24, allowableValues = "range[0,255]", readOnly = true)
	private String requestToCandidateName;

	/**
	 * アプリケーションID
	 */
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 25, allowableValues = "range[0,255]")
	private String appId;

	/**
	 * 見積種別／契約種別
	 */
	@ApiModelProperty(value = "見積種別／契約種別", required = false, position = 26, allowableValues = "range[0,255]")
	private String type;

	/**
	 * 所属（承認依頼者）
	 */
	@ApiModelProperty(value = "所属（承認依頼者）", required = false, position = 27, allowableValues = "range[0,255]")
	private String orgName;

	/**
	 * 事業所名
	 */
	@ApiModelProperty(value = "事業所名", required = false, position = 28, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 29, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 電力区分
	 */
	@ApiModelProperty(value = "電力区分", required = false, position = 30, allowableValues = "高圧(\"1\"), 低圧(\"2\")")
	private String voltageCategory;

	/**
	 * 契約期間From
	 */
	@ApiModelProperty(value = "契約期間From", required = false, position = 31)
	@Temporal(TemporalType.DATE)
	private Date contractYmdStart;

	/**
	 * 契約期間To
	 */
	@ApiModelProperty(value = "契約期間To", required = false, position = 32)
	@Temporal(TemporalType.DATE)
	private Date contractYmdEnd;

	/**
	 * 供給開始予定月
	 */
	@ApiModelProperty(value = "供給開始予定月", required = false, position = 33, allowableValues = "range[0,255]")
	private String supplyStartScheduledDate;

	/**
	 * 解約希望日
	 */
	@ApiModelProperty(value = "解約希望日", required = false, position = 34)
	@Temporal(TemporalType.DATE)
	private Date cancellationHopeDate;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}
