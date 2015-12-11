package sim.gui.hud.strip;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import sim.gui.hud.HUD;
import sim.util.Resizable;

/**
 *
 * @author clement
 */
class StripsDisplay extends JPanel implements Resizable {

    static private StripsDisplay instance = new StripsDisplay();

//    private JPanel strips;
    private Box stripsBackground;

    private StripsDisplay() {
        int STRIPS_PADDING_SIZE = 3;
        Dimension STRIPS_PADDING = new Dimension(STRIPS_PADDING_SIZE, STRIPS_PADDING_SIZE);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.setVisible(true);

        stripsBackground = Box.createVerticalBox();
        stripsBackground.setOpaque(true);
        stripsBackground.setVisible(true);
        stripsBackground.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        stripsBackground.setBackground(Color.white);
        stripsBackground.add(Box.createRigidArea(STRIPS_PADDING));
        //stripsBackground.add(strips);
        //stripsBackground.add(Box.createVerticalGlue());

        Box stripsContainerXCenter = new Box(BoxLayout.X_AXIS);
        stripsContainerXCenter.add(Box.createHorizontalStrut(STRIPS_PADDING_SIZE));
        stripsContainerXCenter.add(stripsBackground);
        stripsContainerXCenter.add(Box.createHorizontalStrut(STRIPS_PADDING_SIZE));

        this.add(Box.createRigidArea(new Dimension(5, 5)));
        this.add(stripsContainerXCenter);
        this.add(Box.createRigidArea(new Dimension(5, 5)));
    }

    static public StripsDisplay getStripsDisplay() {
        return instance;
    }

    public void addStrip(StripContainer sc) {
        stripsBackground.add(sc);
        this.stripsBackground.revalidate();
    }

    public void removeStrip(StripContainer sc) {
        for (int i=0; i < stripsBackground.getComponentCount(); i++){
            if (stripsBackground.getComponent(i)==sc){
                stripsBackground.remove(i);
            }
        }
        //stripsBackground.remove(sc);
        this.stripsBackground.revalidate();
        this.stripsBackground.repaint();
    }

    public void flush() {
        stripsBackground.removeAll();
        this.stripsBackground.repaint();
    }

    @Override
    public void resize() {
        this.setPreferredSize(new Dimension(HUD.HUD_WIDTH - 11, 200));
    }

}
