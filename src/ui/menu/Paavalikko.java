package ui.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import sound.Sound;
import ui.Ikkuna;
import ui.menu.components.OikeaPaneeli;
import ui.menu.components.Painike;
import ui.menu.components.Tekstialue;
import ui.menu.components.Tekstipaneeliotsikko;
import ui.menu.components.Valikko;
import ui.menu.components.Valikkootsikko;
import ui.menu.components.Valikkopaneeli;
import ui.menu.components.Valikkopohjapaneeli;

/**
 * Luokka esittaa paavalikon graafisesti ja reagoi hiiren liikkeisiin 
 * paavalikossa
 * @author Tommi Saarikangas
 *
 */
public class Paavalikko extends Valikko implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Painike aloitaPeliPainike, ohjeetPainike,
	tuloksetPainike, lopetaPainike;
	private Tekstipaneeliotsikko tekstipaneeliotsikko;
	private Valikkootsikko valikkootsikko; 
	private Valikkopohjapaneeli valikkopohjapaneeli;
	private OikeaPaneeli tekstipaneeli;
	private Valikkopaneeli valikkopaneeli;
	private Tekstialue tekstialue;
	private Ikkuna ikkuna;

	/**
	 * Luokan konstruktori, joka luo uuden Paavalikko-olion ja asettelee sille
	 * tarvittavat komponentit
	 * @param ikkuna
	 */
	public Paavalikko(Ikkuna ikkuna) {		
		this.ikkuna = ikkuna;

		//luodaan painikkeet
		this.aloitaPeliPainike = luoPainike("Aloita peli");
		this.ohjeetPainike = luoPainike("Ohjeet");
		this.tuloksetPainike = luoPainike("Tulokset");
		this.lopetaPainike = luoPainike("Lopeta");

		//luodaan otsikot
		this.valikkootsikko = new Valikkootsikko("Speed Type");
		this.tekstipaneeliotsikko = new Tekstipaneeliotsikko();

		this.tekstialue = new Tekstialue();

		//luodaan paneelit
		this.valikkopohjapaneeli = new Valikkopohjapaneeli();
		this.valikkopaneeli = new Valikkopaneeli();
		this.tekstipaneeli = new OikeaPaneeli();

		this.valikkopaneeli.add(Box.createVerticalGlue());
		this.valikkopaneeli.add(this.aloitaPeliPainike);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.ohjeetPainike);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.tuloksetPainike);
		this.valikkopaneeli.add(Box.createRigidArea(Valikko.VALI_PIENI));
		this.valikkopaneeli.add(this.lopetaPainike);
		this.valikkopaneeli.add(Box.createVerticalGlue());

		this.tekstipaneeli.add(Box.createRigidArea(new Dimension(0,30)));
		this.tekstipaneeli.add(this.tekstipaneeliotsikko);
		this.tekstipaneeli.add(Box.createRigidArea(new Dimension(0,30)));
		this.tekstipaneeli.add(this.tekstialue);

		this.valikkopohjapaneeli.add(this.valikkopaneeli);
		this.valikkopohjapaneeli.add(this.tekstipaneeli);

		this.add(this.valikkootsikko, BorderLayout.NORTH);
		this.add(this.valikkopohjapaneeli, BorderLayout.CENTER);
	}

	/**
	 * Luo Painike-tyyppisen painikkeen ja lisaa sille MouseListenerin
	 * @param teksti Painikkeen teksti
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
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * Nayttaa vihjetekstit ja soittaa aanen, kun hiiri osuu kyseiseen
	 * painikkeeseen
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(arg0.getComponent().equals(this.aloitaPeliPainike)) {
			this.naytaTekstit(0);
			Sound.TOM1.play(false);
		}
		if(arg0.getComponent().equals(this.ohjeetPainike)) {
			this.naytaTekstit(1);
			Sound.TOM2.play(false);
		}
		if(arg0.getComponent().equals(this.tuloksetPainike)) {
			this.naytaTekstit(2);
			Sound.TOM3.play(false);
		}
		if(arg0.getComponent().equals(this.lopetaPainike)) {
			this.naytaTekstit(3);
			Sound.TOM4.play(false);
		}

	}

	/**
	 * Nollaa vihjetekstit, kun hiiri poistuu kyseisen komponentin paalta
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		this.tekstipaneeliotsikko.setText("");
		this.tekstialue.setText("");
	}

	/**
	 * Nayttaa halutun nakyman (tai lopettaa pelin), kun kyseista painiketta
	 * painetaan
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getComponent().equals(this.aloitaPeliPainike)) {
			this.ikkuna.naytaPelivalikko();
		}
		if(arg0.getComponent().equals(this.lopetaPainike)) {
			System.exit(0);
		}
		if(arg0.getComponent().equals(this.tuloksetPainike)) {
			this.ikkuna.naytaTulosnakyma();
		}
		if(arg0.getComponent().equals(this.ohjeetPainike)) {
			this.ikkuna.naytaOhjenakyma();
		}
	}

	/**
	 * Ei kaytossa
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * Nayttaa halutun vihjetekstin
	 * @param teksti Halutun vihjetekstin numero
	 */
	public void naytaTekstit(int teksti) {
		switch(teksti) {
		case 0:
			this.tekstipaneeliotsikko.setText("Aloita peli");
			this.tekstialue.setText("Voit valita yhden valmiista" +
					" peliteksteistä tai luoda itse uuden"); break;
		case 1:
			this.tekstipaneeliotsikko.setText("Ohjeet");
			this.tekstialue.setText("Näyttää ohjetekstin"); break;
		case 2:
			this.tekstipaneeliotsikko.setText("Tulokset");
			this.tekstialue.setText("Näyttää tulostaulukot"); break;
		case 3:
			this.tekstipaneeliotsikko.setText("Lopeta");
			this.tekstialue.setText("Lopettaa pelin"); break;
		}
	}
}
