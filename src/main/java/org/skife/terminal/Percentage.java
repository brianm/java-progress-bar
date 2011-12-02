package org.skife.terminal;

public class Percentage
{
    private final boolean show;

    private Percentage(boolean show) {
        this.show = show;
    }

    public static Percentage show() {
        return new Percentage(true);
    }

    public static Percentage hide() {
        return new Percentage(false);
    }

    public int length() {
        return show ? 6 : 1;
    }

    String display(double steps, double completed)
    {
        if (show) {
            Double pct = ((completed / steps) * 100);
            if (pct > 100) {
                pct = 100D;
            }
            return " " + pct.intValue() + "%";
        }
        else {
            return "";
        }
    }
}
