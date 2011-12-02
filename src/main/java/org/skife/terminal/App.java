package org.skife.terminal;

import jline.Terminal;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Hello world!
 */
public class App
{
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
    {
        AnsiConsole.systemInstall();

        System.out.println(Ansi.ansi().newline());
        ProgressBar core = new ProgressBar(Label.create("/ning.0/db.core", 22),
                                           Height.fromBottom(0),
                                           15,
                                           Percentage.show());

        ProgressBar rslv = new ProgressBar(Label.create("/ning.0/front.0/rslv.7", 22),
                                           Height.fromBottom(1),
                                           10,
                                           Percentage.show());
        for (int i = 0; i < 15; i++) {
            Future one  = core.progress().render();
            Future two = rslv.progress().render();

            one.get();
            two.get();
            Thread.sleep(1000);
        }
        System.out.println();

    }
}
