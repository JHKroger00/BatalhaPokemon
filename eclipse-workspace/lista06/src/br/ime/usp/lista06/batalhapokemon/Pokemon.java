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

public class Pokemon {
	private final String name;
	private final int dexNum;
	private final String type1, type2;
	private final int type1Num, type2Num;
	private final int maxHP, atk, def, spatk, spdef, spe;
	private int currentHP;
	private final int catchRate;
	public final Attack[] attack = new Attack[4];
	private boolean fainted = false;
	
	public Pokemon (String name, int dexNum, String type1, String type2, int type1Num, int type2Num, int HP,
					int atk, int def, int spatk, int spdef, int spe, int catchRate, Attack attack1, Attack attack2,  
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
		this.catchRate = catchRate;
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
	
	public int getCatchRate() {
		return catchRate;
	}
	
	public boolean fainted() {
		if(this.currentHP <= 0) {
			fainted = true;
		}
		return fainted;
	}
	
	public void revive() {
		fainted = false;
	}
	
	public int attackPokemon(Attack attack, Pokemon defender, TypeChart tc) {
		double modifier = tc.typeChart[attack.getTypeNum()][defender.getType1Num()]*
				          tc.typeChart[attack.getTypeNum()][defender.getType2Num()];
		double stab = 1;
		double damage;
		
		if((attack.getTypeNum() == type1Num) || (attack.getTypeNum() == type2Num)) {
			stab = 1.5;
		}
			
		if (attack.getCategory().equals("p")) {
			int power = attack.getPower();
			double atk = this.getAtk();
			double def = defender.getDef();
			damage = ((((22)*power*(atk/def))/50)+2)*modifier*stab;
			return (int)damage;
		}
		
		int power = attack.getPower();
		double spatk = this.getSpAtk();
		double spdef = defender.getSpDef(); 
		damage = ((((22)*power*(spatk/spdef))/50)+2)*modifier*stab;
		return (int)damage;
	}
	
	public void takeDamage(int damage) {
		this.currentHP -= damage;	
	}
	
	public static Pokemon setPokemon(String name, PokemonList pl) {
		Pokemon pokemon;
		Attack[] attacks = new Attack[4];
		int i;
		for(i = 0; i < pl.names.length && !(pl.names[i].equals(name)); i++);
		if (i < pl.names.length) {
			for(int j = 0; j < 4; j++) {
				attacks[j] = new Attack(pl.attacks[i][j], pl.attackType[i][j], pl.attackTypeNum[i][j], pl.category[i][j], 
										pl.power[i][j], pl.priority[i][j]);
			}
			pokemon = new Pokemon(pl.names[i], pl.dexNum[i], pl.types[i][0], pl.types[i][1], pl.attackTypeNum[i][0],
				  pl.attackTypeNum[i][1], pl.stats[i][0], pl.stats[i][1], pl.stats[i][2], pl.stats[i][3], pl.stats[i][4], 
				  pl.stats[i][5], pl.catchRate[i], attacks[0], attacks[1], attacks[2], attacks[3]);
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
	
	public boolean hurtsUser(Attack attack, Pokemon p, int damage) {
		String name = attack.getName();
		
		if(name.equals("TakeDown")) {
			p.takeDamage(damage/4);
			return true;
		}
		
		if(name.equals("DoubleEdge")) {
			p.takeDamage(damage/3);
			return true;
		}
		
		if(name.contentEquals("FlareBlitz")) {
			p.takeDamage(damage/3);
			return true;
		}
		return false;
	}
	
	public boolean healsUser(Attack attack, Pokemon p, int damage) {
		String name = attack.getName();
		
		if(name.equals("Absorb")) {
			p.setCurrentHP(p.getCurrentHP()+(damage/2));
			return true;
		}
		
		if(name.equals("MegaDrain")) {
			p.setCurrentHP(p.getCurrentHP()+(damage/2));
			return true;
		}
		
		if(name.equals("GigaDrain")) {
			p.setCurrentHP(p.getCurrentHP()+(damage/2));
			return true;
		}
		
		if(name.equals("LeechLife")) {
			p.setCurrentHP(p.getCurrentHP()+(damage/2));
			return true;
		}
		return false;
	}
}