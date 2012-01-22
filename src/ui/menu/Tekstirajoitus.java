package ui.menu;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Luo uuden dokumentin, ja asettaa sen merkkimaaralle rajoituksen
 * 
 * @author Tommi Saarikangas
 * 
 */
public class Tekstirajoitus extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int raja;

	/**
	 * Konstruktori
	 * 
	 * @param raja
	 *            Kuinka monta merkkia dokumentti hyvaksyy
	 */
	public Tekstirajoitus(int raja) {
		super();
		this.raja = raja;
	}

	/**
	 * Ylikirjoittaa insertString-metodin, jotta se ei hyvaksy yli rajan
	 * pituisia merkkijonoja
	 */
	@Override
	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null)
			return;

		if ((getLength() + str.length()) <= raja) {
			super.insertString(offset, str, attr);
		}
	}
}
