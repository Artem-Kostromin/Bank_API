package ru.sberstart.handler.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Card;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;

import java.io.IOException;
import java.io.InputStream;


@AllArgsConstructor
public class CreateCardHandler implements HttpHandler {
    private final CardService cardService;
    private final AccountService accountService;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        if("POST".equals(httpExchange.getRequestMethod())) {
            Card card = handlePostRequest(httpExchange);
            Card card1 = cardService.persist(card.getAccountId(), card);
            accountService.findOne(card.getAccountId()).getCards().add(card1);
            new ResponseMaker<Card>().makeResponse(card, httpExchange);
        } else {
            int account_id = RequestParamTransformer.handleGetRequest(httpExchange);
            Card card = cardService.persist(account_id, new Card());
            accountService.findOne(account_id).getCards().add(card);
            new ResponseMaker<Card>().makeResponse(card, httpExchange);
        }
    }

    private Card handlePostRequest(HttpExchange httpExchange) {
        InputStream is = httpExchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Card card = null;
        try {
            card = mapper.readValue(is, Card.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return card;
    }
}
