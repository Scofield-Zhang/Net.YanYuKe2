package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.NotEvaluateBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CustomImageView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * 未评价
 */
public class NotEvaluateActivity extends BaseActivity {

    private TextView tvTitle;
    private ListView lvNotEvaluate;
    private int pageNum = 1;
    private List<NotEvaluateBean.DataBean> datas;
    private MyAdapter adapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_not_evaluate;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvNotEvaluate = (ListView) findViewById(R.id.lv_not_evaluate);
    }

    @Override
    protected void initListener() {
        lvNotEvaluate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("evaluationId",String.valueOf(datas.get(i).getEvaluationId()));
                intent.setClass(NotEvaluateActivity.this, UserEvaluateActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText("待评价商品");
        adapter = new MyAdapter();
        loadData();


    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createParams())
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("EvaluateInfoBean", "onSuccess: " + response.get());
                            try {
                                Gson gson = new Gson();
                                NotEvaluateBean  notEvaluateBean = gson.fromJson(response.get(), NotEvaluateBean.class);
                                if (notEvaluateBean.getCode() == 1 && notEvaluateBean.getData() != null) {
                                    datas = notEvaluateBean.getData();
                                    lvNotEvaluate.setAdapter(adapter);
                                } else {
                                    ToastUtils.showToast(NotEvaluateActivity.this, "没有需要评价的数据");
                                }

                            } catch (JsonParseException e) {
                                ToastUtils.showToast(NotEvaluateActivity.this, "数据解析失败");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160142");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        params.put("pageNum", String.valueOf(pageNum));
        return params;
    }
    class MyAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_evaluate, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NotEvaluateBean.DataBean dataBean = datas.get(position);
            Glide.with(NotEvaluateActivity.this)
                    .load(dataBean.getPictureUrl()+"?x-oss-process=style/Android_EVALUATED")
                    .into(holder.civShopImg);
            holder.tvShopTitle.setText(dataBean.getGoodsName());
            String totalValuse = String.format("总\b价\b值：￥ %s",String.valueOf(dataBean.getGoodsPrice()));
            String useTime = String.format("使用时间：%s", DateUtils.formatDateAndHour(dataBean.getUserTime()));
            holder.tvShopEvaluteDetail.setText(totalValuse+"\n"+useTime);
            return convertView;
        }
    }


    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.tv_not_eva:
                break;
        }

    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();

    }

    public class ViewHolder {
        public View rootView;
        public TextView tvShopEvaluteDetail;
        public TextView tvNotEva;
        public TextView tvShopTitle;
        public CustomImageView civShopImg;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvShopEvaluteDetail = (TextView) rootView.findViewById(R.id.tv_shop_evalute_detail);
            this.tvNotEva = (TextView) rootView.findViewById(R.id.tv_not_eva);
            this.civShopImg = (CustomImageView) rootView.findViewById(R.id.civ_shop_img);
            this.tvShopTitle = (TextView) rootView.findViewById(R.id.tv_shop_title);
        }
    }
}
