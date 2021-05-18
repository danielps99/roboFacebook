package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {
        Comando comando = Comando.valueOf(args[0]);
        new App(comando).start();
    }
}
