package jp.co.ricoh.cotos.commonlib.security.mom;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.dto.result.StringResult;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.logic.message.MessageUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.SuperUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.util.DatasourceProperties;
import jp.co.ricoh.cotos.commonlib.util.RemoteMomProperties;
import jp.co.ricoh.jmo.cache.AuthoritySearch;
import jp.co.ricoh.jmo.dto.cache.AuthorityInfoActionDto;
import jp.co.ricoh.jmo.dto.cache.AuthorityInfoLevelDto;
import jp.co.ricoh.jmo.dto.service.EmployeeAuthInfoDto;
import jp.co.ricoh.jmo.dto.service.EmployeeInfoDto;
import jp.co.ricoh.jmo.dto.service.EmployeeOrgInfoDto;
import jp.co.ricoh.jmo.service.KengenServiceServiceLocator;

@Component
public class MomAuthorityService {

	/** ロガー */
	private static final Log log = LogFactory.getLog(MomAuthorityService.class);

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	RemoteMomProperties remoteMomProperties;

	@Autowired
	DatasourceProperties datasourceProperties;

	@Autowired
	DBUtil dbUtil;

	@Autowired
	SuperUserMasterRepository superUserMasterRepository;

	public enum AuthLevel {
		不可("00"), 自顧客("10"), 配下("30"), 自社("50"), 地域("70"), 東西("80"), すべて("90");

		private final String value;

		private AuthLevel(final String value) {
			this.value = value;
		}

		@JsonValue
		public String toValue() {
			return this.value;
		}

		@JsonCreator
		public static AuthLevel fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.value.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * MoM権限レベルを取得
	 */
	public AuthLevel searchMomAuthority(String singleUserId, ActionDiv actionDiv, AuthDiv authDiv) throws RemoteException, SQLException, ServiceException {

		// MoM提供モジュール経由でMom権限情報を取得
		List<AuthorityInfoActionDto> authorityInfoActionDtoList = this.searchMomAuthoritiesExternal(singleUserId);

		// Mom権限情報wお取得できない場合、nullを返却
		if (authorityInfoActionDtoList == null) {
			return null;
		}

		// 引数のアクション区分、権限情報からっ権限レベルを取得
		List<AuthorityInfoLevelDto> authorityInfoLevelDtoList = Arrays.asList(authorityInfoActionDtoList.stream().filter(authorityInfoActionDto -> actionDiv.toString().equals(authorityInfoActionDto.getActionId())).findFirst().get().getLevelList());
		String targetAuthLevel = authorityInfoLevelDtoList.stream().filter(authorityInfoLevelDto -> authDiv.toString().equals(authorityInfoLevelDto.getInfoId())).findFirst().get().getLevelId();

		// 取得した権限レベルを返却する
		return Arrays.asList(AuthLevel.values()).stream().filter(authLevel -> authLevel.value.equals(targetAuthLevel)).findFirst().get();
	}

