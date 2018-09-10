package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務の承認実績を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work_approval_result")
public class ArrangeWorkApprovalResult extends EntityBase {

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
	@ApiModelProperty(value = "手配業務承認実績ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務承認ルート
	 */
	@ManyToOne(optional = true)
	@ApiModelProperty(value = "手配業務承認ルート", required = true, position = 2)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 承認処理カテゴリ
	 */
	@ApiModelProperty(value = "承認処理カテゴリ", required = true, position = 3, allowableValues = "range[0,255]")
	private ApprovalProcessCategory approvalProcessCategory;

	/**
	 * 処理実施者MoM社員ID
	 */
	@ApiModelProperty(value = "処理実施者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String actualEmpId;

	/**
	 * 処理実施者氏名
	 */
	@ApiModelProperty(value = "処理実施者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String actualUserName;

	/**
	 * 処理実施者組織名
	 */
	@ApiModelProperty(value = "処理実施者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String actualOrgName;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 7, allowableValues = "range[0,255]")
	private String requestComment;

	/**
	 * 実施日時
	 */
	@ApiModelProperty(value = "実施日時", required = true, position = 8)
	private Date processedAt;

}
