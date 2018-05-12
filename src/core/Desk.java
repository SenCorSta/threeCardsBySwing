package core;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Desk extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000;
	public static long bet = 5;
	private JButton btnCheck = new JButton("看牌");
	private JButton btnNewGame = new JButton("开始新游戏");
	private JButton btnQuit = new JButton("弃牌");
	private JButton btnCall = new JButton("跟牌");
	private JButton btnRaise = new JButton("加注");
	private JButton btnNext = new JButton("下一局");
	private JButton[] btnFight = null;
	private JTextArea text=new JTextArea("8");
	
	
	public static void main(String[] args) {
		Desk desk = new Desk();
		desk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private static Timer timer;
	public static Player[] player;// 玩家们
	public static int playerNum;// 玩家人数
	JPanel deskPanel = new DeskPanel();

	public Desk() {
		//JFrame desk = new JFrame();
		Container c = getContentPane();

		deskPanel.setSize(800, 600);
		deskPanel.setLocation(0, 0);
		// jp.setOpaque(false);//设置为透明

		c.add(deskPanel);		
		
		setSize(800, 600);
		setLocationRelativeTo(null);// 设置窗口为屏幕正中
		setTitle("ICe扎金花 0.3.3");
		//最后更新日期:2013/8/14
		setVisible(true);
		makeBtns();
		init();
		closeBtn();
		//action();
		

	}
	public void makeBtns(){
		btnFight = new JButton[17];
		for (int i = 0; i < 17; i++) {// 初始化开牌按钮
			btnFight[i] = new JButton("开牌");
			btnFight[i].setVisible(false);
		}
	}

	public void repaint() {
		deskPanel.repaint();
	}

//	private static Image background;// 背景图片 未实现
//	static {
//		try {
//			background = ImageIO.read(Desk.class.getResource("png/1.png"));
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//	}
	private static Image backcard;// 背面牌的图案
	static {
		try {
			backcard = ImageIO.read(Desk.class.getResource("png/Back.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	private static Image npcbackcard;// 背面牌的图案
	static {
		try {
			npcbackcard = ImageIO.read(Desk.class
					.getResource("png/npccard.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static long leftTimes = 0;
	public static String leftTimesStr = "";
	public static String winnermsg = "";
	public static String testCode = "";
	public static boolean paintallcards = false;

	private class DeskPanel extends JPanel {// 内部类负责画图

		/**
		 * 画图ID没有的东西 方便以后维护
		 */
		private static final long serialVersionUID = 1000;

		public void paintComponent(Graphics g) {
			// g.drawImage(background, 0, 0, this);

			g.setColor(new Color(0x357515));

			g.fillRect(0, 0, 800, 600);
			g.setColor(new Color(0xffffff));
			g.setFont(new Font(TOOL_TIP_TEXT_KEY, NORMAL, 15));
			g.drawString(winnermsg, 650, 550);
			g.drawString("玩家数:", 700, 282);//玩家数
			//g.drawString(testCode, 600, 400);//测试用代码显示当前行动玩家
			g.drawString(Player.getInfo(), 30, 550);
			g.drawString("累计下注金钱:" + Player.moneyAll, 600, 250);
			paintCard(g);
			//输入玩家框
			
			text.setSize(16,16);
			text.setLocation(755, 270);
			text.setVisible(true);
			this.add(text);
			text.setVisible(true);
			//开始新游戏
			btnNewGame.setSize(100, 40);
			btnNewGame.setLocation(600, 260);
			btnNewGame.setVisible(true);
			this.add(btnNewGame);
			// 下一局按钮
			btnNext.setSize(80, 30);
			btnNext.setLocation(600, 310);
			
			this.add(btnNext);

			// 看牌按钮
			btnCheck.setSize(80, 30);
			btnCheck.setLocation(600, 460);
			
			this.add(btnCheck);

			// 跟牌按钮
			btnCall.setSize(80, 30);
			btnCall.setLocation(600, 510);
			
			this.add(btnCall);

			// 加注按钮
			btnRaise.setSize(80, 30);
			btnRaise.setLocation(690, 510);
			
			this.add(btnRaise);

			// 弃牌按钮
			btnQuit.setSize(80, 30);
			btnQuit.setLocation(690, 460);
			
			this.add(btnQuit);
			
			

		}

		public void btn(int i, int x, int y) {
			if (btnFight[i] == null) {
				return;
			}
			
			btnFight[i].setLocation(x, y);
			btnFight[i].setSize(60, 25);

			this.add(btnFight[i]);
			

		}

		public void paintCard(Graphics g) {
			Player[] players = player;
			if (players == null) {
				return;
			}
			for (int i = 0; i < players.length; i++) {
				int j = 190;
				j = j * (i / 5);
				Cards cards = players[i].cards;
				g.setColor(new Color(0xA8A8D9));
				if (!player[i].left) {
					g.drawString(players[i].name, 155 + j, 105 * (i % 5) + 35);
					g.drawString("￥:" + players[i].money, 148 + j,
							105 * (i % 5) + 55);
				} else {

					g.drawString("已破产", 148 + j, 105 * (i % 5) + 55);
				}
				if (btnFight != null) {
					btn(i, 152 + j, 105 * (i % 5) + 86);
				}

				if (cards != null && players[i].isHum && !players[i].quit) {// 画玩家的牌
					if (players[i].check) {
						g.drawImage(cards.c1.img, 0 + (j), 105 * (i % 5), this);
						g.drawImage(cards.c2.img, 25 + (j), 105 * (i % 5), this);
						g.drawImage(cards.c3.img, 50 + (j), 105 * (i % 5), this);
					} else {
						g.drawImage(backcard, 0 + (j), 105 * (i % 5), this);
						g.drawImage(backcard, 25 + (j), 105 * (i % 5), this);
						g.drawImage(backcard, 50 + (j), 105 * (i % 5), this);
					}
				}
				if (cards != null && !players[i].isHum && !players[i].quit) {// 画Npc的牌
					if (players[i].check) {
						g.drawImage(npcbackcard, 0 + (j), 105 * (i % 5), this);
						g.drawImage(npcbackcard, 25 + (j), 105 * (i % 5), this);
						g.drawImage(npcbackcard, 50 + (j), 105 * (i % 5), this);
					} else {
						g.drawImage(backcard, 0 + (j), 105 * (i % 5), this);
						g.drawImage(backcard, 25 + (j), 105 * (i % 5), this);
						g.drawImage(backcard, 50 + (j), 105 * (i % 5), this);
					}
				}
				if (paintallcards && !player[i].left) {
					g.drawImage(cards.c1.img, 0 + (j), 105 * (i % 5), this);
					g.drawImage(cards.c2.img, 25 + (j), 105 * (i % 5), this);
					g.drawImage(cards.c3.img, 50 + (j), 105 * (i % 5), this);
				}

			}

		}
	}

	
	public static int fightInt=0;
	private void init() {// 设计界面
		Container c = getContentPane();// 获取容器
		c.setLayout(null);// 设置无布局
//		//输入玩家框
//		text.setSize(30,20);
//		btnNewGame.setLocation(600, 290);
//		btnNewGame.setVisible(true);
//		this.add(text);
//		btnNewGame.setVisible(true);
//		// 下一局按钮
//		btnNext.setSize(80, 30);
//		btnNext.setLocation(800, 260);
		btnNext.setVisible(false);
//		c.add(btnNext);
//
//		// 看牌按钮
//		btnCheck.setSize(80, 30);
//		btnCheck.setLocation(600, 460);
//		btnCheck.setVisible(false);
//		c.add(btnCheck);
//
//		// 跟牌按钮
//		btnCall.setSize(80, 30);
//		btnCall.setLocation(800, 510);
//		btnCall.setVisible(false);
//		c.add(btnCall);
//
//		// 加注按钮
//		btnRaise.setSize(80, 30);
//		btnRaise.setLocation(890, 510);
//		btnRaise.setVisible(false);
//		c.add(btnRaise);
//
//		// 弃牌按钮
//		btnQuit.setSize(80, 30);
//		btnQuit.setLocation(890, 460);
//		btnQuit.setVisible(false);
//		c.add(btnQuit);
		btnNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				swapall();
				winnermsg = "";
				closeBtn();
				action();				
			}
		});
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				winnermsg = "";
				btnNext.setVisible(false);
				actionByTurn();
			}
		});

		btnCall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player[0].doCall();
				movingpalyer = 1;
				repaint();
				closeBtn();
				continueAction();
			}
		});
		btnCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player[0].doCheck();
				btnCheck.setVisible(false);

				repaint();

			}
		});
		btnRaise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player[0].doRaise(10);// 暂时设置为10以后设置
				movingpalyer = 1;
				repaint();
				closeBtn();
				continueAction();
				repaint();

			}
		});
		btnQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player[0].doQuit();
				repaint();
				movingpalyer = 1;
				closeBtn();
				continueAction();

			}
		});
		
		
		for (int i = 0; i < btnFight.length; i++) {
			btnFight[i].addActionListener(new BtnActions(i));
			
		}
		
		
		
		
	}

	class  BtnActions  implements ActionListener{
		//重写了一个事件监听改为带参数的
		public int x;
		public BtnActions(int x) {
			super();
			this.x = x;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			fight(this.x);
			
		}
		
	}
	public static boolean over = false;
	public static boolean fristtrun = true;
	public void action() {
		int num=0;
		try {
			num=Integer.parseInt(text.getText());
		} catch (Exception e) {
			
		}
		if(num>1&&num<18){
			playerNum=num;
		}else{
			playerNum = 8;
		}
		Player.playerOfNumToZero();
		player = new Player[playerNum];
		player[0] = new PlayerHum();
		for (int i = 1; i < player.length; i++) {
			player[i] = new PlayerAI();// 决定人数
		}
		
		//int turns = 1;//统计第几回合用 暂时不用

		actionByTurn();

	}
	private void swapall(){
		over = false;
		fristtrun = true;
		leftTimes = 0;
		leftTimesStr = "";
		winnermsg = "";
		testCode = "";
		paintallcards = false;
		movingpalyer=0;
		if(timer!=null){
			timer.cancel();			
		}
		
	}

	private void continueAction() {
	
		timer=new Timer();

		
	
		timer.schedule(new TimerTask() {

			@Override
			public  void run() {
				
				nextPlayer(movingpalyer);
				repaint();
						}
		}, 700, 700);
	}
	
	

	public static int movingpalyer = 0;

	public void actionByTurn() {// 每一场游戏的开始
		partSwap();// 初始化
		partdoBet();// 打底
		
		if(!fristtrun){
			continueAction();	
		}else{
			fristtrun=false;
			showBtn();
		}
		
		
		

	}

	public void partSwap() {// 洗牌发牌
		Player.clear(player);// 复位
		paintallcards = false;
		for (int i = 0; i < Card.sendted.length; i++) {
			Card.sendted[i] = false;// 重新洗牌
		}
		for (int i = 0; i < player.length; i++) {

			player[i].setCards();// 发牌

		}
		repaint();
	}

	public void partdoBet() {
		for (int i = 0; i < player.length; i++) {// 打底
			player[i].doBet(bet);
		}
	}

	public void showBtn() {
		if (isOver()) {
			winner();
			return;
		}
		if (timer != null) {
			timer.cancel();
		}
		if(!player[0].quit){
			btnCheck.setVisible(!player[0].check);// 看过牌就不显示,没有看就显示
			btnQuit.setVisible(!player[0].quit);
			btnCall.setVisible(true);
			btnRaise.setVisible(true);
			// btnFight.setVisible(true);
		}
		for (int i = 0; i < playerNum; i++) {
			if (!player[i].isHum && player[i].check && !player[i].quit) {
				btnFight[i].setVisible(true);
				;
			}
		}

		if (player[0].quit) {
			movingpalyer = 1;
			continueAction();
		}

	}

	public void closeBtn() {
		btnCheck.setVisible(false);
		btnCall.setVisible(false);
		btnQuit.setVisible(false);
		btnRaise.setVisible(false);
		btnNext.setVisible(false);

		for (int i = 0; i < playerNum; i++) {

			btnFight[i].setVisible(false);
			;

		}
	}
	public void fight(int i){
		player[0].doFight(player[i]);
		repaint();
		movingpalyer = 1;
		closeBtn();
		continueAction();
	}

	public   void nextPlayer(int i) {
		testCode = i + "";
		repaint();
		if (isOver()) {
			winner();
			return;
		}

		if (i >= playerNum) {
			showBtn();

			return;
		}
		repaint();
		if (i < playerNum && player[i].quit == false) {

			player[i].play(player);
			repaint();
		}

		do {
			movingpalyer++;
		} while (player[movingpalyer % playerNum].quit);// 如果下家弃牌就在加1

		if (movingpalyer >= playerNum) {
			movingpalyer = movingpalyer % playerNum;
		}
		if (movingpalyer == 0) {
			showBtn();
		}

	}

	public boolean isOver() {
		int v = 0;
		for (int i = 0; i < player.length; i++) {
			if (!player[i].quit) {
				v++;
			}
		}
		if (v == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void winner() {
		if (isOver()) {
			closeBtn();
			btnNext.setVisible(true);
			timer.cancel();
			paintallcards = true;
			for (int i = 0; i < player.length; i++) {

				if (!player[i].quit) {
					player[i].winner();
					player[i].addMoney(bet * player.length);
					winnermsg = player[i].name + "胜利!";
					movingpalyer=i+1;
				}
			}

		}
	}

}

