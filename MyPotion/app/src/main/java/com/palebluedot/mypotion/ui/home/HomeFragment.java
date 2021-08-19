package com.palebluedot.mypotion.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.databinding.FragmentHomeBinding;
import com.wajahatkarim3.easyflipview.EasyFlipView;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel model;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setModel(model);
        binding.setLifecycleOwner(getActivity());

        RecyclerView recyclerView = binding.homeRecycler;
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(model);

        //TODO: select effect
        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int position) {
                model.onItemClick(position);
                // HomeViewModel would be update selected potion
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        View root = binding.getRoot();
        View emptyCard = binding.emptyCard.getRoot();
        EasyFlipView potionCard = binding.potionCard.homeFlipView;

        //TODO: flip card, image button - touch prob
        binding.potionCard.potionFlipFront.frontBtn.setOnClickListener(view -> model.intake());
        model.mPotionList.observe(getViewLifecycleOwner(), new Observer<List<MyPotion>>() {
            @Override
            public void onChanged(List<MyPotion> myPotions) {
                adapter.setData(new ArrayList<>(myPotions));
            }
        });

        model.mPotion.observe(getViewLifecycleOwner(), new Observer<MyPotion>() {
            @Override
            public void onChanged(MyPotion selectedPotion) {
                if(selectedPotion != null) {
                    binding.setData(selectedPotion);
                    binding.setModel(model);
                    //TODO: replace with binding
                    emptyCard.setVisibility(View.GONE);
                    potionCard.setVisibility(View.VISIBLE);
                }
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