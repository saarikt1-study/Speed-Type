package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Enumeraatio-luokka, joka huolehtii pelin aanista. Antaa tyokalut
 * musiikkitiedostojen lataamiseksi klippeihin ja klippien soittamiseen.
 * @author Tommi Saarikangas
 *
 */
public enum Sound {
	TOM1("sound/tom1.wav"),
	TOM2("sound/tom2.wav"),
	TOM3("sound/tom3.wav"),
	TOM4("sound/tom4.wav"),
	UUH("sound/uuh.wav"),
	TYPE("sound/type.wav"),
	VOITTO("sound/victory.wav"),
	VOITTO2("sound/victory2.wav"),
	VOITTO3("sound/victory3.wav"),
	THEME("sound/theme.wav");

	/**
	 * Enumeraatiot aanen voimakkuudelle, jotka ovat talla hetkella mute ja on
	 * 
	 * @author Tommi Saarikangas
	 * 
	 */
	public static enum Volume {
		MUTE, ON
	}

	/**
	 * Maaritellaan Volume tyyppinen, staattinen muuttuja, jonka avulla voidaan
	 * vaihtaa aanenvoimakkuutta (Eli talla hetkella vaimentaa aani kokonaan)
	 */
	public static Volume volume = Volume.ON;

	private Clip clip;

	// Soundin konstruktori, jossa ladataan parametrina annettu aanitiedosto
	// clipiksi
	private Sound(String soundFile) {
		File tiedosto = new File(soundFile);
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(tiedosto);

			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Soittaa clipin, jolla tata metodia on kutsuttu
	 * 
	 * @param loop
	 *            Jos loop-arvo on true, clippia loopataan loputtomasti, muuten
	 *            se soitetaan vain kerran
	 */
	public void play(boolean loop) {
		// varmistaa, etta muten ollessa paalla, clippia ei soiteta
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) {
				clip.stop();
			}
			// kelataan alkuun ennen uutta soittoa
			clip.setFramePosition(0);
			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.start();
			}
		}
	}

	/**
	 * Lopettaa clipin soittamisen
	 */
	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
	}

	/**
	 * Antaa tiedon clipin nykyisesta tilasta, eli soitetaanko sita kyseisella
	 * hetkella
	 * 
	 * @return true, jos clippi soi, muuten false
	 */
	public boolean isRunning() {
		if (clip.isRunning()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Kutsuu luokan konstruktoria kaikilla aanilla, eli lataa jokaisen aanen
	 * valmiiksi klippiin, jotta niiden soittamisessa ei esiinny viiveita
	 */
	public static void init() {
		values();
	}
}
