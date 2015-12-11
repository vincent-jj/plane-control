package sim.gui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HelpMenu extends JMenu{

  public HelpMenu(String title){
    super(title);
    
    //bouton d'aide
    JMenuItem helpItem = new JMenuItem("Help",KeyEvent.VK_H);
    helpItem.addActionListener(new HelpListener());
    this.add(helpItem);
    
    this.addSeparator();
    
    //bouton about du menu Help
    JMenuItem aboutItem = new JMenuItem("About",KeyEvent.VK_A);
    aboutItem.addActionListener(new AboutListener());
    this.add(aboutItem);
  }
  
  private class AboutListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
                null, 
                "Contrôle aérien Version 1.1\n"+
                "Creators : ~nbremond, ~afiot, ~cfoyer, ~rherveux, ~yjanvier, ~vjeanjean, ~alraffin", 
                "About", 
                JOptionPane.INFORMATION_MESSAGE
              );
    }
    
  }
  
  private class HelpListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
                null, 
                "toto", 
                "How To", 
                JOptionPane.INFORMATION_MESSAGE
              );
    }
    
  }
  
}
