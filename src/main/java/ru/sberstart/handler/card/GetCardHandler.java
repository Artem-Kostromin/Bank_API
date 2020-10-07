package ru.sberstart.handler.card;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.entity.Card;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.CardService;

import java.io.IOException;

@AllArgsConstructor
public class GetCardHandler implements HttpHandler {
    private final CardService cardService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        int cardId = RequestParamTransformer.handleGetRequest(httpExchange);
        Card card = cardService.findOne(cardId);
        new ResponseMaker<Card>().makeResponse(card, httpExchange);
    }
}
