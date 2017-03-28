package net.youlvke.yanyuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.activity.MyTabLayoutActivity;
import net.youlvke.yanyuke.bean.GroupBean;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 账单的适配器
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> implements View.OnClickListener {
    private Context mContext;
    private List<GroupBean.DataBean> mResData;
    private OnItemClickListener mOnItemClickListener;
    private static SelectData mLastData;
    private int height;
    private ImageView ivCancel;//取消按钮
    private List<GroupBean.DataBean.CotimeBean> cotime;
    private int position;

    public GroupAdapter(Context mContext) {
        this.mContext = mContext;
        mResData = new ArrayList<GroupBean.DataBean>();
    }


    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.activity_bill_list_item_one, null);
        return new GroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GroupHolder holder, int position) {
        this.position= position;
        GroupBean.DataBean dataBean = mResData.get(position);
        //设置时间
        holder.tevDate.setText(DateUtils.formatDate(dataBean.getAddTime()));
        holder.tvBillIn.setText(String.valueOf(dataBean.getTotal()));
        cotime = dataBean.getCotime();
        MyAdapter adapter = new MyAdapter();
        holder.lvBill.setAdapter(adapter);
        SelectData data = new SelectData(dataBean, position);
        holder.tevDate.setOnClickListener(this);
        holder.frmDetails.setOnClickListener(this);
        holder.tevDate.setTag(data);
//        holder.rlBillDetail.setOnClickListener(this);

        if (dataBean.isItemStatus()) {
            mLastData = data;
            holder.frmDetails.setVisibility(View.VISIBLE);
            height = holder.itemView.getMeasuredHeight();
        } else {
            holder.frmDetails.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mResData.size();
    }

    public void addData(List<GroupBean.DataBean> list) {
        if (list != null && list.size() > 0) {
            mResData.addAll(list);
        }
    }

    public void changeStatus(int position) {
        if ( position >= mResData.size()) {
            return;
        }
        if (mLastData != null) {
            if (mLastData.position == position && mLastData.bean.isItemStatus()) return;
        }
        if (mResData.size()<=0){
            ToastUtils.showToast(mContext,"服务器没有数据");
            return;
        }
        changeItem(new SelectData(mResData.get(position), position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                if (v instanceof TextView) {
                    SelectData data = (SelectData) v.getTag();
                    if (data != null) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onClick(data);
                        }
                        changeItem(data);
                    }
                }
                break;
          case R.id.ll_bill:
              Intent intent = new Intent() ;
              intent.setClass(mContext, MyTabLayoutActivity.class);
              intent.putExtra("income",String.valueOf(mResData.get(position).getTotal()));
              intent.putExtra("date",DateUtils.getDate(mResData.get(position).getAddTime()));
              mContext.startActivity(intent);
                break;
        }
    }

    /**
     * 展示修改账单详情页面
     */

    /*private void showBillDetailDialog() {


        final AlertDialog dialog = new AlertDialog.Builder(mContext, R.style.theme_dialog).create();

        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_reword_detail);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ivCancel = (ImageView) window.findViewById(R.id.iv_cancel);

        *//** 取消  *//*
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);

    }*/

    private void changeItem(SelectData data) {
        if (mLastData != null && data.position == mLastData.position) {
            mLastData.bean.setItemStatus(!mLastData.bean.isItemStatus());
            notifyItemChanged(mLastData.position);
            return;
        }
        if (mLastData != null) {
            mLastData.bean.setItemStatus(false);
            notifyItemChanged(mLastData.position);
        }
        GroupBean.DataBean bean = data.bean;
        bean.setItemStatus(true);
        //刷新数据
        notifyItemChanged(data.position);
    }

    public interface OnItemClickListener {
        void onClick(SelectData bundle);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class GroupHolder extends RecyclerView.ViewHolder {
        private TextView tevDate;
        private LinearLayout frmDetails;
        private RelativeLayout rlBillDetail;
        private TextView tvBillIn;
        private ListView lvBill;


        public GroupHolder(View itemView) {
            super(itemView);
            tevDate = (TextView) itemView.findViewById(R.id.tv_date);
            frmDetails = (LinearLayout) itemView.findViewById(R.id.ll_bill);
            tvBillIn = (TextView) itemView.findViewById(R.id.tv_bill_in);
            lvBill = (ListView) itemView.findViewById(R.id.lv_bill);
            //rlBillDetail = (RelativeLayout) itemView.findViewById(R.id.rl_bill_detail);

        }
    }

    public class SelectData {
        public SelectData(GroupBean.DataBean bean, int position) {
            this.bean = bean;
            this.position = position;
        }

        GroupBean.DataBean bean;
        int position;
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return cotime.size();
        }

        @Override
        public Object getItem(int position) {
            return cotime.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_bill, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            GroupBean.DataBean.CotimeBean cotimeBean = cotime.get(position);
            holder.tvBillTime.setText(DateUtils.HourAndMinute(cotimeBean.getAddTime()));
            //
            String s = cotimeBean.getCommissiontype() != 1 ? "\t\t消费奖励" : "\t\t红钻奖励";
            holder.tvBillConsumption.setText(Html.fromHtml(initColor(cotimeBean.getNickname(), s)));
            holder.tvBillSum.setText(String.format("+ %s", String.valueOf(cotimeBean.getMoney())));
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tvBillTime;
            public TextView tvBillConsumption;
            public TextView tvBillSum;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvBillTime = (TextView) rootView.findViewById(R.id.tv_bill_time);
                this.tvBillConsumption = (TextView) rootView.findViewById(R.id.tv_bill_consumption);
                this.tvBillSum = (TextView) rootView.findViewById(R.id.tv_bill_sum);

            }

        }
    }

    private String initColor(String text1, String text2) {
        String str = "<font color='#333333'>" + text1 + "</font>"
                + "<font color= '#666666'>" + text2 + "</font>";
        return str;
    }
}
