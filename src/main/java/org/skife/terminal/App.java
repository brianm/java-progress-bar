package org.skife.terminal;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.concurrent.Future;


public class App
{
    public static void main(String[] args) throws Exception
    {
        AnsiConsole.systemInstall();

        System.out.println(Ansi.ansi().newline());
        ProgressBar core = new ProgressBar(Label.create("slower thing", 15),
                                           Height.fromBottom(0),
                                           15,
                                           Percentage.show());

        ProgressBar rslv = new ProgressBar(Label.create("fast thing", 15),
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
