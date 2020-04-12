package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository("UserDaoJdbcImpl") //DB接続用クラスのアノテーション。
public class UserDaoJdbcImpl implements UserDao {

    //DB接続のため、JdbcTemplateをDIする。
    @Autowired
    JdbcTemplate jdbc;

    // Userテーブルの全データを取得.
    @Override
    public List<User> selectMany() throws DataAccessException {

        // queryForListメソッドでSQLを発行。queryForListの場合、結果はMap型をList化した形式で結果が返ってくる。
        // MapはKeyとValueがセットになった配列型オブジェクトのこと。
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user");

        // Userの型に変換するため、格納用のArrayListのインスタンスを生成。
        List<User> userList = new ArrayList<>();

        for (Map<String, Object> map : getList) {

            User user = new User();
            // map.getでKeyに基づいたValueを取得。それをUserクラスのSetterに当てはめる。
            // 今回の場合はDBのuser_idというカラムから紐づくレコードを取得している。
            user.setUserId((int) map.get("user_id"));
            user.setPassword((String) map.get("password"));
            user.setUserName((String) map.get("user_name"));

            // userList(List型の配列)にUserインスタンスを追加。
            userList.add(user);
        }
        // ListをServiceに返す。
        return userList;
    }


}
