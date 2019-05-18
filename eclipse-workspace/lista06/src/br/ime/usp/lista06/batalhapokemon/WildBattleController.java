/*********************************************************/
/** 					                **/
/** Autores: Joao Henrique de A. Kroger  NUSP: 10770109 **/
/**	     Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercicio 02                             **/
/**                                                     **/
/*********************************************************/

package br.ime.usp.lista06.batalhapokemon;

import java.util.Scanner;
import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;

public class WildBattleController extends Controller {
	public Trainer t;
	public Pokemon wildPokemon;
	private TypeChart tc = new TypeChart();
	private Map map = new Map();
	public static Scanner sc = new Scanner(System.in);
	public boolean caught = false;
	
	public static int getInt(Scanner s) {
		while(!s.hasNextInt()) {
			System.out.println("This is not an available option! Select another one!");
			s.next();
		}
		return s.nextInt();
	}
	
	@Override
	public void run() { 
		Event e1 = es.getEvent(0);
		Event e2 = es.getEvent(1);
		
		if(e1.getPriority() > e2.getPriority()) {
			e1.setTrainer(t);
			if(e1.getName() == 'c')
				e1.setPokemon(wildPokemon);
			
			if(e2.getName() != 'n') {
				e2.setBattle(t, wildPokemon);
				Fight f = (Fight)e2;
				f.setAttack(wildPokemon);
			}
			
			if(e1.getName() == 'r' || e2.getName() == 'n') {
				e1.action();
				e1.description();
			}
			else {
				e1.action();
				e1.description();
				if(!caught) {
					((Fight)e2).action(wildPokemon);
					((Fight)e2).description(wildPokemon);
				}
			}
		}
		else if (e2.getPriority() > e1.getPriority()) {			
			if(e1.getName() == 'f') {
				e1.setBattle(t, wildPokemon);
				Fight f = (Fight)e1;
				f.setAttack(t);
			}
			else {
				e1.setTrainer(t);
				if(e1.getName() == 'c')
					e1.setPokemon(wildPokemon);
			}
			
			((Run)e2).action();
			((Run)e2).description(wildPokemon);
		}
		else {
			e1.setTrainer(t);
			
			e1.action();
			e1.description();
		}
	}
	
	@Override
	public void runFight() {
		Fight e1 = (Fight)es.getEvent(0);
		Fight e2 = (Fight)es.getEvent(1);
		Attack a1, a2;
		
		a1 = e1.setAttack(t);
		a2 = e2.setAttack(wildPokemon);
		
		e1.setBattle(t, wildPokemon);
		e2.setBattle(t, wildPokemon);
		
		if(a1.getPriority() > a2.getPriority()) {
			e1.action();
			e1.description();
			if(!wildPokemon.fainted()) {
				((Fight)e2).action(wildPokemon);
				((Fight)e2).description(wildPokemon);
			}	
		}
		else if (a2.getPriority() > a1.getPriority()) {
			((Fight)e2).action(wildPokemon);
			((Fight)e2).description(wildPokemon);
			if(!t.getCurrentPokemon().fainted()) {
				e1.action();
				e1.description();
			}
		}
		else {
			if(t.getCurrentPokemon().getSpe() > wildPokemon.getSpe()) {
				e1.action();
				e1.description();
				if(!wildPokemon.fainted()) {
					((Fight)e2).action(wildPokemon);
					((Fight)e2).description(wildPokemon);
				}
			}
			else {
				((Fight)e2).action(wildPokemon);
				((Fight)e2).description(wildPokemon);
				if(!t.getCurrentPokemon().fainted()) {
					e1.action();
					e1.description();
				}
			}
		}		
	}
	
	class Fight extends Event {
		private final char name = 'f';
		private final int priority = 1;
		private Attack attack;
		private int a, damage;
		private double modifier;
		private boolean usable = false;
		private Trainer t;
		private Pokemon wildPokemon;
		
		public char getName() {
			return name;
		}
		
		public int getPriority() {
			return priority;
		}
		
		private boolean comparesDouble (double x, double y) {
			double epsilon = 0.1;
			
			if (x-y < epsilon && y-x < epsilon) 
				return true;
			return false;
		}
		
		@Override
		public void setBattle(Trainer t, Pokemon p) {
			this.t = t;
			this.wildPokemon = p;
		}
		
