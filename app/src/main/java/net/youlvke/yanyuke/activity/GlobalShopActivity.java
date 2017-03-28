package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.GlobalBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.MyGridView;
import net.youlvke.yanyuke.view.PullToRefreshView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.progressBar;


/**
 * 中心界面
 */

public class GlobalShopActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private TextView tvTitle;
    private ImageView ivSearch;
    private RelativeLayout rlBgColor;
    private EditText etInput;
    private ImageView ivClean;
    private TextView tvSearch;
    private GridView gvLabel;
    private ListView lvHistorySearch;
    private PullToRefreshView mPullToRefreshView;
    private int pageNum = 1;
    private GlobalBean.DataBean datas;
    private ImageView ivHot1;
    private TextView ivHot1ProgressText;
    private TextView tvHot1Detail;
    private ProgressBar progress;
    private TextView tvHot1Residue;
    private ImageView ivHot2;
    private TextView ivHot2ProgressText;
    private TextView tvHot2Detail;
    private ProgressBar progress2;
    private TextView tvHot2Residue;
    private ImageView ivHot3;
    private TextView ivHot3ProgressText;
    private TextView tvHot3Detail;
    private ProgressBar progress3;
    private TextView tvHot3Residue;
    private Banner middleBanner;
    private List<String> imageViews;
    private TextView tvNew1ProgressText;
    private TextView tvNew1Reduce;
    private ProgressBar progressNew5;
    private TextView tvNewTitle;
    private TextView tvNew2ProgressText;
    private TextView tvNew2Reduce;
    private TextView tvNew2Title;
    private ImageView ivHot3Pic;
    private ProgressBar progressNew7;
    private TextView tvNew3Title;
    private ImageView ivNew2Pic;
    private ImageView ivNew1Pic;
    private ImageView ivNew3Pic;
    private ProgressBar progressNew6;
    private TextView tvNew3ProgressText;
    private TextView tvNew3Reduce;
    private MyGridView gvShopDetail;
    private MyAdapter adapter;
    private Intent intent;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_center;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_Refresh);
        //hot1
        ivHot1 = (ImageView) findViewById(R.id.iv_hot1);
        ivHot1ProgressText = (TextView) findViewById(R.id.tv_hot1_progress_text);
        tvHot1Detail = (TextView) findViewById(R.id.tv_hot1_detail);
        progress = (ProgressBar) findViewById(progressBar);
        tvHot1Residue = (TextView) findViewById(R.id.tv_hot1_residue);

