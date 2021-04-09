package com.annushkaproject.programmerscalculator.utils;

import com.annushkaproject.programmerscalculator.model.ProgrammerCalcModel;
import com.annushkaproject.programmerscalculator.model.Operator;

import java.math.RoundingMode;

public class StandardOperationsUtil {
    public static final int SCALE = 20;
    public static double calculateResultForTwoSidedOperator(ProgrammerCalcModel data) {
        if (data.getSecondValue() == null) data.setSecondValueEqualToFirst();
        if (!data.getOperator().requiresTwoValues()) return -1; /*TODO: throw an exception.*/
        double result;
        switch (data.getOperator()) {
            case ADD: result = data.getFirstValue().add(data.getSecondValue()).doubleValue();break;
            case SUBTRACT: result = data.getFirstValue().subtract(data.getSecondValue()).doubleValue();break;
            case MULTIPLY: result = data.getFirstValue().multiply(data.getSecondValue()).doubleValue();break;
            case remainder_divide: result = data.getFirstValue().divide(data.getSecondValue(), SCALE, RoundingMode.HALF_UP).doubleValue();break;
            case POWER: result = data.getFirstValue().pow(data.getSecondValue().intValue()).doubleValue();break;
            default: return 0;
        }
        return result;
    }

    /**
     * Used to calculate percents.
     * @param data Model for calculations.
     * @return Calculated result.
     */
    public static double calculatePercentForData(ProgrammerCalcModel data) {
        if (data.getSecondValue() == null) {
            return -1; //TODO: throw an exception.
        }

        double firstNumber = data.getFirstValue().doubleValue();
        double secondNumber = data.getSecondValue().doubleValue();

        double result = firstNumber / 100.0 * secondNumber;

        return result;
    }

    /**
     * Used to calculate the result for operators that require one value.
     * @param number Number for calculation.
     * @param operator Operator for calculation.
     * @return Calculated result.
     */
    public static double calculateResultForOneSidedOperator(double number, Operator operator) {
        if (operator.requiresTwoValues()) {
            return -1; //TODO: throw an exception.
        }

        switch (operator) {
            case PERCENT:
                return number / 100.0; //TODO: change to BigDecimal
            case ASIN:
                return Math.asin(number);
            case ACOS:
                return Math.acos(number);
            case ATAN:
                return Math.atan(number);
            case SIN:
                return Math.sin(number);
            case COS:
                return Math.cos(number);
            case TAN:
                return Math.tan(number);
            case LN:
                return Math.log(number);
            case LOG:
                return Math.log10(number);
            case DENOMINATOR:
                return 1.0 / number; //TODO: change to BigDecimal
            case EXPONENT_POWER:
                return Math.exp(number);
            case SQUARE:
                return number * number;
            case ABS:
                return Math.abs(number);
            case SQUARE_ROOT:
                return Math.sqrt(number);
            case FACTORIAL: return StandardOperationsUtil.calculateFactorial(number);
            default: return 0;
        }
    }
    static private double calculateFactorial(double value) {
        double result = 1;
        for (int i = 2; i <= value; i++) result *= i;
        return result;
    }
}
