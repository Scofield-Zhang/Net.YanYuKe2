package net.youlvke.yanyuke.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.activity.MyTabLayoutActivity;
import net.youlvke.yanyuke.bean.RewardBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;
import static net.youlvke.yanyuke.utils.DateUtils.HourAndMinute;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PagerFragmentSpend extends BaseFragment {

    private ListView lvPager;
    private List<RewardBean.DataBean> datas;
    private MyAdapter adapter;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View initView() {
        setStatusColor(getResources().getColor(R.color.dialog_bg));
        View view = View.inflate(getActivity(), R.layout.pager_list, null);
        lvPager = (ListView) view.findViewById(R.id.lv_pager);

        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initData() {


    }


    @Override
    public void onClick(View view) {

    }


    @Override
    public void onResume() {
        super.onResume();
        loadData(date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        date = ((MyTabLayoutActivity) context).getDate();
        ToastUtils.showToast(mContext, date);
        adapter = new MyAdapter();
        loadData(date);

    }


    private void loadData(String date) {

        try {
            IRequest.post(mContext, Constants.BASEURL_YYKUSER, createParam(date)).execute(new RequestListener() {


                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("BASEURL_YYKUSER", "onSuccess: " + response.get());
                    try {
                        Gson gson = new Gson();
                        RewardBean rewardBean = gson.fromJson(response.get(), RewardBean.class);
                        if (rewardBean.getCode() == 1 && rewardBean.getData() != null) {
                            datas = rewardBean.getData();
                            lvPager.setAdapter(adapter);
                        }

                    } catch (NullPointerException e) {
                        ToastUtils.showToast(mContext, "没有当前的数据");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createParam(String date) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160141");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        params.put("date", date);
        params.put("type", "3");
        Log.d("BASEURL_YYKUSER", "createParam: " + date);
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
                // view = View.inflate();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tab_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            RewardBean.DataBean dataBean = datas.get(position);

            if (dataBean.getCommissiontype() == 3) {
                holder.tvTime.setText(HourAndMinute(dataBean.getAddtime()));
                holder.tvAssociatedName.setText("提现支出");
                holder.tvIncome.setText(String.format("-%s", dataBean.getMoney()));
                holder.tvIncome.setTextColor(getResources().getColor(R.color.bill_blue));
            } else {
                switch (dataBean.getCommissiontype()) {
                    case 1:
                        holder.tvRewardType.setText("消费奖励");
                        break;
                    case 2:
                        holder.tvRewardType.setText("红钻奖励");
                        break;
                }
                holder.tvTime.setText(HourAndMinute(dataBean.getAddtime()));
                holder.tvAssociatedName.setText(dataBean.getNickname());
                holder.tvIncome.setText(String.format("+%s", dataBean.getMoney()));
            }

            return convertView;
        }


        public class ViewHolder {
            public View rootView;
            public TextView tvTime;
            public TextView tvAssociatedName;
            public TextView tvRewardType;
            public TextView tvIncome;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvTime = (TextView) rootView.findViewById(R.id.tv_time);
                this.tvAssociatedName = (TextView) rootView.findViewById(R.id.tv_associated_name);
                this.tvRewardType = (TextView) rootView.findViewById(R.id.tv_reward_type);
                this.tvIncome = (TextView) rootView.findViewById(R.id.tv_income);
            }

        }
    }
}
