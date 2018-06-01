package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.converter.ApprovalProcessCategoryDivConverterContract;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約承認実績を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class ContractApprovalResult extends EntityBase {

	public enum ApprovalProcessCategory {

		承認依頼, 承認依頼差戻, 承認;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ApprovalProcessCategory> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_approval_result_seq")
	@SequenceGenerator(name = "contract_approval_result_seq", sequenceName = "contract_approval_result_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約承認実績ID", required = true, position = 1)
	private long id;

	/**
	 * 契約承認ルート
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "approvalRouteId")
	@JsonIgnore
	private ContractApprovalRoute contractApprovalRoute;

	/**
	 * 承認処理カテゴリー
	 */
	@Convert(converter = ApprovalProcessCategoryDivConverterContract.class)
	@ApiModelProperty(value = "承認処理カテゴリー", required = true, position = 2)
	private ApprovalProcessCategory approvalProcessCategory;

	/**
	 * 処理実施者MoM社員ID
	 */
	@ApiModelProperty(value = "処理実施者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String actualEmptxId;

	/**
	 * 処理実施者社員名
	 */
	@ApiModelProperty(value = "処理実施者社員名", required = false, position = 4, allowableValues = "range[0,255]")
	private String actualEmptxName;

	/**
	 * 処理実施者部署名
	 */
	@ApiModelProperty(value = "処理実施者部署名", required = true, position = 5, allowableValues = "range[0,255]")
	private String actualEmptxDepartmentName;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = true, position = 6, allowableValues = "range[0,255]")
	private String approvalComment;

	/**
	 * 実施日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "実施日時", required = true, position = 7)
	private Date processedAt;

	@PrePersist
	public void prePersist() {
		this.processedAt = new Date();
	}
}
