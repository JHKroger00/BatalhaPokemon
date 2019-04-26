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

public abstract class Event {
 
	 abstract public void action(Trainer t); 
	 abstract public String description(Trainer t);
}
