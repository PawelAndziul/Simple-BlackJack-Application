package sample.Models;

public class PointValues {
    private Integer mainValue;
    private Integer additionalValue;

    public PointValues(Integer mainValue, Integer additionalValue) {
        this.mainValue = mainValue;
        this.additionalValue = additionalValue;
    }

    public PointValues(Integer mainValue) {
        this.mainValue = mainValue;
        this.additionalValue = 0;
    }


    public Integer getMainValue() {
        return mainValue;
    }

    public void setMainValue(Integer mainValue) {
        this.mainValue = mainValue;
    }

    public Integer getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(Integer additionalValue) {
        this.additionalValue = additionalValue;
    }
}