		public Attack setAttack(Trainer t) {
			System.out.println("Which move should " + t.getCurrentPokemon().getName() + " use?");
			for(int i = 0; i < 4; i++) {
				System.out.println((i+1) + " " + t.getCurrentPokemon().attack[i].getName());
			}
			
			a = getInt(sc);
			
			while(!usable) {
				if (a >= 1 && a <= 4)
					usable = true;
				else {
					System.out.println("Chosen move does not exist!");
					System.out.println("Choose another move!");
					a = getInt(sc);
				}
			}
			this.attack = t.getCurrentPokemon().attack[a-1];
			return this.attack;
		}
		
		public Attack setAttack(Pokemon p) {
			a = ThreadLocalRandom.current().nextInt(0, 4);
			this.attack = p.attack[a];
			return this.attack;
		}
		
		public void action() {
			Pokemon currentAttacker = t.getCurrentPokemon();
			attack = currentAttacker.attack[a-1];
			damage = currentAttacker.attackPokemon(attack, wildPokemon, tc); 
			wildPokemon.takeDamage(damage);
		}
		
		public void description() {
			modifier = tc.typeChart[attack.getTypeNum()][wildPokemon.getType1Num()]*
			          tc.typeChart[attack.getTypeNum()][wildPokemon.getType2Num()];
			
			System.out.println(t.getCurrentPokemon().getName() + " used " + attack.getName() + "!");
			
			if(comparesDouble(modifier, 0.0)) {
				System.out.println("It does not affect " + wildPokemon.getName() + "...");
			}
			
			if(comparesDouble(modifier, 0.25) || comparesDouble(modifier, 0.5)) {
				System.out.println("It's not very effective...");
			}
			
			if(comparesDouble(modifier, 2.0) || comparesDouble(modifier, 4.0)) {
				System.out.println("It's super effective!");
			}
			
			if(!comparesDouble(modifier, 0.0)) {
				if(attack.hurtsUser(attack, t.getCurrentPokemon(), damage)) {
					System.out.println(t.getCurrentPokemon().getName() + " lost some of its HP due to recoil");
				}
				else if(attack.healsUser(attack,  t.getCurrentPokemon(), damage)){
					System.out.println(t.getCurrentPokemon().getName() + " restored some of its HP");
				}
			}
		}
		
		public void action(Pokemon p) {			
			damage = p.attackPokemon(attack, t.getCurrentPokemon(), tc); 
			t.getCurrentPokemon().takeDamage(damage);
		}
		
		public void description(Pokemon p) {
			modifier = tc.typeChart[attack.getTypeNum()][t.getCurrentPokemon().getType1Num()]*
			          tc.typeChart[attack.getTypeNum()][t.getCurrentPokemon().getType2Num()];
			
			System.out.println(p.getName() + " used " + attack.getName() + "!");
			
			if(comparesDouble(modifier, 0.0)) {
				System.out.println("It does not affect " + t.getCurrentPokemon().getName() + "...");
			}
			
			if(comparesDouble(modifier, 0.25) || comparesDouble(modifier, 0.5)) {
				System.out.println("It's not very effective...");
			}
			
			if(comparesDouble(modifier, 2.0) || comparesDouble(modifier, 4.0)) {
				System.out.println("It's super effective!");
			}
			
			if(!comparesDouble(modifier, 0.0)) {
				if(attack.hurtsUser(attack, p, damage)) {
						System.out.println(p.getName() + " lost some of its HP due to recoil");
				}
				if(attack.healsUser(attack, p, damage)) {
					System.out.println(p.getName() + " restored some of its HP");
				}
			}
		}
	}
	
	private class Switch extends Event {
		private final char name = 's';
		private final int priority = 3;
		private int n, p;
		private boolean usable = false;
		private Trainer t;
		
		public char getName() {
			return name;
		}
		
		public int getPriority() {
			return priority;
		}
		
		@Override
		public void setTrainer(Trainer t) {
			this.t = t;
		}
		
		public void action() {
			System.out.println("Choose the Pokemon to switch in");
			for (int i = 0; i < t.getNumberOfPokemon() && t.getPokemon(i) != null; i++) {
				System.out.println((i+1) + " - " + t.getPokemon(i).getName() + " HP: " + t.getPokemon(i).getCurrentHP() 
									+ "/" + t.getPokemon(i).getMaxHP());
				n = i+1;
			}
			
			p = getInt(sc);
			
			while (!usable) {
				if(p == (t.getCurrent()+1)) {
					System.out.println(t.getCurrentPokemon().getName() + " is already in battle!");
					System.out.println("Choose another Pokemon!");
					p = getInt(sc);
				}
				else if(p < 1 || p > n) {
					System.out.println("Chosen Pokemon does not exist!");
					System.out.println("Choose another Pokemon!");
					p = getInt(sc);
				}
				else if(t.getPokemon(p-1).getCurrentHP() == 0) {
					System.out.println(t.getPokemon(p-1).getName() + " can't battle anymore!");
					System.out.println("Choose another Pokemon!");
					p = getInt(sc);
				}
				else {
					t.setCurrent(p-1);
					usable = true;
				}
			}	
		}
		
