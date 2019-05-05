package br.ime.usp.lista06.batalhapokemon;

import java.util.Scanner;

public class BattleController extends Controller {
	public Trainer t1, t2;
	private TypeChart tc = new TypeChart();
	public static Scanner sc = new Scanner(System.in);
	
	public static int getInt(Scanner s) {
		while(!s.hasNextInt()) {
			System.out.println("Option not available, select another!");
			s.next();
		}
		return s.nextInt();
	}
	
	@Override
	public void run() { 
		Event e1 = es.getEvent(0);
		Event e2 = es.getEvent(1);
		System.out.println(e1.getPriority() + "event1" + e2.getPriority() + "event2");
		if(e1.getPriority() >= e2.getPriority()) {
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
		else {
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
		
		public void setTrainer(Trainer t) {
			
		}
		
		public Attack setAttack(Trainer t) {
			System.out.println("Which move should " + t.getCurrentPokemon().getName() + " use?");
			for(int i = 0; i < 4; i++) {
				System.out.println((i+1) + t.getCurrentPokemon().attack[i].getName());
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
			attack = attacker.getCurrentPokemon().attack[a-1];
			damage = attacker.getCurrentPokemon().attackPokemon(attack, defender.getCurrentPokemon(), tc); 
			defender.getCurrentPokemon().takeDamage(damage);
			attacker.getCurrentPokemon().attack[a-1].hurtsUser(attacker.getCurrentPokemon().attack[a-1], attacker.getCurrentPokemon(), damage);
			attacker.getCurrentPokemon().attack[a-1].healsUser(attacker.getCurrentPokemon().attack[a-1], attacker.getCurrentPokemon(), damage);
		}
		
		public String description() {
			return attacker.getCurrentPokemon().getName() + "used " + attack.getName() + "!";
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
		
		public void setTrainers(Trainer t1, Trainer t2) {
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
					System.out.println(t.getCurrentPokemon().getName() + "is already in battle!");
					System.out.println("Choose another Pokemon!");
					p = getInt(sc);
				}
				else if(p < 1 || p > n) {
					System.out.println("Chosen Pokemon does not exist!");
					System.out.println("Choose another Pokemon!");
					p = getInt(sc);
				}
				else if(t.getPokemon(p - 1).getCurrentHP() <= 0) {
					System.out.println(t.getPokemon(p - 1).getName() + "is fainted!");
					System.out.println("Choose another Pokemon!");
					p = getInt(sc);
				}
				else {
					t.setCurrent(p - 1);
					usable = true;
				}
			}
			
		}
		
		public String description() {
			return t.getCurrentPokemon().getName() + "was sent out!";
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
		public void setTrainers(Trainer t1, Trainer t2) {
		}
		
		public void action() {
			
			System.out.println("Choose item:");
			System.out.println("1 - Potion");
			System.out.println("2 - Super potion");
			System.out.println("3 - Hyper potion");
			
			item = getInt(sc);
			
			System.out.println("Which pokemon should be healed?");
			for(int i = 0; i < t.getNumberOfPokemon(); i++) {
				System.out.println((i+1) + t.getPokemon(i).getName());
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
		
		public String description() {
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
	
	private class nullEvent extends Event {
		public final char name = 'n';
		public final int priority = 0;
		
		@Override
		public void action() {
			// This event does nothing 
		}
		@Override
		public String description() {
			return " ";
		}
		@Override
		public char getName() {
			return name;
		}
		@Override
		public int getPriority() {
			return priority;
		}
		@Override
		public void setTrainers(Trainer t1, Trainer t2) {
		}
		@Override
		public void setTrainer(Trainer t) {
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
		public void setTrainers(Trainer t1, Trainer t2) {
		}
		public void action() {
			return;
		}
		
		public String description() {
			return  t.getName() + " ran away safely!";
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
	
	
	public static void main(String[] args) throws Exception {
		PokemonList pl = new PokemonList();
		boolean finished = false;
		
		BattleController bc = new BattleController();
		
		bc.t1 = Trainer.createTrainer(1, pl);
		bc.t2 = Trainer.createTrainer(2, pl);
		
		while(!finished) {
			char auxOpt1;
			char auxOpt2;
			Pokemon p1 = bc.t1.getCurrentPokemon();
			Pokemon p2 = bc.t2.getCurrentPokemon();
			
			System.out.println(bc.t1.getName() + "'s" + p1.getName() + ": " 
					+ p1.getCurrentHP() + "/" + p1.getMaxHP() + " HP");
			
			System.out.println(bc.t2.getName() + "'s" + p2.getName() + ": " 
					+ p2.getCurrentHP() + "/" + p2.getMaxHP() + " HP");
			
			System.out.println(bc.t1.getName() + "choose your move:");
			printOptions();
			auxOpt1 = sc.next().charAt(0);
			while(auxOpt1 != 'f' && auxOpt1 != 's' && auxOpt1 != 'i' && auxOpt1 != 'r' 
					|| (auxOpt1 == 's' && bc.t1.getNumberOfPokemon() == 1)) {
				System.out.println("This is not an available option! Select another one!");
				auxOpt1 = sc.next().charAt(0);
			}
			
			System.out.println(bc.t2.getName() + "choose your move:");
			printOptions();
			auxOpt2 = sc.next().charAt(0);
			while(auxOpt2 != 'f' && auxOpt2 != 's' && auxOpt2 != 'i' && auxOpt2 != 'r'
					|| (auxOpt2 == 's' && bc.t2.getNumberOfPokemon() == 1)) {
				System.out.println("This is not an available option! Select another one!");
				auxOpt2 = sc.next().charAt(0);
			}
			
			bc.setOptions(auxOpt1);
			bc.setOptions(auxOpt2);
			
			if (auxOpt1 == 'f' && auxOpt2 == 'f') {
				bc.runFight();
			}
			else 	
				bc.run();
			
			if(bc.t1.activePokemonFainted()) {
				if(bc.t1.getActivePokemon() > 0) {
					System.out.println(bc.t1.getName() + " you need to choose another pokemon!");
					bc.setOptions('s');
					bc.setOptions('0');
					bc.run();
				}
				else {
					finished = true;
					System.out.println("The fight is over, " + bc.t2.getName() + " won the battle!");
				}
			}
			if(bc.t2.activePokemonFainted()) {
				if(bc.t2.getActivePokemon() > 0) {
					System.out.println(bc.t2.getName() + " you need to choose another pokemon!");
					bc.setOptions('0');
					bc.setOptions('s');
					bc.run();
				}
				else {
					finished = true;
					System.out.println("The fight is over, " + bc.t1.getName() + " won the battle!");
				}
			}
			
			else if(auxOpt1 == 'r') {
				finished = true;
				System.out.println("The fight is over, " + bc.t2.getName() + " won the battle!");
			}
			else if(auxOpt2 == 'r') {
				finished = true;
				System.out.println("The fight is over, " + bc.t1.getName() + " won the battle!");
			}
		}
	}
}
