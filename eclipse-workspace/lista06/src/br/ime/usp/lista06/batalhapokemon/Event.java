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

public abstract class Event {
	
	abstract public void action(); 
	
	abstract public void description();
	
	public abstract char getName();

	public abstract int getPriority();
	 
	public void setTrainers(Trainer t1, Trainer t2) {
		// This method is used for the fight event
	}
	 
	public void setTrainer(Trainer t) {
		// This method is used for the other kinds of events
	}
	
	public void setBattle(Trainer t, Pokemon p) {
		// This method is used to set a battle against a wild pokémon
	}
	
	public void setPokemon(Pokemon p) {
		
	}
}
