package net.youlvke.yanyuke.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import net.youlvke.yanyuke.MyApplication;
import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.activity.HomeFoodThemeActivity;
import net.youlvke.yanyuke.activity.LocationActivity;
import net.youlvke.yanyuke.activity.SearchActivity;
import net.youlvke.yanyuke.activity.ShopDetailActivity;
import net.youlvke.yanyuke.activity.WinBillDetailActivity;
import net.youlvke.yanyuke.bean.HomeBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.service.LocationService;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CustomImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

import static net.youlvke.yanyuke.R.id.rl_home_theme_physical;
import static net.youlvke.yanyuke.R.id.tv_home_theme_it;
import static net.youlvke.yanyuke.R.id.tv_home_theme_play;
import static net.youlvke.yanyuke.utils.Constants.BASEURL_YYKGOODS;

/**
 * 这个是主界面
 *
 * @author Administrator
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class HomeFragment extends BaseFragment {


    private static final int REQUEST_CODE = 111;
    private BGABadgeImageView ivMess;
    private EditText etSearch;//搜索框
    private GridView gvHotRecommond;
    private LayoutInflater inflater;
    private RelativeLayout rlHomeThemFood;
    private TextView tvChoiseArea;
    private Banner banner;
    private List<String> imageViews = null;
    private LocationService locationService;
    private SharedPreferences sp;
    public static double longitude;//经度
    public static double latitude;//维度
    public static String city;
    private ImageView ivRefresh;
    private int TYPE_LEFT = 0;
    private int TYPE_RIGHT = 1;
    private HomeBean.DataBean datas;
    private MyAdapter mAdapter;
    private List<HomeBean.DataBean.HotBean> hotList = new ArrayList();
    private List<Long> hotGoodsId;
    private List<Long> topList;
    private Intent intent;
    private RelativeLayout rlHomeThemePhysical;
    private RelativeLayout tvHomeThemeIt;
    private RelativeLayout tvHomeThemePlay;


    @Override
    public View initView() {
        setStatusColor(getResources().getColor(R.color.main));
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gvHotRecommond = (GridView) view.findViewById(R.id.gv_hot_recommond);
        ivMess = (BGABadgeImageView) view.findViewById(R.id.iv_mess);
        tvChoiseArea = (TextView) view.findViewById(R.id.tv_choise_area);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        banner = (Banner) view.findViewById(R.id.banner);
        rlHomeThemFood = (RelativeLayout) view.findViewById(R.id.rl_home_theme_food);
        rlHomeThemePhysical = (RelativeLayout) view.findViewById(R.id.rl_home_theme_physical);
        tvHomeThemeIt = (RelativeLayout) view.findViewById(R.id.tv_home_theme_it);
        tvHomeThemePlay = (RelativeLayout) view.findViewById(R.id.tv_home_theme_play);

        ivRefresh = (ImageView) view.findViewById(R.id.iv_refresh);
        sp = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        intent = new Intent();

        return view;
    }


    /**
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                city = location.getDistrict();
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                sp.edit().putString("district", city).commit();
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */

                /*if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                    }
                }*/

                if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    ToastUtils.showToast(getActivity(), "离线定位成功");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    ToastUtils.showToast(getActivity(), "服务端网络定位失败");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    ToastUtils.showToast(getActivity(), "网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    ToastUtils.showToast(getActivity(), "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                String string = sp.getString("district", "杭州");
                if (string.equals(city)) {
                    return;
                } else {
                    tvChoiseArea.setText(string);
                }
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void initListener() {
        etSearch.setOnFocusChangeListener(new OnSearchFocusChangeListenerImplementation());
        ivMess.setOnClickListener(this);
        rlHomeThemFood.setOnClickListener(this);
        rlHomeThemePhysical.setOnClickListener(this);
        tvHomeThemeIt.setOnClickListener(this);
        tvHomeThemePlay.setOnClickListener(this);
        tvChoiseArea.setOnClickListener(this);
        banner.setOnBannerClickListener(new MyOnBannerClickListener());
        ivRefresh.setOnClickListener(this);
        gvHotRecommond.setOnItemClickListener(new MyOnItemClickListener());
    }

    @Override
    public void initData() {
        mAdapter = new MyAdapter();
        loadData();
    }

    private void loadBanner() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < datas.getTop().size(); i++) {
            imageViews.add(datas.getTop().get(i).getPictureUrl() + "?x-oss-process=style/Android_INDEX");
        }
        banner.setImageLoader(new GlideMyImageLoader());
        banner.setImages(imageViews);
        banner.setBannerAnimation(Transformer.Default);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.start();
        ivMess.showCirclePointBadge();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        try {
            IRequest.post(getActivity(), BASEURL_YYKGOODS, createCodeParams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("onSuccess", "onSuccess: " + response.get());
                    try {
                        Gson gson = new Gson();
                        HomeBean homeBean = gson.fromJson(response.get(), HomeBean.class);
                        if (homeBean.getCode() == 1 && homeBean.getData() != null) {
                            datas = homeBean.getData();
                            try {
                                loadBanner();
                                refreshData();
                                createHotGoodsIdList();
                                createTopGoodsIdList();
                            } catch (NullPointerException e) {
                                ToastUtils.showToast(mContext, "服务器不能用了");
                            }
                        } else {
                            ToastUtils.showToast(getActivity(), "服务器异常");
                        }
                    } catch (JsonSyntaxException e) {
                        ToastUtils.showToast(getActivity(), "数据解析错误");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTopGoodsIdList() {
        topList = new ArrayList<>();
        for (int i = 0; i < datas.getTop().size(); i++) {
            topList.add(datas.getTop().get(i).getGoodsId());
        }
    }


    private void createHotGoodsIdList() {
        hotGoodsId = new ArrayList<>();
        for (int i = 0; i < datas.getHot().size(); i++) {
            hotGoodsId.add(datas.getHot().get(i).getGoodsId());
        }
    }

    private void refreshData() {
        try {
            List<HomeBean.DataBean.HotBean> hot = datas.getHot();
            if (hot.size() > 0) {
                Log.d("refreshData", "refreshData: " + hot.size());
                Random random = new Random();
                for (int i = 0; i < 4; i++) {
                    int index = random.nextInt(hot.size());
                    if (hotList.contains(hot.get(index))) {
                        i--;
                        continue;
                    } else {
                        hotList.add(hot.get(index));
                    }
                }
                gvHotRecommond.setAdapter(mAdapter);
            } else {
                ToastUtils.showToast(mContext, "服务器没有数据");
            }
        } catch (IllegalArgumentException e) {
            ToastUtils.showToast(mContext, "服务器没有数据");
        }
    }

    /**
     * 设置请求参数
     */
    private Map createCodeParams() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160213");
        params.put("goodsCity", "杭州市");
        return params;
    }

    /**
     * 图片加载器
     */
    private class GlideMyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            JumpGoodsDetail(position, 1);
            Log.d("JumpGoodsDetail", "MyOnItemClickListener: " + position);
        }
    }

    private class MyOnBannerClickListener implements OnBannerClickListener {
        @Override
        public void OnBannerClick(int position) {
            JumpGoodsDetail(position, 0);
        }
    }

    private void JumpGoodsDetail(int position, int type) {
        intent.setClass(getActivity(), ShopDetailActivity.class);
        if (type == 0) {
            intent.putExtra("goodsId", datas.getTop().get(position - 1).getGoodsId());
            startActivity(intent);
        } else if (type == 1) {
            intent.putExtra("goodsId", hotList.get(position).getGoodsId());
            startActivity(intent);
        }
    }

    /**
     * 当搜索框的焦点发生变化时
     */
    private class OnSearchFocusChangeListenerImplementation implements
            OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v.hasFocus()) {
                intent.setClass(getActivity(), SearchActivity.class);
                intent.putExtra("search", "你好");
                startActivity(intent);
                v.clearFocus();//清除焦点
            }
        }
    }


    /**
     * 点击监听事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mess:
                startActivity(new Intent(getActivity(), WinBillDetailActivity.class));
                break;
            case R.id.rl_home_theme_food:
                intent.putExtra("type", 1);
                intent.setClass(getActivity(), HomeFoodThemeActivity.class);
                startActivity(intent);
                break;
            case rl_home_theme_physical:
                intent.putExtra("type", 2);
                intent.setClass(getActivity(), HomeFoodThemeActivity.class);
                startActivity(intent);
                break;
            case tv_home_theme_it:
                intent.putExtra("type", 3);
                intent.setClass(getActivity(), HomeFoodThemeActivity.class);
                startActivity(intent);
                break;
            case tv_home_theme_play:
                intent.putExtra("type", 4);
                intent.setClass(getActivity(), HomeFoodThemeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_choise_area:
                intent.setClass(getActivity(), LocationActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_refresh:
                if (hotList != null)
                    hotList.clear();
                if (datas == null) {
                    ToastUtils.showToast(getActivity(), "网络连接异常！");
                    return;
                }
                refreshData();
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hotList.size();
        }

        @Override
        public Object getItem(int i) {
            return hotList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_hot_recommend, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (hotList != null) {
                holder.tvGoodsDetail.setText(hotList.get(position).getGoodsName());
                Glide.with(getActivity())
                        .load(hotList.get(position).getPictureUrl() + "?x-oss-process=style/Android_POPULAR")
                        .into(holder.tvGoodsDetailPic);
            }
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tvGoodsDetail;
            public CustomImageView tvGoodsDetailPic;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvGoodsDetail = (TextView) rootView.findViewById(R.id.tv_home_goods_detail);
                this.tvGoodsDetailPic = (CustomImageView) rootView.findViewById(R.id.tv_goods_detail_pic);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 100) {
            String result = data.getStringExtra("result");
            tvChoiseArea.setText(result);
        }
    }

    @Override
    public void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        banner.stopAutoPlay();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.releaseBanner();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
        locationService = ((MyApplication) getActivity().getApplicationContext()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();


    }
}
