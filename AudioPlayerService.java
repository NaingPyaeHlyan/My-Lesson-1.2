package mm.com.fairway.mylesson12;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AudioPlayerService extends Service {
    MediaPlayer mediaPlayer;
    final String TAG = "PlayerService";
    public AudioPlayerService(){
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.heartandsoul);
        Log.d(TAG,"Service Created");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        mediaPlayer.start();
        Log.d(TAG,"Service Start");
        return START_STICKY;
    }
   // @Override
    public void onDestory(){
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
    public void pause(){
        mediaPlayer.pause();
        Log.d(TAG,"Player Paused");
    }
    @Override
    public boolean onUnbind(Intent intent){
        return super.onUnbind(intent);
    }
    public class MyBinder extends Binder{
        AudioPlayerService getService(){
            return AudioPlayerService.this;
        }
    }
    private final IBinder myBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent){
        //TODO: Return the communication channel to the service.
        return myBinder;
    }
}
