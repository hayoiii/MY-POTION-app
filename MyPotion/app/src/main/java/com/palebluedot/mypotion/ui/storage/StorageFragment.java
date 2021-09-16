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
import com.palebluedot.mypotion.databinding.FragmentStorageBinding;

import java.util.ArrayList;
import java.util.List;

public class StorageFragment extends Fragment {

    private StorageViewModel model;
    private FragmentStorageBinding binding;
    LikeRecyclerAdapter likeAdapter;

    private final TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getText().equals(getString(R.string.tab_like))) {
                binding.storageLikeRecycler.setVisibility(View.VISIBLE);
                binding.storageHistoryRecycler.setVisibility(View.GONE);
            }
            else {
                binding.storageLikeRecycler.setVisibility(View.GONE);
                binding.storageHistoryRecycler.setVisibility(View.VISIBLE);
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

        likeAdapter.setOnItemClickListener(new LikeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // TODO: detail fragment
            }
        });
        binding.storageLikeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.storageLikeRecycler.setAdapter(likeAdapter);
        //TODO: loading time
        likeAdapter.setData(new ArrayList<>(model.mLikeList));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}