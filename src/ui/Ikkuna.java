package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import sound.Sound;
import ui.game.Pelinakyma;
import ui.menu.Paavalikko;
import ui.menu.Pelivalikko;
import model.Pelilogiikka;

/**
 * Luokka, joka esittaa peli-ikkunaa
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Ikkuna extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int IKKUNA_PIENI_LEVEYS = 640;
	private static final int IKKUNA_PIENI_KORKEUS = 480;
	// Paneelien nimet CardLayouttia varten
	private static final String PELINAKYMA = "pelinakyma";
	private static final String PAAVALIKKO = "paavalikko";
	private static final String PELIVALIKKO = "pelivalikko";
	private static final String TULOSNAKYMA = "tulosnakyma";
	private static final String OHJENAKYMA = "ohjenakyma";
	private Pelinakyma pelinakyma;
	private Paavalikko paavalikko;
	private Pelivalikko pelivalikko;
	private Pelilogiikka peli;
	private Ohjeet ohjeet;
	private CardLayout cl;
	private Tulosnakyma tulosnakyma;

	/**
	 * Konstruktori. Luo ikkunan ja sen sisaltamat paneelit
	 * 
	 * @param peli
	 *            Kaytettava pelilogiikka
	 */
	public Ikkuna(Pelilogiikka peli) {
		this.peli = peli;

		// luodaan eri nakymia esittavat paneelit
		this.paavalikko = new Paavalikko(this);
		this.pelivalikko = new Pelivalikko(this.peli, this);
		this.tulosnakyma = new Tulosnakyma(this.peli, this);
		this.ohjeet = new Ohjeet(this.peli, this);
		this.pelinakyma = new Pelinakyma(this.peli, this);
		// luodaan CardLayout-olio, jotta voidaan vaihtaa naytettavaa paneelia
		// sen show-metodilla
		this.cl = new CardLayout();
		this.setLayout(this.cl);

		this.add(this.pelinakyma, PELINAKYMA);
		this.add(this.paavalikko, PAAVALIKKO);
		this.add(this.pelivalikko, PELIVALIKKO);
		this.add(this.tulosnakyma, TULOSNAKYMA);
		this.add(this.ohjeet, OHJENAKYMA);

		this.naytaPaavalikko();

		this.setPreferredSize(new Dimension(IKKUNA_PIENI_LEVEYS,
				IKKUNA_PIENI_KORKEUS));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Palauttaa CardLayout-olion
	 * 
	 * @return Kaytetty CardLayout-olio
	 */
	public CardLayout annaLayoout() {
		return this.cl;
	}

	/**
	 * Nayttaa paavalikon
	 */
	public void naytaPaavalikko() {
		this.cl.show(this.getContentPane(), PAAVALIKKO);
		if (!Sound.THEME.isRunning()) {
			Sound.THEME.play(true);
		}
	}

	/**
	 * Nayttaa pelivalikon
	 */
	public void naytaPelivalikko() {
		this.pelivalikko.naytaTekstinakyma();
		this.cl.show(this.getContentPane(), PELIVALIKKO);
	}

	/**
	 * Nayttaa ja alustaa pelinakyman
	 * 
	 * @param teksti
	 *            Pelattava peliteksti
	 */
	public void naytaPelinakyma(String teksti) {
		// asettaa pelitekstiksti parametrina annetun tekstin
		this.peli.asetaPeliteksti(teksti);
		this.peli.initPeli();
		this.requestFocusInWindow();
		Sound.THEME.stop();
		this.cl.show(this.getContentPane(), PELINAKYMA);
	}

	/**
	 * Nayttaa tulosnakyman
	 */
	public void naytaTulosnakyma() {
		this.cl.show(this.getContentPane(), TULOSNAKYMA);
		this.tulosnakyma.initTulosnakyma();
		// soitetaan theme, jos tullaan pelinakymasta suoraan tulosnakymaan
		if (!Sound.THEME.isRunning()) {
			Sound.THEME.play(true);
		}
	}

	/**
	 * Nayttaa ohjenakyman
	 */
	public void naytaOhjenakyma() {
		// soitetaan theme, jos tullaan pelinakymasta suoraan ohjenakymaan
		if (!Sound.THEME.isRunning()) {
			Sound.THEME.play(true);
		}
		this.cl.show(this.getContentPane(), OHJENAKYMA);
	}

}
