package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import sound.Sound;

/**
 * Pelilogiikka-luokka, joka lukee pelitekstit tiedostosta ja vertaa
 * nappainpainalluksia pelitekstiin. Sisaltaa myos tiedon pelin tilasta
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Pelilogiikka extends Observable {
	private static final String CLASSIC = "pelitekstit/classic.txt";
	private boolean paattynyt, aloitettu;
	private int oikeatMerkit;
	private String peliteksti, kirjoitettuTeksti, pelinnimi;
	private StringBuffer sb;
	private Tulostaulukko tulostaulukko;
	private boolean onTallennettu;

	/**
	 * Luontimetodi, joka luo uuden Pelilogiikka-olion
	 */
	public Pelilogiikka() {
		// pelin alkuasetelma
		this.paattynyt = false;
		this.aloitettu = false;
		this.onTallennettu = false;
		this.oikeatMerkit = 0;

		// alustetaan aluksi pelin nimeksi classic
		this.pelinnimi = "classic";
		// alustetaan aluksi pelitekstiksi classic
		this.peliteksti = this.lueTeksti(CLASSIC);
		// luodaan StringBufferi, johon voidaan lisata kirjoitetut merkit
		this.sb = new StringBuffer();

		try {
			this.tulostaulukko = new Tulostaulukko(
					"tulokset/classic_tulokset.txt", 100);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Palauttaa tiedon siita, onko kyseistä pelia viela tallennettu
	 * 
	 * @return jos on jo tallennettu true, muuten false
	 */
	public boolean onkoTallennettu() {
		if (onTallennettu) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Asettaa onTallennettu-attribuutille halutun arvon
	 * 
	 * @param tallennettu
	 *            onTallennettu-attribuutille asetettava arvo
	 */
	public void asetaTallennettu(boolean tallennettu) {
		if (tallennettu) {
			this.onTallennettu = true;
		} else {
			this.onTallennettu = false;
		}
	}

	/**
	 * Tallentaa yksittaisen tuloksen tiettyyn taulukkoon
	 * 
	 * @param rivi
	 *            Tallennettava Tulosrivi
	 * @param taulukonnimi
	 *            Pelitekstin nimi, jonka perusteella Tulosrivi tallennetaan
	 *            tietynnimiseen tulostiedostoon
	 * @return palauttaa sijan, jolle tulos on tallennettu
	 */
	public int tallennaTulos(Tulosrivi rivi, String taulukonnimi) {
		// maaritellaan polku oikein
		String s = "tulokset/" + taulukonnimi + "_tulokset.txt";
		try {
			this.tulostaulukko = new Tulostaulukko(s, 100);
			tulostaulukko.lisaaRivi(rivi);
			// tallennetaan rivi valittomasti tiedostoon, koska esim. macin
			// command+Q-ohjelman lopetusta on vaikea hallita
			tulostaulukko.tallennaRivitTiedostoon();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.tulostaulukko.annaSija();
	}

	/**
	 * Palauttaa parametrina annetun nimisen tulostaulukon
	 * 
	 * @param taulukonnimi
	 *            Pelitekstin nimi, jonka avulla palautetaan haluttu
	 *            tulostaulukko tiedostosta
	 * @return parametrina annetun nimen mukainen tulostaulukko tai null, jos
	 *         tulostaulukon luominen ei onnistu
	 */
	public Tulostaulukko annaTulostaulukko(String taulukonnimi) {
		// maaritellaan polku oikein
		String s = "tulokset/" + taulukonnimi + "_tulokset.txt";
		try {
			Tulostaulukko tulostaulukko = new Tulostaulukko(s, 100);
			return tulostaulukko;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.err.println("Tulostaulukon hakeminen tiedostosta epaonnistui");
		return null;
	}

	/**
	 * Lukee pelitekstin tiedostosta
	 * 
	 * @param tiedostonNimi
	 *            Tiedosto, josta teksti luetaan
	 * @return luettu teksti tai null, jos lukeminen ei onnistu
	 */
	public String lueTeksti(String tiedostonNimi) {
		try {
			FileInputStream in = new FileInputStream(tiedostonNimi);
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String teksti = br.readLine();
			return teksti;

		} catch (FileNotFoundException e) {
			System.err.println("Tekstitiedostoa ei löytynyt.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Tiedostoa ei voitu lukea.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Palauttaa infoLista-nimisen tiedoston pohjalta luodun listan pelitekstien
	 * nimista
	 * 
	 * @return lista pelitekstien nimista tai null, jos tiedoston lukemimnen
	 *         epaonnistuu
	 */
	public ArrayList<String> annaInfolista() {
		// infolista pitaa sisallaan kaikkien pelitekstien/tulostaulukoiden
		// nimet
		ArrayList<String> infolista = new ArrayList<String>();
		try {
			FileInputStream in = new FileInputStream("pelitekstit/info.txt");
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String rivi = null;

			while ((rivi = br.readLine()) != null) {
				infolista.add(rivi);
			}

			return infolista;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Vertaa kirjoitettuja merkkeja pelitekstin merkkeihin ja ilmoittaa
	 * tarkkailijoiksi rekisteröityneille luokille muutoksista
	 * 
	 * @param c
	 *            verrattava merkki
	 * @return true, jos merkki on oikea, muuten false
	 */
	public boolean vertaaMerkkeja(char c) {
		// jos tekstin kirjoittaminen on viela kesken
		if (this.oikeatMerkit < this.peliteksti.length()) {
			// tallennetaan kirjoitetut kirjaimet kayttamalla StringBufferia,
			// koska
			// se on nopeampi kuin +=
			sb.append(c);
			// tallennetaan, mita pelaaja oikeasti kirjoitti
			this.kirjoitettuTeksti = sb.toString();
			// verrataan kirjoitettua merkkia nykyiseen merkkiin
			if (c == this.annaNykyinenMerkki()) {
				// Peli on aloitettu, ensimmaisen oikean nappaimen painamisen
				// jalkeen
				this.aloitettu = true;
				this.oikeatMerkit++;
				Sound.TYPE.play(false);
				// jos merkin kirjoittamisen jalkeen teksti on kirjoitettu
				// kokonaan, niin this.paattynyt = true
				if (this.oikeatMerkit == this.peliteksti.length()) {
					this.paattynyt = true;

					// arvotaan voittohuuto, jotta siihen saadaan vahan
					// vaihtelua
					Random rand = new Random();
					double arpa = rand.nextDouble();
					if (arpa < 0.3) {
						Sound.VOITTO.play(false);
					} else if (arpa < 0.6) {
						Sound.VOITTO2.play(false);
					} else {
						Sound.VOITTO3.play(false);
					}
				}
				this.setChanged();
				this.notifyObservers();

				return true;
			}
			// jos kirjoitetaan vaara merkki
			else {
				if (this.onAloitettu()) {
					Sound.UUH.play(false);
				}
				return false;
			}
		}
		// jos koko teksti on jo kirjoitettu
		else {
			this.oikeatMerkit++;
			this.setChanged();
			this.notifyObservers();
			return false;
		}
	}

	/**
	 * Palauttaa "nykyisen" merkin, eli kyseisella hetkella kirjoitusvuorossa
	 * olevan oikean merkin pelattavasta pelitekstista
	 * 
	 * @return seuraavaksi kirjoitettava merkki tai 0, jos peliteksti on jo
	 *         kirjoitettu
	 */
	public char annaNykyinenMerkki() {
		if (this.oikeatMerkit < this.peliteksti.length()) {
			return this.peliteksti.charAt(this.oikeatMerkit);
		} else
			return 0;
	}

	/**
	 * Palauttaa pelitekstin
	 * 
	 * @return peliteksti
	 */
	public String annaPeliteksti() {
		return this.peliteksti;
	}

	/**
	 * Palauttaa pelitekstin nimen
	 * 
	 * @return pelitekstin nimi eli pelinnimi
	 */
	public String annaPelinnimi() {
		return this.pelinnimi;
	}

	/**
	 * palauttaa kirjoitetun tekstin, eli kirjaimet, mita kayttaja on
	 * kirjoittanut, vaikka ne olisivatkin "vaaria"
	 * 
	 * @return kirjoitettu teksti
	 */
	public String annaKirjoitettuTeksti() {
		return this.kirjoitettuTeksti;
	}

	/**
	 * Palauttaa tiedon siitä, monta oikeaa merkkia pelitekstista pelaaja on
	 * kirjoittanut
	 * 
	 * @return oikeiden merkkien lukumaaran
	 */
	public int annaOikeatMerkit() {
		if (this.oikeatMerkit < this.peliteksti.length()) {
			return this.oikeatMerkit;
		} else
			return 0;
	}

	/**
	 * Palauttaa tiedon pelin paattymisesta
	 * 
	 * @return true, jos peli on paattynyt, muuten false
	 */
	public boolean onPaattynyt() {
		if (this.paattynyt == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Palauttaa tiedon pelin aloittamisesta
	 * 
	 * @return true, jos peli on aloitettu, muuten false
	 */
	public boolean onAloitettu() {
		if (this.aloitettu == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Asettaa pelitekstiksi parametrina annetun tekstin
	 * 
	 * @param teksti
	 *            pelitekstiksi asetettava teksti
	 */
	public void asetaPeliteksti(String teksti) {
		this.pelinnimi = teksti;
		// luetaan "teksti"-niminen tiedosto seuraavanlaisella polulla
		String uusiTeksti = "pelitekstit/" + teksti + ".txt";
		this.peliteksti = this.lueTeksti(uusiTeksti);
	}

	/**
	 * Alustaa pelin alkutilaansa
	 */
	public void initPeli() {
		this.asetaTallennettu(false);
		this.aloitettu = false;
		this.paattynyt = false;
		this.oikeatMerkit = 0;
		this.nollaaKirjoitettuTeksti();
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Nollaa kirjoitetun tekstin
	 */
	public void nollaaKirjoitettuTeksti() {
		this.sb.delete(0, sb.length());
	}
}
