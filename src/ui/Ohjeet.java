package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import sound.Sound;
import ui.menu.components.Painike;
import ui.menu.components.Tekstialue;
import ui.menu.components.Valikko;
import ui.menu.components.Valikkootsikko;
import model.Pelilogiikka;

/**
 * Esittaa ohjevalikon graafisesti
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Ohjeet extends Valikko {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tekstialue ohjepaneeli;
	private Pelilogiikka peli;
	private Painike takaisin;
	private Ikkuna ikkuna;
	private JPanel alapaneeli;
	private Valikkootsikko otsikko;

	/**
	 * Konstruktori
	 * 
	 * @param peli
	 *            Kaytettava pelilogiikka
	 * @param ikkuna
	 *            Ikkuna, jossa peli esitetaan
	 */
	public Ohjeet(Pelilogiikka peli, Ikkuna ikkuna) {
		this.peli = peli;
		this.ikkuna = ikkuna;
		this.ohjepaneeli = new Tekstialue();
		this.takaisin = new Painike("Takaisin pŠŠvalikkoon");
		// Lukee ohjeet tekstitiedostosta
		this.ohjepaneeli.setText(this.peli.lueTeksti("ohjeet/ohjeet.txt"));
		this.alapaneeli = new JPanel();
		this.otsikko = new Valikkootsikko("Ohjeet");

		this.takaisin.addMouseListener(new Painikekuuntelija());

		this.alapaneeli.setLayout(new BoxLayout(this.alapaneeli,
				BoxLayout.PAGE_AXIS));
		this.alapaneeli.setBackground(Color.BLACK);

		this.alapaneeli.add(this.ohjepaneeli);
		this.alapaneeli.add(this.takaisin);

		this.add(this.otsikko, BorderLayout.NORTH);
		this.add(this.alapaneeli, BorderLayout.CENTER);
	}

	/**
	 * Sisaluokka, joka kuuntelee hiirta
	 * 
	 * @author Tommi Saarikangas
	 * 
	 */
	public class Painikekuuntelija implements MouseListener {

		/**
		 * Ei kaytossa
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		/**
		 * Soittaa aanen, kun hiiri osuu kyseiseen painikkeeseen
		 */
		@Override
		public void mouseEntered(MouseEvent arg0) {
			if (arg0.getSource().equals(takaisin)) {
				Sound.TOM4.play(false);
			}
		}

		/**
		 * Ei kaytossa
		 */
		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		/**
		 * Reagoi kyseisen painikkeen painamiseen
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {
			if (arg0.getSource().equals(takaisin)) {
				ikkuna.naytaPaavalikko();
			}
		}

		/**
		 * Ei kaytossa
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}
}
