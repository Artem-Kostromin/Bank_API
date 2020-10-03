package ru.sberstart.bootstrap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberstart.commannd.AbstractCommand;
import ru.sberstart.repository.IAccountRepository;
import ru.sberstart.repository.ICardRepository;
import ru.sberstart.repository.IClientRepository;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.repository.impl.CardRepositoryImpl;
import ru.sberstart.repository.impl.ClientRepositoryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bootstrap implements ServiceLocator {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final Map<String, AbstractCommand> commandList = new LinkedHashMap<>();
    private final IClientRepository clientRepository = new ClientRepositoryImpl();
    private final IAccountRepository accountRepository = new AccountRepositoryImpl();
    private final ICardRepository cardRepository = new CardRepositoryImpl();

    public void startApp(Set<Class<? extends AbstractCommand>> commands) throws IOException {
        initCommands(commands);
        while(true) {
            System.out.println("Добро пожаловать в Bank_API!\nВведите команду:");
            String command = br.readLine();
            if("exit".equals(command)) {
                System.out.println("Работа Bank_API завершена");
                break;
            }
            if(!commandList.containsKey(command)) {
                System.out.println("Введенной команды не существует.");
            }
            commandList.get(command).execute();
        }
    }

    private void initCommands(Set<Class<? extends AbstractCommand>> COMMANDS) {
        for(Class<? extends AbstractCommand> clazz: COMMANDS) {
            try {
                registry((AbstractCommand) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void registry(AbstractCommand command) {
        command.setBootstrap(this);
        commandList.put(command.getName(), command);

    }

}
