package ui.menu.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;

/**
 * Luokka tekstipaneelin otsikon esittamiseen
 * @author Tommi Saarikangas
 *
 */
public class Tekstipaneeliotsikko extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Luo uuden Tekstipaneeliotsikko-olion
	 */
	public Tekstipaneeliotsikko() {
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setForeground(Color.WHITE);
	}
}
