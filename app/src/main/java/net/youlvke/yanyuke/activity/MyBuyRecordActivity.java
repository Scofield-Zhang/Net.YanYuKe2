package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.SwipeMenuAdapter;
import net.youlvke.yanyuke.bean.BuyRecordBean;
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
 * 购买记录
 */
public class MyBuyRecordActivity extends BaseActivity {


    private SwipeMenuListView smlvBuyRecord;
    private TextView tvTitle;
    private List<BuyRecordBean.DataBean> datas = new ArrayList<>();
    ;
    private MyAdapter adapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_buy_record;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        smlvBuyRecord = (SwipeMenuListView) findViewById(R.id.smlv_buy_record);
    }

    @Override
    protected void initListener() {
        smlvBuyRecord.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        datas.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        startActivity(new Intent(MyBuyRecordActivity.this, ShopDetailActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText("购买记录");
        loadData();
        smlvBuyRecord.setMenuOrientation(SwipeMenuAdapter.Orientation.VERTICAL);

        smlvBuyRecord.setMenuCreator(creator);
        adapter = new MyAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createBuyRecordParams()).execute(new RequestListener() {

                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("MyBuyRecordActivity", "onSuccess: " + response.get());
                    Gson gson = new Gson();
                    BuyRecordBean buyRecordBean = gson.fromJson(response.get(), BuyRecordBean.class);
                    int code = buyRecordBean.getCode();
                    if (code == 1) {
                        datas = buyRecordBean.getData();
                        smlvBuyRecord.setAdapter(adapter);
                    } else {
                        ToastUtils.showToast(MyBuyRecordActivity.this, "服务器出现异常");
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Map createBuyRecordParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160116");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(MyBuyRecordActivity.this);
            openItem.setBackground(new ColorDrawable(Color.rgb(197,85,85)));
            openItem.setWidth(dp2px(95));
            openItem.setTitle("移除");
            openItem.setTitleSize(16);
            openItem.setTitleColor(getResources().getColor(R.color.text_color_gray));
            menu.addMenuItem(openItem);

            SwipeMenuItem againItem = new SwipeMenuItem(MyBuyRecordActivity.this);
            againItem.setBackground(new ColorDrawable(Color.parseColor("#BFC8B9")));
            againItem.setWidth(dp2px(95));
            againItem.setTitle("再来一单");
            againItem.setTitleSize(16);
            againItem.setTitleColor(getResources().getColor(R.color.text_color_gray));
            menu.addMenuItem(againItem);
        }
    };

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
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
                convertView = getLayoutInflater().inflate(R.layout.item_buy_record, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            BuyRecordBean.DataBean dataBean = datas.get(position);
            holder.tvBuyTitle.setText(dataBean.getGoodsInfo().getGoodsName());
            holder.tvIssue.setText(dataBean.getNewsequenceId());
            String str = String.format("本期参与 %d人次", dataBean.getCount());
            DateUtils dateUtils = new DateUtils();
            String buyTime = dateUtils.formatDatePoint(dataBean.getPurchaseDate());

            holder.tvJoinDetail.setText(str+"   "+buyTime);

            return convertView;
        }
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tvBuyTitle;
        public TextView tvIssue;
        public TextView tvJoinDetail;


        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvBuyTitle = (TextView) rootView.findViewById(R.id.tv_buy_title);
            this.tvIssue = (TextView) rootView.findViewById(R.id.tv_issue);
            this.tvJoinDetail = (TextView) rootView.findViewById(R.id.tv_join_detail);

        }

    }
}
