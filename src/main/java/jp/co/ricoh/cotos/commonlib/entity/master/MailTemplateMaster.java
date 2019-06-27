package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * メールテンプレートマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "mail_template_master")
public class MailTemplateMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_template_master_seq")
	@SequenceGenerator(name = "mail_template_master_seq", sequenceName = "mail_template_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "メールテンプレートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * サービスカテゴリ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービスカテゴリ", required = true, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 2)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "処理カテゴリ", required = true, allowableValues = "承認依頼(\"1\"), 承認依頼取消(\"2\"), 承認依頼差戻(\"3\"), 承認(\"4\"), 作業依頼(\"5\"), 作業完了(\"6\"), キャンセル手続き(\"7\"), キャンセル手続き中止(\"8\"), 解約手続き(\"9\"), 解約手続き中止(\"10\"), 問い合わせ(\"11\"), 100(お客様担当者), 101(接点店担当者), 102(母店接点店担当者)", example = "1", position = 3)
	private String processCategory;

	/**
	 * 商品グループマスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long productGrpMasterId;

	/**
	 * メール件名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "メール件名", required = true, position = 5, allowableValues = "range[0,255]")
	private String mailSubject;

	/**
	 * メール本文
	 */
	@Column(nullable = false)
	@Lob
	@ApiModelProperty(value = "メール本文", required = true, position = 6)
	private String mailBody;

	/**
	 * メール制御マスタ
	 */
	@OneToMany(mappedBy = "mailTemplateMaster")
	@ApiModelProperty(value = "メール制御マスタ", required = false, position = 7)
	private List<MailControlMaster> mailControlMasterList;

	/**
	 * バウンスメール戻り先
	 */
	@Column
	@ApiModelProperty(value = "バウンスメール戻り先", required = true, position = 8)
	private String envelopeFrom;

}
