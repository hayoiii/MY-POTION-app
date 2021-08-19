package com.palebluedot.mypotion.feature.detail;

import static android.content.Context.CLIPBOARD_SERVICE;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.databinding.FragmentDetailBinding;
import com.palebluedot.mypotion.feature.produce.ProduceActivity;
import com.palebluedot.mypotion.feature.search.SearchActivity;
import com.palebluedot.mypotion.util.MyUtil;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements View.OnClickListener {
    public DetailFragment() {
        // Required empty public constructor
    }

    private DetailViewModel model;
    String mSerialNo = null;

    private EasyFlipView detailFlip;
    private View noDataView;
    private ListView rawMaterialList;
    private ArrayAdapter adapter;
    private ListView activityListView;
    private LinearLayout activityPageLayout;
    private TextView tagsText;


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
        tagsText = view.findViewById(R.id.detail_effect_tags);
        //부모 액티비티의 viewModel 가져옴
        //TODO: 로딩 (데이터 바인딩으로 처리하기 visibility로 로딩 켜고 끄기)
        model.getDetail().observe(this.getActivity(), potionDetail -> {
            if(potionDetail.isNoData()) {
                binding.setData(potionDetail);
                noDataView.setVisibility(View.VISIBLE);
                detailFlip.setVisibility(View.GONE);
            }
            else {
                binding.setData(potionDetail);
                tagsText.setText(model.getTagStyleString());
                ArrayList<String> rawMaterials = MyUtil.splitByComma(potionDetail.getRawMaterials());
                adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, rawMaterials);
                rawMaterialList.setAdapter(adapter);
                rawMaterialList.setOnItemClickListener((adapterView, view1, i, l) -> {
                    String material = (String) adapterView.getItemAtPosition(i);
                    ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("copy_raw_material", material);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(requireContext(), "클립보드에 복사하였습니다", Toast.LENGTH_SHORT).show();
                });
            }
        });
        model.getLike().observe(this.getActivity(), aBoolean -> binding.setLike(aBoolean));
        binding.setLifecycleOwner(this);

        noDataView = view.findViewById(R.id.no_data);

        //터치 못하도록 막기
        activityListView = requireActivity().findViewById(R.id.result_list_view);
        activityListView.setVisibility(View.INVISIBLE);
        activityPageLayout = requireActivity().findViewById(R.id.search_pagination);
        activityPageLayout.setVisibility(View.INVISIBLE);


        // action buttons
        Button addBtn = view.findViewById(R.id.detail_add_btn);
        ShineButton likeBtn = view.findViewById(R.id.like_fab);
        addBtn.setOnClickListener(this::onClick);
        likeBtn.setOnCheckStateChangeListener(model);

        ImageButton mCloseBtn = view.findViewById(R.id.detail_close_btn);
        mCloseBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(DetailFragment.this).commit();
        });

        // 카드 뒤집는 동작
        detailFlip = view.findViewById(R.id.detail_flip);
        Button rawMaterialBtn = view.findViewById(R.id.detail_front_btn);
        Button goToFrontBtn = view.findViewById(R.id.detail_back_btn);
        rawMaterialBtn.setOnClickListener(v -> detailFlip.flipTheView());
        goToFrontBtn.setOnClickListener(v -> detailFlip.flipTheView());

        rawMaterialList = view.findViewById(R.id.raw_material_list);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        CookieBar.dismiss(getActivity());
        activityListView.setVisibility(View.VISIBLE);
        activityPageLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_add_btn:
                PotionDetail detail = model.getDetail().getValue();
                if(detail == null)  return;
                //AddElixirActivity 실행
                    Intent intent = new Intent(getActivity(), ProduceActivity.class);
                    intent.putExtra("EDIT_MODE", false);
                    intent.putExtra("name", detail.getName());
                    intent.putExtra("factory", detail.getFactory());
                    intent.putExtra("effect", detail.getEffect());
                    intent.putExtra("serialNo", mSerialNo);
                    startActivity(intent);
                break;
        }
    }


//    @Override
//    public void onCheckedChanged(View view, boolean checked) {
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