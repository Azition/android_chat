package com.addapp.izum.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ILDAR on 09.07.2015.
 * Активити для просмотра фотографии в профиле.
 */
public class PhotoViewActivity extends Activity {

    private ArrayList<String> arrayPhoto;
    private ViewPhotoViewActivity viewActivity;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/****************************************************************************************
        Передача массива с путями фотографии и позции фотографии
*****************************************************************************************/
        Intent intent = getIntent();
        arrayPhoto = intent.getStringArrayListExtra("arrayPhoto");
        position = intent.getIntExtra("position", 0);

        viewActivity = new ViewPhotoViewActivity(this, arrayPhoto, position);
        setContentView(viewActivity.getMainView());
    }

/****************************************************************************************
    Класс для работы и отображения фотографии
*****************************************************************************************/

    private class ViewPhotoViewActivity{

        private Context context;
        private ArrayList<String> arrayPhoto;
        private ViewPager photoPager;
        private int position;

        public ViewPhotoViewActivity(Context context, ArrayList<String> arrayPhoto, int position){

            this.context = context;
            this.arrayPhoto = arrayPhoto;
            this.position = position;

            init();         // инициализация компонентов представления
            /*
            *       statusBarLayout - слой статус бара, в него входит imageBar и statusText
            *
            *       mDrawerLayout - слой меню, он содержит mDrawerList
            *
            * */

            setup();        // настройка компонентов
        }

        private void init() {
            photoPager = new ViewPager(context);
        }

        private void setup() {

            photoPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            photoPager.setAdapter(new PhotoPagerAdapter(arrayPhoto));
            photoPager.setCurrentItem(position);
            photoPager.setBackgroundColor(Color.BLACK);
        }

        /*   **********  Getters  **********  */

        public Context getContext(){
            return this.context;
        }

        public View getMainView(){
            return photoPager;
        }

        class PhotoPagerAdapter extends PagerAdapter{

            private ArrayList<String> arrayPhoto;

            public PhotoPagerAdapter(ArrayList<String> arrayPhoto){
                this.arrayPhoto = arrayPhoto;
            }

            @Override
            public int getCount() {
                return arrayPhoto.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(container.getContext());

                Picasso.with(context)
                        .load(context.getResources()
                        .getIdentifier(arrayPhoto.get(position), "drawable", getPackageName()))
                        .fit()
                        .centerInside()
                        .into(photoView);

                // Now just add PhotoView to ViewPager and return it
                container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                return photoView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
    }
}
