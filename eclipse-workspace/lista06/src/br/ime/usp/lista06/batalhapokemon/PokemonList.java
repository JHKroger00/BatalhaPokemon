package br.ime.usp.lista06.batalhapokemon;

import java.io.*;
import java.lang.Integer;

public class PokemonList {
	
	int pokemons = 3;
	int i, j, k = 0;
	int[] dexNum = new int[pokemons];
	String[] names = new String[pokemons];
	String[][] types = new String[pokemons][2];
	int[][] typeNum = new int[pokemons][2]; 
	int[][] stats = new int[pokemons][6];
	String[][] attacks = new String[pokemons][4];
	String[][] attackType = new String[pokemons][4];
	int[][] attackTypeNum = new int[pokemons][4];
	String[][] category = new String[pokemons][4];
	int[][] power = new int[pokemons][4];
	
	public PokemonList() throws Exception {
		File file = new File("C:\\Users\\Finder Telecom\\eclipse-workspace\\Lista06\\src\\br\\ime\\usp\\mac0321\\BatalhaPokemon\\Pokemon.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String st;
				
		while ((st = br.readLine()) != null) {
			if (!st.equals("")) {
				String[] info = st.split(" "); 
				dexNum[i] = Integer.parseInt(info[0]);
				names[i] = info[1];
				types[i][0] = info[2];
				types[i][1] = info[3];
				typeNum[i][0] = Integer.parseInt(info[4]);
				typeNum[i][1] = Integer.parseInt(info[5]);
				for(j = 0, k = 6; j < 6; j++, k++) 
					stats[i][j] = Integer.parseInt(info[k]);
				for(j = 0; j < 4; j++, k++) 
					attacks[i][j] = info[k];
				for(j = 0; j < 4; j++, k++) 
					attackType[i][j] = info[k];
				for(j = 0; j < 4; j++, k++) 
					attackTypeNum[i][j] = Integer.parseInt(info[k]);
				for(j = 0; j < 4; j++, k++) 
					category[i][j] = info[k];
				for(j = 0; j < 4; j++, k++) 
					power[i][j] = Integer.parseInt(info[k]);
				i++;
			}
		}
		br.close();
	}
		
/**		public static void main (String[] args) throws Exception {
			PokemonList pl = new PokemonList();
			
			System.out.println(pl.names[0]);
			System.out.println(pl.names[1]);
			System.out.println(pl.names[2]);
	}                                                                 **/
}
	

