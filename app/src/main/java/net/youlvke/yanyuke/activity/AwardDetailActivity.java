package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.AwardDetialBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.FileUtils;
import net.youlvke.yanyuke.utils.TelphoneUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CircleImageView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * 奖励详情
 */
public class AwardDetailActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvGOon;
    private Context mContext;
    private String sequenceId;
    private AwardDetialBean.DataBean datas;
    private ImageView ivShopDetail;
    private TextView tvGoodsDescription;
    private CircleImageView ivAwardDetail;
    private TextView tvAwardwer;
    private TextView tvShopNum;
    private TextView tvAwardwerNum;
    private TextView tvMysteriousCode;
    private TextView tvSequenceDetail;


    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_award_detail;
    }

    @Override
    protected void initView() {
        sequenceId = getIntent().getStringExtra("sequenceId");
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvGOon = (TextView) findViewById(R.id.tv_go_on);
        ivShopDetail = (ImageView) findViewById(R.id.iv_shop_detail);
        tvGoodsDescription = (TextView) findViewById(R.id.tv_goods_description);
        ivAwardDetail = (CircleImageView) findViewById(R.id.iv_award_detail);
        tvAwardwer = (TextView) findViewById(R.id.tv_awardwer);
        tvShopNum = (TextView) findViewById(R.id.tv_shop_num);
        tvAwardwerNum = (TextView) findViewById(R.id.tv_awardwer_num);
        tvMysteriousCode = (TextView) findViewById(R.id.tv_mysterious_code);
        tvSequenceDetail = (TextView) findViewById(R.id.tv_sequence_detail);

    }

    @Override
    protected void initListener() {
        tvGOon.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("奖励详情");
        loadData();
    }

    private void loadData() {
        try {
            IRequest.post(mContext, Constants.BASEURL_YYKGOODS, createParams())
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                Gson gson = new Gson();
                                AwardDetialBean awardDetialBean = gson.fromJson(response.get(), AwardDetialBean.class);
                                if (awardDetialBean.getCode() == 1 && awardDetialBean.getData() != null) {
                                    datas = awardDetialBean.getData();
                                    Glide.with(mContext)
                                            .load(datas.getGoodsinfo().getCoverPic().get(0).getPictureUrl() + "?x-oss-process=style/Android_DETAILS")
                                            .into(ivShopDetail);
                                    tvGoodsDescription.setText(datas.getGoodsinfo().getGoodsName() + "总价值" + datas.getTotalCount() + "元");
                                    Glide.with(mContext)
                                            .load(datas.getUserinfo().getAvatar() + "?x-oss-process=style/Android_DETAILS")
                                            .into(ivAwardDetail);
                                    String userName = null;
                                    if (TelphoneUtils.isMobileExact(datas.getUserinfo().getNickname())) {
                                        userName =TelphoneUtils.subTel( datas.getUserinfo().getNickname());
                                    }else {
                                        userName = datas.getUserinfo().getNickname();
                                    }
                                    tvAwardwer.setText(String.format("获奖者：%s", userName));
                                    String modifyclauses = FileUtils.modifyclauses(datas.getSequenceId());
                                    tvShopNum.setText(String.format("商品期数：%s",modifyclauses));
                                    tvAwardwerNum.setText(String.format("本次参与：%s人次",String.valueOf(datas.getSalesCount())));
                                    tvMysteriousCode.setText(String.format("神秘代码：%s",datas.getWinningNumber()));
                                    tvSequenceDetail.setText("第"+modifyclauses+"期正在火热进行中……");
                                } else {
                                    ToastUtils.showToast(mContext, "没有数据！");
                                }

                            } catch (JsonParseException e) {
                                ToastUtils.showToast(mContext, "数据解析失败！");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    *
    *
    *
    pageNum
    userId
    sessionToken
    */
    private Map createParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160205");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        params.put("sequenceId", sequenceId);
        Log.d("AwardDetailActivity", "createParams: " + session + "123" + String.valueOf(userId) + sequenceId);
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.tv_go_on:
                Intent intent = new Intent();
                intent.setClass(mContext,ShopDetailActivity.class);
                intent.putExtra("goodsId",datas.getGoodsId());
                startActivity(intent);
                break;
        }
    }
}
