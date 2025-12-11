package devjoaomarcelo.jmspring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JmLogger {

    public static final String WHITE  = " \u001B[37m";

    // --- Adicione estas cores no início da sua classe, se ainda não tiver ---
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m"; // Usado como laranja
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";

    public static DateTimeFormatter JMDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void showBanner() {

        // 1. O banner original dividido em linhas
        String[] lines = {
                "   ____  _____  ________      __         _   __  __        _____ _____  _____  _____ _   _  _____  ____ ",
                "  / / / |  __ \\|  ____\\ \\    / /        | | |  \\/  |      / ____|  __ \\|  __ \\|_   _| \\ | |/ ____| \\ \\ \\",
                " / / /  | |  | | |__   \\ \\  / /_____    | | | \\  / |_____| (___ | |__) | |__) | | | |  \\| | |  __   \\ \\ \\",
                "< < <   | |  | |  __|   \\ \\/ /______|   | | | |\\/| |______\\___ \\|  ___/|  _  /  | | | . ` | | |_ |   > > > JM-Spring Web FrameWork",
                " \\ \\ \\  | |__| | |____   \\  /      | |__| | | |  | |      ____) | |    | | \\ \\ _| |_| |\\  | |__| |  / / / For Use Personal",
                "  \\_\\_\\ |_____/|______|   \\/        \\____/  |_|  |_|     |_____/|_|    |_|  \\_\\_____|_| \\_|\\_____| /_/_/ By Developer Joao Marcelo"
        };

        // 2. Paleta de cores para o gradiente
        String[] colors = { RED, YELLOW, GREEN, CYAN, BLUE, PURPLE };

        // 3. Imprime cada linha com sua cor correspondente
        for (int i = 0; i < lines.length; i++) {
            // Usa o operador % para repetir as cores se houver mais linhas que cores
            System.out.println(colors[i % colors.length] + lines[i] + RESET);
        }
    }

    public static void log(String modulo, String mensagem){
        String date = LocalDateTime.now().format(JMDATE);
        System.out.printf(GREEN + "%15s " + YELLOW + "%-30s:"+ WHITE + "%s\n"+ RESET, date, modulo, mensagem);

    }


}
