package com.jikexueyuan.chatrobot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class TextAdapter extends BaseAdapter{

    private List<RobotData> robotDatas;
    private Context context;
    private RelativeLayout relativeLayout;

    public TextAdapter(List<RobotData> robotDatas,Context context){
        this.robotDatas = robotDatas;
        this.context = context;
    }
    @Override
    public int getCount() {
        return robotDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return robotDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (robotDatas.get(position).getFlag() == RobotData.RECEIVER){
            relativeLayout = (RelativeLayout) inflater.inflate(R.layout.left_item,null);
        }
        if (robotDatas.get(position).getFlag() == RobotData.SEND){
            relativeLayout = (RelativeLayout) inflater.inflate(R.layout.right_item,null);
        }
        TextView tv = (TextView) relativeLayout.findViewById(R.id.tv);
        TextView time = (TextView) relativeLayout.findViewById(R.id.time);
        tv.setText(robotDatas.get(position).getContent());
        time.setText(robotDatas.get(position).getTime());
        return relativeLayout;
    }
}
