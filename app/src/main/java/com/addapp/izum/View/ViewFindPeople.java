package com.addapp.izum.View;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.addapp.izum.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

/**
 * Created by ILDAR on 24.06.2015.
 */
public class ViewFindPeople {
    
    private View view;                          // общая вьюшка
    private RelativeLayout findContent;         // слой отображения найденных людей
    private GridView gridFindContext;           // список найденных людей
    private ArrayAdapter<String> adapter;       // адаптер вывода параметров поиска

    private RelativeLayout.LayoutParams params;
    private PullToRefreshGridView toRefreshGridView;

    private DrawerLayout fDrawerLayout;         // слой вслывающего со всплывающим меню
    private ListView fDrawerList;               // меню - список параметров
    private FloatingActionButton fab;           // второй вариант кнопки всплывающего меню

    private String[] findItemList = {"Поиск", "Пол", "Возраст", "Интересы"};    // временный массив


    public ViewFindPeople(View view){
        this.view = view;
        init();                                 // функция инициализации переменных
        setup();                                // функция установки настроек
    }

    /*
        Инициализация компонентов
    */

    private void init() {

        findContent = (RelativeLayout)view.findViewById(R.id.find_content);

        fDrawerLayout = (DrawerLayout)view.findViewById(R.id.fDrawer_layout);

        fDrawerList = (ListView)view.findViewById(R.id.find_menu);

        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, findItemList);

        toRefreshGridView = new PullToRefreshGridView(getContext());

        gridFindContext = toRefreshGridView.getRefreshableView();

        fab = new FloatingActionButton(view.getContext());

//        listFindContext = new ListView(view.getContext());
    }

    private void setup() {

//        fDrawerLayout.setScrimColor(Color.TRANSPARENT);

        fDrawerList.setAdapter(adapter);
        fDrawerList.setBackgroundResource(R.color.izum_color);

        /*
        *   Вывод изображения иконки через Picasso
        */

//        listFindContext.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                                                ViewGroup.LayoutParams.MATCH_PARENT));

        /*
            Установка параметров всплывающей кнопки
        */
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        params.addRule(RelativeLayout.ALIGN_PARENT_END, 1);
        params.addRule(RelativeLayout.ABOVE, gridFindContext.getId());
        params.setMargins(0, 0, 20, 20);

        fab.setLayoutParams(params);

        fab.setSize(FloatingActionButton.SIZE_MINI);
        fab.setColor(view.getResources().getColor(R.color.izum_color));
        fab.initBackground();
        fab.setImageResource(R.drawable.message);


        /*
            Установка параметров таблицы поиска
        */

//        gridFindContext.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        gridFindContext.setVerticalSpacing(0);
        gridFindContext.setHorizontalSpacing(0);

        gridFindContext.setOnTouchListener(new ShowHideOnScroll(fab));
        gridFindContext.setNumColumns(3);
        gridFindContext.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        toRefreshGridView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        toRefreshGridView.setScrollingWhileRefreshingEnabled(true);
        toRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        findContent.addView(toRefreshGridView);
        findContent.addView(fab);

    }

    /*   **********  Getters  **********  */

    public Context getContext(){
        return view.getContext();
    }

    public View getView(){
        return this.view;
    }

    public DrawerLayout getDrawerLayout(){
        return this.fDrawerLayout;
    }

    public GridView getGridFindContext(){ return this.gridFindContext; }

    public FloatingActionButton getButtonParamFind(){ return this.fab; }

    public PullToRefreshGridView getToRefreshGridView() {
        return toRefreshGridView;
    }

    /*   **********  Setters  **********  */
}
