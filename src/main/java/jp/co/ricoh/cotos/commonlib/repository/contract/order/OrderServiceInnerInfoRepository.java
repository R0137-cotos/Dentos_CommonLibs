package jp.co.ricoh.cotos.commonlib.repository.contract.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderServiceInnerInfo;

@Repository
public interface OrderServiceInnerInfoRepository extends JpaRepository<OrderServiceInnerInfo, Long> {

}
