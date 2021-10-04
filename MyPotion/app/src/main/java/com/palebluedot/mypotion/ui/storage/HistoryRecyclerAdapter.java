package com.palebluedot.mypotion.ui.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.Like;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>{
    protected ArrayList<MyPotion> mData = new ArrayList<>();
    private Context context;
    ViewGroup.LayoutParams params;

    public void setData(ArrayList<MyPotion> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_history, parent, false);

        HistoryRecyclerAdapter.ViewHolder vh = new HistoryRecyclerAdapter.ViewHolder(view);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,(int) MyUtil.dpToPx(44, context));

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.ViewHolder holder, int position) {
        MyPotion potion = mData.get(position);
        holder.aliasText.setText(potion.alias);
        holder.beginText.setText(potion.beginDate);
        holder.finishText.setText(potion.finishDate);

        holder.chipGroup.removeAllViews();
        List<String> tags = potion.effectTags;

        for(String t:tags){
            Chip chip = new Chip(context);
            chip.setText(t);
            chip.setChipBackgroundColor(context.getColorStateList(R.color.secondary_light));
            chip.setCheckedIconVisible(false);
            chip.setCloseIconVisible(false);
            chip.setClickable(false);
            chip.setCheckable(false);
            holder.chipGroup.addView(chip, params);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView aliasText, beginText, finishText;
        final ChipGroup chipGroup;
        final ImageButton menuBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            aliasText = view.findViewById(R.id.item_alias);
            beginText = view.findViewById(R.id.item_begin);
            finishText = view.findViewById(R.id.item_finish);
            chipGroup = view.findViewById(R.id.item_chipGroup);
            menuBtn = view.findViewById(R.id.item_menu_btn);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int positon = getAdapterPosition();
                        mListener.onItemClick(v, mData.get(positon));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, MyPotion potion);
    }
    private HistoryRecyclerAdapter.OnItemClickListener mListener = null ;
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(HistoryRecyclerAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }
}
