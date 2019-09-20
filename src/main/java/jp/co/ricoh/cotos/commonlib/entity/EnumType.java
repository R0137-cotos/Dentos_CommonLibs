package jp.co.ricoh.cotos.commonlib.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractType;

/**
 * 共通区分定義
 */
public class EnumType {

	/**
	 * 承認処理カテゴリ
	 */
	public enum ApprovalProcessCategory {

		承認依頼("1"), 承認依頼差戻("2"), 承認("3"), 承認依頼取消("4");

		private final String text;

		private ApprovalProcessCategory(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ApprovalProcessCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 対象契約種別
	 */
	public enum TargetContractType {

		共通("1"), 新規("2"), 契約変更("3"), 情報変更("4");

		private final String text;

		private TargetContractType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TargetContractType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

		public static TargetContractType fromContractType(ContractType contractType) {

			// TargetContractType と ContractType 間で区分値構造が異なることによる変換処理
			switch (contractType) {
			case 新規:
				return TargetContractType.新規;
			case 契約変更:
				return TargetContractType.契約変更;
			case 情報変更:
				return TargetContractType.情報変更;
			default:
				throw new IllegalArgumentException(String.valueOf(contractType.toString()));
			}
		};
	}

	/**
	 * サービスカテゴリ
	 */
	public enum ServiceCategory {

		共通("0"), 見積("1"), 契約("2"), 手配("3"), 電力_見積("101"), 電力_契約("102");

		private final String text;

		private ServiceCategory(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ServiceCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 処理カテゴリ
	 */
	public enum ProcessCategory {

		承認依頼("1"), 承認依頼取消("2"), 承認依頼差戻("3"), 承認("4"), 作業依頼("5"), 作業完了("6"), キャンセル手続き("7"), キャンセル手続き中止("8"), 解約手続き("9"), 解約手続き中止("10"), 問い合わせ("11"), 売上計上停止("12"), 売上計上再開("13"), 売上開始指示("14"), 問い合わせ返信("15"), 承認済差戻("16");

		private final String text;

		private ProcessCategory(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ProcessCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * ワークフロー種別
	 */
	public enum WorkflowType {

		承認フロー("1"), タスクフロー("2");

		private final String text;

		private WorkflowType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static WorkflowType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 承認対象種別
	 */
	public enum ApprovalTargetType {

		新規("1"), 情報変更("2"), 契約変更("3"), キャンセル("4"), 解約("5"), 作業完了報告("6"), 非承認("7"), 売上指示("8"), 売上計上("9"), 承認済差戻("10");

		private final String text;

		private ApprovalTargetType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ApprovalTargetType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 状態
	 */
	public enum DetailStatus {

		NOUPDATE("1"), ADD("2"), DELETE("3"), UPDATE("4");

		private final String text;

		private DetailStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static DetailStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 販売店商流順
	 */
	public enum DealerFlowOrder {

		販売店("1"), 母店("2");

		private final String text;

		private DealerFlowOrder(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static DealerFlowOrder fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 宛先種別
	 */
	public enum TargetDirectionType {

		担当CE("1"), 担当SA("2"), 全担当者("98"), その他("99");

		private final String text;

		private TargetDirectionType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TargetDirectionType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 汎用マスタ明細区分
	 */
	public enum CommonMasterDetailType {

		お客様担当者, 接点店担当者, 母店接点店担当者;

	}

	/**
	 * 汎用マスタカラム名区分
	 */
	public enum ColumnNameType {

		issue_format, commercial_flow_div, estimated_system_div, file_kind, cancel_reason, cost_type, sales_tax_rate, branch_custoemr_cd, process_category;

	}

	/**
	 * イニシャル売上計上処理状態
	 */
	public enum InitialAccountSalesStatus {

		未計上("0"), 計上済み("1"), 処理不要("2"), 処理不可("3");

		private final String text;

		private InitialAccountSalesStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static InitialAccountSalesStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * イニシャル/ランニング区分
	 */
	public enum InitialRunningDiv {

		イニシャル("1"), ランニング("2"), 期間売("3");

		private final String text;

		private InitialRunningDiv(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static InitialRunningDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	* 各種ID値などのダミー設定値
	*/
	public enum DummyCodeValue {

		Dummy_Mcl_MoM_Rel_Id("999999");

		private final String text;

		private DummyCodeValue(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

	}
}
