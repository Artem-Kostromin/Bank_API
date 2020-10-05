package ru.sberstart;

import org.reflections.Reflections;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.commannd.AbstractCommand;

import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        final Set<Class<? extends AbstractCommand>> commands = new Reflections().getSubTypesOf(AbstractCommand.class);

        new Bootstrap().startApp(commands);

    }
}
