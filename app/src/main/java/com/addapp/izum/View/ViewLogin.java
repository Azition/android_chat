package com.addapp.izum.View;


import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.addapp.izum.AbstractClasses.CommonView;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ILDAR on 15.06.2015.
 */
public class ViewLogin extends CommonView{

    private RelativeLayout mainLayout;
    private ImageView header;
    private TextView justText1, justText2;
    private EditText setPhoneNumber, setPassword;
    private Button bSingin, bRegistration;
    private TextView tvForgotPass;

    private RelativeLayout.LayoutParams params;

    public ViewLogin(View view) {
        super(view);
    }

    @Override
    protected void init(View view) {
        mainLayout = (RelativeLayout)view.findViewById(R.id.main_layout);
        header = new ImageView(getContext());
        justText1 = new TextView(getContext());
        justText2 = new TextView(getContext());
        setPhoneNumber = new EditText(getContext());
        setPassword = new EditText(getContext());
        bSingin = new Button(getContext());
        bRegistration = new Button(getContext());
        tvForgotPass = new TextView(getContext());
    }

    @Override
    protected void setup() {
        mainLayout.setBackground(getContext()
                .getResources()
                .getDrawable(R.drawable.login_bg));

        /********************************************************
        *       Логотип
        *********************************************************/

        int imageSize = MainUserData.percentToPix(40, MainUserData.WIDTH);
        int topPadding = MainUserData.percentToPix(7, MainUserData.HEIGHT);
        params = new RelativeLayout
                .LayoutParams(imageSize, imageSize);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = topPadding;

        header.setLayoutParams(params);
        header.setId(Utils.generateViewId());
        Picasso.with(getContext())
                .load(R.drawable.logo)
                .into(header);

        /********************************************************
         *       JustText (Свобода общения)
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, header.getId());
        params.topMargin = MainUserData.percentToPix(5, MainUserData.HEIGHT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        justText1.setLayoutParams(params);
        justText1.setGravity(Gravity.CENTER_HORIZONTAL);
        justText1.setText("Общение и знакомство");
        justText1.setTextColor(Color.WHITE);
        justText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        justText1.setTypeface(Typeface
                .createFromAsset(getContext().getAssets(),
                        "Roboto-Light.ttf"));
        justText1.setId(Utils.generateViewId());

        /********************************************************
         *       JustText (Убедитесь сами)
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, justText1.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        justText2.setLayoutParams(params);
        justText2.setGravity(Gravity.CENTER_HORIZONTAL);
        justText2.setText("в твоем городе");
        justText2.setTextColor(Color.WHITE);
        justText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        justText2.setTypeface(Typeface
                .createFromAsset(getContext().getAssets(),
                        "Roboto-Light.ttf"));
        justText2.setId(Utils.generateViewId());

        /********************************************************
         *       EditText for set phone number
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                MainUserData.percentToPix(80, MainUserData.WIDTH),
                MainUserData.percentToPix(8, MainUserData.HEIGHT));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, justText2.getId());
        params.topMargin = MainUserData.percentToPix(6, MainUserData.HEIGHT);

        setPhoneNumber.setLayoutParams(params);
        setPhoneNumber.setBackgroundResource(R.drawable.shape_edit_login);
        setPhoneNumber.setHint("Номер телефона");
        setPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        setPhoneNumber.setHintTextColor(Color.WHITE);
        setPhoneNumber.setTextColor(Color.WHITE);
        setPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(20));
        setPhoneNumber.setId(Utils.generateViewId());

        /********************************************************
         *       EditText for set password
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                MainUserData.percentToPix(80, MainUserData.WIDTH),
                MainUserData.percentToPix(8, MainUserData.HEIGHT));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.addRule(RelativeLayout.BELOW, setPhoneNumber.getId());

        setPassword.setLayoutParams(params);
        setPassword.setBackgroundResource(R.drawable.shape_edit_login);
        setPassword.setHint("Пароль");
        setPassword.setHintTextColor(Color.WHITE);
        setPassword.setTextColor(Color.WHITE);
        setPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(20));
        setPassword.setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        setPassword.setId(Utils.generateViewId());

        /********************************************************
         *       Singin button
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                MainUserData.percentToPix(80, MainUserData.WIDTH),
                MainUserData.percentToPix(8, MainUserData.HEIGHT));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.addRule(RelativeLayout.BELOW, setPassword.getId());

        bSingin.setLayoutParams(params);
        bSingin.setBackgroundResource(R.drawable.button_login);
        bSingin.setText("Вход");
        bSingin.setTextColor(Color.WHITE);
        bSingin.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(20));
        bSingin.setId(Utils.generateViewId());

        /********************************************************
         *       Registration button
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                MainUserData.percentToPix(80, MainUserData.WIDTH),
                MainUserData.percentToPix(8, MainUserData.HEIGHT));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.addRule(RelativeLayout.BELOW, bSingin.getId());

        bRegistration.setLayoutParams(params);
        bRegistration.setBackgroundResource(R.drawable.button_login);
        bRegistration.setText("Регистрация");
        bRegistration.setTextColor(Color.WHITE);
        bRegistration.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(20));
        bRegistration.setId(Utils.generateViewId());

        /********************************************************
         *       Forgot password
         *********************************************************/

        params = new RelativeLayout
                .LayoutParams(
                MainUserData.percentToPix(80, MainUserData.WIDTH),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = MainUserData.percentToPix(3, MainUserData.HEIGHT);
        params.addRule(RelativeLayout.BELOW, bRegistration.getId());

        tvForgotPass.setLayoutParams(params);
        tvForgotPass.setGravity(Gravity.CENTER_HORIZONTAL);
        SpannableString content = new SpannableString("Забыли пароль?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvForgotPass.setText(content);
        tvForgotPass.setTextColor(Color.WHITE);
        tvForgotPass.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(20));


        /********************************************************
         *       Add in layout components
         *********************************************************/

        mainLayout.addView(header);
        mainLayout.addView(justText1);
        mainLayout.addView(justText2);
        mainLayout.addView(setPhoneNumber);
        mainLayout.addView(setPassword);
        mainLayout.addView(bSingin);
        mainLayout.addView(bRegistration);
        mainLayout.addView(tvForgotPass);
    }

    public EditText getSetPhoneNumber() {
        return setPhoneNumber;
    }

    public EditText getSetPassword() {
        return setPassword;
    }

    public Button getBSingin() {
        return bSingin;
    }
}
