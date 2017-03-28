package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.SearchBean;
import net.youlvke.yanyuke.utils.GlideRoundTransform;

import java.util.List;

import static net.youlvke.yanyuke.R.id.et_home_search;
import static net.youlvke.yanyuke.R.id.lv_search_result;

public class SearchResultActivity extends BaseActivity {

    private TextView tvTitle;
    private RelativeLayout llSearch;
    private EditText etHomeSearch;
    private ListView lvSearchResult;
    private List<SearchBean.DataBean> searchBean;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initView() {
        searchBean = (List<SearchBean.DataBean>) getIntent().getSerializableExtra("search");
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llSearch = (RelativeLayout) findViewById(R.id.ll_search);
        etHomeSearch = (EditText) findViewById(et_home_search);
        lvSearchResult = (ListView) findViewById(lv_search_result);
    }

    @Override
    protected void initListener() {
        etHomeSearch.setOnClickListener(this);
        lvSearchResult.setOnItemClickListener(new MyOnItemClickListener());
    }

    @Override
    protected void initData() {
        tvTitle.setVisibility(View.GONE);
        llSearch.setVisibility(View.VISIBLE);
        etHomeSearch.setText(getIntent().getStringExtra("search_result"));
        lvSearchResult.setAdapter(new MyAdapter());

    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.et_home_search:
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                break;
        }
    }

    private  class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent();
            intent.setClass(SearchResultActivity.this,ShopDetailActivity.class);
            Log.d("onItemClick", "onItemClick: "+searchBean.get(position).getGoodsId());
            intent.putExtra("goodsId",searchBean.get(position).getGoodsId());
            startActivity(intent);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchBean.size();
        }

        @Override
        public Object getItem(int position) {
            return searchBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_search_result, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SearchBean.DataBean dataBean = searchBean.get(position);
            Glide.with(SearchResultActivity.this)
                    .load(dataBean.getPictureUrl()+"?x-oss-process=style/Android_SEARCH")
                    .transform(new GlideRoundTransform(SearchResultActivity.this,2))
                    .into(holder.ivPic1);
            holder.tvSearchTitle.setText(dataBean.getGoodsName());
            int progress = (int) ((double) dataBean.getSalesCount() / (double) dataBean.getTotalCount() * 100);
            holder.progressBar.setProgress(progress);
            holder.tvSearchResidue.setText(String.format("剩余份数%s", String.valueOf(dataBean.getStockCount())));
            holder.tvSearchProgress.setText(String.format("参与进度%s", String.valueOf(progress)));
            return convertView;
        }
    }


    public class ViewHolder {
        public View rootView;
        public TextView tvSearchTitle;
        public ProgressBar progressBar;
        public TextView tvSearchResidue;
        public TextView tvSearchProgress;
        public ImageView ivPic1;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvSearchTitle = (TextView) rootView.findViewById(R.id.tv_search_title);
            this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            this.tvSearchResidue = (TextView) rootView.findViewById(R.id.tv_search_residue);
            this.tvSearchProgress = (TextView) rootView.findViewById(R.id.tv_search_progress);
            this.ivPic1 = (ImageView) rootView.findViewById(R.id.iv_pic_1);
        }

    }
}
