package ru.sberstart.handler;

import org.junit.Test;
import ru.sberstart.handler.account.CreateAccountHandler;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.impl.AccountServiceImpl;
import ru.sberstart.util.db.JdbcConnection;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.net.URL;

public class TestAccountHandler {
    private final AccountService accountService = new AccountServiceImpl(new AccountRepositoryImpl(JdbcConnection.getConnection()));
    private final CreateAccountHandler createAccountHandler = new CreateAccountHandler(accountService);

    @Test
    public void createAccountHandlerTest() throws IOException {
        HttpClient httpClient = HttpClient.New(new URL("http://localhost:8080/createAccount"));
        httpClient.
        createAccountHandler.handle(httpClient);
    }
}
