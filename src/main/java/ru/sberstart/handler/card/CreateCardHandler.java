package ru.sberstart.handler.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class CreateCardHandler implements HttpHandler {
    private final AccountService aService;
    private final CardService cService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long accountId = Long.parseLong(handleGetRequest(httpExchange));
        Card card = cService.persist(accountId, new Card());
        Account account = aService.findOne(accountId);
        List<Card> cards = account.getCards();
        boolean b = cards.add(card);
        if(b) {
            System.out.println("adding");
        } else {
            System.out.println("not adding");
        }

        OutputStream outputStream = httpExchange.getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String s = null;
        try {
            s = writer.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, Objects.requireNonNull(s).length());

        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }
}
