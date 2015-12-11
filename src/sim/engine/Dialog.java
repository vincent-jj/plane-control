package sim.engine;

import edu.cmu.sphinx.jsgf.JSGFGrammarException;
import edu.cmu.sphinx.jsgf.JSGFGrammarParseException;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import javax.speech.recognition.GrammarException;
import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * A simple Dialog demo showing a simple speech application built using Sphinx-4 that uses the DialogManager.
 * <p/>
 * This demo uses a DialogManager to manage a set of dialog states. Each dialog state potentially has its own grammar.
 */
public class Dialog extends Thread{

	private Engine engine;
    /** Main method for running the Dialog demo. 
     * @throws JSGFGrammarException 
     * @throws JSGFGrammarParseException 
     **/
    public Dialog(Engine engine) throws JSGFGrammarParseException, JSGFGrammarException {
    	this.engine = engine;
    }
    public void run() {
    	try {	
    		URL url;
            url = Dialog.class.getResource("dialog.config.xml");
            ConfigurationManager cm = new ConfigurationManager(url);

       sim.engine.DialogManager dialogManager = (sim.engine.DialogManager)
                    cm.lookup("dialogManager");
       

	   dialogManager.addNode("plane", new MyBehavior(engine));
	    dialogManager.setInitialNode("plane");
	    

            System.out.println("Loading dialogs ...");

            dialogManager.allocate();

            System.out.println("Running  ...");

            try {
				dialogManager.go();
			} catch (JSGFGrammarParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSGFGrammarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            System.out.println("Cleaning up  ...");

            dialogManager.deallocate();

        } catch (IOException e) {
            System.err.println("Problem when loading Dialog: " + e);
        } catch (PropertyException e) {
            System.err.println("Problem configuring Dialog: " + e);
        }
        System.exit(0);
    }
}


/**
 * Defines the standard behavior for a node. The standard behavior is: <ul> <li> On entry the set of sentences that can
 * be spoken is displayed. <li> On recognition if a tag returned contains the prefix 'dialog_' it indicates that control
 * should transfer to another dialog node. </ul>
 */
class MyBehavior extends NewGrammarDialogNodeBehavior {

    private Collection<String> sampleUtterances;

    private Engine engine;
    
    protected MyBehavior(Engine e) {
    	engine = e;
    }
    /** Executed when we are ready to recognize */
    public void onReady() {
        super.onReady();
        help();
    }


    /**
     * Displays the help message for this node. Currently we display the name of the node and the list of sentences that
     * can be spoken.
     */
    protected void help() {
        System.out.println(" ======== " + getGrammarName() + " =======");
        dumpSampleUtterances();
        System.out.println(" =================================");
    }
    
    protected void take_off(String avion) {
	avion = avion.replace("take off","");
	avion = format(avion);
	System.out.println("je fais décoller l'avion "+avion);
	engine.handleCommand(avion + " t");
	}

    protected void landing(String avion, String piste) {
	avion = avion.replaceAll("landing.*","");
	piste = piste.replaceAll(".*landing","");
	avion = format(avion);
	piste = format(piste);
	System.out.println("je fais atterir l'avion "+avion+" sur la piste "+piste);
	engine.handleCommand(avion + " l " + piste);
    }

    public void movingTo(String avion, String balise) {
	avion = avion.replaceAll("clearance.*","");
	balise=balise.replaceAll(".*clearance","");
	avion = format(avion);
	balise = format(balise);
	System.out.println("je déplace l'avion "+avion+" vers la balise "+balise);
	engine.handleCommand(avion + " c " + balise);
    }


    public void changingAltitude(String avion, String altitude) {
	avion = avion.replaceAll("clearance.*","");
	altitude=altitude.replaceAll(".*clearance","");
	avion = format(avion);
	altitude = format(altitude);
	int altitudeInt = Integer.parseInt(altitude);
	System.out.println("je change l'altitude de l'avion "+avion+" pour "+altitudeInt);
	engine.handleCommand(avion + " c " + altitude);
    }

    public void changingAngle(String avion, String angle) {
	avion = avion.replaceAll("clearance.*","");
	angle=angle.replaceAll(".*clearance","");
	avion = format(avion);
	angle = format(angle);
	int angleInt = Integer.parseInt(angle);
	System.out.println("je change le cap de l'avion "+avion+" pour l'angle "+angleInt+"°");
	engine.handleCommand(avion + " c " + angle);
    }


    public void changingSpeed(String avion, String speed) {
	avion = avion.replaceAll("speed.*","");
	speed=speed.replaceAll(".*speed","");
	avion = format(avion);
	speed = format(speed);
	int speedInt = Integer.parseInt(speed);
	System.out.println("je change la vitesse de l'avion "+avion+" pour "+speedInt);
	engine.handleCommand(avion + " s " + speed);
    }


