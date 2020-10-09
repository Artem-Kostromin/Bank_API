package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.entity.Account;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestAccountHandler {
    private final CloseableHttpClient client = HttpClients.createDefault();

    @BeforeClass
    public static void startServer() throws IOException {
        new Bootstrap().startApp();
    }

    @Test
    public void createAccountHandlerTest() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8091/createAccount");
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(is, Account.class);

        Assert.assertTrue(account.getId() > 0);
    }

    @Test
    public void getAccountsHandlerTest() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8091/getAccounts");
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        String result = IOUtils.toString(is, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Account[] accounts = mapper.readValue(result, Account[].class);

        Assert.assertTrue(accounts.length > 1);
    }

    @Test
    public void getAccountHandlerTest() throws IOException {
        int id = 1;
        HttpPost request = new HttpPost("http://localhost:8091/getAccount?id="+id);
        String json = "{" +
                "\"id\":\""+id+"\"" +
                "}";
        request.setEntity(new StringEntity(json));
        CloseableHttpResponse response = client.execute(request);
        String result = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(result, Account.class);

        Assert.assertEquals(id, account.getId());
    }

    @Test
    public void removeAccountHandlerTest() throws IOException {
        int id = 2;
        HttpPost request = new HttpPost("http://localhost:8091/removeAccount?id="+id);
        String json = "{" +
                "\"id\":\""+id+"\"" +
                "}";
        request.setEntity(new StringEntity(json));
        CloseableHttpResponse response = client.execute(request);
        String result = EntityUtils.toString(response.getEntity());

        Assert.assertNotNull(result);
    }
}
