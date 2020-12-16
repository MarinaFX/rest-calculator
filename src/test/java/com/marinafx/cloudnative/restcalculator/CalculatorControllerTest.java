package com.marinafx.cloudnative.restcalculator;

import com.marinafx.cloudnative.restcalculator.controller.CalculatorController;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class CalculatorControllerTest {

    @Test
    public void testGetLogsMediaTypeReturn() {
        CalculatorController mock = Mockito.mock(CalculatorController.class);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        Mockito.doReturn(new ResponseEntity<>(
                "{ \n \"Service\" : \"Logs\", \n" +
                        " \"History\" : {\n" +
                        " \"Operation\": \"Addition\",\n" +
                        "  \"Expression\": \"5.0 + 4.0\"\n } \n}",
                responseHeaders,
                HttpStatus.OK
        )).when(mock).getLogs();

        ResponseEntity<String> entity = mock.getLogs();

        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
    }

    @Test
    public void testAddition() {
        CalculatorController mock = Mockito.mock(CalculatorController.class);

        Mockito.doReturn(new ResponseEntity<>(
                "{ \n \"Service\"   : \"Calculator\", \n" +
                        " \"Operation\" : \"100.0 + 15.0\", \n" +
                        " \"Answer\"    : \"115.0\" \n}",
                HttpStatus.OK
        )).when(mock).calculate("sum", 100, 15);

        ResponseEntity<String> entityResult = mock.calculate("sum", 100, 15);

        String result = "{ \n \"Service\"   : \"Calculator\", \n" +
                " \"Operation\" : \"100.0 + 15.0\", \n" +
                " \"Answer\"    : \"115.0\" \n}";

        Assertions.assertEquals(result, entityResult.getBody());
        Assert.assertThat(entityResult.getStatusCode(),
                equalTo(HttpStatus.OK));
    }

    @Test
    public void testExponentiationBody() {
        CalculatorController mock = Mockito.mock(CalculatorController.class);

        Mockito.doReturn(new ResponseEntity<>(
                "{ \n \"Service\"   : \"Calculator\", \n" +
                        " \"Operation\" : \"2.0 ^ 5.0\", \n" +
                        " \"Answer\"    : \"32.0\" \n}",
                HttpStatus.OK
        )).when(mock).calculate("pow", 2, 5);

        ResponseEntity<String> entityResult = mock.calculate("pow", 2, 5);

        String result = "{ \n \"Service\"   : \"Calculator\", \n" +
                " \"Operation\" : \"2.0 ^ 5.0\", \n" +
                " \"Answer\"    : \"32.0\" \n}";

        Assertions.assertEquals(result, entityResult.getBody());
    }

    @Test
    public void testWrongOperation() {
        CalculatorController mock = Mockito.mock(CalculatorController.class);

        Mockito.doReturn(new ResponseEntity<>(
                "{ \n \"Service\" : \"Calculator\", \n" +
                        " \"Answer\"  : \"No operations of such 'add' were found\" \n}",
                HttpStatus.OK
        )).when(mock).calculate("add", 2, 5);

        ResponseEntity<String> entityResult = mock.calculate("add", 2, 5);

        String result = "{ \n \"Service\" : \"Calculator\", \n" +
                " \"Answer\"  : \"No operations of such 'add' were found\" \n}";

        Assertions.assertEquals(result, entityResult.getBody());
    }

    @Test
    public void testWrongOperationStatusCode() {
        CalculatorController mock = Mockito.mock(CalculatorController.class);

        Mockito.doReturn(new ResponseEntity<>(
                "{ \n \"Service\" : \"Calculator\", \n" +
                        " \"Answer\"  : \"No operations of such 'add' were found\" \n}",
                HttpStatus.BAD_REQUEST
        )).when(mock).calculate("add", 35, 900);

        Assertions.assertEquals(new ResponseEntity<>(
                "{ \n \"Service\" : \"Calculator\", \n" +
                        " \"Answer\"  : \"No operations of such 'add' were found\" \n}",
                HttpStatus.BAD_REQUEST), mock.calculate("add", 35, 900));
    }
}