	/**
	 * シングルユーザーIDに紐づく、すべてのCOTOS用MoM権限レベルを取得
	 * 
	 * @throws Exception
	 */
	public Map<ActionDiv, Map<AuthDiv, AuthLevel>> searchAllMomAuthorities(String singleUserId) throws Exception {

		// 外部ライブラリ経由でMom権限情報を取得
		List<AuthorityInfoActionDto> authorityInfoActionDtoList;

		try {
			authorityInfoActionDtoList = this.searchMomAuthoritiesExternal(singleUserId);
		} catch (RemoteException | SQLException | ServiceException e) {
			log.error(messageUtil.createMessageInfo("ExternalModuleError").getMsg());
			throw e;
		}

		// Mom権限情報wお取得できない場合、nullを返却
		if (authorityInfoActionDtoList == null) {
			return null;
		}

		// ユーザーの全MoM権限情報
		Map<ActionDiv, Map<AuthDiv, AuthLevel>> allMomAuthorities = new HashMap<>();

		// アクション区分「00：なし」を除外してループ
		Arrays.asList(ActionDiv.values()).stream().filter(actionDiv -> actionDiv != ActionDiv.なし).forEach(actionDiv -> {

			// 該当アクション区分のMoM権限情報を取得
			List<AuthorityInfoLevelDto> authorityInfoLevelDtoList = Arrays.asList(authorityInfoActionDtoList.stream().filter(authorityInfoActionDto -> actionDiv.toString().equals(authorityInfoActionDto.getActionId())).findFirst().get().getLevelList());

			// 権限区分区分「0：なし」を除外してループ
			Map<AuthDiv, AuthLevel> authorities = Arrays.asList(AuthDiv.values()).stream().filter(authDiv -> authDiv != AuthDiv.なし).collect(Collectors.toMap(authDiv -> authDiv, authDiv -> {

				// 該当権限区分のMoM権限レベルを取得
				String targetAuthLevel = authorityInfoLevelDtoList.stream().filter(authorityInfoLevelDto -> authDiv.toString().equals(authorityInfoLevelDto.getInfoId())).findFirst().get().getLevelId();
				return Arrays.asList(AuthLevel.values()).stream().filter(authLevel -> authLevel.value.equals(targetAuthLevel)).findFirst().get();
			}));

			allMomAuthorities.put(actionDiv, authorities);
		});

		return allMomAuthorities;
	}

	/**
	 * 権限判定用パラメーターから対象のアクションを実施する権限があるか判定する
	 */
	public boolean hasAuthority(AuthorityJudgeParameter authParam, ActionDiv actionDiv, AuthDiv authDiv, AccessType accessType) throws Exception {

		// 権限レベルを取得
		AuthLevel authLevel = this.searchMomAuthority(authParam.getActorMvEmployeeMaster().getSingleUserId(), actionDiv, authDiv);

		// 認可判定処理開始
		log.info(messageUtil.createMessageInfo("AuthorizeProcessJudgeStartInfo", Arrays.asList(accessType.name(), authLevel.name()).toArray(new String[0])).getMsg());

		// 参照・編集処理、承認処理により認可処理を分岐
		if (AccessType.参照.equals(accessType) || AccessType.編集.equals(accessType)) {

			if (AccessType.参照.equals(accessType)) {
				// 承認者に含まれる場合、参照権限を付与
				if (authParam.getApproverMvEmployeeMasterList() != null && !authParam.getApproverMvEmployeeMasterList().isEmpty() && authParam.getApproverMvEmployeeMasterList().stream().filter(approver -> approver.getMomEmployeeId().equals(authParam.getActorMvEmployeeMaster().getMomEmployeeId())).count() > 0) {
					return true;
				}
			}

			if (AccessType.編集.equals(accessType)) {
				// 次回承認者の場合、編集権限を付与
				if (authParam.getNextApproverMvEmployeeMaster() != null && authParam.getNextApproverMvEmployeeMaster().getMomEmployeeId().equals(authParam.getActorMvEmployeeMaster().getMomEmployeeId())) {
					return true;
				}
			}

			// 参照・編集処理用の認可処理を実施
			return this.hasEditAuthority(authLevel, authParam.getActorMvEmployeeMaster(), authParam.getVKjbMaster(), authParam.getMvEmployeeMasterList());
		} else if (AccessType.承認.equals(accessType)) {

			// 直接指定された承認者であれば、権限あり
			if (authParam.isManualApprover()) {
				return true;
			}

			// 自己承認フラグであれば、権限あり
			if (authParam.isSelfApprover()) {
				return true;
			}

			// 承認処理用の認可処理を実施
			return this.hasApproveAuthority(authLevel, authParam.getActorMvEmployeeMaster(), authParam.getRequesterMvEmployeeMaster());
		} else {
			return false;
		}
	}

