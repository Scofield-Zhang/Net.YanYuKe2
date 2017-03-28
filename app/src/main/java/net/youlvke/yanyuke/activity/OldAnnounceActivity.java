package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.OldAnnounceBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.TelphoneUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CircleImageView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 往期揭晓
 */
public class OldAnnounceActivity extends BaseActivity {

    private TextView tvTitle;
    private ListView lvOldAnnounce;
    private String goodsId;
    private List<OldAnnounceBean.DataBean> datas;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_old_annouce;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra("goodsId");
        tvTitle = (TextView) findViewById(R.id.tv_title);

        lvOldAnnounce = (ListView) findViewById(R.id.lv_old_annouce);
        lvOldAnnounce.setOnItemClickListener(new OnOldItemClickListener());

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("往期揭晓");

        loadData();

    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createOldAnnounceParams())
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("BASEURL_YYKGOODS", "onSuccess: " + response.get());
                            try {
                                Gson gson = new Gson();
                                OldAnnounceBean oldAnnounceBean = gson.fromJson(response.get(), OldAnnounceBean.class);
                                if (oldAnnounceBean.getCode() == 1 && oldAnnounceBean.getData() != null) {
                                    datas = oldAnnounceBean.getData();

                                    MyAdapter adapter = new MyAdapter();
                                    lvOldAnnounce.setAdapter(adapter);
                                } else {
                                    ToastUtils.showToast(OldAnnounceActivity.this, "获取失败");
                                }

                            } catch (JsonSyntaxException e) {
                                ToastUtils.showToast(OldAnnounceActivity.this, "数据解析错误！");
                            }


                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createOldAnnounceParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160204");
        params.put("goodsId", goodsId);
        params.put("pageNum", "1");
        Log.d("BASEURL_YYKGOODS", "createOldAnnounceParams: " + goodsId);
        return params;
    }


    /**
     * 往期揭晓条目点击
     */
    class OnOldItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(OldAnnounceActivity.this, AwardDetailActivity.class);
            intent.putExtra("sequenceId", String.valueOf(datas.get(position).getSequenceId()));
            startActivity(intent);
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_old_annouce, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            OldAnnounceBean.DataBean dataBean = datas.get(position);
            String s = DateUtils.formatDateAndHour(dataBean.getAnnouncedDate());
            long sequenceId = dataBean.getSequenceId();
            holder.tvAnnounceDetail.setText(String.format("第%s期 揭晓时间：%s", String.valueOf(sequenceId), s));
            Glide.with(OldAnnounceActivity.this)
                    .load(dataBean.getUserinfo().getAvatar())
                    .into(holder.civAnnouce);
            String s3 = TelphoneUtils.subTel(dataBean.getUserinfo().getNickname());
            String s1 = initColor("获奖者：", s3);
            holder.evWinner.setText(Html.fromHtml(s1));
            holder.tvSequence.setText(String.format("商品期数：%s", String.valueOf(dataBean.getSequenceId())));
            holder.tvParticpateCount.setText(String.format("本次参与：%d人次", dataBean.getCount()));
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tvAnnounceDetail;
            public CircleImageView civAnnouce;
            public TextView evWinner;
            public TextView tvSequence;
            public TextView tvParticpateCount;
            public ImageView ivArrows;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvAnnounceDetail = (TextView) rootView.findViewById(R.id.tv_announce_detail);
                this.civAnnouce = (CircleImageView) rootView.findViewById(R.id.civ_annouce);
                this.evWinner = (TextView) rootView.findViewById(R.id.tv_winner);
                this.tvSequence = (TextView) rootView.findViewById(R.id.tv_sequence);
                this.tvParticpateCount = (TextView) rootView.findViewById(R.id.tv_particpate_count);
                this.ivArrows = (ImageView) rootView.findViewById(R.id.iv_arrows);
            }

        }
    }

    private String initColor(String text1, String text2) {
        String str = "<font color='#333333'>" + text1 + "</font>"
                + "<font color= '#4770E7'>" + text2 + "</font>";
        return str;
    }
}
