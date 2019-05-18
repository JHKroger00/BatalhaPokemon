/*********************************************************/
/** 						        **/
/** Autores: Joao Henrique de A. Kroger  NUSP: 10770109 **/
/**          Bruno Macedo Sanches        NUSP: 10770263 **/
/**                                                     **/
/** Professor: Marcelo Finger                           **/
/** Lista 06 - Exercicio 01                             **/
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
	
	public Event getEvent(int i) {
		return events[i];
	}
	
}

public class Controller {
	public EventSet es = new EventSet();
	 
	public void setOptions(char opt) {
	}
	
	public void addEvent(Event c) { 
		es.add(c); 
	}  
	 
	public void run() { 
	}
	
	public void runFight() {
		
	}
}
