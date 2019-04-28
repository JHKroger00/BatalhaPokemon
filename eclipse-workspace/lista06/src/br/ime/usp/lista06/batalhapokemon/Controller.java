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
	
	public Event getEvent(int i) {
		return events[i];
	}
	
}

public class Controller {
	 private EventSet es = new EventSet(); 
	 private Trainer t1, t2;
	 
	 public void setOptions(char opt) {
	 }
	 
	 public void addEvent(Event c) { 
		 es.add(c); 
	 }  
	 
	 public void run() { 
		 Event e;
		 for(int j = 0; j < 2; j++) {
			 e = es.getEvent(j);
			 if(j == 0) {
				 if(e.getName() == 'f') {
					 e.setTrainers(t1, t2);
				 }
				 else
					 e.setTrainer(t1);
				 e.action();
				 e.description();
			 }
			 else {
				 if(e.getName() == 'f') {
					 e.setTrainers(t2, t1);
				 }
				 else
					 e.setTrainer(t2);
				 e.action();
				 e.description();
			 }
			 
		 }
			 
	 } 
	 
} 
