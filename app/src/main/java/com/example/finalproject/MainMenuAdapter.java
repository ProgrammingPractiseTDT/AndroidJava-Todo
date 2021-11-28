package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MainMenuAdapter extends BaseAdapter {
    private List<String> menu;
    private LayoutInflater layoutInflater;
    private Context context;

    public MainMenuAdapter(Context context, List<String> menu) {
        this.context = context;
        this.menu = menu;
        this.layoutInflater = layoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int i) {
        return menu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.main_menu_item_layout, null);
            holder = new ViewHolder();
            holder.menuItemName = (TextView) view.findViewById(R.id.txt_menu_item);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        String menu_item = this.menu.get(i);
        holder.menuItemName.setText(menu_item);
        return view;
    }

    static class ViewHolder{
        TextView menuItemName;
    }
}
