package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.util.List;

import jakarta.validation.Valid;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文情報DTO トップレベル
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderListDto {
	@Valid
	private List<OrderBasicInfoDto> orderList;
}
