package com.carOrder.model;
import java.sql.Date;

public class CarOrderVO {
	
	/*********************** 訂單相關 ***********************/
	private String cod_id; 			// 禮車訂單編號
	private String membre_id; 		// 會員編號
	private String vender_id; 		     // 廠商編號
	private String cid; 				 // 禮車編號
	private java.sql.Timestamp cod_time; // 禮車訂單成立時間
	private Integer cfinal_price; 	// 禮車訂單最終價格
	private Integer cdeposit; 		// 禮車訂單訂金
	private Integer creturn_pay; 	// 禮車訂單押金
	private Date cneed_date; 	// 租車日
	private Date creturn_date;// 還車日
	private String cstart; 			// 起點
	private String cdest; 			// 終點
	private Integer cpay_status; 	// 禮車訂單付款狀態 (XX)
	private Integer cod_status; 	// 禮車訂單狀態
	/*********************** 評價相關 ***********************/
	private Integer crev_star; 		// 評價星數
	private String crev_msgs; 		// 評價內容
	private java.sql.Timestamp crev_time; // 評價時間  (XX)
	/*********************** 檢舉相關 ***********************/
	private Integer cvr_status; 	// 禮車廠商檢舉狀態
	private Integer cmr_status; 	// 禮車會員檢舉狀態
	private String cvr_desc; 		// 禮車廠商檢舉細節
	private String cmr_desc; 		// 禮車會員檢舉細節
	private String cvr_result; 		// 禮車廠商檢舉結果
	private String cmr_result; 		// 禮車會員檢舉結果
	
	

	/******* 訂單相關 *******/
	public String getCod_id() {
		return cod_id;
	}

	public void setCod_id(String cod_id) {
		this.cod_id = cod_id;
	}

	public String getMembre_id() {
		return membre_id;
	}

	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}

	public String getVender_id() {
		return vender_id;
	}

	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public java.sql.Timestamp getCod_time() {
		return cod_time;
	}

	public void setCod_time(java.sql.Timestamp cod_time) {
		this.cod_time = cod_time;
	}

	public Integer getCfinal_price() {
		return cfinal_price;
	}

	public void setCfinal_price(Integer cfinal_price) {
		this.cfinal_price = cfinal_price;
	}

	public Integer getCdeposit() {
		return cdeposit;
	}

	public void setCdeposit(Integer cdeposit) {
		this.cdeposit = cdeposit;
	}

	public Integer getCreturn_pay() {
		return creturn_pay;
	}

	public void setCreturn_pay(Integer creturn_pay) {
		this.creturn_pay = creturn_pay;
	}

	public Date getCneed_date() {
		return cneed_date;
	}

	public void setCneed_date(Date cneed_date) {
		this.cneed_date = cneed_date;
	}

	public Date getCreturn_date() {
		return creturn_date;
	}

	public void setCreturn_date(Date creturn_date) {
		this.creturn_date = creturn_date;
	}

	public String getCstart() {
		return cstart;
	}

	public void setCstart(String cstart) {
		this.cstart = cstart;
	}

	public String getCdest() {
		return cdest;
	}

	public void setCdest(String cdest) {
		this.cdest = cdest;
	}

	public Integer getCpay_status() {
		return cpay_status;
	}

	public void setCpay_status(Integer cpay_status) {
		this.cpay_status = cpay_status;
	}

	public Integer getCod_status() {
		return cod_status;
	}

	public void setCod_status(Integer cod_status) {
		this.cod_status = cod_status;
	}

	/******* 評價相關 *******/
	public Integer getCrev_star() {
		return crev_star;
	}

	public void setCrev_star(Integer crev_star) {
		this.crev_star = crev_star;
	}

	public String getCrev_msgs() {
		return crev_msgs;
	}

	public void setCrev_msgs(String crev_msgs) {
		this.crev_msgs = crev_msgs;
	}

	public java.sql.Timestamp getCrev_time() {
		return crev_time;
	}

	public void setCrev_time(java.sql.Timestamp crev_time) {
		this.crev_time = crev_time;
	}

	/******* 檢舉相關 *******/
	public Integer getCvr_status() {
		return cvr_status;
	}

	public void setCvr_status(Integer cvr_status) {
		this.cvr_status = cvr_status;
	}

	public Integer getCmr_status() {
		return cmr_status;
	}

	public void setCmr_status(Integer cmr_status) {
		this.cmr_status = cmr_status;
	}

	public String getCvr_desc() {
		return cvr_desc;
	}

	public void setCvr_desc(String cvr_desc) {
		this.cvr_desc = cvr_desc;
	}

	public String getCmr_desc() {
		return cmr_desc;
	}

	public void setCmr_desc(String cmr_desc) {
		this.cmr_desc = cmr_desc;
	}

	public String getCvr_result() {
		return cvr_result;
	}

	public void setCvr_result(String cvr_result) {
		this.cvr_result = cvr_result;
	}

	public String getCmr_result() {
		return cmr_result;
	}

	public void setCmr_result(String cmr_result) {
		this.cmr_result = cmr_result;
	}

}// end of CarOrderVO
