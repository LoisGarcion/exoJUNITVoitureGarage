package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}

		immatriculation = i;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
		// Trouver le dernier stationnement de la voiture si il y en a
		if(myStationnements.size()>0) {
				Stationnement lastStationnement = myStationnements.get(myStationnements.size() - 1);

				//verif si le dernier stationnement a une date de fin
				if (lastStationnement.estEnCours()) { //on aurait pu utiliser la méthode estDansUnGarage() aussi
					throw new Exception("La voiture est déjà en stationnement");
				}
		}
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {

		if(myStationnements.size() == 0){
			throw new Exception("la voiture n'est pas en stationnement actuellement");
		}
		Stationnement lastStationnement = myStationnements.get(myStationnements.size()-1);
		//verif si la voiture est actuellement en stationnement
		if(!lastStationnement.estEnCours()){
			throw new Exception("la voiture n'est pas en stationnement actuellement");
		}
		// Terminer ce stationnement
		lastStationnement.terminer();
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> listGarage = new HashSet<Garage>();
		for(Stationnement stationnement : myStationnements){
			listGarage.add(stationnement.getGarage());
		}
		return listGarage;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// Vrai si le dernier stationnement est en cours
		if(myStationnements.size() == 0){
			return false;
		}
		Stationnement lastStationnement = myStationnements.get(myStationnements.size()-1);
		return lastStationnement.estEnCours();

	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 * <br>
	 * Exemple :
	 * 
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
		Set<Garage> listGarage = garagesVisites();
		//on parcourt chaque garage de notre liste de garage
		for(Garage garage : listGarage){
			out.println(garage.toString());
			//On cherche chaque stationnement effectué et on affiche
			for(Stationnement stationnement : myStationnements){
				if(stationnement.getGarage().equals(garage)){
					out.println(stationnement.toString());
				}
			}
		}
	}


}
