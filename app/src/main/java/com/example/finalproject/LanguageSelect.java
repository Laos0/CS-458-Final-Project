package com.example.finalproject;

import android.content.Context;
import android.content.res.Configuration;
//import android.widget.Toast;

import java.util.Locale;

class LanguageSelect {
    // Sets the Language Using Locale.
    static void languageSelect(int position, Context baseContext) {
        switch (position) {
            case 0:
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                baseContext.getResources().updateConfiguration(config, baseContext.getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 1:
                Locale locale2 = new Locale("fr");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                baseContext.getResources().updateConfiguration(config2, baseContext.getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Locale locale3 = new Locale("es");
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                baseContext.getResources().updateConfiguration(config3, baseContext.getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 3:
                Locale locale4 = new Locale("de");
                Locale.setDefault(locale4);
                Configuration config4 = new Configuration();
                config4.locale = locale4;
                baseContext.getResources().updateConfiguration(config4, baseContext.getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 4:
                Locale locale5 = new Locale("ru");
                Locale.setDefault(locale5);
                Configuration config5 = new Configuration();
                config5.locale = locale5;
                baseContext.getResources().updateConfiguration(config5, baseContext.getResources().getDisplayMetrics());
               //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
