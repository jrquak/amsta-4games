import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.screens.MainMenu;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Yorick on 16-03-17.
 */
public class ScreenManagerTest {

    @Test
    public void GoBackScreens() {
        ScreenManager manager = ScreenManager.getInstance();

        manager.changeScreen(new MainMenu());
        assertEquals(manager.getScreenStackSize(), 1);
        manager.changeScreen(new MainMenu());
        assertEquals(manager.getScreenStackSize(), 2);
        manager.goBackOneScreen();
        assertEquals(manager.getScreenStackSize(), 1);
        manager.goBackOneScreen();
        assertEquals(manager.getScreenStackSize(), 1);
    }
}
