package com.marinafx.cloudnative.restcalculator.controller;


import com.marinafx.cloudnative.restcalculator.commands.Operation;
import com.marinafx.cloudnative.restcalculator.exception.NoSuchOperationExists;
import com.marinafx.cloudnative.restcalculator.service.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CalculatorController {

    private final Calculator calculator;

    @Autowired
    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping(value = "calculate/{operation}/{n1}/{n2}")
    public ResponseEntity<String> calculate(
            @PathVariable("operation") String op,
            @PathVariable("n1") double n1,
            @PathVariable("n2") double n2) {
        try {
            double result = calculator.execute(n1, n2, op);
            return new ResponseEntity<>(
                    "{ \n \"Service\"   : \"Calculator\", \n" +
                            " \"Operation\" : \"" + n1 + " " + parseOperation(op) + " " + n2 + "\", \n" +
                            " \"Answer\"    : \"" + result + "\" \n}",
                    HttpStatus.OK
            );
        } catch (NoSuchOperationExists e) {
            return new ResponseEntity<>(
                    "{ \n \"Service\" : \"Calculator\", \n" +
                            " \"Answer\"  : \"" + e.getMessage() + "\" \n}",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping(value = "/logs")
    public ResponseEntity<String> getLogs() {
        return new ResponseEntity<>(
                "{ \n \"Service\" : \"Logs\", \n" +
                        " \"History\" : " + buildLogs() + "\n}",
                HttpStatus.OK
        );
    }

    private StringBuilder buildLogs() {
        List<Operation> history = calculator.getHistory();
        StringBuilder result = new StringBuilder();

        if (history.size() == 0){
            result.append("{ }");
        }
        else {
            if (history.size() == 1) {
                result.append("{ \n");
                result.append(history.get(0).toString());
                result.append(" \n}");
            } else {
                result.append("[\n{\n");
                for (int i = 0; i < history.size(); i++) {
                    if (i - history.size() == -1) {
                        result.append(history.get(i).toString());
                    } else {
                        result.append(history.get(i).toString());
                        result.append(", \n");
                    }
                }
                result.append(" \n}\n]");
            }
        }

        return result;
    }

    private String parseOperation(String op) {
        switch (op.toLowerCase()) {
            case "sum":
                return "+";
            case "sub":
                return "-";
            case "multiplication":
                return "*";
            case "division":
                return "/";
            case "pow":
                return "^";
            default:
                throw new NoSuchOperationExists("Could not parse operation because no symbol matched '" + op + "'");
        }
    }
}
