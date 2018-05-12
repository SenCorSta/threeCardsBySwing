package core;

public class Player {
	String name = "";
	int id = 0;
	long money = 1000;// 玩家金钱
	boolean check = false;// 是否看牌
	boolean quit = false;// 是否弃牌
	boolean isHum = false;// 是否是人类
	boolean left = false;// 是否离开桌子
	Cards cards;// 玩家的牌
	public static String info="";

	static int playerOfNum = 0;// 静态变量,每实例化一次就加1
	static int risk = 0;// 静态变量,场上风险
	static long moneyLast = 5;// 静态变量,最小下注
	static long moneyAll = 0;// 静态变量,所有注的和

	static final int RISK_ADD = 120;// 风险增加基数
	
	
	public Player() {
		this.id = playerOfNum;
		this.name = "玩家" + id;
		this.money = 1000;
		playerOfNum++;
	}
	
	public static String getInfo(){
		return info;
	}

	public int getId() {
		return this.id;
	}

	public void setCards() {// 得到一组牌

		this.cards = new Cards(this.id);

	}
	public long addMoney(long money){
		this.money+=money;
		return money;
		
	}

	public boolean doFight(Player p) {// 与一个玩家进行比牌
		if (this.cards.Isbig(p.cards)) {

			p.doQuit();
		} else {
			this.doQuit();
		}

		return this.cards.Isbig(p.cards);

	}

	public void doCheck() {// 看牌
		this.check = true;
	}
	public void doBet(long bet) {// 打底
		if (!left) {
			this.money -= bet;
			moneyAll+=bet;
		}
		
	}

	public void doQuit() {// 弃牌
		risk -= 200;
		this.quit = true;
	}

	public void doCall() {// 跟
		long calls = moneyLast;
		if (this.check) {
			calls *= 2.5;
			System.out.println(info=this.name + "跟走" + calls + "元.");
			
		} else {
			System.out.println(info=this.name + "跟闷" + calls + "元.");
		}
		// System.out.println("当前闷牌下限:" + moneyLast);
		moneyAll += calls;
		this.money -= calls;

		risk += RISK_ADD+calls*10;
	}

	public void doRaise(long raises) {// 涨价
		long calls = moneyLast;
		
		
		if (this.check) {
			calls = raises + (long)(moneyLast*2.5);
			moneyLast += raises / 2.5;
			System.out.println(info=this.name + "涨价,走" + calls + "元.");
		} else {
			calls = raises + moneyLast;
			moneyLast += raises;
			System.out.println(info=this.name + "涨价,闷" + calls + "元.");
		}
		// System.out.println("当前闷牌下限:" + moneyLast);
		risk += RISK_ADD+calls*10+raises*20;
		moneyAll += calls;
		this.money -= calls;

	}

	public long winner() {// 单出现赢家时,执行加钱操作
		long temp = moneyAll;
		
		this.money += temp;
		return temp;
	}

	public static void clear(Player[] ply) {// 重置战场变量
		moneyAll = 0;
		moneyLast = 5;
		risk = 0;
		for (int i = 0; i < ply.length; i++) {
			ply[i].check = false;
			if (ply[i].money<=0) {
				ply[i].quit = true;
				ply[i].left = true;
			}else{
				ply[i].quit = false;
			}
			
			if (!ply[i].isHum) {
				PlayerAI ai = (PlayerAI) ply[i];
				ai.aiInSide = 500;
			}

		}
	}
	public static void playerOfNumToZero(){
		playerOfNum=0;
	}

	public void play(Player[] ply) {// 留为空 给子类重写

	}

	public void seecard(Player[] ply) {

	}

}
