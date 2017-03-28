package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.AnnouncedBean;
import net.youlvke.yanyuke.bean.OngoingBean;
import net.youlvke.yanyuke.bean.WinningBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.FileUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CountDownView;
import net.youlvke.yanyuke.view.CustomImageView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * 我参与的界面
 */
public class MyJoinActivity extends BaseActivity {


    private TextView tvTitle;
    private RadioGroup rgJoinGroup;
    private ListView lvMyJoin;
    private int ViewType;//注意这个不同布局的类型起始值必须从0开始
    private MyAdapter adapter;
    private int sequenceState = 1;
    private List<OngoingBean.DataBean> data1;
    private Intent intent;
    private List<WinningBean.DataBean> data2;
    private List<AnnouncedBean.DataBean> data3;
    private Context mContext;


    @Override
    public int getLayoutResId() {

        this.mContext = this;
        return R.layout.activity_my_join;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvMyJoin = (ListView) findViewById(R.id.lv_my_join);
        rgJoinGroup = (RadioGroup) findViewById(R.id.rg_join_group);

    }

    @Override
    protected void initListener() {
        rgJoinGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_join_ing:
                        ViewType = 0;
                        sequenceState = 1;

                        break;
                    case R.id.rb_join_winning:
                        ViewType = 1;
                        sequenceState = 2;
                        break;
                    case R.id.rb_join_results:
                        ViewType = 2;
                        sequenceState = 3;
                        break;
                }
                loadData(sequenceState);
                //刷新数据
                //adapter.notifyDataSetInvalidated();
            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText("我参与的");
        loadData(sequenceState);
        adapter = new MyAdapter();
    }

    private void loadData(final int sequenceState) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createParams(sequenceState))
                    .loading(true)
                    .execute(new RequestListener() {


                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("MyJoinActivity", "onSuccess: " + response.get());
                            Gson gson = new Gson();
                            try {

                                switch (sequenceState) {
                                    case 1:
                                        OngoingBean ongoingBean = gson.fromJson(response.get(), OngoingBean.class);
                                        if (ongoingBean.getCode() == 1 && ongoingBean.getData() != null) {
                                            data1 = ongoingBean.getData();
                                            if (data1 != null) {
                                                lvMyJoin.setAdapter(adapter);
                                            }
                                        } else {
                                            ToastUtils.showToast(MyJoinActivity.this, "没有要进行中的开奖！");
                                        }
                                        break;
                                    case 2:
                                        WinningBean winningBean = gson.fromJson(response.get(), WinningBean.class);
                                        if (winningBean.getCode() == 1 && winningBean.getData() != null) {
                                            data2 = winningBean.getData();
                                            if (data2 != null) {
                                                lvMyJoin.setAdapter(adapter);
                                            }
                                        } else {
                                            ToastUtils.showToast(MyJoinActivity.this, "没有要进行中的开奖！");
                                        }
                                        break;
                                    case 3:

                                        AnnouncedBean announcedBean = gson.fromJson(response.get(), AnnouncedBean.class);
                                        if (announcedBean.getCode() == 1 && announcedBean.getData() != null) {
                                            data3 = announcedBean.getData();
                                            if (data3 != null) {
                                                lvMyJoin.setAdapter(adapter);
                                            }
                                        } else {
                                            ToastUtils.showToast(MyJoinActivity.this, "没有要进行中的开奖！");
                                        }
                                        break;

                                }

                            } catch (JsonParseException e) {
                                ToastUtils.showToast(MyJoinActivity.this, "数据解析失败！");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Map createParams(int sequenceState) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160111");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        params.put("sequenceState", String.valueOf(sequenceState));
        Log.d("MyJoinActivity", "createParams: " + String.valueOf(sequenceState));
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
    }

    /**
     * 我的适配器
     */
    class MyAdapter extends BaseAdapter implements View.OnClickListener {

        protected int currentPostion;
        private OngoingBean.DataBean dataBean;

        @Override
        public int getCount() {
            if (sequenceState == 1) {
                return data1.size();
            } else if (sequenceState == 2) {
                return data2.size();
            }
            return data3.size();
        }

        @Override
        public Object getItem(int position) {
            if (sequenceState == 1) {
                return data1.get(position);
            } else if (sequenceState == 2) {
                return data2.get(position);
            }
            return data3.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //得到条目的样式
        @Override
        public int getItemViewType(int position) {
            super.getItemViewType(position);
            if (ViewType == 0) {
                return ViewType;
            } else if (ViewType == 1) {
                return ViewType;
            } else {
                return ViewType = 2;
            }
        }

        //得到样式条目的数量
        @Override
        public int getViewTypeCount() {
            super.getViewTypeCount();
            return 3;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            TypeOneViewHolder typeOneViewHolder = null;
            TypeTwoViewHolder typeTwoViewHolder = null;
            TypeThreeViewHolder typeThreeViewHolder = null;
            ShowTextColor showTextColor = new ShowTextColor();

            if (convertView == null) {
                switch (ViewType) {
                    case 0:
                        convertView = getLayoutInflater().inflate(R.layout.item_shoping_ing, viewGroup, false);
                        typeOneViewHolder = new TypeOneViewHolder(convertView);
                        convertView.setTag(typeOneViewHolder);
                        break;
                    case 1:
                        convertView = getLayoutInflater().inflate(R.layout.item_shoping_winning, viewGroup, false);
                        typeTwoViewHolder = new TypeTwoViewHolder(convertView);
                        convertView.setTag(typeTwoViewHolder);
                        break;
                    case 2:
                        convertView = getLayoutInflater().inflate(R.layout.item_shoping_results, viewGroup, false);
                        typeThreeViewHolder = new TypeThreeViewHolder(convertView);
                        convertView.setTag(typeThreeViewHolder);
                        break;
                }

            } else {

                if (ViewType == 0) {
                    typeOneViewHolder = (TypeOneViewHolder) convertView.getTag();
                } else if (ViewType == 1) {
                    typeTwoViewHolder = (TypeTwoViewHolder) convertView.getTag();
                } else if (ViewType == 2) {
                    typeThreeViewHolder = (TypeThreeViewHolder) convertView.getTag();
                }
            }

            //设置数据
            if (ViewType == 0) {
                dataBean = data1.get(position);
                //加载图片
                loadImage(typeOneViewHolder.ivPic1, dataBean.getGoodsinfo().getCoverPic().get(0).getPictureUrl());
                typeOneViewHolder.tvPayTitle.setText(dataBean.getGoodsinfo().getGoodsName());
                showTextColor.showDoubleTextColor(typeOneViewHolder.tvJoinDesc0,
                        "第" + dataBean.getGoodsinfo().getGoodsSequenceNum() + "期<br />本次参与&nbsp&nbsp",
                        "" + dataBean.getCount() + "人次");
                int partProgress = (int) ((double) dataBean.getSalesCount() / (double) dataBean.getTotalCount() * 100);
                typeOneViewHolder.tvJoinProgress.setText(String.format("参与进度%s%%", String.valueOf(partProgress)));
                typeOneViewHolder.progressBar.setProgress(partProgress);
                int remaining = dataBean.getTotalCount() - dataBean.getSalesCount();
                typeOneViewHolder.tvResidus.setText(String.format("剩余%s份", String.valueOf(remaining)));
                typeOneViewHolder.btnAddTo.setOnClickListener(this);

            } else if (ViewType == 1) {

                final WinningBean.DataBean dataBean1 = data2.get(position);
                showTextColor.showDoubleTextColor(typeTwoViewHolder.tvJoinDesc1,
                        "第" + FileUtils.modifyclauses(dataBean1.getSequenceId()) + "期<br />本次参与&nbsp&nbsp", "" + dataBean1.getSalesCount() + "人次");
               /* try {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long time = sdf.parse(dataBean1.getAnnouncedDate()).getTime();
                    long countdown = time - System.currentTimeMillis();
                    Log.d("countdown", "getView: "+countdown);
                    if (countdown ==0){
                        ToastUtils.showToast(MyJoinActivity.this,"<<<<<<<<<<");
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                typeTwoViewHolder.tvPayTitle.setText(dataBean1.getGoodsinfo().getGoodsName());
                typeTwoViewHolder.tvCountTime.setTypeMills(dataBean1.getCountdown());
                typeTwoViewHolder.tvCountTime.setOnTimeCountDownListener(new CountDownView.OnTimeCountDownListener() {
                    @Override
                    public void onTimeCountDown() {
                        Log.d("onTimeCountDown", "onTimeCountDown: <<<<<<<<<<");
                        announcedData(String.valueOf(dataBean1.getSequenceId()),String.valueOf(dataBean1.getPeriodsId()));
                    }
                });

                loadImage(typeTwoViewHolder.ivPic1, dataBean1.getGoodsinfo().getCoverPic().get(0).getPictureUrl());
            } else if (ViewType == 2) {
                AnnouncedBean.DataBean dataBean3 = data3.get(position);
                loadImage(typeThreeViewHolder.ivPic1,dataBean3.getGoodsinfo().getCoverPic().get(0).getPictureUrl());
                //参与期数
                showTextColor.showDoubleTextColor(typeThreeViewHolder.tvJoinDesc2,
                        "第"+dataBean3.getSequenceId()+"期<br />本次参与&nbsp&nbsp", ""+dataBean3.getCount()+"人次");

                //幸运得主：天下俞超520\n：1000016\n总        需  888人次\n揭晓时间  2016.11.11  13：09：00"
                showTextColor.showFourTextColor(typeThreeViewHolder.tvPayDesc,
                        "幸运得主&nbsp&nbsp", ""+dataBean3.getNickname()+"<br />", "神秘代码&nbsp&nbsp", ""+dataBean3.getWinningNumber()+"");
                String format1 = String.format("总\b\b\b\b\b\b需\b\b%s", String.valueOf(dataBean3.getTotalCount()));
                String format2 = String.format("揭晓时间\b\b%s", DateUtils.formatDatePoint(dataBean3.getReleaseDate()));
                typeThreeViewHolder.tvJoinDesc3.setText(format1+"\n"+format2);
                typeThreeViewHolder.tvPayTitle.setText(dataBean3.getGoodsinfo().getGoodsName());
            }
            return convertView;
        }

        private void loadImage(ImageView imageView, String url) {
            Glide.with(MyJoinActivity.this)
                    .load(url + "?x-oss-process=style/Android_LOTTERY")
                    .into(imageView);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_add_to:
                    intent = new Intent();
                    intent.setClass(MyJoinActivity.this, ShopDetailActivity.class);
                    intent.putExtra("goodsId", dataBean.getGoodsId());
                    startActivity(intent);
                    break;
            }
        }


        public class TypeOneViewHolder {
            public View rootView;
            public CustomImageView ivPic1;
            public TextView tvPayTitle;
            public TextView tvJoinDesc0;
            public TextView tvJoinProgress;
            public TextView tvResidus;
            public ProgressBar progressBar;
            public Button btnAddTo;

            public TypeOneViewHolder(View rootView) {
                this.rootView = rootView;
                this.ivPic1 = (CustomImageView) rootView.findViewById(R.id.iv_pic_1);
                this.tvPayTitle = (TextView) rootView.findViewById(R.id.tv_pay_title);
                this.tvJoinDesc0 = (TextView) rootView.findViewById(R.id.tv_join_desc0);
                this.tvJoinProgress = (TextView) rootView.findViewById(R.id.tv_join_progress);
                this.tvResidus = (TextView) rootView.findViewById(R.id.tv_residus);
                this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
                this.btnAddTo = (Button) rootView.findViewById(R.id.btn_add_to);
            }

        }

        public class TypeTwoViewHolder {
            public View rootView;
            public CountDownView tvCountTime;
            public CustomImageView ivPic1;
            public TextView tvPayTitle;
            public TextView tvJoinDesc1;

            public TypeTwoViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvCountTime = (CountDownView) rootView.findViewById(R.id.tv_countTime);
                this.ivPic1 = (CustomImageView) rootView.findViewById(R.id.iv_pic_1);
                this.tvPayTitle = (TextView) rootView.findViewById(R.id.tv_pay_title);
                this.tvJoinDesc1 = (TextView) rootView.findViewById(R.id.tv_join_desc1);
            }
        }

        public  class TypeThreeViewHolder {
            public View rootView;
            public ImageView ivPic1;
            public TextView tvPayTitle;
            public TextView tvJoinDesc2;
            public TextView tvPayDesc;
            public TextView tvJoinDesc3;

            public TypeThreeViewHolder(View rootView) {
                this.rootView = rootView;
                this.ivPic1 = (ImageView) rootView.findViewById(R.id.iv_pic_1);
                this.tvPayTitle = (TextView) rootView.findViewById(R.id.tv_pay_title);
                this.tvJoinDesc2 = (TextView) rootView.findViewById(R.id.tv_join_desc2);
                this.tvPayDesc = (TextView) rootView.findViewById(R.id.tv_pay_desc);
                this.tvJoinDesc3 = (TextView) rootView.findViewById(R.id.tv_join_desc3);
            }

        }
    }

    private void announcedData(String SequenceId,String periodsId) {
        try {
            IRequest.post(mContext,Constants.BASEURL_YYKGOODS,createWinParams(SequenceId,periodsId)).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("announcedData", "onSuccess: "+response.get());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createWinParams(String sequenceId,String periodsId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160215");
        params.put("sequenceId", sequenceId);
        params.put("periodsId", periodsId);
        Log.d("announcedData", "createWinParams: "+sequenceId+"<<<<<"+periodsId);
        return params;
    }

    class ShowTextColor {
        /**
         * @param view
         * @param text1
         * @param text2
         */
        private void showDoubleTextColor(TextView view, String text1, String text2) {
            String str = "<font color='#333333'>" + text1 + "</font>"
                    + "<font color= '#c55555'>" + text2 + "</font>";
            view.setText(Html.fromHtml(str));
        }

        private void showFourTextColor(TextView view, String text1, String text2, String text3, String text4) {
            String str = "<font color='#333333'>" + text1 + "</font>"
                    + "<font color= '#4487D2'>" + text2 + "</font>"
                    + "<font color= #333333'>" + text3 + "</font>"
                    + "<font color= '#4487D2'>" + text4 + "</font>";
            view.setText(Html.fromHtml(str));
        }
    }

}
