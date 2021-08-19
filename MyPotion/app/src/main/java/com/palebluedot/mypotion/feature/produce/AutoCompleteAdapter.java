package com.palebluedot.mypotion.feature.produce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    //데이터를 넣을 리스트
    List<String> allItemList;
    List<String> filteredItemList;

    public AutoCompleteAdapter() {
            super();
            allItemList = new ArrayList<>(Arrays.asList(Constant.TAGS));
            filter = getFilter();
            filteredItemList = allItemList;
    }

    private Filter filter;
    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ListFilter() ;
        }
        return filter;
    }


    @Override
    public int getCount() {
        return filteredItemList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_auto_complete, parent, false);
        }

        TextView itemView = convertView.findViewById(R.id.auto_complete_item);

        //getItem(position) 코드로 자동완성 될 아이템을 가져온다
        String tagItem = (String) filteredItemList.get(position);

        if (tagItem != null) {
            itemView.setText(tagItem);
        }       // 레이아웃 파라미터 생성
       return convertView;
    }

    //-------------------------- 자동완성 코드 --------------------------
    class ListFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
//            filteredItemList.clear();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = allItemList;
                results.count = allItemList.size() ;
            } else {
                ArrayList<String> filteredList = new ArrayList<String>();

                String filterPattern = constraint.toString().trim();
                for(String item : allItemList){
                    if (item.contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                if(filteredList.size() == 0 || !filteredList.contains(constraint.toString())){
                    filteredList.add("#'"+(String)constraint + "'" +" 태그 만들기");
                }

                results.values = filteredList;
                results.count = filteredList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // update listview by filtered data list.
            filteredItemList = (ArrayList<String>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }
}
