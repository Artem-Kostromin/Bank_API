package ru.sberstart.handler.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;

@AllArgsConstructor
public class CreateAccountHandler implements HttpHandler {
    private final AccountService service;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        Account account = new Account();
        long id = service.persist(account).getId();
        account.setId(id);

        new ResponseMaker<Account>().makeResponse(account, httpExchange);
    }
}
