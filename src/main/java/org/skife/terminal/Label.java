package org.skife.terminal;

public class Label
{
    private final String text;
    private final int width;

    private Label(String text, int width)
    {
        this.text = text;
        this.width = width;
    }

    public static Label create(String text)
    {
        return new Label(text, text.length());
    }

    public static Label create(String text, int width)
    {
        return new Label(text, width);
    }

    public static Label empty()
    {
        return new Label("", 0);
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        if (text.length() > width) {
            b.append(text.substring(0, width));
        }
        else {
            b.append(text);
        }
        int remaining = width - text.length();
        if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
                b.append(" ");
            }
        }
        return b.toString();
    }

    int getWidth()
    {
        return width;
    }
}
