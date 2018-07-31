package jp.co.ricoh.cotos.commonlib.security.mom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.dto.parameter.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.dto.result.StringResult;
import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.jmo.cache.AuthoritySearch;
import jp.co.ricoh.jmo.dto.cache.AuthorityInfoActionDto;
import jp.co.ricoh.jmo.dto.cache.AuthorityInfoLevelDto;
import jp.co.ricoh.jmo.dto.service.EmployeeAuthInfoDto;
import jp.co.ricoh.jmo.dto.service.EmployeeInfoDto;
import jp.co.ricoh.jmo.dto.service.EmployeeOrgInfoDto;
import jp.co.ricoh.jmo.service.KengenServiceServiceLocator;

@Component
public class MomAuthorityService {

	@Value("${remote.momauthority.url}")
	private String momServiceUrl;
	@Value("${remote.momauthority.relatedid}")
	private String momRelatedId;
	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUser;
	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Autowired
	DBUtil dbUtil;

	public enum ActionDiv {
		照会("01"), 登録("02"), 更新("03"), 削除("04"), 印刷("05"), ダウンロード("06"), 集計("07");

		private final String value;

		private ActionDiv(final String value) {
			this.value = value;
		}

		public String toValue() {
			return this.value;
		}
	}

	public enum AuthDiv {
		見積_契約_手配("2200"), 請求_計上_本部("2210"), システム管理("2220");

		private final String value;

		private AuthDiv(final String value) {
			this.value = value;
		}

		public String toValue() {
			return this.value;
		}
	}

	public enum AuthLevel {
		不可("00"), 自顧客("10"), 配下("30"), 自社("50"), 地域("70"), 東西("80"), すべて("90");

		private final String value;

		private AuthLevel(final String value) {
			this.value = value;
		}

		public String toValue() {
			return this.value;
		}
	}

	/**
	 * MoM権限レベルを取得
	 */
	public AuthLevel searchMomAuthority(String singleUserId, ActionDiv actionDiv, AuthDiv authDiv) throws Exception {

		// MoM権限取得用のコネクションを作成
		Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

		// 権限情報取得用サービスを初期化
		KengenServiceServiceLocator kengenServiceLocator = new KengenServiceServiceLocator();
		kengenServiceLocator.setKengenServiceEndpointAddress(momServiceUrl);

		// MoM提供モジュールから社員情報の取得
		EmployeeInfoDto[] employeeInfoDtos = kengenServiceLocator.getKengenService().getContactEmpFromSUID(singleUserId, Calendar.getInstance(), false, momRelatedId);
		if (employeeInfoDtos.length != 1) {
			return null;
		}

		// 社員情報から、権限分類を特定
		List<EmployeeAuthInfoDto> empAuthInfoList = new ArrayList<>();
		List<EmployeeOrgInfoDto> employeeOrgInfoDtoList = Arrays.asList(employeeInfoDtos[0].getOrgList());
		employeeOrgInfoDtoList.stream().forEach(employeeOrgInfoDto -> Collections.addAll(empAuthInfoList, employeeOrgInfoDto.getClassifyList()));
		List<String> classify = empAuthInfoList.stream().map(empAuthInfo -> empAuthInfo.getClassifyId()).collect(Collectors.toList());

		// MoM提供モジュールから権限情報の取得
		AuthoritySearch authoritySearch = new AuthoritySearch();
		AuthorityInfoActionDto[] authorityInfoActionDtos = authoritySearch.getAuthSumFromEmpId((String[]) classify.toArray(new String[0]), momRelatedId, connection);
		if (authorityInfoActionDtos == null || authorityInfoActionDtos.length == 0) {
			return null;
		}

		// 引数のアクション区分、権限情報からっ権限レベルを取得
		List<AuthorityInfoActionDto> authorityInfoActionDtoList = Arrays.asList(authorityInfoActionDtos);
		List<AuthorityInfoLevelDto> authorityInfoLevelDtoList = Arrays.asList(authorityInfoActionDtoList.stream().filter(authorityInfoActionDto -> actionDiv.toValue().equals(authorityInfoActionDto.getActionId())).findFirst().get().getLevelList());
		String targetAuthLevel = authorityInfoLevelDtoList.stream().filter(authorityInfoLevelDto -> authDiv.value.equals(authorityInfoLevelDto.getInfoId())).findFirst().get().getLevelId();

		// 取得した権限レベルを返却する
		return Arrays.asList(AuthLevel.values()).stream().filter(authLevel -> authLevel.value.equals(targetAuthLevel)).findFirst().get();
	}

