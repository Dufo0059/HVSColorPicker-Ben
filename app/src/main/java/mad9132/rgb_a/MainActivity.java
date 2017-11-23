package mad9132.rgb_a;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import model.RGBAModel;

/**
 * The Controller for RGBAModel.
 *
 * As the Controller:
 *   a) event handler for the View
 *   b) observer of the Model (RGBAModel)
 *
 * Features the Update / React Strategy.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 * @version 1.0
 */
public class MainActivity extends Activity implements Observer, SeekBar.OnSeekBarChangeListener {
    // CLASS VARIABLES
    private static final String ABOUT_DIALOG_TAG = "About";
    private static final String LOG_TAG          = "RGBA";
    private static final String HKEY = "H";
    private static final String SKEY = "S";
    private static final String BKEY = "B";

    // INSTANCE VARIABLES
    // Pro-tip: different naming style; the 'm' means 'member'
    private AboutDialogFragment mAboutDialog;
    private TextView            mColorSwatch;
    private RGBAModel           mModel;
    private SeekBar             mHueSB;
    private SeekBar             mSatSB;
    private SeekBar             mBrightSB;

    //TODO: declare private members for mSatSB, mBrightSB, and mAlphaSB
    private TextView            mHueTV;
    private TextView            mSatTV;
    private TextView            mBrightTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a new AboutDialogFragment()
        // but do not show it (yet)
        mAboutDialog = new AboutDialogFragment();

        // Instantiate a new RGBA model
        // Initialize the model red (max), green (min), blue (min), and alpha (max)
        mModel = new RGBAModel();
        mModel.setHue( RGBAModel.MAX_HUE );
        mModel.setSaturation(0);
        mModel.setBright(0);
        // The Model is observing this Controller (class MainActivity implements Observer)
        mModel.addObserver( this );

        // reference each View
        mColorSwatch = (TextView) findViewById( R.id.colorSwatch );
        mSatSB = (SeekBar) findViewById( R.id.satSB);
        mBrightSB = (SeekBar) findViewById( R.id.brightSB);
        mHueSB = (SeekBar) findViewById( R.id.hueSB );
        mHueTV = (TextView) findViewById( R.id.hueTV);
        mSatTV = (TextView) findViewById( R.id.satTV);
        mBrightTV = (TextView) findViewById( R.id.brightTV);
       // mAlphaTV = (TextView) findViewById( R.id.Alpha );


        mSatSB.setMax( RGBAModel.MAX_SATURATION );
        mBrightSB.setMax( RGBAModel.MAX_BRIGHT );
        mHueSB.setMax( RGBAModel.MAX_BRIGHT );

        mSatSB.setOnSeekBarChangeListener( this );
        mBrightSB.setOnSeekBarChangeListener( this );
        mHueSB.setOnSeekBarChangeListener( this );


        // initialize the View to the values of the Model
        //mAlphaSB.setProgress(mModel.getAlpha());
        this.updateView();

        TextView HSVdisplay = (TextView) findViewById(R.id.colorSwatch);


        HSVdisplay.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                // generate the new random number
                //Toast.makeText(getApplicationContext() ,"test2", Toast.LENGTH_SHORT).show();
                String message = String.format("H:%d\nS:%d\nB:%d",mModel.getHue(),mModel.getSaturation(),mModel.getBright());
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                return true;


            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch ( item.getItemId() ) {

            case R.id.action_about:
                mAboutDialog.show( getFragmentManager(), ABOUT_DIALOG_TAG );
                return true;

            //TODO: handle the remaining menu items

            default:
                Toast.makeText(this, "MenuItem: " + item.getTitle(), Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Event handler for the <SeekBar>s: red, green, blue, and alpha.
     */

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // Did the user cause this event?
        // YES > continue
        // NO  > leave this method
        if ( fromUser == false ) {
            return;
        }

        // Determine which <SeekBark> caused the event (switch + case)
        // GET the SeekBar's progress, and SET the model to it's new value
        switch ( seekBar.getId() ) {
            case R.id.hueSB:
                mModel.setHue(mHueSB.getProgress() );
                mHueTV.setText( getResources().getString(R.string.hueProgress, progress).toUpperCase() );
                break;

            case R.id.satSB:
                mModel.setSaturation( mSatSB.getProgress() );
                mSatTV.setText( getResources().getString(R.string.satProgress, progress).toUpperCase() );
                break;

            case R.id.brightSB:
                mModel.setBright( mBrightSB.getProgress() );
                mBrightTV.setText( getResources().getString(R.string.brightProgress, progress).toUpperCase() );
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // No-Operation
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mHueTV.setText( getResources().getString(R.string.hue) );
                break;
            case R.id.satSB:
                mSatTV.setText( getResources().getString(R.string.saturation) );
                break;

            case R.id.brightSB:
                mBrightTV.setText( getResources().getString(R.string.brightness) );
                break;

     //       case R.id.alphaSB:
    //            mHueTV.setText( getResources().getString(R.string.alpha) );
    //            break;
        }


    }

    // The Model has changed state!
    // Refresh the View to display the current values of the Model.
    @Override
    public void update(Observable observable, Object data) {
//        Log.i( LOG_TAG, "The color (int) is: " + mModel.getColor() + "" );

        this.updateView();
    }

    private void updateColorSwatch() {

        mColorSwatch.setBackgroundColor(Color.HSVToColor(new float[]{(float)mModel.getHue(), (float)mModel.getSaturation()/100
                , (float)mModel.getBright()/100}
        ));
    }

    private void updateHueSB() {
        //GET the model's red value, and SET the red <SeekBar>
        mHueSB.setProgress( mModel.getHue() );
    }
    private void updateBrightSB() {
        //GET the model's red value, and SET the red <SeekBar>
        mBrightSB.setProgress( mModel.getBright() );
    }
    private void updateSaturationSB() {
        //GET the model's red value, and SET the red <SeekBar>
        mSatSB.setProgress( mModel.getSaturation() );
    }


    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateSaturationSB();
        this.updateBrightSB();

    }

    public void onClick(View view) {

        Button but = (Button) view;
        float[] currentHSV = new float[3];
        ColorDrawable buttonColor = (ColorDrawable)but.getBackground();
        int colorId = buttonColor.getColor();

        Color.colorToHSV(colorId,currentHSV);

        mModel.setHSV((int)(currentHSV[0]),(int)(currentHSV[1]*100),(int)(currentHSV[2]*100));
        updateColorSwatch();

        String message = String.format("H:%d\nS:%d\nB:%d",mModel.getHue(),mModel.getSaturation(),mModel.getBright());
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
}
}

