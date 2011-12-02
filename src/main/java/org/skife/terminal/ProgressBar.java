package org.skife.terminal;

import jline.Terminal;
import jline.TerminalFactory;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ProgressBar
{
    static {
        AnsiConsole.systemInstall();
    }

    private static final Terminal terminal = TerminalFactory.get();

    private final AtomicInteger line = new AtomicInteger(-1);
    private final AtomicReference<Double> progress = new AtomicReference<Double>(0D);
    private final Percentage percentage;
    private final Label label;

    public ProgressBar(Label label, Height height, Percentage percentage)
    {
        this.label = label;
        this.line.set(height.getOffset());
        this.percentage = percentage;
    }

    public ProgressBar progress(Double percent)
    {
        progress.set(percent);
        return this;
    }

    public Future<Void> render()
    {
        return Renderer.render(new Callable<Void>()
        {
            private int height()
            {
                int l = line.get();
                if (l >= 0) {
                    return l;
                }
                else {
                    return terminal.getHeight() + l + 1;
                }
            }

            @Override
            public Void call() throws Exception
            {
                double prog = progress.get();
                int bar_width = terminal.getWidth()
                                - (label.getWidth() + 1) // label + space
                                - 2  // brackets
                                - percentage.length();

                Ansi ansi = Ansi.ansi();
                ansi.saveCursorPosition();
                ansi.cursor(height(), 0);
                ansi.eraseLine(Ansi.Erase.FORWARD);

                ansi.a(label).a(" ");

                ansi.fg(Ansi.Color.GREEN);
                ansi.a("[");

                ansi.fg(Ansi.Color.BLUE);
                Double so_far_d = prog * bar_width;
                int so_far = so_far_d.intValue();
                if (so_far > bar_width) {
                    so_far = bar_width;
                }

                for (int i = 0; i < so_far; i++) {
                    ansi.a("*");
                }

                for (int i = 0; i < bar_width - so_far; i++) {
                    ansi.a(" ");
                }

                ansi.fg(Ansi.Color.GREEN);
                ansi.a("]");

                ansi.reset();
                ansi.a(percentage.display(prog));

                System.out.print(ansi);
                System.out.flush();
                ansi.restorCursorPosition();

                return null;
            }
        }

        );
    }
}
