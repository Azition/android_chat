package com.addapp.izum.Controller;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.addapp.izum.AbstractClasses.CommonModel;
import com.addapp.izum.Adapter.AdapterListEditProfile;
import com.addapp.izum.Adapter.AdapterProfileGridButton;
import com.addapp.izum.Adapter.AdapterProfileInfoList;
import com.addapp.izum.Adapter.AdapterProfilePhotoPager;
import com.addapp.izum.CustomViewComponents.CommonDateEditDialog;
import com.addapp.izum.CustomViewComponents.CommonEditTextDialog;
import com.addapp.izum.CustomViewComponents.CommonListViewDialog;
import com.addapp.izum.CustomViewComponents.CommonMultiChangeList;
import com.addapp.izum.Interface.OnCustomClickListener;
import com.addapp.izum.Model.ModelProfile;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SocketIO;
import com.addapp.izum.Structure.ChoiceListItem;
import com.addapp.izum.Structure.ProfileEditListItem;
import com.addapp.izum.Structure.User;
import com.addapp.izum.View.ViewProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ILDAR on 24.06.2015.
 */
public class ControllerProfile
        implements CommonModel.OnUpdateListener{
    private final String TAG = getClass().getSimpleName();

    private static final boolean DEBUG = true;

    private ModelProfile modelProfile;
    private ViewProfile viewProfile;

    private AdapterProfileInfoList profileInfoList;
    private AdapterListEditProfile adapterListEditProfile;
    private AdapterProfilePhotoPager adapterProfilePhotoPager;

    private CommonEditTextDialog commonEditTextDialog;
    private CommonListViewDialog commonListViewDialog;

    private SocketIO mSocket = SocketIO.getInstance();
    private MainUserData userData = MainUserData.getInstance();
    private Handler handler = new Handler();

    private OnCustomClickListener mListener;

    public ControllerProfile(ModelProfile modelProfile, ViewProfile viewProfile){
        this.modelProfile = modelProfile;
        this.viewProfile = viewProfile;
        modelProfile.setOnUpdateListener(this);
        modelProfile.setRenderListener(mRenderListener);

        profileInfoList = new AdapterProfileInfoList();
        adapterListEditProfile = new AdapterListEditProfile();
        adapterProfilePhotoPager = new AdapterProfilePhotoPager();
        adapterProfilePhotoPager.setProfile(viewProfile.getAttr());
        adapterProfilePhotoPager.setListener(onPickAction);
        viewProfile.setAdapterListEditProfile(adapterListEditProfile);
    }

    /*
    *       Привязка событии к компонентам
    */

    public void bindListener(){

        Log.e(TAG, "bindListener");

        viewProfile.getImageAddUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewProfile.getAttr() == ViewProfile.MY_PROFILE) {
                    viewProfile.initiatePopupWindow();
                }
            }
        });

        viewProfile.getStatusText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonEditTextDialog = new CommonEditTextDialog(viewProfile.getContext(), new CommonEditTextDialog.OnClickSaveListener() {
                    @Override
                    public void onClickSave(String text) {
                        viewProfile.getStatusText().setText(text);
                        mSocket.setStatus(text);
                    }
                });
                commonEditTextDialog.setTitleText("Ваш статус");
                commonEditTextDialog.setHintText("Чем вы занимаетесь?");
                commonEditTextDialog.show();
            }
        });
        viewProfile.getPhotoPager().setAdapter(adapterProfilePhotoPager);

/****************************************************************************************
        Временное отключение индикатора фоток
*****************************************************************************************/
//        viewProfile.getPageIndicator().setViewPager(viewProfile.getPhotoPager());
    /*    viewProfile.getGridButtons().setAdapter(new AdapterProfileGridButton(viewProfile.getAttr(),
                viewProfile.getGridButtonHeight()));*/
/****************************************************************************************
        Временное отключение слушателей событии фоток
 *****************************************************************************************/
