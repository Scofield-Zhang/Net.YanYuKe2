package net.youlvke.yanyuke.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.ShopInfoBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CustomImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


public class ShopCollectActivity extends BaseActivity {

    private TextView tvTitle;
    private ListView lvShopCollect;
    private List<ShopInfoBean.DataBean> data = new ArrayList<>();
    private MyAdapter adapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_shop_collect;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvShopCollect = (ListView) findViewById(R.id.lv_shop_collect);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("收藏商品");
        loadData();
        adapter = new MyAdapter();

    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER,createCollect())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("response", "onSuccess: "+response.get());
                            try {
                                Gson gson = new Gson();
                                ShopInfoBean shopInfoBean = gson.fromJson(response.get(), ShopInfoBean.class);
                                int code = shopInfoBean.getCode();
                                if (code == 1 && shopInfoBean.getData() != null){
                                    data = shopInfoBean.getData();
                                    lvShopCollect.setAdapter(adapter);
                                }else if (code == 2){
                                    ToastUtils.showToast(ShopCollectActivity.this,"获取失败");
                                }
                            }catch (NullPointerException e){
                                ToastUtils.showToast(ShopCollectActivity.this,"您还没有收藏任何商品");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Map createCollect() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160122");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

       @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder ;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_shop_collect, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ShopInfoBean.DataBean dataBean = data.get(position);
            holder.tvShopDetail.setText(dataBean.getGoodsInfo().getGoodsDescription());
            Glide
                    .with(ShopCollectActivity.this)
                    .load(dataBean.getGoodsPictureInfo().get(0).getPictureUrl())
                    .into(holder.civCollectShop);
           holder.btnCaceCollect.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   /*userId  goodsId  sessionToken*/
                   cancelCollect(position);
               }
           });
            return convertView;
        }

        private void cancelCollect(final int position) {
            try {
                IRequest.post(ShopCollectActivity.this,Constants.BASEURL_YYKUSER,createCancelParams(position)).execute(new RequestListener() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get());
                            int code = jsonObject.getInt("code");
                            if (code == 1){
                                data.remove(position);
                                notifyDataSetChanged();
                                loadData();
                            }else {
                                ToastUtils.showToast(ShopCollectActivity.this,"服务器开小差了0.0");
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

        private Map createCancelParams(int position) {
            HashMap<String, String> params = new HashMap<>();
            params.put("action", "20160123");
            params.put("sessionToken", session);
            params.put("userId", String.valueOf(userId));
            params.put("goodsId", String.valueOf(data.get(position).getGoodsId()));
            return params;
        }
    }


    class ViewHolder {
        public  TextView btnCaceCollect;
        public View rootView;
        public CustomImageView civCollectShop;
        public TextView tvShopDetail;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.civCollectShop = (CustomImageView) rootView.findViewById(R.id.civ_collect_shop);
            this.tvShopDetail = (TextView) rootView.findViewById(R.id.tv_shop_detail);
            this.btnCaceCollect = (TextView) rootView.findViewById(R.id.btn_cacel_collect);
        }
    }
}
