package net.youlvke.yanyuke.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.GroupAdapter;
import net.youlvke.yanyuke.adapter.MyDecoration;
import net.youlvke.yanyuke.bean.GroupBean;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ScreenUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * 账单详情
 */
public class BillDetailActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private GroupAdapter groupAdapter;
    private LinearLayoutManager linearLayoutManager;

    private TextView tvTitle;
    private TextView tvTilte2;
    private List<GroupBean.DataBean> datas;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_bill_detail;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTilte2 = (TextView) findViewById(R.id.tv_title2);
        tvTilte2.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        groupAdapter = new GroupAdapter(this);
        //mRecyclerView.setAdapter(groupAdapter);
        //添加分割线
        mRecyclerView.addItemDecoration(new MyDecoration(this, LinearLayoutManager.VERTICAL,
                ScreenUtils.dp2px(10), getResources().getColor(R.color.text_address)));

    }

    @Override
    protected void initListener() {
        tvTilte2.setOnClickListener(this);

        /*groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onClick(GroupAdapter.SelectData bundle) {
                ToastUtils.showToast(BillDetailActivity.this,"11111");
            }
        });*/
    }

    @Override
    protected void initData() {
        tvTilte2.setText("查看往期");
        tvTitle.setText("账单详情");

        loadData();
//添加滚动监听
        mRecyclerView.addOnScrollListener(new MyOnScrollListener());
    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createParams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("onSuccess", "onSuccess: " + response.get());
                    try {
                        Gson gson = new Gson();
                        GroupBean groupBean = gson.fromJson(response.get(), GroupBean.class);
                        if (groupBean.getCode() == 1 && groupBean.getData() != null) {
                            datas = groupBean.getData();
                            mRecyclerView.setAdapter(groupAdapter);
                            groupAdapter.addData(datas);
                            mRecyclerView.smoothScrollToPosition(linearLayoutManager.getItemCount() - 1);
                        }
                    } catch (NullPointerException e) {
                        ToastUtils.showToast(BillDetailActivity.this, "数据解析异常！");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160130");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }


    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.tv_title2://跳转到查看往期界面
                startActivity(new Intent(BillDetailActivity.this, CheckOldActivity.class));
                break;
        }
    }


    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int first = linearLayoutManager.findFirstVisibleItemPosition();
                int count = linearLayoutManager.getChildCount();
                groupAdapter.changeStatus(first + count - 1);
            }
        }
    }
}
