package net.youlvke.yanyuke.activity;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.AddressInfo;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 修改展示地址界面
 *
 * @author Administrator
 */

public class AlterAddressActivity extends BaseActivity {

    private ListView lvAddress;
    private Button btnSubmit;
    protected int curentPosition;
    private TextView tvTitle;
    private List<AddressInfo.DataBean> datas;
    private MyAdapter adapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_manager_address;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvAddress = (ListView) findViewById(R.id.lv_address);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

    }


    @Override
    protected void initListener() {
        btnSubmit.setOnClickListener(this);
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                curentPosition = position;

            }
        });

    }

    @Override
    protected void initData() {
        tvTitle.setText("收货地址管理");
        datas = new ArrayList<>();
        loadAddressData();
        if (datas.size() == 0) {
            startActivity(new Intent(AlterAddressActivity.this, SetAddressActivity.class));
        }
        adapter = new MyAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAddressData();
    }

    private void loadAddressData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createGetAddress())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("getAddress_tag", "onSuccess: " + response.get());
                            Gson gson = new Gson();
                            AddressInfo addressInfo = gson.fromJson(response.get(), AddressInfo.class);
                            int code = addressInfo.getCode();
                            if (code == 1) {//绑定成功
                                datas = addressInfo.getData();
                            } else if (code == 2) {//服务器开小差了！
                                ToastUtils.showToast(AlterAddressActivity.this, "服务器开小差了！");
                            }
                            lvAddress.setAdapter(adapter);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createGetAddress() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160106");
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", session);
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.has_choice://选择是否是默认

                break;
            case R.id.btn_submit:
                startActivity(new Intent(this, SetAddressActivity.class));
                break;
            case R.id.iv_delete:

                break;
            default:
                break;
        }
    }

    private void delAddressParams(final String action, final int position) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createDelParas(action, position))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.optInt("code");
                                if (code == 1) {
                                    if (Integer.parseInt(action) == 20160109) {
                                        datas.remove(position);
                                        ToastUtils.showToast(AlterAddressActivity.this, "删除成功");
                                    } else if (Integer.parseInt(action) == 20160110) {
                                        ToastUtils.showToast(AlterAddressActivity.this, "修改成功");
                                    }
                                    adapter.notifyDataSetChanged();
                                    loadAddressData();
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

    private Map createDelParas(String action, int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", action);// "20160109"
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", session);
        params.put("addressId", datas.get(position).getAddressId());
        return params;
    }


    class MyAdapter extends BaseAdapter implements View.OnClickListener {

        private int position;
        ViewHolder holder;

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
        public View getView(int position, View view, ViewGroup viewGroup) {
            this.position = position;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_address, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            AddressInfo.DataBean dataBean = datas.get(position);
            holder.tvPersonInfo.setText(dataBean.getContactName() + "/" + dataBean.getContactPhone());
            holder.tvPersonAddress.setText(dataBean.getProvince() + dataBean.getCity() + dataBean.getAddressDetail());

            holder.ivDelete.setOnClickListener(this);
            holder.hasChoice.setOnClickListener(this);
            holder.ivEdit.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_delete:
                    delAddressParams("20160109", position);
                    break;
                case R.id.has_choice:
                    delAddressParams("20160110", position);
                    String defaultFlag = datas.get(position).getDefaultFlag();
                    Log.d("position", "onClick: " + curentPosition + Integer.parseInt(defaultFlag));
                    if (Integer.parseInt(defaultFlag) == 0) {
                        holder.hasChoice.setSelected(false);
                    } else if (Integer.parseInt(defaultFlag) == 1) {
                        holder.hasChoice.setSelected(true);
                    }
                case R.id.iv_edit:
                    Intent intent = new Intent();
                    AddressInfo.DataBean dataBeanCurrent = datas.get(position);
                    intent.putExtra("currentBean", dataBeanCurrent);
                    intent.setClass(AlterAddressActivity.this, MotifyAddressActivity.class);
                    startActivity(new Intent(intent));
                    break;
            }


        }

        public class ViewHolder {
            public View rootView;
            public TextView tvPersonInfo;
            public TextView tvPersonAddress;
            public ImageView hasChoice;
            public TextView tvDelete;
            public ImageView ivDelete;
            public ImageView ivEdit;
            public TextView tvEdit;
            public RelativeLayout relativeLayout1;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvPersonInfo = (TextView) rootView.findViewById(R.id.tv_person_info);
                this.tvPersonAddress = (TextView) rootView.findViewById(R.id.tv_person_address);
                this.hasChoice = (ImageView) rootView.findViewById(R.id.has_choice);
                this.tvDelete = (TextView) rootView.findViewById(R.id.tv_delete);
                this.ivDelete = (ImageView) rootView.findViewById(R.id.iv_delete);
                this.ivEdit = (ImageView) rootView.findViewById(R.id.iv_edit);
                this.tvEdit = (TextView) rootView.findViewById(R.id.tv_edit);
                this.relativeLayout1 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
            }
        }
    }
}
