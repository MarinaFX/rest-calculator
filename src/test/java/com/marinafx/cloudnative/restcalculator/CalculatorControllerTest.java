package com.marinafx.cloudnative.restcalculator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculatorControllerTest {

    @Test
    public void testGetLogsMediaTypeReturn() throws IOException {
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet("http://localhost:8085/logs");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    public void testAddition() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8085/calculate/sum/100/15");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertThat(response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.OK.value()));
    }

    @Test
    public void testExponentiationBody() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8085/calculate/pow/2/5");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        HttpEntity entity = response.getEntity();

        Assert.assertThat(EntityUtils.toString(entity),
                equalTo("{ \n \"Service\"   : \"Calculator\", \n" +
                        " \"Operation\" : \"2.0 ^ 5.0\", \n" +
                        " \"Answer\"    : \"32.0\" \n}"));
    }

    @Test
    public void testWrongOperation() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8085/calculate/add/2/5");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        HttpEntity entity = response.getEntity();

        Assert.assertThat(EntityUtils.toString(entity),
                equalTo("{ \n \"Service\" : \"Calculator\", \n" +
                        " \"Answer\"  : \"No operations of such 'add' were found\" \n}"));
    }

    @Test
    public void testWrongOperationStatusCode() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8085/calculate/add/2/5");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertThat(response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.BAD_REQUEST.value()));
    }
}
