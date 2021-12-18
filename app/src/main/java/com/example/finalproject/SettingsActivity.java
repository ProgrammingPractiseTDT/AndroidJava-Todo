package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import android.preference.Preference;

import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.Locale;

public class SettingsActivity extends PreferenceActivity {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadLocale();
        this.addPreferencesFromResource(R.xml.preferences);

        Preference preference = (Preference) findPreference("language_pref");
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                setLocale(newValue.toString());
                recreate();
                restartActivity();
                return true;
            }
        });
    }


    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My language", lang);
        editor.apply();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("My language","");
        setLocale(language);
    }

//    private void setLanguage(String lg) {
//        Locale locale = new Locale(lg);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration config = res.getConfiguration();
//        config.locale = locale;
//        res.updateConfiguration(config, dm);
//        startActivity(new Intent(this, SettingsActivity.class));
//    }
}
