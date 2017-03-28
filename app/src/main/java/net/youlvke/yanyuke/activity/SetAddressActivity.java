package net.youlvke.yanyuke.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.AddressInfo;
import net.youlvke.yanyuke.controller.OptionsPickerView;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.tv_title;
import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 管理收货地址
 */
public class SetAddressActivity extends BaseActivity {
	// 省数据集合
	private ArrayList<String> mListProvince = new ArrayList<>();
	// 市数据集合
	private ArrayList<ArrayList<String>> mListCiry = new ArrayList<ArrayList<String>>();
	// 区数据集合
	private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<ArrayList<ArrayList<String>>>();
	private TextView tvChoice;
	private View tvAddressMasker;
	private JSONObject mJsonObj;
	private OptionsPickerView<String> mOpv;
	private AddressInfo addressInfo;
	private EditText etConsinee;// 收货人
	private EditText tvTelNumber;// 电话号码
	private EditText etDetailAddress;
	private ImageView isDefualt;
	private Button btnSave;
	private Boolean flag = true;
	private TextView tvTitle;
	private int choiceFlag = 1;
	private String mProvince;
	private String mCity;
	private String mArea;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_select_address;
	}

	@Override
	protected void initListener() {
		tvChoice.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		isDefualt.setOnClickListener(this);
	}
	protected void initView() {
		mOpv = new OptionsPickerView<>(this);
		tvAddressMasker = findViewById(R.id.tv_address_Masker);
		tvChoice = (TextView) findViewById(R.id.tv_choice);
		etConsinee = (EditText) findViewById(R.id.et_consinee);
		tvTelNumber = (EditText) findViewById(R.id.et_tel_number);
		etDetailAddress = (EditText) findViewById(R.id.et_detail_address);
		isDefualt = (ImageView) findViewById(R.id.is_defualt);
		btnSave = (Button) findViewById(R.id.btn_save);
		tvTitle = (TextView) findViewById(tv_title);
	}

	@Override
	protected void initData() {
		tvTitle.setText("管理收货地址");
		// 初始化Json对象
		initJsonData();
		// 初始化Json数据
		initJsonDatas();

	}

	@Override
	protected void onInnerClick(View v) {
		super.onInnerClick(v);
		switch (v.getId()) {
			case R.id.tv_choice:
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
				choiceCity();
				mOpv.show();
				break;
			case R.id.btn_save:// 提交

				String Cansinee = isEmtyContent(etConsinee, "收货人地址不能为空");// 收货人
				String telNumber = isEmtyContent(tvTelNumber, "电话号码不能为空");// 电话号码
				String detailAddress = isEmtyContent(etDetailAddress, "详细地址不能为空");

				if (Cansinee == null && telNumber == null && mProvince == null
						&& detailAddress == null) {
					return;
				}
				createAlterParams(detailAddress, Cansinee, telNumber,
						mCity, mArea, mProvince, String.valueOf(choiceFlag));
				break;

			case R.id.is_defualt:
				if (flag) {
					isDefualt.setImageResource(R.mipmap.btn_no_choice);
					choiceFlag = 0;
				} else {
					isDefualt.setImageResource(R.mipmap.btn_yes_choice);
					choiceFlag = 1;
				}
				flag = !flag;
			default:
				break;
		}

	}

	private void createAlterParams(String addressDetail, String contactName, String contactPhone,
								   String setCity, final String region, String province, String defaultFlag) {
		try {
			IRequest.post(this, Constants.BASEURL_YYKUSER,createGetAddress( addressDetail, contactName, contactPhone,
                     setCity, region, province, defaultFlag)).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
					Log.d("createAlterParams", "onSuccess: "+response.get());
					try {
						JSONObject jsonObject = new JSONObject(response.get());
						int code = jsonObject.getInt("code");
						if (code == 1){
							ToastUtils.showToast(SetAddressActivity.this,"添加成功");
							finish();
						}else if (code == 2){
							ToastUtils.showToast(SetAddressActivity.this,"添加失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
            });
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private Map createGetAddress(String addressDetail,String contactName,String contactPhone,
								 String setCity,String region,String province,String defaultFlag) {
		HashMap<String, String> params = new HashMap<>();
		params.put("action", "20160107");
		params.put("userId", String.valueOf(userId));
		params.put("sessionToken", session);
		params.put("addressDetail", addressDetail);
		params.put("contactName", contactName);
		params.put("contactPhone", contactPhone);
		params.put("city", setCity);
		params.put("region", region);
		params.put("province", province);
		params.put("defaultFlag", defaultFlag);
		return params;
	}
	/**
	 * 非空判断
	 * 
	 * @param text
	 * @return
	 */
	private String isEmtyContent(EditText text, String text1) {
		String trim = text.getText().toString().trim();
		if (TextUtils.isEmpty(trim)) {
			ToastUtils.showToast(this, text1);
			return null;
		} else {
			return trim;
		}

	}
	/** 选择城市*/
	private void choiceCity() {
		mOpv.setPicker(mListProvince, mListCiry, mListArea, true);
		// 设置是否循环滚动
		mOpv.setCyclic(false, false, false);
		// 设置默认选中的三级项目
		mOpv.setSelectOptions(0, 0, 0);
		// 监听确定选择按钮
		mOpv.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				mProvince = mListProvince.get(options1);
				mCity = mListCiry.get(options1).get(option2);
				mArea = mListArea.get(options1).get(option2).get(options3);
				tvChoice.setText(mProvince + mCity + mArea);

			}
		});
	}

	/** 从assert文件夹中读取省市区的json文件，然后转化为json对象 */
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "UTF-8"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/** 初始化Json数据，并释放Json对象 */
	private void initJsonDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);// 获取每个省的Json对象
				String province = jsonP.getString("name");

				ArrayList<String> options2Items_01 = new ArrayList<String>();
				ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
				JSONArray jsonCs = jsonP.getJSONArray("city");
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
					String city = jsonC.getString("name");
					options2Items_01.add(city);// 添加市数据
					ArrayList<String> options3Items_01_01 = new ArrayList<String>();
					JSONArray jsonAs = jsonC.getJSONArray("area" + "" + "");
					for (int k = 0; k < jsonAs.length(); k++) {
						options3Items_01_01.add(jsonAs.getString(k));// 添加区数据
					}
					options3Items_01.add(options3Items_01_01);
				}
				mListProvince.add(province);// 添加省数据
				mListCiry.add(options2Items_01);
				mListArea.add(options3Items_01);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mOpv != null && mOpv.isShowing()) {
				mOpv.dismiss();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
