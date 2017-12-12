package com.amsta.puzzel.events;

/**
 * Created by Yorick on 17-03-17.
 */
public class ButtonClickedEvent {
    private Object source;

    /**
     * Instantiates a new Button clicked event.
     *
     * @param source the source
     */
    public  ButtonClickedEvent(Object source)
    {
        this.source = source;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public Object getSource()
    {
        return source;
    }
}
