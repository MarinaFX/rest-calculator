package com.marinafx.cloudnative.restcalculator.config;

import com.marinafx.cloudnative.restcalculator.commands.Multiplication;
import com.marinafx.cloudnative.restcalculator.commands.Subtraction;
import com.marinafx.cloudnative.restcalculator.service.Calculator;
import com.marinafx.cloudnative.restcalculator.commands.Addition;
import com.marinafx.cloudnative.restcalculator.commands.Division;
import com.marinafx.cloudnative.restcalculator.commands.Pow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan(basePackages = "com.marinafx.cloudnative.restcalculator")
public class ApplicationConfig {

    @Bean
    @Autowired
    public Calculator calculator(BeanFactory beanFactory){
        return new Calculator(beanFactory);
    }

    @Bean(name = "sum")
    @Scope(value = "prototype")
    public Addition sum(){
        return new Addition();
    }

    @Bean(name = "sub")
    @Scope(value = "prototype")
    public Subtraction sub(){
        return new Subtraction();
    }

    @Bean(name = "multiplication")
    @Scope(value = "prototype")
    public Multiplication multiply(){
        return new Multiplication();
    }

    @Bean(name = "division")
    @Scope(value = "prototype")
    public Division divide(){
        return new Division();
    }

    @Bean(name = "pow")
    @Scope(value = "prototype")
    public Pow pow(){
        return new Pow();
    }
}
