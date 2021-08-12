package com.palebluedot.mypotion.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.databinding.FragmentHomeBinding;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel model;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model =
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setModel(model);
        RecyclerView recyclerView = binding.homeRecycler;
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter();

        model.mList.observe((LifecycleOwner) getActivity(), new Observer<List<MyPotion>>() {
            @Override
            public void onChanged(List<MyPotion> myPotions) {
                adapter.setData(new ArrayList<>(myPotions));
            }
        });

        model.mPotion.observe(getActivity(), new Observer<MyPotion>() {
            @Override
            public void onChanged(MyPotion selectedPotion) {
                binding.setData(selectedPotion);
            }
        });

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}