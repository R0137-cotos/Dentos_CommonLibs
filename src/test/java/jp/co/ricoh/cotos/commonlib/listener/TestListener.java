package jp.co.ricoh.cotos.commonlib.listener;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DealerFlowOrder;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.DealerContract;
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.DealerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
import jp.co.ricoh.cotos.commonlib.repository.contract.DealerContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.CustomerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.DealerEstimationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestListener {

	static ConfigurableApplicationContext context;

	@Autowired
	CustomerEstimationRepository customerEstimationRepository;

	@Autowired
	DealerEstimationRepository dealerEstimationRespository;

	@Autowired
	DealerContractRepository dealerContractRepository;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("listener/listener.sql");
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	@WithMockCustomUser
	public void CustomerEstimationListenerのテスト() throws Exception {
		CustomerEstimation customerEstimation = new CustomerEstimation();
		customerEstimation.setMomKjbSystemId("000000000442415");
		Estimation estimation = new Estimation();
		estimation.setId(1L);
		customerEstimation.setEstimation(estimation);
		customerEstimationRepository.save(customerEstimation);
		customerEstimation = customerEstimationRepository.findById(customerEstimation.getId()).get();
		Assert.assertEquals("MoM企事部IDが正しく取得されること", "000000000447380", customerEstimation.getMomCustId());
		Assert.assertEquals("MoM企業IDが正しく取得されること", "000000000417365", customerEstimation.getCompanyId());
		Assert.assertEquals("MoM事業所IDが正しく取得されること", "000000000445220", customerEstimation.getOfficeId());
		Assert.assertEquals("企事部設定区分が正しく取得されること", DepartmentDiv.企事部, customerEstimation.getDepartmentDiv());
		Assert.assertEquals("顧客名が正しく取得されること", "花房工事株式会社＊", customerEstimation.getCustomerName());
		Assert.assertEquals("企業名が正しく取得されること", "花房工事株式会社", customerEstimation.getCompanyName());
		Assert.assertEquals("事業所名が正しく取得されること", "＊", customerEstimation.getOfficeName());
		Assert.assertEquals("部門名が正しく取得されること", null, customerEstimation.getDepartmentName());
		Assert.assertEquals("郵便番号が正しく取得されること", "1710014", customerEstimation.getPostNumber());
		Assert.assertEquals("住所が正しく取得されること", "東京都豊島区池袋４丁目３６－１７", customerEstimation.getAddress());
		Assert.assertEquals("電話番号が正しく取得されること", "0339808308", customerEstimation.getPhoneNumber());
		Assert.assertEquals("FAX番号が正しく取得されること", null, customerEstimation.getFaxNumber());
	}

	@Test
	@WithMockCustomUser
	public void DealerEstimationListenerのテスト() throws Exception {
		DealerEstimation dealerEstimation = new DealerEstimation();
		dealerEstimation.setMomKjbSystemId("000000000442415");
		dealerEstimation.setDealerFlowOrder(DealerFlowOrder.販売店);
		Estimation estimation = new Estimation();
		estimation.setId(1L);
		dealerEstimation.setEstimation(estimation);
		dealerEstimationRespository.save(dealerEstimation);
		dealerEstimation = dealerEstimationRespository.findById(dealerEstimation.getId()).get();
		Assert.assertEquals("販売店名が正しく取得されること", "花房工事株式会社", dealerEstimation.getDealerName());
		Assert.assertEquals("郵便番号が正しく取得されること", "1710014", dealerEstimation.getPostNumber());
		Assert.assertEquals("住所が正しく取得されること", "東京都豊島区池袋４丁目３６－１７", dealerEstimation.getAddress());
		Assert.assertEquals("電話番号が正しく取得されること", "0339808308", dealerEstimation.getOrgPhoneNumber());
	}

	@Test
	@WithMockCustomUser
	public void DealerContractListenerのテスト() throws Exception {
		DealerContract dealerContract = new DealerContract();
		dealerContract.setMomKjbSystemId("000000000442415");
		dealerContract.setDealerFlowOrder(DealerFlowOrder.販売店);
		Contract contract = new Contract();
		contract.setId(1L);
		dealerContract.setContract(contract);
		dealerContractRepository.save(dealerContract);
		dealerContract = dealerContractRepository.findById(dealerContract.getId()).get();
		Assert.assertEquals("販売店名が正しく取得されること", "花房工事株式会社", dealerContract.getDealerName());
		Assert.assertEquals("郵便番号が正しく取得されること", "1710014", dealerContract.getPostNumber());
		Assert.assertEquals("住所が正しく取得されること", "東京都豊島区池袋４丁目３６－１７", dealerContract.getAddress());
		Assert.assertEquals("電話番号が正しく取得されること", "0339808308", dealerContract.getOrgPhoneNumber());
	}
}
