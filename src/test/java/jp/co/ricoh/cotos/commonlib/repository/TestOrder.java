package jp.co.ricoh.cotos.commonlib.repository;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderManagementInfo;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderBasicInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderBranchCustomerInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderContractorInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderDistributorInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderManagementInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderProductGroupInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderProductInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderServiceInnerInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderSetupInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrdererInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestOrder {

	@Autowired
	OrderSetupInfoRepository orderSetupInfoRepository;

	@Autowired
	OrderServiceInnerInfoRepository orderServiceInnerInfoRepository;

	@Autowired
	OrderProductInfoRepository orderProductInfoRepository;

	@Autowired
	OrderProductGroupInfoRepository orderProductGroupInfoRepository;

	@Autowired
	OrderManagementInfoRepository orderManagementInfoRepository;

	@Autowired
	OrdererInfoRepository ordererInfoRepository;

	@Autowired
	OrderDistributorInfoRepository orderDistributorInfoRepository;

	@Autowired
	OrderContractorInfoRepository orderContractorInfoRepository;

	@Autowired
	OrderBranchCustomerInfoRepository orderBranchCustomerInfoRepository;

	@Autowired
	OrderBasicInfoRepository orderBasicInfoRepository;

	@Autowired
	TestTools testTools;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文セットアップ先情報() {
		全てのカラムがNullではないことを確認_共通(orderSetupInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文サービス固有情報() {
		全てのカラムがNullではないことを確認_共通(orderServiceInnerInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文商品情報() {
		全てのカラムがNullではないことを確認_共通(orderProductInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文商品グループ情報() {
		全てのカラムがNullではないことを確認_共通(orderProductGroupInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文管理情報() {
		全てのカラムがNullではないことを確認_共通(orderManagementInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文者情報() {
		全てのカラムがNullではないことを確認_共通(ordererInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文販売店情報() {
		全てのカラムがNullではないことを確認_共通(orderDistributorInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文顧客情報() {
		全てのカラムがNullではないことを確認_共通(orderContractorInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文担当支社情報() {
		全てのカラムがNullではないことを確認_共通(orderBranchCustomerInfoRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_注文基本情報() {
		全てのカラムがNullではないことを確認_共通(orderBasicInfoRepository, 4L, 5L);
	}

	@Test
	@Transactional
	public void 注文管理情報_契約取込状況_見取込_ID昇順のデータ取得() {
		context.getBean(DBConfig.class).initTargetTestData("repository/order.sql");
		List<OrderManagementInfo> list = orderManagementInfoRepository.findByContractCaptureStatusOrderById(OrderManagementInfo.CaptureStatus.未取込);
		assertEquals("list.size()", 2, list.size());
		assertEquals("1件目のID", 401L, list.get(0).getId());
		assertEquals("2件目のID", 501L, list.get(1).getId());
		list.forEach(omi -> assertEquals("契約状態" ,OrderManagementInfo.CaptureStatus.未取込, omi.getContractCaptureStatus()));
	}

	@Transactional
	private <T extends EntityBase, ID extends Serializable> void 全てのカラムがNullではないことを確認_共通(CrudRepository<T, ID> repository, @SuppressWarnings("unchecked") ID... ids) {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/order.sql");

		List<ID> idList = Arrays.asList(ids);

		idList.stream().forEach(id -> {
			// データが取得できることを確認
			T found = repository.findOne(id);
			Assert.assertNotNull(found);
			// 全てのカラムがNullではないことを確認
			try {
				testTools.assertColumnsNotNull(found);
			} catch (Exception e1) {
				Assert.fail("例外が発生した場合、エラー");
			}
		});
	}

}
