package net.youlvke.yanyuke.adapter;


import net.youlvke.yanyuke.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {
	
	/** The default items length */
	public static final int DEFAULT_LENGTH = 4;
	
	// items
	private ArrayList<T> items;
	// length
	private int length;

	/**
	 * Constructor
	 * @param list the items
	 * @param length the max items length
	 */
	public ArrayWheelAdapter(List<ProvinceBean> list, int length) {
		this.items = (ArrayList<T>) list;
		this.length = length;
	}
	
	/**
	 * Contructor
	 * @param list the items
	 */
	public ArrayWheelAdapter(List<ProvinceBean> list) {
		this(list, DEFAULT_LENGTH);
	}

	@Override
	public Object getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index);
		}
		return "";
	}

	@Override
	public int getItemsCount() {
		return items.size();
	}

	@Override
	public int indexOf(Object o){
		return items.indexOf(o);
	}

}
