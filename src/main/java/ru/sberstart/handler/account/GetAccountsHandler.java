package ru.sberstart.handler.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;

import java.util.List;

@AllArgsConstructor
public class GetAccountsHandler implements HttpHandler {
    private final AccountService aService;
    private final CardService cService;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        List<Account> accounts = aService.findAll();
        accounts.forEach(a -> a.setCards(cService.findAllByAccount(a.getId())));
        new ResponseMaker<List<Account>>().makeResponse(accounts, httpExchange);
    }
}
