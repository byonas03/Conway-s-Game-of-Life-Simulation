import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Window implements MouseListener {
	JFrame f = new JFrame("Conway's Game of Life");
	boolean[][] tiles;
	int size;
	boolean update = false, pressed = false;;

	public Window(int s) {
		f.setVisible(true);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tiles = new boolean[(int) 1920 / s][(int) 1080 / s];
		size = s;

		f.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent arg0) {
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent arg0) {
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent arg0) {
				update = false;
				pressed = true;
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent arg0) {
				update = true;
			}
		});

		Timer dynamics = new Timer();
		dynamics.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(update);
				paint(f.getGraphics());

				boolean[][] tiles_copy = tiles.clone();

				if (update) {
					for (int i = 0; i < tiles.length; i++) {
						for (int j = 0; j < tiles[i].length; j++) {
							if (tiles[i][j]) {
								int alive_neighbors = getAliveNeighbors(i, j);
								if (alive_neighbors < 2) {
									tiles_copy[i][j] = false;
								} else if (alive_neighbors == 2 || alive_neighbors == 3) {
									tiles_copy[i][j] = true;
								} else if (alive_neighbors > 3) {
									tiles_copy[i][j] = false;
								}
							} else {
								if (getAliveNeighbors(i, j) == 3)
									tiles_copy[i][j] = true;
							}
						}
					}
					tiles = tiles_copy;
				}
			}
		}, 0, 50);

		Timer paint = new Timer();
		paint.schedule(new TimerTask() {
			@Override
			public void run() {
				if (pressed && !update) {
					int x = (int) MouseInfo.getPointerInfo().getLocation().getX(),
							y = (int) MouseInfo.getPointerInfo().getLocation().getY();
					tiles[(int) (x / size)][(int) (y / size)] = true;
				}
			}
		}, 0, 500);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("a");
		update = false;
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
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX(), y = arg0.getY();
		tiles[(int) (x / size)][(int) (y / size)] = true;
		System.out.println("b");
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		update = true;
		System.out.println("c");
	}

	public int getAliveNeighbors(int i, int j) {
		int val = 0;
		if (i + 1 < tiles.length && tiles[i + 1][j]) {
			val++;
		}
		if (i - 1 >= 0 && tiles[i - 1][j]) {
			val++;
		}
		if (j + 1 < tiles[0].length && tiles[i][j + 1]) {
			val++;
		}
		if (j - 1 >= 0 && tiles[i][j - 1]) {
			val++;
		}
		if (i + 1 < tiles.length && j + 1 < tiles[i].length && tiles[i + 1][j + 1]) {
			val++;
		}
		if (i - 1 >= 0 && j - 1 >= 0 && tiles[i - 1][j - 1]) {
			val++;
		}
		if (j - 1 >= 0 && i + 1 < tiles.length && tiles[i + 1][j - 1]) {
			val++;
		}
		if (i - 1 >= 0 && j + 1 < tiles[0].length && tiles[i - 1][j + 1]) {
			val++;
		}
		return val;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (tiles[i][j] == true)
					g.setColor(Color.BLACK);
				else
					g.setColor(Color.WHITE);
				g.fillRect(size * i, size * j, size, size);
			}
		}
	}

	public static void main(String args[]) {
		new Window(50);
	}
}
