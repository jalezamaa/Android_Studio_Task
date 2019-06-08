package pe.edu.cibertec.recordaudio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btRecord,btPlay;

    MediaRecorder recorder = null;
    MediaPlayer player = null;
    String fileName = null;

    String currentPathImage; //Ruta absoluta de Imagen

    ArrayList<String> mAudios;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mlayoutManager;
    RecyclerView.Adapter mAdapter;

    boolean recording = false;
    boolean playing = false;
    boolean  permissionGranted = false;

    static  final int REQUEST_RECORD_AUDIO = 1;
    static  final String LOG_TAG = "Audio Recorder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btRecord = findViewById(R.id.btRecord);
        btPlay = findViewById(R.id.btPlay);

        mAudios = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);


//        Random r = new Random();
//        int numero = r.nextInt(80);
//
//        fileName = getExternalCacheDir().getAbsolutePath();
//        fileName = fileName +"/"+numero+"audiorecorder.3gp";

        checkPermissionRecord();

        //prueba

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        String imageFileName = timeStamp + "_";
//
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        File image = null;
//
//        try {
//            image = File.createTempFile(
//                    imageFileName, //nombre
//                    ".mp3", //extencion
//                    storageDir //Directorio
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        currentPathImage = image.getAbsolutePath();//
//        fileName = currentPathImage;



        //fin prueba

        btRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!recording)
                {
                    startRecording();
                    btRecord.setText("Detener");
                }
                else
                {
                    stopRecording();
                    btRecord.setText("Grabar");
                }
                recording = !recording;
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playing)
                {
                    startPlaying();
                    btPlay.setText("Detener");
                }
                else
                {
                    stopPlaying();
                    btPlay.setText("Play");
                }
                playing =!playing;
            }
        });
    }

    private void stopPlaying() {
        player.stop();
        player.release();
        player = null;
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;

//        Random r = new Random();
//        int numero = r.nextInt(80);
//
//        fileName = getExternalCacheDir().getAbsolutePath();
//        fileName = fileName +"/ "+ numero +"audiorecorder.3gp";

        mAudios.add(fileName);
        mAdapter = new MainAdapter(mAudios);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void startPlaying() {
        player = new MediaPlayer();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
                btPlay.setText("Play");
                playing = !playing;
            }
        });

        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();


        } catch (IOException e) {
            //e.printStackTrace();
            Log.e(LOG_TAG,e.toString());
        }

        btRecord.setText("Grabar");
    }

    private void checkPermissionRecord() {

        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.RECORD_AUDIO},REQUEST_RECORD_AUDIO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_RECORD_AUDIO) {
            permissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                startRecording();
//            }
        }
        if(!permissionGranted)
        {
            finish();
        }
    }

    private void startRecording() {

        Random r = new Random();
        int numero = r.nextInt(80);

//        fileName = getExternalCacheDir().getAbsolutePath();
//        fileName = fileName +"/"+numero+"audiorecorder.3gp";


        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    "Audio"+numero, //nombre
                    ".3gp", //extencion
                    storageDir //Directorio
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPathImage = image.getAbsolutePath();
        fileName = currentPathImage;

        ////////////////////////////////////////////////////////////////////////


        //Genera una instancia para grabar
        recorder = new MediaRecorder();

        //Indicar la fuente de medio p microfono
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        //Indicar formato de salida
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        //Indicar el archivo donde se guardara el audio
        recorder.setOutputFile(fileName);

        //Decodear
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {

            recorder.prepare();

        } catch (IOException e) {

            Log.e(LOG_TAG,e.toString());
            //e.printStackTrace();
        }

        recorder.start();
        btRecord.setText("Grabando");
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(recorder!=null)
        {
            recorder.release();
            recorder = null;

        }
        if(player!= null)
        {
            player.release();
            player = null;
        }
    }
}
