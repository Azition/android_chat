package com.addapp.izum.Controller;

import com.addapp.izum.Model.ModelPrivateMessage;
import com.addapp.izum.View.ViewPrivateMessage;

/**
 * Created by ILDAR on 18.06.2015.
 */
public class ControllerPrivateMessage {

    private ModelPrivateMessage mPM;
    private ViewPrivateMessage vPM;

    public ControllerPrivateMessage(ModelPrivateMessage mPM, ViewPrivateMessage vPM){
        this.mPM = mPM;
        this.vPM = vPM;
    }
}
