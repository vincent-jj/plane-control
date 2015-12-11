package sim.gui.hud.command;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import sim.engine.Engine;
import sim.gui.hud.HUD;

/**
 *
 * @author clement
 */
class CommandGetter extends JTextField {

    static private final CommandGetter instance = new CommandGetter();
    static private final String INITIAL_VALUE = "command line";

    static private final Color INITIAL_COLOR = Color.LIGHT_GRAY;

    private CommandGetter() {
        super();

        this.setColumns((HUD.HUD_WIDTH - 50) / this.getFont().getSize());

        this.setText(INITIAL_VALUE);
        this.setForeground(INITIAL_COLOR);

        this.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                CommandGetter cmd = (CommandGetter) e.getSource();
                if (cmd.getText().equals(INITIAL_VALUE)) {
                    cmd.setText("");
                    cmd.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                CommandGetter cmd = (CommandGetter) e.getSource();
                if (cmd.getText().equals("")) {
                    cmd.setText(INITIAL_VALUE);
                    cmd.setForeground(INITIAL_COLOR);
                }
            }

        });

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                CommandGetter cmd = (CommandGetter) e.getSource();
                String command = cmd.getText().trim();
                if (e.getKeyCode() == 10) {
                    cmd.sendCommand();
                } else if (command.equals(INITIAL_VALUE)) {
                    cmd.setText("");
                    cmd.setForeground(Color.black);
                } else if (e.getKeyChar() == ' ') {
                    cmd.setText(command);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.setMaximumSize(this.getPreferredSize());
        this.setEnabled(true);
        this.setVisible(true);
    }

    static public CommandGetter getCommandGetter() {
        return instance;
    }

    public void append(String string) {
        if (this.getText().equals(INITIAL_VALUE)) {
            this.setText("");
            this.setForeground(Color.black);
        }
        String oldText = this.getText().trim() + ' ' + string + ' ';
        this.setText(oldText.trim());
    }

    public void flush() {
        setText(INITIAL_VALUE);
        setForeground(INITIAL_COLOR);
    }

    public void sendCommand() {
        String cmd = this.getText();
        if (!cmd.equals(INITIAL_VALUE)
                && !cmd.equals("")) {
            Engine.getEngine().handleCommand(cmd);

            this.setText(INITIAL_VALUE);
            this.setForeground(INITIAL_COLOR);
        }
    }
}
