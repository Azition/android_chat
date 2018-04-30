package com.addapp.izum.Structure;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Азат on 09.10.2015.
 */
public class User {

    private String name;
    private int year;
    private int month;
    private int day;
    private String gender;
    private String city;
    private String region;
    private String country;
    private String userInfo;
    private String avatar;
    private int likeCount;
    private int balance;
    private String status;
    private String family_status;
    private String looking_for;
    private ArrayList<String> interest = new ArrayList<>();
    private ArrayList<String> target_acquaintance = new ArrayList<>();
    private ArrayList<String> photo = new ArrayList<>();

    private User(Builder builder){
        name = builder.name;
        year = builder.year;
        month = builder.month;
        day = builder.day;
        gender = builder.gender;
        city = builder.city;
        region = builder.region;
        country = builder.country;
        userInfo = builder.userInfo;
        avatar = builder.avatar;
        status = builder.status;
        family_status = builder.family_status;
        looking_for = builder.looking_for;
        interest = builder.interest;
        target_acquaintance = builder.target_acquaintance;
    }

    public static class Builder{
        private String name;
        private int year;
        private int month;
        private int day;
        private String gender;
        private String city;
        private String region;
        private String country;
        private String userInfo;
        private String avatar;
        private int likeCount;
        private int balance;
        private String status;
        private String family_status;
        private String looking_for;
        private ArrayList<String> interest = new ArrayList<>();
        private ArrayList<String> target_acquaintance = new ArrayList<>();
        private ArrayList<String> photo = new ArrayList<>();

        public Builder(String name){
            this.name = name;
        }

        public Builder setBirthday(String birthday){
            if (birthday.equals("")){
                this.year = this.month = this.day = 0;
                return this;
            }
            for (int i = 0, k = 0, cursorPos = 0; i < birthday.length(); ++i){
                if (birthday.charAt(i) == '-' || birthday.length() == i+1){
                    switch (k){
                        case 0:
                            year = Integer.parseInt(birthday.substring(0, i));
                            k++;
                            cursorPos = i+1;
                            break;
                        case 1:
                            month = Integer.parseInt(birthday.substring(cursorPos, i));
                            k++;
                            cursorPos = i+1;
                            break;
                        case 2:
                            day = Integer.parseInt(birthday.substring(cursorPos, i+1));
                            break;
                    }
                }
            }
            return this;
        }

        public Builder setGender(String gender){
            this.gender = gender;
            return this;
        }

        public Builder setCity(String city){
            this.city = city;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setUserInfo(String userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setFamilyStatus(String family_status) {
            this.family_status = family_status;
            return this;
        }

        public Builder setLookingFor(String looking_for) {
            this.looking_for = looking_for;
            return this;
        }

        public Builder setTargets(JSONArray array){
            target_acquaintance.clear();
            for(int i = 0; i < array.length(); i++)
                try {
                    target_acquaintance.add(array.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return this;
        }

        public Builder setInterest(JSONArray array){
            interest.clear();
            for(int i = 0; i < array.length(); i++)
                try {
                    interest.add(array.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public String getStatus() {
        return status;
    }

    public String getFamilyStatus() {
        return family_status;
    }

    public String getLookingFor() {
        return looking_for;
    }

    public String getInterest() {
        String text = "";
        for(int i = 0; i < interest.size(); i++){
            if (i == interest.size() - 1) {
                text += interest.get(i);
                continue;
            }
            text += interest.get(i) + "\n";
        }
        return text;
    }

    public String getTarget(){
        String text = "";
        for(int i = 0; i < target_acquaintance.size(); i++){
            if (i == target_acquaintance.size() - 1) {
                text += target_acquaintance.get(i);
                continue;
            }
            text += target_acquaintance.get(i) + "\n";
        }
        return text;
    }

    public ArrayList<String> getArrayInterest(){
        return interest;
    }

    public ArrayList<String> getArrayTarget(){
        return target_acquaintance;
    }

    public int getBirthYear() {
        return year;
    }

    public int getBirthMonth() {
        return month;
    }

    public int getBirthDay() {
        return day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBirthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public void setFamily_status(String family_status) {
        this.family_status = family_status;
    }

    public void setLookingFor(String looking_for) {
        this.looking_for = looking_for;
    }

    @Override
    public String toString() {
        String interest = "";
        for (String name : this.interest){
            interest += name + " ";
        }
        String target = "";
        for (String name : this.target_acquaintance){
            target += name + " ";
        }
        String str = "Name: " + name + "\nAvatar: " + avatar +
                "\nBirthday: " + year + "-" + month +
                "-" + day + "\nGender: " + gender + "\nCity: " + city +
                "\nRegion: " + region + "\nCountry: " + country +
                "\nInfo: " + userInfo + "\nStatus: " + status +
                "\nFind: " + looking_for + "\nFamilyStatus: " + family_status +
                "\nInterest: " + interest + "\nTarget: " + target;
        return str;
    }
}
