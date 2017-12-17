package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.mapper.MapperQuestion;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QR;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Question4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Theme4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;

public class DBConnecteurQALSDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurQALSDao {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurQALSDaoImpl.class);

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Theme4ALS donnerTheme(int groupeCategorie, int niveau) {
        // Création de la requête
        String requete = String.format(
                "SELECT reference FROM THEME_QALS where reference NOT IN (SELECT reference FROM THEME_QALS_LECTURE) AND reference NOT IN (SELECT reference FROM THEME_QALS_PRESENTE) AND groupeCategorieRef=%d AND active=1 AND difficulte=%d;",
                groupeCategorie, niveau);

        ResultSetHandler<String> h = new ResultSetHandler<String>() {
            @Override
            public String handle(ResultSet rs) throws SQLException {
                String ref = null;

                if (rs.next()) {
                    // Convertir chaque question
                    ref = rs.getString("reference");
                }
                return ref;
            }
        };

        String reference = executerRequete(requete, h);

        return recupererTheme4ALS(reference);
    }

    public Theme4ALS recupererTheme4ALS(String reference) {
        Theme4ALS theme4ALS = recupererPartieTheme4ALS(reference);
        if (theme4ALS.getReference() == null) {
            LOGGER.error("Le theme avec la référence {} est introuvable !", reference);
            return null;
        }

        theme4ALS.setQuestions(recupererPartieQuestions4ALS(theme4ALS.getReference()));
        return theme4ALS;
    }

    private Theme4ALS recupererPartieTheme4ALS(String reference) {
        // Création de la requête
        String requete = String.format(
                "SELECT id, categorie, categorieRef, groupeCategorieRef, theme, difficulte, reference, club, dateReception, version, active FROM THEME_QALS WHERE reference=%s;",
                reference);

        ResultSetHandler<Theme4ALS> h = new ResultSetHandler<Theme4ALS>() {
            @Override
            public Theme4ALS handle(ResultSet rs) throws SQLException {
                Theme4ALS theme4als = new Theme4ALS();

                if (rs.next()) {
                    // Convertir chaque question
                    theme4als = MapperQuestion.convertirTheme4ALS(rs);
                }
                return theme4als;
            }
        };

        return executerRequete(requete, h);
    }

    private Map<String, QR> recupererPartieQuestions4ALS(String reference) {
        // Création de la requête
        String requete = String.format(
                "SELECT seq, question, reponse FROM QUESTION_QALS WHERE reference=%s ORDER BY seq ASC;", reference);

        ResultSetHandler<Map<String, QR>> h = new ResultSetHandler<Map<String, QR>>() {
            @Override
            public Map<String, QR> handle(ResultSet rs) throws SQLException {
                Map<String, QR> questions4als = new HashMap<>();

                int indexQuestion = 1;
                while (rs.next()) {
                		questions4als.put(String.valueOf(indexQuestion), MapperQuestion.convertirQR(rs));
                		indexQuestion += 1;
                }
                return questions4als;
            }
        };

        return executerRequete(requete, h);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur) {
        signalerAnomalie("QALS", reference, version, anomalie, lecteur);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbTheme() {
        return compterNbTheme(-1);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbTheme(int groupeCategorieRef) {
        return compterNbQuestion("QALS", ajouterClauseGroupeCategorieRef(groupeCategorieRef));
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbThemeJoue() {
        return compterNbThemeJoue(-1);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbThemeJoue(int groupeCategorieRef) {
        return compterNbQuestionLue("QALS", ajouterClauseGroupeCategorieRef(groupeCategorieRef));
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbThemeNonJoue(int groupeCategorieRef) {
        return compterNbQuestionNonLue("QALS", ajouterClauseGroupeCategorieRef(groupeCategorieRef));
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public boolean existerThemeNonJoue(int groupeCategorieRef, int niveau) {
        return compterNbQuestionNonLue("QALS", ajouterClauseGroupeCategorieRef(groupeCategorieRef)+" "+ajouterClauseDifficulte(niveau)) > 0;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbThemePresente() {
        return compterNbThemePresente(-1);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbThemePresente(int groupeCategorieRef) {
        return compterNbQuestionPresente("QALS", ajouterClauseGroupeCategorieRef(groupeCategorieRef));
    }

    private String ajouterClauseGroupeCategorieRef(int groupeCategorieRef) {
        StringBuilder query = new StringBuilder();

        if (groupeCategorieRef > 0) {
            query.append(" AND groupeCategorieRef=");
            query.append(groupeCategorieRef);
        }
        
        return query.toString();
    }

    private String ajouterClauseDifficulte(int niveau) {
        StringBuilder query = new StringBuilder();

        if (niveau > 0) {
            query.append(" AND difficulte=");
            query.append(niveau);
        }
        
        return query.toString();
    }
    
    @Override
    public void creerTheme(Theme4ALSBdjDistante theme4als) {
        StringBuilder query = new StringBuilder();
        query.append(
                "INSERT INTO THEME_QALS (categorie,categorieRef,groupeCategorieRef,theme,difficulte,reference,club,dateReception,version,active) VALUES ('");
        query.append(DBUtils.escapeSql(theme4als.getCategorie4ALS()));
        query.append("',");
        query.append(theme4als.getCategorie4ALSRef());
        query.append(",");
        query.append(theme4als.getGroupeCategorie4ALS());
        query.append(",'");
        query.append(DBUtils.escapeSql(theme4als.getTheme()));
        query.append("',");
        query.append(theme4als.getDifficulte());
        query.append(",'");
        query.append(theme4als.getReference());
        query.append("','");
        query.append(DBUtils.escapeSql(theme4als.getClub()));
        query.append("','");
        query.append(theme4als.getDateEnvoi());
        query.append("',");
        query.append(theme4als.getVersion());
        query.append(",1);"); // question active

        executerUpdateOuInsert(query.toString());

        creerQuestions(theme4als.getReference(), theme4als.getQuestions());
    }

    private void creerQuestions(Long reference, Map<Integer, Question4ALSBdjDistante> questions) {
        for (Entry<Integer, Question4ALSBdjDistante> question4ALS : questions.entrySet()) {
            String requete = String.format("INSERT INTO QUESTION_QALS (seq,question,reponse,reference) VALUES ('%d','%s','%s',%d);",question4ALS.getKey(),DBUtils.escapeSql(question4ALS.getValue().getQuestion()),DBUtils.escapeSql(question4ALS.getValue().getReponse()), reference);
            
            executerUpdateOuInsert(requete);
        }
    }

    @Override
    public void desactiverTheme(String reference) {
        desactiverQuestion("QALS", reference);
    }

    @Override
    public void corrigerTheme(Theme4ALSBdjDistante theme4als) {
    		// Création de la requête
        String requete = String.format("UPDATE THEME_QALS SET difficulte=%d, theme='%s', club='%s', dateReception='%s', version=%d, categorie='%s', categorieRef=%d, groupeCategorieRef=%d WHERE reference=%d;",theme4als.getDifficulte(),DBUtils.escapeSql(theme4als.getTheme()),DBUtils.escapeSql(theme4als.getClub()),theme4als.getDateEnvoi(),theme4als.getVersion(),DBUtils.escapeSql(theme4als.getCategorie4ALS()), theme4als.getCategorie4ALSRef(),theme4als.getGroupeCategorie4ALS(),theme4als.getReference());
        executerUpdateOuInsert(requete);
        executerUpdateOuInsert(String.format("delete from QUESTION_QALS WHERE reference=%s;", theme4als.getReference()));

        creerQuestions(theme4als.getReference(), theme4als.getQuestions());
    }

    @Override
    public List<Anomalie> listerAnomalies(Long indexDebut) {
        return listerAnomalies("QALS", indexDebut);
    }

    @Override
    public List<Lecture> listerQuestionsLues(Long indexDebut) {
        return listerQuestionsLues("QALS", indexDebut);
    }

    @Override
    public Long recupererIndexMaxAnomalie() {
        return recupererIndexMaxAnomalie("QALS");
    }

    @Override
    public Long recupererIndexMaxLecture() {
        return recupererIndexMaxLecture("QALS");
    }

    @Override
    public Long recupererReferenceMaxQuestion() {
        return recupererReferenceMaxQuestion("QALS");
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void marquerThemePresente(String referenceTheme, String lecteur) {
        // Création de la requête
        String requete = String.format("INSERT INTO THEME_QALS_PRESENTE (reference,date_presentation,lecteur) VALUES (%s,'%s','%s');",referenceTheme,Utils.recupererDateHeureCourante(),lecteur);

        executerUpdateOuInsert(requete);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void marquerThemeJoue(String referenceTheme, String lecteur) {
        jouerQuestion("QALS", referenceTheme, lecteur);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void annulerMarquerThemeJoue(String referenceTheme, String lecteur) {
        executerUpdateOuInsert(String.format("DELETE FROM THEME_QALS_LECTURE where reference=%s;", referenceTheme));
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Map<String, Map<String, Long>> compterParGroupeCategorie() {
        // Création de la requête
        String requete = "SELECT groupeCategorieRef, difficulte, count(difficulte) FROM THEME_QALS WHERE active=1 GROUP BY groupeCategorieRef, difficulte order by groupeCategorieRef;";
        return compterParGroupeCategorie(requete);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Map<String, Map<String, Long>> compterParGroupeCategorieLue() {
        // Création de la requête
        String requete = "SELECT groupeCategorieRef, difficulte, count(difficulte) FROM THEME_QALS WHERE reference IN (SELECT reference FROM THEME_QALS_LECTURE)  GROUP BY groupeCategorieRef, difficulte order by groupeCategorieRef;";
        return compterParGroupeCategorie(requete);
    }

    private Map<String, Map<String, Long>> compterParGroupeCategorie(String requete) {

        ResultSetHandler<Map<String, Map<String, Long>>> h = new ResultSetHandler<Map<String, Map<String, Long>>>() {
            @Override
            public Map<String, Map<String, Long>> handle(ResultSet rs) throws SQLException {
                Map<String, Map<String, Long>> inventaireParGroupeCategorie = new HashMap<>();

                for (int i = 1; i <= 4; i++) {
                    Map<String, Long> mapGroupeCategorie = new HashMap<>();
                    for (int j = 1; j <= 4; j++) {
                        mapGroupeCategorie.put(String.valueOf(j), Long.valueOf(0));
                    }
                    inventaireParGroupeCategorie.put(String.valueOf(i), mapGroupeCategorie);
                }

                while (rs.next()) {
                    String groupeCategorie = rs.getString("groupeCategorieRef");
                    String difficulte = rs.getString("difficulte");
                    Long nbQuestion = rs.getLong(3);

                    Map<String, Long> mapTemp = inventaireParGroupeCategorie.get(groupeCategorie);
                    mapTemp.put(difficulte, nbQuestion);
                    inventaireParGroupeCategorie.put(groupeCategorie, mapTemp);
                }
                return inventaireParGroupeCategorie;
            }
        };

        return executerRequete(requete, h);
    }

}
