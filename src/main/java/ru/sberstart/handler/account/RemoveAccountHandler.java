package ru.sberstart.handler.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.entity.Account;
import ru.sberstart.handler.util.POSTRequestHandler;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;

import javax.xml.ws.Response;
import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class RemoveAccountHandler implements HttpHandler {
    private final AccountService service;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long accountId;
        if("POST".equals(httpExchange.getRequestMethod())) {
            Account account = POSTRequestHandler.handleAccountPostRequest(httpExchange);
            accountId = account.getId();
        } else {
            accountId = RequestParamTransformer.handleGetRequest(httpExchange);
        }
        service.removeOne(accountId);
        new ResponseMaker<String>().makeResponse("Account with id = " + accountId + "removed successful", httpExchange);
    }
}
