package core;

public class Cards {// 玩家的牌
	int id;// 对应玩家id
	Card c1;// 第一张牌
	Card c2;// 第二张牌
	Card c3;// 第三张牌
	int power;

	public Cards(int id) {
		this.id = id;
		this.c1 = new Card();
		this.c2 = new Card();
		this.c3 = new Card();
		
		
		swapCard();
		this.power = biger();

	}

	public String getName() {// 把牌组打印出来
		return c1.getName() + " " + c2.getName() + " " + c3.getName();
	}

	public String getPowerName() {// 把牌组的牌型打印出来

		String s = "";
		switch (this.power) {
		case 1:
			s = "单牌";
			break;
		case 2:
			s = "对子";
			break;
		case 3:
			s = "顺子";
			break;
		case 4:
			s = "金花";
			break;
		case 5:
			s = "金链";
			break;
		case 6:
			s = "豹子";
			break;
		default:
			break;
		}
		return s;

	}

	public void swapCard() {
		int i1 = this.c1.id;
		int i2 = this.c2.id;
		int i3 = this.c3.id;
		Card temp;

		int x1 = i1 % 13, x2 = i2 % 13, x3 = i3 % 13;

		if (x1 >= x2 && x1 >= x3) {
			temp = this.c1;
			this.c1 = this.c3;
			this.c3 = temp;
			// System.out.println("1");
			if (x3 >= x2) {
				temp = this.c1;
				this.c1 = this.c2;
				this.c2 = temp;
				// System.out.println("1.1");
			}
		} else if (x1 <= x2 && x1 <= x3) {
			// System.out.println("2");
			if (x2 >= x3) {
				temp = this.c2;
				this.c2 = this.c3;
				this.c3 = temp;
				// System.out.println("2.1");
			}

		} else if (x1 <= x2 && x1 >= x3) {
			temp = this.c1;
			this.c1 = this.c2;
			this.c2 = temp;
			// System.out.println("3");
			if (x2 >= x3) {
				temp = this.c1;
				this.c1 = this.c3;
				this.c3 = temp;
				// System.out.println("3.1");
			}
		} else if (x1 <= x3 && x1 >= x2) {
			temp = this.c1;
			this.c1 = this.c2;
			this.c2 = temp;
			// System.out.println("4");

		}
		
		if (this.c1.id%13 == 0 && this.c2.id%13 != 0) {
			temp = this.c1;
			this.c1 = this.c2;
			this.c2 = this.c3;
			this.c3 = temp;
		} else if (this.c1.id%13 == 0 && this.c2.id%13 == 0) {
			temp = this.c1;
			this.c1 = this.c3;
			this.c3 = temp;
			
		}

	}

	public int biger() {// 判断牌组牌型的权
		int i1 = this.c1.id, i2 = this.c2.id, i3 = this.c3.id;
		int big = 0, clear = 0, max, min, x = i1 % 13, y = i2 % 13, z = i3 % 13;
		if (x == y && y == z) {
			big = 6;// 豹子
		}
		max = i1 > i2 ? (i1 > i3 ? i1 : i3) : (i2 > i3 ? i2 : i3);
		min = i1 < i2 ? (i1 < i3 ? i1 : i3) : (i2 < i3 ? i2 : i3);
		if (max / 13 == min / 13) {
			big = 4;
			clear = 1;// 清水
		}
		max = x > y ? (x > z ? x : z) : (y > z ? y : z);
		min = x < y ? (x < z ? x : z) : (y < z ? y : z);
		if (max - min == 2 || (x + y + z == 23 && x * y * z == 0)) {

			if (clear == 1) {
				big = 5;// 金链子
			} else {
				big = 3;// 顺子
			}
		}
		if ((x == y && max != min) || (x == z && max != min)
				|| (y == z && max != min)) {
			big = 2;// 对子
		}
		if (big == 0)
			big = 1;// 单张

		return big;
	}

	public boolean Isbig(Cards cards) {// 与另外一组牌比较大小,返回true就大
		boolean win = true;
		int i1 = this.c1.id;
		int i2 = this.c2.id;
		int i3 = this.c3.id;
		int j1 = cards.c1.id;
		int j2 = cards.c2.id;
		int j3 = cards.c3.id;
		int x1 = i1 % 13, y1 = i2 % 13, z1 = i3 % 13, x2 = j1 % 13, y2 = j2 % 13, z2 = j3 % 13;
		x1 = (x1 == 0) ? 13 : x1;
		y1 = (y1 == 0) ? 13 : y1;
		z1 = (z1 == 0) ? 13 : z1;
		x2 = (x2 == 0) ? 13 : x2;
		y2 = (y2 == 0) ? 13 : y2;
		z2 = (z2 == 0) ? 13 : z2;
		int max1 = x1 > y1 ? (x1 > z1 ? x1 : z1) : (y1 > z1 ? y1 : z1);
		int min1 = x1 < y1 ? (x1 < z1 ? x1 : z1) : (y1 < z1 ? y1 : z1);
		int mid1 = x1 + y1 + z1 - max1 - min1;
		int max2 = x2 > y2 ? (x2 > z2 ? x2 : z2) : (y2 > z2 ? y2 : z2);
		int min2 = x2 < y2 ? (x2 < z2 ? x2 : z2) : (y2 < z2 ? y2 : z2);
		int mid2 = x2 + y2 + z2 - max2 - min2;
		if (this.power > cards.power) {
			win = true;
		} else if (this.power < cards.power) {
			win = false;
		} else {// 都是同样的牌型
			if (this.power == 6) {// 豹子的情况
				if (max1 < max2) {// 牌大就赢
					win = false;
				} else {
					win = true;
				}
			} else if (this.power == 3 || this.power == 5) {// 金链或顺子的情况
				if (max1 + mid1 == 23 && max1 * min1 == 0) {// 单左边QKA的时候
					mid1 = max1;

				} else if (max2 + mid2 == 23 && max2 * min2 == 0) {// 右边QKA
					mid2 = max2;
				} else if (mid1 == mid2) {// 相等的时候
					win = false;
				} else if (mid1 < min2) {// 牌大就赢
					win = false;
				} else {
					win = true;
				}
			} else if (this.power == 4 || this.power == 1) {// 清水和单张的情况
				if (max1 == max2 && min1 == min2 && mid1 == mid2) {// 相等的时候
					win = false;
				} else if (max1 == max2 && mid1 == mid2 && min1 < mid2) {// 2支相等
					win = false;
				} else if (max1 == max2 && mid1 < mid2) {// 1支相等
					win = false;
				} else if (max1 < max2) {// 大就赢
					win = false;
				} else {
					win = true;
				}

			} else if (this.power == 2) {// 对子的时候
				int dd1 = (max1 == mid1) ? max1 : min1;
				int ddd1 = (max1 == mid1) ? min1 : (max1 == min1) ? mid1 : max1;
				int dd2 = (max2 == mid2) ? max2 : min2;
				int ddd2 = (max2 == mid2) ? min2 : (max2 == min2) ? mid2 : max2;
				if (dd1 == dd2 & ddd1 == ddd2) {
					win = false;
				} else if (dd1 == dd2 & ddd1 < ddd2) {
					win = false;
				} else if (dd1 < dd2) {
					win = false;
				} else {
					win = true;
				}

			}

		}
		return win;
	}
}
