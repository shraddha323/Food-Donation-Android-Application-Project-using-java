package com.example.food_donation_app;


import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mMob;
    private String mKey;

    public Upload(){
        //needed empty constr
    }
    public  Upload(String ImageUrl,String name,String mob){
        if(name.trim().equals(""))
        {
            name="No Name";
        }
        if(mob.trim().equals(""))
        {
            mob="No mob";
        }
        mName=name;
        mImageUrl=ImageUrl;
        mMob=mob;
    }
    public String getName(){
        return mName;
    }
    public void setName(String name){
        mName=name;
    }
    public String getMob(){
        return mMob;
    }
    public void setMob(String mob){
        mMob=mob;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUrl(String ImageUrl){
        mImageUrl=ImageUrl;
    }
    @Exclude
    public String getKey()
    {
        return mKey;
    }
    @Exclude
    public void setKey(String key)
    {
        mKey=key;
    }

}
