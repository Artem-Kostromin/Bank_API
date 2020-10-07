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
                "<h2>available commands:</h2>" +
                "<a href=\"http://localhost:8080/createAccount\">создание аккаута</a>";

        httpExchange.sendResponseHeaders(200, s.length());

        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
