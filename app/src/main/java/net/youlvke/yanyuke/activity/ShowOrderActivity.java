package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.ShowOrderBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.GlideRoundTransform;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CircleImageView;
import net.youlvke.yanyuke.view.RatingBar;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowOrderActivity extends BaseActivity {

    private TextView tvTitle;
    private ListView lvShowOrder;
    private String goodsId;
    private List<ShowOrderBean.DataBean> datas;
    private float sDensity;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_show_order;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra("goodsId");
        lvShowOrder = (ListView) findViewById(R.id.lv_show_order);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("晒单分享");
        loadData();


    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createParams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("onSuccess", "onSuccess: " + response.get());
                    try {
                        Gson gson = new Gson();
                        ShowOrderBean showOrderBean = gson.fromJson(response.get(), ShowOrderBean.class);
                        if (showOrderBean.getCode() == 1 && showOrderBean.getData() != null) {
                            datas = showOrderBean.getData();
                            MyAdapter adapter = new MyAdapter();
                            lvShowOrder.setAdapter(adapter);
                        }

                    } catch (JsonParseException e) {
                        Log.d("JsonParseException", "onSuccess: " + e.toString());
                        ToastUtils.showToast(ShowOrderActivity.this, "数据解析异常");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160207");
        params.put("goodsId", goodsId);
        params.put("pageNum", "1");
        return params;
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
                convertView = getLayoutInflater().inflate(R.layout.item_show_detail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ShowOrderBean.DataBean dataBean = datas.get(position);
            //用户昵称
            holder.tvUserName.setText(dataBean.getNickname());
            //xi星星数
            holder.rbStar.setStar((float) dataBean.getScore());
            //期号
            holder.tvEvaluateIssue.setText(String.format("期号  %s", String.valueOf(dataBean.getSequenceId())));
            holder.tvGradeDetail.setText(dataBean.getContent());
            //头像
            Glide.with(ShowOrderActivity.this)
                    .load(dataBean.getAvatar())
                    .into(holder.civHeadImg);

            //Log.d("GlideRoundTransform", "getView: "+dataBean.getEvaPic().size()+dataBean.getEvaPic().get(0).getPictureUrl());
            if (dataBean.getEvaPic() != null) {
                for (int i = 0; i < dataBean.getEvaPic().size(); i++) {
                    ImageView civ = new ImageView(ShowOrderActivity.this);
                    civ.setScaleType(ImageView.ScaleType.FIT_XY);
                    civ.setLayoutParams(new ViewGroup.LayoutParams(dipToPixel(ShowOrderActivity.this,60),
                            dipToPixel(ShowOrderActivity.this,60)));
                    holder.llShareDetail.addView(civ);
                    Glide.with(ShowOrderActivity.this)
                            .load(dataBean.getEvaPic().get(i).getPictureUrl() + "?x-oss-process=style/Android_DRYING_SINGLE")
                            .transform(new GlideRoundTransform(ShowOrderActivity.this, 2))
                            .into(civ);
                    Log.d("GlideRoundTransform", "getView: "+dataBean.getEvaPic().get(i).getPictureUrl() + "?x-oss-process=style/Android_DRYING_SINGLE");
                }
            }
            return convertView;
        }
    }

    public class ViewHolder {
        public View rootView;
        public CircleImageView civHeadImg;
        public TextView tvUserName;
        public TextView tvGrade;
        public RatingBar rbStar;
        public TextView tvGradeDetail;
        public TextView tvEvaluateIssue;
        public LinearLayout llShareDetail;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.civHeadImg = (CircleImageView) rootView.findViewById(R.id.civ_head_img);
            this.tvUserName = (TextView) rootView.findViewById(R.id.tv_user_name);
            this.tvGrade = (TextView) rootView.findViewById(R.id.tv_grade);
            this.rbStar = (RatingBar) rootView.findViewById(R.id.rb_star);
            this.tvGradeDetail = (TextView) rootView.findViewById(R.id.tv_grade_detail);
            this.llShareDetail = (LinearLayout) rootView.findViewById(R.id.ll_share_detail);
            this.tvEvaluateIssue = (TextView) rootView.findViewById(R.id.tv_evaluate_issue);
        }

    }


    public int dipToPixel(Context context, int nDip) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }
}
