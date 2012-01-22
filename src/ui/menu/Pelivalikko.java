package ui.menu;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import model.Pelilogiikka;
import sound.Sound;
import ui.Ikkuna;
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
 * Luokka esittaa pelivalikon graafisesti ja reagoi hiiren liikkeisiin
 * pelivalikossa
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Pelivalikko extends Valikko implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TEKSTINAKYMA = "tekstinakyma";
	private static final String VALITSEPELI_NAKYMA = "valitsepeliNakyma";
	private static final String UUSIPELI_NAKYMA = "uusipeliNakyma";
	private static final Dimension TEXTFIELD_PIENI = new Dimension(220, 50);
	private Painike pelaaPainike, uusiPainike, takaisinPainike, hyvaksyPainike,
			nuoliPainike_V, tallennaPainike, nuoliPainike_U;
	private Valikkopohjapaneeli valikkopohjapaneeli;
	private Valikkopaneeli valikkopaneeli;
	private OikeaPaneeli tekstipaneeli, valitsePeliPaneeli, uusiPeliPaneeli;
	private Valikkootsikko valikkootsikko;
	private Tekstipaneeliotsikko tekstipaneeliotsikko;
	private Ikkuna ikkuna;
	private OikeaPaneeliPohja oikeaPaneeliPohja;
	private CardLayout cl;
	private JComboBox pelivalinta;
	private ArrayList<String> comboBoxItems;
	private Pelilogiikka peli;
	private JTextField pelinNimiUusi;
	private JTextArea pelitekstiUusi;
	private Tekstialue tekstialue;

	/**
	 * Konstruktori, joka luo pelivalikon ja sen esittämiseen tarvittavat
	 * komponentit ja asettelee ne paikoilleen
	 * 
	 * @param peli
	 *            Kaytettava pelilogiikka
	 * @param ikkuna
	 *            Ikkuna, jossa peli esitetaan
	 */
	public Pelivalikko(Pelilogiikka peli, Ikkuna ikkuna) {
		this.peli = peli;
		this.ikkuna = ikkuna;

		// luodaan painikkeet
		this.pelaaPainike = luoPainike("Pelaa");
		this.uusiPainike = luoPainike("Uusi");
		this.takaisinPainike = luoPainike("Takaisin päävalikkoon");
		this.hyvaksyPainike = luoPainike("Hyväksy");
		this.nuoliPainike_V = luoPainike("<----");
		this.tallennaPainike = luoPainike("Tallenna");
		this.nuoliPainike_U = luoPainike("<----");

		// luodaan paneelit
		this.valikkopohjapaneeli = new Valikkopohjapaneeli();
		this.valikkopaneeli = new Valikkopaneeli();
		this.oikeaPaneeliPohja = new OikeaPaneeliPohja();
		this.tekstipaneeli = new OikeaPaneeli();
		this.valitsePeliPaneeli = new OikeaPaneeli();
		this.uusiPeliPaneeli = new OikeaPaneeli();

		// luodaan tekstikentat
		this.pelinNimiUusi = luoNimikentta();
		this.pelitekstiUusi = luoTekstikentta();

		this.comboBoxItems = this.peli.annaInfolista();

		// luodaan otsikot
		this.valikkootsikko = new Valikkootsikko("Pelivalikko");
		this.tekstipaneeliotsikko = new Tekstipaneeliotsikko();

		this.tekstialue = new Tekstialue();
		this.pelivalinta = luoValintalaatikko();

		// asetetaan vihjetekstit niille komponenteille, joille ei muuten anneta
		// vihjetta
		this.hyvaksyPainike.setToolTipText("Painamalla pääset pelaamaan" +
				" valittua pelitekstiä");
		this.pelivalinta
				.setToolTipText("Valitse peliteksti, jota haluat pelata");
		this.nuoliPainike_U.setToolTipText("Takaisin edelliseen valikkoon");
		this.nuoliPainike_V.setToolTipText("Takaisin edelliseen valikkoon");
		this.pelinNimiUusi.setToolTipText("Anna pelitekstillesi kuvaava nimi." +
				" Max 40 merkkiä");
		this.pelitekstiUusi.setToolTipText("Kirjoita haluamasi" +
				" peliteksti. Max 150 merkkiä");
		this.tallennaPainike.setToolTipText("Tallenna pelitekstisi");

		// lisataan painikkeet valikkopaneeliin sopivin valein
		this.valikkopaneeli.add(Box.createVerticalGlue());
		this.valikkopaneeli.add(this.pelaaPainike);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.uusiPainike);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.takaisinPainike);
		this.valikkopaneeli.add(Box.createVerticalGlue());

		// lisataan painikkeet valitsePeliPaneeliin
		this.valitsePeliPaneeli.add(Box.createVerticalGlue());
		this.valitsePeliPaneeli.add(this.pelivalinta);
		this.valitsePeliPaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valitsePeliPaneeli.add(this.hyvaksyPainike);
		this.valitsePeliPaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valitsePeliPaneeli.add(this.nuoliPainike_V);
		this.valitsePeliPaneeli.add(Box.createVerticalGlue());

		this.uusiPeliPaneeli.add(Box.createVerticalGlue());
		this.uusiPeliPaneeli.add(this.pelinNimiUusi);
		this.uusiPeliPaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.uusiPeliPaneeli.add(this.pelitekstiUusi);
		this.uusiPeliPaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.uusiPeliPaneeli.add(this.tallennaPainike);
		this.uusiPeliPaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.uusiPeliPaneeli.add(this.nuoliPainike_U);
		this.uusiPeliPaneeli.add(Box.createVerticalGlue());

		// lisataan tekstikomponentit tekstipaneeliin
		this.tekstipaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.tekstipaneeli.add(this.tekstipaneeliotsikko);
		this.tekstipaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.tekstipaneeli.add(this.tekstialue);

		this.oikeaPaneeliPohja.add(this.uusiPeliPaneeli, UUSIPELI_NAKYMA);
		this.oikeaPaneeliPohja.add(this.valitsePeliPaneeli, VALITSEPELI_NAKYMA);
		this.oikeaPaneeliPohja.add(this.tekstipaneeli, TEKSTINAKYMA);

		// lisataan valikko- ja tekstipaneelit valikkopohjapaneeliin
		this.valikkopohjapaneeli.add(this.valikkopaneeli);
		this.valikkopohjapaneeli.add(this.oikeaPaneeliPohja);

		// lisataan valikolle otsikko ja valikkopohjapaneeli sen alle
		this.add(valikkootsikko, BorderLayout.NORTH);
		this.add(valikkopohjapaneeli, BorderLayout.CENTER);

		this.cl = (CardLayout) this.oikeaPaneeliPohja.getLayout();
		this.cl.show(this.oikeaPaneeliPohja, TEKSTINAKYMA);

		this.tallennaPainike.addActionListener(new Pelitekstikuuntelija(
				this.peli, this));
	}

	/**
	 * Paivittaa pelivalinta-JComboBoxin
	 */
	public void initPelivalikko() {
		// luetaan uusi lista tiedostosta
		this.comboBoxItems = this.peli.annaInfolista();
		// poistetaan vanhat itemit
		this.pelivalinta.removeAllItems();
		// lisataan uudet itemit
		for (int i = 0; i < this.comboBoxItems.size(); i++) {
			this.pelivalinta.addItem(this.comboBoxItems.get(i));
		}
	}

	/**
	 * Luo nimikentan ja asettaa sen ulkoasun halutunlaiseksi
	 * 
	 * @return JTextFiel-olio
	 */
	private JTextField luoNimikentta() {
		JTextField nimikentta = new JTextField();
		nimikentta.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				"Pelitekstin nimi:", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 15),
				Color.WHITE));
		nimikentta.setBackground(Color.BLACK);
		nimikentta.setForeground(Color.WHITE);
		nimikentta.setCaretColor(Color.WHITE);
		// rajoitetaan nimikentan merkkien maara
		nimikentta.setDocument(new Tekstirajoitus(40));
		// asetataan nimikentalle koko
		this.asetaKoko(nimikentta, TEXTFIELD_PIENI);
		return nimikentta;
	}

	/**
	 * Luo tekstikentän ja asettaa sen ulkoasun halutunlaiseksi
	 * 
	 * @return JTextArea-olio
	 */
	private JTextArea luoTekstikentta() {
		JTextArea tekstikentta = new JTextArea();
		tekstikentta.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				"Uusi peliteksti:", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 15),
				Color.WHITE));
		tekstikentta.setBackground(Color.BLACK);
		tekstikentta.setForeground(Color.WHITE);
		tekstikentta.setLineWrap(true);
		tekstikentta.setWrapStyleWord(true);
		tekstikentta.setCaretColor(Color.WHITE);
		// rajoitetaan tekstikentan merkkien maara
		tekstikentta.setDocument(new Tekstirajoitus(150));
		return tekstikentta;
	}

	/**
	 * Luo valintalaatikon ja asettaa sen ulkoasun halutunlaiseksi
	 * 
	 * @return JComboBox-olio
	 */
	private JComboBox luoValintalaatikko() {
		JComboBox valintalaatikko = new JComboBox();
		valintalaatikko.setBackground(Color.BLACK);
		valintalaatikko.setForeground(Color.WHITE);
		valintalaatikko.setMinimumSize(new Dimension(200, 30));
		valintalaatikko.setPreferredSize(new Dimension(200, 30));
		valintalaatikko.setMaximumSize(new Dimension(200, 30));
		// asetetaan valintalaatikkoon pelitekstien nimet
		for (int i = 0; i < this.comboBoxItems.size(); i++) {
			valintalaatikko.addItem(this.comboBoxItems.get(i));
		}
		return valintalaatikko;
	}

	/**
	 * Luo painikkeen ja asettaa sille MouseListenerin
	 * 
	 * @param teksti
	 *            Painikkeen teksti
	 * @return Painike-luokan olio
	 */
	private Painike luoPainike(String teksti) {
		Painike painike = new Painike(teksti);
		painike.addMouseListener(this);
		return painike;
	}

	/**
	 * Nayttaa nakyman, jossa esitetaan vihjetekstit
	 */
	public void naytaTekstinakyma() {
		this.cl.show(this.oikeaPaneeliPohja, TEKSTINAKYMA);
	}

	private void naytaUusiPeliNakyma() {
		this.pelinNimiUusi.setText("");
		this.pelitekstiUusi.setText("");
		this.cl.show(this.oikeaPaneeliPohja, UUSIPELI_NAKYMA);
	}

	private void asetaKoko(Component comp, Dimension d) {
		comp.setMinimumSize(d);
		comp.setPreferredSize(d);
		comp.setMaximumSize(d);
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Reagoi hiiren liikkeisiin painikkeiden ylla esittamalla halutut tekstit
	 * vihjetekstipaneelissa ja soittamalla tietyn aanen tietyn komponentin
	 * paalla
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getComponent().equals(this.pelaaPainike)) {
			this.naytaTekstit(0);
			Sound.TOM1.play(false);
		}
		if (e.getComponent().equals(this.uusiPainike)) {
			this.naytaTekstit(1);
			Sound.TOM2.play(false);
		}
		if (e.getComponent().equals(this.takaisinPainike)) {
			this.naytaTekstit(2);
			Sound.TOM4.play(false);
		}
		if (e.getComponent().equals(this.hyvaksyPainike)
				|| e.getComponent().equals(this.tallennaPainike)) {
			Sound.TOM1.play(false);
		}
		if (e.getComponent().equals(this.nuoliPainike_V)
				|| e.getComponent().equals(this.nuoliPainike_U)) {
			Sound.TOM4.play(false);
		}
	}

	/**
	 * Tyhjentaa vihjetekstikentat, kun hiiri poistuu komponenttien paalta
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		this.tekstipaneeliotsikko.setText("");
		this.tekstialue.setText("");

	}

	/**
	 * Reagoi painikkeiden painamiseen ja nayttaa halutun nakyman
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getComponent().equals(this.takaisinPainike)) {
			this.ikkuna.naytaPaavalikko();
		}
		if (e.getComponent().equals(this.pelaaPainike)) {
			this.initPelivalikko();
			this.cl.show(this.oikeaPaneeliPohja, VALITSEPELI_NAKYMA);
		}
		if (e.getComponent().equals(this.nuoliPainike_V)
				|| e.getComponent().equals(this.nuoliPainike_U)) {
			this.naytaTekstinakyma();
		}
		if (e.getComponent().equals(this.hyvaksyPainike)) {
			String teksti = (String) this.pelivalinta.getSelectedItem();
			this.ikkuna.naytaPelinakyma(teksti);
		}
		if (e.getComponent().equals(this.uusiPainike)) {
			this.naytaUusiPeliNakyma();
		}
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	private void naytaTekstit(int teksti) {
		switch (teksti) {
		case 0:
			this.tekstipaneeliotsikko.setText("Pelaa");
			this.tekstialue.setText("Valitse mieleisesi peliteksti ja pelaa.");
			break;
		case 1:
			this.tekstipaneeliotsikko.setText("Uusi");
			this.tekstialue.setText("Luo uusi peliteksti");
			break;
		case 2:
			this.tekstipaneeliotsikko.setText("Takaisin päävalikkoon");
			this.tekstialue.setText("Palaa takaisin päävalikkoon");
			break;
		}
	}

	/**
	 * Palauttaa nimikentan tekstin
	 * 
	 * @return pelinNimiUusi-komponenttiin kirjoitettu teksti
	 */
	public String annaPelinnimiUusi() {
		return this.pelinNimiUusi.getText();
	}

	/**
	 * Palauttaa pelitekstikentan tekstin
	 * 
	 * @return pelitekstiUusi-komponenttiin kirjoitettu teksti
	 */
	public String annaPelitekstiUusi() {
		return this.pelitekstiUusi.getText();
	}

	/**
	 * Asettaa pelinNimiUusi-komponenttiin annetun tekstin
	 * 
	 * @param teksti
	 *            Teksti, joka asetetaan komponenttiin
	 */
	public void asetaPelinnimiUusi(String teksti) {
		this.pelinNimiUusi.setText(teksti);
	}

	/**
	 * Asettaa pelitekstiUusi-komponenttiin annetun tekstin
	 * 
	 * @param teksti
	 *            Teksti, joka asetetaan komponenttiin
	 */
	public void asetaPelitekstiUusi(String teksti) {
		this.pelitekstiUusi.setText(teksti);
	}
}
