package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.CardBagBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.rqcode.BGAQRCodeUtil;
import net.youlvke.yanyuke.rqcode.QRCodeEncoder;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.RQCodeEncryptionUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * Created by Administrator on 2016/11/15 0015.
 * 我的卡券包
 */
public class MyCardBagActivity extends BaseActivity {

    private TextView tvTitle;
    private ListView lvCardBag;
    private List<CardBagBean.DataBean> datas;
    private MyAdapter adapter;
    private ImageView ivRq;
    private TextView tvRqcode;
    private AsyncTask<Void, Void, Bitmap> task;
    private Context mContext;

    @Override
    public int getLayoutResId() {
        this.mContext= this;
        return R.layout.activity_card_bag;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvCardBag = (ListView) findViewById(R.id.lv_card_bag);

    }

    @Override
    protected void initListener() {    }

    @Override
    protected void initData() {
        tvTitle.setText("我的卡券包");
        adapter = new MyAdapter();
        loadData();

    }

    private void loadData() {
        try {
            IRequest.post(MyCardBagActivity.this, Constants.BASEURL_YYKUSER, createCardparams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("MyCardBagActivity", "onSuccess: "+response.get());
                    Gson gson = new Gson();
                    CardBagBean cardBagBean = gson.fromJson(response.get(), CardBagBean.class);
                    //int code = jsonObject.getInt("code");
                    if (cardBagBean.getCode() == 1 && cardBagBean.getData()!= null) {
                        datas = cardBagBean.getData();
                        lvCardBag.setAdapter(adapter);
                    } else {
                        ToastUtils.showToast(MyCardBagActivity.this, "你没有卡券");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createCardparams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160112");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
    }

    /** 二维码生成 */
    private void createChineseQRCodeWithLogo(final String codeText) {
        task = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(MyCardBagActivity.this.getResources(), R.mipmap.ic_launcher);
                return QRCodeEncoder.syncEncodeQRCode(codeText, BGAQRCodeUtil.dp2px(MyCardBagActivity.this, 150), Color.parseColor("#000000"), logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    ivRq.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MyCardBagActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null)
        task.cancel(true);
    }
    /**
     * 大图浏览
     */
    private void showBigPic(int position) {
        final AlertDialog dialog = new AlertDialog.Builder(MyCardBagActivity.this, R.style.theme_dialog)
                .create();
        // 调用系统的输入法
        dialog.setView(new EditText(MyCardBagActivity.this));

        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_show_rqcode);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        ivRq = (ImageView) window.findViewById(R.id.iv_rq);
        tvRqcode = (TextView) window.findViewById(R.id.tv_rqcode);
        //二维码加密
        createChineseQRCodeWithLogo(datas.get(position).getCodeNumber());
        tvRqcode.setText(RQCodeEncryptionUtils.RQCodeDecryption(datas.get(position).getCodeNumber()));
        dialog.setCanceledOnTouchOutside(true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (task != null)
        task.cancel(true);
    }

    public class MyAdapter extends BaseAdapter implements View.OnClickListener {
        private int position;

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
            this.position = position;
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = MyCardBagActivity.this.getLayoutInflater().inflate(R.layout.item_card, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                convertView.getTag();
            }
            CardBagBean.DataBean dataBean = datas.get(position);
            holder.tvShopDetail.setText(dataBean.getGoodsInfo().getGoodsName());
            holder.tvPrice.setText(String.format("价值 ￥%s.0", dataBean.getGoodsInfo().getGoodsPrice()));
            Glide
                    .with(MyCardBagActivity.this)
                    .load(dataBean.getGoodsInfo().getCoverPic().get(0).getPictureUrl())
                    .into(holder.ivPic);
            holder.ivRQCode.setOnClickListener(this);
            return convertView;
        }

        @Override
        public void onClick(View view) {
            showBigPic(position);
        }


    }

    public class ViewHolder {
        public View rootView;
        public ImageView ivPic;
        public TextView tvShopDetail;
        public TextView tvDetailAddress;
        public ImageView ivLocation;
        public TextView tvReadyUse;
        public TextView tvValidTime;
        public LinearLayout llUseTime;
        public TextView tvPrice;
        public TextView tvActualPayNum;
        public TextView tvBondCode;
        public ImageView ivRQCode;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivPic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tvShopDetail = (TextView) rootView.findViewById(R.id.tv_shopDetail);
            this.tvDetailAddress = (TextView) rootView.findViewById(R.id.tv_detail_address);
            this.ivLocation = (ImageView) rootView.findViewById(R.id.iv_location);
            this.tvReadyUse = (TextView) rootView.findViewById(R.id.tv_ready_use);
            this.tvValidTime = (TextView) rootView.findViewById(R.id.tv_valid_time);
            this.llUseTime = (LinearLayout) rootView.findViewById(R.id.ll_use_time);
            this.tvPrice = (TextView) rootView.findViewById(R.id.tv_price);
            this.tvActualPayNum = (TextView) rootView.findViewById(R.id.tv_actual_pay_num);
            this.tvBondCode = (TextView) rootView.findViewById(R.id.tv_bond_code);
            this.ivRQCode = (ImageView) rootView.findViewById(R.id.iv_rq_code);
        }
    }
}

