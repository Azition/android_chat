package com.addapp.izum.OtherClasses;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Created by ILDAR on 13.08.2015.
 */
public class Utils {

    private static String[] colorsMan = {
        "#8e81ef", "#8196ef", "#8186ef", "#8198ef", "#7a82f4",
        "#7b98e9", "#7ba5e8"
    };

    private static String[] colorsFemale = {
            "#ffaedc", "#ef81d1", "#ef819e", "#ef81e9", "#ef81b5",
            "#ef81aa", "#f97ee0"
    };

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId(){
        for(;;){
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1;
            if(sNextGeneratedId.compareAndSet(result, newValue)){
                return result;
            }
        }
    }

    public static String generateColorMan(){
        Random rand = new Random();
        return colorsMan[rand.nextInt(colorsMan.length)];
    }

    public static String generateColorFemale(){
        Random rand = new Random();
        return colorsFemale[rand.nextInt(colorsFemale.length)];
    }

    public static String deleteEnters(String source){
        return source.replaceAll("\n","");
    }
}
