package ru.sberstart.handler.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.handler.util.RequestParamTransformer;
import ru.sberstart.handler.util.ResponseMaker;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@AllArgsConstructor
public class GetAccountHandler implements HttpHandler {
    private final AccountService aService;
    private final CardService cService;

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) {
        int id = 0;
        if("GET".equals(httpExchange.getRequestMethod())) {
            id = RequestParamTransformer.handleGetRequest(httpExchange);
        } else if("POST".equals(httpExchange.getRequestMethod())) {
            id = handlePostRequest(httpExchange);
        }
        Account account = aService.findOne(id);
        account.setCards(cService.findAllByAccount(id));

        new ResponseMaker<Account>().makeResponse(account, httpExchange);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private Integer handlePostRequest(HttpExchange httpExchange) {
        InputStream is = httpExchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Integer integer = null;
        try {
            integer = mapper.readValue(is, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return integer;
    }
}
