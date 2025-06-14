package com.example.apiservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private List<TeamDetail.Player> players;
    private Context context;

    public PlayerAdapter(Context context, List<TeamDetail.Player> players) {
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamDetail.Player player = players.get(position);
        holder.nameTextView.setText(player.getName());
        holder.positionTextView.setText(player.getPosition());
        holder.nationalityTextView.setText(player.getNationality());
        holder.birthDateTextView.setText(player.getDateOfBirth());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView positionTextView;
        TextView nationalityTextView;
        TextView birthDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.playerName);
            positionTextView = itemView.findViewById(R.id.playerPosition);
            nationalityTextView = itemView.findViewById(R.id.playerNationality);
            birthDateTextView = itemView.findViewById(R.id.playerBirthDate);
        }
    }
} 