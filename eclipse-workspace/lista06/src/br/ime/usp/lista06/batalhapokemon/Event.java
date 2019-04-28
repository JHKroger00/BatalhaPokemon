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
	 abstract public void action(); 
	 abstract public String description();
	 
	 public char getName() {
		 return name;
	 }
	 
	 public void setTrainers(Trainer t1, Trainer t2) {
		 //This class is only implemented in the Event class 'Fight'
	 }
	 
	 public void setTrainer(Trainer t) {
		 //This class is implemented in the Event classes 'Run', 'Item' and 'Switch'
	 }
}
