package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.ShopListBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.PullToRefreshView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 美食主题
 */
public class HomeFoodThemeActivity extends BaseActivity implements
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener,
        AdapterView.OnItemClickListener {

    private PullToRefreshView ptrHomeFood;
    private ListView lvFoodTheme;
    private MyAdapter adapter;
    private int type;
    private int type_pic;
    private RelativeLayout rlTheme;
    private int pageNum = 1;
    private List<ShopListBean.DataBean> datas;
    private Intent intent;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_home_food_theme;
    }

    @Override
    protected void initView() {
        intent = new Intent();
        type = getIntent().getIntExtra("type", 1);
        ptrHomeFood = (PullToRefreshView) findViewById(R.id.ptr_home_food);
        lvFoodTheme = (ListView) findViewById(R.id.lv_food_theme);
        rlTheme = (RelativeLayout) findViewById(R.id.rl_theme);


    }

    @Override
    protected void initListener() {
        ptrHomeFood.setOnHeaderRefreshListener(this);
        ptrHomeFood.setOnFooterRefreshListener(this);
        lvFoodTheme.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        initHead();
        adapter = new MyAdapter();

    }

    private void loadData(int type) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createParams(type))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("HomeFoodThemeActivity", "onSuccess: " + response.get());
                            try {
                                Gson gson = new Gson();
                                ShopListBean shopListBean = gson.fromJson(response.get(), ShopListBean.class);
                                if (shopListBean.getCode() == 1 && shopListBean.getData() != null) {
                                    datas = shopListBean.getData();
                                    lvFoodTheme.setAdapter(adapter);
                                } else {
                                    ToastUtils.showToast(HomeFoodThemeActivity.this, "获取失败");
                                }
                            } catch (JsonParseException e) {
                                ToastUtils.showToast(HomeFoodThemeActivity.this, "数据解析失败");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Map createParams(int type) {
        //
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160201");
        params.put("goodsCity", "杭州市");
        params.put("goodsCategory", String.valueOf(type));
        params.put("pageNum", String.valueOf(pageNum));
        Log.d("HomeFoodThemeActivity", "createParams: " + type + ":" + pageNum);
        return params;
    }

    private void initHead() {
        switch (type) {
            case 1:
                type_pic = R.drawable.img_top_food;
                break;
            case 2:
                type_pic = R.drawable.img_top_physical;
                break;
            case 3:
                type_pic =R.drawable.img_top_it;
                break;
            case 4:
                type_pic = R.drawable.img_top_play;
            case 5:
               // type_pic = R.mipmap.ff;
                break;
        }
        rlTheme.setBackgroundResource(type_pic);
        loadData(type);
    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {

        ptrHomeFood.postDelayed(new Runnable() {

            @Override
            public void run() {
                /** 联网加载数据 */
                adapter.notifyDataSetChanged();
                ptrHomeFood.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        ptrHomeFood.postDelayed(new Runnable() {

            @Override
            public void run() {
//				listDrawable.add(R.drawable.car_gridview);
//                adapter.notifyDataSetChanged();
                ptrHomeFood.onHeaderRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        intent.setClass(HomeFoodThemeActivity.this, ShopDetailActivity.class);
        intent.putExtra("goodsId",datas.get(position).getGoodsId());
        startActivity(intent);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = HomeFoodThemeActivity.this.getLayoutInflater().inflate(R.layout.item_list_home_food, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            ShopListBean.DataBean dataBean = datas.get(position);
            holder.tvResidue.setText(String.format("剩余%s份",String.valueOf(dataBean.getStockCount())));
            int progress = (int) ((double)dataBean.getSalesCount() / (double) dataBean.getTotalCount()*100);
            holder.tvProgressText.setText(progress+"%");
            holder.progressBar.setProgress(progress);
            holder.tvThemeTitle.setText(dataBean.getGoodsName());
            holder.tvGoodsThemeDetail.setText(dataBean.getGoodsDescription());
            Glide.with(HomeFoodThemeActivity.this)
                    .load(dataBean.getPictureUrl()+"?x-oss-process=style/Android_THEME")
                    .into(holder.ivListHomePic);
            return convertView;
        }

        public  class ViewHolder {
            public View rootView;
            public ImageView ivListHomePic;
            public TextView tvProgressText;
            public TextView tvResidue;
            public ProgressBar progressBar;
            public TextView tvThemeTitle;
            public TextView tvGoodsThemeDetail;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.ivListHomePic = (ImageView) rootView.findViewById(R.id.iv_list_home_pic);
                this.tvProgressText = (TextView) rootView.findViewById(R.id.tv_progress_text);
                this.tvResidue = (TextView) rootView.findViewById(R.id.tv_residue);
                this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
                this.tvThemeTitle = (TextView) rootView.findViewById(R.id.tv_theme_title);
                this.tvGoodsThemeDetail = (TextView) rootView.findViewById(R.id.tv_goods_theme_detail);
            }

        }
    }
}
