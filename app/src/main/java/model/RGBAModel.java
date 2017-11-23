package model;

import java.util.Observable;

import android.graphics.Color;

/**
 * The model holds the data.
 *
 * Model color.
 * Based on red, green, blue and alpha (transparency).
 *
 * RGB: integer values in the domain range of 0 to 255 (inclusive).
 * Alpha: integer value in the domain range of 0 to 255 (inclusive).
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 * @version 1.0
 */
public class RGBAModel extends Observable {

    // CLASS VARIABLES
    //public static final Integer MAX_ALPHA = 255;
    public static final Integer MAX_HUE   = 359;
    public static final Integer MAX_SATURATION   = 100;
    public static final Integer MAX_BRIGHT   = 100;
   // public static final Integer MIN_ALPHA = 0;
   // public static final Integer MIN_RGB   = 0;

    // INSTANCE VARIABLES
    private Integer bright;
    private Integer saturation;
    private Integer hue;

    /**
     * No argument constructor.
     *
     * Instantiate a new instance of this class, and
     * initialize red, green, blue, and alpha to max values.
     */
    public RGBAModel() {
        this( MAX_HUE, MAX_SATURATION, MAX_BRIGHT );
    }

    /**
     * Convenience constructor.
     *
     * @param hue - starting red value
     * @param saturation - starting green value
     * @param bright - starting blue value
     */
    public RGBAModel( Integer hue, Integer saturation, Integer bright ) {
        super();

        this.bright  = bright;
        this.saturation = saturation;
        this.hue= hue;
    }



    // GETTERS

    public Integer getBright()  { return bright; }
    public Integer getSaturation() { return saturation; }
    public Integer getHue()   { return hue; }

    // SETTERS
    public void setBright(Integer bright) {
        this.bright = bright;

        this.updateObservers();
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;

        this.updateObservers();
    }

    public void setHue(Integer hue) {
        this.hue = hue;

        this.updateObservers();
    }

    // Convenient setter: set R, G, B
    public void setHSV( Integer hue, Integer saturation, Integer bright ) {
        this.hue   = hue;
        this.saturation = saturation;
        this.bright  = bright;

        this.updateObservers();
    }

    // the model has changed!
    // broadcast the update method to all registered observers
    private void updateObservers() {
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public String toString() {
        return "RGB-A [h=" + hue + " s=" + saturation + " b=" + bright + "]";
    }

    // Proof that my model is independent of any view.
    public static void main( String[] args ) {
        RGBAModel model = new RGBAModel( 127, 127, 127 );

        System.out.println( model );
    }


}