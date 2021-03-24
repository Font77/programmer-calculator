package com.annushkaproject.programmerscalculator.utils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.annushkaproject.programmerscalculator.Interfaces.AppearanceUpdateInterface;
import com.annushkaproject.programmerscalculator.model.ThemeSetting;
import com.annushkaproject.programmerscalculator.model.kl_lang_setting;
public class SharedPreferencesUtil {
    private static final String PREF = "Preferences";
    private static final String THEME_SETTING_KEY = "ThemeSettingKey";
    private static final String lang_settings_key = "lang_settings_key";
    private static final String PREFERENCES_LOG_TAG = "PreferencesLog";
    private SharedPreferences preferences; private SharedPreferences.Editor preferencesEditor;
    public SharedPreferencesUtil(Activity activity) {
        preferences = activity.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
    public void saveThemeSetting(ThemeSetting themeSetting) {
        preferencesEditor.putInt(THEME_SETTING_KEY, ThemeSetting.getNumberByThemeSetting(themeSetting));
        preferencesEditor.commit();
    }
    public void save_lang_setting(kl_lang_setting lang_setting) {
        preferencesEditor.putInt(lang_settings_key, kl_lang_setting.get_nmbr_by_lang_setting(lang_setting));
        preferencesEditor.commit();
    }
    public ThemeSetting loadThemeSetting() {
        int value = preferences.getInt(THEME_SETTING_KEY, ThemeSetting.getNumberByThemeSetting(ThemeSetting.LIGHT));
        return ThemeSetting.getThemeSettingByNumber(value);
    }
    public kl_lang_setting load_language_setting() {
        int value = preferences.getInt(lang_settings_key, kl_lang_setting.get_nmbr_by_lang_setting(kl_lang_setting.hindi));
        return kl_lang_setting.get_lang_setting_by_nmbr(value);
    }
    public void onPreferencesUpdated(AppearanceUpdateInterface updateInterface) {
        if (updateInterface == null) { Log.d(PREFERENCES_LOG_TAG, "onPreferencesUpdated: updateInterface is null");return; }
        preferences.registerOnSharedPreferenceChangeListener((prefs, key) -> {
            updateInterface.needUpdateAppearance(loadThemeSetting());
        });
    }
}