		public void description() {
			System.out.println(t.getCurrentPokemon().getName() + " was sent out!");
		}
	}
	
	private class Item extends Event {
		private final char name = 'i';
		private final int priority = 2;
		private int item, p;
		private int pokemon;
		private boolean available;
		private Trainer t;
		
		public char getName() {
			return name;
		}
		
		public int getPriority() {
			return priority;
		}
		
		@Override
		public void setTrainer(Trainer t) {
			this.t = t;
		}
		
		public void action() {
			
			System.out.println("Choose item:");
			System.out.println("1 - Potion");
			System.out.println("2 - Super potion");
			System.out.println("3 - Hyper potion");
			
			item = getInt(sc);
			
			while(item < 1 || item > 3) {
				System.out.println("Chosen item not available!");
				System.out.println("Choose another item!");
				item = getInt(sc);
			}
			
			System.out.println("Which pokemon should be healed?");
			for(int i = 0; i < t.getNumberOfPokemon(); i++) {
				System.out.println((i+1) + " " +  t.getPokemon(i).getName() + " HP: " + t.getPokemon(i).getCurrentHP() 
						+ "/" + t.getPokemon(i).getMaxHP());
			}
			
			pokemon = getInt(sc);
			
			while(!available) {
				if (pokemon > 0 && pokemon <= t.getNumberOfPokemon()) {
					if(!t.getPokemon(pokemon-1).fainted()) {
						if (t.getPokemon(pokemon-1).getCurrentHP() == t.getPokemon(pokemon-1).getMaxHP()){
							System.out.println(t.getPokemon(pokemon-1).getName() + "'s HP is full!");
							System.out.println("Choose another Pokemon");
							pokemon = getInt(sc);
						}
						else {
							p = pokemon-1;
							available = true;
						}
					}
					else {
						System.out.println("Chosen Pokemon can't battle anymore!");
						System.out.println("Choose another Pokemon");
						pokemon = getInt(sc);
					}		
				}
				else {
					System.out.println("Chosen Pokemon is not available!");
					System.out.println("Choose another Pokemon");
					pokemon = getInt(sc);
				}
			}
			
			if (item == 1) 
				Bag.useItem("potion", t.getPokemon(p));
			else if (item == 2) 
				Bag.useItem("super potion", t.getPokemon(p));
			else if (item == 3) 
				Bag.useItem("hyper potion", t.getPokemon(p));
		}
		
		public void description() {
			switch(item) {
			case 1 : 
				System.out.println(t.getName() + " used a potion on " + t.getPokemon(p).getName() + "!");
			case 2 :
				System.out.println(t.getName() + " used a superpotion on " + t.getPokemon(p).getName() + "!");
			case 3 :
				System.out.println(t.getName() + " used a hyperpotion on " + t.getPokemon(p).getName() + "!");
			}
		}	
	}
	
	private class Catch extends Event {
		private final char name = 'c';
		private final int priority = 2;
		private int b;
		private double ball;
		private Pokemon p;
		
		public char getName() {
			return name;
		}
		
		public int getPriority() {
			return priority;
		}
		
		@Override
		public void setPokemon(Pokemon p) {
			this.p = p;
		}
		
		public void action() {
			int a, m;
			
			System.out.println("Choose poke ball:");
			System.out.println("1 - Poke ball");
			System.out.println("2 - Great ball");
			System.out.println("3 - Ultra ball");
			
			b = getInt(sc);
			
			while(b < 1 || b > 3) {
				System.out.println("Chosen poke ball not available!");
				System.out.println("Choose another poke ball!");
				b = getInt(sc);
			}
			
			if (b == 1) 
				ball = 1.0;
			else if (b == 2)
				ball = 1.5;
			else 
				ball = 2.0;
			
			a = (int)Math.floor(((double)((3*p.getMaxHP())-(2*p.getCurrentHP()))*p.getCatchRate()*ball)/((double)3*p.getMaxHP()));
			m = ThreadLocalRandom.current().nextInt(0, 256);
			
			if(a >= m) 
				caught = true;
		}
		
