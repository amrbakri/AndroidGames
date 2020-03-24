package com.example.crazyeights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends View {

	private Paint whitePaint;
	
	private Bitmap cardBack;
	private Bitmap nextCardButton;
	
	private Context myContext;
	private List<Card> deck = new ArrayList<Card>();
	private int scaledCardW;
	private int scaledCardH;
	private int screenW;
	private int screenH;
	private float scale;
	
	private int movingCardIdx = -1;
	private int movingX;
	private int movingY;
	
	private int oppScore;
	private int myScore;
	
	private int validRank = 8;
	private int validSuit = 0;
	
	private int scoreThisHand = 0;
	
	private boolean myTurn;
	
	private List<Card> myHand = new ArrayList<Card>();
	private List<Card> oppHand = new ArrayList<Card>();
	private List<Card> discardPile = new ArrayList<Card>();
	
	private ComputerPlayer computerPlayer = new	ComputerPlayer();
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		myContext = context;
		scale =	myContext.getResources().getDisplayMetrics().density;
		whitePaint = new Paint();
		whitePaint.setAntiAlias(true);
		whitePaint.setColor(Color.BLUE);
		whitePaint.setStyle(Paint.Style.STROKE);
		whitePaint.setTextAlign(Paint.Align.LEFT);
		whitePaint.setTextSize(scale*15);
		
		//myTurn = new Random().nextBoolean();
		//myTurn = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//canvas.drawBitmap(deck.get(0).getBitmap(), 0, 0, null);
		canvas.drawText("Computer Score: " + Integer.toString(oppScore), 10, whitePaint.getTextSize()+10, whitePaint);
		canvas.drawText(""+scale, 10, 120, whitePaint);
		canvas.drawText("My Score: " + Integer.toString(myScore), 10, screenH-whitePaint.getTextSize()-10, whitePaint);
		
		if (myHand.size() > 7) {
			canvas.drawBitmap(nextCardButton,screenW-nextCardButton.getWidth()-(30*scale), 
					screenH-nextCardButton.getHeight()-scaledCardH-(90*scale),	null);
			}
		for (int i = 0; i < myHand.size(); i++) {
			if (i < 7) {
				if (i == movingCardIdx) {
				canvas.drawBitmap(myHand.get(i).getBitmap(),movingX,movingY,null);
				} else {
				if (i < 7) {
					canvas.drawBitmap(myHand.get(i).getBitmap(),i*(scaledCardW+5),screenH-scaledCardH-whitePaint.
							getTextSize()-(50*scale),null);
				}
			}
		}
		
		for (int i1 = 0; i1 < oppHand.size(); i1++) {
			canvas.drawBitmap(cardBack, i1*(scale*5), whitePaint.getTextSize()+(50*scale),	null);
		}
		
		canvas.drawBitmap(cardBack,	(screenW/2)-cardBack.getWidth()-10,	(screenH/2)-(cardBack.getHeight()/2), null);
		
		if (!discardPile.isEmpty()) {
			canvas.drawBitmap(discardPile.get(0).getBitmap(), (screenW/2)+10, (screenH/2)-(cardBack.getHeight()/2),	null);
		}
		
		for (int i1 = 0; i1 < myHand.size(); i1++) {
			if (i1 == movingCardIdx) {
				canvas.drawBitmap(myHand.get(i1).getBitmap(), movingX, movingY, null);
			} else {
				canvas.drawBitmap(myHand.get(i1).getBitmap(),i1*(scaledCardW+5),screenH-scaledCardH-whitePaint.
						getTextSize()-(50*scale), null);
			}
		}
		}
			invalidate();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		initCards();
		dealCards();
		drawCard(discardPile);
		
		nextCardButton = BitmapFactory.decodeResource(getResources(),R.drawable.arrow_next);
		
		Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(),R.drawable.card_back);
		    scaledCardW = (int) (screenW/8);
			scaledCardH = (int) (scaledCardW*1.28);
		cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW,scaledCardH,false);
			
			validSuit = discardPile.get(0).getSuit();
			validRank = discardPile.get(0).getRank();
			
			myTurn = new Random().nextBoolean();
			if (!myTurn) {
				makeComputerPlay();
			}
	}
	
	private void updateScores() {
		for (int i = 0; i < myHand.size(); i++) {
			oppScore += myHand.get(i).getScoreValue();
			scoreThisHand += myHand.get(i).getScoreValue();
		}
		for (int i = 0; i < oppHand.size(); i++) {
			myScore += oppHand.get(i).getScoreValue();
			scoreThisHand += oppHand.get(i).getScoreValue();
		}
	}
	
	private void makeComputerPlay() {
		// TODO Auto-generated method stub
		int tempPlay = 0;
		while (tempPlay == 0) {
			tempPlay = computerPlayer.makePlay(oppHand,validSuit, validRank);
			if (tempPlay == 0) {
				drawCard(oppHand);
			}
		}
		
		if (tempPlay == 108 || tempPlay == 208 ||tempPlay == 308 || tempPlay == 408) {
			validRank = 8;
			validSuit = computerPlayer.chooseSuit(oppHand);
			String suitText = "";
			if (validSuit == 100) {
				suitText = "Diamonds";
			} else if (validSuit == 200) {
			suitText = "Clubs";
			} else if (validSuit == 300) {
			suitText = "Hearts";
			} else if (validSuit == 400) {
			suitText = "Spades";
			}
			Toast.makeText(myContext, "Computer chose " +suitText, Toast.LENGTH_SHORT).show();
					} else {
						validSuit = Math.round((tempPlay/100) * 100);
						validRank = tempPlay - validSuit;
					}	
					for (int i = 0; i < oppHand.size(); i++) { 
						Card tempCard = oppHand.get(i);
							if (tempPlay == tempCard.getId()) {
								discardPile.add(0, oppHand.get(i));
								oppHand.remove(i);
							}
					}
					if (oppHand.isEmpty()) {
						endHand();
						}
					myTurn = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int eventaction = event.getAction();
		int X = (int)event.getX();
		int Y = (int)event.getY();
		
		switch (eventaction ) {
		
		case MotionEvent.ACTION_DOWN:
			if (myTurn) {
				for (int i = 0; i < 7; i++) {
					if (X > i*(scaledCardW+5) && X < i*(scaledCardW+5)+ scaledCardW && Y > screenH-scaledCardH-whitePaint.
					getTextSize()-(50*scale)) {
						movingCardIdx = i;
						/*
						 * the offset is added here so the on-screen UI elements are NOT grabbed from its top-left 
						 */
						movingX = X - (int) (30 * scale);
						movingY = Y - (int) (70 * scale);
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			/*
			 * the offset is added here so the on-screen UI elements are NOT grabbed from its top-left 
			 */
			movingX = X - (int) (30 * scale);
			movingY = Y - (int) (70 * scale);
			break;
		case MotionEvent.ACTION_UP:
			if (movingCardIdx > -1 && X > (screenW/2)-(100*scale) && X < (screenW/2)+(100*scale) &&
			Y > (screenH/2)-(100*scale) &&	Y < (screenH/2)+(100*scale) &&	(myHand.get(movingCardIdx).getRank() == 8 ||
					myHand.get(movingCardIdx).getRank() == 	validRank ||myHand.get(movingCardIdx).getSuit() == 
					validSuit)) {
				
				validRank = myHand.get(movingCardIdx).getRank();
				validSuit = myHand.get(movingCardIdx).getSuit();
				discardPile.add(0, myHand.get(movingCardIdx));
				myHand.remove(movingCardIdx);
				if (myHand.isEmpty()) {
					//Handle end of hand
					endHand();
				}else {
					if (validRank == 8) {
						showChooseSuitDialog();
					}else {
						myTurn = false;
						makeComputerPlay();
					}
				}
			}
			
			if (movingCardIdx == -1 && myTurn && X > (screenW/2)-(100*scale) && 	X < (screenW/2)+(100*scale) &&
					Y > (screenH/2)-(100*scale) &&	Y < (screenH/2)+(100*scale)) {
						if (checkForValidDraw()) {
							drawCard(myHand);
						} else {
							Toast.makeText(myContext, "You have a valid play.", Toast.LENGTH_SHORT).show();
						}
			}
			
			if (myHand.size() > 7 && X > screenW-nextCardButton.getWidth()-(30*scale) && 
				Y > screenH-nextCardButton.getHeight()-scaledCardH-	(90*scale) &&
				Y < screenH-nextCardButton.getHeight()-scaledCardH-	(60*scale)) {
					Collections.rotate(myHand, 1);
					}
			
			movingCardIdx = -1;
			break;
		}
		invalidate();
		return true;
	}
	
	private void initCards() {
		for (int i = 0; i < 4; i++) {
			for (int j = 102; j < 115; j++) {
				int tempId = j + (i*100);
				Card tempCard = new Card(tempId);
				int resourceId = getResources().getIdentifier("card"+ tempId, "drawable",myContext.getPackageName()); 
				Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(),resourceId);
				scaledCardW = (int) (screenW/8); 
				scaledCardH = (int) (scaledCardW*1.28);
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap,	scaledCardW, scaledCardH, false);
				tempCard.setBitmap(scaledBitmap);
				deck.add(tempCard);
			}
		}
	}
	
	private void drawCard(List<Card> handToDraw) {
		handToDraw.add(0, deck.get(0));
		deck.remove(0);
		if (deck.isEmpty()) {
			for (int i = discardPile.size()-1; i > 0 ; i--) {
			    deck.add(discardPile.get(i));
			    discardPile.remove(i);
			    Collections.shuffle(deck,new Random());
			}
		}
	}
	
	private void dealCards() {
		Collections.shuffle(deck,new Random());
		for (int i = 0; i < 7; i++) {
			drawCard(myHand);
			drawCard(oppHand);
		}
	}
	
	private void showChooseSuitDialog() {
		
		final Dialog chooseSuitDialog = new Dialog(myContext);
		chooseSuitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		chooseSuitDialog.setContentView	(R.layout.choose_suit_dialog);
		final Spinner suitSpinner = (Spinner) chooseSuitDialog.findViewById(R.id.suitSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(myContext, R.array.suits,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		suitSpinner.setAdapter(adapter);
		Button okButton = (Button) chooseSuitDialog.findViewById(R.id.okButton);
		okButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				validSuit = (suitSpinner.getSelectedItemPosition()+1)*100;
				String suitText = "";
				if (validSuit == 100) {
					suitText = "Diamonds";
				} else if (validSuit == 200) {
					suitText = "Clubs";
					} else if (validSuit == 300) {
						suitText = "Hearts";
						} else if (validSuit == 400) {
							suitText = "Spades";
						}
				chooseSuitDialog.dismiss();
				Toast.makeText(myContext,"You chose " + suitText,Toast.LENGTH_SHORT).show();
				myTurn = false;
				makeComputerPlay();
			}
		});
		chooseSuitDialog.show();
	}
	
	private void endHand() {
		final Dialog endHandDialog = new Dialog(myContext);
		endHandDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		endHandDialog.setContentView(R.layout.end_hand_dialog);
		updateScores();
		TextView endHandText = (TextView)endHandDialog.findViewById(R.id.endHandText);
		if (myHand.isEmpty()) {
			if (myScore >= 300) {
				endHandText.setText("You reached " + myScore +"points. You won! Would you like to play again?");
			} else {
				endHandText.setText("You went out and got " +scoreThisHand + " points!");
			}
		} else if (oppHand.isEmpty()) {
			endHandText.setText("The computer went out and got "+ scoreThisHand + " points.");
		}
		Button nextHandButton = (Button)endHandDialog.findViewById(R.id.nextHandButton);
		if (oppScore >= 300 || myScore >= 300) {
			nextHandButton.setText("New Game");
		}
		nextHandButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				if (oppScore >= 300 || myScore >= 300) {
					myScore = 0;
					oppScore = 0;
				}
				initNewHand();
				endHandDialog.dismiss();
			}
		});
		endHandDialog.show();
	}
	
	protected void initNewHand() {
		// TODO Auto-generated method stub
		scoreThisHand = 0;
		if (myHand.isEmpty()) {
			myTurn = true;
		} else if (oppHand.isEmpty()) {
			myTurn = false;
		}
		deck.addAll(discardPile);
		deck.addAll(myHand);
		deck.addAll(oppHand);
		discardPile.clear();
		myHand.clear();
		oppHand.clear();
		dealCards();
		drawCard(discardPile);
		validSuit = discardPile.get(0).getSuit();
		validRank = discardPile.get(0).getRank();
		if (!myTurn) { 
			makeComputerPlay();
		}
	}

	private boolean checkForValidDraw() {
		boolean canDraw = true;
		for (int i = 0; i < myHand.size(); i++) {
			int tempId = myHand.get(i).getId();	
			int tempRank = myHand.get(i).getRank();
			int tempSuit = myHand.get(i).getSuit();
			if (validSuit == tempSuit || validRank == tempRank||tempId == 108 || tempId == 208 ||tempId == 308 
					|| tempId == 408) {
				canDraw = false;
			}
		}
		return canDraw;
	}
}
