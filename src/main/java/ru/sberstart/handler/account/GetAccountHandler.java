package ru.sberstart.handler.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;

@AllArgsConstructor
public class GetAccountHandler implements HttpHandler {
    private final AccountService aService;
    private final CardService cService;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        int id = RequestParamTransformer.handleGetRequest(httpExchange);
        Account account = aService.findOne(id);
        account.setCards(cService.findAllByAccount(id));

        new ResponseMaker<Account>().makeResponse(account, httpExchange);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }
}
