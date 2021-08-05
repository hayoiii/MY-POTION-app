package com.palebluedot.mypotion.feature.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.databinding.FragmentDetailBinding;
import com.palebluedot.mypotion.databinding.FragmentDetailFlipFrontBinding;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wajahatkarim3.easyflipview.EasyFlipView;


//TODO: like db 추가, 성분 추가
public class DetailFragment extends Fragment implements View.OnClickListener {
    public DetailFragment() {
        // Required empty public constructor
    }

    private DetailViewModel model;
    String mSerialNo = null;

    private ImageView mBottomBtn;
    private Button rawMaterialBtn;
    private Button goToFrontBtn;
    private ShineButton likeBtn;
    private Button addBtn;
    private EasyFlipView detailFlip;
    private View noDataView;
    private ListView rawMaterialList;

    private ListView activityListView;
    private LinearLayout activityPageLayout;


    public static DetailFragment newInstance(String serialNo, String name, String factory) {
        Bundle args = new Bundle();
        args.putString("serialNo",serialNo);
        args.putString("name", name);
        args.putString("factory", factory);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSerialNo = getArguments().getString("serialNo");
        model = new ViewModelProvider(this).get(DetailViewModel.class);
        model.build(mSerialNo);
        model.setName(getArguments().getString("name"));
        model.setFactory(getArguments().getString("factory"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        View view = binding.getRoot();

        //부모 액티비티의 viewModel 가져옴
        //TODO: 로딩 (데이터 바인딩으로 처리하기 visibility로 로딩 켜고 끄기)
        model.getDetail().observe(this.getActivity(), new Observer<PotionDetail>() {
            @Override
            public void onChanged(PotionDetail potionDetail) {
                binding.setData(potionDetail);
                if(potionDetail.isNoData()) {
                    noDataView.setVisibility(View.VISIBLE);
                    detailFlip.setVisibility(View.GONE);
                }
            }
        });
        model.getLike().observe(this.getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.setLike(aBoolean);
            }
        });
        binding.setLifecycleOwner(this);

        noDataView = view.findViewById(R.id.no_data);

        //터치 못하도록 막기
        activityListView = requireActivity().findViewById(R.id.result_list_view);
        activityListView.setVisibility(View.INVISIBLE);
        activityPageLayout = requireActivity().findViewById(R.id.search_bottom_page_layout);
        activityPageLayout.setVisibility(View.GONE);

        //페이지 컨트롤러를 닫기 버튼으로 만들기
        mBottomBtn = requireActivity().findViewById(R.id.detail_bottom_btn);
        mBottomBtn.setVisibility(View.VISIBLE);
        mBottomBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(DetailFragment.this).commit();
        });

        // action buttons
        addBtn = view.findViewById(R.id.detail_add_btn);
        likeBtn = view.findViewById(R.id.like_fab);
        addBtn.setOnClickListener(this::onClick);
        likeBtn.setOnCheckStateChangeListener(model);

        // 카드 뒤집는 동작
        detailFlip = view.findViewById(R.id.detail_flip);
        goToFrontBtn = view.findViewById(R.id.detail_back_btn);
        goToFrontBtn.setOnClickListener(v -> {
            detailFlip.flipTheView();
        });
        rawMaterialBtn = view.findViewById(R.id.detail_front_btn);
        rawMaterialBtn.setOnClickListener(v -> {
            detailFlip.flipTheView();
        });

        rawMaterialList = view.findViewById(R.id.raw_material_list);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        CookieBar.dismiss(getActivity());
        activityListView.setVisibility(View.VISIBLE);
        activityPageLayout.setVisibility(View.VISIBLE);
        mBottomBtn.setVisibility(View.GONE);
        Log.e("DetailFragment","onDestroyView");
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.detail_add_btn:
//                //AddElixirActivity 실행
//                if(valid) {
//                    CookieBar.dismiss(getActivity());
//                    Intent intent = new Intent(getActivity(), AddElixirActivity.class);
//                    Log.e("DetailFragment", "isNew:");
//                    intent.putExtra("isNew", true);
//                    intent.putExtra("product", product);
//                    intent.putExtra("factory", factory);
//                    intent.putExtra("effect", effect);
//                    intent.putExtra("serialNo", mSerialNo);
//                    ((SearchActivity)getActivity()).goToAdd(intent);
//                }
//                CookieBar.dismiss(getActivity());
//                break;
//        }
    }


//    @Override
//    public void onCheckedChanged(View view, boolean checked) {
//        //TODO: 프로그레스바로 로딩 구현하기
//        if(valid) {
//            boolean succeed = insertOrDeleteLike(checked);
//            if (!succeed) {
//                CookieBar.dismiss(getActivity());
//                CookieBar.build(getActivity())
//                        .setTitle("실패하였습니다.").setBackgroundColor(R.color.contrastDark)
//                        .setEnableAutoDismiss(true)
//                        .setSwipeToDismiss(true)
//                        .setCookiePosition(Gravity.BOTTOM)
//                        .show();
//            } else {
//                if (checked) {
//                    CookieBar.dismiss(getActivity());
//                    CookieBar.build(getActivity())
//                            .setTitle("저장되었습니다.").setTitleColor(R.color.primary_text)
//                            .setMessage("저장소에서 확인하실 수 있습니다.\n기록을 시작하려면 엘릭서를 제조하세요.").setMessageColor(R.color.primary_text)
//                            .setIcon(R.drawable.ic_filled_check_circle_24)
//                            //TODO : when from likes
//                            .setAction("저장소로 이동", () -> {
//                                //MainActivity의 likes로 이동
//                                CookieBar.dismiss(getActivity());
//                                ((SearchActivity) requireActivity()).goToLikes();
//                                mBottomBtn.callOnClick();       //fragment 종료
//                            })
//                            .setBackgroundColor(R.color.cookie_background)
//                            .setEnableAutoDismiss(true)
//                            .setDuration(3000)
//                            .setSwipeToDismiss(true)
//                            .setCookiePosition(Gravity.BOTTOM)
//                            .show();
//                } else {
//                    CookieBar.dismiss(getActivity());
//                    CookieBar.build(getActivity())
//                            .setTitle("저장소에서 삭제하였습니다.").setBackgroundColor(R.color.contrastDark)
//                            .setEnableAutoDismiss(true)
//                            .setSwipeToDismiss(true)
//                            .setCookiePosition(Gravity.BOTTOM)
//                            .show();
//                }
//            }
//        }
//    }
}