		public void description() {
			if (caught)
				System.out.println("Gotcha! " + p.getName() + " was caught!");
			else
				System.out.println("Oh, no! The Pokemon broke free!");
		}	
	}
	
	private class Run extends Event {
		public final char name = 'r';
		public final int priority = 4;
		public Trainer t;
		
		public char getName() {
			return name;
		}
		
		public int getPriority() {
			return priority;
		}
		
		@Override
		public void setTrainer(Trainer t) {
			this.t = t;
		}
		
		public void action() {
			return;
		}
		
		public void description() {
			System.out.println(t.getName() + " ran away safely!");
		}
		
		public void description(Pokemon p) {
			System.out.println("The wild " + p.getName() + " fled away!");
		}
	}
	
	private class nullEvent extends Event {
		public final char name = 'n';
		public final int priority = 0;
		
		public char getName() {
			return name;
		}

		public int getPriority() {
			return priority;
		}
		
		public void action() {
			// This event does nothing 
		}
		
		public void description() {
		}
	}
	
	public static void printOptions() {
		System.out.println("f - attack the wild pokemon");
		System.out.println("s - switch your pokemon");
		System.out.println("i - use a healing potion");
		System.out.println("c - catch the wild pokemon");
		System.out.println("r - run away from battle");
	}
	
	public void setOptions(char opt) {
		switch(opt) {
		case 'r':
			this.addEvent(new Run());
			break;
		case 'i':
			this.addEvent(new Item());
			break;
		case 's':
			this.addEvent(new Switch());
			break;
		case 'f':
			this.addEvent(new Fight());
			break;
		case 'c':
			this.addEvent(new Catch());
			break;
		default:
			this.addEvent(new nullEvent());
		}
	}
	
