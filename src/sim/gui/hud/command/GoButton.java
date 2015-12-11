package sim.gui.hud.command;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author clement
 */
class GoButton extends JButton {

    final CommandGetter cmd;

    public GoButton(final CommandGetter cmd) {
        super("GO", new ImageIcon("../button/img/Button GoTerm.png"));
        this.setIconTextGap(0);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.cmd = cmd;
        this.setSize(10, 30);
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cmd.sendCommand();
            }

        });

        this.setFocusable(true);
    }
}
