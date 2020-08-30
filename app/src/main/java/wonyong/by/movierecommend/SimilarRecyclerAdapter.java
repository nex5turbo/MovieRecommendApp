package wonyong.by.movierecommend;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SimilarRecyclerAdapter extends RecyclerView.Adapter<SimilarRecyclerAdapter.MyViewHolder>{

    private ArrayList<MovieRecyclerData> items;
    DisplayInfo displayInfo;

    public SimilarRecyclerAdapter(ArrayList<MovieRecyclerData> items, DisplayInfo displayInfo){
        this.items = items;
        this.displayInfo = displayInfo;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView poster;

        MyViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.similarTitleText);
            poster = itemView.findViewById(R.id.similarPoster);
        }
    }

    //dp = pixel/(dpi/160)
    //pixel = dp*(dpi/160)

    @Override
    public SimilarRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.similar_recycler_item, parent, false);
        SimilarRecyclerAdapter.MyViewHolder vh = new SimilarRecyclerAdapter.MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(SimilarRecyclerAdapter.MyViewHolder holder, int position){
        MovieRecyclerData data = items.get(position);
        holder.title.setText(data.title);
        int widthDp = displayInfo.getDeviceWidthDp();
        int sectionPixel = displayInfo.getPixelfromDp((widthDp-24)/2);

        holder.title.setMaxWidth(sectionPixel);

        holder.poster.setImageBitmap(Bitmap.createScaledBitmap(data.poster, sectionPixel, (int)(sectionPixel*1.5), false));
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
}
