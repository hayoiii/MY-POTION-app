package com.palebluedot.mypotion.feature.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.PotionItem;
import com.palebluedot.mypotion.data.model.SearchResults;
import com.palebluedot.mypotion.feature.detail.DetailFragment;

import java.util.ArrayList;

//TODO: go to home fragment after producing a potion
public class SearchActivity extends AppCompatActivity {
    public static String TAG = "SearchActivity";
    private SearchViewModel model;
    private DetailFragment detailFragment;
    private EditText mSearchView;
    ListView mListView;

    private ImageView mSearchButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private LinearLayout mPagination, mTotalLayout;
    TextView mPageNo;
    TextView mMaxpageNo;
    TextView mTotalNo;
    int pageNo = 1;
    int maxPageNo = 1;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//TODO: databinding
        mTotalLayout = findViewById(R.id.search_total_layout);
        mPagination = findViewById(R.id.search_pagination);
        mPageNo = findViewById(R.id.curr_pageNo);
        mMaxpageNo = findViewById(R.id.max_pageNo);
        mTotalNo = findViewById(R.id.total);
        mSearchView = findViewById(R.id.search_view);
        mListView = findViewById(R.id.result_list_view);
        mSearchButton = findViewById(R.id.search_button);
        mPreviousButton = findViewById(R.id.previous_button);
        mNextButton = findViewById(R.id.next_button);

        final SearchListAdapter adapter = new SearchListAdapter(new ArrayList<>());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            //Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            PotionItem potion = (PotionItem)parent.getItemAtPosition(position);

            detailFragment = DetailFragment.newInstance(potion.getSerialNo(), potion.getProduct(), potion.getFactory());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_layout, detailFragment)
                    .addToBackStack(potion.getSerialNo())
                    .commit();
        });

        model = new ViewModelProvider(this).get(SearchViewModel.class);
        //observe : model의 LiveData를 관찰한다.
        model.get().observe(this, new Observer<SearchResults>() {
            @Override
            public void onChanged(SearchResults searchResults) {
                if(searchResults.getResults() == null) {
                    // TODO: 네트워크 연결x 일 때

                    toggleVisibility(false);
                }
                else if(searchResults.getTotal() == 0) {
                    // TODO: 검색 결과 없을 때

                    toggleVisibility(false);
                }
                else {
                    adapter.setData(searchResults.getResults());
                    pageNo = searchResults.getPageNo();
                    maxPageNo = searchResults.getMaxPageNo();
                    total = searchResults.getTotal();
                    mPageNo.setText(String.valueOf(pageNo));
                    mMaxpageNo.setText(String.valueOf(maxPageNo));
                    mTotalNo.setText(String.valueOf(total));

                    toggleVisibility(true);
                }
            }
        });

        //TODO: 엔터 버튼을 눌러도 검색되도록
        mSearchButton.setOnClickListener(v -> {
            String keyword = mSearchView.getText().toString();
            model.search(this, keyword, 1);
        });

        mNextButton.setOnClickListener(v -> {
            if (pageNo+1 <= maxPageNo){
                pageNo++;
                String keyword = mSearchView.getText().toString();
                model.search(this, keyword, pageNo);
            }
        });

        mPreviousButton.setOnClickListener(v -> {
            if(pageNo > 1){
                pageNo--;
                String keyword = mSearchView.getText().toString();
                model.search(this, keyword, pageNo);
            }
        });
    }

    private void toggleVisibility(boolean on) {
        if(!on) {
            mListView.setVisibility(View.INVISIBLE);
            mPagination.setVisibility(View.INVISIBLE);
            mTotalLayout.setVisibility(View.INVISIBLE);
        }
        else {
            mListView.setVisibility(View.VISIBLE);
            mPagination.setVisibility(View.VISIBLE);
            mTotalLayout.setVisibility(View.VISIBLE);
        }
    }
}