package wonyong.by.movierecommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>{



    private Constants CONST = new Constants();
    private ArrayList<MovieRecyclerData> items;

    public MovieRecyclerAdapter(ArrayList<MovieRecyclerData> items){
        this.items = items;
    }

    private MovieRecyclerAdapter.OnItemClickListener mListener = null;

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(MovieRecyclerAdapter.OnItemClickListener listener)
    {
        this.mListener = listener;
    }
    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView movieName;
        TextView openDate;
        TextView country;
        ImageView thumnail;

        MyViewHolder(View itemView){
            super(itemView);

            movieName = itemView.findViewById(R.id.recyclerMovieName);
            openDate = itemView.findViewById(R.id.recyclerMovieOpendate);
            country = itemView.findViewById(R.id.recyclerMovieCountry);
            thumnail = itemView.findViewById(R.id.recyclerMovieThumbnail);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        mListener.onItemClick(view,pos);
                    }
                }
            });
        }
    }

    @Override
    public MovieRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.movie_recycler_item, parent, false);
        MovieRecyclerAdapter.MyViewHolder vh = new MovieRecyclerAdapter.MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(MovieRecyclerAdapter.MyViewHolder holder, int position){
        String genre = "";
        MovieRecyclerData data = items.get(position);
        holder.movieName.setText(data.title);
        if(holder.openDate.equals("")){
            holder.openDate.setText("정보 없음");
        }else {
            holder.openDate.setText(data.release_date + " 개봉");
        }
        for(int temp : data.genre_ids){
            genre = genre+" "+CONST.getGenre(temp);
        }
        holder.country.setText(genre);
        Glide.with(holder.itemView).load(data.posterUrl).into(holder.thumnail);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public void clearItems(){
        for(int i = 0; i < items.size(); i++){
            items.remove(0);
            notifyItemRemoved(0);
        }
    }
}
