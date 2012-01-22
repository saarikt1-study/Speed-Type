package ui.menu.components;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Kuvaa valikkonakyman oikeanpuoleista paneelia
 * @author tommi
 *
 */
public class OikeaPaneeli extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension VASEN_PANEELI_PIENI = new Dimension(320,400);

	/**
	 * Konstruktori, joka luo uuden OikeaPaneeli-olion ja asettaa sille halutun
	 * layoutin, koon ja varin
	 */
	public OikeaPaneeli() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.asetaKoko(VASEN_PANEELI_PIENI);
		this.setBackground(Color.BLACK);
	}
	
	/**
	 * Asettaa paneelin kooksi d
	 * @param d paneelin uusi koko
	 */
	public void asetaKoko(Dimension d) {
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
	}
}