/*        viewProfile.getGridPhoto().setAdapter(new AdapterProfilePhotoGrid());
        viewProfile.getGridPhoto().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewProfile.getPhotoPager().setCurrentItem(i);

                mSocket.getProfileData(userData.getId());

                viewProfile.getStatusText().setText(modelProfile.getUser().getStatus());
                Log.e(TAG, "user: " + modelProfile.getUser());
                profileInfoList.setUserInfo(modelProfile.getUser());
                profileInfoList.notifyDataSetChanged();
            }
        });         */

        viewProfile.getListInfo().setAdapter(profileInfoList);

        viewProfile.setUpdateListener(new ViewProfile.UpdateProfileListener() {

            User newUser = new User.Builder(userData.getUsername())
                    .build();

            @Override
            public void OnUpdateProfile() {
                if (DEBUG) Log.e("OnUpdateProfile", "Update profile");
                Log.e(TAG, newUser.toString());

                JSONObject json = new JSONObject();

                try {

                    json.put("userID", userData.getId());
                    json.put("token", userData.getToken());

                    /*********************************************************************
                     *   Проверка имени
                     **********************************************************************/
                    if (!newUser.getName().equals(userData.getUsername())) {
                        json.put("name", newUser.getName());
                    }

                    /*********************************************************************
                     *   Проверка даты рождения
                     **********************************************************************/
                    if (newUser.getBirthDay() != 0 ||
                            newUser.getBirthMonth() != 0 ||
                            newUser.getBirthYear() != 0){
                        String date = newUser.getBirthYear() +
                                "-" + newUser.getBirthMonth() +
                                "-" + newUser.getBirthDay();
                        json.put("birthday", date);
                    }

                    /*********************************************************************
                     *   Проверка изменения данных о пользователе
                     **********************************************************************/
                    if (newUser.getUserInfo() != null){
                        json.put("info", newUser.getUserInfo());
                    }

                    /*********************************************************************
                     *   Проверка региона
                     **********************************************************************/
                    if (newUser.getRegion() != null){
                        int regionID = 0;
                        for (ChoiceListItem item : modelProfile.getRegion()){
                            if (item.getText().equals(newUser.getRegion()))
                                regionID = item.getId();
                        }
                        if (regionID != 0) {
                            json.put("region", regionID);
                            if (newUser.getCity() == null)
                                json.put("city", modelProfile.getCity().get(0).getId());
                        }
                    }

                    /*********************************************************************
                     *   Проверка региона
                     **********************************************************************/
                    if (newUser.getCity() != null){
                        int cityID = 0;
                        for (ChoiceListItem item : modelProfile.getCity())
                            if (item.getText().equals(newUser.getCity()))
                                cityID = item.getId();
                        if (cityID != 0){
                            json.put("city", cityID);
                        }
                    }

                    /*********************************************************************
                     *   Проверка семейного статуса
                     **********************************************************************/
                    if(newUser.getFamilyStatus() != null){
                        json.put("familyStatus", newUser.getFamilyStatus());
                    }

                    /*********************************************************************
                     *   Проверка (не знаю как названить, пусть будет) lookingFor
                     **********************************************************************/
                    if(newUser.getLookingFor() != null){
                        int value = -1;
                        for (ChoiceListItem item : modelProfile.getFindGender())
                            if (item.getText().equals(newUser.getLookingFor()))
                                value = item.getId();
                        if (value != -1)
                            json.put("lookingfor", value);
                    }

                    /*********************************************************************
                     *   Проверка пола
                     **********************************************************************/
                    if(newUser.getGender() != null){
                        int value = -1;
                        for (ChoiceListItem item : modelProfile.getGender())
                            if (item.getText().equals(newUser.getGender()))
                                value = item.getId();
                        if (value != -1)
                            json.put("gender", value);
                    }

                    /*********************************************************************
                     *   Проверка выбраных целей
                     **********************************************************************/

                    ArrayList<ChoiceListItem> tmpArray = new ArrayList<ChoiceListItem>();
                    for(ChoiceListItem item : modelProfile.getTarget())
                        if(item.isCheck()) tmpArray.add(item);

                    if (modelProfile.getUser().getArrayTarget().size() ==
                            tmpArray.size()){
                        int count = 0;
                        JSONArray arr = new JSONArray();
                        for(String name : modelProfile.getUser().getArrayTarget())
                            for (ChoiceListItem item : tmpArray)
                                if (name.equals(item.getText()))
                                    count++;
                        if (count != modelProfile.getUser().getArrayTarget().size()){
                            for (ChoiceListItem item : tmpArray){
                                arr.put(item.getId());
                            }
                            json.put("targets",arr);
                        }
                    } else {
                        JSONArray arr = new JSONArray();
                        for (ChoiceListItem item : tmpArray){
                            arr.put(item.getId());
                        }
                        json.put("targets",arr);
                    }

                    /*********************************************************************
                     *   Проверка выбраных интересов
                     **********************************************************************/
                    tmpArray.clear();
                    for(ChoiceListItem item : modelProfile.getInterest())
                        if(item.isCheck()) tmpArray.add(item);

                    if (modelProfile.getUser().getArrayInterest().size() ==
                            tmpArray.size()){
                        int count = 0;
                        JSONArray arr = new JSONArray();
                        for(String name : modelProfile.getUser().getArrayInterest())
                            for (ChoiceListItem item : tmpArray)
                                if (name.equals(item.getText()))
                                    count++;
                        if (count != modelProfile.getUser().getArrayInterest().size()){
                            for (ChoiceListItem item : tmpArray){
                                arr.put(item.getId());
                            }
                            json.put("interest",arr);
                        }
                    } else {
                        JSONArray arr = new JSONArray();
                        for (ChoiceListItem item : tmpArray){
                            arr.put(item.getId());
                        }
                        json.put("interest",arr);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mSocket.setProfileData(json);
            }

            @Override
            public void OnChangeItem(final int pos) {
                if (DEBUG) Log.e("ControllerProfile", "OnChangeItem");
                switch (pos) {
                    case 1:
                        commonEditTextDialog = new CommonEditTextDialog(getContext(), new CommonEditTextDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                if (DEBUG) Log.e("ControllerProfile", "Update EditList");
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setName(text);
                            }
                        });
                        commonEditTextDialog.setTitleText("Ваше имя");
                        commonEditTextDialog.setHintText("Введите имя...");
                        commonEditTextDialog.setMainText(adapterListEditProfile.getItems().get(pos).getInfo());
                        commonEditTextDialog.show();
                        break;
                    case 2:
                        CommonDateEditDialog commonDateEditDialog = new CommonDateEditDialog(getContext(), new CommonDateEditDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(int year, int month, int day) {
                                adapterListEditProfile.getItems().get(pos)
                                        .setDate(year, month, day);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setBirthday(year, month, day);
                            }
                        });
                        commonDateEditDialog.setTitleText("Ваша дата рождения");
                        ProfileEditListItem item = adapterListEditProfile.getItems().get(pos);
                        commonDateEditDialog.setDate(item.getDate().getYear(),
                                            item.getDate().getMonth(),
                                            item.getDate().getDay());
                        commonDateEditDialog.show();
                        break;
                    case 4:
                        commonEditTextDialog = new CommonEditTextDialog(getContext(), new CommonEditTextDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setUserInfo(text);
                            }
                        });
                        commonEditTextDialog.setTitleText("О себе");
                        commonEditTextDialog.setHintText("Напишите о себе...");
                        commonEditTextDialog.setMainText(adapterListEditProfile.getItems().get(pos).getInfo());
                        commonEditTextDialog.setMultiLine(true);
                        commonEditTextDialog.show();
                        break;
                    case 5:
                        commonListViewDialog = new CommonListViewDialog(getContext());
                        commonListViewDialog.setTitleText("Регион");
                        commonListViewDialog.setItems(modelProfile.getRegion());
                        commonListViewDialog.setClickListener(new CommonListViewDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setRegion(text);
                                modelProfile.getCityList(text);
                            }
                        });
                        commonListViewDialog.show();
                        break;
                    case 6:
                        commonListViewDialog = new CommonListViewDialog(getContext());
                        commonListViewDialog.setTitleText("Город");
                        commonListViewDialog.setItems(modelProfile.getCity());
                        commonListViewDialog.setClickListener(new CommonListViewDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setCity(text);
                            }
                        });
                        commonListViewDialog.show();
                        break;
                    case 7:
                        commonListViewDialog = new CommonListViewDialog(getContext());
                        commonListViewDialog.setTitleText("Семейное положение");
                        commonListViewDialog.setItems(modelProfile.getFamilyStatus());
                        commonListViewDialog.setClickListener(new CommonListViewDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setFamily_status(text);
                            }
                        });
                        commonListViewDialog.show();
                        break;
                    case 8:
                        commonListViewDialog = new CommonListViewDialog(getContext());
                        commonListViewDialog.setTitleText("Ищете");
                        commonListViewDialog.setItems(modelProfile.getFindGender());
                        commonListViewDialog.setClickListener(new CommonListViewDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setLookingFor(text);
                            }
                        });
                        commonListViewDialog.show();
                        break;
                    case 9:
                        CommonMultiChangeList cMultiChangeListTarget = new CommonMultiChangeList(getContext());
                        cMultiChangeListTarget.setTitleText("Цели знакомства");
                        cMultiChangeListTarget.setItems(modelProfile.getTarget());
                        cMultiChangeListTarget.setClickListener(new CommonMultiChangeList.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                            }
                        });
                        cMultiChangeListTarget.show();
                        break;
                    case 10:
                        CommonMultiChangeList cMultiChangeListInterest = new CommonMultiChangeList(getContext());
                        cMultiChangeListInterest.setTitleText("Интересы");
                        cMultiChangeListInterest.setItems(modelProfile.getInterest());
                        cMultiChangeListInterest.setClickListener(new CommonMultiChangeList.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                            }
                        });
                        cMultiChangeListInterest.show();
                        break;
                    case 11:
                        commonListViewDialog = new CommonListViewDialog(getContext());
                        commonListViewDialog.setTitleText("Ваш пол");
                        commonListViewDialog.setItems(modelProfile.getGender());
                        commonListViewDialog.setClickListener(new CommonListViewDialog.OnClickSaveListener() {
                            @Override
                            public void onClickSave(String text) {
                                adapterListEditProfile.getItems().get(pos).setInfo(text);
                                adapterListEditProfile.notifyDataSetChanged();
                                newUser.setGender(text);
                            }
                        });
                        commonListViewDialog.show();
                        break;
                }
            }
        });

        modelProfile.getProfileData(userData.getId());
    }

    public void setListener(OnCustomClickListener mListener){
        this.mListener = mListener;
    }

    private Context getContext() {
        return viewProfile.getContext();
    }

    @Override
    public void onUpdate() {
        Log.e("ControllerProfile", "onUpdate");
        final User user = modelProfile.getUser();
        Log.e(TAG, "onUpdate: user = " + user);

        adapterListEditProfile.setItems(user);

        handler.post(new Runnable() {
            @Override
            public void run() {
                adapterProfilePhotoPager.setAvatar_path(user.getAvatar());
                viewProfile.getStatusText().setText(modelProfile.getUser().getStatus());
                profileInfoList.setUserInfo(modelProfile.getUser());
                profileInfoList.notifyDataSetChanged();
                adapterProfilePhotoPager.notifyDataSetChanged();
            }
        });
    }

    private OnCustomClickListener onPickAction = new OnCustomClickListener() {
        @Override
        public void onClick(int value) {
            switch(value){
                case AdapterProfilePhotoPager.MAKE_PHOTO:
                    mListener.onClick(value);
                    break;
                case AdapterProfilePhotoPager.TAKE_IN_GALLERY:
                    mListener.onClick(value);
                    break;
                case AdapterProfilePhotoPager.OPEN:
                    break;
                case AdapterProfilePhotoPager.DELETE:
                    break;
            }
        }
    };

    private ModelProfile.OnRenderProgess mRenderListener = new ModelProfile.OnRenderProgess() {
        @Override
        public void onRender() {
            Log.e(TAG, "onRender");
        }
    };
}
