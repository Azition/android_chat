package com.addapp.izum.Structure;

/**
 * Created by Азат on 27.10.2015.
 */
public class PrivateItem {

    public PrivateItem(String iconUrl,
                       String message,
                       String name,
                       String id) {
        this.iconUrl = iconUrl;
        this.message = message;
        this.name = name;
        this.id = id;
    }
    private String id;
    private String iconUrl;
    private String name;
    private String age;
    private String message;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
