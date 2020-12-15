package com.marinafx.cloudnative.restcalculator.commands;

public class Subtraction extends Operation{

    @Override
    public double calculate() {
        return getX() - getY();
    }

    @Override
    public String toString() {
        this.setName("Subtraction");
        return "\"Operation\" : \"" + super.getName() +"\", \n" + "\"Expression\": \"" + getX() + " - " + getY() + "\"";
    }
}
