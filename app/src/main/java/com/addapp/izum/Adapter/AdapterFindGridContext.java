package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.OtherClasses.FastBlur;
import com.addapp.izum.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ILDAR on 01.07.2015.
 */
public class AdapterFindGridContext extends BaseAdapter {

    private final LayoutInflater mInflater;
    private Configurations config;
    private Context context;
    private boolean[] arrayShow = new boolean[24];

    public AdapterFindGridContext(Context context){
        mInflater = LayoutInflater.from(context);

        this.context = context;
        config = new Configurations(context);
        for (int i=0; i<arrayShow.length;i++){
            arrayShow[i] = true;
        }
    }


    @Override
    public int getCount() {
//        return mItems.size();
        return 24;
    }

    @Override
    public Item getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
//        return mItems.get(position).drawableId;
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ImageView picture;
        final TextView name;

        if(v == null){
            v = mInflater.inflate(R.layout.item_find_grid, parent, false);
            v.setTag(R.id.imagePhoto, v.findViewById(R.id.imagePhoto));
            v.setTag(R.id.text_name, v.findViewById(R.id.text_name));
        }

        picture = (ImageView)v.getTag(R.id.imagePhoto);
        name = (TextView)v.getTag(R.id.text_name);

//        Item item = getItem(position);

        int resId = parent.getResources().getIdentifier("photo_" + (position % 3 + 1), "drawable", parent.getContext().getPackageName());

        Picasso.with(parent.getContext())
                .load(resId)
                .fit()
                .centerCrop()
                .into(picture);

        name.setTypeface(config.getFont());
        name.setText("name");

        return v;
    }

    private void blur(Bitmap bkg, View view) {
        float radius = 20;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);
        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), overlay));
    }

    private static class Item{
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId){
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}
