package ru.sberstart.handler.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.service.AccountService;

import java.io.OutputStream;

@AllArgsConstructor
public class CreateAccountHandler implements HttpHandler {
    private final AccountService service;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        Account account = new Account();
        long id = service.persist(account).getId();
        account.setId(id);

        OutputStream outputStream = httpExchange.getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String s = writer.writeValueAsString(account);

        httpExchange.sendResponseHeaders(200, s.length());

        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
