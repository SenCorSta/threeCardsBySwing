package core;

import java.awt.Image;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;



public class Card {
	int id;
	String color;
	String point;
	String name;
	Image img;
	final static int CARD_NUMS = 52;
	static boolean[] sendted = new boolean[CARD_NUMS];

	public Card() {

		this.id = setId();
		this.color = getColor(this.id);
		this.point = getPoint(this.id);
		this.name = "[" + this.color + this.point + "]";
		try {
			this.img = ImageIO.read(Card.class.getResource("png/"+id+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public Card(int i) {

		this.id = i;
		this.color = getColor(this.id);
		this.point = getPoint(this.id);
		this.name = "[" + this.color + this.point + "]";
		try {
			this.img = ImageIO.read(Card.class.getResource("png/"+id+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int setId() {// 产生一个随机不重复的扑克牌id
		Random ran = new Random();

		int index;
		do {
			index = ran.nextInt(CARD_NUMS);
		} while (sendted[index]);// 使用一个逻辑数组标记是否重复，若为true则重新选一遍

		sendted[index] = true;
		return index;

	}

	private String getColor(int id) {
		int temp = id / 13;
		String color = "";
		switch (temp) {
		case 0:
			color = "黑桃";
			break;
		case 1:
			color = "红桃";
			break;
		case 2:
			color = "梅花";
			break;
		default:
			color = "方片";
			break;
		}
		return color;

	}

	private String getPoint(int id) {
		int temp = id % 13 + 1;
		String point = "";
		switch (temp) {
		case 1:
			point = " A";
			break;
		case 10:
			point = "10";
			break;
		case 11:
			point = " J";
			break;
		case 12:
			point = " Q";
			break;
		case 13:
			point = " k";
			break;
		default:
			point = " " + temp;
			break;
		}
		return point;

	}

	public String getName() {
		return this.name;
	}

}
