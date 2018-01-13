package sample.Models;

public class Card {
    private String type;
    private String value;
    private int numericalValue;
    private int additionalNumericalValue;

    public Card(String type, String value)
    {
        this.type = type;
        this.value = value;
        this.numericalValue = 0;
    }

    public Card(String type, String value, int numericalValue)
    {
        this.type = type;
        this.value = value;
        this.numericalValue = numericalValue;
    }

    public Card(String type, String value, int numericalValue, int additionalNumericalValue)
    {
        this.type = type;
        this.value = value;
        this.numericalValue = numericalValue;
        this.additionalNumericalValue = additionalNumericalValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public int getAdditionalNumericalValue() {
        return additionalNumericalValue;
    }

    public void setAdditionalNumericalValue(int additionalNumericalValue) {
        this.additionalNumericalValue = numericalValue;
    }
}
