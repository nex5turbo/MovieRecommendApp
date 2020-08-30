package wonyong.by.movierecommend;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainFragment extends Fragment {

    Constants CONST = new Constants();
    Context thisContext = getActivity();

    ArrayList<MovieRankRecyclerData> boxofficeData = new ArrayList<MovieRankRecyclerData>();
    ArrayList<MovieRankRecyclerData> boxofficeKoreaData = new ArrayList<MovieRankRecyclerData>();
    ArrayList<MovieRankRecyclerData> boxofficeForeignData = new ArrayList<MovieRankRecyclerData>();

    TextView boxofficeText;
    TextView boxofficeKoreaText;
    TextView boxofficeForeignText;

    RecyclerView boxofficeRecyclerView;
    RecyclerView boxofficeKoreaRecyclerView;
    RecyclerView boxofficeForeignRecyclerView;

    MovieRankRecyclerAdapter boxofficeAdapter;
    MovieRankKoreaRecyclerAdapter boxofficeKoreaAdapter;
    MovieRankForeignRecyclerAdapter boxofficeForeignAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.activity_main_fragment, container, false);
        Log.d("OPEN APP", "OPENED ROOT");
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initWidget();
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        boxofficeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        boxofficeAdapter = new MovieRankRecyclerAdapter(boxofficeData);
        boxofficeRecyclerView.setAdapter(boxofficeAdapter);

        boxofficeKoreaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        boxofficeKoreaAdapter = new MovieRankKoreaRecyclerAdapter(boxofficeKoreaData);
        boxofficeKoreaRecyclerView.setAdapter(boxofficeKoreaAdapter);

        boxofficeForeignRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        boxofficeForeignAdapter = new MovieRankForeignRecyclerAdapter(boxofficeForeignData);
        boxofficeForeignRecyclerView.setAdapter(boxofficeForeignAdapter);
    }

    private void initWidget() {
        boxofficeText = getActivity().findViewById(R.id.boxofficeText);
        boxofficeKoreaText = getActivity().findViewById(R.id.boxofficeKoreaText);
        boxofficeForeignText = getActivity().findViewById(R.id.boxofficeForeignText);

        boxofficeRecyclerView = getActivity().findViewById(R.id.boxofficeRecyclerView);
        boxofficeKoreaRecyclerView = getActivity().findViewById(R.id.boxofficeKoreaRecyclerView);
        boxofficeForeignRecyclerView = getActivity().findViewById(R.id.boxofficeForeignRecyclerView);

        PagerSnapHelper snapHelper1 = new PagerSnapHelper();
        PagerSnapHelper snapHelper2 = new PagerSnapHelper();
        PagerSnapHelper snapHelper3 = new PagerSnapHelper();
        snapHelper1.attachToRecyclerView(boxofficeRecyclerView);
        snapHelper2.attachToRecyclerView(boxofficeKoreaRecyclerView);
        snapHelper3.attachToRecyclerView(boxofficeForeignRecyclerView);

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String searchDate = getYesterdayDate();
                String str_boxofficeURL = CONST.MOVIE_BOXOFFICE_URL+"key="+CONST.MOVIE_BOXOFFICE_KEY+"&targetDt="+searchDate;
                String str_boxofficeKoreaURL = str_boxofficeURL+"&repNationCd=K";
                String str_boxofficeForeignURL = str_boxofficeURL+"&repNationCd=F";
                Log.d("URL RANK", str_boxofficeURL);
                Log.d("URL RANK", str_boxofficeKoreaURL);
                Log.d("URL RANK", str_boxofficeForeignURL);
                try {
                    ArrayList<MovieRankRecyclerData> temp;
                    ArrayList<MovieRankRecyclerData> tempKorea;
                    ArrayList<MovieRankRecyclerData> tempForeign;
                    URL boxofficeURL = new URL(str_boxofficeURL);
                    URL boxofficeKoreaURL = new URL(str_boxofficeKoreaURL);
                    URL boxofficeForeignURL = new URL(str_boxofficeForeignURL);
                    temp = (ArrayList<MovieRankRecyclerData>) new GetDataTask(CONST.TYPE_RANK, thisContext).execute(boxofficeURL).get();
                    tempKorea = (ArrayList<MovieRankRecyclerData>) new GetDataTask(CONST.TYPE_RANK, thisContext).execute(boxofficeKoreaURL).get();
                    tempForeign = (ArrayList<MovieRankRecyclerData>) new GetDataTask(CONST.TYPE_RANK, thisContext).execute(boxofficeForeignURL).get();
                    dataInsert(temp, 0);
                    dataInsert(tempKorea ,1);
                    dataInsert(tempForeign, 2);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boxofficeAdapter.notifyDataSetChanged();
                        boxofficeKoreaAdapter.notifyDataSetChanged();
                        boxofficeForeignAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


    }

    private String getYesterdayDate(){
        Date dDate = new Date();
        dDate = new Date(dDate.getTime()+(1000*60*60*24*-1));
        SimpleDateFormat dSdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String yesterday = dSdf.format(dDate);

        return yesterday;
    }

    private void dataInsert(ArrayList<MovieRankRecyclerData> temp, int type){
        switch(type){
            case 0://total
                for(MovieRankRecyclerData tmp : temp){
                    boxofficeData.add(tmp);
                }
                break;
            case 1://korea
                for(MovieRankRecyclerData tmp : temp){
                    boxofficeKoreaData.add(tmp);
                }
                break;
            case 2://foreign
                for(MovieRankRecyclerData tmp : temp){
                    boxofficeForeignData.add(tmp);
                }
                break;
        }

    }
}