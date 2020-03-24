package com.example.crazyeights;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class TitleView extends View {

	private Bitmap TitleGraphic;
	private int screenW;
	private int screenH;
	private Bitmap playButtonUp;
	private Bitmap playButtonDown;
	private boolean playButtonPressed;
	private Context myContext;
	
	/**
	 * To load the graphics into the memory.
	 * IMPORTANT_NOTE: Since the constructor gets context as a parameter, then, the value passed to it from Activity is THIS
	 * and NOT getApplicationcontext()
	 * @param context
	 */
	public TitleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		myContext = context;
		TitleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
		playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
		playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
	}
	
	/**
	 * This method is called by a VIEW and AFTER the constructor and BEFORE any thing is drawn
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.drawBitmap(TitleGraphic, (screenW-(TitleGraphic.getWidth()))/2, (screenH-TitleGraphic.getHeight())/2, null);
		canvas.drawBitmap(playButtonUp, (screenW-(playButtonUp.getWidth()))/2, (int)(screenH*0.7), null);
		
		if (playButtonPressed) {
			canvas.drawBitmap(playButtonDown, (screenW-(playButtonUp.getWidth()))/2, (int) (screenH*0.7), null);
		}else {
			canvas.drawBitmap(playButtonUp, (screenW-(playButtonUp.getWidth()))/2, (int) (screenH*0.7), null);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		int eventaction = event.getAction();
		
		int X = (int)event.getX();
		int Y = (int)event.getY();
		
		switch (eventaction ) {
		
		case MotionEvent.ACTION_DOWN:
			if (X > ((screenW-playButtonUp.getWidth())/2) && (X < ((screenW-playButtonUp.getWidth())/2) + (playButtonUp.getWidth())) && 
					( Y > (int)(screenH*0.7)) && (Y < (int)(screenH*0.7) + playButtonUp.getHeight())) {
						playButtonPressed = true;
					}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (playButtonPressed) {
				Intent gameIntent = new Intent(myContext, GameActivity.class);
				myContext.startActivity(gameIntent);
			}
			playButtonPressed = false;
			break;
		}
		
		invalidate();
		return true;
	}

}
