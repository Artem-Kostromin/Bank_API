package ru.sberstart.handler.card;

import lombok.AllArgsConstructor;
import ru.sberstart.entity.Card;
import ru.sberstart.handler.util.POSTRequestHandler;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.CardService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.math.BigDecimal;

@AllArgsConstructor
public class CheckBalanceHandler implements HttpHandler {
    private final CardService cardService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long cardId;
        if("POST".equals(httpExchange.getRequestMethod())) {
            Card card = POSTRequestHandler.handleCardPostRequest(httpExchange);
            cardId = card.getId();
        } else {
            cardId = RequestParamTransformer.handleGetRequest(httpExchange);
        }
        BigDecimal balance = cardService.checkBalance(cardId);
        new ResponseMaker<BigDecimal>().makeResponse(balance, httpExchange);
    }
}
