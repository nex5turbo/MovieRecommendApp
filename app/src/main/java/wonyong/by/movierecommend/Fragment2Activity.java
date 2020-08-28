package wonyong.by.movierecommend;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Fragment2Activity extends Fragment {

    Constants CONST = new Constants();
    EditText movieNameText;
    Button searchButton;
    Context thisContext = getActivity();
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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.activity_fragment2, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initWidget();
        initListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                            getActivity().runOnUiThread(new Runnable() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("검색어가 비었습니다.");
        builder.setMessage("검색어를 입력하세요.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.showSoftInput(movieNameText, 0);
                    }
                });
        builder.show();
    }

    private void initWidget() {
        movieNameText = getActivity().findViewById(R.id.movieNameTexxt2);
        searchButton = getActivity().findViewById(R.id.serachButton2);
        recyclerView = getActivity().findViewById(R.id.recyclerView2);
        progressBar = getActivity().findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
    }
}
