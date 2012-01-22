package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Pelilogiikka;
import sound.Sound;
import ui.menu.components.OikeaPaneeli;
import ui.menu.components.OikeaPaneeliPohja;
import ui.menu.components.Painike;
import ui.menu.components.Tekstialue;
import ui.menu.components.Tekstipaneeliotsikko;
import ui.menu.components.Valikko;
import ui.menu.components.Valikkootsikko;
import ui.menu.components.Valikkopaneeli;
import ui.menu.components.Valikkopohjapaneeli;

/**
 * Luokka kuvaa tulosnakyman graafisesti
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Tulosnakyma extends Valikko implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TEKSTINAKYMA = "tekstinakyma";
	private static final String TULOSNAKYMA = "tulosnakyma";
	private JTable tulostaulukko;
	private OikeaPaneeli tekstipaneeli;
	private OikeaPaneeliPohja oikeaPaneeliPohja;
	private Valikkopaneeli valikkopaneeli;
	private Valikkopohjapaneeli valikkopohjapaneeli;
	private Painike hyvaksyPainike, takaisinPainike;
	private JComboBox tulosValinta;
	private Valikkootsikko valikkootsikko;
	private Ikkuna ikkuna;
	private ArrayList<String> comboBoxItems;
	private Pelilogiikka peli;
	private CardLayout cl;
	private JScrollPane tulosPaneeli = new JScrollPane();
	private Tekstipaneeliotsikko tekstipaneeliotsikko;
	private Tekstialue tekstialue;

	/**
	 * Tulosnakyman konstruktori, joka luo uuden tulosnakyman ja siihen
	 * tarvittavat swing-komponentit
	 * 
	 * @param peli
	 *            kaytettava pelilogiikka
	 * @param ikkuna
	 *            ikkuna, jossa tulosnakyma esitetaan
	 */
	public Tulosnakyma(Pelilogiikka peli, Ikkuna ikkuna) {
		this.peli = peli;
		this.ikkuna = ikkuna;
		this.tulostaulukko = new JTable();
		this.comboBoxItems = this.peli.annaInfolista();

		// luodaan paneelit
		this.tekstipaneeli = new OikeaPaneeli();
		this.valikkopaneeli = new Valikkopaneeli();
		this.valikkopohjapaneeli = new Valikkopohjapaneeli();
		this.oikeaPaneeliPohja = new OikeaPaneeliPohja();
		this.tulosPaneeli = new JScrollPane(this.tulostaulukko);
		this.tulosPaneeli.setBackground(Color.BLACK);

		this.tulosPaneeli.setBorder(BorderFactory.createEmptyBorder(15, 15, 15,
				15));
		this.tulostaulukko.setBackground(Color.BLACK);
		this.tulostaulukko.setForeground(Color.WHITE);
		this.tulostaulukko.setFillsViewportHeight(true);
		this.tulostaulukko.setShowGrid(false);
		this.tulostaulukko.setEnabled(false);
		this.tulostaulukko.getTableHeader().setBackground(Color.BLACK);
		this.tulostaulukko.getTableHeader().setForeground(Color.WHITE);

		this.tekstipaneeliotsikko = new Tekstipaneeliotsikko();
		this.tekstialue = new Tekstialue();

		this.hyvaksyPainike = luoPainike("Näytä");
		this.takaisinPainike = luoPainike("Palaa päävalikkoon");

		this.tulosValinta = new JComboBox();
		this.tulosValinta.setMinimumSize(new Dimension(200, 30));
		this.tulosValinta.setPreferredSize(new Dimension(200, 30));
		this.tulosValinta.setMaximumSize(new Dimension(200, 30));
		this.tulosValinta.setBackground(Color.BLACK);
		this.tulosValinta.setForeground(Color.WHITE);
		// lisataan customValinta-komponenttiin tulostaulukoiden nimet
		for (int i = 0; i < this.comboBoxItems.size(); i++) {
			this.tulosValinta.addItem(this.comboBoxItems.get(i));
		}
		this.tulosValinta
				.setToolTipText("Valitse peliteksti, jonka tulokset haluat nähdä");

		this.tekstipaneeli.add(Box.createRigidArea(new Dimension(0, 30)));
		this.tekstipaneeli.add(this.tekstipaneeliotsikko);
		this.tekstipaneeli.add(Box.createRigidArea(new Dimension(0, 30)));
		this.tekstipaneeli.add(this.tekstialue);

		this.valikkootsikko = new Valikkootsikko("Tulokset");

		cl = (CardLayout) this.oikeaPaneeliPohja.getLayout();

		// lisataan painikkeet valikkopaneeliin
		this.valikkopaneeli.add(Box.createVerticalGlue());
		this.valikkopaneeli.add(this.tulosValinta);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.hyvaksyPainike);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.takaisinPainike);
		this.valikkopaneeli.add(Box.createVerticalGlue());

		this.oikeaPaneeliPohja.add(this.tekstipaneeli, TEKSTINAKYMA);
		this.oikeaPaneeliPohja.add(this.tulosPaneeli, TULOSNAKYMA);

		this.valikkopohjapaneeli.add(this.valikkopaneeli);
		this.valikkopohjapaneeli.add(this.oikeaPaneeliPohja);

		this.add(this.valikkootsikko, BorderLayout.NORTH);
		this.add(this.valikkopohjapaneeli, BorderLayout.CENTER);

		this.cl.show(this.oikeaPaneeliPohja, TEKSTINAKYMA);

	}

	/**
	 * Paivittaa tulosValinta-komponentin
	 */
	public void initTulosnakyma() {
		// hakee uuden listan tiedostosta
		this.comboBoxItems = this.peli.annaInfolista();
		// tyhjentaa komponentin
		this.tulosValinta.removeAllItems();
		// lisaa komponenttiin uuden listan mukaiset itemit
		for (int i = 0; i < this.comboBoxItems.size(); i++) {
			this.tulosValinta.addItem(this.comboBoxItems.get(i));
		}
	}

	/**
	 * Asettaa JTable-komponentin sarakkeille halutut leveydet
	 */
	private void asetaSarakeleveydet() {
		this.tulostaulukko.getColumnModel().getColumn(0).setPreferredWidth(50);
		this.tulostaulukko.getColumnModel().getColumn(1).setPreferredWidth(250);
		this.tulostaulukko.getColumnModel().getColumn(2).setPreferredWidth(100);

	}

	/**
	 * Luo Painike-tyyppisen painikkeen ja asettaa sille kuuntelijan
	 * 
	 * @param teksti
	 *            painikkeen teksti
	 * @return painike
	 */
	private Painike luoPainike(String teksti) {
		Painike painike = new Painike(teksti);
		painike.addMouseListener(this);
		return painike;
	}

	/**
	 * Ei kaytossa
	 */
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * Esittaa vihjetekstin ja aanen, kun hiiri osuu painikkeisiin
	 */
	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getComponent().equals(this.hyvaksyPainike)) {
			this.tekstipaneeliotsikko.setText("Näytä");
			this.tekstialue.setText("Näyttää valitun tulostaulukon");
			Sound.TOM1.play(false);
		}
		if (arg0.getComponent().equals(this.takaisinPainike)) {
			this.tekstipaneeliotsikko.setText("Takaisin päävalikkoon");
			this.tekstialue.setText("Palaa takaisin päävalikkoon");
			Sound.TOM4.play(false);
		}
	}

	/**
	 * Tyhjentaa vihjetekstin, kun hiiri poistuu painikkeiden paalta
	 */
	public void mouseExited(MouseEvent arg0) {
		this.tekstipaneeliotsikko.setText("");
		this.tekstialue.setText("");
	}

	/**
	 * Reagoi hiiren painamiseen painikkeiden paalla, nayttamalla oikean nakyman
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getComponent().equals(this.takaisinPainike)) {
			this.ikkuna.naytaPaavalikko();
		}
		if (arg0.getComponent().equals(this.hyvaksyPainike)) {
			String s = (String) this.tulosValinta.getSelectedItem();
			this.tulostaulukko.setModel(this.peli.annaTulostaulukko(s));
			this.asetaSarakeleveydet();
			this.cl.show(this.oikeaPaneeliPohja, TULOSNAKYMA);
		}
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
