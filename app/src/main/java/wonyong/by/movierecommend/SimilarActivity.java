package wonyong.by.movierecommend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SimilarActivity extends AppCompatActivity {

    Constants CONST = new Constants();
    DisplayInfo displayInfo;

    int id;
    ArrayList<MovieRecyclerData> data = new ArrayList<>();

    RecyclerView recyclerView;
    SimilarRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar);
        displayInfo = new DisplayInfo(this);
        initWidget();
        initData();
        getSimilarData();
    }

    private void initWidget() {
        recyclerView = findViewById(R.id.similarRecyclerview);
    }

    private void getSimilarData() {
        String strURL = CONST.TMDB_MOVIE_SIMILAR_SEARCH_URL+id+"/recommendations?api_key="+CONST.TMBD_KEY+"&language=ko&page=1";
        try {
            URL url = new URL(strURL);
            data = (ArrayList<MovieRecyclerData>)new GetDataTask(CONST.TYPE_SEARCH_SIMILAR_TMDB).execute(url).get();
            initRecyclerView();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        adapter = new SimilarRecyclerAdapter(data, displayInfo);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
    }
}