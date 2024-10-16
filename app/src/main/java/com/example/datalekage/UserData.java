package com.example.datalekage;

public class UserData {

    public String uname,uemail,uphone,ukey,imgurl;

    public UserData() {
    }

    public UserData(String uname,  String uemail, String uphone,  String ukey, String imgurl) {
        this.uname = uname;
        this.uemail = uemail;
        this.uphone = uphone;
        this.ukey = ukey;
        this.imgurl = imgurl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
