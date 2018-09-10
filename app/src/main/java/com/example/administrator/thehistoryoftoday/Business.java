package com.example.administrator.thehistoryoftoday;

/**
 * Created by Administrator on 2018/5/18/018.
 */

public class Business {

    private String Title;
    private String Des;
    private boolean Col;

    public Business(String title,String des){
        this.Title=title;
        this.Des=des;
    }

    public String getTitle(){
        return Title;
    }

    public String getDes(){
        return Des;
    }

    public boolean getCol(){
        return Col;
    }
}
