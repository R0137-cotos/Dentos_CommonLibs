package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;

@Entity
@Data
@Table(name = "mail_template_master")
public class MailTemplateMaster {

	@Id
	@ApiModelProperty(value = "メールテンプレートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@ApiModelProperty(value = "商品マスタID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = true, position = 3)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリ
	 */
	@ApiModelProperty(value = "処理カテゴリ", required = true, position = 4)
	private ProcessCategory processCategory;

	/**
	 * メール件名
	 */
	@ApiModelProperty(value = "メール件名", required = true, position = 5, allowableValues = "range[0,255]")
	private String mailSubject;

	/**
	 * メール本文
	 */
	@ApiModelProperty(value = "メール本文", required = true, position = 6)
	private String mailBody;

}
