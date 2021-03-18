package com.xiaov.seckill.util;

import com.xiaov.seckill.entity.MiaoshaUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaov
 * @since 2021-03-08 20:43
 */
public class UserUtil {

    public static void createUser(int count) throws Exception {
        List<MiaoshaUser> users = new ArrayList<MiaoshaUser>(count);

        //生成用户
        for (int i = 0; i < count; i++) {
            MiaoshaUser user = new MiaoshaUser();
            user.setId(13000000000L+i);
            user.setLoginCount(1);
            user.setNickname(String.valueOf(13000000000L+i));
            user.setRegisterDate(new Date());
            user.setSalt("2019210438");
            user.setPassword(MD5Util.inputPassToDBPass("123456",user.getSalt()));
            users.add(user);
        }
        System.out.println("create user finished");

        //插入数据库
        Connection connection = DBUtil.getConn();
        String sql = "insert into miaosha_user(login_count, nickname, register_date, salt, password, id)values(?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        for (MiaoshaUser user : users) {
            pstmt.setInt(1, user.getLoginCount());
            pstmt.setString(2, user.getNickname());
            pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
            pstmt.setString(4, user.getSalt());
            pstmt.setString(5, user.getPassword());
            pstmt.setLong(6, user.getId());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        pstmt.close();
        connection.close();
        System.out.println("insert to db finished");

        //登录，生成token
        String urlString = "http://localhost:8888/seckill/login/create_token";
        File file = new File("/Users/xiaov/Downloads/tokens.txt");
        if(file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (MiaoshaUser user : users) {
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile="+user.getNickname()+"&password="+MD5Util.inputPassToFormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0 ,len);
            }
            inputStream.close();
            bout.close();
            String response = bout.toString();
            System.out.println("create token : " + user.getId());

            String row = user.getNickname()+","+response;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }

        raf.close();

        System.out.println("over");
    }

    public static void main(String[] args) throws Exception {
        createUser(10000);
    }
}
