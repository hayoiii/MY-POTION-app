package com.palebluedot.mypotion.feature.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.PotionItem;

import java.util.List;


public class SearchListAdapter extends BaseAdapter {
    private List<PotionItem> mData;

    public List<PotionItem> getData() {
        return mData;
    }

    public SearchListAdapter(List<PotionItem> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position){
        return mData.get(position);

    }

    @Override
    public long getItemId(int position){
        return position;
    }

    void setData(List<PotionItem> potionItems){
        mData = potionItems;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_search, parent, false);
            TextView productText = convertView.findViewById(R.id.item_product);
            TextView factoryText = convertView.findViewById(R.id.item_factory);

            holder.productText = productText;
            holder.factoryText = factoryText;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        PotionItem potion = mData.get(position);
        holder.productText.setText(potion.getProduct());
        holder.factoryText.setText(potion.getFactory());
        return convertView;
    }

    static class ViewHolder{
        TextView productText;
        TextView factoryText;
    }
}
