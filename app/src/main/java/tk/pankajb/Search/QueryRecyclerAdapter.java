package tk.pankajb.Search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tk.pankajb.InfoActivity;
import tk.pankajb.R;
import tk.pankajb.Search.SearchQueryResponse.Search;

public class QueryRecyclerAdapter extends RecyclerView.Adapter<Query_ViewHolder> {

    private List<Search> list;
    private Context context;

    public QueryRecyclerAdapter(Context context, List<Search> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Query_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        return new Query_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Query_ViewHolder holder, final int position) {

        holder.movieNameText.setText(list.get(position).getTitle());
        holder.movieTypeText.setText(list.get(position).getType());
        holder.movieYearText.setText(list.get(position).getYear());

        if(list.get(position).getChitra() != null){
            Glide.with(context).load(list.get(position).getChitra()).into(holder.poster);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("Id",list.get(position).getImdbID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
