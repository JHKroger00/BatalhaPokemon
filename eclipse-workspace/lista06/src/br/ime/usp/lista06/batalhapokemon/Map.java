/*********************************************************/
/** 				                        **/
/** Autores: Joao Henrique de A. Kroger  NUSP: 10770109 **/
/**	     Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercicio 02                             **/
/**                                                     **/
/*********************************************************/

package br.ime.usp.lista06.batalhapokemon;

import java.lang.System;
import java.util.Random;

//Run -> Run configurations -> Common -> Other ->UTF-8
public class Map {
	private int[] sizeOfMap = {13, 13}; //size of the map
	private final char tS = '\u26F9'; //Trainer symbol
	private final char gS = '\u2F8B'; //Grass symbol
	private final char rS = '\u2317'; //Road symbol
	private final int probability = 8;
	
	
	private final char[][] originalMap = {
								  {gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, rS, rS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, rS, rS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS},
								  {gS, rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS, gS},
								  {gS, rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS, gS},
								  {rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS, gS, gS}};
	
	private char[][] actualMap = {
								  {gS, gS, gS, gS, gS, tS, rS, gS, gS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, gS, gS, gS, gS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, rS, rS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, gS, rS, rS, rS, rS, rS, rS, gS, gS, gS, gS},
								  {gS, gS, rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS},
								  {gS, rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS, gS},
								  {gS, rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS, gS},
								  {rS, rS, rS, gS, gS, gS, gS, gS, gS, gS, gS, gS, gS}};
	
	public void printActualMap() {
		System.out.println();
		for (int i = 0; i < sizeOfMap[0]; i++) {
			for (int j = 0; j < sizeOfMap[1]; j++) {
				System.out.print(actualMap[i][j]);
			}
			System.out.println();
		}
	}
	
	public void printOriginalMap() {
		for (int i = 0; i < sizeOfMap[0]; i++) {
			for (int j = 0; j < sizeOfMap[1]; j++) {
				System.out.print(originalMap[i][j]);
			}
			System.out.println();
		}
	}
	
	public boolean move(Trainer t, char direction) {
		if(direction != 'w' && direction != 's' && direction != 'a' && direction != 'd')
			return false;
		else if ((direction == 'w' && t.getPosition()[0] == 0)
			||( direction == 's' && t.getPosition()[0] == sizeOfMap[0] - 1)
			||(direction == 'a' && t.getPosition()[1] == 0)
			||(direction == 'd' && t.getPosition()[1] == sizeOfMap[1] - 1)) {
			System.out.println(t.getName() + ", you tried to step out of the map!");
			return false;
		}
		else {
			int[] pos = t.getPosition();
			actualMap[pos[0]][pos[1]] = originalMap[pos[0]][pos[1]];
			t.walkInMap(direction);
			pos = t.getPosition();
			actualMap[pos[0]][pos[1]] = tS;
			return true;
		}
	}
	
	public boolean isGrass(int[] pos) {
		if (originalMap[pos[0]][pos[1]] == gS) 
			return true;
		else
			return false;
	}
	
	public boolean hasPokemon(Trainer t) {
		if (isGrass(t.getPosition())) {
			Random generator = new Random(System.currentTimeMillis());
			int rand = generator.nextInt(10);
			if (rand <= probability)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	static Pokemon getWildPokemon(PokemonList pl) {
		int numberOfPokemons = pl.names.length;
		Random generator = new Random(System.currentTimeMillis());
		int rand = generator.nextInt(numberOfPokemons);
		Pokemon wild = Pokemon.setPokemon(pl.names[rand] , pl);
		return wild;
	}
}
