package com.ljstudio.pangpang.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ljstudio.pangpang.dialog.R;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;


public class CommonListAdapter<T> extends BaseAdapter {

    Context cxt;
    List<T> items;
    Set<Long> splitItems;
    int itemLayoutResId;
    int groupLayoutResId;
    WeakReference<ViewFormatter> formatter;

    public CommonListAdapter(Context cxt, List<T> items, Set<Long> splitItems, int itemLayoutResId, int groupLayoutResId, ViewFormatter<T> formater) {
        this.cxt = cxt;
        this.items = items;
        this.splitItems = splitItems;
        this.formatter = new WeakReference<ViewFormatter>(formater);
        this.itemLayoutResId = itemLayoutResId;
        this.groupLayoutResId = groupLayoutResId;
    }

    public void changeItems(List<T> items, Set<Long> splitItems) {
        this.items = items;
        this.splitItems = splitItems;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean group = splitItems != null && splitItems.contains((long) position);
        if (convertView == null || !Boolean.valueOf(group).equals(convertView.getTag(R.layout.dialog_list_group_header))) {
            if (group) {
                convertView = LayoutInflater.from(cxt).inflate(groupLayoutResId, parent, false);
                convertView.setTag(R.layout.dialog_list_group_header, true);
            } else {
                convertView = LayoutInflater.from(cxt).inflate(itemLayoutResId, parent, false);
                convertView.setTag(R.layout.dialog_list_group_header, false);
            }
        }
        formatter.get().formatItemView(this, items.get(position), convertView, position, group);
        return convertView;
    }

    public static interface ViewFormatter<T> {
        void formatItemView(CommonListAdapter<T> adapter, T item, View view, int index, boolean group);
    }
}
