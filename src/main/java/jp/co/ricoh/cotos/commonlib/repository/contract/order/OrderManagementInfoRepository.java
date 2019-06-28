package jp.co.ricoh.cotos.commonlib.repository.contract.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderManagementInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderManagementInfo.CaptureStatus;

@Repository
public interface OrderManagementInfoRepository extends JpaRepository<OrderManagementInfo, Long> {

	public List<OrderManagementInfo> findByContractCaptureStatusOrderById(CaptureStatus captureStatus);
}
