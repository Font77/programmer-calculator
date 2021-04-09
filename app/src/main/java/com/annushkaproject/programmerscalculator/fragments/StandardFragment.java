package com.annushkaproject.programmerscalculator.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.annushkaproject.programmerscalculator.R;
import com.annushkaproject.programmerscalculator.activities.HistoryActivity;
import com.annushkaproject.programmerscalculator.managers.HistoryManager;
import com.annushkaproject.programmerscalculator.model.CalculationModel;
import com.annushkaproject.programmerscalculator.model.Operator;
import com.annushkaproject.programmerscalculator.model.ProgrammerCalcModel;
import com.annushkaproject.programmerscalculator.utils.InstanceStateUtil;
import com.annushkaproject.programmerscalculator.utils.StandardOperationsUtil;

import java.util.ArrayList;

public class StandardFragment extends Fragment {

    private TextView textView;
    private ArrayList<Button> numberButtons = new ArrayList<>();
    private ArrayList<Button> operatorButtons = new ArrayList<>();

    private ProgrammerCalcModel calcModel = new ProgrammerCalcModel();
    private boolean secondValueInputStarted = false;

    private String packageName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (packageName == null) {
            packageName = savedInstanceState.getString("PACKAGE_NAME");
            calcModel = InstanceStateUtil.restoreSavedInstance(savedInstanceState);
        }
        View view = inflater.inflate(R.layout.fragment_standard, container, false);
        Button btnHistory = view.findViewById(R.id.buttonHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = getView().findViewById(R.id.u5h_tekst_viyu);

        fillNumberButtons();
        fillOperatorButtons();

        setupCalculateButton();
        setupDeleteButton();
        setupClearButton();
        setupSignButton();
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) { super.onSaveInstanceState(outState);
        InstanceStateUtil.saveInstanceState(outState, calcModel, packageName);
    }

    public void setupFragment(String packageName) {
        this.packageName = packageName;
    }

    private void fillNumberButtons() {
        for (int i = 0; i < 10; i++) {
            Button button = getView().findViewById(getResources().getIdentifier("button" + i, "id",
                    packageName));
            button.setOnClickListener(v -> {
                Button button1 = (Button)v;
                System.out.println(button1.getText().toString());

                usePressedNumber(button1.getText().toString());
            }
            );

            numberButtons.add(button);
        }

        //Adding "." button separately
        Button button = getView().findViewById(R.id.button_decimal_point);
        button.setOnClickListener(v -> {
            Button button12 = (Button)v;
            System.out.println(button12.getText().toString());

            if (!currentString().contains(".")) {
                usePressedNumber(button12.getText().toString());
            }
        }
        );
        numberButtons.add(button);

        Button buttonPi = getView().findViewById(R.id.buttonPi);
        buttonPi.setOnClickListener(v -> {
                    Button button13 = (Button)v;
                    System.out.println(button13.getText().toString());

                    usePiPressedNumber();
                }
        );
        numberButtons.add(buttonPi);
    }

    private String currentString() {
        return textView.getText().toString();
    }

    private void fillOperatorButtons() {
        operatorButtons.add(getView().findViewById(R.id.buttonPlus));
        operatorButtons.add(getView().findViewById(R.id.buttonMinus));
        operatorButtons.add(getView().findViewById(R.id.buttonRDivide));
        operatorButtons.add(getView().findViewById(R.id.buttonMultiply));
        operatorButtons.add(getView().findViewById(R.id.buttonPercent));

        if (isInLandscapeOrientation()) {
            operatorButtons.add(getView().findViewById(R.id.buttonAsin));
            operatorButtons.add(getView().findViewById(R.id.buttonAcos));
            operatorButtons.add(getView().findViewById(R.id.buttonAtan));

            operatorButtons.add(getView().findViewById(R.id.buttonSin));
            operatorButtons.add(getView().findViewById(R.id.buttonCos));
            operatorButtons.add(getView().findViewById(R.id.buttonTan));

            operatorButtons.add(getView().findViewById(R.id.buttonLn));
            operatorButtons.add(getView().findViewById(R.id.buttonLog));
            operatorButtons.add(getView().findViewById(R.id.buttonRev));

            operatorButtons.add(getView().findViewById(R.id.buttonEpow));
            operatorButtons.add(getView().findViewById(R.id.buttonSquare));
            operatorButtons.add(getView().findViewById(R.id.buttonPower));

            operatorButtons.add(getView().findViewById(R.id.buttonAbs));
            operatorButtons.add(getView().findViewById(R.id.buttonSqrt));
            operatorButtons.add(getView().findViewById(R.id.buttonFactorial));
        }

        for (Button button : operatorButtons) {
            button.setOnClickListener(v -> {
                Button button1 = (Button)v;
                System.out.println(button1.getText().toString());

                Operator operator = Operator.operatorForTitle(button1.getText().toString());
                usePressedOperator(operator);
            }

            );
        }
    }

