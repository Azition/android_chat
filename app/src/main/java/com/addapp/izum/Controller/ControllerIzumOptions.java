package com.addapp.izum.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;

import com.addapp.izum.Adapter.AdapterOptionList;
import com.addapp.izum.CustomViewComponents.CommonEditTextDialog;
import com.addapp.izum.Main;
import com.addapp.izum.Model.ModelIzumOptions;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.View.ViewIzumOptions;

/**
 * Created by ILDAR on 16.09.2015.
 */
public class ControllerIzumOptions {

    private static final String PREFS_NAME = "TOKEN";
    
    private ModelIzumOptions mOptions;
    private ViewIzumOptions vOptions;
    private AdapterOptionList adapterOptionList;

    private MainUserData userData = MainUserData.getInstance();

    private CommonEditTextDialog commonEditTextDialog;

    public ControllerIzumOptions(ModelIzumOptions mOptions, ViewIzumOptions vOptions) {
        this.mOptions = mOptions;
        this.vOptions = vOptions;
    
        bindListener();
    }

    private void bindListener() {
        adapterOptionList = new AdapterOptionList(vOptions.getContext());
        vOptions.getOptionList().setAdapter(adapterOptionList);

        vOptions.getOptionList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 1:
                        commonEditTextDialog = new CommonEditTextDialog(vOptions.getContext(), new CommonEditTextDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterOptionList.setNickName(text);
                                adapterOptionList.notifyDataSetChanged();
                            }
                        });
                        commonEditTextDialog.setTitleText("Ваше имя");
                        commonEditTextDialog.setHintText("Введите имя...");
                        commonEditTextDialog
                                .setMainText(adapterOptionList.getNickName());
                        commonEditTextDialog.show();
                        break;

                    case 10:
                        deleteToken();
                        ((Main)vOptions.getContext()).close();
                        break;
                }
            }
        });
    }

    private void deleteToken(){
        userData.setToken("");
        SharedPreferences settings =
                vOptions.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_NAME, "");
        editor.commit();
    }
}
