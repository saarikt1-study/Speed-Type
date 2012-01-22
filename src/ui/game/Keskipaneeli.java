package ui.game;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import sound.Sound;
import ui.Ikkuna;
import ui.menu.Tekstirajoitus;
import ui.menu.components.Painike;
import ui.menu.components.Tekstialue;
import model.Pelilogiikka;
import model.Tulosrivi;

/**
 * Kuvaa pelinakyman keskipaneelia
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Keskipaneeli extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PELI_VOITETTU = "peli voitettu";
	private static final String ALKUNAKYMA = "alkunakyma";
	private Pelilogiikka peli;
	private JPanel peliKaynnissaPaneeli, peliVoitettuPaneeli;
	private JLabel seuraavaKirjain, kellotaulu, aloitusvihje, nimi,
			voittoteksti;
	private Tekstialue oikeastiKirjoitit;
	private Painike pelaaUudestaan, tallenna;
	private Timer pelikello;
	private long aloitusaika;
	private long kulunutAika;
	private JTextField nimikentta;
	private CardLayout cl;
	private Ikkuna ikkuna;
	private String naytettavaAika;

	/**
	 * Konstruktori, joka luo uuden keskipaneeli-olion ja luo sen nayttamiseen
	 * vaaditut komponentit
	 * 
	 * @param peli
	 *            kaytetty pelilogiikka
	 * @param ikkuna
	 *            ikkuna, jossa peli esitetaan
	 */
	public Keskipaneeli(Pelilogiikka peli, Ikkuna ikkuna) {
		this.peli = peli;
		this.ikkuna = ikkuna;
		this.peliKaynnissaPaneeli = luoPaneeli();
		this.peliVoitettuPaneeli = luoPaneeli();
		this.pelaaUudestaan = new Painike("Pelaa uudestaan");
		this.seuraavaKirjain = luoJLabel("Seuraava kirjain: "
				+ this.peli.annaNykyinenMerkki());
		this.kellotaulu = luoJLabel("0");
		this.aloitusvihje = luoJLabel("Peli alkaa, kun kirjoitat" +
				" ensimmäisen merkin.");
		this.oikeastiKirjoitit = new Tekstialue();
		this.nimi = luoJLabel("Nimi:");
		this.voittoteksti = luoJLabel("");
		this.nimikentta = new JTextField(30);
		this.tallenna = new Painike("Tallenna");
		this.cl = new CardLayout();

		this.pelaaUudestaan.addMouseListener(new Painikekuuntelija());
		this.tallenna.addMouseListener(new Painikekuuntelija());

		this.oikeastiKirjoitit.setMinimumSize(new Dimension(500, 80));
		this.oikeastiKirjoitit.setPreferredSize(new Dimension(500, 80));
		this.oikeastiKirjoitit.setMaximumSize(new Dimension(500, 80));
		this.oikeastiKirjoitit.setEditable(false);

		this.setLayout(this.cl);

		this.nimikentta.addActionListener(new TallennusKuuntelija());
		// nimi max 40 merkkia
		this.nimikentta.setDocument(new Tekstirajoitus(40));

		// Boxlayout ei jostain syysta halunnut kayttaa preferredSize-kokoa,
		// joten pakotetaan nimikentta kayttamaan sita
		this.nimikentta.setMaximumSize(this.nimikentta.getPreferredSize());
		this.nimikentta.setBackground(Color.BLACK);
		this.nimikentta.setForeground(Color.WHITE);
		this.nimikentta.setBorder(BorderFactory.createLineBorder(
				Color.DARK_GRAY, 1));
		this.nimikentta.setCaretColor(Color.WHITE);

		this.peliKaynnissaPaneeli
				.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliKaynnissaPaneeli.add(this.seuraavaKirjain);
		this.peliKaynnissaPaneeli
				.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliKaynnissaPaneeli.add(this.kellotaulu);
		this.peliKaynnissaPaneeli
				.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliKaynnissaPaneeli.add(this.aloitusvihje);

		this.peliVoitettuPaneeli.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliVoitettuPaneeli.add(this.voittoteksti);
		this.peliVoitettuPaneeli.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliVoitettuPaneeli.add(this.nimi);
		this.peliVoitettuPaneeli.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliVoitettuPaneeli.add(this.nimikentta);
		this.peliVoitettuPaneeli.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliVoitettuPaneeli.add(oikeastiKirjoitit);
		this.peliVoitettuPaneeli.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliVoitettuPaneeli.add(this.pelaaUudestaan);
		this.peliVoitettuPaneeli.add(Box.createRigidArea(new Dimension(0, 20)));
		this.peliVoitettuPaneeli.add(this.tallenna);

		this.add(this.peliKaynnissaPaneeli, ALKUNAKYMA);
		this.add(this.peliVoitettuPaneeli, PELI_VOITETTU);

		// paivittaa pelikellon 20 ms valein. 1 ms valein paivittyvan kellon
		// kanssa prosessorin kuormitus oli pelattaessa vahintaan 75%,
		// 20 ms paivityksella pysyi max 25% kuormitus
		this.pelikello = new Timer(20, new Kellokuuntelija());

	}

	/**
	 * Luo JLabelin ja muuttaa sen ulkoasun halutuksi
	 * 
	 * @param teksti
	 *            JLabelin teksti
	 * @return Jlabel-olio
	 */
	private JLabel luoJLabel(String teksti) {
		JLabel label = new JLabel(teksti);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(Color.WHITE);
		return label;
	}

	/**
	 * Luo Jpanel-olion ja muuttaa sen ulkoasun halutuksi
	 * 
	 * @return JPanel-olio
	 */
	private JPanel luoPaneeli() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setOpaque(true);
		panel.setBackground(Color.BLACK);
		panel.setForeground(Color.WHITE);
		return panel;
	}

	/**
	 * Reagoi nappainten painamiseen ja muuttaa keskipaneelin nakymaa
	 * tarvittaessa
	 */
	public void update(Observable o, Object arg) {
		if (this.peli.onAloitettu()) {
			this.naytaPeliKaynnissa();
		}
		if (this.peli.onPaattynyt()) {
			this.naytaPeliVoitettu();
		}
		if (!this.peli.onAloitettu()) {
			this.naytaAlkunakyma();
		}
		this.seuraavaKirjain.setText("Seuraava kirjain: "
				+ this.peli.annaNykyinenMerkki());
	}

	private void naytaPeliVoitettu() {
		// jos ei ole viela tallennettu
		if (!this.peli.onkoTallennettu()) {
			this.pysaytaKello();
			this.oikeastiKirjoitit.setText("Oikeasti kirjoitit seuraavaa:\n\n"
					+ this.peli.annaKirjoitettuTeksti());
			this.voittoteksti
					.setText("Aikasi oli: "
							+ this.annaNaytettavaAika()
							+ ". Tallenna tulos kirjoittamalla nimesi ja" +
									" painamalla enter tai \"Tallenna\".");
			this.nimikentta.setText("");
			this.nimikentta.setEditable(true);
			this.nimikentta.setFocusable(true);
			this.tallenna.setEnabled(true);
			this.cl.show(this, PELI_VOITETTU);
			// focus request vasta nakyman vaihtamisen jalkeen, jotta
			// focus oikeasti on suoraan nimikentalla
			this.nimikentta.requestFocusInWindow();
		}
	}

	private void naytaPeliKaynnissa() {
		this.kaynnistaKello();
		this.aloitusvihje.setText("Aloita alusta painamalla F2.");

	}

	private void naytaAlkunakyma() {
		this.cl.show(this, ALKUNAKYMA);
		this.pysaytaKello();
		this.alustaTeksti();
		this.nollaaAika();
	}

	/**
	 * Sisaluokka, joka vastaa kellotaulun paivittamisesta
	 * 
	 * @author Tommi Saarikangas
	 * 
	 */
	public class Kellokuuntelija implements ActionListener {

		/**
		 * Paivittaa kellotaulun tekstia
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			DecimalFormat df = new DecimalFormat("###,###");
			kulunutAika = System.currentTimeMillis() - aloitusaika;
			naytettavaAika = df.format(kulunutAika);
			kellotaulu.setText(naytettavaAika);
		}
	}

	/**
	 * Sisaluokka, joka kuuntelee hiiren liikkeita painikkeiden suhteen
	 * 
	 * @author Tommi Saarikangas
	 * 
	 */
	public class Painikekuuntelija implements MouseListener {

		/**
		 * Ei kaytossa
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		/**
		 * Soittaa aanen, kun hiiri viedaan painikkeiden paalle
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getComponent().equals(tallenna)) {
				Sound.TOM2.play(false);
			}
			if (e.getComponent().equals(pelaaUudestaan)) {
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
		 * Reagoi pelaaUudestaan-painikkeen painamiseen alustamalla pelinakyman
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource().equals(pelaaUudestaan)) {
				ikkuna.requestFocusInWindow();
				peli.initPeli();
			}
		}

		/**
		 * Ei kaytossa
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	/**
	 * Kaynnistaa pelikellon
	 */
	public void kaynnistaKello() {
		if (!this.pelikello.isRunning()) {
			this.aloitusaika = System.currentTimeMillis();
			this.pelikello.start();
		}
	}

	/**
	 * Pysayttaa pelikellon
	 */
	public void pysaytaKello() {
		if (this.pelikello.isRunning()) {
			this.pelikello.stop();
		}
	}

	/**
	 * Palauttaa kuluneen ajan
	 * 
	 * @return kulunutAika
	 */
	public long annaAika() {
		return this.kulunutAika;
	}

	/**
	 * Palauttaa kuluneen ajan String-muotoisen esityksen
	 * 
	 * @return kulunut aika String-muodossa
	 */
	public String annaNaytettavaAika() {
		return this.naytettavaAika;
	}

	/**
	 * Asettaa kellotaulun tekstiksi "0"
	 */
	public void nollaaAika() {
		this.kellotaulu.setText("0");
	}

	/**
	 * Alustaa aloitusvihjeen tekstin halutuksi
	 */
	public void alustaTeksti() {
		this.aloitusvihje
				.setText("Peli alkaa, kun kirjoitat ensimmäisen merkin.");
	}

	/**
	 * Sisaluokka, joka kuuntelee pelituloksen tallentamista
	 * 
	 * @author Tommi Saarikangas
	 * 
	 */
	public class TallennusKuuntelija implements ActionListener {

		/**
		 * Tallentaa tuloksen juuri pelatun pelitekstin omaan tulostaulukkoon
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int sija = 0;
			// tallentamisessa toimii tallenna-panike ja enterin painaminen
			if (e.getSource().equals(tallenna)
					|| e.getSource().equals(nimikentta)) {
				String s = nimikentta.getText();
				// jos pelaaja ei anna nimea, nimi on "tuntematon"
				if (s.length() == 0) {
					s = "tuntematon";
				}
				Tulosrivi uusiRivi = new Tulosrivi(s, annaAika());
				sija = peli.tallennaTulos(uusiRivi, peli.annaPelinnimi());
				nimikentta.setText("Tulos tallennettu. Pääsit sijalle " + sija);
				if (sija == -1) {
					nimikentta
							.setText("Tuloksesi ei riittänyt" +
									" listasijoitukseen");
				}
				// varmistetaan, ettei pelaaja voi lisata tulostaa useammin
				///kuin kerran
				nimikentta.setEditable(false);
				nimikentta.setFocusable(false);
				peli.asetaTallennettu(true);
				tallenna.setEnabled(false);
				ikkuna.requestFocusInWindow();
			}
		}
	}
}
