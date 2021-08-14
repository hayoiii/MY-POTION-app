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

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.databinding.FragmentHomeBinding;


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
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.homeRecycler;
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(model);

        View root = binding.getRoot();
        View emptyCard, potionCard;
        emptyCard = root.findViewById(R.id.empty_card);
        potionCard = root.findViewById(R.id.potion_card);

        model.mList.observe(getViewLifecycleOwner(), new Observer<List<MyPotion>>() {
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
                    emptyCard.setVisibility(View.GONE);
                    potionCard.setVisibility(View.VISIBLE);
                }
            }
        });

//        model.pos.observe(getActivity(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer pos) {
//                if (pos < 0) {
//                    emptyCard.setVisibility(View.VISIBLE);
//                    potionCard.setVisibility(View.GONE);
//                }
//                else {
//                    emptyCard.setVisibility(View.GONE);
//                    potionCard.setVisibility(View.VISIBLE);
//                }
//            }
//        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}