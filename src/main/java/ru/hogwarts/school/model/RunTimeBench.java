package ru.hogwarts.school.model;


public class RunTimeBench {

    String testType;
    long leadTime;
    long calculatedValue;

    public RunTimeBench() {
    }

    public String getTestType() {
        return testType;
    }

    public long getLeadTime() {
        return leadTime;
    }

    public long getCalculatedValue() {
        return calculatedValue;
    }


    public void setTestType(String testType) {
        this.testType = testType;
    }

    public void setLeadTime(long leadTime) {
        this.leadTime = leadTime;
    }

    public void setCalculatedValue(long calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    @Override
    public String toString() {
        return "RunTimeBench{" +
                "testType='" + testType + '\'' +
                ", leadTime=" + leadTime +
                ", calculatedValue=" + calculatedValue +
                '}';
    }
}
