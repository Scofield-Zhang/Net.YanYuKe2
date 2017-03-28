package net.youlvke.yanyuke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 地址
 * 
 * @author Administrator
 * 
 */
public class AddressInfo implements Serializable {

	/**
	 * code : 1
	 * data : [{"addTime":"2017-01-22 16:00:00","addressDetail":"杭州市江干区水墩村5组53号","addressId":1,"city":"杭州","contactName":"宋志颖","contactPhone":"13057558530","contatctZipCode":"333333","defaultFlag":1,"delFlag":0,"province":"浙江","region":"江干区","userId":1},{"addTime":"2017-01-23 15:00:00","addressDetail":"浙江省温州市苍南县龙港镇","addressId":2,"city":"温州","contactName":"宋志颖","contactPhone":"13057558530","contatctZipCode":"2222221","defaultFlag":0,"delFlag":0,"province":"浙江","region":"苍南县","userId":1}]
	 * message : 获取成功
	 */

	private int code;
	private String message;
	private List<DataBean> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

	public static class DataBean implements Serializable{
		/**
		 * addTime : 2017-01-22 16:00:00
		 * addressDetail : 杭州市江干区水墩村5组53号
		 * addressId : 1
		 * city : 杭州
		 * contactName : 宋志颖
		 * contactPhone : 13057558530
		 * contatctZipCode : 333333
		 * defaultFlag : 1
		 * delFlag : 0
		 * province : 浙江
		 * region : 江干区
		 * userId : 1
		 */

		private String addTime;
		private String addressDetail;
		private String addressId;
		private String city;
		private String contactName;
		private String contactPhone;
		private String contatctZipCode;
		private String defaultFlag;
		private String delFlag;
		private String province;
		private String region;
		private String userId;

		public String getAddTime() {
			return addTime;
		}

		public void setAddTime(String addTime) {
			this.addTime = addTime;
		}

		public String getAddressDetail() {
			return addressDetail;
		}

		public void setAddressDetail(String addressDetail) {
			this.addressDetail = addressDetail;
		}

		public String getAddressId() {
			return addressId;
		}

		public void setAddressId(String addressId) {
			this.addressId = addressId;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactPhone() {
			return contactPhone;
		}

		public void setContactPhone(String contactPhone) {
			this.contactPhone = contactPhone;
		}

		public String getContatctZipCode() {
			return contatctZipCode;
		}

		public void setContatctZipCode(String contatctZipCode) {
			this.contatctZipCode = contatctZipCode;
		}

		public String getDefaultFlag() {
			return defaultFlag;
		}

		public void setDefaultFlag(String defaultFlag) {
			this.defaultFlag = defaultFlag;
		}

		public String getDelFlag() {
			return delFlag;
		}

		public void setDelFlag(String delFlag) {
			this.delFlag = delFlag;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	}
}
