package Controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import java.net.URL;

import ImportManager.ImportManager;
import Model.*;

public class Render {

	private Map<Character, BufferedImage> pics;

	/*HOW TO PLACE AN IMAGE INTO THE MAP
	 * 
	 *  character = 'NAME OF CHAR ASSOCIATED WITH THIS IMAGE';
	 * 	img = ImportManager.NAME_OF_IMAGE;
	 *	pics.put(character, img);
	 *  and that's it :D
	 * 
	 * */
	public Render() {
		pics = new HashMap<Character, BufferedImage>();
		Character character = new Character('[');
		BufferedImage img = null;
		//player img
		character = 'P';
		img = ImportManager.mario[0];
		pics.put(character, img);
		//este clase es muy importante
		//enemy img
		character = 'M';
		img = ImportManager.mush[0];
		pics.put(character, img);
		//platform img
		character = 'G';
		img = ImportManager.platform;
		pics.put(character, img);
		//box
		character = 'B';
		img = ImportManager.box;
		pics.put(character, img);
		//pwrbox
		character = 'A';
		img = ImportManager.pwrBox;
		pics.put(character, img);
		//fireflower
		character = 'f';//not to be confused with 'F' which is fireball
		img = ImportManager.flower;
		pics.put(character, img);

		character = 'F';
	}



	public BufferedImage getImage(char c){
		return pics.get(c);
	}


}