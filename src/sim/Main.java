package sim;

import javax.swing.JOptionPane;
import java.util.Arrays;

import edu.cmu.sphinx.jsgf.JSGFGrammarException;
import edu.cmu.sphinx.jsgf.JSGFGrammarParseException;
import sim.engine.Dialog;

import sim.engine.Engine;
import sim.engine.Airport;
import sim.gui.GUI;
import sim.gui.hud.HUD;
import sim.util.Parser;
import sim.util.UniversalPosition;

public class Main {

    private static int _TIME = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UniversalPosition.setScreenSize(600, 600);//TODO cet appel peut probablement etre mis ailleurs

        OptionChoice[] possibleValues = {OptionChoice.KJFK, OptionChoice.KLAX,
            OptionChoice.KORD, OptionChoice.KSEA, OptionChoice.KPHX, OptionChoice.KTUS, OptionChoice.KDEN, OptionChoice.KSFO, OptionChoice.KATL, OptionChoice.KMIA, OptionChoice.PHNL, OptionChoice.KIND, OptionChoice.KMCI, OptionChoice.KMSY, OptionChoice.KPDX, OptionChoice.KBOS, OptionChoice.KMSP, OptionChoice.KLAS, OptionChoice.KIAD, OptionChoice.KSLC, OptionChoice.KAUS, OptionChoice.KMEM, OptionChoice.KBNA, OptionChoice.KPHL, OptionChoice.KCMH, OptionChoice.KABQ};
        Arrays.sort(possibleValues);
        OptionChoice selectedValue = (OptionChoice) JOptionPane.showInputDialog(null,
                "Veuillez choisir la carte à charger", "Choix carte",
                JOptionPane.PLAIN_MESSAGE, null, possibleValues, possibleValues[0]);

        if (selectedValue == null) {
            return;
        }

        Airport a = Parser.getAirport(selectedValue.getCode());
        UniversalPosition.setCenter(a.getLatitude(), a.getLongitude());
        Parser.parse(a);
        //        MapObjects m = Parser.m;

        Engine.init(_TIME, Parser.m);

        GUI gui = GUI.getGUI();
        HUD hud = HUD.getHUD();
        gui.addHUD(hud);

        //TO BE REMOVED : ça sera fait dans le code de la première JFrame
        GUI.displayGame();

        Timer t = new Timer(Engine.getEngine(), gui, _TIME);
        Dialog d;
        try {
            d = new Dialog(Engine.getEngine());
            d.start();
        } catch (JSGFGrammarParseException e) {
            e.printStackTrace();
        }
        //Engine.getEngine().changeSpeed(10);
        t.start();
        //
    }

}

class OptionChoice implements Comparable<OptionChoice> {

    public static OptionChoice KSEA = new OptionChoice("KSEA", "Seatle");
    public static OptionChoice KJFK = new OptionChoice("KJFK", "New York");
    public static OptionChoice KORD = new OptionChoice("KORD", "Chicago");
    public static OptionChoice KLAX = new OptionChoice("KLAX", "Los Angeles");
    public static OptionChoice KPHX = new OptionChoice("KPHX", "Phoenix");
    public static OptionChoice KSFO = new OptionChoice("KSFO", "San Francisco");
    public static OptionChoice KTUS = new OptionChoice("KTUS", "Tucson");
    public static OptionChoice KDEN = new OptionChoice("KDEN", "Denver");
    public static OptionChoice KMIA = new OptionChoice("KMIA", "Miami");
    public static OptionChoice KATL = new OptionChoice("KATL", "Atlanta");
    public static OptionChoice PHNL = new OptionChoice("PHNL", "Honolulu");
    public static OptionChoice KIND = new OptionChoice("KIND", "Indianapolis");
    public static OptionChoice KMCI = new OptionChoice("KMCI", "Kansas City");
    public static OptionChoice KMSY = new OptionChoice("KMSY", "New Orleans");
    public static OptionChoice KPDX = new OptionChoice("KPDX", "Portland,OR");
    public static OptionChoice KBOS = new OptionChoice("KBOS", "Boston");
    public static OptionChoice KMSP = new OptionChoice("KMSP", "Minneapolis");
    public static OptionChoice KLAS = new OptionChoice("KLAS", "Las Vegas");
    public static OptionChoice KIAD = new OptionChoice("KIAD", "Washington");
    public static OptionChoice KSLC = new OptionChoice("KSLC", "Salt Lake City");
    public static OptionChoice KAUS = new OptionChoice("KAUS", "Austin");
    public static OptionChoice KMEM = new OptionChoice("KMEM", "Memphis");
    public static OptionChoice KBNA = new OptionChoice("KBNA", "Nashville");
    public static OptionChoice KPHL = new OptionChoice("KPHL", "Philadelphia");
    public static OptionChoice KCMH = new OptionChoice("KCMH", "Colombus");
    public static OptionChoice KABQ = new OptionChoice("KABQ", "Albuquerque");

    private String code;
    private String name;

    private OptionChoice(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.code + " - " + this.name;
    }

    @Override
    public int compareTo(OptionChoice o) {
        return this.code.compareTo(o.getCode());
    }

}
