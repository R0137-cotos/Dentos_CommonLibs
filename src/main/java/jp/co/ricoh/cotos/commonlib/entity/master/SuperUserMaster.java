package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * スーパーユーザーを管理するマスター
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "super_user_master")
public class SuperUserMaster extends EntityBaseMaster {

	/**
	 * MoM社員ID
	 */
	@Id
	String momEmployeeId;
}
