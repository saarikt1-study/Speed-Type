package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Pelilogiikka;
import ui.*;

/**
 * Pelikontrollit-luokka tarkkailee KeyEventteja ja valittaa tiedot painetuista
 * nappaimista Pelilogiikalle
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Pelikontrollit implements KeyListener {
	private Pelilogiikka peli;
	private Ikkuna ikkuna;

	/**
	 * Pelikontrollit-luokan konstruktori
	 * 
	 * @param peli
	 *            Pelilogiikka, joka huolehtii pelin logiikasta
	 * @param ikkuna
	 *            Ikkuna, jonka keyeventteja luokka tarkkailee
	 */
	public Pelikontrollit(Pelilogiikka peli, Ikkuna ikkuna) {
		this.peli = peli;
		this.ikkuna = ikkuna;
		this.ikkuna.addKeyListener(this);
	}

	/**
	 * Reagoi KeyEventteihin ja lahettaa pelilogiikalle tiedot niista
	 */
	public void keyPressed(KeyEvent e) {
		// ei reagoi pelkan shiftin tai altin painamiseen
		if (e.getKeyCode() == KeyEvent.VK_ALT
				|| e.getKeyCode() == KeyEvent.VK_SHIFT) {

		} else {
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				this.peli.initPeli();
			} else {
				this.peli.vertaaMerkkeja(e.getKeyChar());
			}
		}
	}

	/**
	 * Ei kaytossa
	 */
	public void keyReleased(KeyEvent arg0) {
	}

	/**
	 * Ei kaytossa
	 */
	public void keyTyped(KeyEvent arg0) {
	}
}
