package tk.pankajb.Search;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tk.pankajb.InfoActivity;
import tk.pankajb.R;
import tk.pankajb.Search.SearchQueryResponse.Search;

public class QueryRecyclerAdapter extends RecyclerView.Adapter<QueryRecyclerAdapter.Query_ViewHolder> {

    private List<Search> list;
    private Activity context;

    public QueryRecyclerAdapter(Activity context, List<Search> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Query_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new Query_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Query_ViewHolder holder, final int position) {

        holder.movieNameText.setText(list.get(position).getTitle());
        holder.movieTypeText.setText(list.get(position).getType());
        holder.movieYearText.setText(list.get(position).getYear());

        if (list.get(position).getChitra() != null) {
            Glide.with(context).load(list.get(position).getChitra()).placeholder(R.drawable.movie_default).into(holder.poster);
        }else {
            Glide.with(context).load(context.getDrawable(R.drawable.movie_default)).placeholder(R.drawable.movie_default).into(holder.poster);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("Id", list.get(position).getImdbID());
                context.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Query_ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView poster;
        TextView movieNameText;
        TextView movieYearText;
        TextView movieTypeText;

        public Query_ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            poster = view.findViewById(R.id.single_resource_poster);
            movieNameText = view.findViewById(R.id.single_resource_name);
            movieYearText = view.findViewById(R.id.single_resource_year);
            movieTypeText = view.findViewById(R.id.single_resource_type);
        }
    }
}
