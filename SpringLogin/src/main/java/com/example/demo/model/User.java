package com.example.demo.model;

import lombok.Data;

@Data //lombokを使用しているのでアノテーション一つでGetter,Setterを作成できる。
public class User {

    private int userId;
    private String password;
    private String userName;

}