/**
 * Joueur v Joueur : Jeu de Marienbad
 * @author MANANJEAN Enrick / AVRIL Killian
 * @version 2.0
 */
class JeuMarienbad{
	void principal() {
		System.out.println("**Jeu de Marienbad**");
        // Définition des noms des joueurs et du nombre de lignes
        String joueur1 = SimpleInput.getString("Nom du joueur 1 : ");//Insérer le nom du joueur1
        String joueur2 = SimpleInput.getString("Nom du joueur 2 : ");//Insérer le nom du joueur2
        int j_joue;//Le joueur qui joue actuellement
        int nbLignes;
        do{
			nbLignes = SimpleInput.getInt("Choisissez le nombre de lignes : ");//Insertion du nombre de ligne
		}while(nbLignes <= 1);
        //Nom des participants
        System.out.println("1 : " + joueur1);
        System.out.println("2 : " + joueur2);
        do {
            j_joue = SimpleInput.getInt("Qui va commençer? : ");//Choix du joueur qui va commencer
        } while (j_joue < 1 || j_joue > 2);
        // Initialisation du jeu avec les lignes et les allumettes(début de la partie)
        System.out.println(joueur1+" et "+joueur2+", "+"C'est parti !");
        int[] jeu = initialiserJeu(nbLignes);//

        // Variables de contrôle du jeu
        String tour = joueurCourant(j_joue, joueur1, joueur2);//tour de quels joueurs//Joueur qui commence
        boolean jeuTermine = false;//Si jeu est terminé(non vu que ça commence)

        // Boucle de jeu
        while(!(jeuTermine(jeu))){//Tant que le jeu n'est pas terminé
            afficherJeu(jeu);//Affiche l'état du jeu
            System.out.println(tour + ", c'est à toi de jouer.");//Annonce à qui le tour
            jeu_joueur(jeu);
            // Vérification si le jeu est terminé
            if (jeuTermine(jeu)) {//Si le jeu est arrivé à la fin
                System.out.println("Félicitations! " + tour + " a gagné !");//Annonce le vainqueur, c'est le dernier joueur qui a joué
            } else {//Si le jeu n'est pas encore terminé
                if(j_joue == 1){//Si c'est le joueur1 qui vient de jouer
					j_joue = 2;//C'est au tour du joueur2
				}else if(j_joue == 2){//Si c'est le joueur2 qui vient de jouer
					j_joue = 1;//C'est au tour du joueur2
				}
				tour = joueurCourant(j_joue, joueur1, joueur2);//Changement de tour
            }
        }//Refaire la même procédure encore
        testInitialiserJeu();
        testJeu_allu();
        testJoueurCourant();
      
    }
    
    /**
     * Initialise les lignes avec le nombre d'allumettes par ligne.
     * 
     * @param nbLignes nombre de lignes à initialiser
     * @return tableau contenant le nombre d'allumettes par ligne
     */
    int[] initialiserJeu(int nbLignes) {
        int[] jeu = new int[nbLignes];//Créer un tableau de nbLignes longueur
        for (int i = 0; i < nbLignes; i++) {//Parcourir le tableau et modifier c'est valeurs selon le nombre de lignes choisis au départ
            jeu[i] = 2 * i + 1; // Chaque ligne contient un nombre impair d'allumettes (1, 3, 5, etc.)
        }
        return jeu;//Retourne le tableau fini
    }

    /**
     * Affiche l'état actuel du jeu, avec le nombre d'allumettes restantes sur chaque ligne.
     * @param lignes tableau représentant le nombre d'allumettes sur chaque ligne.
     * 
     */
    void afficherJeu(int[] lignes) {
        System.out.println("\nÉtat actuel du jeu :");//Affiche "Etat actuel du jeu"
        for (int i = 0; i < lignes.length; i++) {
            System.out.print(i + " : ");// 0 :
            for (int j = 0; j < lignes[i]; j++) {
                System.out.print(" | ");// 0 : |
            }
            System.out.println();//Va à la ligne
        }
    }

