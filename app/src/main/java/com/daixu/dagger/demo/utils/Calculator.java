package com.daixu.dagger.demo.utils;

public class Calculator {

    public double sum(double a, double b) {
        return a + b;
    }

    public double substract(double a, double b) {
        return a - b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Dividor cannot be 0");
        }
        return a / b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }
}