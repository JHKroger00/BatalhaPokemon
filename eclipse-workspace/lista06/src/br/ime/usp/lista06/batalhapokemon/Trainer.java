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
	
	public Trainer(String name, int id, Pokemon[] Pokemons) {
		this.name = name;
		this.id = id;
		for(int i = 0; i < 6; i++) {
			this.pokemon[i] = Pokemons[i];
		}
	}
	
	public  static Trainer createTrainer(int id) {
		Trainer t;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Trainer " + id + ", What's your name ?");
		String auxName = sc.nextLine();
		Pokemon[] auxPokemons = new Pokemon[6];
		
		for(int i = 0; i < 6; i++) {
			auxPokemons[i] = null;
			System.out.println(auxName + ", choose your " + i + " º pokemon, type none if you don't want"
					+ "any more pokemons." );
			
			String auxPokemonName = sc.nextLine();
			if(!auxPokemonName.equals("none")) {
				auxPokemons[i] = Pokemon.setPokemon(auxPokemonName);
			}
		}
		t = new Trainer(auxName, id, auxPokemons);
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
		return pokemon[i-1];
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
	
	public static void main(String[] args) {
		
	}
}


