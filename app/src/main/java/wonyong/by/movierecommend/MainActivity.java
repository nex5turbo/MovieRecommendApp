package wonyong.by.movierecommend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/*
Naver Movie Search API
Client ID : YUOz3v1oFnNd9meSfC6t
Client Password : j3mHCWXLxM

영화진흥 위원회 API
KEY : 927f426e5914b98a82fe8636620488b5
&curPage=1&itemPerPage=100
 */


public class MainActivity extends AppCompatActivity {

    Constants CONST = new Constants();
    EditText movieNameText;
    Button searchButton;
    Context thisContext = this;
    ArrayList<MovieRecyclerData> data = new ArrayList<MovieRecyclerData>();
    MovieRecyclerAdapter adapter;
    RecyclerView recyclerView;
    String nowSearchText = "";
    ProgressBar progressBar;

    final int searchColor = 0x80000000;
    final int searchEndColor = 0x00000000;
    final Drawable searchDrawable = new ColorDrawable(searchColor);
    final Drawable searchEndDrawable = new ColorDrawable(searchEndColor);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        initListener();
        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MovieRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieNameText.getText().toString().trim().equals("")){
                    requireText();
                    return;
                }
                if(movieNameText.getText().toString().trim().equals(nowSearchText)){
                    return;
                }
                recyclerView.setForeground(searchDrawable);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();
                data = null;
                data = new ArrayList<MovieRecyclerData>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String movieName = movieNameText.getText().toString().trim();
                        nowSearchText = movieName;
//                        String movieName = "해리포터";
                        String apiURL = CONST.TMDB_MOVIE_SEARCH_URL+"api_key="+CONST.TMBD_KEY+"&language=ko&query="+movieName+"&include_adult=false"; // json 결과
                        Log.d("TMDB URL", apiURL);
                        URL url = null;
                        try {
                            url = new URL(apiURL);
                            ArrayList<MovieRecyclerData> tempData = (ArrayList<MovieRecyclerData>) new GetDataTask(CONST.TYPE_SEARCH_TMDB, thisContext).execute(url).get();
                            for(MovieRecyclerData temp : tempData){
                                data.add(temp);
                            }
                            for(MovieRecyclerData tmp : data){
                                Log.d("###after", tmp.title);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(null);
                                    adapter = new MovieRecyclerAdapter(data);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                    recyclerView.setForeground(searchEndDrawable);
                                }
                            });

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void requireText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("검색어가 비었습니다.");
        builder.setMessage("검색어를 입력하세요.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.showSoftInput(movieNameText, 0);
                    }
                });
        builder.show();
    }

    private void initWidget() {
        movieNameText = findViewById(R.id.movieNameTexxt);
        searchButton = findViewById(R.id.serachButton);
        recyclerView = findViewById(R.id.recyclerView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

}

/*
  String text = URLEncoder.encode(searchObject, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=" + display + "&"; // json 결과
                    // Json 형태로 결과값을 받아옴.
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();



출처: https://hyongdoc.tistory.com/167 [Doony Garage]
 */