package ru.sberstart;

import org.reflections.Reflections;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.commannd.AbstractCommand;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        final Set<Class<? extends AbstractCommand>> commands = new Reflections().getSubTypesOf(AbstractCommand.class);

        try {
            new Bootstrap().startApp(commands);
        } catch (Exception e) {
            System.out.println("Произошла ошибка :" + e.getMessage());
        }
    }
}
