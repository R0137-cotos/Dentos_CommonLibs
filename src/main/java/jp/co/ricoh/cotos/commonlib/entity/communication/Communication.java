package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.WorkflowType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * コミュニケーションを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "communication")
public class Communication extends EntityBase {

	@Id
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = true, position = 2)
	private ServiceCategory communicationCategory;

	/**
	 * 処理カテゴリー
	 */
	@ApiModelProperty(value = "処理カテゴリー", required = true, position = 3)
	private ProcessCategory processCategory;

	/**
	 * 対象文書キー
	 */
	@ApiModelProperty(value = "対象文書キー<br />コミュニケーションの対象情報を一意に特定するためのキー情報を格納<br />" //
			+ "見積⇒見積ID<br />" //
			+ "契約⇒契約ID<br />" //
			+ "手配⇒手配業務ID", required = true, position = 4, allowableValues = "range[0,255]") //
	private String targetDocKey;

	/**
	 * ワークフロー種別
	 */
	@ApiModelProperty(value = "ワークフロー種別", required = true, position = 5)
	private WorkflowType workflowType;

	/**
	 * 承認対象種別
	 */
	@ApiModelProperty(value = "承認対象種別<br />" //
			+ "承認フロー⇒新規/情報変更/プラン変更/キャンセル/解約/作業完了報告<br />" //
			+ "タスクフロー⇒非承認", required = true, position = 6) //
	private ApprovalTargetType approvalTargetType;

	/**
	 * 対象文書画面URL
	 */
	@ApiModelProperty(value = "対象文書画面URL<br />Home画面でレコードクリック時の画面遷移先URLを格納<br />" //
			+ "見積⇒対象文書キーで特定される見積情報画面のURL<br />" //
			+ "契約⇒対象文書キーで特定される契約情報画面のURL<br />" //
			+ "手配⇒対象文書キーで特定される手配業務情報画面のURL", required = true, position = 7, allowableValues = "range[0,1000]") //
	private String targetDocUrl;

	/**
	 * 依頼者
	 */
	@ApiModelProperty(value = "依頼者<br />" //
			+ "ワークフローの起点となったユーザーのMoM社員IDを設定", required = true, position = 8, allowableValues = "range[0,255]") //
	private String requestOrigin;

	/**
	 * 伝達者
	 */
	@ApiModelProperty(value = "伝達者<br />" //
			+ "ユーザー識別子としてMoM社員IDを設定", required = true, position = 9, allowableValues = "range[0,255]") //
	private String requestFrom;

	/**
	 * 被伝達者
	 */
	@ApiModelProperty(value = "被伝達者<br />" //
			+ "ユーザー識別子としてMoM社員IDを設定", required = true, position = 10, allowableValues = "range[0,255]") //
	private String requestTo;

	/**
	 * 被伝達者候補
	 */
	@ApiModelProperty(value = "被伝達者候補<br />" //
			+ "ユーザー識別子としてMoM社員IDを設定", required = false, position = 11, allowableValues = "range[0,255]") //
	private String requestToCandidate;

	/**
	 * 対象文書番号
	 */
	@ApiModelProperty(value = "対象文書番号<br />" //
			+ "見積⇒見積番号を設定<br />" //
			+ "契約/手配⇒契約番号を設定", required = true, position = 12, allowableValues = "range[0,255]") //
	@Pattern(regexp = "見積:CEYYYYMMDDNNNNN、契約/手配:CAYYYYMMDDNNNNN")
	private String targetDocNumber;

	/**
	 * 対象文書番号枝番
	 */
	@ApiModelProperty(value = "対象文書番号枝番<br />" //
			+ "見積⇒見積番号枝番を設定<br />" //
			+ "契約/手配⇒契約番号枝番を設定", required = true, position = 13, allowableValues = "range[0,99]") //
	private int targetDocBranchNumber;

	/**
	 * 顧客名
	 */
	@ApiModelProperty(value = "顧客名", required = true, position = 14, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名<br />" //
			+ "商品マスタの商品名を設定", required = true, position = 15, allowableValues = "range[0,255]") //
	private String productName;

	/**
	 * 件名
	 */
	@ApiModelProperty(value = "件名<br />"
			+ "見積⇒見積の案件名を設定<br />" //
			+ "契約⇒契約の案件名を設定<br />" //
			+ "手配⇒手配業務タイプマスタの手配業務タイプ名を設定", required = true, position = 16, allowableValues = "range[0,255]") //
	private String title;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 17, allowableValues = "range[0,255]")
	private String communicationComment;

	/**
	 * 伝達日時
	 */
	@ApiModelProperty(value = "伝達日時", required = true, position = 18, readOnly = true)
	private Date communicatedAt;
}
