package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 見積情報の添付ファイルを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(EstimationAttachedFileListener.class)
@ToString
@Table(name = "estimation_attached_file")
public class EstimationAttachedFile extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_attached_file_seq")
	@SequenceGenerator(name = "estimation_attached_file_seq", sequenceName = "estimation_attached_file_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積添付ファイルID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * ファイル種類
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ファイル種類", required = false, position = 3, allowableValues = "range[0,255]")
	private String fileKind;

	/**
	 * 添付ファイルパス
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 1023)
	@ApiModelProperty(value = "添付ファイルパス", required = true, position = 4, allowableValues = "range[0,1023]")
	private String attachedFilePath;

	/**
	 * コメント
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "コメント", required = false, position = 5, allowableValues = "range[0,1000]")
	private String attachedComment;

	/**
	 * 添付者MoM社員ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "添付者MoM社員ID", required = true, position = 6, allowableValues = "range[0,255]")
	private String attachedEmpId;

	/**
	 * 添付者氏名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "添付者氏名", required = true, position = 7, allowableValues = "range[0,255]")
	private String attachedEmpName;

	/**
	 * 添付者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "添付者組織名", required = false, position = 8, allowableValues = "range[0,255]")
	private String attachedOrgName;

	/**
	 * 添付日時
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "添付日時", required = true, position = 9, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date attachedAt;

	@PrePersist
	public void prePersist() {
		this.attachedAt = new Date();
	}
}
