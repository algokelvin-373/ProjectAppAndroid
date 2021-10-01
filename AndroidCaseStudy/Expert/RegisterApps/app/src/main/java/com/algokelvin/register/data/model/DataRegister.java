package com.algokelvin.register.data.model;

public class DataRegister {
    private static String fullName = "Masukkan Nama Lengkap";
    private static String nickName = "Masukkan Nama Panggilan";
    private static String placeBirth = "Masukkan Tempat Lahir";
    private static String address = "Masukkan Alamat Rumah";
    private static String city = "Masukkan Nama Kota";
    private static String noHP = "Masukkan No. HP";
    private static String job = "Masukkan Pekerjaan";
    private static String hobby = "Masukkan Hobby Anda";
    private static String favoritePlace = "Masukkan Tempat Favorit Anda";

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        DataRegister.fullName = fullName;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        DataRegister.nickName = nickName;
    }

    public static String getPlaceBirth() {
        return placeBirth;
    }

    public static void setPlaceBirth(String placeBirth) {
        DataRegister.placeBirth = placeBirth;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        DataRegister.address = address;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        DataRegister.city = city;
    }

    public static String getNoHP() {
        return noHP;
    }

    public static void setNoHP(String noHP) {
        DataRegister.noHP = noHP;
    }

    public static String getJob() {
        return job;
    }

    public static void setJob(String job) {
        DataRegister.job = job;
    }

    public static String getHobby() {
        return hobby;
    }

    public static void setHobby(String hobby) {
        DataRegister.hobby = hobby;
    }

    public static String getFavoritePlace() {
        return favoritePlace;
    }

    public static void setFavoritePlace(String favoritePlace) {
        DataRegister.favoritePlace = favoritePlace;
    }
}