	/**
	 * 権限判定用パラメーターから対象のアクションを実施する権限があるか判定する
	 */
	public boolean hasAuthority(AuthorityJudgeParameter authParam, ActionDiv actionDiv, AuthDiv authDiv, AccessType accessType) throws Exception {

		AuthLevel authLevel = this.searchMomAuthority(authParam.getActorEmployeeMaster().getSingleUserId(), actionDiv, authDiv);

		// 参照・編集処理、承認処理により認可処理を分岐
		if (AccessType.参照.equals(accessType) || AccessType.編集.equals(accessType)) {

			// 参照・編集処理用の認可処理を実施
			return this.hasEditAuthority(authLevel, authParam.getActorEmployeeMaster(), authParam.getKjbMaster(), authParam.getEmployeeMasterList());
		} else if (AccessType.承認.equals(accessType)) {

			// 承認処理用の認可処理を実施
			return this.hasApproveAuthority(authLevel, authParam.getActorEmployeeMaster(), authParam.getRequesterEmployeeMaster());
		} else {
			return false;
		}
	}

	/**
	 * 参照・編集権限が存在するか判定する
	 */
	private boolean hasEditAuthority(AuthLevel authLevel, EmployeeMaster editor, KjbMaster customer, List<EmployeeMaster> targetEmployeeMasterList) {

		// 権限レベルによる認可処理を実施
		switch (authLevel) {
		case 不可:
			return false;
		case 自顧客:
			// 担当SA、追加編集者、担当CE、担当SEであるかを確認
			return targetEmployeeMasterList.stream().anyMatch(targetEmployeeMaster -> editor.getMomEmployeeId().equals(targetEmployeeMaster.getMomEmployeeId()));
		case 配下:
			// 担当SA、追加編集者、担当CE、担当SEの所属組織が配下であるか確認
			return targetEmployeeMasterList.stream().anyMatch(targetEmployeeMaster -> this.isLowerOrg(targetEmployeeMaster.getMomOrgId(), editor.getMomOrgId(), editor.getOrgHierarchyLevel()));
		case 自社:
			// 担当SA、追加編集者、担当CE、担当SEと販社が同一であるか確認
			return targetEmployeeMasterList.stream().anyMatch(targetEmployeeMaster -> editor.getHanshCd().equals(targetEmployeeMaster.getHanshCd()));
		case 地域:
			// 担当SA、追加編集者、担当CE、担当SEの販社と関連販社であるか確認
			return targetEmployeeMasterList.stream().anyMatch(targetEmployeeMaster -> this.isRelatedOrg(targetEmployeeMaster.getSingleUserId(), editor.getSingleUserId()));
		case 東西:
		case すべて:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 承認権限があるか判定する
	 */
	private boolean hasApproveAuthority(AuthLevel authLevel, EmployeeMaster approver, EmployeeMaster requester) {

		// 権限レベルによる認可処理を実施
		switch (authLevel) {
		case 不可:
		case 自顧客:
			return false;
		case 配下:
		case 自社:
		case 地域:
			Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("approverSingleUserId", approver.getSingleUserId());
			queryParams.put("requesterSingleUserId", requester.getSingleUserId());
			long result = dbUtil.loadCountFromSQLFile("sql/security/approverAuthority/approverAuthority_authority" + authLevel.toValue() + ".sql", queryParams);
			return 0 != result;
		case 東西:
		case すべて:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 対象の組織が下位組織か判定する
	 */
	private boolean isLowerOrg(String targetOrgId, String rootOrgId, Integer rootOrgHierarchyLevel) {

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("orgId", rootOrgId);
		queryParams.put("hierarchyLevel", rootOrgHierarchyLevel);

		List<StringResult> orgIdList = dbUtil.loadFromSQLFile("sql/security/editorAuthority/getLowerOrgs.sql", StringResult.class, queryParams);

		if (orgIdList != null) {
			if (orgIdList.stream().anyMatch(orgId -> targetOrgId.equals(orgId.getResult()))) {
				return true;
			} else {
				return orgIdList.stream().anyMatch(orgId -> this.isLowerOrg(targetOrgId, orgId.getResult(), rootOrgHierarchyLevel));
			}
		} else {
			return false;
		}
	}

	/**
	 * 関連組織に所属するか判定する
	 */
	private boolean isRelatedOrg(String targetSingleUserId, String editorSingleUserId) {

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("editorSingleUserId", editorSingleUserId);
		queryParams.put("targetSingleUserId", targetSingleUserId);

		long result = dbUtil.loadCountFromSQLFile("sql/security/editorAuthority/isRelatedOrg.sql", queryParams);

		return result > 0;
	}
}
