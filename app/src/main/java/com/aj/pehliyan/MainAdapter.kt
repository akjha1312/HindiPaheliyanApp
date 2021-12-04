package com.aj.pehliyan

import android.content.Context
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.graphics.Typeface
import android.view.View
import android.widget.TextView

class MainAdapter(private val context: Context, private val listTitle: Array<String>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return listTitle.size
    }

    override fun getItem(position: Int): Any {
        return listTitle
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val row = inflater.inflate(R.layout.singlelist_row_catg, null)
            val hindiFont = Typeface.createFromAsset(context.assets, "NotoSans-Regular.ttf")
            val title = row.findViewById<TextView>(R.id.paheliTitle)
            title.typeface = hindiFont
            title.text = listTitle[position]
            return row

    }
}