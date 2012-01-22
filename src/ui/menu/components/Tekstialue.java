package ui.menu.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Kuvaa tekstialuetta
 * @author Tommi Saarikangas
 *
 */
public class Tekstialue extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktori, joka luo uuden tekstialueen ja asettaa sille halutun
	 * koon, varin ja tyyliasetukset
	 */
	public Tekstialue() {
		this.setMinimumSize(new Dimension(250, 200));
		this.setPreferredSize(new Dimension(250, 200));
		this.setMaximumSize(new Dimension(250, 200));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setEditable(false);
		
		//keskittaa tekstialueen tekstin
		StyledDocument doc = this.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
}
