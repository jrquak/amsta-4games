package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * The names from the students is shown in this class.
 *
 * @author Jordy Quak
 */
public class CreditScreen extends BaseScreen {

    private Texture background;
    private Texture logoHva;
    private Texture logoAmsta;
    private Label label;
    private TextButton btnBack;

    private final int GAP_BETWEEN = 30;
    private final int GAP_BETWEEN_LOGO_NAME = 150;

    private final String NAMES = "Gemaakt door\n" + "Tom Noordeloos \n" + "Koos Han Jansen \n" +
            "Jordy Quak \n" + "Yorick Schellevis \n" + "Wyomi Beuker \n" + "Jacco Sierkstra \n\n" +
            "Project Amsta \n" + "Jaar 2016/2017 \n" + "Groep AMSTA08";

    private Vector2 namesPos;
    private Vector2 hvaPos;
    private Vector2 amstaPos;

    @Override
    public boolean initialize() {

        background = new Texture("ui/background.jpg");
        logoHva = new Texture("credit/hvalogo.png");
        logoAmsta = new Texture("credit/amstalogo.png");

        // font stuff
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = GlobalVals.FONT_SIZE_BTN;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        // base pos is at center of screen width and 75% of the height
        namesPos = new Vector2(GlobalVals.V_WIDTH / 2, GlobalVals.V_HEIGHT / 4 * 2);

        Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
        label = new Label(NAMES, style);
        label.setPosition(namesPos.x - label.getPrefWidth() / 2, namesPos.y);

        // Use glyph layout to check the size of the text with this font and font size
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, NAMES);

        // Give hva logo a position
        hvaPos = new Vector2(namesPos);
        hvaPos.sub(0, layout.height + GAP_BETWEEN - GAP_BETWEEN_LOGO_NAME);

        // Give amsta logo a position
        amstaPos = new Vector2(hvaPos);
        amstaPos.sub(0, logoHva.getHeight() + GAP_BETWEEN);

        // Make a back button with the right listner
        btnBack = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GlobalVals.V_WIDTH / 2 - GlobalVals.getBtnWidth() / 2, 50), "Terug");
        btnBack.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                ScreenManager.getInstance().goBackOneScreen();
            }
        });

        return true;
    }

    @Override
    public void update() {
        btnBack.update();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);
        btnBack.draw(batch);

        batch.draw(logoHva, hvaPos.x - logoHva.getWidth() / 2, hvaPos.y);
        batch.draw(logoAmsta, amstaPos.x - logoAmsta.getWidth() / 2, amstaPos.y);

        label.draw(batch, 1.0f);
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }
}
