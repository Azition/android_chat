package com.addapp.izum.Adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.addapp.izum.Activity.PhotoViewActivity;
import com.addapp.izum.CustomViewComponents.CommonListViewDialog;
import com.addapp.izum.CustomViewComponents.SquareImageView;
import com.addapp.izum.Interface.OnCustomClickListener;
import com.addapp.izum.R;
import com.addapp.izum.Structure.ChoiceListItem;
import com.addapp.izum.View.ViewProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 11.07.2015.
 */
public class AdapterProfilePhotoPager extends PagerAdapter {
    private final String TAG = getClass().getSimpleName();

    public static final int TAKE_IN_GALLERY = 1;
    public static final int MAKE_PHOTO= 0;
    public static final int OPEN = 2;
    public static final int DELETE = 3;

    private int profile;
    private ArrayList<ChoiceListItem> actions = new ArrayList<>();
    private CommonListViewDialog commonListViewDialog;
    private OnCustomClickListener mListener;
    private String avatar_path = "";

    public AdapterProfilePhotoPager() {
        this.profile = 0;
        actions.add(new ChoiceListItem(1, false, "Загрузить фотографию"));
        actions.add(new ChoiceListItem(2, false, "Сделать снимок"));
        actions.add(new ChoiceListItem(3, false, "Открыть"));
        actions.add(new ChoiceListItem(4, false, "Удалить"));
    }

    public void setProfile(int profile){
        this.profile = profile;
    }

    public void setListener(OnCustomClickListener mListener){
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        SquareImageView imgPager = new SquareImageView(container.getContext());
        imgPager.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        imgPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (profile == ViewProfile.MY_PROFILE) {
                    commonListViewDialog = new CommonListViewDialog(container.getContext());
                    commonListViewDialog.setTitleText("Фотография");
                    commonListViewDialog.setItems(actions);
                    commonListViewDialog.setClickListener(new CommonListViewDialog.OnClickSaveListener() {
                        @Override
                        public void onClickSave(String text) {
                            Log.e(TAG, "onClickSave: " + text);
                            switch (text) {
                                case "Загрузить фотографию":
                                    mListener.onClick(TAKE_IN_GALLERY);
                                    break;
                                case "Сделать снимок":
                                    mListener.onClick(MAKE_PHOTO);
                                    break;
                                case "Открыть":
                                    mListener.onClick(OPEN);
                                    break;
                                case "Удалить":
                                    mListener.onClick(DELETE);
                                    break;
                            }
                        }
                    });
                    commonListViewDialog.show();
                } else {

                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add("photo_1");

                    Intent intent = new Intent(container.getContext(), PhotoViewActivity.class);
                    intent.putExtra("position", position);
                    intent.putStringArrayListExtra("arrayPhoto", arrayList);
                    container.getContext().startActivity(intent);
                }
            }
        });

        Log.e(TAG, "Avatar path: " + avatar_path);

        if (avatar_path.equals("")){

            Picasso.with(container.getContext())
                    .load(R.drawable.nophoto)
                    .fit()
                    .centerCrop()
                    .into(imgPager);
        } else {

            Picasso.with(container.getContext())
                    .load(avatar_path)
                    .fit()
                    .centerCrop()
                    .into(imgPager);
        }

        container.addView(imgPager);

        return imgPager;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
