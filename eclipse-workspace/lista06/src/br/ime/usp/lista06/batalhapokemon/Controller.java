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
			}
			else {
				if(e.getName() == 'f') {
					e.setTrainers(t2, t1);
				}
				else
					e.setTrainer(t2);
			}
		}
		Event e1 = es.getEvent(0);
		Event e2 = es.getEvent(1);
		if(e1.getPriority() >= e2.getPriority()) {
			e1.action();
			e1.description();
			e2.action();
			e2.description();	
		}
		else {
			e2.action();
			e2.description();
			e1.action();
			e1.description();
		}		
	}
	
	public void runFight() {
		
	}
}
