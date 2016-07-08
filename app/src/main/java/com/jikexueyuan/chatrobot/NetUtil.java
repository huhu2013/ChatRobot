package com.jikexueyuan.chatrobot;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 网络连接工具类
 */
public class NetUtil {

    /**
     * 如果没有请求参数需要传递，就用get方法
     *
     * @param urlStr 网络请求地址
     * @return 返回的数据
     */
    public static String get(String urlStr) {
        return post(urlStr, null);
    }

    /**
     * 如果有请求参数需要传递，就用post方法,如果参数为null则相当于get方法
     *
     * @param urlStr 网络请求地址
     * @param param  请求参数键值对
     * @return 返回的数据
     */
    public static String post(String urlStr, Map param) {
        //创建一个HttpURLConnection连接对象
        HttpURLConnection connection = null;
        try {
            //得到URL对象
            URL url = new URL(urlStr);
            //打开HttpURLConnection连接
            connection = (HttpURLConnection) url.openConnection();
            //创建StringBuffer对象
            StringBuffer sb;
            if (param != null) {//如果请求参数不为空（即是POST方法）
                //得到StringBuffer实例
                sb = new StringBuffer();
                //默认为false,如果用POST方法，则需要设置为true
                connection.setDoOutput(true);
                //默认为GET方法，这里设置为POST方法
                connection.setRequestMethod("POST");
                //得到一个输出流对象
                OutputStream out = connection.getOutputStream();
                //封装该输出流对象
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                //将参数封装成键值对的形式
                for (Object object : param.keySet()) {
                    sb.append(object).append("=").append(param.get(object)).append("&");
                }
                //将参数写入
                writer.write(sb.deleteCharAt(sb.toString().length() - 1).toString());
                //关闭输出流
                writer.close();
                //置空StringBuffer
                sb = null;
            }
            //建立HttpURLConnection连接
            connection.connect();
            //重新得到StringBuffer实例
            sb = new StringBuffer();
            //获取连接状态码
            int recode = connection.getResponseCode();
            //声明一个输入流
            BufferedReader reader;
            if (recode == 200) {//如果连接成功
                //获取输入流
                InputStream in = connection.getInputStream();
                //封装输入流
                reader = new BufferedReader(new InputStreamReader(in));
                //声明一个行串
                String str;
                //重新得到StringBuffer实例
                sb = new StringBuffer();
                //读取每一行的数据
                while ((str = reader.readLine()) != null) {
                    //将每行数据添至StringBuffer中
                    sb.append(str);
                }
                //关闭输入流
                reader.close();
                //如果没有数据就返回为空
                if (sb.toString().length() == 0) {
                    return null;
                }
                //返回读取到的数据
                return sb.toString().substring(0, sb.toString().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;//如果出现异常就返回为空
        } finally {
            if (connection != null)//如果连接不为空
                //关闭连接
                connection.disconnect();
        }
        //没有数据直接返回空
        return null;
    }
}
