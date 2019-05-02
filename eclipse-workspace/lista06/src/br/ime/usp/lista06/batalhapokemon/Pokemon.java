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

public class Pokemon {
	private String name;
	private int dexNum;
	private String type1;
	private String type2;
	private int type1Num;
	private int type2Num;
	private final int maxHP;
	private int currentHP;
	private final int atk;
	private final int def;
	private final int spatk;
	private final int spdef;
	private final int spe;
	public final Attack[] attack = new Attack[4];
	private boolean fainted = false;
	
	public Pokemon (String name, int dexNum, String type1, String type2, int type1Num, int type2Num, int HP,
					int atk, int def, int spatk, int spdef, int spe, Attack attack1, Attack attack2,  
					Attack attack3,  Attack attack4) {
		this.name = name;
		this.dexNum = dexNum;
		this.type1 = type1;
		this.type2 = type2;
		this.type1Num = type1Num;
		this.type2Num = type2Num;
		this.maxHP = HP;
		this.currentHP = maxHP;
		this.atk = atk;
		this.def = def;
		this.spatk = spatk;
		this.spdef = spdef;
		this.spe = spe;
		this.attack[0] = attack1;
		this.attack[1] = attack2;
		this.attack[2] = attack3;
		this.attack[3] = attack4;
	}
	
	public String getName() {
		return name; 
	}
	
	public int getDexNum() {
		return dexNum;
	}
	
	public String getType1() {
		return type1;
	}
	
	public String getType2() {
		return type2;
	}
	
	public int getType1Num() {
		return type1Num;
	}
	
	public int getType2Num() {
		return type2Num;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	protected void setCurrentHP(int HP) {
		this.currentHP = HP;
	}
	
	public int getAtk() {
		return atk;
	}
	
	public int getDef() {
		return def;
	}
	
	public int getSpAtk( ) {
		return spatk;
	}
	
	public int getSpDef() {
		return spdef;
	}
	
	public int getSpe() {
		return spe;
	}
	
	public boolean fainted() {
		if(this.currentHP <= 0) {
			System.out.println(this.name + " fainted!");
			fainted = true;
		}
		return fainted;
	}
	
	private boolean comparesDouble (double x, double y) {
		
		double epsilon = 0.01;
		
		if (x-y < epsilon && y-x < epsilon) 
			return true;
		
		return false;
	}
	
	public int attackPokemon(Attack attack, Pokemon defender, TypeChart tc) {
		double modifier = tc.typeChart[attack.getTypeNum()][defender.getType1Num()]*
				          tc.typeChart[attack.getTypeNum()][defender.getType2Num()];
		double damage;
		
		if(comparesDouble(modifier, 0.00)) {
			System.out.println(attack.getName() + "does not affect" + defender.getName() + ".");
		}
		
		if(comparesDouble(modifier, 0.25) || comparesDouble(modifier, 0.50)) {
			System.out.println("It's not very effective...");
		}
		
		if(comparesDouble(modifier, 2.00) || comparesDouble(modifier, 4.00)) {
			System.out.println("It's super effective!");
		}
		
		if (attack.getCategory().equals("p")) {
			int power = attack.getPower();
			int atk = this.getAtk();
			int def = defender.getDef();
			damage = ((((42)*power*(atk/def))/50)+2)*modifier;
			return (int)damage;
		}
		
		int power = attack.getPower();
		int spatk = this.getSpAtk();
		int spdef = defender.getSpDef(); 
		damage = ((((42)*power*(spatk/spdef))/50)+2)*modifier;
		return (int)damage;
	}
	
	public void takeDamage(int damage) {
		this.currentHP -= damage;	
	}
	
	public static Pokemon setPokemon(String name, PokemonList pl) {
		Pokemon pokemon;
		Attack[] attacks = new Attack[4];
		int i;
		for(i = 0; !(pl.names[i].equals(name)) && i < pl.names.length; i++);
		if (i < pl.names.length) {
			for(int j = 0; j < 4; j++) {
				attacks[j] = new Attack(pl.attacks[i][j], pl.attackType[i][j], pl.attackTypeNum[i][j], pl.category[i][j], 
										pl.power[i][j], pl.priority[i][j]);
			}
			pokemon = new Pokemon(pl.names[i], pl.dexNum[i], pl.types[i][0], pl.types[i][1], pl.attackTypeNum[i][0],
				  pl.attackTypeNum[i][1], pl.stats[i][0], pl.stats[i][1], pl.stats[i][2], pl.stats[i][3], pl.stats[i][4], 
				  pl.stats[i][5], attacks[0], attacks[1], attacks[2], attacks[3]);
			return pokemon;
		}
		return null;
	}	
}

class Attack {
	
	private String name;
	private String type;
	private int typeNum;
	private String category;
	private int power;
	private int priority;
	
	public Attack(String name, String type, int typeNum, String category, int power, int priority) {
		this.name = name;
		this.type = type;
		this.typeNum = typeNum;
		this.category = category;
		this.power = power;
		this.priority = priority;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getTypeNum() {
		return typeNum;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getPower() {
		return power;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void hurtsUser(Attack attack, Pokemon attacker, Pokemon defender, TypeChart tc) {
		String name = attack.getName();
		int damage = attacker.attackPokemon(attack, defender, tc);
		
		if(name.equals("TakeDown")) {
			attacker.takeDamage(damage/4);
			System.out.println(attacker.getName() + "lost some of its HP due to recoil");
		}
		
		if(name.equals("DoubleEdge")) {
			attacker.takeDamage(damage/3);
			System.out.println(attacker.getName() + "lost some of its HP due to recoil");
		}
	}
	
	public void healsUser(Attack attack, Pokemon attacker, Pokemon defender, TypeChart tc) {
		String name = attack.getName();
		int damage = attacker.attackPokemon(attack, defender, tc);
		
		if(name.equals("Absorb")) {
			attacker.setCurrentHP(attacker.getCurrentHP()+(damage/2));
			System.out.println(attacker.getName() + "restored some of its HP");
		}
		
		if(name.equals("MegaDrain")) {
			attacker.setCurrentHP(attacker.getCurrentHP()+(damage/2));
			System.out.println(attacker.getName() + "restored some of its HP");
		}
		
		if(name.equals("GigaDrain")) {
			attacker.setCurrentHP(attacker.getCurrentHP()+(damage/2));
			System.out.println(attacker.getName() + "restored some of its HP");
		}
	}
}