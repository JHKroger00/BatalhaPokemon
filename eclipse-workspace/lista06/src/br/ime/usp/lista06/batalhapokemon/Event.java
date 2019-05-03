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

public abstract class Event {
	
	public char name;
	public int priority;
	abstract public void action(); 
	abstract public String description();
	
	 public abstract char getName();

	 
	 public abstract int getPriority();
	 
	 public abstract void setTrainers(Trainer t1, Trainer t2);
	 
	 public abstract void setTrainer(Trainer t);
		 
}
