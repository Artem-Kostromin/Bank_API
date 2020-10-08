package ru.sberstart.handler.card;

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

@AllArgsConstructor
public class RemoveCardHandler implements HttpHandler {
    private final CardService service;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long cardId;
        if("POST".equals(httpExchange.getRequestMethod())) {
            Card card = POSTRequestHandler.handleCardPostRequest(httpExchange);
            cardId = card.getId();
        } else {
            cardId = RequestParamTransformer.handleGetRequest(httpExchange);
        }
        service.removeOne(cardId);
        new ResponseMaker<String>().makeResponse("Card with id " + cardId + " removed success!", httpExchange);
    }
}
