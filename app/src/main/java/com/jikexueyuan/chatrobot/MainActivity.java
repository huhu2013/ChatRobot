package com.jikexueyuan.chatrobot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener{

    private List<RobotData> robotDatas;
    private ListView lv;
    private EditText sendText;
    private Button send_btn;
    private TextAdapter textAdapter;
    private String[] welcomeArray;
    private double currentTime;
    private double oldTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        robotDatas = new ArrayList<>();
        textAdapter = new TextAdapter(robotDatas,this);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(textAdapter);
        RobotData robotData = new RobotData(getRandomWelcomeTips(),RobotData.RECEIVER,getTime());
        robotDatas.add(robotData);
        sendText = (EditText) findViewById(R.id.sendText);
        send_btn = (Button) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            Bundle  bundle = new Bundle();
            String str = ParseTuLing.parseData(sendText.getText().toString());
            bundle.putString("data",str);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String str = bundle.getString("data");
            RobotData robotData = new RobotData(str,RobotData.RECEIVER,getTime());
            robotDatas.add(robotData);
            textAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View v) {
        String str = sendText.getText().toString();
        sendText.setText("");
        RobotData robotData = new RobotData(str,RobotData.SEND,getTime());
        robotDatas.add(robotData);
        if (robotDatas.size() > 30){
            for (int i = 0;i < robotDatas.size();i++){
                robotDatas.remove(i);
            }
        }
        textAdapter.notifyDataSetChanged();
        new Thread(runnable).start();
    }

    private String getRandomWelcomeTips(){
        String welcomeTip;
        welcomeArray = this.getResources().getStringArray(R.array.welcome_tips);
        Random random = new Random();
        int index = random.nextInt(welcomeArray.length);
        welcomeTip = welcomeArray[index];
        return welcomeTip;
    }

    public String getTime(){

        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日 hh:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTime >= 10*1000){
            oldTime = currentTime;
            return str;
        }else{
            return "";
        }
    }
}
