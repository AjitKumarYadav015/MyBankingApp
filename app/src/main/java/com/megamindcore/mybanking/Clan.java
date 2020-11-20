package com.megamindcore.mybanking;

public class Clan {

    private String mn;
    private String mm;
    private String mKey;

    public Clan() {
        //empty constructor needed
    }

    public Clan(String n,String m,String key) {
        mn = n;
        mm = m;
        mKey=key;
    }

    public String getn() {
        return mn;
    }

    public void setn(String n) {
        mn = n;
    }

    public String getm() {
        return mm;
    }

    public void setm(String m) {
        mm = m;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}



////////////////////////////////////////////////////////////////////////////////////////////////////
/*

done
 */
