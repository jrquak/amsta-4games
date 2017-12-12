package com.amsta.puzzel.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Yorick on 21-03-17.
 */
public class SoundManager {

    private static boolean playSound = true;
    private static Preferences prefs = Gdx.app.getPreferences("MyPreferences");

    /**
     * The enum Sound type.
     */
    public enum SoundType
    {
        /**
         * Click sound type.
         */
        CLICK,
        /**
         * Right sound type.
         */
        RIGHT,
        /**
         * Wrong sound type.
         */
        WRONG,
        /**
         * Card sound type.
         */
        CARD,
        /**
         * Win sound type.
         */
        WIN
    }

   private Sound sound;

   private AssetManager manager;

    private static SoundManager instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SoundManager getInstance()
    {
        if(instance == null)
            instance = new SoundManager();
        return  instance;
    }

    private SoundManager()
    {
        load();
    }

    private void load()
    {
        manager = new AssetManager();
        manager.load("audio/click2.ogg", Sound.class);
        manager.load("audio/CorrectSound.mp3", Sound.class);
        manager.load("audio/Notmatch.mp3", Sound.class);
        manager.load("audio/cardSlide1.ogg", Sound.class);
        manager.load("audio/Gratz.mp3", Sound.class);
        manager.finishLoading();
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt)
    {
        manager.update();
    }

    /**
     * Play sound.
     *
     * @param type the type
     */
    public void playSound(SoundType type)
    {
        if(playSound == true) {

            switch (type) {
                case CLICK:
                    manager.get("audio/click2.ogg", Sound.class).play();
                    break;
                case RIGHT:
                    manager.get("audio/CorrectSound.mp3", Sound.class).play();
                    break;
                case WRONG:
                    manager.get("audio/Notmatch.mp3", Sound.class).play();
                    break;
                case CARD:
                    manager.get("audio/cardSlide1.ogg", Sound.class).play();
                    break;
                case WIN:
                    manager.get("audio/Gratz.mp3", Sound.class).play();
                    break;
            }
        }
    }

    /**
     * Dispose.
     */
    public void dispose()
    {
        manager.dispose();
        instance = null;
    }

    /**
     * Is sound enabled boolean.
     *
     * @return the boolean
     */
    public boolean isSoundEnabled()
    {
        return playSound;
    }


    /**
     * Toggle the sound enabled
     *
     * @return true if the sound button is toggled on, otherwise false
     */
    public static boolean toggleSound() {
        playSound = !playSound;

        prefs.putBoolean("playSound", playSound);
        prefs.flush();
        return playSound;
    }
}
