package wonyong.by.movierecommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieRankRecyclerAdapter extends RecyclerView.Adapter<MovieRankRecyclerAdapter.MyViewHolder> {

    private Constants CONST = new Constants();
    private ArrayList<MovieRankRecyclerData> items;

    public MovieRankRecyclerAdapter(ArrayList<MovieRankRecyclerData> items){
        this.items = items;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView openDate;
        TextView totalSales;
        TextView totalAudience;

        MyViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.boxofficeItemTitleText);
            openDate = itemView.findViewById(R.id.boxofficeItemOpendtText);
            totalSales = itemView.findViewById(R.id.boxofficeItemTotalSalesText);
            totalAudience = itemView.findViewById(R.id.boxofficeItemTotalAudienceText);
        }
    }

    @Override
    public MovieRankRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.boxoffice_recycler_item, parent, false);
        MovieRankRecyclerAdapter.MyViewHolder vh = new MovieRankRecyclerAdapter.MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(MovieRankRecyclerAdapter.MyViewHolder holder, int position){
        MovieRankRecyclerData data = items.get(position);
        String title = data.title;
        String openDate = data.openDate;
        String totalSales = data.totalSales;
        String totalAudience = data.totalAudience;
        String rank = data.rank;

        holder.title.setText(title+" "+rank+"위");
        holder.openDate.setText(openDate);
        holder.totalSales.setText(totalSales);
        holder.totalAudience.setText(totalAudience);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }


}
