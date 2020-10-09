package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.sberstart.entity.Card;

import java.io.IOException;
import java.math.BigDecimal;

public class TestCardHandler {
    private final CloseableHttpClient client = HttpClients.createDefault();
    private final String rootUrl = "http://localhost:8080/";

    @BeforeClass
    public static void startServer() throws IOException {
        new Bootstrap().startApp();
    }

    @Test
    public void createCardTest() throws IOException {
        HttpPost post = new HttpPost(rootUrl + "createCard");

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"accountId\":\"1\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());

        System.out.println(result);
        ObjectMapper mapper = new ObjectMapper();
        Card card = mapper.readValue(result, Card.class);

        Assert.assertTrue(card.getId() > 0);
    }

    @Test
    public void getCardTest() throws IOException {
        HttpPost post = new HttpPost(rootUrl + "getCard");

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"2\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        Card card = mapper.readValue(result, Card.class);
        Assert.assertEquals(2, card.getId());
    }

    @Test
    public void getCardsTest() throws IOException {
        HttpGet request = new HttpGet(rootUrl + "getCards");
        CloseableHttpResponse response = client.execute(request);

        String result = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        Card[] cards = mapper.readValue(result, Card[].class);

        Assert.assertTrue(cards.length > 0);
    }

    @Test
    public void getCardsByAccountIdTest() throws IOException {
        HttpPost post = new HttpPost(rootUrl + "getCardsByAccount");

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"1\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        Card[] cards = mapper.readValue(result, Card[].class);
        Assert.assertTrue(cards.length > 0);
    }

    @Test
    public void checkBalanceTest() throws IOException {
        HttpPost post = new HttpPost(rootUrl + "checkBalance");

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"1\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());

        System.out.println(result);
        ObjectMapper mapper = new ObjectMapper();
        BigDecimal balance = mapper.readValue(result, BigDecimal.class);
        Assert.assertEquals(BigDecimal.valueOf(500), balance);
    }

    @Test
    public void updateBalanceTest() throws IOException {
        HttpPost post = new HttpPost(rootUrl + "updateBalance");

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"1\",");
        json.append("\"accountId\":\"1\",");
        json.append("\"balance\":\"900\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());
        Assert.assertEquals("\"Balance of cards with id = 1 now equals 900\"", result);
    }

    @Test
    public void removeCardTest() throws IOException {
        HttpPost post = new HttpPost(rootUrl + "removeCard");
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"1\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());

        Assert.assertEquals("\"Card with id 1 removed success!\"", result);
    }


}
