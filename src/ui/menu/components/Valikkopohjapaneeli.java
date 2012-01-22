package ui.menu.components;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Valikoiden pohjapaneelia kuvaava luokka
 * @author Tommi Saarikangas
 *
 */
public class Valikkopohjapaneeli extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Luo uuden pohjapaneelin
	 */
	public Valikkopohjapaneeli() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBackground(Color.BLACK);
	}
}
