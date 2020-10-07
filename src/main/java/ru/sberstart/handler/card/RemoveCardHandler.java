package ru.sberstart.handler.card;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.CardService;

import java.io.IOException;

@AllArgsConstructor
public class RemoveCardHandler implements HttpHandler {
    private final CardService service;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        int accountId = RequestParamTransformer.handleGetRequest(httpExchange);
        service.removeOne(accountId);
        new ResponseMaker<String>().makeResponse("Card with account id " + accountId + " removed success!", httpExchange);
    }
}
