package ru.sberstart.handler.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.AccountRepository;

import java.io.OutputStream;
import java.util.List;

@AllArgsConstructor
public class GetAccountsHandler implements HttpHandler {
    private final AccountRepository repository;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        List<Account> accounts = repository.findAll();

        OutputStream outputStream = httpExchange.getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String s = writer.writeValueAsString(accounts);

        httpExchange.sendResponseHeaders(200, s.length());

        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
