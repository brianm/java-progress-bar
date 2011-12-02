package org.skife.terminal;

public class Label
{
    private final String text;
    private final int padding;

    private Label(String text, int padding)
    {
        if (text.length() > padding) {
            throw new IllegalArgumentException("Text length cannot be greater than padding!");
        }
        this.text = text;
        this.padding = padding;
    }

    public static Label create(String text) {
        return new Label(text, text.length());
    }

    public static Label create(String text, int padding) {
        return new Label(text, padding);
    }

    public static Label empty() {
        return new Label("", 0);
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        b.append(text);
        int remaining = padding - text.length();
        if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
                b.append(" ");
            }
        }
        return b.toString();
    }

    int length() {
        return padding;
    }
}
