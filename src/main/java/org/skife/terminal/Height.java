package org.skife.terminal;


public class Height
{
    private final int offset;

    private Height(int offset)
    {
        this.offset = offset;
    }

    public static Height fromTop(int offset) {
        return new Height(offset);
    }

    public static Height fromBottom(int offset) {
        return new Height((offset * -1) - 1);
    }

    int getOffset()
    {
        return offset;
    }
}
