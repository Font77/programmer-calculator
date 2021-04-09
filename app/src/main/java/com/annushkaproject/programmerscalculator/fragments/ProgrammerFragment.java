package com.annushkaproject.programmerscalculator.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.annushkaproject.programmerscalculator.R;
import com.annushkaproject.programmerscalculator.model.Operator;
import com.annushkaproject.programmerscalculator.model.ProgrammerCalcModel;
import com.annushkaproject.programmerscalculator.model.int_size_enum;
import com.annushkaproject.programmerscalculator.utils.InstanceStateUtil;
import com.annushkaproject.programmerscalculator.utils.ProgrammerOperationsUtil;
import com.annushkaproject.programmerscalculator.utils.c_nmbr_utils;

public class ProgrammerFragment extends Fragment {

    private TextView u5h_tekst_viyu; private TextView u2b_tekst_viyu;
    private TextView dizit_tekst_viyu; private TextView ekuation_tekst_viyu;
    private ProgrammerCalcModel calcModel = new ProgrammerCalcModel();
    private boolean secondValueInputStarted = false;
    private String packageName;
    private int_size_enum bytelengthenum = int_size_enum.l8;
//    private mode_enum modeenum = mode_enum.mode_enum_dec;
//    private mode_enum modeenum = mode_enum.mode_enum_heks;
    private boolean limit_phen_dizits_recahed = false ;
    private int limit_dizits = 14 ;
//    String[] dizit_nems_array = getResources().getStringArray(R.array.dizit_nems_array);

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (packageName == null) {
            packageName = savedInstanceState.getString("PACKAGE_NAME");
            calcModel = InstanceStateUtil.restoreProgrammerSavedInstance(savedInstanceState);
        }
        return inflater.inflate(R.layout.fragment_programmer, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { super.onViewCreated(view, savedInstanceState);
        u5h_tekst_viyu = getView().findViewById(R.id.u5h_tekst_viyu); u2b_tekst_viyu = getView().findViewById(R.id.u2b_tekst_viyu);
        dizit_tekst_viyu = getView().findViewById(R.id.dizit_tekst_viyu); ekuation_tekst_viyu = getView().findViewById(R.id.ekuation_tekst_viyu);
        //
        setupNumberButtons();setupOperatorButtons();setupLetterButtons();setupCalculateButton();setupDeleteButton();
        //setupClearButton(); TODO: add clear button
        setupSignButton();
//        setupModeRadio();

        // heks related
        calcModel.setNmbr_base(16);
        enableButtonsALL();
        setup_heks_cb();
        //modeenum = mode_enum.mode_enum_heks;


        setup_decimal_spinner();
        setupWordLengthButton();
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) { super.onSaveInstanceState(outState);
        InstanceStateUtil.saveInstanceState(outState, calcModel, packageName);
    }

    public void setupFragment(String packageName) { this.packageName = packageName; }

    private void setupNumberButtons() {
        for (int i = 0; i < 10; i++) {
            Button button = getView().findViewById(getResources().getIdentifier("button" + i, "id", packageName));
            int finalI = i;
            button.setOnClickListener((v) -> {
                switch (finalI) {
                    case 0 : dizit_tekst_viyu.setText("ziro"); break;
                    case 1 : dizit_tekst_viyu.setText("vn"); break;
                    case 2 : dizit_tekst_viyu.setText("tuu"); break;
                    case 3 : dizit_tekst_viyu.setText("Three"); break;
                    case 4 : dizit_tekst_viyu.setText("four"); break;
                    case 5 : dizit_tekst_viyu.setText("five"); break;
                    case 6 : dizit_tekst_viyu.setText("siks"); break;
                    case 7 : dizit_tekst_viyu.setText("seven"); break;
                    case 8 : dizit_tekst_viyu.setText("eight"); break;
                    case 9 : dizit_tekst_viyu.setText("nine"); break;
                }
//                dizit_tekst_viyu.setText(dizit_nems_array[finalI]);
                if (!limit_phen_dizits_recahed) usePressedNumber(((Button) v).getText().toString());
            });
        }
    }

    private void setupLetterButtons() {
        int[] letterButtonIDs = new int[]{R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF};
//        int for_start_i = 10 ;
        for (int buttonID : letterButtonIDs) {
            Button button = getView().findViewById(buttonID);
//            int finalFor_start_i = for_start_i;
            button.setOnClickListener((v) -> {
//                dizit_tekst_viyu.setText(dizit_nems_array[finalFor_start_i]);
                switch (buttonID) {
                    case R.id.buttonA : dizit_tekst_viyu.setText("ten"); break;
                    case R.id.buttonB : dizit_tekst_viyu.setText("zilevn"); break;
                    case R.id.buttonC : dizit_tekst_viyu.setText("kvAlv"); break;
                    case R.id.buttonD : dizit_tekst_viyu.setText("dblyu"); break;
                    case R.id.buttonE : dizit_tekst_viyu.setText("Aksen"); break;
                    case R.id.buttonF : dizit_tekst_viyu.setText("phen"); break;
                }

                if (!limit_phen_dizits_recahed) {
//                    System.out.println(button.getText().toString());
                    usePressedNumber(((Button) v).getText().toString());
                }
            });
//            for_start_i += 1;
        }
    }

    private void setupOperatorButtons() {
        int[] operatorButtonIDs = new int[]{R.id.buttonMod, R.id.button_zor, R.id.buttonOR, R.id.buttonAND,
                R.id.buttonLSH, R.id.buttonRSH, R.id.buttonNOT, R.id.buttonRDivide, R.id.buttonMultiply,
                R.id.buttonMinus, R.id.buttonPlus};
        for (int buttonID : operatorButtonIDs) { Button button = getView().findViewById(buttonID);button.setOnClickListener((v) -> {
            if (!limit_phen_dizits_recahed) {
                Operator operator = Operator.operatorForTitle(button.getText().toString());
                usePressedOperator(operator);
            }
            });
        }
    }

    private void setupCalculateButton() { Button equalsButton = getView().findViewById(R.id.buttonEquals);equalsButton.setOnClickListener(v -> {
            Button button = (Button) v; System.out.println(button.getText().toString());
            useEqualsOperator();
        });
    }

    private void setupDeleteButton() { Button delButton = getView().findViewById(R.id.buttonDel); delButton.setOnClickListener(v -> {
            String currentString = currentString();
            if (limit_phen_dizits_recahed) { enableButtonsALL(); enableOperatorButtons(); limit_phen_dizits_recahed = false ;}
            else if (!currentString.equals("-") && currentString.length() > 1) {
                    currentString = currentString.substring(0, currentString.length() - 1);
                    updateText(currentString);
            }
            else updateText(calcModel.textForValue(0.0));
    }); }

    private void setupSignButton() { Button signButton = getView().findViewById(R.id.buttonSign);signButton.setOnClickListener(v -> {
            long currentValue = Long.parseLong(currentString(), calcModel.get_nmbr_base());
            if (currentValue == 0) return;
            String updatedString = formatText(currentValue);
            if (currentValue > 0) updatedString = "-" + updatedString; else updatedString = updatedString.substring(1);
            updateText(updatedString);
        });
    }
    private String currentString() { return u5h_tekst_viyu.getText().toString(); }
    private void setupWordLengthButton() { Button modeButton = getView().findViewById(R.id.buttonLength);modeButton.setOnClickListener(v -> {
            long val = Long.parseLong(currentString(), calcModel.get_nmbr_base());
            if (bytelengthenum.ordinal() < 3) {
                int num = bytelengthenum.ordinal();
                bytelengthenum = int_size_enum.values()[++num];
            } else bytelengthenum = int_size_enum.l8;
            switch (bytelengthenum) {
                case l4: val = (int) val;break;
                case l2: val = (short) val;break;
                case l1: val = (byte) val;
            }
            calcModel.setBytelengthenum(bytelengthenum);
            updateText(formatText(val));
            Log.d("LengthChanged", "Length button pressed, current value: " + bytelengthenum.toString());
            modeButton.setText(bytelengthenum.toString());
        });
    }
    private void setup_heks_cb() {
        CheckBox heks_cb = getView().findViewById(R.id.heks_check_boks); heks_cb.setOnCheckedChangeListener( (v, id) -> {
            long number = Long.parseLong(u5h_tekst_viyu.getText().toString(), calcModel.get_nmbr_base());
            if (heks_cb.isChecked()) { calcModel.setNmbr_base(16); enableButtonsALL(); }
            else {calcModel.setNmbr_base(10); enableButtonsDEC();}
            updateText(formatText(number));
        });
    }
    private void setup_decimal_spinner() {
        Spinner spinner = getView().findViewById(R.id.decimal_precision_spinner);
        String[] decimal_points_items = getResources().getStringArray(R.array.decimal_points_selection_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( spinner.getContext(), R.array.decimal_points_selection_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        c_handle_decimal_spinboks spinboks_listener = new c_handle_decimal_spinboks() ;
//        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) spinboks_listener);
    }
    private void usePressedNumber(String number) {
        if (currentString().equals("0") && !number.equals(".")) { u5h_tekst_viyu.setText(""); u2b_tekst_viyu.setText(""); }
        String newString;
        if (secondValueInputStarted) { newString = number;secondValueInputStarted = false; }
        else newString = u5h_tekst_viyu.getText().toString() + number;
        updateText(newString);
    }

    private void usePressedOperator(Operator operator) {
        boolean readyToSaveOperator = calcModel.getFirstValue() != null;
        if (!readyToSaveOperator) return;
        boolean readyToCalcOneSide = !operator.requiresTwoValues() && calcModel.getFirstValue() != null;
        if (readyToCalcOneSide) { calcModel.setOperator(operator);calculateResult();return; }
        boolean readyToCalcTwoSides = calcModel.getOperator() != null && calcModel.getFirstValue() != null && calcModel.getSecondValue() != null;
        if (readyToCalcTwoSides) calculateResult(); else secondValueInputStarted = true;
        calcModel.setOperator(operator);
    }

    private void useEqualsOperator() { if (calcModel.getOperator() == null) return;calculateResult(); }
    private void calculateResult() {
        long result = ProgrammerOperationsUtil.calculateWithData(calcModel);
        calcModel.resetCalcState();
        calcModel.updateAfterCalculation(result);
        updateText(formatText(result));
        secondValueInputStarted = true;
    }

    private String formatText(long number) { return Long.toString(number, calcModel.get_nmbr_base()).toUpperCase(); }

    private void updateText(String updatedText) {
        if(updatedText.length() > limit_dizits)
        {
            disableAllButtons();
            disableOperatorButtons();
            limit_phen_dizits_recahed = true ;
        }
        else
        {
            if (updatedText.length() > 12) {
                int input1_tekst_size_small = (int) (getResources().getDimension(R.dimen.input1_tekst_size_small));// / getResources().getDisplayMetrics().density) ;
                int input3_tekst_size_small = (int) (getResources().getDimension(R.dimen.input3_tekst_size_small));// / getResources().getDisplayMetrics().density) ;

                u5h_tekst_viyu.setTextSize(18);
                u2b_tekst_viyu.setTextSize(27);
            }
            else {
                int input1_tekst_size_big = (int) (getResources().getDimension(R.dimen.input1_tekst_size_big));// / getResources().getDisplayMetrics().density) ;
                int input3_tekst_size_big = (int) (getResources().getDimension(R.dimen.input3_tekst_size_big));// / getResources().getDisplayMetrics().density) ;
                u5h_tekst_viyu.setTextSize(26);
                u2b_tekst_viyu.setTextSize(39);
            } ;
            u5h_tekst_viyu.setText(updatedText);
            u2b_tekst_viyu.setText(updatedText);
            ekuation_tekst_viyu.setText(c_nmbr_utils.heks_nmbr_string_to_vrdsstring(updatedText));
            calcModel.updateValues(updatedText);
        }
    }

    private void setTextViewValue(Double value) {
        boolean isWholeValue = value % 1 == 0;
        if (isWholeValue) {
            u5h_tekst_viyu.setText(String.format("%.0f", value)); u2b_tekst_viyu.setText(String.format("%.0f", value));
        } else {
            u5h_tekst_viyu.setText(Double.toString(value)); u2b_tekst_viyu.setText(Double.toString(value));
        }
    }

    private void disableOperatorButtons() { setOperatorButtonsClickable(false); }
    private void enableOperatorButtons() { setOperatorButtonsClickable(true); }
    private void disableAllButtons() { setNumberButtonsClickable(10, false);setLetterButtonsClickable(false); }
//    private void enableButtonsBIN() { disableAllButtons();setNumberButtonsClickable(2, true); }
//    private void enableButtonsOCT() { disableAllButtons();setNumberButtonsClickable(8, true); }
    private void enableButtonsDEC() { disableAllButtons();setNumberButtonsClickable(10, true); }
    private void enableButtonsALL() { setLetterButtonsClickable(true);setNumberButtonsClickable(10, true); }

    private void setLetterButtonsClickable(boolean mode) {
        int[] letterButtonIDs = new int[]{R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF};
        for (int buttonID : letterButtonIDs) { Button button = getView().findViewById(buttonID);button.setEnabled(mode); }
    }

    private void setNumberButtonsClickable(int range, boolean mode) {
        for (int i = 0; i < range; i++) {
            Button button = getView().findViewById(getResources().getIdentifier("button" + i, "id", packageName));
            button.setEnabled(mode);
        }
    }

    private void setOperatorButtonsClickable(boolean mode) {
        int[] letterButtonIDs = new int[]{
                R.id.button_decimal_point,  R.id.buttonRDivide,
//                R.id.buttonFdivide,
                R.id.buttonMod,
                R.id.buttonMinus, R.id.buttonPlus , R.id.buttonMultiply, R.id.buttonPower,
                R.id.button_decimal_point, R.id.buttonEquals,
                R.id.button_zor, R.id.buttonOR, R.id.buttonNOT , R.id.buttonAND , R.id.buttonLSH , R.id.buttonRSH,
        };
        for (int buttonID : letterButtonIDs) { Button button = getView().findViewById(buttonID);button.setEnabled(mode); }
    }
}
