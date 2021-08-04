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
import androidx.lifecycle.ViewModelProvider;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.databinding.FragmentDetailBinding;
import com.palebluedot.mypotion.databinding.FragmentDetailFlipFrontBinding;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wajahatkarim3.easyflipview.EasyFlipView;


//TODO: like db 추가, 성분 추가
public class DetailFragment extends Fragment implements View.OnClickListener, ShineButton.OnCheckedChangeListener {

    private DetailViewModel model;

    String product = null;
    String factory = null;
    String mSerialNo = null;
    String effect = null;
    private boolean valid = false;
    private static boolean like_flag = false;

    public DetailFragment() {
        // Required empty public constructor
    }
    private TextView effectText;
    private TextView shapeText;
    private TextView cautionText;
    private TextView takeWayText;
    private TextView storeWayText;
    private TextView productText;
    private TextView factoryText;
    private Button rawMaterialBtn;
    private Button goToFrontBtn;

    private ListView rawMaterialList;

    private ShineButton likeBtn;
    private Button addBtn;

    private EasyFlipView detailFlip;

    private ListView activityListView;
    private LinearLayout activityPageLayout;
    private ImageView mBottomBtn;


    public static DetailFragment newInstance(String serialNo) {
        Bundle args = new Bundle();
        args.putString("serialNo",serialNo);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSerialNo = getArguments().getString("serialNo");
        model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        model.build(mSerialNo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        View view = binding.getRoot();

        FragmentDetailFlipFrontBinding flipFrontBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_flip_front, container, false);

        //부모 액티비티의 viewModel 가져옴
        PotionDetail detailData = model.getDetail();
        binding.setData(detailData);
        binding.setViewmodel(model);
        binding.setLifecycleOwner(this);
        flipFrontBinding.setData(detailData);

        addBtn = view.findViewById(R.id.detail_add_btn);
        likeBtn = view.findViewById(R.id.like_fab);


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
            fragmentManager.popBackStack();
        });

        // action buttons
        addBtn.setOnClickListener(this::onClick);
        likeBtn.setOnCheckStateChangeListener(this);

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

//    private boolean insertOrDeleteLike(boolean insert){
//        dbHelper = DbHelper.getInstance(getActivity());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        if(insert){
//            ContentValues values = new ContentValues();
//            values.put(DbContract.LikesEntry.COLUMN_NAME_PRODUCT, product);
//            values.put(DbContract.LikesEntry.COLUMN_NAME_FACTORY, factory);
//            values.put(DbContract.LikesEntry.COLUMN_NAME_SERIALNO, mSerialNo);
//            String tagsStr="";
//            LinkedList<String> tags = TagManager.getInstance().extractTags(effect);
//            for(int i=0; i<tags.size(); i++){
//                tagsStr += "#"+tags.get(i)+" ";
//            }
//            values.put(DbContract.LikesEntry.COLUMN_NAME_TAGS, tagsStr);
//
//            long newRowId = db.insert(DbContract.LikesEntry.TABLE_NAME, null, values);
//            db.close();
//            return newRowId > 0;
//        }
//        else{
//            int deleteCount = db.delete(DbContract.LikesEntry.TABLE_NAME, DbContract.LikesEntry.COLUMN_NAME_SERIALNO+ "=" + mSerialNo, null);
//            db.close();
//            return deleteCount > 0;
//        }
//    }

//
//    //TODO: error handling
//    private void requestC00Api(String serialNo){
//        Call<PotionDetail> res = OpenDataApiUtil.getInstance().getApi().getDetail(serialNo);
//        res.enqueue(new Callback<PotionDetail>() {
//            @Override
//            public void onResponse(Call<PotionDetail> call, Response<PotionDetail> response) {
//                if (response.body() != null) {
//                    PotionDetail potionDetail = (PotionDetail) response.body();
//                    if (potionDetail.getC003().getRESULT().getCODE().equals("INFO-000")) {
//                        product = potionDetail.getC003().getRow().get(0).getPRDLST_NM();
//                        factory = potionDetail.getC003().getRow().get(0).getBSSH_NM();
//                        String shape = potionDetail.getC003().getRow().get(0).getDISPOS();
//                        String takeWay = potionDetail.getC003().getRow().get(0).getNTK_MTHD();
//                        effect = potionDetail.getC003().getRow().get(0).getPRIMARY_FNCLTY();
//                        String caution = potionDetail.getC003().getRow().get(0).getIFTKN_ATNT_MATR_CN();
//                        String storeWay = potionDetail.getC003().getRow().get(0).getCSTDY_MTHD();
//                        String originalRawMaterials = potionDetail.getC003().getRow().get(0).getRAWMTRL_NM();
//
//                        String[] rawMaterials = originalRawMaterials.split(",");
//
//                        ArrayAdapter adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, rawMaterials);
//                        rawMaterialList.setAdapter(adapter);
//
//                        productText.setText(product);
//                        factoryText.setText(factory);
//
//                        shapeText.setText(shape);
//                        takeWayText.setText(takeWay);
//                        effectText.setText(effect);
//                        cautionText.setText(caution);
//                        storeWayText.setText(storeWay);
//
//                        valid = true;
//
//                    }
//                    else{
//                        //TODO: 오류 코드 시 처리
//                        productText.setText(potionDetail.getC003().getRESULT().getMSG());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PotionDetail> call, Throwable t) {
//                Log.e("Err", t.getMessage());
//            }
//        });
//    }
//
//    @Override
    public void onCheckedChanged(View view, boolean checked) {

    }
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