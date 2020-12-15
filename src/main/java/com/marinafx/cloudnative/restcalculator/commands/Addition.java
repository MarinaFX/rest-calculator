package com.marinafx.cloudnative.restcalculator.commands;


public class Addition extends Operation{

    @Override
    public double calculate(){
        return getX() + getY();
    }

    @Override
    public String toString() {
        this.setName("Addition");
        return "\"Operation\" : \"" + super.getName() +"\", \n" + "\"Expression\": \"" + getX() + " + " + getY() + "\"";
    }
}
