package ui.menu.components;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Valikkopaneelia kuvaava luokka
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Valikkopaneeli extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension VALIKKOPANEELI_PIENI = new Dimension(320,
			400);

	/**
	 * Luo uuden valikkopaneelin halutuilla ominaisuuksilla
	 */
	public Valikkopaneeli() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.asetaKoko(VALIKKOPANEELI_PIENI);
		this.setBackground(Color.BLACK);
	}

	/**
	 * Asettaa valikkopaneelille uuden koon
	 * 
	 * @param d
	 *            Paneelin uusi koko
	 */
	public void asetaKoko(Dimension d) {
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
	}
}
