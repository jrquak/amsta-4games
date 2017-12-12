package com.amsta.puzzel.manager;

/**
 * Created by Yorick on 21-03-17.
 */
public class SettingManager {
    /**
     * The Play sound.
     */
    public boolean playSound = true;

    private static SettingManager instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SettingManager getInstance()
    {
        if(instance == null)
            instance = new SettingManager();
        return instance;
    }

    private SettingManager()
    {

    }
}
