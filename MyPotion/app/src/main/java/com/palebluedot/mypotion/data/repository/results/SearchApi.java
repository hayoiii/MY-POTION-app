package com.palebluedot.mypotion.data.repository.results;

import android.content.Context;
import com.palebluedot.mypotion.feature.search.SearchActivity;
import com.palebluedot.mypotion.util.NetworkUtil;

public class SearchApi {
    private Context mContext = null;

    public void search(String keyword){
        if(NetworkUtil.check(mContext)) {
//            HtfsInfoServiceAPI task = new HtfsInfoServiceAPI(keyword, pageNo, SearchActivity.this);
//            task.execute();
        } else {
            // TODO: 네트워크 연결 안되어있을 때
//            AlertUtil.failureCookieBar(this, "네트워크 연결").setEnableAutoDismiss(false).show();
//            SearchListAdapter adapter = new SearchListAdapter(new ArrayList<>());
//            mPageContainer.setVisibility(View.INVISIBLE);
//            mListView.setAdapter(adapter);
        }
    }

}
