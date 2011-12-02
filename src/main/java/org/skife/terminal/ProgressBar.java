package org.skife.terminal;

import jline.Terminal;
import jline.TerminalFactory;
import org.fusesource.jansi.Ansi;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBar
{
    private static final Terminal terminal = TerminalFactory.get();

    private final int        line;
    private final int        steps;
    private final Percentage percentage;

    private final AtomicInteger stepsCompleted = new AtomicInteger(0);
    private final Label label;

    public ProgressBar(int steps)
    {
        this(Height.fromBottom(0), steps);
    }

    public ProgressBar(int steps, Percentage percentage)
    {
        this(Label.empty(), Height.fromBottom(0), steps, percentage);
    }

    public ProgressBar(Height height, int steps)
    {
        this(Label.empty(), height, steps, Percentage.hide());
    }

    public ProgressBar(Label label, Height height, int steps, Percentage percentage)
    {
        this.label = label;
        this.line = height.getOffset();
        this.steps = steps;
        this.percentage = percentage;
    }

    public ProgressBar progress(int steps)
    {
        stepsCompleted.addAndGet(steps);
        return this;
    }

    public ProgressBar progress()
    {
        return progress(1);
    }

    public Future<Void> render()
    {
        return Renderer.render(new Callable<Void>()
        {
            private int height()
            {
                if (line >= 0) {
                    return line;
                }
                else {
                    return terminal.getHeight() + line + 1;
                }
            }

            @Override
            public Void call() throws Exception
            {
                int steps_completed = stepsCompleted.get();

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
                Double so_far_d = ((steps_completed * 1.0) / (steps * 1.0)) * bar_width;
                int so_far = so_far_d.intValue();
                if (so_far > bar_width) {
                    so_far = bar_width;
                }

                for (int i = 0; i < so_far; i++) {
                    ansi.a("*");
                }

                for (int i = 0; i < bar_width - so_far; i++)
                {
                    ansi.a(" ");
                }

                ansi.fg(Ansi.Color.GREEN);
                ansi.a("]");

                ansi.reset();
                ansi.a(percentage.display(steps, steps_completed));

                System.out.print(ansi);
                System.out.flush();
                ansi.restorCursorPosition();

                return null;
            }
        }

        );
    }
}
