package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;

import java.util.ArrayList;

/**
 * Created by ILDAR on 16.09.2015.
 */
public class AdapterOptionList extends BaseAdapter {

    private static final int HEADER = 0;
    private static final int CONTENT = 1;

    private Context context;
    private ArrayList<ItemOption> items = new ArrayList<>();
    private int textSize = 20;
    private RelativeLayout mainLayout;
    private RelativeLayout.LayoutParams params;
    private OptionView itemView;

    private String nickName = "Sobees";

    private int heightLayout = MainUserData.percentToPix(8, MainUserData.HEIGHT);

    public AdapterOptionList(Context context) {
        this.context = context;

        items.add(new ItemOption("ПРИВАТНОСТЬ", HEADER));
        items.add(new ItemOption("Ник в анонимном чате", CONTENT));
        items.add(new ItemOption("Черный список", CONTENT));
        items.add(new ItemOption("ОПОВЕЩЕНИЯ", HEADER));
        items.add(new ItemOption("Звук", CONTENT));
        items.add(new ItemOption("Вибрация", CONTENT));
        items.add(new ItemOption("Индикация", CONTENT));
        items.add(new ItemOption("ПРИЛОЖЕНИЕ", HEADER));
        items.add(new ItemOption("Оценить", CONTENT));
        items.add(new ItemOption("О программе", CONTENT));
        items.add(new ItemOption("Выход", CONTENT));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){

            mainLayout = new RelativeLayout(context);
            mainLayout.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    heightLayout
            ));
        }
        else{
            mainLayout = (RelativeLayout) view;
        }

        itemView = new OptionView(context, mainLayout);

        ItemOption item = items.get(i);

        return itemView.getOptionItem(item, i);
    }

    private class ItemOption{
        private String item_name;
        private int state;

        public ItemOption(String item_name, int state) {
            this.item_name = item_name;
            this.state = state;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getItemName() {
            return item_name;
        }

        public void setItemName(String item_name) {
            this.item_name = item_name;
        }
    }

    private class OptionView{

        private RelativeLayout mLayout;
        private RelativeLayout.LayoutParams mParams;
        private Context ctx;

        public OptionView(Context ctx, RelativeLayout mLayout){
            this.ctx = ctx;
            this.mLayout = mLayout;
            this.mLayout.removeAllViews();
        }

        public RelativeLayout getOptionItem(ItemOption item, int pos){

            TextView item_name = new TextView(ctx);
            mParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mParams.leftMargin = MainUserData.percentToPix(5, MainUserData.WIDTH);
            item_name.setId(Utils.generateViewId());
            item_name.setLayoutParams(mParams);
            item_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    MainUserData.setTextSize(textSize));
            item_name.setTypeface(Typeface
                    .createFromAsset(context.getAssets(), "Roboto-Light.ttf"));
            item_name.setText(item.getItemName());

            if(item.getState() == CONTENT){
                mLayout.setBackgroundColor(Color.WHITE);
                item_name.setTextColor(Color.BLACK);
            } else {
                mLayout.setBackgroundColor(Color.parseColor("#e7e7e7"));
            }

            mLayout.addView(item_name);

            View item_view = initView(pos, item_name);

            if (item_view != null)
                mLayout.addView(item_view);

            return mLayout;
        }

        private View initView(int pos, View view){

            switch (pos){

                case 1:
                    return initNick(view);

                case 4:case 5:case 6:
                    return initSwitch();

                case 10:
                    return initImage();

                default:
                    return null;
            }
        }

        private TextView initNick(View view){
            TextView editText = new TextView(context);
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.rightMargin = MainUserData.percentToPix(5, MainUserData.WIDTH);
            params.addRule(RelativeLayout.RIGHT_OF, view.getId());
            params.addRule(RelativeLayout.END_OF, view.getId());
            params.leftMargin = 5;
            editText.setLayoutParams(params);
            editText.setGravity(Gravity.RIGHT);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    MainUserData.setTextSize(textSize));
            editText.setText(nickName);

            return editText;
        }

        private Switch initSwitch(){

            Switch aSwitch = new Switch(context);
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.rightMargin = MainUserData.percentToPix(5, MainUserData.WIDTH);
            aSwitch.setLayoutParams(params);
            aSwitch.setChecked(false);

            return aSwitch;
        }

        private ImageView initImage(){

            ImageView imageView = new ImageView(context);
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.rightMargin = MainUserData.percentToPix(5, MainUserData.WIDTH);
            params.topMargin = (int) (heightLayout * 0.2f);
            params.bottomMargin = (int) (heightLayout * 0.2f);
            imageView.setLayoutParams(params);
            imageView.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.logout));

            return imageView;
        }

    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
