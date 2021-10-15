package com.algokelvin.register.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataRegister implements Parcelable {
    private  String fullName = "Masukkan Nama Lengkap";
    private  String nickName = "Masukkan Nama Panggilan";
    private  String placeBirth = "Masukkan Tempat Lahir";
    private  String address = "Masukkan Alamat Rumah";
    private  String city = "Masukkan Nama Kota";
    private  String noHP = "Masukkan No. HP";
    private  String job = "Masukkan Pekerjaan";
    private  String hobby = "Masukkan Hobby Anda";
    private  String favoritePlace = "Masukkan Tempat Favorit Anda";

    public DataRegister(Parcel in) {
        fullName = in.readString();
        nickName = in.readString();
        placeBirth = in.readString();
        address = in.readString();
        city = in.readString();
        noHP = in.readString();
        job = in.readString();
        hobby = in.readString();
        favoritePlace = in.readString();
    }

    public DataRegister() {}

    public static final Creator<DataRegister> CREATOR = new Creator<DataRegister>() {
        @Override
        public DataRegister createFromParcel(Parcel in) {
            return new DataRegister(in);
        }

        @Override
        public DataRegister[] newArray(int size) {
            return new DataRegister[size];
        }
    };

    public  String getFullName() {
        return fullName;
    }

    public  void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public  String getNickName() {
        return nickName;
    }

    public  void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public  String getPlaceBirth() {
        return placeBirth;
    }

    public  void setPlaceBirth(String placeBirth) {
        this.placeBirth = placeBirth;
    }

    public  String getAddress() {
        return address;
    }

    public  void setAddress(String address) {
        this.address = address;
    }

    public  String getCity() {
        return city;
    }

    public  void setCity(String city) {
        this.city = city;
    }

    public  String getNoHP() {
        return noHP;
    }

    public  void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public  String getJob() {
        return job;
    }

    public  void setJob(String job) {
        this.job = job;
    }

    public  String getHobby() {
        return hobby;
    }

    public  void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public  String getFavoritePlace() {
        return favoritePlace;
    }

    public  void setFavoritePlace(String favoritePlace) {
        this.favoritePlace = favoritePlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(nickName);
        dest.writeString(placeBirth);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(noHP);
        dest.writeString(job);
        dest.writeString(hobby);
        dest.writeString(favoritePlace);
    }
}
