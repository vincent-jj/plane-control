package sim.gui.hud.strip;

/**
 *
 * @author clement
 */
class StripString {

    private String text;

    public StripString(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "<html>" + this.text.replaceAll("[\\n]", "<br>") + "</html>";
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
