package ru.sberstart.bootstrap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberstart.commannd.AbstractCommand;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.repository.CardRepository;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.repository.impl.CardRepositoryImpl;
import ru.sberstart.util.db.JdbcConnection;
import ru.sberstart.util.scriptRunner.SQLExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Bootstrap implements ServiceLocator {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final Map<String, AbstractCommand> commandList = new LinkedHashMap<>();
    private final Connection connection = JdbcConnection.getConnection();
    private final AccountRepository accountRepository = new AccountRepositoryImpl(connection);
    private final CardRepository cardRepository = new CardRepositoryImpl(connection);

    public void startApp(Set<Class<? extends AbstractCommand>> commands) throws IOException {
        SQLExecutor.runScript(connection);
        initCommands(commands);

        try {
            Card card = new Card();
            card.setBalance(BigDecimal.valueOf(8652));
            System.out.println(cardRepository.persist(1, card).getBalance());
            cardRepository.findAllByAccount(1).forEach(a -> System.out.println(a.getBalance()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Добро пожаловать в Bank_API!");
        while(true) {
            System.out.println("Введите команду:");
            String command = br.readLine();
            if("exit".equals(command)) {
                System.out.println("Работа Bank_API завершена");
                break;
            }
            if(!commandList.containsKey(command)) {
                System.out.println("Введенной команды не существует.");
                continue;
            }
            commandList.get(command).execute();
        }
    }

    private void initCommands(Set<Class<? extends AbstractCommand>> COMMANDS) {
        for(Class<? extends AbstractCommand> clazz: COMMANDS) {
            try {
                registry(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void registry(AbstractCommand command) {
        command.setBootstrap(this);
        commandList.put(command.getName(), command);

    }

    public Connection getConnection() {
        return this.connection;
    }
}
