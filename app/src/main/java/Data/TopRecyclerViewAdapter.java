package Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdsaam.finalmusicplayer.MusicPlayer;
import com.example.abdsaam.finalmusicplayer.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import Model.TopTracks;
import Util.Constants;

/**
 * Created by abdsaam on 17/01/2018.
 */

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<TopTracks> topTracksList;

    public TopRecyclerViewAdapter(Context context, List<TopTracks> topTracksList) {
        this.context = context;
        this.topTracksList = topTracksList;
    }

    @Override
    public TopRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topplaylist, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(final TopRecyclerViewAdapter.ViewHolder holder, final int position) {

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

       public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            artistname = (TextView) itemView.findViewById(R.id.ArtistName);
            poster = (ImageButton) itemView.findViewById(R.id.albumimage);
            trackname = (TextView) itemView.findViewById(R.id.TrackName);

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
