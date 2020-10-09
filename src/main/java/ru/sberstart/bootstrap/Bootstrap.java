package ru.sberstart.bootstrap;

import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberstart.handler.MainHandler;
import ru.sberstart.handler.account.CreateAccountHandler;
import ru.sberstart.handler.account.GetAccountHandler;
import ru.sberstart.handler.account.GetAccountsHandler;
import ru.sberstart.handler.account.RemoveAccountHandler;
import ru.sberstart.handler.card.*;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.repository.CardRepository;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.repository.impl.CardRepositoryImpl;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;
import ru.sberstart.service.impl.AccountServiceImpl;
import ru.sberstart.service.impl.CardServiceImpl;
import ru.sberstart.util.db.JdbcConnection;
import ru.sberstart.util.scriptRunner.SQLExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.sql.Connection;

@Getter
@Setter
@NoArgsConstructor
public class Bootstrap {
    private final Connection connection = JdbcConnection.getConnection();
    private final AccountRepository accountRepository = new AccountRepositoryImpl(connection);
    private final CardRepository cardRepository = new CardRepositoryImpl(connection, accountRepository);
    private final AccountService accountService = new AccountServiceImpl(accountRepository);
    private final CardService cardService = new CardServiceImpl(cardRepository);

    public void startApp() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8091), 0);
        server.createContext("/", new MainHandler());
        server.createContext("/createAccount", new CreateAccountHandler(accountService));
        server.createContext("/getAccount", new GetAccountHandler(accountService, cardService));
        server.createContext("/getAccounts", new GetAccountsHandler(accountService, cardService));
        server.createContext("/removeAccount", new RemoveAccountHandler(accountService));

        server.createContext("/createCard", new CreateCardHandler(cardService, accountService));
        server.createContext("/getCard", new GetCardHandler(cardService));
        server.createContext("/getCards", new GetCardsHandler(cardService));
        server.createContext("/getCardsByAccount", new GetCardsByAccountHandler(cardService));
        server.createContext("/removeCard", new RemoveCardHandler(cardService));
        server.createContext("/checkBalance", new CheckBalanceHandler(cardService));
        server.createContext("/updateBalance", new UpdateBalanceHandler(cardService));
        server.start();
        SQLExecutor.runScript(connection);
        System.out.println("Server is started");
    }
}
