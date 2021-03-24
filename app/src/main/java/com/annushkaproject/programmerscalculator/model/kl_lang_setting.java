package com.annushkaproject.programmerscalculator.model;
import com.annushkaproject.programmerscalculator.R;

public enum kl_lang_setting { zinglish, hindi, AUTO, UNKNOWN;
    public static int get_nmbr_by_lang_setting(kl_lang_setting themeSetting) {
        switch (themeSetting) { case zinglish: return 0; case hindi: return 1; case AUTO: return 2; default: return -1; }
    }
    public static kl_lang_setting get_lang_setting_by_nmbr(int number) {
        switch (number) {
            case 0: return kl_lang_setting.zinglish; case 1: return kl_lang_setting.hindi;
            case 2: return kl_lang_setting.AUTO; default: return kl_lang_setting.UNKNOWN; }
    }
    public static int get_dizit_nems_by_langsetting(kl_lang_setting lang_setting) {
//        switch (lang_setting) {
//            case zinglish: return R.style.AppTheme;
//            case hindi: return R.style.AppTheme_DarkTheme;
//            case AUTO: default: return R.style.AppTheme;
//        }
        return 0;
    }
}
