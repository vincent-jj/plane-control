package sim.gui.hud.button;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

abstract class Button extends JRadioButton {

    public Button() {
        super();
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Button.this.performAction();
            }
            
        });
        this.setFocusPainted(false);
        this.setFocusable(false);
    }

    abstract public void performAction();
}