    protected String format(String s) {
	s = s.replace("alpha","a");
	s = s.replace("bravo","b");
	s = s.replace("charlie","c");
	s = s.replace("delta","d");
	s = s.replace("echo","e");
	s = s.replace("foxtrot","f");
	s = s.replace("golf","g");
	s = s.replace("hotel","h");
	s = s.replace("india","i");
	s = s.replace("juliet","j");
	s = s.replace("kilo","k");
	s = s.replace("lima","l");
	s = s.replace("mike","m");
	s = s.replace("november","n");
	s = s.replace("oscar","o");
	s = s.replace("papa","p");
	s = s.replace("quebec","q");
	s = s.replace("romeo","r");
	s = s.replace("sierra","s");
	s = s.replace("tango","t");
	s = s.replace("uniform","u");
	s = s.replace("victor","v");
	s = s.replace("whiskey","w");
	s = s.replace("x-ray","x");
	s = s.replace("yankee","y");
	s = s.replace("zulu","z");
	s = s.toUpperCase();
	s = s.replace("ONE","1");
	s = s.replace("TWO","2");
	s = s.replace("THREE","3");
	s = s.replace("FOUR","4");
	s = s.replace("FIVE","5");
	s = s.replace("SIX","6");
	s = s.replace("SEVEN","7");
	s = s.replace("EIGHT","8");
	s = s.replace("NINE","9");
	s = s.replace("ZERO","0");
	s = s.replace("OH","0");
	s = s.replace(" ","");
	return s;
    }


    /**
     * Executed when the recognizer generates a result. Returns the name of the next dialog node to become active, or
     * null if we should stay in this node
     *
     * @param result the recongition result
     * @return the name of the next dialog node or null if control should remain in the current node.
     */
    public String onRecognize(Result result) throws GrammarException {
        String tag = super.onRecognize(result);

        if (tag != null) {
            System.out.println("\n "
			       + result.getBestFinalResultNoFiller() + '\n');
            if (tag.equals("exit")) {
                System.out.println("Goodbye! Thanks for visiting!\n");
                System.exit(0);
            }
            // if (tag.equals("help")) {
            //     help();
	    else if (tag.equals("take")) {
		take_off(result.getBestFinalResultNoFiller());
		// } else if (tag.equals("stats")) {
		//     TimerPool.dumpAll();
	    } 
	    else if (tag.equals("landing")) {
		landing(result.getBestFinalResultNoFiller(),result.getBestFinalResultNoFiller());
	    }
	    else if(tag.equals("movingTo")) {
		movingTo(result.getBestFinalResultNoFiller(),result.getBestFinalResultNoFiller());
	    }
	    else if (tag.equals("changingAltitude")) {
		changingAltitude(result.getBestFinalResultNoFiller(),result.getBestFinalResultNoFiller());
	    }
	    else if (tag.equals("changingAngle")) {
		changingAngle(result.getBestFinalResultNoFiller(),result.getBestFinalResultNoFiller());
	    }
	    else if (tag.equals("changingSpeed")) {
		changingSpeed(result.getBestFinalResultNoFiller(),result.getBestFinalResultNoFiller());
	    }
	    // } else if (tag.startsWith("goto_")) {
	    //     return tag.replaceFirst("goto_", "");
	} else {
	    System.out.println("\n Oops! didn't hear you.\n");
        }
        return null;
    }


    /**
     * execute the given command
     *
     * @param cmd the command to execute
     */
    private void execute(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            // if we can't run the command, just fall back to
            // a non-working demo.
        }
    }


    /**
     * Collects the set of possible utterances.
     * <p/>
     * TODO: Note the current implementation just generates a large set of random utterances and tosses away any
     * duplicates. There's no guarantee that this will generate all of the possible utterances. (yep, this is a hack)
     *
     * @return the set of sample utterances
     */
    private Collection<String> collectSampleUtterances() {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < 100; i++) {
            String s = getGrammar().getRandomSentence();
            if (!set.contains(s)) {
                set.add(s);
            }
        }

        List<String> sampleList = new ArrayList<String>(set);
        Collections.sort(sampleList);
        return sampleList;
    }


    /** Dumps out the set of sample utterances for this node */
    private void dumpSampleUtterances() {
        if (sampleUtterances == null) {
            sampleUtterances = collectSampleUtterances();
        }

        for (String sampleUtterance : sampleUtterances) {
	    if(sampleUtterance.contains("clearance")) {
		String firstPart = sampleUtterance.replaceAll("clearance.*","");
		String secondPart = sampleUtterance.replaceAll(".*clearance","");
		sampleUtterance = format(firstPart) + " clearance " + format(secondPart);
	    }
	    else if(sampleUtterance.contains("take")) {
		String firstPart = sampleUtterance.replaceAll("take off.*","");
		String secondPart = sampleUtterance.replaceAll(".*take off","");
		sampleUtterance = format(firstPart) + " take off " + format(secondPart);
	    }
	    if(sampleUtterance.contains("landing")) {
		String firstPart = sampleUtterance.replaceAll("landing.*","");
		String secondPart = sampleUtterance.replaceAll(".*landing","");
		sampleUtterance = format(firstPart) + " landing " + format(secondPart);
	    }
	    if(sampleUtterance.contains("speed")) {
		String firstPart = sampleUtterance.replaceAll("speed.*","");
		String secondPart = sampleUtterance.replaceAll(".*speed","");
		sampleUtterance = format(firstPart) + " speed " + format(secondPart);
	    }
	    System.out.println("  " + sampleUtterance);
        }
    }


    /** Indicated that the grammar has changed and the collection of sample utterances should be regenerated. */
    protected void grammarChanged() {
        sampleUtterances = null;
    }
}
