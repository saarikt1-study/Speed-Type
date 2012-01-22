package ui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Pelilogiikka;

/**
 * Kuvaa pelinakyman ylapaneelia. Kirjainten esittamiseen kaytetaan
 * JLabel-taulukkoa, jotta sen yksittaisia alkioita pystyttaisiin kasittelemaan
 * helposti
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Ylapaneeli extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pelilogiikka peli;
	private ArrayList<JLabel> kirjaintaulukko;

	/**
	 * Luo uuden Ylapaneeli-olion ja asettaa sille halutut varit
	 * 
	 * @param peli
	 *            Kaytetty pelilogiikka
	 */
	public Ylapaneeli(Pelilogiikka peli) {
		this.peli = peli;
		this.kirjaintaulukko = new ArrayList<JLabel>();
		this.setPreferredSize(new Dimension(320, 60));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
	}

	/**
	 * Alustaa ylapaneelin, eli poistaa kirjaimet paneelista, tyhjentaa
	 * kirjaintaulukon, luo uuden taulukon pelitekstista ja asettaa kirjaimet
	 * jalleen paneeliin
	 */
	public void initYlapaneeli() {
		// poistetaan JLabelit ylapaneelista
		for (int i = 0; i < this.kirjaintaulukko.size(); i++) {
			this.remove(this.kirjaintaulukko.get(i));
		}
		// tyhjennetaan JLabel-taulukko
		this.kirjaintaulukko.clear();
		// luodaan uusi taulukko ja asetetaan sen alkiot jalleen ylapaneeliin
		for (int i = 0; i < this.peli.annaPeliteksti().length(); i++) {
			char c = this.peli.annaPeliteksti().charAt(i);
			JLabel label = new JLabel(String.valueOf(c));
			label.setForeground(new Color(0.5F, 0.5F, 0.5F));
			this.kirjaintaulukko.add(label);
			this.add(label);
		}
	}

	/**
	 * Reagoi nappainten painamiseen ja muuttaa ylapaneelin kirjaimien ulkoasua
	 * tarvittaessa
	 */
	public void update(Observable arg0, Object arg1) {
		// Pelin ollessa kaynnissa, varittaa oikein kirjoitetut merkit
		// valkoiseksi
		if (this.peli.onAloitettu() && !this.peli.onPaattynyt()) {
			this.kirjaintaulukko.get(this.peli.annaOikeatMerkit() - 1)
					.setForeground(Color.WHITE);
		}
		// varitetaan viela viimeinenkin merkki
		if (this.peli.onPaattynyt()) {
			this.kirjaintaulukko.get(this.peli.annaPeliteksti().length() - 1)
					.setForeground(Color.WHITE);
		}
		// Jos pelia ei ole viela aloitettu, kutsuu metodia initYlapaneeli()
		if (!this.peli.onAloitettu()) {
			// this.nollaaYlapaneeli();
			this.initYlapaneeli();
		}
	}
}
