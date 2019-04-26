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

public class Pokemon {
	
	private String name;
	private String species;
	private int dexNum;
	private String type1;
	private String type2;
	private final int maxHP;
	private int currentHP;
	private Attack[] attack = new Attack[4];
	
	public Pokemon (String name, String species, int dexNum, String type1, String type2, int HP,
			 		Attack attack1,  Attack attack2,  Attack attack3,  Attack attack4) {
		this.name = name;
		this.species = species;
		this.dexNum = dexNum;
		this.type1 = type1;
		this.type2 = type2;
		this.maxHP = HP;
		this.currentHP = maxHP;
		this.attack[0] = attack1;
		this.attack[1] = attack2;
		this.attack[2] = attack3;
		this.attack[3] = attack4;
	}
	
	public String getName() {
		return name; 
	}
	
	public String getSpecies() {
		return species; 
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
	
	public void attack(Attack attack) {
		System.out.println(name + "used" + attack.getName() + "!");
	}
	
}

class Attack {
	
	private String name;
	private String type;
	private int damage;
	private int priority;
	
	public Attack(String name, String type, int damage, int priority) {
		this.name = name;
		this.type = type;
		this.damage = damage;
		this.priority = priority;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getPriority() {
		return priority;
	}
	
}
