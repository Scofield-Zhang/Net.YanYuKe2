package net.youlvke.yanyuke.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.activity.BusinessHomeActivity;
import net.youlvke.yanyuke.activity.CardDisableActivity;
import net.youlvke.yanyuke.activity.ContactCustomerActivity;
import net.youlvke.yanyuke.activity.LoginAndRegisterActivity;
import net.youlvke.yanyuke.activity.MainActivity;
import net.youlvke.yanyuke.activity.MePersonDetailActivity;
import net.youlvke.yanyuke.activity.MyCardBagActivity;
import net.youlvke.yanyuke.activity.MyJoinActivity;
import net.youlvke.yanyuke.activity.MyNotPayActivity;
import net.youlvke.yanyuke.activity.MyVouchersActivity;
import net.youlvke.yanyuke.activity.MyWalletActivity;
import net.youlvke.yanyuke.activity.NotEvaluateActivity;
import net.youlvke.yanyuke.activity.ShopCollectActivity;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ScreenUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CircleImageView;
import net.youlvke.yanyuke.view.SlidingArcView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeTextView;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;


/**
 * 这个是我的界面
 *
 * @author Administrator
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MeFragment extends BaseFragment{
  //  private SetRoundHeadImageUtils setRoundHeadImageUtils;
    private String updataName;//更新的用户名
    private RadioButton rbUserStatus;
    private MainActivity mainActivity;
    private String avatarURl;
    private int userlevel;
    public static String session;

    /**
     * 当轮转的条目点击的时候
     */

    private final class OnItemClickListener implements
            SlidingArcView.QTItemClickListener {

        @Override
        public void onClick(View v, int index) {

            if (isLogin()) {
                switch (index) {
                    case 0://卡券包
                        startActivity(new Intent(getActivity(), MyCardBagActivity.class));
                        break;
                    case 1://抵用券
                        startActivity(new Intent(getActivity(), MyVouchersActivity.class));
                        break;
                    case 2://我的钱包界面
                        startActivity(new Intent(getActivity(), MyWalletActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), ContactCustomerActivity.class));
                        break;
                    case 4://客户电话
                        startActivity(new Intent(getActivity(), BusinessHomeActivity.class));
                        break;
                }
            } else
                startActivity(new Intent(getActivity(), LoginAndRegisterActivity.class));
        }
    }
    /**
     *  对登录的状态进行判断
     */
    public boolean isLogin() {
        if (userId == 4) {
            ToastUtils.showToast(getActivity(), "请先登录!");
            Log.d("isLogins", "isLogins: "+userId);
            return false;
        }
        Log.d("isLogins", "isLogins: "+userId);
        return true;
    }

    // 轮转条目滚动选择的时候
    private final class OnSelectorScrollListener implements SlidingArcView.QTScrollListener {
        @Override
        public void onSelect(View v, int index) {

        }
    }

    private int NAME_AND_PSWD = 0;

    private SlidingArcView slidingArcView;// 滑动选择

    private TextView nickName;// 用户名

    private TextView userGrade;// 用户等级

    private BGABadgeImageView meMsg;// 提示信息

    private ImageView meCome;// 签到

    private ImageView meCollect;// 收藏

    private CircleImageView imgAvatar;// 用户头像

    private BGABadgeTextView tvNotPay;// 代付款

    private BGABadgeTextView tvNotUse;//待使用

    private BGABadgeTextView tvNotEvaluate;//待评价

    private Button meOrder;//订单按钮

    private SharedPreferences sp;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View initView() {
        setStatusColor(Color.parseColor("#3B3E45"));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 获取屏幕的高度
        ScreenUtils.initScreen(getActivity());
        sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);

        View view = View.inflate(getActivity(), R.layout.activity_me, null);
        slidingArcView = (SlidingArcView) view.findViewById(R.id.sliding_view);
        nickName = (TextView) view.findViewById(R.id.tv_nickname);// 用户名
        userGrade = (TextView) view.findViewById(R.id.user_grade);// 用户等级
        meMsg = (BGABadgeImageView) view.findViewById(R.id.me_msg);// 提示信息
        meCome = (ImageView) view.findViewById(R.id.me_come);// 签到
        meCollect = (ImageView) view.findViewById(R.id.me_collect);// 收藏
        imgAvatar = (CircleImageView) view.findViewById(R.id.img_avatar);// 用户头像
        tvNotPay = (BGABadgeTextView) view.findViewById(R.id.tv_not_pay);// 代付款
        tvNotUse = (BGABadgeTextView) view.findViewById(R.id.tv_not_use);//待使用
        tvNotEvaluate = (BGABadgeTextView) view.findViewById(R.id.tv_not_evaluate);//待评价
        meOrder = (Button) view.findViewById(R.id.me_order);//待使用
        rbUserStatus = (RadioButton) view.findViewById(R.id.rb_user_status);
        // 添加监听
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initListener() {
        meMsg.setOnClickListener(this);
        meCome.setOnClickListener(this);
        meCollect.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        tvNotPay.setOnClickListener(this);
        tvNotEvaluate.setOnClickListener(this);
        tvNotUse.setOnClickListener(this);
        slidingArcView.setQtScrollListener(new OnSelectorScrollListener());
        slidingArcView.setQtItemClickListener(new OnItemClickListener());
    }


    @Override
    public void initData() {
        userId = sp.getInt("userId", 0);
        Log.d("initData", "initData: "+userId);
        //设置徽章
        setBadgeTextViwe();
        meMsg.showCirclePointBadge();
        //setRoundHeadImageUtils = new SetRoundHeadImageUtils();
        //setRoundHeadImageUtils.selectHeadImage(imgAvatar);
        updataName  = sp.getString("nickname", "张涛");
        nickName.setText(updataName);
        requestData();
    }

    private void requestData() {
        try {
            IRequest.post(getActivity(), Constants.BASEURL_YYKUSER, createUserInfoParams("20160103"))
                    .execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("onSuccess", "onSuccess: "+response.get());
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        int code = jsonObject.getInt("code");
                        switch (code){
                            case 1:
                                parserDatas(jsonObject);
                                break;
                            case 2:
                                if (userId != 0){
                                    ToastUtils.showToast(getActivity(),"用户不存在");
                                }
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", "onSuccess: "+e.toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void parserDatas(JSONObject jsonObject) {
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            if (!data.getString("nickname").equals(updataName)){
                nickName.setText(data.getString("nickname"));
                sp.edit().putString("nickname",data.getString("nickname")).apply();
            }
            int userStatu = data.getInt("userStatu");
            if (userStatu == 1){
                rbUserStatus.setSelected(false);
            }else if (userStatu == 2){
                rbUserStatus.setSelected(true);
            }
            avatarURl = data.getString("avatar");
            userlevel = data.getInt("userlevel");
            userGrade.setText(String.format("LV%s",String.valueOf(userlevel)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createUserInfoParams(String action) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", action);
        session = sp.getString("session", null);
        params.put("sessionToken", session);
        Log.d("createUserInfoParams", "createUserInfoParams: "+session);
        params.put("userId", String.valueOf(userId));
//        params.put("phone", tvTel.getText().toString().trim());
        return params;
    }

    /**
     * 设置badgeView
     */
    private void setBadgeTextViwe() {
        tvNotPay.showCirclePointBadge();
        tvNotUse.showCirclePointBadge();
        tvNotEvaluate.showCirclePointBadge();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();

        Glide
                .with(mainActivity)
                .load(avatarURl)
                .error(R.mipmap.user_vatar)
                .placeholder(R.mipmap.user_vatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgAvatar);
//        setRoundHeadImageUtils.selectHeadImage(imgAvatar);

       nickName.setText(updataName);
    }

    /**
     * 点击事件
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_msg://跳转到消息提醒界面
                meMsg.hiddenBadge();
                startActivity(new Intent(getActivity(), CardDisableActivity.class));
                break;
            case R.id.me_come://
                registration();
                break;
            case R.id.me_collect://
                startActivity(new Intent(getActivity(), ShopCollectActivity.class));
                break;
            case R.id.img_avatar://头像
                //username = sp.getString("username", null);
                if (userId == 0) {
                    startloadLoginAndRegister();
                } else {
                    startMeDetailActivity();
                }
                break;
            case R.id.tv_not_use:
                startActivity(new Intent(getActivity(), MyJoinActivity.class));
                break;
            case R.id.tv_not_evaluate://带评价
                startActivity(new Intent(getActivity(),NotEvaluateActivity.class));
                break;
            case R.id.tv_not_pay:
                startActivity(new Intent(getActivity(), MyNotPayActivity.class));
                break;
        }
    }
    /**签到*/
    private void registration() {
        try {
            IRequest.post(getActivity(), Constants.BASEURL_YYKUSER, createUserInfoParams("20160125")).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        int code = jsonObject.getInt("code");
                        if (code == 1){
                            ToastUtils.showToast(getActivity(),"恭喜您签到成功"+jsonObject.getString("message"));
                        }else if (code == 2){
                            ToastUtils.showToast(getActivity(),"恭喜您签到成功"+jsonObject.getString("message"));
                        }else {
                            ToastUtils.showToast(getActivity(),"网络异常");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("JSONException", "onSuccess: "+e.toString());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到个人详情界面
     */
    private void startMeDetailActivity() {
        Intent intent = new Intent(getActivity(), MePersonDetailActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到注册页面
     */
    private void startloadLoginAndRegister() {
        Intent intent = new Intent(getActivity(),
                LoginAndRegisterActivity.class);
        startActivityForResult(intent, NAME_AND_PSWD);
    }


    /**
     * 登陆注册返回的结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String userName = data.getStringExtra("username");
        String userPswd = data.getStringExtra("pswd");
        if (requestCode == NAME_AND_PSWD) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
