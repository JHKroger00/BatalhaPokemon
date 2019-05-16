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

public class Trainer {
	
	private String name;
	private int id;
	private Pokemon[] pokemon = new Pokemon[6];
	private int currentPokemon;
	private int numberOfPokemon;
	private int activePokemon;
	private int[] positionInMap = {0, 5};
	
	private Trainer(String name, int id, Pokemon[] Pokemons, int numberOfPokemon, int activePokemon) {
		this.name = name;
		this.id = id;
		for(int i = 0; i < 6; i++) {
			this.pokemon[i] = Pokemons[i];
		}
		this.numberOfPokemon = numberOfPokemon;
		this.activePokemon = activePokemon;
	}
	
	public  static Trainer createTrainer(int id, PokemonList pl, int opt) {
		Trainer t;
		int i;
		
		if (opt == 0) {
			System.out.println("Trainer " + id + ", what's your name?");
			String auxName = TrainerBattleController.sc.nextLine();
			Pokemon[] auxPokemons = new Pokemon[6];
			
			for(i = 0; i < 6; i++) {
				System.out.println(auxName + ", choose your " + (i+1) + " Pokemon. Type 'none' if you don't want "
						+ "any more Pokemons.");
				
				String auxPokemonName = TrainerBattleController.sc.nextLine();
				if(!auxPokemonName.equals("none") && !auxPokemonName.equals("None")) {
					if (Pokemon.setPokemon(auxPokemonName, pl) != null)
						auxPokemons[i] = Pokemon.setPokemon(auxPokemonName, pl);
					else {
						System.out.println("Chosen Pokemon is not available!");
						System.out.println("Choose another Pokemon!");
						i--;
					}
				}
				else {
					if(i == 0) {
						System.out.println("Chosen Pokemon is not available!");
						System.out.println("Choose another Pokemon!");
						i--;
					}
					else {
						for(int j = i; j < 6; j++)
							auxPokemons[j] = null; //Creates a null Pokemon 
						break;
					}
				}
			}
			t = new Trainer(auxName, id, auxPokemons, i, i);
			t.setCurrent(0);
			return t;
		}
		if(opt == 1) {
			boolean available = false;
			
			System.out.println("Trainer, what's your name?");
			String auxName = TrainerBattleController.sc.nextLine();
			Pokemon[] auxPokemons = new Pokemon[6];
			
			System.out.println("Which starter Pokemon would you like to take on your journey?");
			System.out.println("Bulbasaur, the grass-type Pokemon");
			System.out.println("Charmander, the fire-type Pokemon");
			System.out.println("Squirtle, the water-type Pokemon");
			
			String auxPokemonName = TrainerBattleController.sc.nextLine();
			
			while(!available) {
				if(auxPokemonName.equals("Bulbasaur") || auxPokemonName.equals("Charmander") || auxPokemonName.equals("Squirtle")) { 
					auxPokemons[0] = Pokemon.setPokemon(auxPokemonName, pl);
					available = true;
				}
				else {
					System.out.println("Chosen Pokemon is not available!");
					System.out.println("Choose another Pokemon!");
					auxPokemonName = TrainerBattleController.sc.nextLine();
				}
			}
			
			for(i = 1; i < 6; i++) 
				auxPokemons[i] = null; //Creates a null Pokemon
			
			System.out.println(auxName + ", your new adventure is about enfold!");
			System.out.println("Embark now on your very own Pokemon adventure!");
			
			t = new Trainer(auxName, id, auxPokemons, 1, 1);
			t.setCurrent(0);
			return t;	
		}
		
		System.out.println("An unexpected error ocurred!");
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public Pokemon getPokemon(int i) {
		return pokemon[i];
	}
	
	public void setPokemon(String name, int i, PokemonList pl) {
		this.pokemon[i] = Pokemon.setPokemon(name, pl);
	}
	
	public Pokemon getCurrentPokemon() {
		return pokemon[currentPokemon];
	}
	
	public int getCurrent() {
		return currentPokemon;
	}
	
	public void setCurrent(int i) {
		currentPokemon = i;
	}
	
	public int getNumberOfPokemon() {
		return numberOfPokemon;
	}
	
	public void setNumberOfPokemon(int n) {
		numberOfPokemon = n;
	}
	
	public int getActivePokemon() {
		return activePokemon;
	}
	
	public boolean activePokemonFainted() {
		if(pokemon[currentPokemon].fainted()) {
			activePokemon--;
			return true;
		}
		else
			return false;
	}
	
	public void walkInMap(char direction) {
		switch (direction) {
			case 'w':
				positionInMap[0] -= 1;
				break;
			case 's':
				positionInMap[0] += 1;
				break;
			case 'a':
				positionInMap[1] -= 1;
				break;
			case 'd':
				positionInMap[1] += 1;
				break;
		}
	}
	
	public int[] getPosition() {
		return positionInMap;
	}
}