//      hot2
        ivHot2 = (ImageView) findViewById(R.id.iv_hot2);
        ivHot2ProgressText = (TextView) findViewById(R.id.tv_hot2_progress_text);
        tvHot2Detail = (TextView) findViewById(R.id.tv_hot2_detail);
        progress2 = (ProgressBar) findViewById(R.id.progressBar2);
        tvHot2Residue = (TextView) findViewById(R.id.tv_hot2_residue);
        //hot3
        ivHot3 = (ImageView) findViewById(R.id.iv_hot3);
        ivHot3ProgressText = (TextView) findViewById(R.id.tv_hot3_progress_text);
        tvHot3Detail = (TextView) findViewById(R.id.tv_hot3_detail);
        progress3 = (ProgressBar) findViewById(R.id.progressBar3);
        tvHot3Residue = (TextView) findViewById(R.id.tv_hot3_residue);
        //banner
        middleBanner = (Banner) findViewById(R.id.banner2);
        //新品推荐 new1
        ivNew1Pic = (ImageView) findViewById(R.id.iv_new1_pic);
        tvNew1ProgressText = (TextView) findViewById(R.id.tv_new1_progress_text);
        tvNew1Reduce = (TextView) findViewById(R.id.rv_new1_reduce);
        progressNew5 = (ProgressBar) findViewById(R.id.progress5);
        tvNewTitle = (TextView) findViewById(R.id.tv_new_title);
        //new1
        ivNew2Pic = (ImageView) findViewById(R.id.iv_new2_pic);
        tvNew2ProgressText = (TextView) findViewById(R.id.tv_new2_progress_text);
        tvNew2Reduce = (TextView) findViewById(R.id.tv_new2_reduce);
        progressNew6 = (ProgressBar) findViewById(R.id.progress6);
        tvNew2Title = (TextView) findViewById(R.id.tv_new2_title);
        //new3
        ivNew3Pic = (ImageView) findViewById(R.id.iv_new3_pic);
        tvNew3ProgressText = (TextView) findViewById(R.id.tv_new3_progress_text);
        tvNew3Reduce = (TextView) findViewById(R.id.tv_new3_reduce);
        progressNew7 = (ProgressBar) findViewById(R.id.progress7);
        tvNew3Title = (TextView) findViewById(R.id.tv_new3_title);
        gvShopDetail = (MyGridView) findViewById(R.id.gd_shop_detail);

        rlBgColor.setBackgroundColor(Color.parseColor("#161d31"));
        ivSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivSearch.setOnClickListener(this);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        ivHot1.setOnClickListener(this);
        ivHot2.setOnClickListener(this);
        ivHot3.setOnClickListener(this);
        ivNew1Pic.setOnClickListener(this);
        ivNew2Pic.setOnClickListener(this);
        ivNew3Pic.setOnClickListener(this);
        middleBanner.setOnBannerClickListener(new MyOnBannerClickListener());
        gvShopDetail.setOnItemClickListener(new MyOnItemClickListener());
    }

    @Override
    protected void initData() {
        intent = new Intent();
        tvTitle.setText("全球购");
        adapter = new MyAdapter();
        loadData();
    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createPersonalParams())
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            try {
                                Gson gson = new Gson();
                                GlobalBean globalBean = gson.fromJson(response.get(), GlobalBean.class);
                                if (globalBean.getCode() == 1 && globalBean.getData() != null) {
                                    datas = globalBean.getData();
                                    try {
                                        initUi();
                                    } catch (NullPointerException e) {
                                        ToastUtils.showToast(GlobalShopActivity.this, "服务器没有数据！");
                                    }
                                } else {
                                    ToastUtils.showToast(GlobalShopActivity.this, "服务器数据异常！");
                                }
                            } catch (JsonParseException e) {
                                ToastUtils.showToast(GlobalShopActivity.this, "数据解析异常！");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initUi() {
        //hot1
        loadPic(0, "?x-oss-process=style/Android_NATIONAL1", ivHot1);
        ivHot1ProgressText.setText(String.format("进度%s%%", calcProgress(datas.getHot().get(0).getSalesCount(), datas.getHot().get(0).getTotalCount())));
        progress.setProgress(calcProgress(datas.getHot().get(0).getSalesCount(), datas.getHot().get(0).getTotalCount()));
        tvHot1Detail.setText(datas.getHot().get(0).getGoodsDescription());
        tvHot1Residue.setText(String.format("剩余%s份", String.valueOf(datas.getHot().get(0).getStockCount())));
        //hot2
        loadPic(1, "?x-oss-process=style/Android_NATIONAL2", ivHot2);
        ivHot2ProgressText.setText(String.format("进度%s%%", calcProgress(datas.getHot().get(1).getSalesCount(), datas.getHot().get(1).getTotalCount())));
        progress2.setProgress(calcProgress(datas.getHot().get(1).getSalesCount(), datas.getHot().get(1).getTotalCount()));
        tvHot2Detail.setText(datas.getHot().get(1).getGoodsDescription());
        tvHot2Residue.setText(String.format("剩余%s份", String.valueOf(datas.getHot().get(1).getStockCount())));
        //hot3
        loadPic(2, "?x-oss-process=style/Android_NATIONAL2", ivHot3);
        ivHot3ProgressText.setText(String.format("进度%s%%", calcProgress(datas.getHot().get(2).getSalesCount(), datas.getHot().get(2).getTotalCount())));
        progress3.setProgress(calcProgress(datas.getHot().get(2).getSalesCount(), datas.getHot().get(2).getTotalCount()));
        tvHot3Detail.setText(datas.getHot().get(2).getGoodsDescription());
        tvHot3Residue.setText(String.format("剩余%s份", String.valueOf(datas.getHot().get(2).getStockCount())));
        //轮播图
        loadBanner();
        //新品推荐
        List<GlobalBean.DataBean.NewBean> aNew = datas.getNew();

        loadNewPic(0, "?x-oss-process=style/Android_NATIONAL4", ivNew1Pic);
        int progress = calcProgress(aNew.get(0).getSalesCount(), aNew.get(0).getTotalCount());
        tvNew1ProgressText.setText(String.format("进度%s%%", String.valueOf(progress)));
        tvNew3Reduce.setText(String.format("剩余%s份", String.valueOf(aNew.get(0).getStockCount())));
        progressNew5.setProgress(progress);
        tvNewTitle.setText(aNew.get(0).getGoodsName());

        loadNewPic(1, "?x-oss-process=style/Android_NATIONAL5", ivNew2Pic);
        int progress1 = calcProgress(aNew.get(1).getSalesCount(), aNew.get(1).getTotalCount());
        tvNew2ProgressText.setText(String.format("进度%s%%", String.valueOf(progress1)));
        tvNew3Reduce.setText(String.format("剩余%s份", String.valueOf(aNew.get(1).getStockCount())));
        progressNew6.setProgress(progress1);
        tvNew2Title.setText(aNew.get(1).getGoodsName());

        loadNewPic(2, "?x-oss-process=style/Android_NATIONAL5", ivNew3Pic);
        int progress3 = calcProgress(aNew.get(2).getSalesCount(), aNew.get(2).getTotalCount());
        tvNew3ProgressText.setText(String.format("进度%s%%", String.valueOf(progress3)));
        tvNew3Reduce.setText(String.format("剩余%s份", String.valueOf(aNew.get(2).getStockCount())));
        progressNew7.setProgress(progress3);
        tvNew3Title.setText(aNew.get(2).getGoodsName());
        gvShopDetail.setAdapter(adapter);
    }

    private void loadBanner() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < datas.getGeneralize().size(); i++) {
            imageViews.add(datas.getGeneralize().get(i).getPictureUrl() + "?x-oss-process=style/Android_NATIONAL3");
        }
        middleBanner.setImageLoader(new GlideMyImageLoader());
        middleBanner.setImages(imageViews);
        middleBanner.setBannerAnimation(Transformer.Default);
        middleBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        middleBanner.start();
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            intent.setClass(GlobalShopActivity.this, ShopDetailActivity.class);
            intent.putExtra("goodsId", datas.getAll().get(i).getGoodsId());
            startActivity(intent);
        }
    }

    private class MyOnBannerClickListener implements OnBannerClickListener {
        @Override
        public void OnBannerClick(int position) {
            intent.setClass(GlobalShopActivity.this, ShopDetailActivity.class);
            intent.putExtra("goodsId", datas.getGeneralize().get(position - 1).getGoodsId());
            startActivity(intent);
        }
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

    /**
     * 加载图片
     *
     * @param index
     * @param style
     */
    private void loadPic(int index, String style, ImageView imageview) {
        Glide.with(this)
                .load(datas.getHot().get(index).getPictureUrl() + style)
                .into(imageview);
    }

    /**
     * 加载图片
     *
     * @param index
     * @param style
     */
    private void loadNewPic(int index, String style, ImageView imageview) {
        Glide.with(this)
                .load(datas.getNew().get(index).getPictureUrl() + style)
                .into(imageview);
    }

    private int calcProgress(int sales, int total) {
        return (int) ((double) sales / (double) total * 100);
    }

    private Map createPersonalParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160212");
        params.put("goodsCategory", "5");
        params.put("pageNum", String.valueOf(pageNum));
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.iv_search:
                ShowSearchDialog();
                break;
            case R.id.iv_hot1:
                hotToShopDetail(0);
                break;
            case R.id.iv_hot2:

                hotToShopDetail(1);
                break;
            case R.id.iv_hot3:

                hotToShopDetail(2);
                break;
            case R.id.iv_new1_pic:

                newToShopDetail(0);
                break;
            case R.id.iv_new2_pic:

                newToShopDetail(1);
                break;
            case R.id.iv_new3_pic:

                newToShopDetail(2);
                break;

            default:
                break;
        }
    }

    private void hotToShopDetail(int index) {
        if (datas == null)
            return;
        intent.setClass(GlobalShopActivity.this, ShopDetailActivity.class);
        intent.putExtra("goodsId", datas.getHot().get(index).getGoodsId());
        startActivity(intent);
    }

    private void newToShopDetail(int index) {
        if (datas == null)
            return;
        intent.setClass(GlobalShopActivity.this, ShopDetailActivity.class);
        intent.putExtra("goodsId", datas.getNew().get(index).getGoodsId());
        startActivity(intent);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.getAll().size();
        }

        @Override
        public Object getItem(int position) {
            return datas.getAll().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_global, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            List<GlobalBean.DataBean.AllBean> all = datas.getAll();
            Glide.with(GlobalShopActivity.this)
                    .load(all.get(position).getPictureUrl() + "?x-oss-process=style/Android_NATIONAL6")
                    .into(holder.ivAllPic);
            int progress = calcProgress(all.get(position).getSalesCount(), all.get(position).getTotalCount());
            holder.progressAll.setProgress(progress);
            holder.tvAllProgressText.setText(String.format("参与进度%s%%", String.valueOf(progress)));
            holder.tvAllReduce.setText(String.format("剩余%s份", String.valueOf(all.get(position).getStockCount())));
            holder.tvAllTitle.setText(all.get(position).getGoodsName());
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public ImageView ivAllPic;
            public TextView tvAllProgressText;
            public TextView tvAllReduce;
            public ProgressBar progressAll;
            public TextView tvAllTitle;
            public CardView tvCode;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.ivAllPic = (ImageView) rootView.findViewById(R.id.iv_all_pic);
                this.tvAllProgressText = (TextView) rootView.findViewById(R.id.tv_all_progress_text);
                this.tvAllReduce = (TextView) rootView.findViewById(R.id.tv_all_reduce);
                this.progressAll = (ProgressBar) rootView.findViewById(R.id.progress_all);
                this.tvAllTitle = (TextView) rootView.findViewById(R.id.tv_all_title);
                this.tvCode = (CardView) rootView.findViewById(R.id.tv_code);
            }

        }
    }

    /**
     * 显示搜索弹窗
     */
    private void ShowSearchDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.theme_dialog).create();
        // 调用系统的输入法
        dialog.setView(new EditText(this));
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_global_search);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        etInput = (EditText) window.findViewById(R.id.et_input);
        tvSearch = (TextView) window.findViewById(R.id.tv_search);
        ivClean = (ImageView) window.findViewById(R.id.iv_clean);
        gvLabel = (GridView) window.findViewById(R.id.gv_label);
        lvHistorySearch = (ListView) window.findViewById(R.id.lv_history_search);

        final String msg = etInput.getText().toString().trim();
        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (view.hasFocus()) {
                    ivClean.setVisibility(View.VISIBLE);
                } else {
                    ivClean.setVisibility(View.INVISIBLE);
                }
            }
        });
        ivClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etInput.setText("");
            }
        });

        String[] labelDatas = {"美食控", "颜值控", "爱出游", "幻想派", "能吃苦"};
        gvLabel.setAdapter(new ArrayAdapter<String>(GlobalShopActivity.this, R.layout.item_label_btn, R.id.tv_label_btn, labelDatas));
        gvLabel.setOnItemClickListener(this);
        String[] historyDatas = {"轩尼诗XO", "拉菲特", "Zippo"};
        lvHistorySearch.setAdapter(new ArrayAdapter<String>(GlobalShopActivity.this,
                R.layout.item_text, R.id.tv_history, historyDatas));
        lvHistorySearch.setOnItemClickListener(this);
        dialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                /** 联网加载数据 */
//                adapter.notifyDataSetChanged();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
//				listDrawable.add(R.drawable.car_gridview);
//                adapter.notifyDataSetChanged();
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);
    }

    /**
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.lv_history_search:
                ToastUtils.showToast(GlobalShopActivity.this, "" + i);
                break;
            case R.id.gv_label:
                ToastUtils.showToast(GlobalShopActivity.this, "" + i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        middleBanner.stopAutoPlay();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        middleBanner.releaseBanner();
    }

    @Override
    public void onStart() {
        super.onStart();
        middleBanner.startAutoPlay();
    }
}
