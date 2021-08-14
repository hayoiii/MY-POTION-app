package com.palebluedot.mypotion.ui.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.MyPotion;

import java.util.ArrayList;
import java.util.Objects;

//TODO: onClickItem -> viewmodel의 pos값 바꾸기, 클릭된 아이템 색깔 바꾸기
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private ColorStateList secondaryLight;
    private ColorStateList secondary;
    private ColorStateList contrastDark;
    private ColorStateList contrastLight;

    protected ArrayList<MyPotion> mData = null;
    HomeViewModel model;

    public HomeRecyclerAdapter(HomeViewModel model) {
        mData = new ArrayList<>();
        this.model = model;
    }

    public void setData(ArrayList<MyPotion> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_home, parent, false) ;
        HomeRecyclerAdapter.ViewHolder vh = new HomeRecyclerAdapter.ViewHolder(view);

        secondaryLight= ColorStateList.valueOf(context.getResources().getColor(R.color.secondary_light, context.getTheme()));
        contrastDark = ColorStateList.valueOf(context.getResources().getColor(R.color.contrastDark, context.getTheme()));
        secondary = ColorStateList.valueOf(context.getResources().getColor(R.color.secondary, context.getTheme()));
        contrastLight = ColorStateList.valueOf(context.getResources().getColor(R.color.contrastLight, context.getTheme()));

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.ViewHolder holder, int position) {
        MyPotion potion = Objects.requireNonNull(mData).get(position);
        holder.aliasText.setText(potion.alias);
        holder.factoryText.setText(potion.factory);
        //TODO: dday
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView aliasText, factoryText, ddayText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aliasText = itemView.findViewById(R.id.home_item_alias);
            factoryText = itemView.findViewById(R.id.home_item_factory);
            ddayText = itemView.findViewById(R.id.home_item_label);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                model.onItemClickListener(pos);
                // HomeViewModel would be update selected potion
            });
        }
    }
}
