package ru.sberstart.handler.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.handler.util.POSTRequestHandler;
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
        Account account;
        long accountId;
        if("POST".equals(httpExchange.getRequestMethod())) {
            account = POSTRequestHandler.handleAccountPostRequest(httpExchange);
            accountId = account.getId();
        } else {
            accountId = RequestParamTransformer.handleGetRequest(httpExchange);
        }
        account = aService.findOne(accountId);
        account.setCards(cService.findAllByAccount(accountId));

        new ResponseMaker<Account>().makeResponse(account, httpExchange);
    }
}
