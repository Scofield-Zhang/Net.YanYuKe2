package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yolanda.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.ShopDetailBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.ObservableScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;

/**
 * 赏评详情界面
 */
public class ShopDetailActivity extends BaseActivity {


    private Button btnImmediatelyBuy;
    private ImageView cancel;
    private TextView tvReduce;
    private TextView tvADD;
    private EditText etInputNum;
    private RadioGroup rgGroup;
    private Button btnDib;
    private AlertDialog dialog;
    private int num;//选择的数量
    private int choiceType;//选择的数量
    private TextView tvOldAnnounce;
    private TextView tvShopWin;
    private TextView tvShowOrder;
    private ImageView ivShare;
    private UMImage imageurl;
    private AlertDialog alertDialog;
    private TextView tvBrightSpot;
    private ObservableScrollView oScrollview;
    private RelativeLayout rlMeasure;
    private int imageHeight;
    private TextView tvTitle;
    private RelativeLayout rlBgColor;
    private CheckBox ivCollect;
    private Banner ivGoodsPic;
    private ShopDetailBean.DataBean datas;
    private TextView tvProgressText;
    private TextView tvSurplus;
    private TextView tvToaltValues;
    private TextView tvGoodsName;
    private TextView tvGoodsDetail;
    private TextView tvSellerName;
    private TextView tvSellerAddress;
    private TextView tvSellerTel;
    private boolean isCollect;
    private ProgressBar progressBar6;
    private TextView isParticipation;
    private Intent intent;
    private List<String> imageViews;
    private LinearLayout llShopDetail;
    private long goodsId;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initView() {
        intent = new Intent();
        goodsId = getIntent().getLongExtra("goodsId", 0);
        btnImmediatelyBuy = (Button) findViewById(R.id.btn_immediately_buy);
        tvOldAnnounce = (TextView) findViewById(R.id.tv_old_announce);
        tvShopWin = (TextView) findViewById(R.id.tv_shop_win);
        tvShowOrder = (TextView) findViewById(R.id.tv_show_order);
        tvBrightSpot = (TextView) findViewById(R.id.tv_bright_spot);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        oScrollview = (ObservableScrollView) findViewById(R.id.oscrollview);
        rlMeasure = (RelativeLayout) findViewById(R.id.rl_measure);
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivCollect = (CheckBox) findViewById(R.id.iv_collect);
        ivGoodsPic = (Banner) findViewById(R.id.iv_goods_pic);
        tvProgressText = (TextView) findViewById(R.id.tv_progress_text);
        tvSurplus = (TextView) findViewById(R.id.tv_surplus);
        tvToaltValues = (TextView) findViewById(R.id.tv_toalt_values);
        tvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        tvGoodsDetail = (TextView) findViewById(R.id.tv_goods_detail);
        tvSellerName = (TextView) findViewById(R.id.tv_seller_name);
        tvSellerAddress = (TextView) findViewById(R.id.tv_seller_address);
        tvSellerTel = (TextView) findViewById(R.id.tv_seller_tel);
        progressBar6 = (ProgressBar) findViewById(R.id.progress6);
        isParticipation = (TextView) findViewById(R.id.is_participation);
        llShopDetail = (LinearLayout) findViewById(R.id.ll_shop_detail);
    }

    @Override
    protected void initListener() {
        ViewTreeObserver viewTreeObserver = rlMeasure.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        tvOldAnnounce.setOnClickListener(this);
        btnImmediatelyBuy.setOnClickListener(this);
        tvShopWin.setOnClickListener(this);
        tvShowOrder.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
        isParticipation.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("商品详情");
        tvTitle.setTextColor(Color.TRANSPARENT);
        tvBrightSpot.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        SpannableString brightSpot = new SpannableString("亮点");
        brightSpot.setSpan(new UnderlineSpan(), 0, brightSpot.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBrightSpot.setText(brightSpot);
        rlBgColor.setBackgroundColor(Color.argb(0, 93, 104, 130));
        //loadData(String.valueOf(getIntent().getExtras("goodsId", 0)));
        loadData(String.valueOf(getIntent().getLongExtra("goodsId", 0)));
    }

    private void loadData(String goodId) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createParams(goodId, "20160202"))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("createShopDetailParams", "onSuccess: " + response.get());