	public static void main(String[] args) throws Exception {
		PokemonList pl = new PokemonList();
		char dir;
		boolean finished = false;
		
		WildBattleController bc = new WildBattleController();
		
		bc.t = Trainer.createTrainer(1, pl, 1);
		
		System.out.println("\nTrainer, use the following commands to move through the map:");
		System.out.println("w - move in the up direction");
		System.out.println("a - move in the left direction");
		System.out.println("s - move in the down direction");
		System.out.println("d - move in the right direction");
		System.out.println("q - quit your current adventure");
		
		bc.map.printActualMap();
		
		dir = sc.next().charAt(0);
		
		while(dir != 'w' && dir != 'a' && dir != 's' && dir != 'd' && dir != 'q') {
			System.out.println("This is not an available command! Select another one!");
			dir = sc.next().charAt(0);
		}
		
		while(dir != 'q') {
			if(!bc.map.move(bc.t, dir)) {
				System.out.println("This is not an available command! Select another one!");
				dir = sc.next().charAt(0);
			}
			
			else {
				bc.map.printActualMap();
				if(bc.map.hasPokemon(bc.t)) {
					bc.wildPokemon = Map.getWildPokemon(pl);
					System.out.println("A wild " + bc.wildPokemon.getName() + " appeared!");
					
					while(!finished) {
						char auxOpt1;
						char auxOpt2;
						Pokemon pokemon = bc.t.getCurrentPokemon();
						int move;
						
						System.out.println(bc.t.getName() + "'s " + pokemon.getName() + ": " 
								+ pokemon.getCurrentHP() + "/" + pokemon.getMaxHP() + " HP");
						
						System.out.println(bc.wildPokemon.getName() + ": " 
								+ bc.wildPokemon.getCurrentHP() + "/" + bc.wildPokemon.getMaxHP() + " HP");
						
						System.out.println("Trainer " + bc.t.getName() + ", choose your move:");
						printOptions();
						auxOpt1 = sc.next().charAt(0);
						
						while(auxOpt1 != 'f' && auxOpt1 != 's' && auxOpt1 != 'i' && auxOpt1 != 'r' && auxOpt1 != 'c') {
							System.out.println("This is not an available option! Select another one!");
							auxOpt1 = sc.next().charAt(0);
						}
						
						while(auxOpt1 == 's' && bc.t.getNumberOfPokemon() == 1) {
							System.out.println("Trainer " + bc.t.getName() + ", you only have one Pokemon!" +
												" You can't switch it out!");
							System.out.println("Select another option!");
							auxOpt1 = sc.next().charAt(0);
						}
						
						while(auxOpt1 == 'i') {
							boolean check = false;
							int p = 0;
							for(int i = 0; i < bc.t.getNumberOfPokemon(); i++) {
								if (bc.t.getPokemon(i).getCurrentHP() == bc.t.getPokemon(i).getMaxHP())
									p++;
							}
							if(p == bc.t.getActivePokemon()) {
								System.out.println("Trainer " + bc.t.getName() + ", all your active Pokemon are at max HP!" +
													" You can't use a potion on any of them!");
								System.out.println("Select another option!");
								auxOpt1 = sc.next().charAt(0);
								check = true;
							}
							if(!check)
								break;
						}
						
						move = ThreadLocalRandom.current().nextInt(0, 256);
						
						if(move < 255)
							auxOpt2 = 'f';
						else
							auxOpt2 = 'r';
						
						bc.setOptions(auxOpt1);
						bc.setOptions(auxOpt2);
						
						if (auxOpt1 == 'f' && auxOpt2 == 'f') 
							bc.runFight();
						else 	
							bc.run();
						
						if(bc.t.activePokemonFainted()) {
							System.out.println(bc.t.getCurrentPokemon().getName() + " fainted!");
							bc.t.getCurrentPokemon().setCurrentHP(0);
							
							if(bc.t.getActivePokemon() > 0) {
								System.out.println("Trainer " + bc.t.getName() + ", you need to choose another Pokemon!");
								bc.setOptions('s');
								bc.setOptions('0');
								bc.run();
							}
							else {
								finished = true;
								System.out.println(bc.t.getName() + " is out of usable Pokemon!");
								System.out.println(bc.t.getName() + " blacked out!");
								System.out.println(bc.t.getName() + " ushered to the nearest Pokemon Center, and was able to "
												   + "recover all the fainted Pokemon back to full health...");
							}
						}
						
						if(bc.wildPokemon.fainted()) {
							finished = true;
							System.out.println("The wild " + bc.wildPokemon.getName() + " fainted!");
						}
 						
						if(auxOpt1 == 'c') {
							if(bc.caught) {
								finished = true;
								if(bc.t.getNumberOfPokemon() == 6) {
									int answer, p;
									System.out.println("Trainer " + bc.t.getName() + ", your party is full!");
									System.out.println("Would you like to replace one of your current pokemon with the one you "
														+ "just caught?");
									System.out.println("1 - Yes");
									System.out.println("2 - No");
									answer = getInt(sc);
									
									while(answer < 1 || answer > 2) {
										System.out.println("Chosen option is not available!");
										System.out.println("Select another one!");
										answer = getInt(sc);
									}
									
									if(answer == 1) {
										System.out.println("Which pokemon should be replaced?");
										for(int i = 0; i < bc.t.getNumberOfPokemon(); i++) {
											System.out.println((i+1) + " " +  bc.t.getPokemon(i).getName());
										}
										p = getInt(sc);
										
										while(p < 1 || p > bc.t.getNumberOfPokemon()) {
											System.out.println("Chosen Pokemon is not available!");
											System.out.println("Choose another Pokemon");
											p = getInt(sc);
										}
										bc.t.setPokemon(bc.wildPokemon.getName(), p-1, pl);
										System.out.println(bc.wildPokemon.getName() + " was added to your party");
									}
									if(answer == 2) {
										System.out.println(bc.wildPokemon.getName() + " was transferred to the BOX in the PC");
									}
								}
								else {
									bc.t.setPokemon(bc.wildPokemon.getName(), bc.t.getNumberOfPokemon(), pl);
									bc.t.setNumberOfPokemon(bc.t.getNumberOfPokemon() + 1);
									System.out.println(bc.wildPokemon.getName() + " was added to your party");
								}
							}
							bc.caught = false;
						}
						
						if(auxOpt1 == 'r') {
							finished = true;
						}
						
						else if(auxOpt2 == 'r') {
							finished = true;
						}
					}
					for(int i = 0; i < bc.t.getNumberOfPokemon(); i++) {
						bc.t.getPokemon(i).setCurrentHP(bc.t.getPokemon(i).getMaxHP());
						bc.t.getPokemon(i).revive();
					}
					bc.t.setCurrent(0);
					bc.t.setActivePokemon(bc.t.getNumberOfPokemon());
					finished = false;
				}
				bc.map.printActualMap();
				dir = TrainerBattleController.sc.next().charAt(0);
			}
		}
		
		System.out.println("Trainer " + bc.t.getName() + ", your Pokemon adventure has ended!");
	}
}
