package net.youlvke.yanyuke.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.VouchersBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 抵用券
 */
public class MyVouchersActivity extends BaseActivity {
    private Button btnBack;
    private TextView tvTitle;
    private ListView lvVouchers;
    private List<VouchersBean.DataBean> data = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_vouchers;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvVouchers = (ListView) findViewById(R.id.lv_vouchers);


        MyAdapter adapter = new MyAdapter();
        lvVouchers.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("我的抵用券");
        loadData();
        adapter = new MyAdapter();
    }

    private void loadData() {

        try {
            IRequest.post(MyVouchersActivity.this, Constants.BASEURL_YYKUSER, createVoucher()).execute(new RequestListener() {


                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("MyVouchersActivity", "onSuccess: " + response.get());
                    Gson gson = new Gson();
                    VouchersBean vouchersBean = gson.fromJson(response.get(), VouchersBean.class);
                    int code = vouchersBean.getCode();
                    if (code == 1) {
                        data = vouchersBean.getData();
                        lvVouchers.setAdapter(adapter);
                    } else if (code == 2) {
                        ToastUtils.showToast(MyVouchersActivity.this, "无可用抵用券");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Map createVoucher() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160113");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = MyVouchersActivity.this.getLayoutInflater().inflate(R.layout.item_vouchers, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            VouchersBean.DataBean dataBean = data.get(i);
            holder.tvNum.setText(String.valueOf(dataBean.getMoney()));

            switch (dataBean.getVoucherstype()) {
                case 0:
                    holder.text1.setText("通用抵用卷");
                    break;
                case 1:
                    holder.text1.setText("美食券");
                    break;
                case 2:
                    holder.text1.setText("实用券");
                    break;
                case 3:
                    holder.text1.setText("数码券");
                    break;
                case 4:
                    holder.text1.setText("娱乐券");
                    break;
                case 5:
                    holder.text1.setText("全国购");
                    break;
            }
            if (dataBean.getValidity() == 0) {
                holder.isUsed.setText("可以使用");
            } else if (dataBean.getValidity() == 1) {
                holder.text1.setText("不能使用");
            }
            DateUtils dateUtils = new DateUtils();
            holder.tvValid.setText(String.format("有效期限%s", dateUtils.formatDate(dataBean.getExpiryDate())));
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tvNum;
            public TextView tvVouchers;
            public TextView tvValid;
            public TextView text1;
            public TextView text2;
            public TextView isUsed;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvNum = (TextView) rootView.findViewById(R.id.tv_num);
                this.tvVouchers = (TextView) rootView.findViewById(R.id.tv_vouchers);
                this.tvValid = (TextView) rootView.findViewById(R.id.tv_valid);
                this.text1 = (TextView) rootView.findViewById(R.id.text1);
                this.text2 = (TextView) rootView.findViewById(R.id.text2);
                this.isUsed = (TextView) rootView.findViewById(R.id.is_used);
            }

        }
    }

}
