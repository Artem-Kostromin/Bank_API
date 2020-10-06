package ru.sberstart.handler.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.service.AccountService;

import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class RemoveAccountHandler implements HttpHandler {
    private final AccountService service;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        String id = handleGetRequest(httpExchange);
        boolean isRemove = service.removeOne(Integer.parseInt(id));
        String answer = null;
        if(isRemove) {
            answer = "remove were success";
        } else {
            answer = "remove failed";
        }

        httpExchange.sendResponseHeaders(200, answer.length());

        outputStream.write(answer.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }
}
