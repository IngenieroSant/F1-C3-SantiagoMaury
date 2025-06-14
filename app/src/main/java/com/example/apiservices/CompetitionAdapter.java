package com.example.apiservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;

public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.ViewHolder> {
    private List<TeamDetail.Competition> competitions;
    private Context context;
    private ImageLoader imageLoader;

    public CompetitionAdapter(Context context, List<TeamDetail.Competition> competitions, ImageLoader imageLoader) {
        this.context = context;
        this.competitions = competitions;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_competition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamDetail.Competition competition = competitions.get(position);
        holder.nameTextView.setText(competition.getName());
        holder.typeTextView.setText(competition.getType());
        
        if (competition.getEmblem() != null) {
            holder.emblemImageView.setImageUrl(competition.getEmblem(), imageLoader);
            holder.emblemImageView.setVisibility(View.VISIBLE);
        } else {
            holder.emblemImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return competitions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView typeTextView;
        NetworkImageView emblemImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.competitionName);
            typeTextView = itemView.findViewById(R.id.competitionType);
            emblemImageView = itemView.findViewById(R.id.competitionEmblem);
        }
    }
} 