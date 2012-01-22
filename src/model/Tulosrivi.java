package model;

import java.text.DecimalFormat;


/**
 * Luokka kuvaa yksittaista rivia tulostaulussa. Siina on tiedot pelaajan 
 * nimesta ja pelin voittoon kuluneesta ajasta.  
 */
public class Tulosrivi implements Comparable<Tulosrivi> {

	private String pelaajanNimi;
	private long pelisekunnit;
	
	/**
	 * Luontimetodi, joka muodostaa uuden Tulosrivi-olion. Tulosrivi kuvaa yhta 
	 * miinaharavan pelitulosta, ja se sisaltaa tiedot pelaajan nimesta, seka
	 *  siita, montako sekuntia pelin voittamiseen kaytettiin.
	 * @param pelaajanNimi pelaajan nimi
	 * @param pelisekunnit voittoon kulunut aika
	 */
	public Tulosrivi(String pelaajanNimi, long pelisekunnit) {		
		this.pelaajanNimi = pelaajanNimi;
		this.pelisekunnit = pelisekunnit;
	}
	
	/**
	 * Palauttaa tiedon pelaajan nimesta.
	 * @return pelaajan nimi
	 */
	public String annaPelaajanNimi() {	
		return this.pelaajanNimi;
	}
	
	/**
	 * Palauttaa voittoon kuluneen ajan.
	 * 
	 * @return voittoa edeltaneen ajan
	 */
	public long annaPelisekunnit() {		
		return this.pelisekunnit;
	}
	
	/**
	 * Palauttaa pelisekunnit String-muodossa muotoiltuna
	 * @return pelitulos
	 */
	public String annaTulos() {
		String tulos;
		DecimalFormat df = new DecimalFormat("###,###");
		tulos = df.format(this.pelisekunnit);
		return tulos;
	}
	
	/**
	 * Vertaa tulosrivia parametrina annettuun. Palauttaa positiivisen luvun, 
	 * jos tama on parempi, negatiivisen jos tama on huonompi ja nollan, jos 
	 * tama on tasmalleen yhta hyva tulos kuin <code>t</code>.
	 * 
	 * @param t vertaamisen kohde
	 * @return <ul><li>negatiivinen luku, jos tama on huonompi kuin 
	 * 			<code>t</code></li>
	 * 			<li>positiivinen luku, jos tamaa on parempi</li>
	 * 			<li>0, jos tulokset ovat tarkalleen samanlaiset</li></ul>
	 */
	public int compareTo(Tulosrivi t) {
		
		// Siina uniikissa tapauksessa, etta kaikki tiedot ovat samat, 
		// palautetaan 0.
		if (this.pelisekunnit == t.annaPelisekunnit() && 
				this.pelaajanNimi.equals(t.annaPelaajanNimi())) {
			
			return 0;
		}
		
		// Tama on parempi kuin t, jos:
		// 1. kulutettu aika on pienempi
		// 2. nimi on aakkosjarjestyksessa ennemmin.
		
		// Talloin palautetaan posit. luku
		if (this.pelisekunnit < t.annaPelisekunnit() ||
			(this.pelisekunnit == t.annaPelisekunnit() &&
					this.pelaajanNimi.compareTo(t.annaPelaajanNimi()) < 0 )) {
			return 1;
		}
		
		// Muulloin negatiivinen.
		else {
			
			return -1;
		}
	}
	
	/**
	 * Palauttaa tulosrivin tiedot merkkijonona, jossa tiedot on erotettu 
	 * toisistaan ampersandilla (&).
	 */
	@Override
	public String toString() {
		
		return this.pelaajanNimi + "&" + this.pelisekunnit;
	}
}

