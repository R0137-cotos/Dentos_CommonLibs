package jp.co.ricoh.cotos.commonlib.serializer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnixTimestampDateSerializerTests {

	@Autowired
	ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@Test
	public void UNIXタイムスタンプにシリアライズされること() throws Exception {
		LocalDate localDate = LocalDate.of(2025, 10, 16);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		ContractApprovalResult target = new ContractApprovalResult();
		target.setProcessedAt(date);

		Map<String, Object> map = mapper.readValue(mapper.writeValueAsString(target), Map.class);

		Assert.assertEquals("UNIXタイムスタンプにシリアライズされること", "1760540400000", map.get("processedAt"));

	}

}