    private void setupCalculateButton() {
        Button equalsButton = getView().findViewById(R.id.buttonEquals);
        equalsButton.setOnClickListener(v -> {
            Button button = (Button)v;
            System.out.println(button.getText().toString());

            useEqualsOperator();
        }
        );
    }

    private void setupDeleteButton() {
        Button delButton = getView().findViewById(R.id.buttonDel);
        delButton.setOnClickListener(v -> {
            String currentString = currentString();
            Boolean isOneDigit = currentString.length() == 1;
            Boolean isOneNegativeDigit = currentString.length() == 2 && currentString.startsWith("-");
            if (isOneDigit || isOneNegativeDigit) {
                updateText(calcModel.textForValue(0.0));
            } else {
                currentString = currentString.substring(0, currentString.length() - 1);
                updateText(currentString);
            }
        });
    }

    private void setupClearButton() {
        Button clearButton = getView().findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(v -> {
            calcModel.resetCalcState();
            updateText(calcModel.textForValue(0.0));
        });
    }

    private void setupSignButton() {
        Button signButton = getView().findViewById(R.id.buttonSign);
        signButton.setOnClickListener(v -> {
            double currentValue = Double.parseDouble(currentString());

            if (currentValue == 0) { //do not make "-0"
                return;
            }

            String updatedString = currentString();
            if (currentValue > 0) {
                updatedString = "-" + updatedString;
            } else {
                updatedString = updatedString.substring(1);
            }

            updateText(updatedString);
        });
    }

    private void usePressedNumber(String number) {
        if ((currentString().equals("0") && !number.equals(".")) || currentString().equals(getString(R.string.not_a_number))) {
            textView.setText(""); //clear text view from 0 value.
        }

        String newString;
        if (secondValueInputStarted) {
            newString = number;
            secondValueInputStarted = false;
        } else {
            newString = textView.getText().toString() + number;
        }

        updateText(newString);
    }

    private void usePiPressedNumber() {
        String newString = (Double.toString(Math.PI));
        if (secondValueInputStarted) {
            secondValueInputStarted = false;
        } else {
            calcModel.resetCalcState();
        }

        updateText(newString);
    }

    private void usePressedOperator(Operator operator) {
        boolean readyToSaveOperator = calcModel.getFirstValue() != null;
        if (!readyToSaveOperator) {
            return;
        }

        if (!operator.requiresTwoValues()) {
            if (operator == Operator.FACTORIAL && !calcModel.isFirstIntegerValue()) {
                handleNotANumberCase();
                return;
            }

            double result;
            if (calcModel.getSecondValue() == null) {
                //apply to first value
                double number = calcModel.getFirstValue().doubleValue();
                result = StandardOperationsUtil.calculateResultForOneSidedOperator(number, operator);
                HistoryManager.getSharedInstance().save(calcModel.textForValue(result));
                calcModel.setFirstValue(result);
            } else if (operator == Operator.PERCENT && calcModel.getSecondValue() != null) {
                //special case for 6 - 10%
                result = StandardOperationsUtil.calculatePercentForData(calcModel);
                calcModel.setSecondValue(result);
            } else {
                //apply to second value
                double number = calcModel.getSecondValue().doubleValue();
                result = StandardOperationsUtil.calculateResultForOneSidedOperator(number, operator);
                calcModel.setSecondValue(result);
            }

            String text = calcModel.textForValue(result);
            textView.setText(text);

            return;
        }

        boolean readyToCalcTwoSides = calcModel.getOperator() != null &&
                                      calcModel.getFirstValue() != null &&
                                      calcModel.getSecondValue() != null;
        if (readyToCalcTwoSides) {
            calculateResult();
        } else {
            secondValueInputStarted = true;
        }

        calcModel.setOperator(operator);
    }

    private void useEqualsOperator() {
        if (calcModel.getOperator() == null) {
            return;
        }

        calculateResult();
    }

    private void calculateResult() {
        if (calcModel.isNotNumber()) {
            handleNotANumberCase();
            return;
        }

        double result = StandardOperationsUtil.calculateResultForTwoSidedOperator(calcModel);
        HistoryManager.getSharedInstance().save(calcModel.textForValue(result));

        calcModel.resetCalcState();

        calcModel.updateAfterCalculation(result);
        updateText(calcModel.textForValue(result));

        secondValueInputStarted = true;
    }

    private void handleNotANumberCase() {
        calcModel.resetCalcState();
        textView.setText(getString(R.string.not_a_number));
    }

    private void updateText(String updatedText) {
        if (updatedText.length() == StandardOperationsUtil.SCALE) {
            showDigitsLimitWarning();
            return;
        }

        textView.setText(updatedText);
        if (updatedText.length() > 10) {
            textView.setTextSize(24);
        } else {
            textView.setTextSize(30);
        }
        calcModel.updateValues(updatedText);
    }

    private void showDigitsLimitWarning() {
        Context context = getActivity().getApplicationContext();
        Toast.makeText(context, R.string.max_digits_warning, Toast.LENGTH_SHORT).show();
    }

    private boolean isInLandscapeOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
