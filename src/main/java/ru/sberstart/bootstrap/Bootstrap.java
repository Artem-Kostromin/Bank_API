package ru.sberstart.bootstrap;

import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberstart.handler.account.CreateAccountHandler;
import ru.sberstart.handler.account.GetAccountHandler;
import ru.sberstart.handler.account.GetAccountsHandler;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.repository.CardRepository;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.repository.impl.CardRepositoryImpl;
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
public class Bootstrap implements ServiceLocator {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final Connection connection = JdbcConnection.getConnection();
    private final AccountRepository accountRepository = new AccountRepositoryImpl(connection);
    private final CardRepository cardRepository = new CardRepositoryImpl(connection);

    public void startApp() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/createAccount", new CreateAccountHandler(accountRepository));
        server.createContext("/getAccount", new GetAccountHandler(accountRepository));
        server.createContext("/getAccounts", new GetAccountsHandler(accountRepository));
        server.start();
        SQLExecutor.runScript(connection);
        System.out.println("Server is started");
    }
}
