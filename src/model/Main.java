package model;

import javax.swing.JFrame;
import javax.swing.UIManager;
import sound.Sound;
import ui.Ikkuna;
import controller.Pelikontrollit;

/**
 * Pelin kaynnistava luokka
 * @author Tommi Saarikangas
 *
 */
public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Paaohjelmametodi, joka asettaa ohjelmalle CrossPlatformLookAndFeelin luo
	 * tarkeimpien luokkien oliot
	 * @param args Kayttajan antama String-taulukko
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Could not set the new Look and Feel.");
			e.printStackTrace();
		}
		//ladataan ŠŠnet klippeihin
		Sound.init();
		Pelilogiikka peli = new Pelilogiikka();
		Ikkuna ikkuna = new Ikkuna(peli);
		Pelikontrollit pelikontrollit = new Pelikontrollit(peli, ikkuna);	
	}
}
