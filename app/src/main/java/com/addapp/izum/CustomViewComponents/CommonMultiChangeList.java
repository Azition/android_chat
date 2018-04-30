package com.addapp.izum.CustomViewComponents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;
import com.addapp.izum.Structure.ChoiceListItem;

import java.util.ArrayList;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class CommonMultiChangeList extends Dialog implements View.OnClickListener {

    private static final boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();

    private TextView yes, no, statusTitleText;
    private ListView commonList;
    private String titleText;
    private AdapterCommonList adapterCommonList;
    private ArrayList<ChoiceListItem> items = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private OnClickSaveListener clickListener;

    public CommonMultiChangeList(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_common_listview);
        findViewById(R.id.line).setVisibility(View.VISIBLE);
        findViewById(R.id.buttons).setVisibility(View.VISIBLE);
        statusTitleText = (TextView)findViewById(R.id.status_title);
        statusTitleText.setText(titleText);
        statusTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));

        yes = (TextView)findViewById(R.id.yes_button);
        no = (TextView)findViewById(R.id.no_button);
        yes.setTypeface(MainUserData.getCommonTextFont(getContext()));
        no.setTypeface(MainUserData.getCommonTextFont(getContext()));
        yes.setOnClickListener(this);
        no.setOnClickListener(this);


        yes.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        no.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        yes.setTextColor(getContext().getResources().getColor(R.color.hint_color));
        no.setTextColor(getContext().getResources().getColor(R.color.izum_color));

        adapterCommonList = new AdapterCommonList(items);

        commonList = (ListView)findViewById(R.id.common_listview);
        commonList.setAdapter(adapterCommonList);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.yes_button:
                String textOut = "";
                int lastIndex = 0;
                for (int i = 0; i < items.size(); i++)
                    if (items.get(i).isCheck())
                        lastIndex = i;
                for(int i=0; i<items.size(); i++){
                    if (items.get(i).isCheck()){
                        if (lastIndex == i)
                            textOut += items.get(i).getText();
                        else
                            textOut += items.get(i).getText() + "\n";
                    }
                }
                clickListener.onClickSave(textOut);
                dismiss();
                break;
            case R.id.no_button:
                dismiss();
                break;
        }
    }


    private class AdapterCommonList extends BaseAdapter {

        private ArrayList<ChoiceListItem> items = new ArrayList<>();
        private LinearLayout mainLayout;

        public AdapterCommonList(ArrayList<ChoiceListItem> items) {
            this.items = items;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View v = view;
            ItemHolder holder = new ItemHolder();

            if (view == null){

                mainLayout = new LinearLayout(viewGroup.getContext());
                mainLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mainLayout.setOrientation(LinearLayout.HORIZONTAL);

                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;

                holder.itemText = new TextView(viewGroup.getContext());
                holder.itemText.setLayoutParams(params);
                holder.itemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
                holder.itemText.setGravity(Gravity.LEFT);
                holder.itemText.setPadding((int) MainUserData.setTextSize(14),
                        (int) MainUserData.setTextSize(10),
                        0,
                        (int) MainUserData.setTextSize(10));

                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) MainUserData.setTextSize(16);

                holder.itemCheck = new CheckBox(viewGroup.getContext());
                holder.itemCheck.setLayoutParams(params);
                holder.itemCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckBox cb = (CheckBox) view;
                        ChoiceListItem item = (ChoiceListItem) cb.getTag();
                        item.setCheck(cb.isChecked());
                    }
                });

                mainLayout.addView(holder.itemText);
                mainLayout.addView(holder.itemCheck);

                v = mainLayout;

                v.setTag(holder);
            } else {
                holder = (ItemHolder) v.getTag();
            }
            ChoiceListItem listItem = items.get(i);
            holder.itemText.setText(listItem.getText());
            if(DEBUG) Log.e("CommonMultiChangeList", "(" + listItem.getText() + ") => ("  + listItem.isCheck() + ")");
            holder.itemCheck.setChecked(listItem.isCheck());
            holder.itemCheck.setTag(listItem);

            return v;
        }
    }

    public void setClickListener(OnClickSaveListener clickListener) {
        this.clickListener = clickListener;
    }

    private class ItemHolder{
        public TextView itemText;
        public CheckBox itemCheck;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public void setItems(ArrayList<ChoiceListItem> items) {
        this.items = items;
    }

    public interface OnClickSaveListener{
        void onClickSave(String text);
    }
}
