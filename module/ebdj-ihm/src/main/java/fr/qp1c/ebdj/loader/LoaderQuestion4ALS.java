package fr.qp1c.ebdj.loader;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.Question;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;

public class LoaderQuestion4ALS {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderQuestion4ALS.class);

	/**
	 * 
	 * @return
	 */
	public static Map<String, Theme4ALS> chargerQuestions() {
		LOGGER.debug("[DEBUT] Chargement des questions de 4ALS.");

		Map<String, Theme4ALS> themes4ALS = new HashMap<>();

		themes4ALS.put("1", donnerTheme1());
		themes4ALS.put("2", donnerTheme2());
		themes4ALS.put("3", donnerTheme3());
		themes4ALS.put("4", donnerTheme4());

		LOGGER.debug("[FIN] Chargement des questions de 4ALS.");

		return themes4ALS;
	}

	public static Theme4ALS donnerTheme1() {

		Theme4ALS theme = new Theme4ALS();

		Map<String, Question> questionsTheme = new HashMap<>();
		theme.setTheme("Îles et archipels de l’océan Indien");
		questionsTheme.put("1", new Question(
				"Quel état insulaire, plus grand que la France, a pour capitale Antananarivo ?", "Madagascar"));
		questionsTheme.put("2",
				new Question("Quelle ancienne colonie française, indépendante depuis 1975, a pour capitale Moroni ?",
						"Les Comores"));
		questionsTheme.put("3",
				new Question(
						"Quel archipel indépendant depuis 1976, membre du Commonwealth, a pour capitale Victoria ?",
						"Les Seychelles"));
		questionsTheme.put("4",
				new Question(
						"Comment appelle-t-on les petites îles inhabitées, appartenant à la France, situées autour de Madagascar ?",
						"Les îles Eparses (de l’océan Indien)"));
		questionsTheme.put("5", new Question(
				"Quelle île est un département français qui a pour chef-lieu Saint-Denis ?", "La Réunion"));
		questionsTheme.put("6",
				new Question(
						"Quel état insulaire, indépendant depuis 1968 et dont la capitale est Port Louis, fut autrefois française puis anglaise ?",
						"L’île Maurice"));
		questionsTheme.put("7",
				new Question("Quel archipel situé au sud de l’Inde forme une république dont la capitale est Malé ?",
						"Les Maldives"));
		questionsTheme.put("8",
				new Question(
						"Quel archipel, au nord de Sumatra, forme, avec les îles Andaman, un territoire dépendant de l’Inde ?",
						"Les îles Nicobar"));
		questionsTheme.put("9",
				new Question("Quel état insulaire au sud de l’Inde s’appelait autrefois Ceylan ?", "Le Sri Lanka"));
		questionsTheme.put("10",
				new Question("Quelle île est devenue en 2011 le 101ème département français ?", "Mayotte"));
		questionsTheme.put("11",
				new Question(
						"Sur quel archipel dépendant des Terres Australes et Antarctiques Françaises est établie la base scientifique de Port-aux-Français ?",
						"Les îles Kerguelen (ou îles de la Désolation)"));
		questionsTheme.put("12",
				new Question("Comment appelle-t-on l’archipel qui regroupe la Réunion, l’île Maurice et Rodrigues ?	",
						"Les Mascareignes"));

		theme.setQuestions(questionsTheme);
		return theme;
	}

	public static Theme4ALS donnerTheme2() {

		Theme4ALS theme = new Theme4ALS();

		Map<String, Question> questionsTheme = new HashMap<>();
		theme.setTheme("Volets et rideaux");

		questionsTheme.put("1",
				new Question(
						"Quel système est un rideau, pouvant être à enrouleur, ou un assemblage souple d’éléments qui se lève ou se baisse devant une fenêtre ?",
						"Un store"));
		questionsTheme.put("2",
				new Question(
						"Quel mot, dérivé de l’ancien nom de l’Iran, désigne un châssis mobile muni d’un panneau à claire-voie ?",
						"Une persienne"));
		questionsTheme.put("3", new Question(
				"Quel volet mobile composé de lames orientables porte le nom d’un sentiment ?", "Une jalousie"));
		questionsTheme.put("4",
				new Question(
						"Quel adjectif se rapportant à un état américain qualifie un store pour l’intérieur à bandes verticales ?",
						"californien"));
		questionsTheme.put("5",
				new Question(
						"Quel nom, formé sur voile, désigne un grand rideau de fenêtre en tissu léger et transparent ?",
						"Un voilage"));
		questionsTheme.put("6",
				new Question("Quel adjectif qualifie un store en lamelles horizontales ?", "Vénitien "));
		questionsTheme.put("7",
				new Question(
						"Quel grillage fait de bois tourné permet de voir sans être vu dans l’architecture arabe traditionnelle ?",
						"Le moucharabieh"));
		questionsTheme.put("8",
				new Question(
						"Quel nom composé désigne un petit rideau tendu au bas d’une fenêtre pour empêcher l’air de passer ?	",
						"Un brise-bise"));
		questionsTheme.put("9", new Question(
				"Quelle fermeture métallique sert à protéger la devanture d’un magasin ?	 ", "Un rideau de fer"));
		questionsTheme.put("10", new Question("Quel nom formé sur vent désigne un grand volet extérieur en bois ?	",
				"Un contrevent"));
		questionsTheme.put("11",
				new Question("Quelle expression désigne des rideaux courts maintenus par une embrasse ?",
						"Des rideaux bonne femme"));
		questionsTheme.put("12",
				new Question(
						"Quel nom féminin, désignant des éléments décorant les murs, s’applique régionalement à un rideau de tissu épais ?",
						"Une tenture"));
		theme.setQuestions(questionsTheme);

		return theme;
	}

	public static Theme4ALS donnerTheme3() {

		Theme4ALS theme = new Theme4ALS();

		Map<String, Question> questionsTheme = new HashMap<>();
		theme.setTheme("Mariages et divorces au cinéma");

		questionsTheme.put("1",
				new Question(
						"Dans quel film de 1994 Hugh Grant qui adore se rendre à des mariages est aussi confronté à la mort de l’un de ses amis ?	",
						"Quatre mariages et un enterrement (Four Weddings and a Funeral)"));
		questionsTheme.put("2",
				new Question(
						"Dans quel film de 1979 Dustin Hoffman et Meryl Streep, époux séparés, se disputent la garde de leur fils ?",
						"Kramer contre Kramer (Kramer versus Kramer)"));
		questionsTheme.put("3",
				new Question(
						"Dans quel film de Truffaut Jeanne Moreau, veuve le jour de son mariage, élimine les hommes qu’elle pense responsables de la mort de son mari ?	",
						"La mariée était en noir"));
		questionsTheme.put("4",
				new Question(
						"Quel film de Fassbinder montre Hanna Schygulla en métaphore de l’Allemagne de l’immédiat après-guerre ?",
						"La Mariage de Maria Braun (Die Ehe der Maria Braun)"));
		questionsTheme.put("5",
				new Question(
						"Dans quelle comédie de Rappeneau, se déroulant sous la Révolution, Belmondo est-il un émigré qui doit rentrer en France pour divorcer ?	",
						"Les Mariés de l’an II "));
		questionsTheme.put("6",
				new Question(
						"Dans quel film de 2000, premier d’une série, Ben Stiller s’apprête-t-il à épouser la fille de De Niro ?",
						"Mon beau-père et moi (Meet the Parents)"));
		questionsTheme.put("7",
				new Question(
						"Quelle comédie de Pietro Germi avec Mastroianni montre comment se débarrasser de sa femme quand le divorce n’existait pas en Italie ?	",
						"Divorce à l’italienne (Divorzio all’italiana)"));
		questionsTheme.put("8",
				new Question(
						"Quel film de Denys Granier-Deferre de 2010 montre un mariage bourgeois, occasion de disputes et du retour d’un ancien secret de famille ?	",
						"Pièce montée"));
		questionsTheme.put("9",
				new Question(
						"Dans quel film de 1993 Robin Williams est-il un père divorcé qui se déguise en gouvernante pour revoir ses enfants ?",
						"Madame Doubtfire (Mrs Doubtfire)"));
		questionsTheme.put("10", new Question(
				"Dans le titre d’un film de Frédéric Beigbeder de 2012 combien de temps dure l’amour ?", "Trois ans"));
		questionsTheme.put("11",
				new Question(
						"Dans quel film d’Éric Lartigau Alain Chabat, pressé de se marier par sa famille, envisage de payer Charlotte Gainsbourg pour passer pour sa fiancée ?",
						"Prête-moi ta main"));
		questionsTheme.put("12",
				new Question(
						"Dans quel film avec Richard Gere, Julia Roberts est une spécialiste de l’abandon de ses fiancés à la dernière minute devant l’autel ?",
						"Just married (ou presque)"));
		theme.setQuestions(questionsTheme);

		return theme;
	}

	public static Theme4ALS donnerTheme4() {

		Theme4ALS theme = new Theme4ALS();

		Map<String, Question> questionsTheme = new HashMap<>();
		theme.setTheme("Expressions avec des noms de légumes");

		questionsTheme.put("1", new Question("Familièrement, quel légume fait-on quand on attend ?	", "Le poireau"));
		questionsTheme.put("2",
				new Question("Quelle plante crucifère dit-on qu’on n’a plus quand on n’a plus un sou ?", "radis"));
		questionsTheme.put("3",
				new Question("Quelle expression utilise-t-on pour dire qu’on alterne les promesses et les menaces ?",
						"(manier) la carotte et le bâton"));
		questionsTheme.put("4",
				new Question("Quelle expression signifie qu’on fait son profit de quelque chose, qu’on s’en régale ?",
						"On en fait ses choux gras"));
		questionsTheme.put("5",
				new Question("De quoi est-ce la fin quand c’est la fin de tout ?", "La fin des haricots"));
		questionsTheme.put("6", new Question("Comment est-on quand on est sur une seule ligne ?", "En rang d’oignons"));
		questionsTheme.put("7",
				new Question("Quelle locution familière désigne quelqu’un d’inconstant en amour, de volage ?	",
						"Avoir un cœur d’artichaut"));
		questionsTheme.put("8",
				new Question(
						"Quelle expression familière indique qu’une chose est faite avec un soin particulier, parfaitement ?",
						"Aux petits oignons"));
		questionsTheme.put("9",
				new Question("Que raconte-t-on quand on raconte des mensonges, des histoires ?", "Des salades"));
		questionsTheme.put("10", new Question(
				"A quel légume compare-t-on familièrement une personne grande et maigre ?	", "Une asperge"));
		questionsTheme.put("11",
				new Question("Quel légume dit-on qu’on fait quand on ne réussit pas ?	", "On fait chou blanc"));
		questionsTheme.put("12",
				new Question("Que dit-on qu’on a dans la tête quand on est stupide ?", "Un pois chiche"));
		theme.setQuestions(questionsTheme);

		return theme;
	}
}
