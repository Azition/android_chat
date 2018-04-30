package com.addapp.izum.CustomViewComponents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;
import com.addapp.izum.Structure.ChoiceListItem;

import java.util.ArrayList;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class CommonListViewDialog extends Dialog implements AdapterView.OnItemClickListener{

    private static final boolean DEBUG = true;

    private TextView statusTitleText;
    private ListView commonList;
    private String titleText;
    private AdapterCommonList adapterCommonList;
    private ArrayList<ChoiceListItem> items = new ArrayList<>();
    private OnClickSaveListener clickListener;

    public CommonListViewDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_common_listview);
        statusTitleText = (TextView)findViewById(R.id.status_title);
        statusTitleText.setText(titleText);
        statusTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));

        adapterCommonList = new AdapterCommonList(items);

        commonList = (ListView)findViewById(R.id.common_listview);
        commonList.setAdapter(adapterCommonList);
        commonList.setOnItemClickListener(this);


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setTitleText(String titleText){
        this.titleText = titleText;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        clickListener.onClickSave(items.get(i).getText());
        dismiss();
    }

    public void setClickListener(OnClickSaveListener clickListener) {
        this.clickListener = clickListener;
    }

    private class AdapterCommonList extends BaseAdapter{

        private ArrayList<ChoiceListItem> items = new ArrayList<>();

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
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ItemHolder holder = new ItemHolder();

            if (view == null){
                holder.itemText = new TextView(viewGroup.getContext());

                v = holder.itemText;

                holder.itemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
                holder.itemText.setGravity(Gravity.CENTER_HORIZONTAL);
                holder.itemText.setPadding(0, (int)MainUserData.setTextSize(10), 0, (int)MainUserData.setTextSize(12));

                v.setTag(holder);
            } else {
                holder = (ItemHolder) v.getTag();
            }

            holder.itemText.setText(items.get(i).getText());

            return v;
        }
    }

    private class ItemHolder{
        public TextView itemText;
    }

    public interface OnClickSaveListener{
        void onClickSave(String text);
    }

    public void setItems(ArrayList<ChoiceListItem> items) {
        this.items = items;
    }
}
