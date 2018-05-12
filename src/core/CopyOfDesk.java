package core;

import java.awt.Color;
import java.awt.Container;
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

public class CopyOfDesk extends JFrame {
	public static long bet = 1;
	private JButton btnCheck = new JButton("看牌");
	private JButton btnQuit = new JButton("弃牌");

	public static void main(String[] args) {
		CopyOfDesk desk = new CopyOfDesk();
		desk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private static Timer timer;
	JPanel deskPanel = new DeskPanel();

	public CopyOfDesk() {
		JFrame desk = new JFrame();
		Container c = getContentPane();

		deskPanel.setSize(800, 600);
		deskPanel.setLocation(0, 0);
		// jp.setOpaque(false);//设置为透明

		c.add(deskPanel);
		init();
		setSize(1050, 600);
		setLocationRelativeTo(null);// 设置窗口为屏幕正中
		setTitle("ICe扎金花 0.1.1");
		setVisible(true);
		action();

	}

	public void repaint() {
		deskPanel.repaint();
	}

	private static Image background;// 背景图片
	static {
		try {
			background = ImageIO.read(CopyOfDesk.class.getResource("png/1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Image backcard;// 背面牌的图案
	static {
		try {
			backcard = ImageIO.read(CopyOfDesk.class.getResource("png/Back.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Image npcbackcard;// 背面牌的图案
	static {
		try {
			npcbackcard = ImageIO.read(CopyOfDesk.class
					.getResource("png/npccard.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class DeskPanel extends JPanel {// 内部类负责画图

		public void paint(Graphics g) {
			// g.drawImage(background, 0, 0, this);

			g.setColor(new Color(0x357515));

			g.fillRect(0, 0, 800, 600);
			paintCard(g);

		}

		public void paintCard(Graphics g) {
			Player[] players = player;
			if (players == null) {
				return;
			}
			for (int i = 0; i < players.length; i++) {
				int j = 180;
				j = j * (i / 5);
				Cards cards = players[i].cards;
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

			}

		}
	}

	private void init() {// 设计界面
		Container c = getContentPane();// 获取容器
		c.setLayout(null);// 设置无布局

		// 看牌按钮
		btnCheck.setSize(80, 30);
		btnCheck.setLocation(800, 460);
		btnCheck.setVisible(true);
		c.add(btnCheck);

		// 弃牌按钮
		btnQuit.setSize(80, 30);
		btnQuit.setLocation(890, 460);
		btnQuit.setVisible(true);
		c.add(btnQuit);

		btnCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player[0].doCheck();
				
				repaint();

			}
		});
		btnQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player[0].doQuit();
				repaint();

			}
		});

	}

	public static Player[] player;
	public static int playerNum;
	public void action() {

		playerNum = 17;

		player = new Player[playerNum];
		player[0] = new PlayerHum();
		for (int i = 1; i < player.length; i++) {
			player[i] = new PlayerAI();// 决定人数

		}
		int turns = 1;
		do {
			repaint();
			System.out.println("-------------------------------------第" + turns
					+ "局:");

			oneturnup(player, playerNum);
			repaint();
			turns++;
		} while (true);
	}

	public void oneturnup(Player[] player, int playerNum) {
		partSwap(player, playerNum);
		partdoBet(player);
		humPlay();
		aiplay();
		winner();
		
	}
	public void aiplay(){
		timer = new Timer();//人物操作完成后进行ai操作
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				int i=1;
				partDoplay(1);//数字表示从第几人开始
				i++;
				repaint();
			}
		}, 800, 800);
		humPlay();
	}
	public void humPlay(){
		
		
		
		
	}

	public void partSwap(Player[] player, int playerNum) {
		Player.clear(player);// 复位
		for (int i = 0; i < Card.sendted.length; i++) {
			Card.sendted[i] = false;// 重新洗牌
		}
		for (int i = 0; i < player.length; i++) {

			player[i].setCards();// 发牌
			repaint();
			
		}
	}

	public void partdoBet(Player[] player) {
		for (int i = 0; i < player.length; i++) {// 打底
			player[i].doBet(bet);
		}
	}

	public void partDoplay(int i) {
		if (isOver()) {
			if (timer!=null) {
				timer.cancel();
			}
			return;
		}else{
			if (player[0].isHum)// 如果是玩家的情况
			{
				
			}else{
				partNext(player, i % playerNum);
			}
			
			repaint();
		}	

	}

	public void partNext(Player[] player, int i) {
		if (player[i].quit == false) {
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			player[i].seecard(player);
			repaint();
			player[i].play(player);
			repaint();
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
			// for (int i = 0; i < player.length; i++) {
			//
			// if (!player[i].quit) {
			// player[i].winner();
			// player[i].addMoney(bet * player.length);
			//
			// }
			// }
			for (int i = 0; i < player.length; i++) {

				if (!player[i].quit) {
					System.out.println(player[i].name
							+ "获得了胜利!赢了"
							+ (long) (player[i].winner() + player[i]
									.addMoney(bet * player.length)) + "元钱.\n");

				}
			}
			for (int i = 0; i < player.length; i++) {

				System.out.println(player[i].name + player[i].cards.getName()
						+ "," + player[i].cards.getPowerName() + ",剩余钱:"
						+ player[i].money);

			}

		}
	}

	public void oneturn(Player[] player, int playerNum) {
		timer = new Timer();
		for (int i = 0; i < Card.sendted.length; i++) {
			Card.sendted[i] = false;// 重新洗牌
		}
		for (int i = 0; i < player.length; i++) {

			player[i].setCards();// 发牌
			repaint();
			// System.out.println(player[i].cards.getName()+player[i].name+","+player[i].cards.getPowerName());
			// 用于测试发牌统计
		}
		for (int i = 0; i < player.length; i++) {// 打底
			player[i].doBet(bet);
		}
		int q = 0;

		do {

			repaint();
			q = playerNum;
			for (int i = 0; i < player.length; i++) {
				repaint();

				if (player[i].quit) {
					q--;
				} else {

					player[i].seecard(player);
					repaint();
					player[i].play(player);

					if (player[i].quit) {
						q--;
					}

				}
				repaint();
			}

		} while (q != 1);
		repaint();
		for (int i = 0; i < player.length; i++) {

			if (!player[i].quit) {
				System.out.println(player[i].name
						+ "获得了胜利!赢了"
						+ (long) (player[i].winner() + player[i].addMoney(bet
								* player.length)) + "元钱.\n");

			}
		}
		for (int i = 0; i < player.length; i++) {

			System.out.println(player[i].name + player[i].cards.getName() + ","
					+ player[i].cards.getPowerName() + ",剩余钱:"
					+ player[i].money);

		}

	}
}
