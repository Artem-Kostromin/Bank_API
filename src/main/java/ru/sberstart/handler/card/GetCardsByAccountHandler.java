package ru.sberstart.handler.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import ru.sberstart.entity.Card;
import ru.sberstart.service.CardService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@AllArgsConstructor
public class GetCardsByAccountHandler implements HttpHandler {
    private final CardService service;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        List<Card> cards = service.findAllByAccount(Long.parseLong(handleGetRequest(httpExchange)));

        OutputStream outputStream = httpExchange.getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String s = writer.writeValueAsString(cards);

        httpExchange.sendResponseHeaders(200, s.length());

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
