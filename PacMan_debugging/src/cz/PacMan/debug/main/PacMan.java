package cz.PacMan.debug.main;

import javax.swing.JFrame;

public class PacMan extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8329195769179766050L;
	
	public PacMan(){
		add(new View());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize((13*32)+5, 349);
		setTitle("Labyrint");
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model.newMap();
		new PacMan();
	}

}
