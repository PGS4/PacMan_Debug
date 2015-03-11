package cz.PacMan.debug.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class View extends JPanel implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4455404367797619013L;
	private Timer main;
	private Hashtable<List<Integer>, String> map = Model.getMap();
	private int mouseBtn = 0;
	private Enumeration<?> objectsInMap;
	private int clicked = 0;
	private int mouseX = 0, mouseY = 0;
	private List<Integer> selectedPos = Arrays.asList(0, 0);
	private boolean klik = false;
	private boolean counter = true;
	private int direction = -1;
	private Color background = new Color(0.7f, 0.7f, 0.7f);
	private List<String> p1 = new ArrayList<String>();
	private List<Integer> sidesInfo = new ArrayList<Integer>();
	private List<List<Integer>> leva = new ArrayList<List<Integer>>(),
			predek = new ArrayList<List<Integer>>(),
			prava = new ArrayList<List<Integer>>();

	public View() {
		main = new Timer(25, this);
		main.start();
		addMouseListener(this);
		setBackground(background);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		objectsInMap = map.keys();
		while (objectsInMap.hasMoreElements()) {
			@SuppressWarnings("unchecked")
			List<Integer> souradnice = (List<Integer>) objectsInMap
					.nextElement();
			int x = souradnice.get(0) * 32;
			int y = souradnice.get(1) * 32;
			ImageIcon ii;
			Image itemImage = null;
			if (map.get(souradnice).equals("#")) {
				ii = new ImageIcon(this.getClass().getResource("/zed.png"));
				itemImage = ii.getImage();
			}
			g2d.drawImage(itemImage, x, y, null);
			if (map.get(souradnice).equals(".")) {
				g2d.drawString("X", x + 16, y + 16);
			}

		}

		if (klik) {
			if (clicked == 1) {
				mouseX = getCursorBlockX();
				mouseY = getCursorBlockY();
				selectedPos = Arrays.asList(mouseX, mouseY);
			}
			if (clicked == 2) {
				if (selectedPos.get(1) > getCursorBlockY()) {
					direction = 0;
				}
				if (selectedPos.get(0) > getCursorBlockX()) {
					direction = 3;
				}
				if (selectedPos.get(0) < getCursorBlockX()) {
					direction = 1;
				}
				if (selectedPos.get(1) < getCursorBlockY()) {
					direction = 2;
				}
				p1 = getPathPriorities().get(1);
				sidesInfo = chooseBestSide(p1);
				clicked = 0;
			}
			klik = false;
		}
		if (clicked == 0) {
			Font info = new Font("Times New Roman", Font.BOLD, 20);
			g2d.setColor(Color.WHITE);
			g2d.setFont(info);
			g2d.drawString("klikni pro pozici robota", 16, 16);
			g2d.drawString("Natoèení: " + direction, 16, 32);
		}
		if (clicked == 1) {
			Font info = new Font("Times New Roman", Font.BOLD, 20);
			g2d.setColor(Color.WHITE);
			g2d.setFont(info);
			g2d.drawString("klikni pro natoèení robota", 32, 32);
		}
		g2d.setColor(Color.RED);
		g2d.drawRect(mouseX * 32, mouseY * 32, 32, 32);
		if (direction == 0) {
			for (int i = 0; i < p1.size(); i++) {
				if (p1.get(i).equals("LEFT")) {
					g2d.setColor(Color.blue);
					g2d.drawRect((mouseX - 1) * 32, (mouseY) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(0), (mouseX - 1) * 32,
							(mouseY) * 32);
					for (int j = 0; j < leva.size(); j++) {
						int x = leva.get(j).get(0);
						int y = leva.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("FRONT")) {
					g2d.setColor(Color.GREEN);
					g2d.drawRect((mouseX) * 32, (mouseY - 1) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(1), (mouseX) * 32,
							(mouseY - 1) * 32);
					for (int j = 0; j < predek.size(); j++) {
						int x = predek.get(j).get(0);
						int y = predek.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("RIGHT")) {
					g2d.setColor(Color.YELLOW);
					g2d.drawRect((mouseX + 1) * 32, (mouseY) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(2), (mouseX + 1) * 32,
							(mouseY) * 32);
					for (int j = 0; j < prava.size(); j++) {
						int x = prava.get(j).get(0);
						int y = prava.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
			}
		}
		if (direction == 1) {
			for (int i = 0; i < p1.size(); i++) {
				if (p1.get(i).equals("LEFT")) {
					g2d.setColor(Color.blue);
					g2d.drawRect((mouseX) * 32, (mouseY - 1) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(0), (mouseX) * 32,
							(mouseY - 1) * 32);
					for (int j = 0; j < leva.size(); j++) {
						int x = leva.get(j).get(0);
						int y = leva.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("FRONT")) {
					g2d.setColor(Color.GREEN);
					g2d.drawRect((mouseX + 1) * 32, (mouseY) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(1), (mouseX + 1) * 32,
							(mouseY) * 32);
					for (int j = 0; j < predek.size(); j++) {
						int x = predek.get(j).get(0);
						int y = predek.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("RIGHT")) {
					g2d.setColor(Color.YELLOW);
					g2d.drawRect((mouseX) * 32, (mouseY + 1) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(2), (mouseX) * 32,
							(mouseY + 1) * 32);
					for (int j = 0; j < prava.size(); j++) {
						int x = prava.get(j).get(0);
						int y = prava.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
			}
		}
		if (direction == 2) {
			for (int i = 0; i < p1.size(); i++) {
				if (p1.get(i).equals("LEFT")) {
					g2d.setColor(Color.blue);
					g2d.drawRect((mouseX + 1) * 32, (mouseY) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(0), (mouseX + 1) * 32,
							(mouseY) * 32);
					for (int j = 0; j < leva.size(); j++) {
						int x = leva.get(j).get(0);
						int y = leva.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("FRONT")) {
					g2d.setColor(Color.GREEN);
					g2d.drawRect((mouseX) * 32, (mouseY + 1) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(1), (mouseX) * 32,
							(mouseY + 1) * 32);
					for (int j = 0; j < predek.size(); j++) {
						int x = predek.get(j).get(0);
						int y = predek.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("RIGHT")) {
					g2d.setColor(Color.YELLOW);
					g2d.drawRect((mouseX - 1) * 32, (mouseY) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(2), (mouseX - 1) * 32,
							(mouseY) * 32);
					for (int j = 0; j < prava.size(); j++) {
						int x = prava.get(j).get(0);
						int y = prava.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
			}
		}
		if (direction == 3) {
			for (int i = 0; i < p1.size(); i++) {
				if (p1.get(i).equals("LEFT")) {
					g2d.setColor(Color.blue);
					g2d.drawRect((mouseX) * 32, (mouseY + 1) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(0), (mouseX) * 32,
							(mouseY + 1) * 32);
					for (int j = 0; j < leva.size(); j++) {
						int x = leva.get(j).get(0);
						int y = leva.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("FRONT")) {
					g2d.setColor(Color.GREEN);
					g2d.drawRect((mouseX - 1) * 32, (mouseY) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(1), (mouseX - 1) * 32,
							(mouseY) * 32);
					for (int j = 0; j < predek.size(); j++) {
						int x = predek.get(j).get(0);
						int y = predek.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
				if (p1.get(i).equals("RIGHT")) {
					g2d.setColor(Color.YELLOW);
					g2d.drawRect((mouseX) * 32, (mouseY - 1) * 32, 32, 32);
					g2d.drawString("" + sidesInfo.get(2), (mouseX) * 32,
							(mouseY - 1) * 32);
					for (int j = 0; j < prava.size(); j++) {
						int x = prava.get(j).get(0);
						int y = prava.get(j).get(1);
						g2d.drawRect(x * 32, y * 32, 32, 32);
					}
				}
			}
		}
	}

	private List<Integer> chooseBestSide(List<String> options) {
		String side = "BACK";
		leva.clear();
		prava.clear();
		predek.clear();
		int left, right, front;
		double vleft, vfront, vright;
		left = 0;
		right = 0;
		front = 0;
		vleft = 0;
		vfront = 0;
		vright = 0;
		List<Double> vlefts, vfronts, vrights;
		vlefts = new ArrayList<Double>();
		vrights = new ArrayList<Double>();
		vfronts = new ArrayList<Double>();
		options.add("BACK");
		for (int y = 2; y < 8; y++) {
			for (int x = 2; x < 11; x++) {
				if (getMValue(x, y).equals(" ")) {
					String pSide = "YEP";
					String cSide = "BACK";
					double vzdalenost = 10000;
					for (int i = 0; i < options.size(); i++) {
						int x0 = selectedPos.get(0);
						int y0 = selectedPos.get(1);
						pSide = options.get(i);
						if (direction == 0) {
							if (pSide.equals("LEFT")) {
								x0 -= 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "LEFT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("BACK")) {
								y0 += 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "BACK";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("FRONT")) {
								y0 -= 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "FRONT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("RIGHT")) {
								x0 += 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "RIGHT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
						} else if (direction == 1) {
							if (pSide.equals("BACK")) {
								x0 -= 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "BACK";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("LEFT")) {
								y0 -= 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "LEFT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("FRONT")) {
								x0 += 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "FRONT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("RIGHT")) {
								y0 += 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "RIGHT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
						} else if (direction == 2) {
							if (pSide.equals("BACK")) {
								y0 -= 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "BACK";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("LEFT")) {
								x0 += 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "LEFT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("FRONT")) {
								y0 += 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "FRONT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("RIGHT")) {
								x0 -= 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "RIGHT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
						} else if (direction == 3) {
							if (pSide.equals("BACK")) {
								x0 += 1;
								if (vzdalenost > Math
										.sqrt((Math.pow(x - x0, 2))
												+ (Math.pow(y - y0, 2)))) {
									vzdalenost = Math
											.sqrt((Math.pow(x - x0, 2))
													+ (Math.pow(y - y0, 2)));
									cSide = "BACK";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("LEFT")) {
								y0 += 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "LEFT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("FRONT")) {
								x0 -= 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "FRONT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
							if (pSide.equals("RIGHT")) {
								y0 -= 1;
								if (vzdalenost > Math.sqrt(Math.pow(x - x0, 2)
										+ Math.pow(y - y0, 2))) {
									vzdalenost = Math.sqrt(Math.pow(x - x0, 2)
											+ Math.pow(y - y0, 2));
									cSide = "RIGHT";
								} else if (vzdalenost == Math.sqrt((Math.pow(x
										- x0, 2))
										+ (Math.pow(y - y0, 2)))) {
									cSide = "BACK";
								}
							}
						}
					}
					if (cSide.equals("LEFT")) {
						left += 1;
						leva.add(Arrays.asList(x, y));
						vlefts.add(vzdalenost);
					}
					if (cSide.equals("FRONT")) {
						front += 1;
						predek.add(Arrays.asList(x, y));
						vfronts.add(vzdalenost);
					}
					if (cSide.equals("RIGHT")) {
						right += 1;
						prava.add(Arrays.asList(x, y));
						vrights.add(vzdalenost);
					}
				}
			}
		}
		if (vlefts.size() != 0) {
			for (int i = 0; i < vlefts.size(); i++) {
				vleft += vlefts.get(i);
			}
			vleft = vleft / vlefts.size();
		} else {
			vleft = 1000;
		}
		if (vfronts.size() != 0) {
			for (int i = 0; i < vfronts.size(); i++) {
				vfront += vfronts.get(i);
			}
			vfront = vfront / vfronts.size();
		} else {
			vfront = 1000;
		}
		if (vrights.size() != 0) {
			for (int i = 0; i < vrights.size(); i++) {
				vright += vrights.get(i);
			}
			vright = vright / vrights.size();
		} else {
			vright = 1000;
		}
		if (left == 0) {
			left = -1000;
		}
		if (right == 0) {
			right = -1000;
		}
		if (front == 0) {
			front = -1000;
		}
		System.out.println("vleft: " + vleft + " vfront: " + vfront
				+ " vright: " + vright);
		if (front == -1000 && left == -1000 && right == -1000) {
			if (options.size() > 0) {
				Random rand = new Random();
				int count = 0;
				do {
					side = options.get(rand.nextInt(options.size()));
					count += 1;
				} while (side.equals("BACK") && count < 20);
				if (count == 20) {
					side = "BACK";
				}
			} else {
				side = "BACK";
			}
		} else {
			if (((left < 17) || left == -1000)
					&& ((front < 17) || front == -1000)
					&& ((right < 17) || right == -1000)) {
				if (vleft < 1.7 && left > 3) {
					if (vfront < 1.7 && front > 3) {
						if (vright < 1.7 && right > 3) {
							if (left > front) {
								if (left > right) {
									side = "LEFT";
								} else {
									side = "RIGHT";
								}
							} else if (front > right) {
								side = "FRONT";
							} else {
								side = "RIGHT";
							}
						} else if (left > front) {
							side = "LEFT";
						} else {
							side = "FRONT";
						}
					} else if (vright < 1.7 && right > 3) {
						if (left > right) {
							side = "LEFT";
						} else {
							side = "RIGHT";
						}
					}else{
						side="LEFT";
					}
				} else {
					if (vfront < 1.7 && front > 3) {
						if(vright<1.7 && right > 3){
							if (front > right) {
								side = "FRONT";
							} else {
								side = "RIGHT";
							}
						}else{
							side="FRONT";
						}
					} else {
						if (vright < 1.7 && right > 3) {
							side = "RIGHT";
						} else {
							side = regularChoose(left, front, right, vleft,
									vfront, vright);
						}
					}
				}
			} else {
				side = regularChoose(left, front, right, vleft, vfront, vright);
			}
		}
		System.out.println(side);
		List<Integer> sides = new ArrayList<Integer>();
		sides.add(left);
		sides.add(front);
		sides.add(right);
		return sides;
	}

	private String regularChoose(int left, int front, int right, double vleft,
			double vfront, double vright) {
		String side = "BACK";
		if (left > front + 6) {
			if (left > right + 6) {
				side = "LEFT";
			} else {
				if (right > left + 6) {
					side = "RIGHT";
				} else {
					if (vright > vleft) {
						side = "LEFT";
					} else {
						side = "RIGHT";
					}
				}
			}
		} else {
			if (front > right + 6) {
				if (front > left + 6) {
					side = "FRONT";
				} else {
					if (vfront > vleft) {
						side = "LEFT";
					} else {
						side = "FRONT";
					}
				}
			} else {
				if (right > front + 6) {
					if (right > left + 6) {
						side = "RIGHT";
					} else {
						if (left > right + 6) {
							side = "LEFT";
						} else {
							if (vleft > vright) {
								side = "RIGHT";
							} else {
								side = "LEFT";
							}
						}
					}
				} else {
					if (front > right) {
						if (front > left) {
							if (front > left + 6) {
								if (vright > vfront) {
									side = "FRONT";
								} else {
									side = "RIGHT";
								}
							} else {
								if (vright < vleft) {
									if (vright < vfront) {
										side = "RIGHT";
									} else {
										side = "FRONT";
									}
								} else {
									if (vleft < vfront) {
										side = "LEFT";
									} else {
										side = "FRONT";
									}
								}
							}
						} else {
							if (left > right + 6) {
								if (vleft > vfront) {
									side = "FRONT";
								} else {
									side = "LEFT";
								}
							} else {
								if (vright < vleft) {
									if (vright < vfront) {
										side = "RIGHT";
									} else {
										side = "FRONT";
									}
								} else {
									if (vleft < vfront) {
										side = "LEFT";
									} else {
										side = "FRONT";
									}
								}
							}
						}
					} else {
						if (right > left) {
							if (right > left + 6) {
								if (vfront < vright) {
									side = "FRONT";
								} else {
									side = "RIGHT";
								}
							}
						} else {
							if (vright < vleft) {
								if (vright < vfront) {
									side = "RIGHT";
								} else {
									side = "FRONT";
								}
							} else {
								if (vleft < vfront) {
									side = "LEFT";
								} else {
									side = "FRONT";
								}
							}
						}
					}
				}
			}
		}
		return side;
	}

	/**
	 * Vrátí hodnotu bodu na hrací ploše
	 * 
	 * @param x
	 *            souøadnice x bodu
	 * @param y
	 *            souøadnice y bodu
	 * @return Hodnota bodu [x,y]
	 */

	private String getMValue(int x, int y) {
		return Model.getMap().get(Arrays.asList(x, y));
	}

	private ArrayList<String> checkValue(int direction, List<Integer> position) {
		ArrayList<String> key = new ArrayList<String>();
		int x = position.get(0);
		int y = position.get(1);
		if (direction == 0) {
			key.add(getMValue(x - 1, y));
			key.add(getMValue(x, y - 1));
			key.add(getMValue(x + 1, y));
			key.add(getMValue(x - 2, y));
			key.add(getMValue(x, y - 2));
			key.add(getMValue(x + 2, y));
			key.add(getMValue(x, y + 1));
		} else if (direction == 1) {
			key.add(getMValue(x, y - 1));
			key.add(getMValue(x + 1, y));
			key.add(getMValue(x, y + 1));
			key.add(getMValue(x, y - 2));
			key.add(getMValue(x + 2, y));
			key.add(getMValue(x, y + 2));
			key.add(getMValue(x - 1, y));
		} else if (direction == 2) {
			key.add(getMValue(x + 1, y));
			key.add(getMValue(x, y + 1));
			key.add(getMValue(x - 1, y));
			key.add(getMValue(x + 2, y));
			key.add(getMValue(x, y + 2));
			key.add(getMValue(x - 2, y));
			key.add(getMValue(x, y - 1));
		} else if (direction == 3) {
			key.add(getMValue(x, y + 1));
			key.add(getMValue(x - 1, y));
			key.add(getMValue(x, y - 1));
			key.add(getMValue(x, y + 2));
			key.add(getMValue(x - 2, y));
			key.add(getMValue(x, y - 2));
			key.add(getMValue(x + 1, y));
		}
		return key;
	}

	private Hashtable<Integer, List<String>> getPathPriorities() {
		if (direction != -1) {
			Hashtable<Integer, List<String>> priorities = new Hashtable<Integer, List<String>>();
			List<String> p1, p2, p3;
			p1 = new ArrayList<String>();
			p2 = new ArrayList<String>();
			p3 = new ArrayList<String>();
			ArrayList<String> points = new ArrayList<String>();
			points = checkValue(direction, selectedPos);
			if (points.get(0).equals(" ")) {
				if (points.get(3).equals(" ")) {
					p1.add("LEFT");
				} else {
					if (points.get(3).equals(".")) {
						p1.add("LEFT");
					} else {
						p1.add("LEFT");
					}
				}
			} else if (points.get(0).equals(".")) {
				p1.add("LEFT");
			}
			if (points.get(1).equals(" ")) {
				if (points.get(4).equals(" ")) {
					p1.add("FRONT");
				} else {
					if (points.get(4).equals(".")) {
						p1.add("FRONT");
					} else {
						p1.add("FRONT");
					}
				}
			} else if (points.get(1).equals(".")) {
				p1.add("FRONT");
			}
			if (points.get(2).equals(" ")) {
				if (points.get(5).equals(" ")) {
					p1.add("RIGHT");
				} else {
					if (points.get(5).equals(".")) {
						p1.add("RIGHT");
					} else {
						p1.add("RIGHT");
					}
				}
			} else if (points.get(2).equals(".")) {
				p1.add("RIGHT");
			}
			priorities.put(1, p1);
			priorities.put(2, p2);
			priorities.put(3, p3);
			return priorities;
		}
		return null;
	}

	public int getCursorX() {
		int x = (int) (MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen()
				.getX());
		return x;
	}

	public int getCursorY() {
		int y = (int) (MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen()
				.getY());
		return y;
	}

	public int getCursorBlockX() {
		int x = getCursorX();
		int cursorBlockX = ((x) - ((x) % 32)) / 32;
		return cursorBlockX;
	}

	public int getCursorBlockY() {
		int y = getCursorY();
		int blockY = ((y) - ((y) % 32)) / 32;
		return blockY;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseBtn = e.getButton();
		map = Model.getMap();
		List<Integer> souradnice = Arrays.asList(getCursorBlockX(),
				getCursorBlockY());
		if (mouseBtn == MouseEvent.BUTTON1) {
			if (counter) {
				clicked += 1;
			}
			counter = false;
			klik = true;
		}
		if (mouseBtn == MouseEvent.BUTTON3) {
			Model.putToMap(souradnice, ".");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		counter = true;
	}

}
