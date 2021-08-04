package com.palebluedot.mypotion.data.repository.results;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.PotionItem;
import com.palebluedot.mypotion.data.model.SearchResults;
import com.palebluedot.mypotion.util.NetworkUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SearchResultsRepository {
    private static SearchResultsRepository instance;
    Context context;
    @NonNull
    MutableLiveData<SearchResults> data = new MutableLiveData<>();

    @NonNull
    public MutableLiveData<SearchResults> getData() {
        return data;
    }

    public void setData(@NonNull MutableLiveData<SearchResults> data) {
        this.data = data;
    }

    public static SearchResultsRepository getInstance(Application application) {
        if(instance == null) {
            synchronized (SearchResultsRepository.class) {
                if(instance == null) {
                    instance = new SearchResultsRepository(application);
                }
            }
        }
        return instance;
    }
    private SearchResultsRepository(Application application){
        this.context = application.getApplicationContext();
    }

    public LiveData<SearchResults> getSearchResults(String keyword, int pageNo) {
        if(NetworkUtil.check(context)) {
            HtfsInfoServiceAPI task = new HtfsInfoServiceAPI(keyword, pageNo);
            try {
                data.setValue(task.execute().get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            data.setValue(new SearchResults(null, 1, 0, 0));

            // TODO: 네트워크 연결 안되어있을 때
//            AlertUtil.failureCookieBar(this, "네트워크 연결").setEnableAutoDismiss(false).show();
//            SearchListAdapter adapter = new SearchListAdapter(new ArrayList<>());
//            mPageContainer.setVisibility(View.INVISIBLE);
//            mListView.setAdapter(adapter);
        }
        return data;

    }


    /*건강기능식품 서비스 api*/
    private class HtfsInfoServiceAPI extends AsyncTask<Void, Void, SearchResults> {
        private final String BASE_URI = "http://apis.data.go.kr/1470000/HtfsInfoService/getHtfsList?ServiceKey=s%2BNvTjaKtsyT1%2BlP6yGS%2FCfxqzWMgdGie6yOQr3dJLHAYG9q0sQmYdrDrrMCnS3797H9TBiHJBI8%2BFy3ex9A0A%3D%3D";
        private String url;

        HtfsInfoServiceAPI(String keyword, int pageNo) {
            this.url = keyword == null || keyword.equals("") ? this.BASE_URI + "&pageNo=" + pageNo : this.BASE_URI + "&Prduct=" + keyword + "&pageNo=" + pageNo;
            context = null;
        }

        /*백그라운드 스레드가 실행되기 전, 메인 스레드에 의해 호출되는 메서드
         * 주로 UI 초기화 */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // loading progress bar
    //            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
    //            dialog.getProgressHelper().setBarColor(ContextCompat.getColor(mContext, R.color.secondary));
    //            dialog.setTitle("Loading");
    //            dialog.setCancelable(true);
    //            dialog.setOnCancelListener(dialog -> {
    //                me.cancel(true);
    //                Toast.makeText(mContext, "취소되었습니다.", Toast.LENGTH_SHORT).show();
    //            });
    //            dialog.show();
        }

        /*실질적인 비동기 작업이 실행
         * UI를 직접 제어하면 X*/
        @Override
        protected SearchResults doInBackground(Void... voids) {
            List<PotionItem> results = new ArrayList<>();
            int pageNo;
            int maxPageNo;
            int total;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactoty.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = dBuilder.parse(this.url);
            } catch (IOException | SAXException e) {
                Log.d("Exception", "on Document");
                // TODO: error handling
                e.printStackTrace();
            }

            // root tag
            doc.getDocumentElement().normalize();

            //page 정보
            NodeList nHeaderList = doc.getElementsByTagName("header");
            Node nHeader = nHeaderList.item(0);
            org.w3c.dom.Element eHeader = (org.w3c.dom.Element) nHeader;
            String sCode = getTagValue("resultCode", eHeader);

            if (!sCode.equals("00")) {
                Log.e("API error", "error code-" + sCode);
                // TODO: error handling
                return null;
            }

            // 페이지 정보 추출
            NodeList nBodyList = doc.getElementsByTagName("body");
            Node nBody = nBodyList.item(0);
            org.w3c.dom.Element eBody = (org.w3c.dom.Element) nBody;
            String sPageNo = getTagValue("pageNo", eBody);
            String sTotal = getTagValue("totalCount", eBody);

            //건강기능식품 리스트 받아오기
            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                    String product = getTagValue("PRDUCT", eElement);
                    String factory = getTagValue("ENTRPS", eElement);
                    String serialNo = getTagValue("STTEMNT_NO", eElement);

                    PotionItem item = new PotionItem(product, factory, serialNo);
                    results.add(item);
                }    // for end
            }    // if end
            pageNo = Integer.parseInt(sPageNo);
            maxPageNo = (int) Math.ceil(Integer.parseInt(sTotal) / 10.0);
            total = Integer.parseInt(sTotal);

            SearchResults searchResults = new SearchResults(results, pageNo, maxPageNo, total);
            return searchResults;
        }

        /*doInBackground의 결과값을 받음*/
        @Override
        protected void onPostExecute(SearchResults searchResults) {
    //            dialog.dismiss();
    //            if (potions != null) {
    //                SearchListAdapter adapter = new SearchListAdapter(potions);
    //                mListView.setAdapter(adapter);
    //                if (potions.size() == 0) {
    //                    CookieBar.build(activity)
    //                            .setTitle("검색결과가 없습니다.").setBackgroundColor(R.color.secondary_light)
    //                            .setTitleColor(R.color.primary_text)
    //                            .setDuration(5000)
    //                            .setEnableAutoDismiss(true)
    //                            .setSwipeToDismiss(true)
    //                            .setCookiePosition(Gravity.BOTTOM);
    //                    mPageContainer.setVisibility(View.INVISIBLE);
    //                }
    //                mCurrpageNo.setText(Integer.toString(pageNo));
    //                mMaxpageNo.setText(Integer.toString(maxPageNo));
    //
    //                mListView.setOnItemClickListener((parent, view, position, id) -> {
    //                    //Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
    //                    Potion potion = (Potion) parent.getItemAtPosition(position);
    //
    //                    detailFragmentFromSearch = DetailFragmentFromSearch.newInstance(potion.getSerialNo());
    //                    detailFragmentFromSearch.init(isLiked(potion.getSerialNo()));
    //
    //                    getSupportFragmentManager().beginTransaction()
    //                            .add(R.id.search_layout, detailFragmentFromSearch)
    //                            .addToBackStack(null)
    //                            .commit();
    //                });
    //                mPageContainer.setVisibility(View.VISIBLE);
    //                return;
    //            }
    //            //검색 실패 시
    //            AlertUtil.createFailureSAD(mContext, "요청").show();
    //            mPageContainer.setVisibility(View.INVISIBLE);
        }

        private String getTagValue(String tag, org.w3c.dom.Element eElement) {
            NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            Node nValue = (Node) nlList.item(0);
            if (nValue == null)
                return null;
            return nValue.getNodeValue();
        }
    }
}
