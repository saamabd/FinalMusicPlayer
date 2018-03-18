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

import Model.TopTracks;

/**
 * Created by abdsaam on 17/03/2018.
 */

public class MusicRecycleViewAdapter extends RecyclerView.Adapter<MusicRecycleViewAdapter.ViewHolder>  {
    private Context context;
    private List<TopTracks> topTracksList;

    public MusicRecycleViewAdapter(Context context, List<TopTracks> topTracksList) {
        this.context = context;
        this.topTracksList = topTracksList;
    }

    @Override
    public MusicRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.musiclibrarysearch, parent, false);
        return new MusicRecycleViewAdapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(final MusicRecycleViewAdapter.ViewHolder holder, final int position) {

        TopTracks topTracks = topTracksList.get(position);
        String posterLink = topTracks.getImg();
        holder.artistname.setText(topTracks.getArtistName());
        holder.trackname.setText(topTracks.getTrack());
        Picasso.with(context).load(posterLink).placeholder(android.R.drawable.ic_btn_speak_now).into(holder.poster);




    }

    @Override
    public int getItemCount() {
        return topTracksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView artistname;
        ImageButton poster;
        TextView trackname;
        ImageButton addPlaylist;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            artistname = (TextView) itemView.findViewById(R.id.ArtistName1);
            poster = (ImageButton) itemView.findViewById(R.id.MusicImage);
            trackname = (TextView) itemView.findViewById(R.id.MusicName1);
            addPlaylist = (ImageButton) itemView.findViewById(R.id.AddPlaylist);
            addPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TopTracks topTracks = topTracksList.get(getAdapterPosition());
                    Intent intent = new Intent(context,Playlist.class);
                    intent.putExtra("toptrack" ,topTracks);
                    context.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TopTracks topTracks = topTracksList.get(getAdapterPosition());
                    Intent intent = new Intent(context,MusicPlayer.class);
                    intent.putExtra("toptrack" ,topTracks);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
