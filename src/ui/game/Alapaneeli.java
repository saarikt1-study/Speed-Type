package ui.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import sound.Sound;
import ui.Ikkuna;
import ui.menu.components.Painike;

/**
 * Luokka kuva pelinakyman alapaneelia
 * @author Tommi Saarikangas
 *
 */
public class Alapaneeli extends JPanel implements MouseListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Painike palaaPaavalikkoon, tulokset, ohjeet;
	private JCheckBox mute;
	private Ikkuna ikkuna;

	/**
	 * Konstruktori, joka luo uuden Alapaneeli-olion
	 * @param ikkuna ikkuna, jossa peli naytetaan
	 */
	public Alapaneeli(Ikkuna ikkuna) {
		this.ikkuna = ikkuna;
		this.palaaPaavalikkoon = luoPainike("Palaa pŠŠvalikkoon");
		this.tulokset = luoPainike("Tulokset");
		this.ohjeet = luoPainike("Ohjeet");
		this.mute = new JCheckBox("Mute");
		this.mute.setBackground(Color.BLACK);
		this.mute.setForeground(Color.WHITE);
		this.mute.addItemListener(this);

		this.setLayout(new FlowLayout());
		this.add(this.palaaPaavalikkoon);
		this.add(this.tulokset);
		this.add(this.ohjeet);
		this.add(this.mute);

		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);

	}

	/**
	 * Luo painikkeen ja asettaa sille kuuntelijan
	 * @param teksti painikkeen teksti
	 * @return Painike-olio
	 */
	private Painike luoPainike(String teksti) {
		Painike painike = new Painike(teksti);
		painike.addMouseListener(this);
		return painike;
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Reagoi hiiren liikuttamiseen painikkeen paalle soittamalla aanen
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource().equals(this.palaaPaavalikkoon)) {
			Sound.TOM4.play(false);
		}	
		if(e.getSource().equals(this.tulokset)) {
			Sound.TOM2.play(false);
		}
		if(e.getSource().equals(this.ohjeet)) {
			Sound.TOM1.play(false);
		}
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Reagoi nappainten painamiseen nayttamalla tietyn nakyman
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource().equals(this.palaaPaavalikkoon)) {
			this.ikkuna.naytaPaavalikko();
		}	
		if(e.getSource().equals(this.tulokset)) {
			this.ikkuna.naytaTulosnakyma();
		}
		if(e.getSource().equals(this.ohjeet)) {
			this.ikkuna.naytaOhjenakyma();
		}
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Reagoi mute-valintapainikkeen kayttamiseen hiljentamalla aanet, tai
	 * laittamalla niiden aanenvoimakkuuden takaisin normaaliksi
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Sound.volume = Sound.Volume.MUTE;
		}
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			Sound.volume = Sound.Volume.ON;
		}	
	}
}
