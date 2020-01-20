package com.ajnshs.pehliyan;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
    private String[] listTitle;
    private Context context;

    public MainAdapter(Context context, String[] listTitle) {
        this.context = context;
        this.listTitle = listTitle;
    }

    @Override
    public int getCount() {
        return listTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return listTitle;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.singlelist_row_catg, parent, false);
        Typeface hindiFont = Typeface.createFromAsset(context.getAssets(), "NotoSans-Regular.ttf");
        TextView title = row.findViewById(R.id.paheliTitle);
        title.setTypeface(hindiFont);
        title.setText(listTitle[position]);
        return row;
    }
}
