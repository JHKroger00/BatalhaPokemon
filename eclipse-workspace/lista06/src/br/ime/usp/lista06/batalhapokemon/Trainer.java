/*********************************************************/
/** 						                            **/
/** Autores: Joao Henrique de A. Kroger  NUSP: 10770109 **/
/**			 Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercicio 01                             **/
/**                                                     **/
/*********************************************************/

package br.ime.usp.lista06.batalhapokemon;

import java.util.Scanner;

public class Trainer {
	
	private String name;
	private int id;
	private Pokemon[] pokemon = new Pokemon[6];
	private int currentPokemon;
	private int numberOfPokemon;
	private int activePokemon;
	
	public Trainer(String name, int id, Pokemon[] Pokemons, int numberOfPokemon, int activePokemon) {
		this.name = name;
		this.id = id;
		for(int i = 0; i < 6; i++) {
			this.pokemon[i] = Pokemons[i];
		}
		this.numberOfPokemon = numberOfPokemon;
		this.activePokemon = activePokemon;
	}
	
	public  static Trainer createTrainer(int id, PokemonList pl) {
		Trainer t;
		Scanner sc = new Scanner(System.in);
		int i;
		
		System.out.println("Trainer " + id + ", what's your name?");
		String auxName = sc.nextLine();
		Pokemon[] auxPokemons = new Pokemon[6];
		
		for(i = 0; i < 6; i++) {
			System.out.println(auxName + ", choose your " + i + " Pokemon. Type 'none' if you don't want"
					+ "any more Pokemons.");
			
			String auxPokemonName = sc.nextLine();
			if(!auxPokemonName.equals("none") && !auxPokemonName.equals("None")) {
				if (Pokemon.setPokemon(auxPokemonName, pl) != null)
					auxPokemons[i] = Pokemon.setPokemon(auxPokemonName, pl);
				else {
					System.out.println("Chosen Pokemon is not available!");
					System.out.println("Choose another Pokemon!");
					i--;
				}
			}
			else {
				for(int j = i; j < 6; j++)
					auxPokemons[j] = null; //Creates a null Pokemon 
				break;
			}
		}
		t = new Trainer(auxName, id, auxPokemons, i, i);
		sc.close();
		return t;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public Pokemon getPokemon(int i) {
		return pokemon[i];
	}
	
	public Pokemon getCurrentPokemon() {
		return pokemon[currentPokemon];
	}
	
	public int getCurrent() {
		return currentPokemon;
	}
	
	public void setCurrent(int i) {
		currentPokemon = i;
	}
	
	public int getNumberOfPokemon() {
		return numberOfPokemon;
	}
	
	public void setNumberOfPokemon(int n) {
		this.numberOfPokemon = n;
	}
	
	public int getActivePokemon() {
		return activePokemon;
	}
}


