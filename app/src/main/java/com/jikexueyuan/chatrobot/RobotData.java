package com.jikexueyuan.chatrobot;



public class RobotData {

    private String content;
    public static final int SEND = 1;
    public static final int RECEIVER = 2;
    private int flag;
    private String time;

    public RobotData(String content,int flag,String time){
        setContent(content);
        setFlag(flag);
        setTime(time);
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
