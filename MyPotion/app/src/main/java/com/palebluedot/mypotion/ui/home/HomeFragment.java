package com.palebluedot.mypotion.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.Intake;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.databinding.FragmentHomeBinding;
import com.palebluedot.mypotion.feature.detail.DetailFragment;
import com.wajahatkarim3.easyflipview.EasyFlipView;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    static public String TAG = "HomeFragment";
    HomeViewModel model;
    private FragmentHomeBinding binding;
    private DetailFragment detailFragment;

    int selectedPosition = -1;

    //TODO: 다른 액티비티에서 넘어올 때 리스트 업데이트하기 (새 포션 추가 액티비티 등)
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
                selectedPosition = position;
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
        binding.potionCard.potionFlipBack.backDetailBtn.setOnClickListener(view -> {
            detailFragment = DetailFragment.newInstance(model.mPotion.getValue().serialNo, model.mPotion.getValue().name, model.mPotion.getValue().factory);
            detailFragment.setParentTag(TAG);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.home_detail_fragment, detailFragment)
                    .addToBackStack("home_detail")
                    .commit();
        });

        model.mPotionList.observe(getViewLifecycleOwner(), new Observer<List<MyPotion>>() {
            @Override
            public void onChanged(List<MyPotion> myPotions) {
                adapter.setData(new ArrayList<>(myPotions));
            }
        });
        model.mTodayIntake.observe(getViewLifecycleOwner(), new Observer<Intake>() {
            @Override
            public void onChanged(Intake intake) {
                binding.setModel(model);
                adapter.notifyItemChanged(selectedPosition);
            }
        });

        model.mPotion.observe(getViewLifecycleOwner(), new Observer<MyPotion>() {
            @Override
            public void onChanged(MyPotion selectedPotion) {
                if(selectedPotion != null) {
                    binding.setData(selectedPotion);
                    binding.setModel(model);
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