package com.marinafx.cloudnative.restcalculator.commands;

public class Multiplication extends Operation{

    @Override
    public double calculate() {
        return getX() * getY();
    }

    @Override
    public String toString() {
        this.setName("Multiplication");
        return "\"Operation\" : \"" + super.getName() +"\", \n" + "\"Expression\": \"" + getX() + " * " + getY() + "\"";
    }
}
