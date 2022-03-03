package algokelvin.android07.intentparcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    String name;
    String address;
    String job;

    protected Person(Parcel in) {
        name = in.readString();
        address = in.readString();
        job = in.readString();
    }

    public Person(String name, String address, String job) {
        this.name = name;
        this.address = address;
        this.job = job;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(job);
    }
}
