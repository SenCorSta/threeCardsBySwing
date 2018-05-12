package core;

import java.util.Random;

public class AllCards {
	Card[] allcards=new Card[core.Card.CARD_NUMS];
	public AllCards(){
		for (int i = 0; i < core.Card.CARD_NUMS; i++) {
			allcards[i]=new Card(i);
		}
		
	}
	
	public void swapCard(){
		Random ran=new Random();
		for (int i = allcards.length-1; i > 0; i--){
			int j=ran.nextInt(i);
			Card c=allcards[i];
			allcards[i]=allcards[j];
			allcards[j]=c;
		}
		
	}
	public String toSrting(){
		String str="";
		for (int i = 0; i < allcards.length; i++) {
			str+=allcards[i].getName();
			if((i+1)%13==0){
				str+="\n";
			}
			
		}
		return str;
	}
}
