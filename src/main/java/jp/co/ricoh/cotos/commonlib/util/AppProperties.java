package jp.co.ricoh.cotos.commonlib.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AppProperties {

	@Autowired
	FileProperties fileProperties;
	
	@Autowired
	SearchProperties searchproperties;
}
