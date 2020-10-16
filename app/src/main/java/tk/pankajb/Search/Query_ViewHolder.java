package tk.pankajb.Search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tk.pankajb.R;

public class Query_ViewHolder extends RecyclerView.ViewHolder {

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
