package com.annushkaproject.programmerscalculator.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.annushkaproject.programmerscalculator.R;
import com.annushkaproject.programmerscalculator.model.CalculationModel;
import com.annushkaproject.programmerscalculator.model.Operator;
import com.annushkaproject.programmerscalculator.utils.StandardOperationsUtil;

import java.util.ArrayList;

public class StandardFragment extends Fragment {

    private TextView textView;
    private ArrayList<Button> numberButtons = new ArrayList<>();
    private ArrayList<Button> operatorButtons = new ArrayList<>();

    private CalculationModel calcModel = new CalculationModel();
    private boolean secondValueInputStarted = false;

    private String packageName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (packageName == null) {
            packageName = savedInstanceState.getString("PACKAGE_NAME");
            if (savedInstanceState.getBoolean("FIRST_VALUE_SAVED")) {
                calcModel.setFirstValue(savedInstanceState.getDouble("FIRST_VALUE"));
            }
            if (savedInstanceState.getBoolean("OPERATOR_SAVED")) {
                calcModel.setOperator(Operator.getOperatorByNumber(savedInstanceState.getInt("OPERATOR")));
            }
            if (savedInstanceState.getBoolean("SECOND_VALUE_SAVED")) {
                calcModel.setSecondValue(savedInstanceState.getDouble("SECOND_VALUE"));
            }
        }
        return inflater.inflate(R.layout.fragment_standard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = getView().findViewById(R.id.inputField);

        fillNumberButtons();
        fillOperatorButtons();

        setupCalculateButton();
        setupDeleteButton();
        setupClearButton();
        setupSignButton();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("PACKAGE_NAME", packageName);
        boolean firstValuePresent = calcModel.getFirstValue() != null;
        boolean operatorPresent = calcModel.getOperator() != null;
        boolean secondValuePresent = calcModel.getSecondValue() != null;
        if (firstValuePresent) {
            outState.putDouble("FIRST_VALUE", calcModel.getFirstValue().getNumber());
        }
        if (operatorPresent) {
            outState.putInt("OPERATOR", Operator.getNumberByOperator(calcModel.getOperator()));
        }
        if (secondValuePresent) {
            outState.putDouble("SECOND_VALUE", calcModel.getSecondValue().getNumber());
        }
        outState.putBoolean("FIRST_VALUE_SAVED", firstValuePresent);
        outState.putBoolean("OPERATOR_SAVED", operatorPresent);
        outState.putBoolean("SECOND_VALUE_SAVED", secondValuePresent);
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
        Button button = getView().findViewById(R.id.buttonComma);
        button.setOnClickListener(v -> {
            Button button12 = (Button)v;
            System.out.println(button12.getText().toString());

            if (!currentString().contains(".")) {
                usePressedNumber(button12.getText().toString());
            }
        }
        );
        this.numberButtons.add(button);
    }

    private String currentString() {
        return textView.getText().toString();
    }

    private void fillOperatorButtons() {
        //TODO: add additional operators for landscape.

        this.operatorButtons.add(getView().findViewById(R.id.buttonPlus));
        this.operatorButtons.add(getView().findViewById(R.id.buttonMinus));
        this.operatorButtons.add(getView().findViewById(R.id.buttonDivide));
        this.operatorButtons.add(getView().findViewById(R.id.buttonMultiply));
        this.operatorButtons.add(getView().findViewById(R.id.buttonPercent));

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
            if (currentString.length() > 1) {
                currentString = currentString.substring(0, currentString.length() - 1);
                updateText(currentString);
            } else {
                updateText(calcModel.textForValue(0.0));
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

    public void setupSignButton() {
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
        if (currentString().equals("0") && !number.equals(".")) {
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

    private void usePressedOperator(Operator operator) {
        boolean readyToSaveOperator = calcModel.getFirstValue() != null;
        if (!readyToSaveOperator) {
            return;
        }

        boolean readyToCalcOneSide = !operator.requiresTwoValues() &&
                                     calcModel.getFirstValue() != null;
        if (readyToCalcOneSide) {
            calcModel.setOperator(operator);
            calculateResult();
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
        double result = StandardOperationsUtil.calculateWithData(calcModel);

        calcModel.resetCalcState();

        calcModel.updateAfterCalculation(result);
        updateText(calcModel.textForValue(result));

        secondValueInputStarted = true;
    }

    private void updateText(String updatedText) {
        textView.setText(updatedText);
        calcModel.updateValues(updatedText);
    }

}
