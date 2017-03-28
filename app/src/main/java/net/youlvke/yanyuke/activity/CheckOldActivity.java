package net.youlvke.yanyuke.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.SwipeMenuAdapter;
import net.youlvke.yanyuke.bean.AwardDetailBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.SwipeMenu;
import net.youlvke.yanyuke.view.SwipeMenuCreator;
import net.youlvke.yanyuke.view.SwipeMenuItem;
import net.youlvke.yanyuke.view.SwipeMenuListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;
import static net.youlvke.yanyuke.utils.ScreenUtils.dp2px;

/**
 * 查看往期
 */
public class CheckOldActivity extends BaseActivity {

    private TextView tvTitle;
    private SwipeMenuListView lvCheckOld;
    private List<AwardDetailBean.DataBean> datas;
    private MyAdapter adapter;

    @Override
    public int getLayoutResId() {

        return R.layout.activity_check_old_bill;
    }

    @Override
    protected void initView() {
        lvCheckOld = (SwipeMenuListView) findViewById(R.id.lv_check_old);
        lvCheckOld.setMenuCreator(creator);


        tvTitle = (TextView) findViewById(R.id.tv_title);

    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(CheckOldActivity.this);
            openItem.setBackground(new ColorDrawable(Color.rgb(197, 85, 85)));
            openItem.setWidth(dp2px(80));
            openItem.setTitle("删除");
            openItem.setTitleSize(18);
            openItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(openItem);
        }
    };

    @Override
    protected void initListener() {
        tvTitle.setOnClickListener(this);
        lvCheckOld.setOnMenuItemClickListener(new MyOnMenuItemClickListener());
        lvCheckOld.setOnItemClickListener(new MyOnItemClickListener());
    }

    @Override
    protected void initData() {
        tvTitle.setText("往期账单");
        lvCheckOld.setMenuOrientation(SwipeMenuAdapter.Orientation.HORIZONTAL);
        adapter = new MyAdapter();
        loadData();
    }


    private void loadData() {

        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createParam()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("BASEURL_YYKUSER", "onSuccess: " + response.get());
                    try {
                        Gson gson = new Gson();
                        AwardDetailBean awardDetailBean = gson.fromJson(response.get(), AwardDetailBean.class);
                        if (awardDetailBean.getCode() == 1 && awardDetailBean.getData() != null) {
                            datas = awardDetailBean.getData();
                            lvCheckOld.setAdapter(adapter);
                        } else {
                            ToastUtils.showToast(CheckOldActivity.this, "没有当前的数据");
                        }

                    } catch (NullPointerException e) {
                        ToastUtils.showToast(CheckOldActivity.this, "数据解析失败");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160131");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        Log.d("BASEURL_YYKUSER", "createParam: ");
        return params;
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent();
            intent.setClass(CheckOldActivity.this,MyTabLayoutActivity.class);
            intent.putExtra("date",DateUtils.getDate(datas.get(position).getAddTime()));
            intent.putExtra("income",String.valueOf(datas.get(position).getTotal()));
            startActivity(intent);
        }
    }

    private void loadDelete(final int position) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createDeleteParam(position))
                    .loading(true)
                    .execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        if (jsonObject.getInt("code") == 1) {
                            datas.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                        ToastUtils.showToast(CheckOldActivity.this,"删除失败");
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

    private Map createDeleteParam(int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160144");//     commissionId
        params.put("commissionId", String.valueOf(datas.get(position).getCommissionId()));
        Log.d("BASEURL_YYKUSER", "createParam: "+datas.get(position).getCommissionId());
        return params;
    }

    private class MyOnMenuItemClickListener implements SwipeMenuListView.OnMenuItemClickListener {
        @Override
        public void onMenuItemClick(int position, SwipeMenu menu, int index) {
            loadDelete(index);
        }
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

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_old_bill, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AwardDetailBean.DataBean dataBean = datas.get(position);

            holder.tvAwardDate.setText(DateUtils.formatDate(dataBean.getAddTime()));
            holder.tvRecordNum.setText(String.format("+ %s", String.valueOf(new DecimalFormat("##0.00").format(dataBean.getTotal()))));

            holder.tvExpandNum.setText(String.format("- %s", String.valueOf(new DecimalFormat("##0.00").format(dataBean.getApplyAmount()))));

            if (dataBean.getTotal() - dataBean.getApplyAmount() > 0) {
                holder.tvTotal.setText("+" + new DecimalFormat("##0.00").format(dataBean.getTotal() - dataBean.getApplyAmount()));
                holder.tvTotal.setTextColor(getResources().getColor(R.color.color_value));
            } else {
                holder.tvTotal.setText("-" + new DecimalFormat("##0.00").format(dataBean.getTotal() - dataBean.getApplyAmount()));
                holder.tvTotal.setTextColor(Color.parseColor("#4FBADF"));
            }
            return convertView;
        }
    }


    public class ViewHolder {
        public View rootView;
        public TextView tvAwardDate;
        public TextView tvRecordNum;
        public TextView tvExpandNum;
        public TextView tvTotal;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvAwardDate = (TextView) rootView.findViewById(R.id.tv_award_date);
            this.tvRecordNum = (TextView) rootView.findViewById(R.id.tv_record_num);
            this.tvExpandNum = (TextView) rootView.findViewById(R.id.tv_expand_num);
            this.tvTotal = (TextView) rootView.findViewById(R.id.tv_total);
        }
    }


}

