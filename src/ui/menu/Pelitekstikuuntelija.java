package ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import model.Pelilogiikka;

/**
 * Kuuntelijaluokka, joka kuuntelee uuden pelitekstin luomista ja tallentaa
 * tekstin ja sen nimen tiedostoon
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Pelitekstikuuntelija implements ActionListener {
	private Pelivalikko pelivalikko;
	private BufferedWriter bw, bw2;
	private Pelilogiikka peli;

	/**
	 * Konstruktori, joka luo uuden Pelitekstikuuntelija-olion
	 * 
	 * @param peli
	 * @param pelivalikko
	 */
	public Pelitekstikuuntelija(Pelilogiikka peli, Pelivalikko pelivalikko) {
		this.pelivalikko = pelivalikko;
		this.peli = peli;
	}

	/**
	 * Tallentaa uuden pelitekstin omaan tiedostoonsa ja lisaa pelitekstin nimen
	 * infolistaan
	 */
	public void actionPerformed(ActionEvent arg0) {
		// tekstikenttiin syotetyt tekstit
		String nimi = this.pelivalikko.annaPelinnimiUusi();
		String teksti = this.pelivalikko.annaPelitekstiUusi();

		File tiedosto = new File("pelitekstit/" + nimi + ".txt");

		if (tiedosto.exists()) {
			this.pelivalikko.asetaPelinnimiUusi("Nimi oli jo olemassa.");
		}
		if (!tiedosto.exists() && nimi.length() > 1 && teksti.length() > 1) {
			try {
				tiedosto.createNewFile();
				this.kirjoitaInfolista(nimi);
				// kirjoitetaan uusi peliteksti tiedostoon
				FileOutputStream fos = new FileOutputStream(tiedosto);
				OutputStreamWriter tekstikirjoittaja = new OutputStreamWriter(
						fos, "UTF-8");
				bw2 = new BufferedWriter(tekstikirjoittaja);
				bw2.write(teksti);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// suljetaan virrat
				if (bw2 != null) {
					try {
						bw2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			this.pelivalikko.asetaPelinnimiUusi("Tallennus onnistui!");
			this.pelivalikko.asetaPelitekstiUusi("");
		}
		// nimen ja tekstin oltava yli merkin pituiset
		if (nimi.length() <= 1 || teksti.length() <= 1) {
			this.pelivalikko
					.asetaPelitekstiUusi("Nimen ja tekstin on oltava" +
							" vähintään kahden merkin pituiset.");
		}
	}

	private void kirjoitaInfolista(String nimi) {

		ArrayList<String> infolista = this.peli.annaInfolista();
		infolista.add(nimi);

		OutputStream out = null;
		Writer kirjoittaja = null;
		BufferedWriter puskurikirjoittaja = null;

		try {
			// Tyhjennetaan tiedosto aluksi
			File infotiedosto = new File("pelitekstit/info.txt");
			if (infotiedosto.exists()) {
				infotiedosto.delete();
			}
			infotiedosto.createNewFile();

			// Luodaan tavuvirta tiedostosta
			out = new FileOutputStream(infotiedosto);
			// tekstivirta UTF-8-merkistöllä
			kirjoittaja = new OutputStreamWriter(out, "UTF-8");
			// puskuroidaan virta
			puskurikirjoittaja = new BufferedWriter(kirjoittaja);

			for (int i = 0; i < infolista.size(); i++) {
				puskurikirjoittaja.write(infolista.get(i) + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (puskurikirjoittaja != null) {
					puskurikirjoittaja.close();
				}
			} catch (IOException ie) {
				ie.printStackTrace(System.err);
			}
		}
	}
}
