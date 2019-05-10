/*********************************************************/
/** 						                            **/
/** Autores: Joao Henrique de A. Kroger  NUSP: 10770109 **/
/**			 Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercicio 02                             **/
/**                                                     **/
/*********************************************************/

package br.ime.usp.lista06.batalhapokemon;

import java.util.Scanner;

public class WildBattleController extends Controller {
	public Trainer t1, t2;
	private TypeChart tc = new TypeChart();
	public static Scanner sc = new Scanner(System.in);
	
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
			if(e1.getName() == 'f') {
				e1.setTrainers(t1, t2);
				Fight f = (Fight)e1;
				f.setAttack(t1);
			}
			else {
				e1.setTrainer(t1);
			}
			if(e2.getName() == 'f') {
				e2.setTrainers(t2, t1);
				Fight f = (Fight)e2;
				f.setAttack(t2);
			}
			
			e1.action();
			e1.description();
			e2.action();
			e2.description();	
		}
		else if (e2.getPriority() > e1.getPriority()) {
			if(e1.getName() == 'f') {
				e2.setTrainers(t2, t1);
				Fight f = (Fight)e2;
				f.setAttack(t2);
			}
			else {
				e2.setTrainer(t2);
			}
			if(e1.getName() == 'f') {
				e1.setTrainers(t1, t2);
				Fight f = (Fight)e1;
				f.setAttack(t1);
			}
			else {
				e1.setTrainer(t1);
			}
			
			e2.action();
			e2.description();
			e1.action();
			e1.description();
		}
		else {
			e1.setTrainer(t1);
			e2.setTrainer(t2);
			
			e1.action();
			e1.description();
			e2.action();
			e2.description();	
		}
	}
	
	@Override
	public void runFight() {
		Fight e1 = (Fight)es.getEvent(0);
		Fight e2 = (Fight)es.getEvent(1);
		Attack a1, a2;
		
		a1 = e1.setAttack(t1);
		a2 = e2.setAttack(t2);
		
		e1.setTrainers(t1, t2);
		e2.setTrainers(t2, t1);
		
		if(a1.getPriority() > a2.getPriority()) {
			e1.action();
			e1.description();
			e2.action();
			e2.description();	
		}
		else if (a2.getPriority() > a1.getPriority()) {
			e2.action();
			e2.description();
			e1.action();
			e1.description();
		}
		else {
			if(t1.getCurrentPokemon().getSpe() > t2.getCurrentPokemon().getSpe()) {
				e1.action();
				e1.description();
				e2.action();
				e2.description();
			}
			else {
				e2.action();
				e2.description();
				e1.action();
				e1.description();
			}
		}		
	}
	
	class Fight extends Event {
		public final char name = 'f';
		public final int priority = 1;
		private Attack attack;
		private int a, damage;
		private boolean usable = false;
		private Trainer attacker, defender;
		
		public char getName() {
			return name;
		}
		
		public int getPriority() {
			return priority;
		}
		
		@Override
		public void setTrainers(Trainer t1, Trainer t2) {
			this.attacker = t1;
			this.defender = t2;
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
		
		public void action() {
			Pokemon currentAttacker = attacker.getCurrentPokemon();
			Pokemon currentDefender = defender.getCurrentPokemon();
			attack = currentAttacker.attack[a-1];
			
			damage = currentAttacker.attackPokemon(attack, currentDefender, tc); 
			currentDefender.takeDamage(damage);
			attack.hurtsUser(attack, currentAttacker, damage);
			attack.healsUser(attack, currentAttacker, damage);
		}
		
		public void description() {
			System.out.println(attacker.getCurrentPokemon().getName() + " used " + attack.getName() + "!");
		}
		
	}
	
	private class Switch extends Event {
		public final char name = 's';
		public final int priority = 3;
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
					System.out.println(t.getPokemon(p-1).getName() + " fainted!");
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
		public final char name = 'i';
		public final int priority = 2;
		public int item, p;
		private int pokemon;
		private boolean usable, available;
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
			
			System.out.println("Choose item:");
			System.out.println("1 - Potion");
			System.out.println("2 - Super potion");
			System.out.println("3 - Hyper potion");
			
			item = getInt(sc);
			
			System.out.println("Which pokemon should be healed?");
			for(int i = 0; i < t.getNumberOfPokemon(); i++) {
				System.out.println((i+1) + " " +  t.getPokemon(i).getName());
			}
			
			pokemon = getInt(sc);
			
			while(!available) {
				if (pokemon > 0 && pokemon <= t.getNumberOfPokemon()) {
					if(!t.getPokemon(pokemon - 1).fainted()) {
						p = pokemon -1;
						available = true;
					}
					else {
						System.out.println("Chosen Pokemon cannot battle anymore!");
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
			
			while (!usable) {
				if (item == 1) {
					Bag.useItem("potion", t.getPokemon(p));
					usable = true;
				}
				else if (item == 2) {
					Bag.useItem("super potion", t.getPokemon(p));
					usable = true;
				}
				else if (item == 3) {
					Bag.useItem("hyper potion", t.getPokemon(p));
					usable = true;
				}
				else {
					System.out.println("Chosen item not available!");
					System.out.println("Choose another item!");
					item = getInt(sc);
				}
			}
		}
		
		public void description() {
			switch(item) {
			case 1 : 
				System.out.println(t.getName() + " used a potion!");
			case 2 :
				System.out.println(t.getName() + " used a superpotion!");
			case 3 :
				System.out.println(t.getName() + " used a hyperpotion!");
			}
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
			System.out.println("Trainer " + t.getName() + " ran away safely!");
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
		System.out.println("f - attack the opponent");
		System.out.println("s - switch your pokemon");
		System.out.println("i - use a healing potion");
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
		default:
			this.addEvent(new nullEvent());
		}
	}
}
