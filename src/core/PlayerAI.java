package core;

import java.util.Random;

public class PlayerAI extends Player {
	int aiInSide = 500;// 电脑的心理值

	public PlayerAI() {
		super();
		Random ran = new Random();
		aiInSide=500+ran.nextInt(2000);//随机生成一个心理值
		this.name = makeName();
	}
	
	public void play(Player[] ply) {
		info = "";
		int quitnum = 0;
		for (int m = 0; m < ply.length; m++) {// 计算没有弃牌的人数

			if (this.quit == false) {
				quitnum++;
			}
		}

		int bigkinds = this.cards.power;
		Random ran = new Random();
		if (ran.nextInt(1000)<400&&!this.check && risk > this.aiInSide) {// 单AI承受不了时50%的机会看牌，进行看牌
			this.check = true;

			switch (bigkinds) {
			case 1:
				aiInSide = 800;
				break;
			case 2:
				this.aiInSide = 2400;
				break;
			case 3:
				this.aiInSide = 9000;
				break;
			case 4:
				this.aiInSide = 15000;
				break;
			case 5:
				this.aiInSide = 30000;
				break;
			case 6:
				this.aiInSide = 99000;
				break;
			default:
				break;
			}
			aiInSide += ran.nextInt(2000);
			System.out.println(info += "\n" + this.name + "开始看牌.");

		}
		if (!this.quit && this.check && this.aiInSide < risk) {// 没有看牌不会弃牌
			quitnum = 0;// 弃牌人数
			int unQuitOther = 0;// 另外一个没有开牌的玩家编号
			for (int j = 0; j < ply.length; j++) {// 计算没有弃牌的人数

				if (ply[j].quit == false) {
					quitnum++;
				}

			}
			if (quitnum == 2 && this.cards.power > 2) {// 如果只剩下2人，会进行强制开牌
				for (int j = 0; j < ply.length; j++) {
					if (ply[j].quit == false && j != this.id) {
						unQuitOther = j;// 找到另外一个没有开牌的玩家编号
					}
				}
				
				if (ply[unQuitOther].check==false&&this.cards.power>3) {//如果剩下的玩家是闷牌
					if (this.money<=0) {
						this.doCall();
						System.out.println(info += "\n" + this.name + "对玩家"
								+ ply[unQuitOther].name + "进行开牌！");

						if (this.doFight(ply[unQuitOther])) {

							System.out.println(info += "\n" + ply[unQuitOther].name
									+ "输了");

						} else {
							System.out.println(info += "\n" + this.name + "输了");
						}
						quitnum--;
					}
				}else{
					this.doCall();
					System.out.println(info += "\n" + this.name + "对玩家"
							+ ply[unQuitOther].name + "进行开牌！");

					if (this.doFight(ply[unQuitOther])) {

						System.out.println(info += "\n" + ply[unQuitOther].name
								+ "输了");

					} else {
						System.out.println(info += "\n" + this.name + "输了");
					}
					quitnum--;
				}
				

			} else if (quitnum == 2 && this.cards.power <= 2) {
				for (int j = 0; j < ply.length; j++) {
					if (ply[j].quit == false && j != this.id) {
						unQuitOther = j;// 找到另外一个没有开牌的玩家编号
					}

				}
				if (ply[unQuitOther].check == false && this.cards.power == 2) {// 如果另外一个玩家是闷牌,且自己牌是对子,则开牌
					this.doCall();
					System.out.println(info += "\n" + this.name + "对玩家"
							+ ply[unQuitOther].name + "进行开牌！");

					if (this.doFight(ply[unQuitOther])) {

						System.out.println(info += "\n" + ply[unQuitOther].name
								+ "输了");

					} else {
						System.out.println(info += "\n" + this.name + "输了");
					}
					quitnum--;
				} else {// 只有两人并且牌太小则弃牌
					this.doQuit();
					System.out.println(info += "\n" + this.name + "放弃了牌局.");
				}

			} else if (quitnum != 1) {// 多于两人或者牌太小则弃牌
				this.doQuit();
				
				System.out.println(info += "\n" + this.name + "放弃了牌局.");

			}

		}

		long addmoney = 0;
		if (!this.check && !this.quit) {// 闷牌

			if (ran.nextInt(1000) < 100) {// 看机会涨价
				addmoney += ran.nextInt(10);
				this.doRaise(addmoney);
			} else {
				this.doCall();
			}

		} else if (this.check && !this.quit) {// 看牌

			if (this.aiInSide / risk > 2) {// 如果牌太大就涨价
				addmoney += ran.nextInt(20);
				this.doRaise(addmoney);
			} else {
				this.doCall();
			}

		}

	}
	private String makeName(){
		String name="";
		String name2="";
		switch (new Random().nextInt(30)) {
		case 0:
			name="小";
			break;
		case 1:
			name="大";
			break;
		case 2:
			name="老";
			break;
		case 4:
			name="狗";
			break;
		case 5:
			name="高";
			break;
		case 6:
			name="李";
			break;
		case 7:
			name="强";
			break;
		case 8:
			name="张";
			break;
		case 9:
			name="龙";
			break;
		case 10:
			name="二";
			break;
		case 11:
			name="鸡";
			break;
		case 12:
			name="猛";
			break;
		case 13:
			name="疯";
			break;
		case 14:
			name="靓";
			break;
		case 15:
			name="山";
			break;
		case 16:
			name="爆";
			break;
		case 17:
			name="十三";
			break;
		case 18:
			name="臭";
			break;
		case 19:
			name="帅";
			break;
		case 20:
			name="陈";
			break;
		case 21:
			name="孙";
			break;
		case 22:
			name="赵";
			break;
		case 23:
			name="曹";
			break;
		case 24:
			name="刘";
			break;
		case 25:
			name="谢";
			break;
		case 26:
			name="吴";
			break;
		case 27:
			name="郭";
			break;
		case 28:
			name="关";
			break;
		case 29:
			name="杨";
			break;
		

		default:
			name="小";
			break;
		}
		switch (new Random().nextInt(31)) {
		case 0:
			name2="东";
			break;
		case 1:
			name2="二";
			break;
		case 2:
			name2="胖";
			break;
		case 4:
			name2="丽";
			break;
		case 5:
			name2="进";
			break;
		case 6:
			name2="子";
			break;
		case 7:
			name2="波";
			break;
		case 8:
			name2="猫";
			break;
		case 9:
			name2="猪";
			break;
		case 10:
			name2="娟";
			break;
		case 11:
			name2="少";
			break;
		case 12:
			name2="天";
			break;
		case 13:
			name2="头";
			break;
		case 14:
			name2="帅";
			break;
		case 15:
			name2="王";
			break;
		case 16:
			name2="神";
			break;
		case 17:
			name2="仙";
			break;
		case 18:
			name2="蛋";
			break;
		case 19:
			name2="妹";
			break;
		case 20:
			name2="B";
			break;
		case 21:
			name2="哥";
			break;
		case 22:
			name2="姐";
			break;
		case 23:
			name2="爷";
			break;
		case 24:
			name2="先生";
			break;
		case 25:
			name2="天生";
			break;
		case 26:
			name2="小姐";
			break;
		case 27:
			name2="大姐";
			break;
		case 28:
			name2="主席";
			break;
		case 29:
			name2="将军";
			break;
		case 30:
			name2="中队";
			break;
		

		default:
			name2="毛";
			break;
		}
		return name+name2;
	}
}
