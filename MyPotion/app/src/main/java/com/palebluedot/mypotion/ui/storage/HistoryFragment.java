package com.palebluedot.mypotion.ui.storage;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.databinding.FragmentHistoryBinding;
import com.palebluedot.mypotion.util.MyUtil;
import com.palebluedot.mypotion.util.TagManager;

import java.util.Date;

public class HistoryFragment extends Fragment {

    private static final String ARG_POTION = "potion";

    private MyPotion potion;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public interface onClickDetailListener {
        void onClick(View v, MyPotion potion);
    }
    HistoryFragment.onClickDetailListener mListener = null;

    public void setOnClickDetailListener(onClickDetailListener listener) {
        this.mListener = listener;
    }

    public static HistoryFragment newInstance(MyPotion potion) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POTION, potion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            potion = getArguments().getParcelable(ARG_POTION);
        }
    }

    View.OnClickListener onCloseListener = view -> {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    };

    View.OnClickListener onDetailListener = view -> {
        if(mListener != null) {
            mListener.onClick(view, potion);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        binding.setData(potion);
        binding.setModel(this);
        View view = binding.getRoot();

        ImageButton frontCloseBtn = view.findViewById(R.id.history_flip_front).findViewById(R.id.close_btn);
        ImageButton backCloseBtn = view.findViewById(R.id.history_flip_back).findViewById(R.id.close_btn);

        frontCloseBtn.setOnClickListener(onCloseListener);
        backCloseBtn.setOnClickListener(onCloseListener);

        Button frontDetailBtn = view.findViewById(R.id.history_flip_front).findViewById(R.id.front_detail_btn);
        Button backDetailBtn = view.findViewById(R.id.history_flip_back).findViewById(R.id.back_detail_btn);

        frontDetailBtn.setOnClickListener(onDetailListener);
        backDetailBtn.setOnClickListener(onDetailListener);
        // Inflate the layout for this fragment
        return view;
    }

    public String getEffect() {
        return TagManager.getInstance().listToString(potion.effectTags);
    }
    public String getIngDays() {

        String beginStr = potion.beginDate;
        Date today = MyUtil.getFormattedToday();
        Date beginDate = MyUtil.stringToDate(beginStr);
        long msDiff = today.getTime() - beginDate.getTime();
        long dayDiff = Math.abs(msDiff) / (24 * 60 * 60 * 1000);
        return "총 "+ dayDiff +"일";
    }
}