package com.amsta.puzzel.interfaces;

import com.amsta.puzzel.events.ButtonClickedEvent;

/**
 * Created by Yorick on 16-03-17.
 */
public interface IButtonPressed {
    /**
     * Button pressed handler.
     *
     * @param event the event
     */
    public void buttonPressedHandler(ButtonClickedEvent event);
}
