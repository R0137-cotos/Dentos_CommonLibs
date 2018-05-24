package jp.co.ricoh.cotos.commonlib.common.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.common.master.ArrangementMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work")
public class ArrangementWork extends EntityBase {

	public enum ArrangementWorkStatus {

		受付待ち, 受付済み, 対応済み, 不受理;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ArrangementWorkStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_seq")
	@SequenceGenerator(name = "arrangement_work_seq", sequenceName = "arrangement_work_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務ID", required = true, position = 1)
	private long id;

	/**
	 * 契約ID
	 */
	@ApiModelProperty(value = "契約ID", required = false, position = 2)
	private long contractId;

	/**
	 * 手配業務ステータス
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "手配業務ステータス", required = false, position = 3)
	private ArrangementWorkStatus arrangementWorkStatus;

	/**
	 * 担当作業者
	 */
	@ManyToMany
	@JoinTable(name = "arrangementWorkUser", joinColumns = @JoinColumn(name = "arrangementWorkId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"))
	@ApiModelProperty(value = "担当作業者", required = false, position = 4)
	private List<EmployeeArrange> arrangementWorkUserList;

	/**
	 * 担当作業日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "担当作業日時", required = false, position = 5)
	private Date workAt;

	/**
	 * メモ
	 */
	@ApiModelProperty(value = "メモ", required = false, position = 6, allowableValues = "range[0,255]")
	private String memo;

	/**
	 * 手配
	 */
	@ManyToOne(optional = false)
	@JsonIgnore
	@ApiModelProperty(value = "手配ID", required = false, position = 7)
	private Arrangement arrangement;

	/**
	 * 手配明細
	 */
	@OneToMany(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配明細", required = false, position = 8)
	private List<ArrangementDetail> arrangementDetailList;

	/**
	 * 手配マスタ
	 */
	@ManyToOne(optional = false)
	@ApiModelProperty(value = "手配マスタID", required = false, position = 9)
	private ArrangementMaster arrangementMaster;
}