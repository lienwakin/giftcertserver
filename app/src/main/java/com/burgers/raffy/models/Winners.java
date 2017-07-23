package com.burgers.raffy.models;

/**
 * Created by Neil on 7/21/2017.
 */

public class Winners {
    private String key;
    private String name;
    private boolean claimed;
    private String amount;
    public Winners(){

    }
    public Winners(String name, String key, String amount, boolean claimed){
        this.key = key;
        this.name = name;
        this.claimed = claimed;
        this.amount = amount;
    }

    public String getKey(){
        return this.key;
    }

    public String getName(){
        return this.name;
    }

    public boolean isClaimed(){
        return this.claimed;
    }

    public String getAmount(){
        return this.amount;
    }

    public void setClaimed(boolean isClaimed){
        this.claimed = isClaimed;
    }
}
