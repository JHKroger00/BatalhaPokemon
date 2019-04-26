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
 
	 abstract public void action(Trainer t); 
	 abstract public String description(Trainer t);
}
