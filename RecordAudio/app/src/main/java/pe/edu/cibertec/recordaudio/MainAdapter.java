package pe.edu.cibertec.recordaudio;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static pe.edu.cibertec.recordaudio.MainActivity.LOG_TAG;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    MediaPlayer player = null;
    String fileName = null;
    private Context context;

    ArrayList<String> mAudios;

    public MainAdapter(ArrayList<String> mAudios) {
        this.mAudios = mAudios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        viewHolder.mNombre.setText(mAudios.get(i));
//        fileName = viewHolder.mNombre.getText().toString();

        viewHolder.mNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player = new MediaPlayer();
                try {
                    player.setDataSource(mAudios.get(i).toString());
                    player.prepare();
                    player.start();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                stopPlaying();
////                btPlay.setText("Play");
////                playing = !playing;
//            }
//        });
//
//        try {
//            player.setDataSource(fileName);
//            player.prepare();
//            player.start();
//
//
//        } catch (IOException e) {
//            //e.printStackTrace();
//            Log.e(LOG_TAG,e.toString());
//        }

    }

    @Override
    public int getItemCount() {
        return mAudios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombre = itemView.findViewById(R.id.full_name);
        }
    }
//
//    private void stopPlaying() {
//        player.stop();
//        player.release();
//        player = null;
//    }


}
