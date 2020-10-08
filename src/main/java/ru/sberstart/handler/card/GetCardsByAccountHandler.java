package ru.sberstart.handler.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;
import ru.sberstart.handler.util.POSTRequestHandler;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.CardService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@AllArgsConstructor
public class GetCardsByAccountHandler implements HttpHandler {
    private final CardService service;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        List<Card> cards;
        if("POST".equals(httpExchange.getRequestMethod())) {
            Account account = POSTRequestHandler.handleAccountPostRequest(httpExchange);
            long accountId = account.getId();
            cards = service.findAllByAccount(accountId);
        } else {
            cards = service.findAllByAccount(RequestParamTransformer.handleGetRequest(httpExchange));
        }
        new ResponseMaker<List<Card>>().makeResponse(cards, httpExchange);
    }
}
