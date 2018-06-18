package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * スーパーユーザーを管理するマスター
 */

@Entity
@Data
public class SuperUserMaster {

	/**
	 * MoM社員ID
	 */
	@Id
	String momEmployeeId;
}
