package com.example.crazyeights;

import android.graphics.Bitmap;

public class Card {
	
	private int id;
	private Bitmap bmp;
	private int suit;
	private int rank;
	private int scoreValue = 0;
	
	public Card (int newId){
		id = newId;
		suit = Math.round((id/100) * 100);//e.g. 309/100 = 3; 3*100 = 300
		rank = id - suit;
		if (rank == 8) {
			scoreValue = 50;
		} else if (rank == 14) {
			scoreValue = 1;
		} else if (rank > 9 && rank < 14) {
			scoreValue = 10;
		} else {
			scoreValue = rank;
		}
	}
	
	public int getScoreValue() {
		return scoreValue;
		}
	
	public void setBitmap(Bitmap newBitmap) {
		bmp = newBitmap;
		}
	
	public Bitmap getBitmap() {
		return bmp;
		}
	
	public int getId() {
		return id;
		}
	
	public int getSuit() {
		return suit;
		}
	
	public int getRank() {
		return rank;
		}

}
