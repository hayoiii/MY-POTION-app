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
import com.palebluedot.mypotion.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class LikeRecyclerAdapter extends RecyclerView.Adapter<LikeRecyclerAdapter.ViewHolder> {
    protected ArrayList<Like> mData = new ArrayList<>();
    private Context context;
    ViewGroup.LayoutParams params;

    public void setData(ArrayList<Like> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LikeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_like, parent, false);

        LikeRecyclerAdapter.ViewHolder vh = new LikeRecyclerAdapter.ViewHolder(view);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,(int) MyUtil.dpToPx(44, context));

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeRecyclerAdapter.ViewHolder holder, int position) {
        Like like = mData.get(position);
        holder.nameText.setText(like.name);
        holder.factoryText.setText(like.factory);
        holder.chipGroup.removeAllViews();

        List<String> tags = like.effectTags;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameText, factoryText;
        final ChipGroup chipGroup;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameText = view.findViewById(R.id.item_name);
            factoryText = view.findViewById(R.id.item_factory);
            chipGroup = view.findViewById(R.id.item_chipGroup);

            view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    // 리스너 객체의 메서드 호출.
                    if (mListener != null) {
                        mListener.onItemClick(v, mData.get(pos));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View v, Like like);
    }
    private LikeRecyclerAdapter.OnItemClickListener mListener = null ;
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(LikeRecyclerAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }
}
