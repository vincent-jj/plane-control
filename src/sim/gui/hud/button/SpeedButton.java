package sim.gui.hud.button;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sim.engine.Engine;

/**
 *
 * @author clement
 */
class SpeedButton extends Button {

    public final static int SPEED_1 = 1;
    public final static int SPEED_2 = 2;
    public final static int SPEED_4 = 4;
    public final static int SPEED_8 = 8;

    private int speed;
    private Icon icoSelected;
    boolean hasSelectedIcon = true;
    private Icon icoUnselected;
    boolean hasUnselectedIcon = true;

    private SpeedButton(String imgSelected, String imgUnselected, int speed) {
        try {
            icoSelected = new ImageIcon(ImageIO.read(new File(imgSelected)));
        } catch (IOException ex) {
            hasSelectedIcon = false;
        }

        try {
            icoUnselected = new ImageIcon(ImageIO.read(new File(imgUnselected)));
        } catch (IOException ex) {
            hasSelectedIcon = true;
        }

        if (SpeedButton.this.hasSelectedIcon && SpeedButton.this.isSelected()) {
            SpeedButton.this.setIcon(SpeedButton.this.icoSelected);
        } else if (SpeedButton.this.hasUnselectedIcon && !SpeedButton.this.isSelected()) {
            SpeedButton.this.setIcon(SpeedButton.this.icoUnselected);
        } else {
            SpeedButton.this.setText("x" + SpeedButton.this.speed);
        }

        this.speed = speed;
        this.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (SpeedButton.this.hasSelectedIcon && SpeedButton.this.isSelected()) {
                    SpeedButton.this.setIcon(SpeedButton.this.icoSelected);
                } else if (SpeedButton.this.hasUnselectedIcon && !SpeedButton.this.isSelected()) {
                    SpeedButton.this.setIcon(SpeedButton.this.icoUnselected);
                } else {
                    SpeedButton.this.setText("x" + SpeedButton.this.speed);
                }
            }

        });
    }

    public static SpeedButton createSpeedButtonx1() {
        return new SpeedButton("data/btn/Buttonx1Pressed.png", "data/btn/Buttonx1Unpressed.png", SPEED_1);
    }

    public static SpeedButton createSpeedButtonx2() {
        return new SpeedButton("data/btn/Buttonx2Pressed.png", "data/btn/Buttonx2Unpressed.png", SPEED_2);
    }

    public static SpeedButton createSpeedButtonx4() {
        return new SpeedButton("data/btn/Buttonx4Pressed.png", "data/btn/Buttonx4Unpressed.png", SPEED_4);
    }

    public static SpeedButton createSpeedButtonx8() {
        return new SpeedButton("data/btn/Buttonx8Pressed.png", "data/btn/Buttonx8Unpressed.png", SPEED_8);
    }

    @Override
    public void performAction() {
        Engine.getEngine().changeSpeed(this.speed);
    }
}
