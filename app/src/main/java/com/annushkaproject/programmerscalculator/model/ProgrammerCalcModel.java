package com.annushkaproject.programmerscalculator.model;
import java.math.BigDecimal;
import static com.annushkaproject.programmerscalculator.model.int_size_enum.l8;
//public class ProgrammerCalcModel extends CalculationModel {
public class ProgrammerCalcModel {
    private BigDecimal firstValue;
    private BigDecimal secondValue;
    private Operator operator;
    private int_size_enum bytelengthenum;
    private int nmbr_base;

    public ProgrammerCalcModel() { firstValue = null; secondValue = null; operator = null; this.bytelengthenum = l8; this.nmbr_base = 16 ;}
    public ProgrammerCalcModel(BigDecimal firstValue, Operator operator) {
        this.firstValue = firstValue; this.operator = operator; this.bytelengthenum = l8; this.nmbr_base = 16 ;
    }
    public ProgrammerCalcModel(BigDecimal firstValue, BigDecimal secondValue, Operator operator) {
        this.firstValue = firstValue; this.secondValue = secondValue; this.operator = operator; this.bytelengthenum = l8; this.nmbr_base = 16 ;
    }

//    public ProgrammerCalcModel() { this.bytelengthenum = l8; }
    public ProgrammerCalcModel(BigDecimal firstValue, Operator operator, int_size_enum bytelengthenum) {
        this.firstValue = firstValue; this.operator = operator; this.bytelengthenum = bytelengthenum; this.nmbr_base = 16 ;
    }

    public ProgrammerCalcModel(BigDecimal firstValue, BigDecimal secondValue, Operator operator, int_size_enum bytelengthenum) {
        this.firstValue = firstValue; this.secondValue = secondValue; this.operator = operator; this.bytelengthenum = bytelengthenum; this.nmbr_base = 16 ;
    }


    public BigDecimal getFirstValue() { return firstValue; }
    public BigDecimal getSecondValue() { return secondValue; }
    public Operator getOperator() { return operator; }
    public int get_nmbr_base() { return nmbr_base; }
    public int_size_enum getBytelengthenum() { return bytelengthenum; }

    public void setFirstValue(double value) { firstValue = BigDecimal.valueOf(value); }
    public void setSecondValue(double value) { secondValue = BigDecimal.valueOf(value); }
    public void setSecondValueEqualToFirst() { secondValue = firstValue.add(BigDecimal.ZERO); }
    public void setOperator(Operator operator) { this.operator = operator; }
    public void setNmbr_base(int argbase) {
        if (16 == argbase || 10 == argbase)
        {
            this.nmbr_base = argbase;
        }
    }
    /////////from calc
    public void resetCalcState() { firstValue = null; secondValue = null; operator = null; }
    public void updateValues(String text) {
        if (10 == this.nmbr_base) {
            if (operator == null) {
                setFirstValue(Double.parseDouble(text));
            } else {
                setSecondValue(Double.parseDouble(text));
            }
        }
        else if (16 == this.nmbr_base) {
            if (operator == null) {
                setFirstValue(Long.parseLong(text,16));
            } else {
                setSecondValue(Long.parseLong(text,16));
            }
        }
    }
    public void updateAfterCalculation(double calculationResult) { resetCalcState(); setFirstValue(calculationResult); }
    public String textForValue(double value) {
        boolean isWholeValue = value % 1 == 0;
        return isWholeValue ? String.format("%.0f", value) : (Double.toString(value));
    }
    public boolean isNotNumber() {
        return (operator == Operator.remainder_divide && secondValue.doubleValue() == 0) ||
                (operator == Operator.DENOMINATOR && firstValue.doubleValue() == 0);
    }
    public boolean isFirstIntegerValue() { return firstValue.stripTrailingZeros().scale() <= 0; }

    ////////// from calc
    public void setBytelengthenum(int_size_enum bytelengthenum) { this.bytelengthenum = bytelengthenum; }

}
