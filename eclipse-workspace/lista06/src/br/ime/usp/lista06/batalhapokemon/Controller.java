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

class EventSet {
	private Event[] events = new Event[2];
	private int i = 0;
	
	public void add(Event e) {
		if (i >= events.length)
			i = 0;
		events[i++] = e;
	}	
}

public class Controller {
	 private EventSet es = new EventSet(); 
	 
	 public void addEvent(Event c) { 
		 es.add(c); 
	 }  
	 
	 public void run(Event e, Trainer t) { 		 
		 e.action(t); 
		 System.out.println(e.description(t));   
	 } 
	 
	 public void run(Event e, Trainer t1, Trainer t2 ) {
		 action(t1, t2);
		 System.out.println(e.description(t1));
	 }
} 
