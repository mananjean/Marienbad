class JeuMarienbadOrdi{
	void principal() {
		System.out.println("**Jeu de Marienbad**");
        // Définition des noms des joueurs et du nombre de lignes
        int difficulte;//niveau de difficulté
        int niv, nb_allumettes;//niveau, nombre d'allumettes à retirer
        String joueur1 = SimpleInput.getString("Votre nom : ");//Insérer nom joueur
        String ORDI = "ordi";//Nom de l'ordinateur
        int j_joue;//Le joueur qui joue actuellement
        System.out.println("1 : Facile");
        System.out.println("2 : Normale");
        System.out.println("3 : Difficile");
        System.out.print(joueur1 + ", ");
        do {
            difficulte = SimpleInput.getInt("Choisissez le niveau de difficulté entre 1 et 3 : ");//Choisir la difficulté
        } while (difficulte < 1 || difficulte > 3);//Redemande si on choisi moins de 1(facile) ou plus de 3(difficile)
        System.out.println();
        System.out.println("**" + Diffi(difficulte) + "**");//Annonce de la difficulté
        System.out.println();
        int nbLignes = SimpleInput.getInt("Choisissez le nombre de lignes : ");//Saisie du nombre de lignes
        //Nom des participants
        System.out.println("1 : " + joueur1);
        System.out.println("2 : " + ORDI);
        do {
            j_joue = SimpleInput.getInt("Qui va commençer? : ");//Choix du joueur qui va commencer
        } while (j_joue < 1 || j_joue > 2);
        // Initialisation du jeu avec les lignes et les allumettes
        System.out.println(joueur1+" c'est parti !");
        int[] jeu = initialiserJeu(nbLignes);//Création du jeu selon le nombre de ligne

        // Variables de contrôle du jeu
        String tour = joueurCourant(j_joue, joueur1, ORDI);//tour de quels joueurs
        boolean jeuTermine = false;//Si jeu est terminé(non vu que ça commence

        // Boucle de jeu
        while (!(jeuTermine(jeu))) {//Tant que le jeu n'est pas terminé
            afficherJeu(jeu);//Affiche l'état du jeu
            //tour du joueur1
            System.out.println("C'est à "+tour+" de jouer.");//Annonce qui joue
            if(j_joue==1) {//Si c'est le tour du joueur
                jeu_joueur(jeu);//Action du joeur
            }else if(j_joue==2) {//Si c'est le tour de l'ordinateur
                ordiDiff(difficulte, jeu);//Action de l'ordinateur mais selon la difficulté
            }
            // Vérification si le jeu est terminé
            if (jeuTermine(jeu)) {//Si le jeu est terminé
                System.out.println("Félicitations! " + tour + " a gagné !");//Annonce le vainqueur de la partie(le dernier joueur a avoir joué)
            } else {//Sinon il passe le tour à l'autre joueur
                if (j_joue == 1) {
                    j_joue = 2;
                } else if (j_joue == 2) {
                    j_joue = 1;
                }
                tour = joueurCourant(j_joue, joueur1, ORDI);//Changement de tour
            }
        }
        testJeu_allu();
        testJoueurCourant();
        testInitialiserJeu();
    }
    /**
     * Choix de stratégie utilisée par l'ordinateur(facile/normal/difficile)
     * @param difficulte qui désigne la difficulté choisie par le joueur
     * @param jeu état du jeu
     */
    void ordiDiff(int difficulte, int[] jeu){//Choix de la stratégie de l'ordinateur selon la difficulté choisie
        if(difficulte==1){//Si on a choisi une difficulté facile
            jeuOrdi_facile(jeu);//Actionne la stratégie de l'ordinateur facile
        } else if (difficulte==2){//Si on a choisi une difficulté normal
            jeuOrdi_normal(jeu);//Actionne la stratégie de l'ordinateur normal
        }else if(difficulte==3){//Si on a choisi une difficulté difficile
            jeuOrdi_difficile(jeu);//Actionne la stratégie de l'ordinateur difficile
        }
    }
    /**
     * Stratégie qui consiste à choisir un entier aléatoire dans une ligne aléatoire, il ne cherche pas a gagner.
     * Il va retirer aléatoirement des allumettes
     */
    void str_aleatoire(int[] jeu) {//tour de l'ordinateur
        int niv = (int) (Math.random() * jeu.length);
        while(jeu[niv] == 0){//stock = 0 demander une nouvelle ligne
			niv = (int) (Math.random() * jeu.length);
        }
        int nb_allumettes = (int) (Math.random() * jeu[niv])+ 1;
        while(nb_allumettes>jeu[niv]){
			nb_allumettes = (int) (Math.random() * jeu[niv])+ 1;
		}
        jeu = Jeu_allu(jeu, niv, nb_allumettes);
        System.out.println("l'ordi a retirer "+ nb_allumettes + " allumette(s) à la ligne "+niv);//zizi
    }
    /**
     * Stratégie utilisant la technique de Grundy pour assurer la victoire
     * Il cherche absolument à gagner
     */
    void str_gagnante(int[] jeu) {//tour de l'ordinateur
        int Nim = jeu[0];
        for(int i = 1; i<jeu.length;i++){
			Nim = Nim^jeu[i];
		}
		int b = 0;
		int cpt = 0;
		for(int j = 0; j<jeu.length;j++){
			if(!(jeu[j]==0)){
				cpt++;
				b = j;
			}
		}
		if(cpt==1){
			Jeu_allu(jeu, b, jeu[b]);
		}
		if(Nim == 0){
			str_aleatoire(jeu);
		}else{
			int nb_allumettes = 0;
			int niv = 0;
			for(int a = 0; a<jeu.length;a++){
				int y = jeu[a]^Nim;
				if(y<jeu[a]){
					nb_allumettes = jeu[a] - y;
					niv = a;
				}
			}
			Jeu_allu(jeu, niv, nb_allumettes);
			System.out.println("l'ordi a retirer "+ nb_allumettes + " allumette(s) à la ligne "+niv);
		}
		
    }
    /**
    * Exécution de la difficulté choisi (facile).
    * @param jeu état actuel du jeu.
    */
    void jeuOrdi_facile(int[] jeu) {//tour de l'ordinateur
        str_aleatoire(jeu);
    }
    
    
    /**
    * Exécution de la difficulté choisi (normale).
    * Utilise la stratégie aléatoirement au début et utilise à la fin la stratégie gagnante.
    * @param jeu état actuel du jeu
    */
    void jeuOrdi_normal(int[] jeu) {//tour de l'ordinateur
		int cpt = 0;
		for(int i = 0;i<jeu.length;i++){
			if(jeu[i]!=0){
				cpt++;
			}
		}
		if(cpt<=3){
			str_gagnante(jeu);
		}else{
			str_aleatoire(jeu);
		}
    }
    
    
    /**
    * Exécution de la difficulté choisi (Difficile).
    * @param l'ordi choisi un entier dans une ligne il cherche a gagner.
    */
    void jeuOrdi_difficile(int[] jeu) {//tour de l'ordinateur
        str_gagnante(jeu);
    }
    
    
    /**
    * Initialise les lignes avec le nombre d'allumettes par ligne.
    * @param nbLignes nombre de lignes à initialiser
    * @return tableau contenant le nombre d'allumettes par ligne
    */
    int[] initialiserJeu ( int nbLignes){
        int[] jeu = new int[nbLignes];
        for (int i = 0; i < nbLignes; i++) {
            jeu[i] = 2 * i + 1; // Chaque ligne contient un nombre impair d'allumettes (1, 3, 5, etc.)
        }
        return jeu;
    }
    
    /**
     * Demande au joueur ce qu'il veut faire et execute l'action.
     * @param jeu état actuel du jeu.
     * 
     */
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
    * Affiche l'état actuel du jeu, avec le nombre d'allumettes restantes sur chaque ligne.
    * @param lignes tableau représentant le nombre d'allumettes sur chaque ligne.
    *
    */
    void afficherJeu ( int[] lignes){
        System.out.println("\nÉtat actuel du jeu :");
        for (int i = 0; i < lignes.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < lignes[i]; j++) {
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    /**
    * Vérifie si le jeu est terminé (plus aucune allumette dans aucune ligne).
    *
    * @param lignes tableau représentant les lignes et le nombre d'allumettes
    * @return true si le jeu est terminé, false sinon
    */
    boolean jeuTermine (int[] lignes){
        boolean fin;
        int cpt = 0;
        for (int i = 0; i < lignes.length; i++) {
            if (lignes[i] != 0) {
                cpt++;
            }
        }
        if (cpt == 0) {
			fin = true;
        } else {
            fin = false;
        }
        return fin;
    }
    /**
    * Retire un nombre d'allumettes sur une ligne.
    * @param jeu tableau représentant le nombre d'allumettes sur chaque ligne.
    * @return jeu tableau après le tour.
    */
    int[] Jeu_allu ( int[] jeu, int niv, int nb_allumettes){
        jeu[niv] -= nb_allumettes;
        return jeu;
    }
    
    
    /**
    * Annonce le niveau de difficulté choisi.
    * @param entier entre 1(inclus) et 3(inclus) qui représente la difficulté.
    * @return la difficulté choisie.
    */
    String Diffi ( int bot){
        String rst = "";
		if (bot == 1) {
            rst = "Facile";
        } else if (bot == 2) {
            rst = "Normale";
        } else if (bot == 3) {
            rst = "Difficile";
        }
        return rst;
    }
    /**
     * Annonce le tour du joueur qui doit jouer
     * 
     */
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
		testCasJeu_allu(new int[]{5}, 0, 2, new int[]{3});
		testCasJeu_allu(new int[]{5, 7}, 0, 3, new int[]{2, 7});
		testCasJeu_allu(new int[]{1, 4, 6}, 1, 1, new int[]{1, 3, 6});
		testCasJeu_allu(new int[]{1}, 0, 1, new int[]{0});
	}

	void testCasJeu_allu(int[] jeuInitial, int niv, int nb_allumettes, int[] jeuAttendu) {
		System.out.println("Test Jeu_allu(jeuInitial, niv=" + niv + ", nb_allumettes=" + nb_allumettes + ")");
		int[] resultat = Jeu_allu(jeuInitial, niv, nb_allumettes);
		boolean testReussi = true;
		if (resultat.length != jeuAttendu.length) {
			testReussi = false;
		} else {
			for (int i = 0; i < resultat.length; i++) {
				if (resultat[i] != jeuAttendu[i]) {
					testReussi = false;
					break;
				}
			}
		}

		// Afficher le résultat du test
		if (testReussi) {
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
	}else{
		System.out.println("Erreur");
		System.out.println("Attendu : " + joueurAttendu);
		System.out.println("Reçu : " + resultat);
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
}
		
	

