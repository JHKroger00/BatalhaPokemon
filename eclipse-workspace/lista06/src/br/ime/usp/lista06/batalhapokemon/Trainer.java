/*********************************************************/
/** 						                            **/
/** Autores: João Henrique de A. Kröger  NUSP: 10770109 **/
/**			 Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercício 01                             **/
/**                                                     **/
/*********************************************************/

package br.ime.usp.lista06.batalhapokemon;

import java.util.Scanner;

public class Trainer extends Controller {
	
	private String name;
	private int id;
	private Pokemon[] pokemon = new Pokemon[6];
	private int currentPokemon;
	
	public Trainer(String name, int id, Pokemon Pokemon1, Pokemon Pokemon2, Pokemon Pokemon3, 
			        Pokemon Pokemon4, Pokemon Pokemon5, Pokemon Pokemon6) {
		this.name = name;
		this.id = id;
		this.pokemon[0] = Pokemon1;
		this.pokemon[1] = Pokemon2;
		this.pokemon[2] = Pokemon3;
		this.pokemon[3] = Pokemon4;
		this.pokemon[4] = Pokemon5;
		this.pokemon[5] = Pokemon6;
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
	
	public int getCurrent() {
		return currentPokemon;
	}
	
	public void setCurrent(int i) {
		currentPokemon = i;
	}

	private class Fight extends Event {
		
		public void action(Trainer t) {
			
		}
		
		public String description(Trainer t) {
			
		}
		
	}
	
	private class Switch extends Event {
		
		public void action(Trainer t) {
			
		}
		
		public String description(Trainer t) {
			
		}
	}
	
	private class Item extends Event {
		public static final String name = "i";
		public static final int priority = 2;
		
		public void action(Trainer t) {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Choose item:");
			System.out.println("1 - Potion");
			System.out.println("2 - Superpotion");
			System.out.println("3 - Hyperpotion");
			
			int i = sc.nextInt();
			
			switch(i) {
				case 1 : Bag.useItem("potion", pokemon[currentPokemon]);
						 System.out.println(t.getName() + "used a potion!");
					break;
				case 2 : Bag.useItem("superpotion", pokemon[currentPokemon]);
						 System.out.println(t.getName() + "used a superpotion!");
					break;
				case 3 : Bag.useItem("hyperpotion", pokemon[currentPokemon]);
						 System.out.println(t.getName() + "used a hyperpotion!");
					break;
			}
		}
		
		public String description(Trainer t) {
			return " ";
		}
		
	}
	
	private class Run extends Event {
		public static final String name = "r";
		public static final int priority = 4;
		
		public void action(Trainer t) {
			return;
		}
		
		public String description(Trainer t) {
			return  t.getName() + " ran away safely!";
		}
	}
	
	private class EventSelect extends Event {
		
	}
}


