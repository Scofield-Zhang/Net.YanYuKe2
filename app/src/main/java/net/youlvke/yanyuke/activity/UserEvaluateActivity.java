package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.BitmapUtil;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.utils.UpLoadPhotoPathNameUtils;
import net.youlvke.yanyuke.view.CustomImageView;
import net.youlvke.yanyuke.view.RatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;

import static net.youlvke.yanyuke.activity.MainActivity.access_key_id;
import static net.youlvke.yanyuke.activity.MainActivity.access_key_secret;
import static net.youlvke.yanyuke.activity.MainActivity.bucket;

/**
 * 用户评价
 */
public class UserEvaluateActivity extends BaseActivity {

    private ArrayList<String> imgPaths = new ArrayList<>();
    private GridView gvSelectorPic;
    private PhotoPickerAdapter adapter;
    private TextView tvTitle;
    private OSS oss;
    private int number = -1;
    private Button btnConfirm;
    private EditText tvEvluationContent;
    private String evaluationId;
    private RatingBar mRatingBarShop;
    private float mStarCount;
    private String content;
    private String picUrl1;
    private StringBuffer append = new StringBuffer();
    private boolean isFinishUpLoad = false;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_evaluate;
    }

    @Override
    protected void initView() {
        evaluationId = getIntent().getStringExtra("evaluationId");
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        tvEvluationContent = (EditText) findViewById(R.id.tv_evluation_content);
        gvSelectorPic = (GridView) findViewById(R.id.gv_selector_pic);
        mRatingBarShop = (RatingBar) findViewById(R.id.m_ratingBar_shop);
    }

    @Override
    protected void initListener() {
        gvSelectorPic.setOnItemClickListener(new SelectorOnItemClickListener());
        btnConfirm.setOnClickListener(this);
        mRatingBarShop.setOnRatingChangeListener(new MyOnRatingChangeListener());
    }

    @Override
    protected void initData() {
        tvTitle.setText("评价详情");
        adapter = new PhotoPickerAdapter(imgPaths);
        gvSelectorPic.setAdapter(adapter);
    }



    private  class MyOnRatingChangeListener implements RatingBar.OnRatingChangeListener {

        @Override
        public void onRatingChange(float ratingCount) {
            UserEvaluateActivity.this.mStarCount = ratingCount;
        }
    }

    /**
     * 选择图片
     */
    private class SelectorOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == imgPaths.size()) {
                PhotoPicker.builder()
                        .setPhotoCount(4)
                        .setShowCamera(true)
                        .setSelected(imgPaths)
                        .setPreviewEnabled(true)
                        .start(UserEvaluateActivity.this, PhotoPicker.REQUEST_CODE);
            } else {
                Intent intent = new Intent();
                intent.setClass(UserEvaluateActivity.this, EnlargePicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imgPaths", imgPaths);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivityForResult(intent, position);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {

                //path = data.getStringArrayListExtra(UserEvaluateActivity.);
                imgPaths.clear();
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                imgPaths.addAll(photos);
                initOss();
                ossUpload(imgPaths.get(0));
                Log.d("onActivityResult", "onActivityResult: "+imgPaths.size());
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && requestCode >= 0 && requestCode <= 4) {
            imgPaths.remove(requestCode);
            adapter.notifyDataSetChanged();
        }
    }
    public void getPicUrl(String photos) {
        StringBuffer sb = new StringBuffer();
        append = sb.append(photos + "&");
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
    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.btn_confirm:
                if (!isFinishUpLoad && number > -1) {//没有传图片
                    ToastUtils.showToast(UserEvaluateActivity.this,"图片正在上传！请稍后……");
                    return;
                }
                content = tvEvluationContent.getText().toString().toString().trim();
                if (TextUtils.isEmpty(content)){
                    ToastUtils.showToast(UserEvaluateActivity.this,"请输入评价内容");
                    return;
                }
                loadData();
                break;
        }




    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createParams())
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("EvaluateInfoBean", "onSuccess: " + response.get());
                            try {
                                JSONObject jsonObject  = new JSONObject(response.get());

                              if( jsonObject.optInt("code")==1){
                                  ToastUtils.showToast(UserEvaluateActivity.this,"修改成功");
                                  finish();
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

    private Map createParams() {
        String trim = append.substring(0, append.length() - 1).toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160128");
        params.put("pictureUrl",trim );
        params.put("content", content);
        params.put("evaluationId", evaluationId);
        if ((int)mStarCount <=1){
            mStarCount=1;
        }
        params.put("score", String.valueOf((int) mStarCount));
        Log.d("createParams", "createParams: "+trim+"<<<<<"+String.valueOf((int) mStarCount));
        return params;
    }
    public void ossUpload(String strPath){
        // 判断图片是否全部上传
        // 如果已经是最后一张图片上传成功，则跳出
        number++;
        if (number == imgPaths.size()) {
            isFinishUpLoad =true;
            return;
        }
        // 指定数据类型，没有指定会自动根据后缀名判断
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("image/jpeg");
        objectMeta.setContentType("application/octet-stream");
        // user自定义metadata
        objectMeta.addUserMetadata("yanyuke", "image/goods");
        String goodsPath = UpLoadPhotoPathNameUtils.getGoodsPath();
        append .append(Constants.UPLOAD_URL+goodsPath+"&");
        PutObjectRequest put = new PutObjectRequest(bucket, goodsPath,strPath);
        put.setMetadata(objectMeta);
        try {
            PutObjectResult putObjectResult = oss.putObject(put);

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                // 在这里可以实现进度条展现功能
               // Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                // 这里进行递归单张图片上传，在外面判断是否进行跳出
                if (number <= imgPaths.size()-1) {
                    Log.d("PutObject", "onSuccess:++number "+number);
                    ossUpload(imgPaths.get(number));
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                ToastUtils.showToast(UserEvaluateActivity.this,"图片上传成功");
                return;
            }
        });
    }

    /**
     * 适配器
     */
    public class PhotoPickerAdapter extends BaseAdapter {

        private ArrayList<String> listPath;

        public PhotoPickerAdapter(ArrayList<String> listPath) {
            this.listPath = listPath;
        }

        @Override
        public int getCount() {

            if (listPath.size() == 4) {
                return listPath.size();
            } else {
                return listPath.size() + 1;
            }
        }

        @Override
        public Object getItem(int position) {
            return listPath.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_selector_pic, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == listPath.size()) {
                holder.ivAddPic.setImageResource(R.drawable.ic_add_evaluate_pic);
                if (position == 4) {
                    holder.ivAddPic.setVisibility(View.GONE);
                }
            } else {
                holder.ivAddPic.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFile(listPath.get(position), 500, 500));
            }
            return convertView;
        }
        class ViewHolder {
            public View rootView;
            public CustomImageView ivAddPic;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.ivAddPic = (CustomImageView) rootView.findViewById(R.id.iv_add_pic);
            }
        }
    }
}
