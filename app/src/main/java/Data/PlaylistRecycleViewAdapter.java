package Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.abdsaam.finalmusicplayer.MusicPlayer;
import com.example.abdsaam.finalmusicplayer.Playlist;
import com.example.abdsaam.finalmusicplayer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.PlaylistNames;
import Model.TopTracks;

/**
 * Created by abdsaam on 18/03/2018.
 */

public class PlaylistRecycleViewAdapter extends RecyclerView.Adapter<PlaylistRecycleViewAdapter.ViewHolder> {
    private Context context;
    private List<PlaylistNames> playlistNames;

    public PlaylistRecycleViewAdapter(Context context, List<PlaylistNames> playlistNames) {
        this.context = context;
        this.playlistNames = playlistNames;
    }

    @Override
    public PlaylistRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlistnames, parent, false);
        return new PlaylistRecycleViewAdapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(final PlaylistRecycleViewAdapter.ViewHolder holder, final int position) {

        PlaylistNames playlistNames1 = playlistNames.get(position);
        String posterLink = playlistNames1.getImg();
        holder.Playlistname.setText(playlistNames1.getTitle());




    }

    @Override
    public int getItemCount() {
        return playlistNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Playlistname;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            Playlistname = (TextView) itemView.findViewById(R.id.Playlistname);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