                            Gson gson = new Gson();
                            try {
                                ShopDetailBean shopDetailBean = gson.fromJson(response.get(), ShopDetailBean.class);
                                if (shopDetailBean.getCode() == 1 && shopDetailBean != null) {
                                    datas = shopDetailBean.getData();

                                    loadBanner();
                                    int surPlus = datas.getTotalCount() - datas.getSalesCount();
                                    double participateProgress = (double) datas.getSalesCount() / (double) datas.getTotalCount() * 100;
                                    tvProgressText.setText(String.format("参与度%s%%", String.valueOf((int) participateProgress)));
                                    tvSurplus.setText(String.format("剩余%s份数", String.valueOf(surPlus)));
                                    progressBar6.setProgress((int) participateProgress);
                                    if (datas.getCol() == 1) {//收藏
                                        ivCollect.setChecked(true);
                                        isCollect = true;
                                    } else if (datas.getCol() == 0) {//未收藏
                                        ivCollect.setChecked(false);
                                        isCollect = false;
                                    }
                                    tvToaltValues.setText(String.valueOf(datas.getTotalCount()));
                                    tvGoodsName.setText(String.valueOf(datas.getGoodsinfo().getGoodsName()));
                                    tvGoodsDetail.setText(datas.getGoodsinfo().getGoodsDescription());
                                    tvSellerName.setText(datas.getMerchant().getMerchantname());
                                    tvSellerAddress.setText(datas.getMerchant().getAddressDetail());
                                    tvSellerTel.setText(datas.getMerchant().getPhone());
                                }
                            } catch (JsonSyntaxException e) {
                                ToastUtils.showToast(ShopDetailActivity.this, "数据解析错误");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadBanner() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < datas.getMerchantsPicture().size(); i++) {
            imageViews.add(datas.getMerchantsPicture().get(i).getPictureUrl() + "?x-oss-process=style/Android_DETAILS");
        }
        ivGoodsPic.setImageLoader(new GlideMyImageLoader());
        ivGoodsPic.setBannerStyle(BannerConfig.NOT_INDICATOR);
        ivGoodsPic.isAutoPlay(false);
        ivGoodsPic.setImages(imageViews);
        ivGoodsPic.start();
    }


    /**
     * 图片加载器
     */
    private class GlideMyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.btn_immediately_buy:
                ShowBuyDialog();
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.btn_dib:
                if (num <= 0 || choiceType == 0) {
                    ToastUtils.showToast(this, "请输入购买数量,或者选择数量");
                    break;
                }
                if (datas == null) {
                    return;
                }
                intent.setClass(this, PayActivity.class);
                intent.putExtra("PAY_NUM", String.valueOf(num));
                intent.putExtra("goodsCategory", datas.getGoodsinfo().getGoodsCategory());
                intent.putExtra("SequenceId", datas.getSequenceId());
                //intent.putExtra("PAY_NUM", String.valueOf(num));
                startActivity(intent);
                dialog.dismiss();
                break;
            case R.id.tv_old_announce:
                intent.setClass(this, OldAnnounceActivity.class);
                intent.putExtra("goodsId", String.valueOf(goodsId));
                startActivity(intent);
                break;
            case R.id.tv_shop_win:
                startActivity(new Intent(this, WinRuleActivity.class));
                break;
            case R.id.tv_show_order:
                intent.setClass(this, ShowOrderActivity.class);
                intent.putExtra("goodsId", String.valueOf(goodsId));
                startActivity(intent);
                break;
            case R.id.iv_share://分享界面
                showSharedialog();
                break;
            case R.id.iv_wechat_share://    微信分享界面（朋友圈）
                UmengShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.iv_weibo_share://     微博分享界面（链接+图片+文字）
                UmengShare(SHARE_MEDIA.SINA);
                break;
            case R.id.iv_qq_share://        QQ联系人分享界面（链接+图片+文字）
                UmengShare(SHARE_MEDIA.QQ);

                break;
            case R.id.iv_qq_zone_share://   分享界面   QQ(有标题有内容)
                UmengShare(SHARE_MEDIA.QZONE);
                break;
            case R.id.iv_collect://   分享界面   QQ(有标题有内容)
                if (isCollect) { //  收藏商品    商品模块
                    collectAndCancelGoods(Constants.BASEURL_YYKUSER, "20160123", String.valueOf(getIntent().getLongExtra("goodsId", 0)), "取消收藏");
                } else { //  取消收藏    用户模块
                    collectAndCancelGoods(Constants.BASEURL_YYKGOODS, "20160208", String.valueOf(getIntent().getLongExtra("goodsId", 0)), "收藏");
                }
                isCollect = !isCollect;
                break;
            case R.id.is_participation:
                if (datas == null) {
                    return;
                }
                intent.setClass(this, ParticipationActivity.class);
                intent.putExtra("participation", String.valueOf(datas.getSequenceId()));
                startActivity(intent);
                break;
        }
    }

    private void UmengShare(SHARE_MEDIA shareMedia) {

        new ShareAction(ShopDetailActivity.this)
                .withTitle(Constants.title)
                .withText(Constants.text)
                .withTargetUrl(Constants.url)
                .setPlatform(shareMedia)
                .withMedia(imageurl)
                .setCallback(shareListener).share();

    }

    /**
     * 分享监听
     */
    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShopDetailActivity.this, "成功了", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShopDetailActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShopDetailActivity.this, "取消了", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        }
    };

    /**
     * 显示购买
     */
    private void ShowBuyDialog() {

        dialog = new AlertDialog.Builder(this, R.style.theme_dialog)
                .create();
        // 调用系统的输入法
        dialog.setView(new EditText(this));

        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_immediately_buy);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        cancel = (ImageView) window.findViewById(R.id.cancel);

        tvReduce = (TextView) window.findViewById(R.id.tv_reduce);
        tvADD = (TextView) window.findViewById(R.id.tv_add);
        etInputNum = (EditText) window.findViewById(R.id.et_input_num);
        rgGroup = (RadioGroup) window.findViewById(R.id.rg_group);
        btnDib = (Button) window.findViewById(R.id.btn_dib);
        tvReduce.setTag("+");
        tvADD.setTag("-");
        //加减添加监听
        tvReduce.setOnClickListener(new OnButtonClickListener());
        tvADD.setOnClickListener(new OnButtonClickListener());
        etInputNum.addTextChangedListener(new OnTextChangeListener());
        cancel.setOnClickListener(this);
        //数据
        btnDib.setOnClickListener(this);
        dialog.setCanceledOnTouchOutside(true);
        rgGroup.setOnCheckedChangeListener(new OnItemCheckedListener());

    }

    /**
     * 创建分享界面
     */
    private void showSharedialog() {
        alertDialog = new AlertDialog.Builder(this, R.style.theme_dialog)
                .create();
        // 调用系统的输入法
        alertDialog.setView(new EditText(this));
        alertDialog.show();
        Window window = alertDialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_share);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        ImageView ivWechatShare = (ImageView) window.findViewById(R.id.iv_wechat_share);
        ImageView ivWeiboShare = (ImageView) window.findViewById(R.id.iv_weibo_share);
        ImageView ivQqShare = (ImageView) window.findViewById(R.id.iv_qq_share);
        ImageView ivQqZoneShare = (ImageView) window.findViewById(R.id.iv_qq_zone_share);
        ivWechatShare.setOnClickListener(this);
        ivWeiboShare.setOnClickListener(this);
        ivQqShare.setOnClickListener(this);
        ivQqZoneShare.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
    }


    private void collectAndCancelGoods(String module, String action, String goodsId, final String text) {
        try {
            IRequest.post(ShopDetailActivity.this, module, createParams(goodsId, action))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("onSuccess", "onSuccess: " + response.get());
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.getInt("code");
                                if (code == 1) {
                                    ToastUtils.showToast(ShopDetailActivity.this, text + "成功");
                                } else {
                                    ToastUtils.showToast(ShopDetailActivity.this, text + "成功");
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


    private Map createParams(String goodId, String action) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", action);
        params.put("userId", String.valueOf(userId));
        params.put("goodsId", goodId);
        params.put("sessionToken", getSharedPreferences("loginInfo", MODE_PRIVATE).getString("session", ""));
        Log.d("onItemClick", "createParams:商品详情界面： " + goodId);
        return params;
    }


    private class MyScrollViewListener implements ObservableScrollView.ScrollViewListener {
        @Override
        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            if (y < 0) {
                rlBgColor.setBackgroundColor(Color.argb(0, 93, 104, 130));
                tvTitle.setBackgroundColor(Color.argb(0, 255, 255, 255));
            } else if (y > 0 && y < imageHeight) {
                float scale = (float) y / imageHeight;
                float alpha = (255 * scale);
                rlBgColor.setBackgroundColor(Color.argb((int) alpha, 93, 104, 130));
                tvTitle.setTextColor(Color.argb((int) alpha, 255, 255, 255));
            } else if (y == 0) {
                rlBgColor.setBackgroundColor(Color.argb(0, 93, 104, 130));
                tvTitle.setTextColor(Color.argb(0, 255, 255, 255));
            } else {
                rlBgColor.setBackgroundColor(Color.argb(255, 93, 104, 130));
                tvTitle.setTextColor(Color.argb(255, 255, 255, 255));
            }
        }
    }


    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            rlMeasure.getViewTreeObserver().removeGlobalOnLayoutListener(
                    this);
            imageHeight = rlMeasure.getHeight();
            oScrollview.setScrollViewListener(new MyScrollViewListener());
        }
    }

    /**
     * 条目点击监听
     */
    class OnItemCheckedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
            switch (checkId) {
                case R.id.rb_5:
                    choiceType = 5;
                    break;
                case R.id.rb_10:
                    choiceType = 10;
                    break;
                case R.id.rb_20:
                    choiceType = 20;
                    break;
                case R.id.rb_bw://包尾 0
                    choiceType = 2;
                    break;
                case R.id.rb_ms://秒杀 1
                    choiceType = 1;
                    break;
                default:
                    choiceType = 0;
                    break;
            }
        }
    }

    /**
     * 加减按钮事件监听器
     */
    class OnButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String numString = etInputNum.getText().toString();

            if (numString == null || numString.equals("")) {
                num = 1;
                etInputNum.setText("1");
            } else {
                if (v.getTag().equals("-")) {
                    if (++num < 0) //先加，再判断
                    {
                        num--;
                        Toast.makeText(ShopDetailActivity.this, "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        etInputNum.setText(String.valueOf(num));
                    }
                } else if (v.getTag().equals("+")) {
                    if (--num < 0) //先减，再判断
                    {
                        num++;
                        Toast.makeText(ShopDetailActivity.this, "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        etInputNum.setText(String.valueOf(num));
                    }
                }
            }
        }
    }

    /**
     * EditText输入变化事件监听器
     */
    class OnTextChangeListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            String numString = s.toString();
            if (numString == null || numString.equals("")) {
                num = 0;
            } else {
                int numInt = Integer.parseInt(numString);
                if (numInt < 0) {
                    Toast.makeText(ShopDetailActivity.this, "请输入一个大于0的数字",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //设置EditText光标位置 为文本末端
                    etInputNum.setSelection(etInputNum.getText().toString().length());
                    num = numInt;
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    }
}
