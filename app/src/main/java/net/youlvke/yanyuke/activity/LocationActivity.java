package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.bean.CityModel;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.utils.XmlParserHandler;
import net.youlvke.yanyuke.view.RollCircleView;
import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.ProvinceModel;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 定位页面
 */
public class LocationActivity extends BaseActivity {


    private TextView tvTitle;
    private RelativeLayout rlBgColor;
    private TextView tvArea;
    private Button btnSelectArea;
    private List<ProvinceModel> provinceList;
    private RollCircleView rcvSelectAddress;
    private String provinceName;
    private String cityName;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        initProvinceDatas();//初始化数据
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvArea = (TextView) findViewById(R.id.tv_area);
        rcvSelectAddress = (RollCircleView) findViewById(R.id.rcv_select_address);
        btnSelectArea = (Button) findViewById(R.id.btn_select_area);
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);



    }

    @Override
    protected void initListener() {
        btnSelectArea.setOnClickListener(this);
        rcvSelectAddress.setSelectChangeListener(new OnSelectChangedListener());
        rcvSelectAddress.setProvinceList(provinceList);

    }

    @Override
    protected void initData() {
        tvTitle.setVisibility(View.GONE);
        rlBgColor.setBackgroundColor(Color.TRANSPARENT);

    }

    protected void initProvinceDatas() {
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.btn_select_area:
                Intent intent = new Intent();
                intent.putExtra("result", cityName);
                ToastUtils.showToast(this, cityName);
                setResult(100, intent);
                finish();
                break;

        }
    }

    /**
     *
     */
    private class OnSelectChangedListener implements RollCircleView.SelectChangedListener {
        @Override
        public void onChange(int province, int cityinprovince) {
            ProvinceModel provinceModel = provinceList.get(province);
            provinceName = provinceModel.getName();
            List<CityModel> cityList = provinceModel.getCityList();
            CityModel cityModel = cityList.get(cityinprovince);
            cityName = cityModel.getName();
            tvArea.setText(provinceName + "-" + cityName);
        }
    }

}
