package net.youlvke.yanyuke.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.youlvke.yanyuke.controller.OptionsPickerView;
import net.youlvke.yanyuke.utils.CountDownTimerUtils;
import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 商家入驻界面
 */
public class BusinessHomeActivity extends BaseActivity {
    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<>();
    // 市数据集合
    private ArrayList<ArrayList<String>> mListCiry = new ArrayList<ArrayList<String>>();
    // 区数据集合
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<ArrayList<ArrayList<String>>>();


    private TextView tvTitle;
    private EditText etBusinessName;//商家名称
    private EditText etBusinessNumber;
    private EditText etTestCode;
    private Button btnTestCode;
    private TextView tvChoiceAddress;
    private EditText etDetailAddress;
    private RadioButton rbYes;
    private RadioButton rbNo;
    private RadioGroup rgBusinessGroup;
    private Button btnSubmitApply;//提交申请
    private OptionsPickerView<String> mOpv;
    private String address;
    private JSONObject mJsonObj;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_business_home;
    }

    @Override
    protected void initView() {
        mOpv = new OptionsPickerView<String>(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etBusinessName = (EditText) findViewById(R.id.et_business_name);
        etBusinessNumber = (EditText) findViewById(R.id.et_business_number);
        etTestCode = (EditText) findViewById(R.id.et_test_code);
        btnTestCode = (Button) findViewById(R.id.btn_business_test_code);
        tvChoiceAddress = (TextView) findViewById(R.id.tv_choice_address);
        etDetailAddress = (EditText) findViewById(R.id.et_detail_address);
        rbYes = (RadioButton) findViewById(R.id.rb_yes);
        rbNo= (RadioButton) findViewById(R.id.rb_no);
        rgBusinessGroup= (RadioGroup) findViewById(R.id.rg_business_group);
        btnSubmitApply= (Button) findViewById(R.id.btn_submit_apply);


    }

    @Override
    protected void initListener() {
       btnTestCode.setOnClickListener(this);
        tvChoiceAddress.setOnClickListener(this);
        btnSubmitApply.setOnClickListener(this);
        rgBusinessGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_yes:
                        break;
                    case R.id.rb_no:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText("商家入驻");
        // 初始化Json对象
        initJsonData();
        // 初始化Json数据
        initJsonDatas();
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.tv_choice_address:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                choiceCity();
                mOpv.show();
                break;
            case R.id.btn_submit_apply:
                String businessName = isEmtyContent(etBusinessName, "请输入您的名字");
                String BusinessNumber = isEmtyContent(etBusinessNumber, "请输入您的手机号");
                String TestCode = isEmtyContent(etTestCode, "请输入您的验证码");
                String DetailAddress = isEmtyContent(etDetailAddress, "请输入详细地址");
                if (TextUtils.isEmpty(address)){
                    ToastUtils.showToast(this,"请选择地");
                    return;
                }

                if (businessName != null && BusinessNumber != null && TestCode != null && DetailAddress != null){
                ToastUtils.showToast(this,"提交成功");
            }
                break;
            case R.id.btn_business_test_code:
                CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(
                        btnTestCode, 60000, 1000);
                countDownTimerUtils.start();
                break;

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
                address = mListProvince.get(options1)
                        + mListCiry.get(options1).get(option2)
                        + mListArea.get(options1).get(option2).get(options3);
                tvChoiceAddress.setText(address);

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
}
