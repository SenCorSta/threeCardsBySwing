package core;

import java.util.Scanner;

import core.Card;

public class CardMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("欢迎来到ICe扎金花^_^面向对象版\nVer0.1.0\n开始游戏前,请输入玩家人数（2-17）：");
		Scanner sc = new Scanner(System.in);
		int playerNum = sc.nextInt();

		Player[] player = new Player[playerNum];
		player[0] = new PlayerHum();
		for (int i = 1; i < player.length; i++) {
			player[i] = new PlayerAI();// 决定人数

		}
		int turns=1;
		do {
			
			System.out.println("-------------------------------------第"+turns+"局:");
			
			oneturn(player, playerNum);
			turns++;
		} while (true);

		// AllCards ac=new AllCards();
		// System.out.println(ac.toSrting());
		// ac.swapCard();
		// System.out.println(ac.toSrting());

	}

	public static void oneturn(Player[] player, int playerNum) {
		for (int i = 0; i < Card.sendted.length; i++) {
			Card.sendted[i] = false;// 重新洗牌
		}
		for (int i = 0; i < player.length; i++) {

			player[i].setCards();// 发牌

			// System.out.println(player[i].cards.getName()+player[i].name+","+player[i].cards.getPowerName());
			// 用于测试发牌统计
		}

		int q = 0;
		do {
			q = playerNum;
			for (int i = 0; i < player.length; i++) {

				if (player[i].quit) {
					q--;
				} else {
					player[i].play(player);
					if (player[i].quit) {
						q--;
					}

				}

			}

		} while (q != 1);
		for (int i = 0; i < player.length; i++) {

			if (!player[i].quit) {
				System.out.println(player[i].name + "获得了胜利!赢了"
						+ player[i].winner() + "元钱.\n");
				
			}
		}
		for (int i = 0; i < player.length; i++) {
			
			System.out.println(player[i].name+player[i].cards.getName() 
					+ "," + player[i].cards.getPowerName()+",剩余钱:"+player[i].money);

		}
		player[0].clear(player);
	}

}
