/*********************************************************/
/** 						        **/
/** Autores: Joao Henrique de A. Kroger  NUSP: 10770109 **/
/**	     Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercicio 01                             **/
/**                                                     **/
/*********************************************************/

package br.ime.usp.lista06.batalhapokemon;

public class Bag {
	private static final int potion = 20;
	private static final int superpotion = 50;
	private static final int hyperpotion = 200;
	
	public static void useItem(String item, Pokemon p) {
		int currentHP = p.getCurrentHP();
		int maxHP = p.getMaxHP();
		
		if (currentHP == maxHP) {
			System.out.println(p.getName() + "'s HP is full!");
			return;
		}
		
		else if (item.equals("potion")) {
			if (currentHP + potion < maxHP)
				p.setCurrentHP(currentHP + potion);
			else 
				p.setCurrentHP(maxHP);
			return;
		}
		
		else if (item.equals("super potion")) {
			if (currentHP + superpotion < maxHP)
				p.setCurrentHP(currentHP + superpotion);
			else 
				p.setCurrentHP(maxHP);
			return;
		}
	
		else if (item.equals("hyper potion")) {
			if (currentHP + hyperpotion < maxHP)
				p.setCurrentHP(currentHP + hyperpotion);
			else 
				p.setCurrentHP(maxHP);
			return;
		}
	}
	
	
}


