package com.addapp.izum.Structure;

/**
 * Created by Азат on 21.10.2015.
 * Используется в ModelProfile как значение массива для списка
 * в компоненте CommonMultiChangeList
 */
public class ChoiceListItem {
    private int id;
    private String text;
    private boolean check;

    public ChoiceListItem(int id, boolean check, String text) {
        this.id = id;
        this.check = check;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
