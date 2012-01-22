package ui.menu.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Luokka kuvaa painiketta ja reagoi hiiren liikkeisiin sen paalla
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Painike extends JButton implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension PAINIKE_PIENI = new Dimension(150, 40);
	private String teksti;

	/**
	 * Konstruktori
	 * 
	 * @param teksti
	 *            Painikkeen teksti
	 */
	public Painike(String teksti) {
		this.teksti = teksti;

		this.setText(this.teksti);
		this.setFont(new Font("Arial", Font.PLAIN, 15));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.asetaKoko(PAINIKE_PIENI);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setBorder(null);
		this.addMouseListener(this);
	}

	/**
	 * Asettaa painikkeen kooksi d
	 * 
	 * @param d
	 *            Painikkeen uusi koko
	 */
	public void asetaKoko(Dimension d) {
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * Asettaa painikkeelle reunuksen, kun hiiri on sen paalla
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setBorder(BorderFactory.createEtchedBorder(new Color(20, 20, 20),
				new Color(15, 15, 15)));
	}

	/**
	 * Poistaa painikkeen reunuksen hiiren poistuessa sen paalta
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setBorder(null);
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
