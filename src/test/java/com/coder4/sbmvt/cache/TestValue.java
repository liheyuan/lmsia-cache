package com.coder4.sbmvt.cache;

/**
 * @author coder4
 */
public class TestValue {
    private String val1;
    private String val2;

    public TestValue() {

    }

    public TestValue(String val1, String val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TestValue{");
        sb.append("val1='").append(val1).append('\'');
        sb.append(", val2='").append(val2).append('\'');
        sb.append('}');
        return sb.toString();
    }
}