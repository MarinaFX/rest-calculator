package com.marinafx.cloudnative.restcalculator.commands;

public class Pow extends Operation{

    @Override
    public double calculate() {
        return Math.pow(getX(), getY());
    }

    @Override
    public String toString() {
        this.setName("Exponentiation");
        return "\"Operation\" : \"" + super.getName() +"\", \n" + "\"Expression\": \"" + getX() + " ^ " + getY() + "\"";
    }
}
