package ui.menu.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Valikon pohjimmaista paneelia kuvaava luokka
 * @author tommi
 *
 */
public class Valikko extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Dimension VALI_PIENI = new Dimension(0,30);
	
	/**
	 * Luo uuden valikkopaneelin
	 */
	public Valikko() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
	}
}
