package core;

import java.util.Scanner;

public class PlayerHum extends Player {
	public PlayerHum() {
		super();
		isHum=true;
		this.name = "ICe";
	}

//	public void play(Player[] ply) {// 
//		Scanner sc = new Scanner(System.in);
//		int key = 0;// 用于判断用户输入
//		int tempRaise;// 用于接受
//		
//
//		if (!this.quit) {
//			System.out.println("请输入你要下的注？（输入0放弃）    你共有$:" + this.money + "元,闷牌下限:"+moneyLast+"走牌下限:"+(long)(moneyLast*2.5));
//			tempRaise = sc.nextInt();
//			if (tempRaise != 0) {// 如果不放弃
//				if (tempRaise > (long)(moneyLast*2.5)&&this.check) {// 加注 看牌
//					this.doRaise(tempRaise-(long)(moneyLast*2.5));
//				} else if (tempRaise > moneyLast&&!this.check) {// 加注 闷
//					this.doRaise(tempRaise-moneyLast);
//				} else {
//					this.doCall();
//				}
//
//			} else {
//				this.doQuit();
//			}
//
//		}
//
//		if (this.check && !this.quit) {// 是否对某玩家比牌？
//			System.out.println("请输入要开牌的玩家：（输入玩家编号,输入‘0’则不开）");
//
//			int numtemp = sc.nextInt();
//			numtemp--;
//			
//			if (numtemp > 0 && numtemp < ply.length && ply[numtemp].check && !ply[numtemp].quit) {
//				if(this.doFight(ply[numtemp])){
//					System.out.println("玩家"+this.name+"赢");
//				}else{
//					System.out.println("玩家"+this.name+"输!");
//				}
//			} else if (numtemp > 0 && !ply[numtemp].check) {
//				System.out.println(ply[numtemp].name + "没有看牌，不能开牌");
//			}
//		}
//		
//	}
//	public void seecard(Player[] ply) {
//		Scanner sc = new Scanner(System.in);
//		int key = 0;// 用于判断用户输入
//		int tempRaise;// 用于接受
//		if (!this.check && !this.quit) {
//			System.out.println("是否查看您的牌？（输入1确认0取消）");
//			key = sc.nextInt();
//			if (key == 1) {
//				this.doCheck();
//				System.out.println(this.cards.getName());
//			}
//		}
//	}
}
