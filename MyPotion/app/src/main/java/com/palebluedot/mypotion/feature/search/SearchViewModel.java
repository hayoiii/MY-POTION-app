package com.palebluedot.mypotion.feature.search;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.palebluedot.mypotion.data.model.SearchResults;
import com.palebluedot.mypotion.data.repository.results.SearchResultsRepository;

public class SearchViewModel extends AndroidViewModel {
    private SearchResultsRepository repository;
    private LiveData<SearchResults> mData;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = SearchResultsRepository.getInstance();
        mData = repository.getData();
    }

    public LiveData<SearchResults> search(Context context, String keyword, int pageNo) {
        return repository.getSearchResults(context, keyword, pageNo);
    }

    public LiveData<SearchResults> get() {
        return mData;
    }
}
