package ru.sberstart.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class MainHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        String s = "<h1>Welcome to Bank_API</h1>" +
                "<b></b>" +
                "<h2>API for accounts:</h2>" +
                "<h3>http://localhost:8091/createAccount - create account</h3>" +
                "<h3>http://localhost:8091/getAccount?id={} - get account by id</h3>" +
                "<h3>http://localhost:8091/getAccounts - get all accounts</h3>" +
                "<h3>http://localhost:8091/removeAccount?id={} - remove account by id (cascade removes cards)</h3>" +
                "<h2>API for cards:</h2>" +
                "<h3>http://localhost:8091/createCard?accountId={} - create new card by account id</h3>" +
                "<h3>http://localhost:8091/getCard?id={} - get card by id</h3>" +
                "<h3>http://localhost:8091/getCards - get all cards</h3>" +
                "<h3>http://localhost:8091/getCardsByAccount?accountId={} - get all cards by account id</h3>" +
                "<h3>http://localhost:8091/checkBalance?id={} - check card balance</h3>" +
                "<h3>http://localhost:8091/updateBalance?id={}&balance={} - update card balance</h3>";

        httpExchange.sendResponseHeaders(200, s.length());

        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