    /**
     * Vérifie si le jeu est terminé (plus aucune allumette dans aucune ligne).
     * 
     * @param lignes tableau représentant les lignes et le nombre d'allumettes
     * @return true si le jeu est terminé, false sinon
     */
    boolean jeuTermine(int[] lignes) {
		boolean fin;
		int cpt = 0;//Compteur
        for (int i = 0;i<lignes.length;i++) {
            if (lignes[i] != 0) {//Cherche s'il y a encore des allumettes dans chaque ligne
                cpt++;//Compte les lignes où il reste encore des allumettes
            }
        }
        if(cpt==0){//Si on compte 0 lignes où il reste encore des allumettes
			fin = true;//C'est la fin
		}else{//Sinon
			fin = false;//C'est pas la fin
		}
		return fin;//Retourn "true" si c'est la fin et "false" sinon
    }
    void jeu_joueur(int[] jeu){
        int niv = SimpleInput.getInt("Où veux-tu retirer des allumettes: ");
        while (niv < 0 || niv > jeu.length - 1 || jeu[niv] == 0) {
            niv = SimpleInput.getInt("Réessaye, où veux-tu retirer des allumettes: ");
        }
        int nb_allumettes = SimpleInput.getInt("Combien : ");
        while (nb_allumettes <= 0 || nb_allumettes > jeu[niv]) {
            nb_allumettes = SimpleInput.getInt("Réessaye, combien : ");
        }
        // Mise à jour du jeu
        jeu = Jeu_allu(jeu, niv, nb_allumettes);
        System.out.println("Ça marche!");
    }
    /**
     * Retire un nombre d'allumettes sur une ligne.
     * @param jeu tableau représentant le nombre d'allumettes sur chaque ligne.
     * @return jeu tableau après le tour.
     */
    int[] Jeu_allu(int[] jeu, int niv, int nb_allumettes){
		jeu[niv] -= nb_allumettes;//Retire le nombre d'allumettes sélectionnées sur la ligne sélectionnées
		return jeu;//Retourne le tableau modifié
	}
	String joueurCourant ( int val, String j1, String j2){
        String rst = "";
        if (val == 1) {
            rst = j1;
        }else if(val == 2){
            rst = j2;
        }
        return rst;
    }
    void testJeu_allu() {
		System.out.println("*** testJeu_allu()");
		testCasJeu_allu(new int[]{5},0 , 2, new int[]{3});
		testCasJeu_allu(new int[]{5, 7},0 , 3, new int[]{2, 7});
		testCasJeu_allu(new int[]{1, 4, 6}, 1, 1, new int[]{1, 3, 6});
		testCasJeu_allu(new int[]{1}, 0, 1, new int[]{});
	}

	void testCasJeu_allu(int[] jeuInitial, int niv, int nb_allumettes, int[] jeuAttendu) {
		System.out.println("Test Jeu_allu(jeuInitial, niv=" + niv + ", nb_allumettes=" + nb_allumettes + ")");
		int[] resultat = Jeu_allu(jeuInitial, niv, nb_allumettes);
		
		// Afficher le résultat du test
		if (resultat == jeuAttendu) {
			System.out.println("ok");
		} else {
			System.out.println("Erreur");
		}
	}
	void testInitialiserJeu() {
		System.out.println("*** testInitialiserJeu()");
		testCasInitialiserJeu(1, new int[]{1});
		testCasInitialiserJeu(3, new int[]{1, 3, 5});
		testCasInitialiserJeu(5, new int[]{1, 3, 5, 7, 9});
	}

	void testCasInitialiserJeu(int nbLignes, int[] jeuAttendu) {
		System.out.println("Test initialiserJeu(nbLignes=" + nbLignes + ")");
		int[] resultat = initialiserJeu(nbLignes);
		
		// Afficher lerésultat du test
		if (resultat == jeuAttendu) {
			System.out.println("ok");
		} else {
			System.out.println("Erreur");
		}
	}
	void testJoueurCourant() {
	 System.out.println("*** testJoueurCourant()");
	 testCasJoueurCourant(1, "Alice", "Ordi", "Alice");
	 testCasJoueurCourant(2, "Alice", "Ordi", "Ordi");
	}

	void testCasJoueurCourant(int val, String j1, String j2, String joueurAttendu) {
	 System.out.println("Test joueurCourant(val=" + val + ", j1=" + j1 + ", j2=" + j2 + ")");
	 String resultat = joueurCourant(val, j1, j2);
	 boolean testReussi = resultat.equals(joueurAttendu);
	 
	 // Afficher le résultat du test
	if (testReussi) {
	  System.out.println("ok");
	} else {
	  System.out.println("Erreur");
	  System.out.println("Attendu : " + joueurAttendu);
	  System.out.println("Reçu : " + resultat);
	 }
	}
}	