	/**
	 * 参照・編集権限が存在するか判定する
	 */
	protected boolean hasEditAuthority(AuthLevel authLevel, MvEmployeeMaster editor, VKjbMaster customer, List<MvEmployeeMaster> targetEmployeeMasterList) {

		// 権限レベルによる認可処理を実施
		switch (authLevel) {
		case 不可:
			return false;
		case 自顧客:
			// 担当SA、追加編集者、担当CE、担当SEであるかを確認
			return targetEmployeeMasterList.stream().anyMatch(targetEmployeeMaster -> editor.getMomEmployeeId().equals(targetEmployeeMaster.getMomEmployeeId()));
		case 配下:
			// 担当SA、追加編集者、担当CE、担当SEの所属組織が配下であるか確認
			return targetEmployeeMasterList.stream().anyMatch(targetEmployeeMaster -> this.isLowerOrg(targetEmployeeMaster.getMomOrgId(), editor.getMomOrgId()));
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
	protected boolean hasApproveAuthority(AuthLevel authLevel, MvEmployeeMaster approver, MvEmployeeMaster requester) {

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
	protected boolean isLowerOrg(String targetOrgId, String rootOrgId) {

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("orgId", rootOrgId);

		List<StringResult> orgIdList = dbUtil.loadFromSQLFile("sql/security/editorAuthority/getLowerOrgs.sql", StringResult.class, queryParams);

		if (orgIdList != null) {
			if (orgIdList.stream().anyMatch(orgId -> targetOrgId.equals(orgId.getResult()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 関連組織に所属するか判定する
	 */
	protected boolean isRelatedOrg(String targetSingleUserId, String editorSingleUserId) {

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("editorSingleUserId", editorSingleUserId);
		queryParams.put("targetSingleUserId", targetSingleUserId);

		long result = dbUtil.loadCountFromSQLFile("sql/security/editorAuthority/isRelatedOrg.sql", queryParams);

		return result > 0;
	}

	/**
	 * 外部ライブラリから、シングルユーザーIDに紐づく権限情報を取得する
	 */
	protected List<AuthorityInfoActionDto> searchMomAuthoritiesExternal(String singleUserId) throws SQLException, RemoteException, ServiceException {

		// 権限情報取得用サービスを初期化
		KengenServiceServiceLocator kengenServiceLocator = new KengenServiceServiceLocator();
		kengenServiceLocator.setKengenServiceEndpointAddress(remoteMomProperties.getUrl());

		// MoM提供モジュールから社員情報の取得
		EmployeeInfoDto[] employeeInfoDtos = kengenServiceLocator.getKengenService().getContactEmpFromSUID(singleUserId, Calendar.getInstance(), false, remoteMomProperties.getRelatedid());
		if (employeeInfoDtos.length != 1) {
			return null;
		}

		// 社員情報から、権限分類を特定
		List<EmployeeAuthInfoDto> empAuthInfoList = new ArrayList<>();
		List<EmployeeOrgInfoDto> employeeOrgInfoDtoList = Arrays.asList(employeeInfoDtos[0].getOrgList());
		employeeOrgInfoDtoList.stream().forEach(employeeOrgInfoDto -> Collections.addAll(empAuthInfoList, employeeOrgInfoDto.getClassifyList()));
		List<String> classify = empAuthInfoList.stream().map(empAuthInfo -> empAuthInfo.getClassifyId()).collect(Collectors.toList());

		AuthorityInfoActionDto[] authorityInfoActionDtos = null;
		try (Connection connection = DriverManager.getConnection(datasourceProperties.getUrl(), datasourceProperties.getUsername(), datasourceProperties.getPassword())) {
			// MoM提供モジュールから権限情報の取得
			AuthoritySearch authoritySearch = new AuthoritySearch();
			authorityInfoActionDtos = authoritySearch.getAuthSumFromEmpId((String[]) classify.toArray(new String[0]), remoteMomProperties.getRelatedid(), connection);
		}

		if (authorityInfoActionDtos == null || authorityInfoActionDtos.length == 0) {
			return null;
		}

		return Arrays.asList(authorityInfoActionDtos);
	}
}