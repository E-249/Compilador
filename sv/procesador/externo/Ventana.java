package procesador.externo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana<N extends Number> {
	private JFrame window = null;
	private JPanel panel;
	private BufferedImage imagen;
	private int color = 0xFFFFFF;
	
	public void createWindow(int width, int height) {
		window = new JFrame();
		imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(imagen, 0, 0, null);
			}
		};
		
		panel.setPreferredSize(new Dimension(width, height));
		window.setContentPane(panel);
		window.pack();
		
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		
		window.setVisible(true);
	}
	
	public void closeWindow() {
		if (window != null)
			window.dispose();
	}
	
	public void changeColor(int color) {
		this.color = color;
	}
	
	public void drawPixel(int x, int y) {
		imagen.setRGB(x, y, color);
		window.repaint();
	}
}
