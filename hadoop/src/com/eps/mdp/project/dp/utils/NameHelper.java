package com.eps.mdp.project.dp.utils;

/**
 * Created by tomaszdabek on 21.06.2017.
 */
public class NameHelper {
    public static String resolveName(String source){
        return source.equals("1") ? Resources.STACK : Resources.REDDIT;
    }
}
