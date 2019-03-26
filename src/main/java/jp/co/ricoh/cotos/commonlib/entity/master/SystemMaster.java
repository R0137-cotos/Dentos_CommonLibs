package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * システムマスタ
 */
@Entity
@Data
@ToString(exclude = { "appMasterList" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "system_master")
public class SystemMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "システムID", required = true, position = 1)
	private String systemId;

	@Column(nullable = false)
	@ApiModelProperty(value = "他システムデータ排他フラグ", required = true, position = 2, allowableValues = "range[0,9]")
	private int otherSysDataExcludeFlg;

	@OneToMany(mappedBy = "systemMaster")
	@ApiModelProperty(value = "アプリケーションマスタ", required = false, position = 3)
	private List<AppMaster> appMasterList;
}
