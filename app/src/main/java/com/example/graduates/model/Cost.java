package com.example.graduates.model;

public class Cost {
    private double in;
    private double out;
    private double input;
    private double output;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getIn() {
        return in;
    }

    public void setIn(double in) {
        this.in = in;
    }

    public double getOut() {
        return out;
    }

    public void setOut(double out) {
        this.out = out;
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "in=" + in +
                ", out=" + out +
                ", input=" + input +
                ", output=" + output +
                ", email='" + email + '\'' +
                '}';
    }
}
