package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.controller.TimePickerView;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.PhotoUtils;
import net.youlvke.yanyuke.utils.TelphoneUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.utils.UpLoadPhotoPathNameUtils;
import net.youlvke.yanyuke.view.listener.WheelView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.path;
import static net.youlvke.yanyuke.R.id.tv_select_num;
import static net.youlvke.yanyuke.R.id.tv_title;
import static net.youlvke.yanyuke.activity.MainActivity.access_key_id;
import static net.youlvke.yanyuke.activity.MainActivity.access_key_secret;
import static net.youlvke.yanyuke.activity.MainActivity.bucket;
import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * 我的个人详情界面
 *
 * @author Administrator
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MePersonDetailActivity extends BaseActivity {


    private ImageView ivImgHead;

    private Button btn_picture;

    private Button btn_photo;

    private Button btn_cancle;

    private Bitmap head;// 头像Bitmap

    private RelativeLayout rlNickName;// 昵称布局

    private WheelView year;

    private WheelView month;

    private WheelView day;

    private TextView nickName;

    private RelativeLayout rlBirthday;

    private TextView tvSelectDate;

    private SharedPreferences sp;

    private Button btnConfirm;// 确定

    private EditText etUserName;// 用户名

    private ImageView ivCancel;// 清空

    private String updateName;

    private RelativeLayout rlAddAddress;

    private View tvMasker;// 面具

    private TimePickerView pvTime;

    private RelativeLayout rlTelNum;

    private TextView tvTitle;
    private String ageDate;
    private TextView tvSelectNum;
    private String boundtelphone;
    private OSS oss;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private Dialog dialog;
    private String headPath;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what== 1){
                upLoadHead();
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_me_preson_detail;
    }


    /**
     * 初始化控件
     */
    protected void initView() {

        ivImgHead = (ImageView) findViewById(R.id.iv_img_head);
        rlNickName = (RelativeLayout) findViewById(R.id.rl_nickname);
        rlBirthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        rlTelNum = (RelativeLayout) findViewById(R.id.rl_telNum);
        nickName = (TextView) findViewById(R.id.tv_nick_name);
        tvSelectDate = (TextView) findViewById(R.id.tv_select_date);
        rlAddAddress = (RelativeLayout) findViewById(R.id.rl_add_address);
        tvMasker = findViewById(R.id.tv_Masker);
        tvTitle = (TextView) findViewById(tv_title);
        tvSelectNum = (TextView) findViewById(tv_select_num);

    }

    @Override
    protected void initListener() {
        ivImgHead.setOnClickListener(this);
        rlNickName.setOnClickListener(this);
        rlBirthday.setOnClickListener(this);
        rlAddAddress.setOnClickListener(this);
        rlTelNum.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的信息");
        sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        loadDatas();
        userId = sp.getInt("userId", 1);//
        updateName = sp.getString("nickname", "");
        nickName.setText(updateName);
        ageDate = sp.getString("age_date", "1991/9/15");
        tvSelectDate.setText(ageDate);
        showMainPagerPhoto();// 显示主页头像


    }

    private void loadDatas() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createPersonalParams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("on88Success", "onSuccess: " + response.get());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createPersonalParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160103");
        params.put("userId", String.valueOf(userId));
        Log.d("createPersonalParams", "createPersonalParams: " + String.valueOf(userId));
        params.put("sessionToken", session);
        Log.d("createPersonalParams", "createPersonalParams: " + userId);
        return params;
    }

    /**
     * 显示主页头像
     */
    private void showMainPagerPhoto() {
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(toRoundBitmap(bt));// 转换成drawable
            ivImgHead.setImageDrawable(drawable);
        } else {
            /** 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中 */
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //String updateName = sp.getString("nickname", "李晓明");
        //nickName.setText(updateName);
        showMainPagerPhoto();
        boundtelphone = sp.getString("boundtelphone", "绑定手机号");
        tvSelectNum.setText(TelphoneUtils.subTel(boundtelphone));
    }


    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.iv_img_head:
                showNotifyImageDialog();
                break;
            case R.id.rl_nickname:
                showNotifyNameDialog();
                break;
            case R.id.rl_birthday:// 显示年龄
                showNotifyAge();
                pvTime.show();
                break;
            case R.id.iv_cancel:
                etUserName.setText("");
                break;
            case R.id.rl_telNum:
                startActivity(new Intent(MePersonDetailActivity.this, BoundTelNumActivity.class));
                break;
            case R.id.rl_add_address:// TODO
                getAddress();
                break;
            case R.id.btn_picture:
                Intent openAlbumIntent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                dialog.dismiss();
                break;
            case R.id.btn_photo:
                Intent openCameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "image.jpg"));
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                dialog.dismiss();
                break;
            case R.id.btn_cancle:
                dialog.dismiss();
                break;
        }
    }

    private void getAddress() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createGetAddress())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("getAddress", "onSuccess: " + response.get());
                            try {
                                JSONObject json = new JSONObject(response.get());
                                int code = json.optInt("code");
                                if (code == 1) {//没有地址
                                    startActivity(new Intent(MePersonDetailActivity.this, SetAddressActivity.class));

                                } else if (code == 2) {//有地址
                                    startActivity(new Intent(MePersonDetailActivity.this, AlterAddressActivity.class));
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

    private Map createGetAddress() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160138");
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", session);
        return params;
    }


    private void showNotifyAge() {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        // 控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 60,
                calendar.get(Calendar.YEAR));// 要在setTime 之前才有效果哦0
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                if (!ageDate.equals(getTime(date))) {
                    motifyAge(getTime(date));
                }
            }
        });
    }

    private void motifyAge(final String date) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createMotifyNameAndAge("20160105", "birth", date))
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.getInt("code");
                                Log.d("motify", "onSuccess: " + response.get());
                                if (code == 1) {
                                    sp.edit().putString("age_date", date).commit();
                                    tvSelectDate.setText(date);
                                    ToastUtils.showToast(MePersonDetailActivity.this, "修改成功");
                                } else if (code == 2) {
                                    ToastUtils.showToast(MePersonDetailActivity.this, "修改失败");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    /**
     * 修改昵称的弹窗
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showNotifyNameDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.theme_dialog)
                .create();
        // 调用系统的输入法
        dialog.setView(new EditText(this));

        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_notify_name);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        etUserName = (EditText) window.findViewById(R.id.et_user_name);
        ivCancel = (ImageView) window.findViewById(R.id.iv_cancel);
        btnConfirm = (Button) window.findViewById(R.id.btn_confirm);
        etUserName.setText(sp.getString("nickname", null));
        etUserName.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivCancel.setVisibility(View.VISIBLE);
                } else {
                    ivCancel.setVisibility(View.GONE);
                }
            }
        });

        ivCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //etUserName.setText(sp.getString("nickname",null));
                String newName = etUserName.getText().toString().trim();
                if (!sp.getString("nickname", "").equals(newName)) {
                    loadData(newName);
                    nickName.setText(newName);
                    sp.edit().putString("nickname", newName).commit();
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
    }

    private void loadData(final String newName) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createMotifyNameAndAge("20160136", "nickname", newName))
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.getInt("code");
                                Log.d("motify", "onSuccess: " + response.get());
                                if (code == 1) {
                                    sp.edit().putString("nickname", newName).commit();
                                    ToastUtils.showToast(MePersonDetailActivity.this, "修改成功");
                                } else if (code == 2) {
                                    ToastUtils.showToast(MePersonDetailActivity.this, "修改失败");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改昵称
    private Map createMotifyNameAndAge(String action, String key, String content) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", action);
        params.put(key, content);
        params.put("userId", userId + "");
        params.put("sessionToken", sp.getString("session", null));
        return params;
    }

    /**
     * 显示头像修改弹窗
     */
    private void showNotifyImageDialog() {
        View view = getLayoutInflater().inflate(
                R.layout.activity_show_photo_dialog, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        btn_picture = (Button) window.findViewById(R.id.btn_picture);
        btn_photo = (Button) window.findViewById(R.id.btn_photo);
        btn_cancle = (Button) window.findViewById(R.id.btn_cancle);
        btn_picture.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
            initOss();
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = PhotoUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            ivImgHead.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = PhotoUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            opLoadHead(imagePath);
        }
    }


    private void opLoadHead(final String imagePath) {
        new Thread(new Runnable() {



            @Override
            public void run() {
                //new OssPutObjectUtils(oss,bucket,path,).asyncPutObjectFromLocalFile();
                headPath = UpLoadPhotoPathNameUtils.getHeadPath();
                Log.d("TAGrun", "run: " + UpLoadPhotoPathNameUtils.getHeadPath() + imagePath + "bucket" + bucket);
                PutObjectRequest put = new PutObjectRequest(bucket,headPath , imagePath);
                ObjectMetadata metadata = new ObjectMetadata();
                // 指定Content-Type
                metadata.setContentType("application/octet-stream");
                // user自定义metadata
                metadata.addUserMetadata("yanyuke", "image/Avatar");
                put.setMetadata(metadata);
                // Log.d("shangchuan",testObject+uploadFilePath);
                // 异步上传时可以设置进度回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                    }
                });


                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        handler.sendEmptyMessage(1);
                       // upLoadHead();
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                            Log.d("clientExcepion", clientExcepion.toString());
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
            }
        }).start();
    }

    private void initOss() {

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(access_key_id, access_key_secret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(4); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(3); // 失败后最大重试次数，默认2次
        // oss为全局变量，OSS_ENDPOINT是一个OSS区域地址
        oss = new OSSClient(getApplicationContext(), Constants.OSS_ENDPOINT, credentialProvider, conf);
    }

    private void upLoadHead() {

        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createDelParas())
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                if (jsonObject.getInt("code")==1){

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

    private Map createDelParas() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160104");// "20160109"
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", session);
        params.put("avatar",  Constants.UPLOAD_URL+headPath);
        Log.d("onSuccess", "createDelParas: "+String.valueOf(userId)+session+"<<<<<" +headPath);
        return params;
    }



    /**
     * 把bitmap转成圆形
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        // 取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        // 构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        // new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        // 设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        // 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        // 且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        // 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pvTime != null && pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
