package ru.sberstart.commannd;

import lombok.Getter;
import lombok.Setter;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.bootstrap.ServiceLocator;

@Getter
@Setter
public abstract class AbstractCommand {
    protected ServiceLocator bootstrap;

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute();

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

}
