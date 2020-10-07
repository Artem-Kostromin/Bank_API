package ru.sberstart.handler.card;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.entity.Card;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.CardService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UpdateBalanceHandler implements HttpHandler {
    private final CardService cardService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        List<Integer> params = handleGetRequest(httpExchange);
        long cardId = params.get(0);
        BigDecimal balance = BigDecimal.valueOf(params.get(1));
        Card card = new Card();
        card.setId(cardId);
        card.setBalance(balance);
        cardService.update(cardId, card);
        new ResponseMaker<String>().makeResponse("Balance of cards with id = " + cardId + " now equals " + balance, httpExchange);
    }

    public static List<Integer> handleGetRequest(HttpExchange httpExchange) {
        ArrayList<Integer> integers = new ArrayList<>();
        String[] strArray = httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("&");
        integers.add(Integer.parseInt(strArray[0].split("=")[1]));
        integers.add(Integer.parseInt(strArray[1].split("=")[1]));
        return integers;
    }
}
