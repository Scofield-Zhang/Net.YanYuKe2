package net.youlvke.yanyuke.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.ParticipationBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CircleImageView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;

/**
 * 参与详情
 */
public class ParticipationActivity extends BaseActivity {


    private ListView lvParticipation;
    private TextView tvTitle;
    private String sequenceId;
    private List<ParticipationBean.DataBean> datas;
    private TextView tvIndcatorText;
    private int pageNum;
    private MyAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_participation;
    }

    @Override
    protected void initView() {
        sequenceId = getIntent().getStringExtra("participation");
        lvParticipation = (ListView) findViewById(R.id.lv_participation);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvIndcatorText = (TextView) findViewById(R.id.tv_indcator_text);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initListener() {//TODO
       /* lvParticipation.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisiblePosition = lvParticipation.getLastVisiblePosition();
                if (adapter.getCount() == lastVisiblePosition) {
                    if (pageNum <= 2)
                        pageNum++;
                    loadData(pageNum++);
                }
                adapter.notifyDataSetChanged();
            }
        });*/
    }

    @Override
    protected void initData() {
        tvTitle.setText("我参与的");
        loadData(pageNum);
    }


    private void loadData(int pageNum) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createParticipate(pageNum))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("loadData", "onSuccess: " + response.get());
                            Gson gson = new Gson();
                            try {//participate
                                ParticipationBean participationBean = gson.fromJson(response.get(), ParticipationBean.class);
                                if (participationBean.getCode() == 1) {
                                    datas = participationBean.getData();
                                    if (datas != null) {
                                        adapter = new MyAdapter();
                                        lvParticipation.setAdapter(adapter);
                                        tvIndcatorText.setVisibility(View.GONE);
                                    }
                                } else {
                                    tvIndcatorText.setVisibility(View.VISIBLE);
                                    tvIndcatorText.setText("您还没有收藏商品");
                                    ToastUtils.showToast(ParticipationActivity.this, "您还没有收藏商品");
                                }
                            } catch (JsonSyntaxException e) {
                                ToastUtils.showToast(ParticipationActivity.this, "数据解析错误");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //


    private Map createParticipate(int pageNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160211");
        params.put("userId", String.valueOf(userId));
        params.put("sequenceId", sequenceId);
        params.put("pageNum", String.valueOf(pageNum));
        params.put("sessionToken", getSharedPreferences("loginInfo", MODE_PRIVATE).getString("session", ""));
        return params;
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_participate, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ParticipationBean.DataBean dataBean = datas.get(position);


            holder.tvParticipationIp.setText(String.format("(%s)", dataBean.getUserIp()));
            holder.tvParticipationName.setText(dataBean.getNickname());
            DateUtils dateUtils = new DateUtils();
            holder.tvParticipationTime.setText(dateUtils.formatDatePoint(dataBean.getPurchaseDate()));
            holder.tvParticipationNumber.setText(String.format("参与了%s次", String.valueOf(dataBean.getPurchaseCount())));

            Glide.with(ParticipationActivity.this)
                    .load(datas.get(position).getAvatar() + "")
                    .into(holder.civParticipationPic);

            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public CircleImageView civParticipationPic;
            public TextView tvParticipationName;
            public TextView tvParticipationTime;
            public TextView tvParticipationNumber;
            public TextView tvParticipationIp;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.civParticipationPic = (CircleImageView) rootView.findViewById(R.id.civ_participation_pic);
                this.tvParticipationName = (TextView) rootView.findViewById(R.id.tv_participation_name);
                this.tvParticipationTime = (TextView) rootView.findViewById(R.id.tv_participation_time);
                this.tvParticipationNumber = (TextView) rootView.findViewById(R.id.tv_participation_number);
                this.tvParticipationIp = (TextView) rootView.findViewById(R.id.tv_participation_ip);
            }

        }
    }
}
