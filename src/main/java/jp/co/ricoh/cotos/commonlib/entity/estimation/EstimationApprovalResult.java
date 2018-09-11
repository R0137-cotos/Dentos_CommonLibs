package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積承認実績を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class EstimationApprovalResult extends EntityBase {

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
	@ApiModelProperty(value = "見積承認実績ID", required = true, position = 1)
	private long id;

	/**
	 * 見積承認ルート
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "approval_route_id", referencedColumnName = "id")
	private EstimationApprovalRoute estimationApprovalRoute;

	/**
	 * 承認処理カテゴリー
	 */
	@ApiModelProperty(value = "承認処理カテゴリー", required = true, position = 2)
	private ApprovalProcessCategory approvalProcessCategory;

	/**
	 * 処理実施者MoM社員ID
	 */
	@ApiModelProperty(value = "処理実施者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String actualEmpId;

	/**
	 * 処理実施者社員名
	 */
	@ApiModelProperty(value = "処理実施者社員名", required = true, position = 4, allowableValues = "range[0,255]")
	private String actualUserName;

	/**
	 * 処理実施者組織名
	 */
	@ApiModelProperty(value = "処理実施者組織名", required = false, position = 5, allowableValues = "range[0,255]")
	private String actualOrgName;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 6, allowableValues = "range[0,255]")
	private String requestComment;

	/**
	 * 実施日時
	 */
	@ApiModelProperty(value = "実施日時", required = true, position = 7)
	private Date processedAt;

}
