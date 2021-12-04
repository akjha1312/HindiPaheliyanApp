package com.aj.pehliyan;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PaheliAdapter extends BaseAdapter {
    private ArrayList<Pahli> listTitle;
    private Context context;

    public PaheliAdapter(Context context, ArrayList<Pahli> listTitle) {
        this.context = context;
        this.listTitle = listTitle;
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return listTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.singlelist_row_paheli, parent, false);
        Typeface hindiFont = Typeface.createFromAsset(context.getAssets(), "NotoSans-Regular.ttf");
        TextView srNum = row.findViewById(R.id.paheliNum);
        srNum.setText(String.valueOf(listTitle.get(position).getQuesNum()));
        TextView title = row.findViewById(R.id.paheliTitle);
        title.setTypeface(hindiFont);
        title.setText(listTitle.get(position).getQues());
        return row;
    }
}
