package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.entity.Account;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestAccountHandler {
    private final CloseableHttpClient client = HttpClients.createDefault();

    @Before
    public void initMockServer() throws IOException {

    }

    @Test
    public void createAccountHandlerTest() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/createAccount");
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(is, Account.class);

        Assert.assertTrue(account.getId() > 0);
    }

    @Test
    public void getAccountsHandlerTest() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/getAccounts");
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        String result = IOUtils.toString(is, StandardCharsets.UTF_8);
        System.out.println(result);

        ObjectMapper mapper = new ObjectMapper();
        Account[] accounts = mapper.readValue(result, Account[].class);

        Assert.assertTrue(accounts.length > 1);
    }
}
