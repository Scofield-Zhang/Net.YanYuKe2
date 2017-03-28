package net.youlvke.yanyuke.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class DefaultBaseAdapter<T> extends BaseAdapter{
	
	public List<T> data;

	public DefaultBaseAdapter(List<T> data) {
		super();
		this.data=data;
	}




	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
}
