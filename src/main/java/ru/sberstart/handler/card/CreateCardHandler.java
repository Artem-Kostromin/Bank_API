package ru.sberstart.handler.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Card;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;

import java.io.OutputStream;


@AllArgsConstructor
public class CreateCardHandler implements HttpHandler {
    private final CardService cardService;
    private final AccountService accountService;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        int account_id = RequestParamTransformer.handleGetRequest(httpExchange);
        Card card = cardService.persist(account_id, new Card());
        accountService.findOne(account_id).getCards().add(card);
        new ResponseMaker<Card>().makeResponse(card, httpExchange);
    }
}
