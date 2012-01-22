package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.table.AbstractTableModel;

/**
 * Luokka toimii tulosrivien paremmuusjarjestyksen
 * tietorakenteena, sekä tarjoaa myos tyokaluja tulosrivien
 * kaittelyyn, kuten tiedostoon kirjoittamiseen ja tiedostosta lukemiseen.
 */
public class Tulostaulukko extends AbstractTableModel implements
Iterable<Tulosrivi> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Tulosrivi> tulokset;
	private String tiedostonNimi;
	private int koko, sija;

	/**
	 * Luo tulostaulukon annetuista parametreista. Taulukkoon mahtuu
	 * enintaan <code>koko</code> tulosrivia, sen jalkeen
	 * huonoin jatetaann pois lisattaessa
	 * riveja;. <code>tiedostonNimi</code> kertoo, mista; tiedostosta
	 * tulokset luetaan ja mihin uudet kirjoitetaan.
	 * 
	 * @param tiedostonNimi
	 *            kohdetiedoston nimi
	 * @param koko
	 *            tulostaulukon rivien maara;
	 */
	public Tulostaulukko(String tiedostonNimi, int koko) throws ParseException {

		this.tiedostonNimi = tiedostonNimi;
		// Epakelpoja arvoja ei tarvitse tarkistaa, silla negatiiviset arvot
		// vain estavat yhdenkaan rivin lisaamisen.
		this.koko = koko;
		this.tulokset = new ArrayList<Tulosrivi>();

		InputStream in = null;
		Reader lukija = null;
		BufferedReader puskurilukija = null;
		File tiedosto = new File(tiedostonNimi);

		if (tiedosto.exists()) {
			try {
				// Luodaan tavuvirta tiedostosta
				in = new FileInputStream(tiedostonNimi);
				// tekstivirta UTF-8-merkistolla
				lukija = new InputStreamReader(in, "UTF-8");
				// puskuroidaan virta
				puskurilukija = new BufferedReader(lukija);

				String rivi = null;
				while ((rivi = puskurilukija.readLine()) != null) {

					String[] sanat = rivi.split("&");

					Tulosrivi t = new Tulosrivi(sanat[0],
							Integer.parseInt(sanat[1]));
					this.lisaaRivi(t);
				}
			} catch (IOException ie) {
				ie.printStackTrace(System.err);
			} finally {
				// suljetaan virta
				try {
					if (puskurilukija != null) {
						puskurilukija.close();
					}
				} catch (IOException ie) {
					ie.printStackTrace(System.err);
				}
			}
		} else {

			// Yrittaa kymmenen kertaa luoda tyhjan tiedoston ja jos ei onnistu,
			// ilmoittaa siita kayttajalle.
			int i = 0;
			while (!tiedosto.exists() && i < 10) {
				try {
					tiedosto.createNewFile();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
				i++;
			}

			if (!tiedosto.exists()) {
				System.out.println("Tiedostoa ei voitu luoda.\n"
						+ "High Score -lista ei toimi.");
			}
		}
	}

	/**
	 * Lisaa taulukkoon yhden tuloksen ja poistaa sielta huonoimman,
	 * jos taulukon koko muuten ylittyisi. Palauttaa luvun, joka kertoo
	 * monenneksiko uusi rivi paatyi tai luvun -1, jos rivi oli liian
	 * huono lisattavaksi.
	 * 
	 * @param rivi
	 *            lisaa <code>Tulosrivi</code>
	 * @return tuloksen sijoitus taulukossa tai -1, jos tulos oli liian huono
	 */
	public int lisaaRivi(Tulosrivi rivi) {

		// Oletetaan etta rivia ei lisata.
		boolean lisataan = false;

		if (this.tulokset.size() < this.koko) {

			// Jos on tilaa, lisataan joko tapauksessa.
			lisataan = true;
		} else {

			// Muulloin iteroidaan:
			for (int i = 0; i < this.tulokset.size(); i++) {

				Tulosrivi t = this.tulokset.get(i);

				// Jos jokin rivi on huonompi, todetaan etta oletus oli
				// vaara
				if (rivi.compareTo(t) > 0) {

					lisataan = true;

					// Siis poistetaan huonoin eli viimeinen
					this.tulokset.remove(this.koko - 1);
					break;
				}
			}
		}

		// Jos tulos oli riittava, lisataan rivi oikeaan indeksiin
		if (lisataan) {

			int indeksi = 0;

			Iterator<Tulosrivi> iteraattori = this.iterator();

			// Niin kauan kuin verrattava on parempi, on indeksi liian suuri
			// annettavaksi uudelle riville. Heti kun verrattava rivi on
			// huonompi, ollaan oikeassa kohdassa.
			while (iteraattori.hasNext()
					&& iteraattori.next().compareTo(rivi) >= 0) {
				indeksi++;
			}

			this.tulokset.add(indeksi, rivi);
			indeksi++;
			this.sija = indeksi;
			return indeksi;
		}
		this.sija = -1;
		return -1;
	}

	public int annaSija() {
		return this.sija;
	}

	/**
	 * Sisaluokka, joka kuvaa olion iteraattoria. (Java-API: <a
	 * href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/Iterator.html">
	 * Iterator</a>)
	 */
	class Iteraattori implements Iterator<Tulosrivi> {

		private List<Tulosrivi> tulokset;
		private int indeksi;

		/**
		 * Luo uuden iteraattorin nykyisist&auml; tuloksista.
		 */
		public Iteraattori() {

			this.tulokset = Tulostaulukko.this.tulokset;
			this.indeksi = 0;
		}

		/**
		 * Kertoo, onko seuraava iteroitava olio olemassa.
		 * 
		 * @return <code>true</code> jos seuraava olio on olemassa, muutoin
		 *         <code>false</code>
		 */
		public boolean hasNext() {

			// Jos asken kayty indeksi on pienempi kuin listan pituus
			// vahennettyna yhdella, on viela iteroitavaa, paitsi jos listan
			// pituus on nolla.
			if (this.tulokset.size() > 0
					&& this.indeksi < (this.tulokset.size())) {

				return true;
			}

			return false;
		}

		/**
		 * Palauttaa jarjestyksessa seuraavan <code>Tulosrivi</code>-olion.
		 * Heittaa poikkeuksen <code>NoSuchElementException</code>,
		 * jos seuraavaa oliota ei ole.
		 */
		public Tulosrivi next() {

			try {

				Tulosrivi tulos = this.tulokset.get(this.indeksi);
				this.indeksi++;

				return tulos;
			} catch (IndexOutOfBoundsException e) {

				throw new NoSuchElementException("Ei oliota indeksissa "
						+ this.indeksi);
			}
		}

		/**
		 * Heittaa poikkeuksen
		 * <code>UnsupportedOperationException</code>.
		 */
		public void remove() {

			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Palauttaa iteraattorin talle tulostaulukolle.
	 * 
	 * @return iteraattori tuloksille
	 */
	public Iterator<Tulosrivi> iterator() {

		return new Iteraattori();
	}

	/**
	 * Tallentaa tulosrivit tiedostoon. Palauttaa totuusarvon onnistumisesta.
	 * 
	 * @return <code>true</code> jos tallennus onnistui, muutoin
	 *         <code>false</code>
	 */
	public boolean tallennaRivitTiedostoon() {

		OutputStream out = null;
		Writer kirjoittaja = null;
		BufferedWriter puskurikirjoittaja = null;

		try {
			// Tyhjennetaan tiedosto aluksi
			File filu = new File(this.tiedostonNimi);
			if (filu.exists()) {
				filu.delete();
			}
			filu.createNewFile();

			// Luodaan tavuvirta tiedostosta
			out = new FileOutputStream(filu);
			// tekstivirta Latin-1-merkistolla
			kirjoittaja = new OutputStreamWriter(out, "UTF-8");
			// puskuroidaan virta
			puskurikirjoittaja = new BufferedWriter(kirjoittaja);

			for (int i = 0; i < this.tulokset.size(); i++) {
				try {
					Tulosrivi t = this.tulokset.get(i);
					puskurikirjoittaja.write(t.toString() + "\n");
				} catch (Exception e) { // Sivuutetaan virheet, tulostetaan
					e.printStackTrace(System.err);
				}
			}
		} catch (IOException ie) {

			ie.printStackTrace(System.err);

			return false;
		} finally {
			// suljetaan virta
			try {
				if (puskurikirjoittaja != null) {
					puskurikirjoittaja.close();
				}
			} catch (IOException ie) {
				ie.printStackTrace(System.err);
			}
		}
		return true;
	}

	/**
	 * Palauttaa rivien maaran
	 */
	public int getRowCount() {

		return this.tulokset.size();
	}

	/**
	 * Palauttaa sarakkeiden maaran
	 */
	public int getColumnCount() {

		return 3;
	}

	/**
	 * Palauttaa tietyn solun arvon
	 * @param rivi tietty taulukon rivi
	 * @param sarake tietty taulukon sarake
	 */
	public Object getValueAt(int rivi, int sarake) {

		Tulosrivi tulos = this.tulokset.get(rivi);
		Object olio = null;

		switch (sarake) {
		case 0:
			olio = (rivi+1) + ".";
			break;
		case 1:
			olio = tulos.annaPelaajanNimi();
			break;
		case 2:
			olio = tulos.annaTulos();
			break;
		}

		return olio;
	}

	/**
	 * Palauttaa sarakkeen nimen indeksissa <code>sarake</code>.
	 * 
	 * @return sarakkeen <code>sarake</code> nimi tai tyhja merkkijono
	 */
	@Override
	public String getColumnName(int sarake) {

		switch (sarake) {

		case 0:
			return "Sija";
		case 1:
			return "Nimi";
		case 2:
			return "Aika";
		default:
			return "";
		}
	}
}
