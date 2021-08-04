package com.palebluedot.mypotion.feature.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.PotionItem;
import com.palebluedot.mypotion.data.model.SearchResults;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel model;

    private EditText mSearchView;
    ListView mListView;

    private ImageView mSearchButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private CardView mPagination;
    TextView mPageNo;
    TextView mMaxpageNo;
    int pageNo = 1;
    int maxPageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mPagination = findViewById(R.id.search_pagination);
        mPageNo = findViewById(R.id.curr_pageNo);
        mMaxpageNo = findViewById(R.id.max_pageNo);
        mSearchView = findViewById(R.id.search_view);
        mListView = findViewById(R.id.result_list_view);
        mSearchButton = findViewById(R.id.search_button);
        mPreviousButton = findViewById(R.id.previous_button);
        mNextButton = findViewById(R.id.next_button);

        final SearchListAdapter adapter = new SearchListAdapter(new ArrayList<PotionItem>());
        mListView.setAdapter(adapter);

        model = new ViewModelProvider(this).get(SearchViewModel.class);
        //observe : model의 LiveData를 관찰한다.
        model.get().observe(this, new Observer<SearchResults>() {
            @Override
            public void onChanged(SearchResults searchResults) {
                adapter.setData(searchResults.getResults());
                pageNo = searchResults.getPageNo();
                maxPageNo = searchResults.getMaxPageNo();
                mPageNo.setText(pageNo);
                mMaxpageNo.setText(maxPageNo);
            }
        });

        mSearchButton.setOnClickListener(v -> {
            String keyword = mSearchView.getText().toString();
            model.search(keyword, 1);
        });
    }
}