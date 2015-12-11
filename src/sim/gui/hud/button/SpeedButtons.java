
package sim.gui.hud.button;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

/**
 *
 * @author clement
 */
public class SpeedButtons extends JPanel {
    
    public SpeedButtons() {
        ButtonGroup bg = new ButtonGroup();
        
        SpeedButton sb1 = SpeedButton.createSpeedButtonx1();
        bg.add(sb1);
        sb1.setSelected(true);
        this.add(sb1);
        
        SpeedButton sb2 = SpeedButton.createSpeedButtonx2();
        bg.add(sb2);
        sb2.setSelected(false);
        this.add(sb2);
        
        SpeedButton sb4 = SpeedButton.createSpeedButtonx4();
        bg.add(sb4);
        sb4.setSelected(false);
        this.add(sb4);
        
        SpeedButton sb8 = SpeedButton.createSpeedButtonx8();
        bg.add(sb8);
        sb8.setSelected(false);
        this.add(sb8);

        this.setOpaque(false);
        this.setVisible(true);
    }

}
