package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.SearchRecordsAdapter;
import net.youlvke.yanyuke.bean.SearchBean;
import net.youlvke.yanyuke.dao.SearchRecordDao;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.lv_hot_search;


public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    private TextView tvTitle;
    private RelativeLayout llSearch;
    private ListView lvHomeHistory;
    private EditText etHomeSearch;
    private ImageView ivHomeSearch;
    private SearchRecordDao recordsDao;
    private ArrayList<String> searchRecordsList;
    private ArrayList<String> tempList;
    private SearchRecordsAdapter recordsAdapter;
    private TextView tvClearRecord;
    private ListView lvHotSearch;
    private List<String> str = null;
    private int pagenum = 1;
    private List<SearchBean.DataBean> datas;
    private Intent intent;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etHomeSearch = (EditText) findViewById(R.id.et_home_search);
        llSearch = (RelativeLayout) findViewById(R.id.ll_search);
        ivHomeSearch = (ImageView) findViewById(R.id.iv_home_search);
        lvHomeHistory = (ListView) findViewById(R.id.lv_home_history);
        tvClearRecord = (TextView) findViewById(R.id.tv_clear_record);
        lvHotSearch = (ListView) findViewById(lv_hot_search);
        etHomeSearch.requestFocus();
        etHomeSearch.setFocusable(true);
    }

    @Override
    protected void initListener() {

        ivHomeSearch.setOnClickListener(this);
        etHomeSearch.setOnEditorActionListener(new MyOnEditorActionListener());
        etHomeSearch.addTextChangedListener(new MyTextWatcher());
        etHomeSearch.setOnFocusChangeListener(new MyOnFocusChangeListener());
        tvClearRecord.setOnClickListener(this);
        lvHotSearch.setOnItemClickListener(this);
        lvHomeHistory.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setVisibility(View.GONE);
        llSearch.setVisibility(View.VISIBLE);
        recordsDao = SearchRecordDao.getInstance(this);
        searchRecordsList = new ArrayList<>();
        tempList = new ArrayList<>();
        tempList.addAll(recordsDao.getRecordsList());
        reversedList();
        checkRecordsSize();
        bindAdapter();
        str = new ArrayList<>();
        str.add("红袍茶叶");
        str.add("紫砂壶");
        str.add("周大福");
        lvHotSearch.setAdapter(new ArrayAdapter<>(this, R.layout.item_search_text, R.id.tv_history, str));
    }

    private void loadData(String text) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKGOODS, createParams(text))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("SearchActivity", "onSuccess: " + response.get());
                            try {
                                Gson gson = new Gson();
                                SearchBean searchBean = gson.fromJson(response.get(), SearchBean.class);
                                if (searchBean.getCode() == 1 && searchBean.getData() != null) {
                                    datas = searchBean.getData();
                                    SearchResult();
                                } else {
                                    ToastUtils.showToast(SearchActivity.this, "抱歉未搜到此商品！");
                                }
                            } catch (JsonParseException e) {
                                ToastUtils.showToast(SearchActivity.this, "数据解析失败");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createParams(String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160210");
        params.put("goodsCity", "杭州市");
        params.put("pageNum", String.valueOf(pagenum));
        params.put("message", text);
        return params;
    }

    private void bindAdapter() {
        recordsAdapter = new SearchRecordsAdapter(this, searchRecordsList);
        lvHomeHistory.setAdapter(recordsAdapter);
    }


    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.tv_clear_record:
                tempList.clear();
                reversedList();
                recordsDao.deleteAllRecords();
                recordsAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_home_search:
                loadData(etHomeSearch.getText().toString().trim());
                break;
        }
    }

    /**
     * 当没有匹配的搜索数据的时候不显示历史记录栏
     */
    private void checkRecordsSize() {
        if (searchRecordsList.size() == 0) {
            lvHomeHistory.setVisibility(View.GONE);
        } else {
            lvHomeHistory.setVisibility(View.VISIBLE);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String result = "";
        switch (adapterView.getId()) {

            case R.id.lv_home_history:
                if (searchRecordsList.size() > 0) {
                    result = searchRecordsList.get(position);
                }
                break;
            case R.id.lv_hot_search:
                result = str.get(position);
                break;
        }
        loadData(result);
        //SearchResult(result);
    }

    private void SearchResult() {
        intent = new Intent();
        intent.setClass(this, SearchResultActivity.class);
        intent.putExtra("search", (Serializable) datas);
        startActivity(intent);
        finish();
    }


    //根据输入的信息去模糊搜索
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String tempName = etHomeSearch.getText().toString();
            tempList.clear();
            tempList.addAll(recordsDao.querySimlarRecord(tempName));
            reversedList();
            checkRecordsSize();
            recordsAdapter.notifyDataSetChanged();
        }
    }

    private class MyOnEditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (etHomeSearch.getText().toString().length() > 0) {
                    String record = etHomeSearch.getText().toString();

                    //判断数据库中是否存在该记录
                    if (!recordsDao.isHasRecord(record)) {
                        tempList.add(record);
                    }
                    //将搜索记录保存至数据库中
                    recordsDao.addRecords(record);
                    reversedList();
                    checkRecordsSize();
                    recordsAdapter.notifyDataSetChanged();
                    loadData(etHomeSearch.getText().toString().trim());
                    //根据关键词去搜索
                } else {
                    ToastUtils.showToast(SearchActivity.this, "搜索内容不能为空");
                }
            }
            return false;
        }
    }

    //颠倒list顺序，用户输入的信息会从上依次往下显示
    private void reversedList() {
        searchRecordsList.clear();
        for (int i = tempList.size() - 1; i >= 0; i--) {
            searchRecordsList.add(tempList.get(i));
        }
    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {

            }
        }
    }
}
