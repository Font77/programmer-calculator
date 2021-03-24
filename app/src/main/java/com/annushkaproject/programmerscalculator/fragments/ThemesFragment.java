package com.annushkaproject.programmerscalculator.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.annushkaproject.programmerscalculator.R;
import com.annushkaproject.programmerscalculator.model.ThemeSetting;
import com.annushkaproject.programmerscalculator.utils.SharedPreferencesUtil;

import static com.annushkaproject.programmerscalculator.model.ThemeSetting.getThemeStyleByThemeSetting;

public class ThemesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_themes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RadioButton lightRadio = getActivity().findViewById(R.id.lightThemeButton);
        RadioButton darkRadio = getActivity().findViewById(R.id.darkThemeButton);
        SharedPreferencesUtil prefUtil = new SharedPreferencesUtil(getActivity());
        ThemeSetting themeSetting = prefUtil.loadThemeSetting();
        switch (themeSetting) {
            case LIGHT: lightRadio.setChecked(true);darkRadio.setChecked(false);break;
            case DARK: lightRadio.setChecked(false);darkRadio.setChecked(true);break;
            case UNKNOWN: lightRadio.setChecked(false);darkRadio.setChecked(false);break;
        }

        lightRadio.setOnClickListener((v) -> { ThemeSetting theme = ThemeSetting.LIGHT;
            prefUtil.saveThemeSetting(ThemeSetting.getThemeSettingByNumber(ThemeSetting.getNumberByThemeSetting(theme)));
            Log.d("ThemeChanged", "mode_enum radio pressed, current value: " + theme.toString());
            getActivity().recreate();
        });

        darkRadio.setOnClickListener((v) -> { ThemeSetting theme = ThemeSetting.DARK;
            prefUtil.saveThemeSetting(ThemeSetting.getThemeSettingByNumber(ThemeSetting.getNumberByThemeSetting(theme)));
            Log.d("ThemeChanged", "mode_enum radio pressed, current value: " + theme.toString());
            getActivity().recreate();
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
