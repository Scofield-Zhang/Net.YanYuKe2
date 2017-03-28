package net.youlvke.yanyuke.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.SwipeMenuAdapter;
import net.youlvke.yanyuke.bean.Income;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

import static net.youlvke.yanyuke.utils.ScreenUtils.dp2px;

/**
 * 充值记录
 */
public class RechargeRecodeActivity extends BaseActivity {

    private TextView tvTitle;

    private SwipeMenuListView lvCheckOld;
    private List<Income.DataBean> datas = new ArrayList<>();
    ;
    private MyAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_recharge_record;
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvCheckOld = (SwipeMenuListView) findViewById(R.id.lv_recharge_record);
        lvCheckOld.setMenuCreator(creator);
        lvCheckOld.setOnMenuItemClickListener(new MyOnMenuItemClickListener());
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(RechargeRecodeActivity.this);
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

    }

    /**
     * 初始化操作
     */
    @Override
    protected void initData() {
        tvTitle.setText("充值记录");
        loadData();
        lvCheckOld.setMenuOrientation(SwipeMenuAdapter.Orientation.HORIZONTAL);
        adapter = new MyAdapter();
    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createRechargeParams())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson = new Gson();
                            Income income = gson.fromJson(response.get(), Income.class);
                            if (income.getCode() == 1) {
                                datas = income.getData();
                                lvCheckOld.setAdapter(adapter);
                            } else {
                                ToastUtils.showToast(RechargeRecodeActivity.this, "服务器出错了");
                            }
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Map createRechargeParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160117");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
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
                convertView = getLayoutInflater().inflate(R.layout.item_recharge_record, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Income.DataBean dataBean = datas.get(position);
            DateUtils dateUtils = new DateUtils();
            String[] strings = dateUtils.formatSliteDate(dataBean.getRechargeDate(), " ");

            holder.tvDate.setText(strings[0]);

            holder.tvDetailTime.setText(strings[1].substring(0, 5));
            if (dataBean.getRechargeWay() == 0) {
                holder.tvRechargeType.setText("支付宝充值");
            } else if (dataBean.getRechargeWay() == 1) {
                holder.tvRechargeType.setText("微信充值");
            } else if (dataBean.getRechargeWay() == 2) {
                holder.tvRechargeType.setText("银联支付");
            }
            holder.tvRechargeNum.setText(String.valueOf(dataBean.getRechargeAmount()));
            return convertView;
        }
    }

    private class MyOnMenuItemClickListener implements SwipeMenuListView.OnMenuItemClickListener {
        @Override
        public void onMenuItemClick(int position, SwipeMenu menu, int index) {
            //Income item = datas.get(position);
            datas.remove(position);
            adapter.notifyDataSetChanged();
        }
    }
}

class ViewHolder {

    public View rootView;
    public TextView tvDate;
    public TextView tvRechargeType;
    public TextView tvRechargeNum;
    public TextView tvDetailTime;

    public ViewHolder(View rootView) {
        this.rootView = rootView;
        this.tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        this.tvRechargeType = (TextView) rootView.findViewById(R.id.tv_recharge_type);
        this.tvRechargeNum = (TextView) rootView.findViewById(R.id.tv_recharge_num);
        this.tvDetailTime = (TextView) rootView.findViewById(R.id.tv_detail_time);
    }

}

