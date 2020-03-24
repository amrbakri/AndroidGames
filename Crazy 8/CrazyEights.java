package com.example.crazyeights;

import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.os.Build;

public class CrazyEights extends Activity {

	/**
	 * New Lessons are:
	 * (1) How to create a customized VIEW.
	 * (2) How To control a drawn circle on the VIEW by Dragging and other TOUCH events
	 * (3) What is and how important is onSizeChanged(), and in it is where you SHOULD initialize your Variables. 
	 * (4) Force the App. to cling to a designated screen Orientation.
	 * (5) android:configrationChanges in the manifest.
	 * (6) How to set a specific Screen Time Out e.g: tView.SetKeepScreenOn(true)
	 * (7) How to Make Full Screen
	 * (8) Collections.shuffle(, ); GameView
	 * (9) Setting the scaling factor to the density settings for the onScreen Elements e.g: "Text Size" using
	 *     myContext.getResources().getDisplayMetrics().density;
	 * (10) use the drawText() method of canvas to draw text
	 *     
	 * @author Amr
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		/*
		 * Here we pass "this" as a parameter to the constructor because the constructor accepts "context" as a parameter.
		 * if the constructor was accepting "applicationCotext" as a constructor then, we should have passed 
		 * "getApplicationContext"
		 */
		TitleView tView = new TitleView(this);
		tView.setKeepScreenOn(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(tView);
	}
}
