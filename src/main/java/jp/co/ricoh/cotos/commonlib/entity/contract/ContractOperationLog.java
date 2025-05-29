package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ContractOperationLogListener.class)
@Data
@Table(name = "contract_operation_log")
public class ContractOperationLog extends EntityBase {

	public enum OperationLogType {
		売上開始指示, 売上計上開始, 売上計上停止, キャンセル手続き, キャンセル中止, 解約手続き, 解約確定, 解約取込, 解約手続き中止, 変更キャンセル, 変更確定, 新規作成, 帳票出力, 契約変更, 確定, 情報変更, 新規作成_情報変更, 更新, 業務依頼, 業務処理完了, 締結開始指示, 新規作成_契約変更, 自動更新, 移行データ_新規作成, 移行データ_更新, 承認済差戻, 破棄, 企業情報更新, 特殊対応_担当者変更, 特殊対応_メニュー変更, 特殊対応_単価変更
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_operation_log_seq")
	@SequenceGenerator(name = "contract_operation_log_seq", sequenceName = "contract_operation_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "操作履歴ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 操作内容
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "操作内容", required = true, position = 3, allowableValues = "range[0,1000]")
	@Enumerated(EnumType.STRING)
	private OperationLogType operation;

	/**
	 * 操作者MoM社員ID
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "操作者MoM社員ID<br/>※POST時「RJ社員情報マスタ」存在チェック実施", required = true, position = 4, allowableValues = "range[0,255]")
	private String operatorEmpId;

	/**
	 * 操作者氏名
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "操作者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String operatorName;

	/**
	 * 操作者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "操作者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String operatorOrgName;

	/**
	 * 実施日時
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@ApiModelProperty(value = "実施日時(作成時不要)", required = true, position = 7, readOnly = true)
	private Date operatedAt;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.operatedAt = super.getCreatedAt();
	}
}