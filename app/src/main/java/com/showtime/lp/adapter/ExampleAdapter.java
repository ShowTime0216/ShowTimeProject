package com.showtime.lp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.showtime.lp.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class ExampleAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;

    private LayoutInflater layoutInflater;

    public ExampleAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_dialog_example, null);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(list.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView text;
    }
}
