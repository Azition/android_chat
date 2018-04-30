package com.addapp.izum.Structure;

/**
 * Created by Азат on 20.10.2015.
 * Используется в адапторе редактирования профиля как единица списка.
 */
public class ProfileEditListItem {
    private String header;
    private String info;
    private DateItem date;

    public ProfileEditListItem(String header, String info) {
        this.header = header;
        this.info = info;
    }

    public ProfileEditListItem(String header, int year, int month, int day){
        this.header = header;
        this.date = new DateItem(year, month, day);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DateItem getDate(){
        return date;
    }

    public void setDate(int year, int month, int day){
        this.date = new DateItem(year, month, day);
    }

    public class DateItem{
        private int year;
        private int month;
        private int day;

        public DateItem(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        @Override
        public String toString() {
            return day + "." + month + "." + year;
        }
    }
}
