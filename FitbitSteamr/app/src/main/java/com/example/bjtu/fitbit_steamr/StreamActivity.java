package com.example.bjtu.fitbit_steamr;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StreamActivity extends AppCompatActivity implements View.OnClickListener {

    static Toast toast = null;
    String token = null;
    String streamID = null;
    String key = null;

    ImageView img1 = null;
    ImageView img2 = null;
    ImageView img3 = null;

    Animation animation = null;
    AlarmManager am = null;

    TextView txt_streamID;
    TextView txt_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        Bundle bundle = this.getIntent().getExtras();
        token = bundle.getString("token");

        Button btn_stream = this.findViewById(R.id.btn_stream);
        Button btn_stop = this.findViewById(R.id.btn_stop);

        txt_streamID = this.findViewById(R.id.txt_streamID);
        txt_key = this.findViewById(R.id.txt_key);

        img1 = this.findViewById(R.id.img1);
        img2 = this.findViewById(R.id.img2);
        img3 = this.findViewById(R.id.img3);

        animation = AnimationUtils.loadAnimation(StreamActivity.this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);

        btn_stop.setOnClickListener(this);
        btn_stream.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_stream:
                streamID = txt_streamID.getText().toString();
                key = txt_key.getText().toString();
                if (streamID.equals("")) {
                    if(toast!=null){
                        toast.setText("Error:Stream ID is null!");
                    }else{
                        toast= Toast.makeText(StreamActivity.this, "Error:Stream ID is null!", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                } else if (key.equals("")) {
                    if(toast!=null){
                        toast.setText("Error:Key is null!");
                    }else{
                        toast= Toast.makeText(StreamActivity.this, "Error:Key is null!", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                } else {
                    Intent startIntent = new Intent(this,TaskService.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("key", key);
                    bundle.putString("token", token);
                    bundle.putString("streamID", streamID);

                    startIntent.putExtras(bundle);

                    Toast.makeText(StreamActivity.this,"Start streaming!",Toast.LENGTH_SHORT).show();
                    img1.startAnimation(animation);
                    img2.startAnimation(animation);
                    img3.startAnimation(animation);
                    System.out.println("Start Task Service!"+startService(startIntent));

                }

                break;
            case R.id.btn_stop:
                Intent stopIntent = new Intent(this,TaskService.class);
                Toast.makeText(StreamActivity.this,"Stop Streaming!",Toast.LENGTH_SHORT).show();
                stopService(stopIntent);
                img1.clearAnimation();
                img2.clearAnimation();
                img3.clearAnimation();
                break;
        }
    }

}
