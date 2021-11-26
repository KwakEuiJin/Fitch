package org.app.gimalpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Middle_list extends BaseAdapter {
    Context context;
    ArrayList<Toeat> toeatitem;

    TextView name;
    TextView nut;

    public Middle_list(Context context, ArrayList<Toeat> toeatitem){
        this.context = context;
        this.toeatitem = toeatitem;
    }

    @Override
    public int getCount() {
        return this.toeatitem.size();
    }

    @Override
    public Object getItem(int position) {
        return toeatitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.add_supplement,null);
            name = (TextView) convertView.findViewById(R.id.name);
            nut = (TextView) convertView.findViewById(R.id.nutrition);
            name.setText(toeatitem.get(position).getname());
            nut.setText(toeatitem.get(position).getnut());
        }
        return convertView;
    }
}
