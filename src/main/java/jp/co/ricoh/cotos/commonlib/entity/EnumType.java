package jp.co.ricoh.cotos.commonlib.entity;

import java.util.Arrays;

/**
 * 共通区分定義
 */
public class EnumType {

	/**
	 * 承認処理カテゴリ
	 */
	public enum ApprovalProcessCategory {

		承認依頼("1"), 承認依頼差戻("2"), 承認("3");

		private final String text;

		private ApprovalProcessCategory(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ApprovalProcessCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 対象契約種別
	 */
	public enum TargetContractType {

		共通("1"), 新規("2"), プラン変更("3"), 情報変更("4");

		private final String text;

		private TargetContractType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static TargetContractType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * サービスカテゴリ
	 */
	public enum ServiceCategory {

		見積("1"), 契約("2"), 手配("3");

		private final String text;

		private ServiceCategory(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ServiceCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 処理カテゴリ
	 */
	public enum ProcessCategory {

		承認依頼("1"), 承認依頼取消("2"), 承認依頼差戻("3"), 承認("4"), 作業依頼("5"), 作業完了("6"), キャンセル手続き("7"), キャンセル手続き中止("8"), 解約手続き("9"), 解約手続き中止("10");

		private final String text;

		private ProcessCategory(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

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
		public String toString() {
			return this.text;
		}

		public static WorkflowType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 承認対象種別
	 */
	public enum ApprovalTargetType {

		新規("1"), 情報変更("2"), プラン変更("3"), キャンセル("4"), 解約("5"), 作業完了報告("6"), 非承認("7");

		private final String text;

		private ApprovalTargetType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ApprovalTargetType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 状態
	 */
	public enum DetailStatus {

		NONE("1"), ADD("2"), DELETE("3");

		private final String text;

		private DetailStatus(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static DetailStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
}
