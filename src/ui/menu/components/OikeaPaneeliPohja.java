package ui.menu.components;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * Luokka kuvaa valikoiden oikeanpuoleisen paneelin pohjaa, niissa tapauksissa,
 * joissa oikeanpuoleista paneelia halutaan vaihtaa
 * @author Tommi Saarikangas
 *
 */
public class OikeaPaneeliPohja extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Luo paneelin ja asettaa sille CardLayoutin
	 */
	public OikeaPaneeliPohja() {
		this.setLayout(new CardLayout());
	}
}
