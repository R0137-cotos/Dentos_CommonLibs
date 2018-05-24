package jp.co.ricoh.cotos.commonlib.common.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手配を表すEntity
 */

@Entity
@Data
@Table(name = "arrangement_master")
public class ArrangementMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_master_seq")
	@SequenceGenerator(name = "arrangement_master_seq", sequenceName = "arrangement_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配ID", required = true, position = 1)
	private long id;

	/**
	 * 手配名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "手配名", required = false, position = 2, allowableValues = "range[0,255]")
	private String name;

	/**
	 * 有効期間開始日
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "有効期間開始日", required = false, position = 3)
	private Date arrangementTermStart;

	/**
	 * 有効期間終了日
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "有効期間終了日", required = false, position = 4)
	private Date arrangementTermEnd;

	/**
	 * 説明
	 */
	@ApiModelProperty(value = "説明", required = false, position = 5, allowableValues = "range[0,255]")
	private String description;
}
