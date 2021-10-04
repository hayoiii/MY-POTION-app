package com.palebluedot.mypotion.ui.storage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.Like;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.databinding.FragmentStorageBinding;
import com.palebluedot.mypotion.feature.detail.DetailFragment;

import java.util.ArrayList;
import java.util.List;

public class StorageFragment extends Fragment {
    static public String TAG = "StorageFragment";

    private StorageViewModel model;
    private FragmentStorageBinding binding;
    LikeRecyclerAdapter likeAdapter;
    HistoryRecyclerAdapter historyAdapter;

    private final TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getText().equals(getString(R.string.tab_like))) {
                binding.likeRecyclerContainer.setVisibility(View.VISIBLE);
                binding.historyRecyclerContainer.setVisibility(View.GONE);
            }
            else {
                binding.likeRecyclerContainer.setVisibility(View.GONE);
                binding.historyRecyclerContainer.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model =
                new ViewModelProvider(getActivity()).get(StorageViewModel.class);

        binding = FragmentStorageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        likeAdapter = new LikeRecyclerAdapter();
        historyAdapter = new HistoryRecyclerAdapter();
        likeAdapter.setOnItemClickListener((v, like) -> {
            // detail fragment
            // TODO: touch x
            DetailFragment detailFragment = DetailFragment.newInstance(like.serialNo, like.name, like.factory);
            detailFragment.setParentTag(TAG);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.child_fragment, detailFragment)
                    .addToBackStack("like_detail")
                    .commit();
        });
        historyAdapter.setOnItemClickListener((v, potion) -> {
            //TODO: potion card
            HistoryFragment historyFragment = HistoryFragment.newInstance(potion);
            historyFragment.setOnClickDetailListener(new HistoryFragment.onClickDetailListener() {
                @Override
                public void onClick(View v, MyPotion potion) {
                    if(potion.serialNo == null) return;

                    DetailFragment detailFragment = DetailFragment.newInstance(potion.serialNo, potion.name, potion.factory);
                    detailFragment.setParentTag(TAG);
                    getChildFragmentManager().beginTransaction()
                            .remove(historyFragment)
                            .add(R.id.child_fragment, detailFragment)
                            .addToBackStack("history_detail")
                            .commit();
                }
            });

            getChildFragmentManager().beginTransaction()
                    .add(R.id.child_fragment, historyFragment)
                    .addToBackStack("history_card")
                    .commit();
        });

        binding.storageLikeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.storageLikeRecycler.setAdapter(likeAdapter);
        binding.storageHistoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.storageHistoryRecycler.setAdapter(historyAdapter);

        model.mLikeList.observe(getViewLifecycleOwner(), new Observer<List<Like>>() {
            @Override
            public void onChanged(List<Like> likes) {
                likeAdapter.setData(new ArrayList<>(likes));
            }
        });

        model.mPotionList.observe(getViewLifecycleOwner(), new Observer<List<MyPotion>>() {
            @Override
            public void onChanged(List<MyPotion> myPotions) {
                historyAdapter.setData(new ArrayList<>(myPotions));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}