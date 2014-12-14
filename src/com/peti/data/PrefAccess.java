package com.peti.data;

import java.util.prefs.Preferences;


/**
 * Static access to preferences
 */
public class PrefAccess {
    private static Preferences preferences;

    public static Preferences getPreferences(){
        if(preferences == null){
            preferences = Preferences.userRoot().node("com.peti.javahazi");
        }
        return preferences;
    }

}
