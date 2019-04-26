/*********************************************************/
/** 						                            **/
/** Autores: Jo�o Henrique de A. Kr�ger  NUSP: 10770109 **/
/**			 Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exerc�cio 01                             **/
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
		public static final String name = "f";
		public static final int priority = 1;
		private Attack attack;
		private int a, damage;
		private boolean usable = false;
		
		public void action(Trainer t) {
		}
		
		public void action(Trainer attacker, Trainer defender) {
			System.out.println("Which move should " + attacker.pokemon[attacker.getCurrent()].getName() + " use?");
			for(int i = 0; i < 4; i++) {
				System.out.println((i+1) + attacker.pokemon[attacker.getCurrent()].attack[i].getName());
			}
			
			Scanner sc = new Scanner(System.in);
			a = sc.nextInt();
			
			while(!usable) {
				if (a < 1 || a > 4) {
					System.out.println("Chosen move does not exist!");
					System.out.println("Choose another move!");
					a = sc.nextInt();
				}
				else {
					attack = attacker.pokemon[currentPokemon].attack[a-1];
					damage = attacker.pokemon[attacker.getCurrent()].attackPokemon(attack, defender.pokemon[defender.getCurrent()]);
					defender.pokemon[defender.getCurrent()].takeDamage(damage);
				}
			}		
		}
		
		public String description(Trainer t) {
			return t.pokemon[currentPokemon].getName() + "used " + attack.getName() + "!";
		}
		
	}
	
	private class Switch extends Event {
		public static final String name = "s";
		public static final int priority = 3;
		private int n, p;
		private boolean usable = false;
		
		public void action(Trainer t) {
			System.out.println("Choose the Pokemon to switch in");
			for (int i = 0; i < 6 && t.pokemon[i] != null; i++) {
				System.out.println((i+1) + " - " + t.pokemon[i].getName() + " HP: " + t.pokemon[i].getCurrentHP() 
						+ "/" + t.pokemon[i].getMaxHP());
				n = i+1;
			}
			
			Scanner sc = new Scanner(System.in);
			p = sc.nextInt();
			
			while (!usable) {
				if(p == (t.currentPokemon+1)) {
					System.out.println(t.pokemon[t.getCurrent()].getName() + "is already in battle!");
					System.out.println("Choose another Pokemon!");
					p = sc.nextInt();
				}
				else if(p < 1 || p > n) {
					System.out.println("Chosen Pokemon does not exist!");
					System.out.println("Choose another Pokemon!");
					p = sc.nextInt();
				}
				else if(t.pokemon[p-1].getCurrentHP() == 0) {
					System.out.println(t.pokemon[p-1].getName() + "is fainted!");
					System.out.println("Choose another Pokemon!");
					p = sc.nextInt();
				}
				else {
					currentPokemon = p-1;
					usable = true;
				}
			}
		}
		
		public String description(Trainer t) {
			return t.pokemon[t.getCurrent()].getName() + "was sent out!";
		}
	}
	
	private class Item extends Event {
		public static final String name = "i";
		public static final int priority = 2;
		public int item;
		
		public void action(Trainer t) {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Choose item:");
			System.out.println("1 - Potion");
			System.out.println("2 - Superpotion");
			System.out.println("3 - Hyperpotion");
			
			item = sc.nextInt();
			
			switch(item) {
				case 1 : Bag.useItem("potion", pokemon[t.getCurrent()]);
					break;
				case 2 : Bag.useItem("superpotion", pokemon[t.getCurrent()]);
					break;
				case 3 : Bag.useItem("hyperpotion", pokemon[t.getCurrent()]);
					break;
			}
		}
		
		public String description(Trainer t) {
			switch(item) {
			case 1 : 
					 return(t.getName() + "used a potion!");
			case 2 :
					 return(t.getName() + "used a superpotion!");
			case 3 :
					 return(t.getName() + "used a hyperpotion!");
			default :
					 return("Chosen item not available");
			}
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


