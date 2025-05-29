package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MoM販社別届先情報用MVIEW
 */
@Entity
@Data
@Table(name = "mv_t_jmcj005")
public class MvTJmcj005Master {

	/** 販社別設置届先ID */
	@Id
	@ApiModelProperty(value = "販社別設置届先ID", required = true, position = 1)
	private String hanshSetiskiId;

	private String setiskiId;
	
	/** 販社コード(支社コード) */
	@ApiModelProperty(value = "販社コード(支社コード)", required = true, position = 3)
	private String hanshCd;

	private String susSiteId;

	private String dltFlg;

	private String trsMotoSts;

	private String trsMotoSysKbn;

	/** RINGS得意先コード */
	@ApiModelProperty(value = "RINGS得意先コード", required = true, position = 8)
	private String ringsTkiskCd;

	/** RINGS届先コード */
	@ApiModelProperty(value = "RINGS届先コード", required = true, position = 8)
	private String ringsTodokesakiCd;

	private String ricohTkiskCd;

	private String oeTodokesakiCd;

	private String postNum;

	private String adsCd;

	private String adsTnyrykBun;

	private String tdhknNm;

	private String ads1;

	private String ads2;

	private String ads3;

	private String bantiGo;

	private String buildingNm;

	private String setiskiFlor;

	private String todokesakNm1;

	private String todokesakNm2;

	private String todokesakNmKana;

	private String todokesakSplNm;

	private String telNum;

	private String faxNum;

	@Column(name = "new_s_kbn")
	private String newSKbn;

	private String noukiKaitoKbn;

	private String cyuzanNoukiKaitoKbn;

	private String kaitoMtdSetiskiKbn;

	private String kaitoTelSetiskiKbn;

	private String ctiNoukiKaitoKbn;

	private String ctiCyuzanNoukiKaitoKbn;

	private String ctiKaitoMtdSetiskiKbn;

	private String ctiKaitoTelSetiskiKbn;

	private String rsNoukiKaitoKbn;

	private String rsCyuzanNoukiKaitoKbn;

	private String rsKaitoMtdSetiskiKbn;

	private String rsKaitoTelSetiskiKbn;

	private String ffmNoukiKaitoKbn;

	private String ffmCyuzanNoukiKaitoKbn;

	private String ffmKaitoMtdSetiskiKbn;

	private String ffmKaitoTelSetiskiKbn;

	private String steiHaisoCd;

	private String steiZaikkCd;

	private String ringsZaikkCd;

	private String nijitenKbn;

	private String userKytenCd;

	private String dayHaisoKbn;

	private String nouhinOutputKbn;

	private String nouhinWrkCd1;

	private String nouhinWrkCd2;

	private String nouhinWrkCd3;

	private String nouhinWrkCd4;

	private String nouhinWrkCd5;

	private String nouhinWrkCd6;

	private String nouhinWrkCd7;

	private String nouhinWrkCd8;

	private String kijiHanbaitenCd;

	private String uridnKijCd;

	private String uridnKijNm;

	private String siyoKishuNm1;

	private String siyoKishuNm2;

	private String siyoKishuNm3;

	private String dokujiKomoku_1;

	private String dokujiKomoku_2;

	private String dokujiKomoku_3;

	private String dokujiKomoku_4;

	private String dokujiKomoku_5;

	@Column(name = "tdksk_m_crt_kbn")
	private String tdkskMCrtKbn;

	private String juchuCtion_jiko;

	private String juchuMemo;

	private String endUserFlg;

	private String setisakiFreeInputKahiKbn;

	private String salesTmUmKbn;

	private String salesStartTm;

	private String salesEndTm;

	private String teikyuUmKbn;

	private String teikyuDt;

	private String kiboNohinTm;

	private String satNohinKbn;

	private String sunNohinKbn;

	private String hannyuFyoFlg;

	private String unsoSusKbn;

	private String kikyuChkKbn;

	private String setisakiTntoshNm;

	private String setisakiTntoshKshNm;

	private String shhnUltrNm;

	private String jyuryoNm;

	private String steiDnpyoYohiKbn;

	private String tyksoHenkoKknnYohiKbn;

	private String ricohFlg;

	private String deliveryCardFormNo;

	private String kaiPageFlg;

	private String juryosyoReturnKbn;

	private String juryosyoReturnKishId;

	private String juryosyoReturnSskId;

	private String ibmCstCd;

	private String lenovoCstId;

	private String makerButuruTaishoFlg;

	private String routeHaisoKbn;

	private String routeKaisyKbn;

	private String routeKaisySeikyKahiKbn;

	private String pmmcChtrKbn;

	private String pmmcCenterKbn;

	private String shteiSirskInfo;

	private String shiteiKeriInfo;

	private String ringsZikKshCd;

	private String ringsUragKshCd;

	private String ringsSyainCd;

	private String hanbaitenBaidenKbn;

	private String hanbaitenNouhinsyoType;

	private String hanbaitenShohizeiKbn;

	private String hanbaitenBaikaType;

	private String nHanbaitenCd;

	private String hanbaitenTkiskCd;

	private String hanbaitenTodokesakiCd;

	private String hanbaitenKishId;

	private String hanbaitenSoshikiId;

	private String juchuKbn;
	
	@Temporal(TemporalType.DATE)
	private Date ringsUpdtDt;

	@Temporal(TemporalType.DATE)
	private Date oeUpdtDt;

	@Temporal(TemporalType.DATE)
	private Date kizunaUpdtDt;

	private String kensakuTelNum;

	private String kensakuFaxNum;

	private String rmaUserCd;

	private String rmaCommercialFlg;

	private String machineChokusoFlg;

	private String gyomuRenraku;

	private String kako_jissekiKbn;

	private String kadoFlg;

	private String supplyChokusoKbn;

	private String userKytenTdkskCdOe;

	@Temporal(TemporalType.DATE)
	private Date scWebCrtDt;

	private String scWebCrtUserId;

	private String scWebCrtHanshCd;

	private String scWebCrtKinoCd;
	
	@Temporal(TemporalType.DATE)
	private Date scWebUpdtDt;

	private String scWebUpdtUserId;

	private String scWebUpdtHanshCd;

	private String scWebUpdtKinoCd;

	private String scBtCrtKinoCd;

	private String scBtCrtAsofDt;

	@Temporal(TemporalType.DATE)
	private Date scBtCrtDt;

	private String scBtUpdtKinoCd;

	private String scBtUpdtAsofDt;

	@Temporal(TemporalType.DATE)
	private Date scBtUpdtDt;

	@Temporal(TemporalType.DATE)
	private Date scSysKnrDt;

	private String scSysKnrNo;

	private String scMomUpdtKinoCd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date scMomUpdtDtTm;

	private String kshNumsFaxNum;

	private String pmmcTrhkskCd;

	private String steiDnpyoCrtKbn;

	private String sellingKbn;

	private String juchuUragSenyoKbn;

	private String todokesakiBunrui;

	private String clnTodokesakiNm;

	private String lastSinseiId;

	private String supplyTyksoOutTishgiFlg;

	@Temporal(TemporalType.DATE)
	private Date riyoStrtDate;

	private String ringsResultFlg;

	private String momKishId;

	private String originalSystemCode;

	private String zikKshCd;

	private String uragKshCd;
}