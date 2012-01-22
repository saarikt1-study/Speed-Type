package ui.game;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import ui.Ikkuna;
import model.Pelilogiikka;

/**
 * Kuvaa pelinakymaa
 * @author Tommi Saarikangas
 *
 */
public class Pelinakyma extends JPanel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ylapaneeli ylapaneeli;
	private Keskipaneeli keskipaneeli;
	private Alapaneeli alapaneeli;
	private Pelilogiikka peli;
	private Ikkuna ikkuna;

	/**
	 * Konstruktori. Luo pelinakyman ja sen esittamisessa kaytetyt paneelit
	 * @param peli Kaytettava pelilogiikka
	 * @param ikkuna Ikkuna, jossa peli esitetaan
	 */
	public Pelinakyma(Pelilogiikka peli, Ikkuna ikkuna) {
		this.peli = peli;
		this.ikkuna = ikkuna;

		this.ylapaneeli = new Ylapaneeli(this.peli);
		this.keskipaneeli = new Keskipaneeli(this.peli, this.ikkuna);
		this.alapaneeli = new Alapaneeli(this.ikkuna);
		
		this.setLayout(new BorderLayout());
		this.addMouseListener(this);
		
		//Asetetaan tarvittavat paneelit tarkkailemaan pelilogiikkaa
		this.peli.addObserver(keskipaneeli);
		this.peli.addObserver(ylapaneeli);
		
		this.add(BorderLayout.NORTH, this.ylapaneeli);
		this.add(BorderLayout.CENTER, this.keskipaneeli);
		this.add(BorderLayout.SOUTH, this.alapaneeli);
	}

	/**
	 * Klikkaamalla pelialuetta, focus takaisin ikkunalle --> nappaimiston 
	 * nappaily toimii taas
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//varmistetaan, etta klikkaamalla pelialuetta, saadaan focus takaisin
		//ikkunalle, jotta pelaamista voi jatkaa
		if(e.getComponent().equals(this)) {
			this.ikkuna.requestFocusInWindow();
		}
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseExited(MouseEvent e) {		
	}
	
	/**
	 * Ei kaytossa
	 */
	@Override
	public void mousePressed(MouseEvent e) {		
	}
	
	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
