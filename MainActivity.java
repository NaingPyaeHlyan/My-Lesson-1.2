package mm.com.fairway.mylesson12;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    AudioPlayerService myServices;
    private boolean isBound;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (ImageButton)findViewById(R.id.button1);
        isBound=false;

        TextView textView = (TextView)findViewById(R.id.marqueeText);
        textView.setSelected(true);
    }

    public void playAudio(View view) {
        Intent objIntent = new Intent(this, AudioPlayerService.class);
        if(!isBound){
            bindService(objIntent, myConnection, Context.BIND_AUTO_CREATE);
            isBound=true;
            //btn.setBackgroundResource(R.drawable.pause);//setText("Pause");
            btn.setImageResource(R.drawable.pause);
            startService(objIntent);
        }
        else
        {
            myServices.pause();
            btn.setImageResource(R.drawable.play);
            isBound=false;
            unbindService(myConnection);
        }
    }

    public void stopAudio(View view) {
        Intent objIntent = new Intent(this, AudioPlayerService.class);
        if (isBound) {
            isBound = false;
            unbindService(myConnection);
            stopService(objIntent);
        } else

            stopService(objIntent);
        btn.setImageResource(R.drawable.play);
    }
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myServices = ((AudioPlayerService.MyBinder)iBinder).getService();
            isBound =true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(isBound){
            //Disconnect from an application service.
            isBound=false;
        }
    }
}
