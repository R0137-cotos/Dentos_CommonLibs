package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務エラー履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work_error_log")
public class ArrangementWorkErrorLog extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_error_log_seq")
	@SequenceGenerator(name = "arrangement_work_error_log_seq", sequenceName = "arrangement_work_error_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務エラー履歴ID ", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	@Size(max = 255)
	@ApiModelProperty(value = "エラー内容", required = true, position = 2, allowableValues = "range[0,255]")
	private String errorMassage;

	@ApiModelProperty(value = "エラー発生日時", required = true, position = 3, readOnly = true)
	private Date errorOccuredAt;

}
