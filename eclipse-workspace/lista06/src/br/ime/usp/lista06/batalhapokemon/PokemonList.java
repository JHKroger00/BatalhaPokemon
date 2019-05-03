package br.ime.usp.lista06.batalhapokemon;

import java.io.*;
import java.lang.Integer;

public class PokemonList {
	
	int pokemons;
	int i, j, k = 0;
	int[] dexNum;
	String[] names;
	String[][] types, attacks, attackType, category;
	int[][] typeNum, stats, attackTypeNum, power, priority; 
	
	public PokemonList() throws Exception {
		
		String absPath = new File("").getAbsolutePath();
		
		File file = new File(absPath + "/src/br/ime/usp/lista06/batalhapokemon/Pokemon.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
			
		String st = br.readLine();
		pokemons = Integer.parseInt(st);
		
		dexNum = new int[pokemons];
		names = new String[pokemons];
		types = new String[pokemons][2];
		typeNum = new int[pokemons][2]; 
		stats = new int[pokemons][6];
		attacks = new String[pokemons][4];
		attackType = new String[pokemons][4];
		attackTypeNum = new int[pokemons][4];
		category = new String[pokemons][4];
		power = new int[pokemons][4];
		priority = new int[pokemons][4];
			
		while ((st = br.readLine()) != null && i < pokemons) {
			if (!st.equals("")) {
				String[] info = st.split(" "); 
				dexNum[i] = Integer.parseInt(info[k++]);
				names[i] = info[k++];
				types[i][0] = info[k++];
				types[i][1] = info[k++];
				typeNum[i][0] = Integer.parseInt(info[k++]);
				typeNum[i][1] = Integer.parseInt(info[k++]);
				for(j = 0; j < 6; j++) 
					stats[i][j] = Integer.parseInt(info[k++]);
				for(j = 0; j < 4; j++) 
					attacks[i][j] = info[k++];
				for(j = 0; j < 4; j++) 
					attackType[i][j] = info[k++];
				for(j = 0; j < 4; j++) 
					attackTypeNum[i][j] = Integer.parseInt(info[k++]);
				for(j = 0; j < 4; j++) 
					category[i][j] = info[k++];
				for(j = 0; j < 4; j++) 
					power[i][j] = Integer.parseInt(info[k++]);
				for(j = 0; j < 4; j++) 
					priority[i][j] = Integer.parseInt(info[k++]);
				i++; k = 0;
			}
		}
		br.close();
	}
}
	

