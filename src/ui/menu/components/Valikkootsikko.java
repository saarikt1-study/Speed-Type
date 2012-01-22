package ui.menu.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Valikon otsikoita kuvaava luokka
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Valikkootsikko extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Border PIENI_REUNUS = BorderFactory.createEmptyBorder(
			20, 20, 20, 20);
	private String nimi;

	/**
	 * Luo uuden valikko-otsikon halutulla ulkoasulla
	 * 
	 * @param nimi
	 *            Otsikko
	 */
	public Valikkootsikko(String nimi) {
		this.nimi = nimi;
		this.setText(this.nimi);
		// vahan tilaa otsikon ymparille, ettei nayta niin ahtaalta
		this.setBorder(PIENI_REUNUS);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("helvetica", Font.PLAIN, 18));
	}
}
