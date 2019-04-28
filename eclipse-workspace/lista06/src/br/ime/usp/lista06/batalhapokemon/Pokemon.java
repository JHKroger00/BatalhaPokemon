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
	private String species;
	private int dexNum;
	private String type1;
	private String type2;
	private final int maxHP;
	private int currentHP;
	private final int atk;
	private final int def;
	private final int spatk;
	private final int spdef;
	private final int spe;
	public final Attack[] attack = new Attack[4];
	
	public Pokemon (String name, int dexNum, String type1, String type2, int HP,
					int atk, int def, int spatk, int spdef, int spe, Attack attack1,  Attack attack2,  
					Attack attack3,  Attack attack4) {
		this.name = name;
		this.dexNum = dexNum;
		this.type1 = type1;
		this.type2 = type2;
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
	
	public int attackPokemon(Attack attack, Pokemon attacker, Pokemon defender) {
		if (attack.getCategory().equals("p")) {
			int power = attack.getPower();
			int atk = attacker.getAtk();
			int def = defender.getDef();
			return(((42)*power*(atk/def))/50)+2;
		}
		
		int power = attack.getPower();
		int spatk = attacker.getAtk();
		int spdef = defender.getSpDef(); 
		return(((42)*power*(spatk/spdef))/50)+2;	
	}
	
	public void takeDamage(int damage) {
		this.currentHP -= damage;
		
	}
	
	public static Pokemon setPokemon(String name) {
		//Procura o pokemon na lista, chama o construtor e devolve o pokemon
	}
	
}

class Attack {
	
	private String name;
	private String type;
	private String category;
	private int power;
	private int priority;
	
	public Attack(String name, String type, String category, int damage, int priority) {
		this.name = name;
		this.type = type;
		this.category = category;
		this.power = damage;
		this.priority = priority;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
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
	
	public void hurtsUser(Attack attack, Pokemon attacker, Pokemon defender) {
		String name = attack.getName();
		int damage = attacker.attackPokemon(attack, attacker, defender);
		
		if(name.equals("TakeDown")) {
			attacker.takeDamage(damage/4);
			System.out.println(attacker.getName() + "lost some of its HP due to recoil");
		}
		
		if(name.equals("DoubleEdge")) {
			attacker.takeDamage(damage/3);
			System.out.println(attacker.getName() + "lost some of its HP due to recoil");
		}
	}
	
	public void healsUser(Attack attack, Pokemon attacker, Pokemon defender) {
		String name = attack.getName();
		int damage = attacker.attackPokemon(attack, attacker, defender);
		
		if(name.equals("Absorb")) {
			attacker.setCurrentHP(attacker.getCurrentHP()+(damage/2));
			System.out.println(attacker.getName() + "restored its HP");
		}
		
		if(name.equals("MegaDrain")) {
			attacker.setCurrentHP(attacker.getCurrentHP()+(damage/2));
			System.out.println(attacker.getName() + "restored its HP");
		}
		
		if(name.equals("GigaDrain")) {
			attacker.setCurrentHP(attacker.getCurrentHP()+(damage/2));
			System.out.println(attacker.getName() + "restored its HP");
		}
	}
}