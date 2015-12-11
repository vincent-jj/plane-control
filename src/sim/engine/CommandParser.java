package sim.engine;

import sim.gui.GUI;
import sim.util.Heading;
import sim.engine.commandReturn.PlaneCommandReturn;

/**
 *
 * @author clement
 */

/**
 * A class to own parser function for command line entries
 */
class CommandParser {

    private static CommandParser instance = new CommandParser();

    private CommandParser() {

    }

    public static CommandParser getParser() {
        return instance;
    }

    /**
     * this intern method is used to factorise code and change easily the way to display error message when the given text is not correct
     * @param error the text to display
     */
    private static void printError(String error){
        System.out.println("ERROR : " + error);//TODO une autre methode d'affichage dédiée aux erreurs ?
    }

    /**
     * this intern method is used to factorise code and change easily the way to display the response of the pilot.
     * @param s the text to display
     */
    private static void printPlaneReturn(PlaneCommandReturn r){
        System.out.println(r.toString());
    }

    /**
     * this parser will analyse a command from GUI (or vocal recognition ?) and call the corresponding function on the corresponding plane.
     * @param command the command to parse. The format muste be PlaneID, ONE space, comand, ONE space[, arg ...]
     * @return true if there is no parse error. Note that even if the parsing is good (ie, true is returned), a plane may not execute the command due to his current state.
     */
    public static boolean processCommand(String command) {
        Engine engine = Engine.getEngine();
        GUI gui = GUI.getGUI();
        command = command.toUpperCase();// we use a non case-sensitive parser
        String[] splitCommand = command.split(" ");//note that we will not parse if the user write to space, a tab or another charcter to separate args
        String arg;//used for the command arg (third string) if needed
        if (splitCommand.length < 2) {
            printError("you must specify at least one argument");
            return false;
        }
        Plane plane = engine.getPlane(splitCommand[0]);
        if (plane == null){
            printError("commands must start with a plane ID");
            return false;
        }

        switch (splitCommand[1].intern()){
        	case "S":
        	case "SPEED":
        		if(splitCommand.length < 3) {
        			printError("you must specify at least one argument after clearance");
        			return false;
        		}
        		else {
        			int speed = ((Integer.parseInt(splitCommand[2]))*10)/36;
        			printPlaneReturn(plane.setSpeed(speed));
        		}
            case "C":
            case "CLEARANCE":
                if (splitCommand.length < 3) {
                    printError("you must specify at least one argument after clearance");
                    return false;
                }
                arg = splitCommand[2];
                if (arg.matches("\\d\\d?")){//regex : one or two digits
                    int alt =1000*Integer.parseInt(arg);
                    printPlaneReturn(plane.setAltitude(alt));
                }else if (arg.matches("\\d\\d\\d")){//regex : three digits
                    Heading heading = new Heading(Integer.parseInt(arg));
                    printPlaneReturn(plane.setHeading(heading));
                }else{
                    Station s = engine.getMapObjects().getStation(arg);
                    if (s == null){
                        printError(arg + " isn't a Station name");
                        return false;
                    }else{
                        printPlaneReturn(plane.setStation(s));
                    }
                }
                return true;
            case "T" :
            case "TAKEOFF" :
                if (!plane.isTakingOffPlane()){
                    printError("This plane must land, not take-off");
                    return false;
                }else{
                    TakingOffPlane tPlane = (TakingOffPlane) plane;
                    printPlaneReturn(tPlane.takeOff());
                }
                return true;
            case "W":
            case "LINEUP":
                if (!plane.isTakingOffPlane()){
                    printError("This plane must land, not take-off");
                    return false;
                }else{
                TakingOffPlane tPlane = (TakingOffPlane) plane;
                printPlaneReturn(tPlane.lineUp());
                }
                return true;
            case "L":
            case "LANDING":
                if (plane.isTakingOffPlane()){
                    printError("This plane must take-oof, not land");
                    return false;
                }
                if (splitCommand.length < 3) {
                    printError("you must specify at least one argument after landing");
                    return false;
                }
                LandingPlane lPlane = (LandingPlane) plane;
                arg = splitCommand[2];
                Runway r = engine.getMapObjects().getRunway(arg);
                if (r == null){
                    printError("cannot find the runway "+arg+".");
                    return false;
                }else{
                    printPlaneReturn(lPlane.land(r));
                }
                return true;
            default :
                printError("unknow command");
                return false;
        }
    }
}
