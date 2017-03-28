package net.youlvke.yanyuke.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.youlvke.yanyuke.R;

public class MyMessageActivity extends BaseActivity {


    private TextView tvTitle;
    private ListView lvMsg;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvMsg = (ListView) findViewById(R.id.lv_msg);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("消息");
        MyAdapter adapter = new MyAdapter();
        lvMsg.setAdapter(adapter);
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
    }

    class MyAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           if (convertView ==null){
               convertView = getLayoutInflater().inflate(R.layout.item_me_msg,parent,false);
            }
            return convertView;
        }
    }
}
