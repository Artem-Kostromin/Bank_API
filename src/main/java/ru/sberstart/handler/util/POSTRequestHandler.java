package ru.sberstart.handler.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;

import java.io.IOException;
import java.io.InputStream;

public class POSTRequestHandler {
    public static Account handleAccountPostRequest(HttpExchange httpExchange) {
        InputStream is = httpExchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Account account = null;
        try {
            account = mapper.readValue(is, Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }
    public static Card handleCardPostRequest(HttpExchange httpExchange){
        InputStream is = httpExchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Card card = null;
        try {
            card = mapper.readValue(is, Card.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return card;
    }
}
