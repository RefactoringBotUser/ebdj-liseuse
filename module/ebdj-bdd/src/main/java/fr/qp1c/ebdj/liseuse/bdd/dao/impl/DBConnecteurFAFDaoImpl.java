package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.commons.dbutils.ResultSetHandler;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.mapper.MapperQuestion;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;

public class DBConnecteurFAFDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurFAFDao {

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<QuestionFAF> listerQuestionsJouable(int nbQuestion) {
        // Création de la requête
        StringBuilder query = new StringBuilder();
        query.append(
                "SELECT id,question,reponse,theme,difficulte,categorie,categorieRef,reference,version,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT active=1 AND EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference)");

        if (nbQuestion > 0) {
            query.append(" LIMIT ");
            query.append(nbQuestion);
        }
        query.append(";");

        ResultSetHandler<List<QuestionFAF>> h = new ResultSetHandler<List<QuestionFAF>>() {
            @Override
            public List<QuestionFAF> handle(ResultSet rs) throws SQLException {
                List<QuestionFAF> listeQuestionsAJouer = new ArrayList<>();

                while (rs.next()) {
                    // Ajouter la question à la liste
                    listeQuestionsAJouer.add(MapperQuestion.convertirQuestionFAF(rs));
                }
                return listeQuestionsAJouer;
            }
        };

        return executerRequete(query.toString(), h);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public QuestionFAF donnerQuestionsJouable(List<Long> categoriesAExclure, Long niveauMin, Long niveauMax) {
        // Création de la requête
        String requete = String.format(
                "SELECT id,question,reponse,theme,difficulte,categorie,categorieRef,reference,version,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference) AND difficulte>=%d AND difficulte<=%d",niveauMin, niveauMax);

        return donnerQuestionsJouable(requete, categoriesAExclure);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public QuestionFAF donnerQuestionsJouable(List<Long> categoriesAExclure, Long niveau) {
        // Création de la requête
        String requete = String.format(
                "SELECT id,question,reponse,theme,difficulte,categorie,categorieRef,reference,version,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference) AND difficulte=%d", niveau );
       
        return donnerQuestionsJouable(requete, categoriesAExclure);
    }

    private QuestionFAF donnerQuestionsJouable(String requete, List<Long> categoriesAExclure) {
        StringBuilder query = new StringBuilder();
        query.append(requete);

        if (categoriesAExclure != null && !categoriesAExclure.isEmpty()) {
            query.append(" AND Q_FAF.categorieRef NOT IN (");
            StringJoiner clauseIn = new StringJoiner(",", "", "");

            for (Long categorie : categoriesAExclure) {
                clauseIn.add(categorie.toString());
            }
            query.append(clauseIn);
            query.append(')');
        }
        query.append(" LIMIT 1;");

        ResultSetHandler<QuestionFAF> h = new ResultSetHandler<QuestionFAF>() {
            @Override
            public QuestionFAF handle(ResultSet rs) throws SQLException {
                QuestionFAF question = null;

                if (rs.next()) {
                    // Convertir chaque question
                    question = MapperQuestion.convertirQuestionFAF(rs);
                }
                return question;
            }
        };

        return executerRequete(query.toString(), h);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Map<String, Long> compterParCategorie() {
        return compter("SELECT categorie, count(1) FROM QUESTION_FAF GROUP BY categorie order by categorie;");
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Map<String, Long> compterParCategorieLue() {
        return compter(
                "SELECT categorie, count(1) FROM QUESTION_FAF Q_FAF WHERE EXISTS(SELECT DISTINCT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference) GROUP BY categorie order by categorie;");
    }

    private Map<String, Long> compter(String requete) {
        ResultSetHandler<Map<String, Long>> h = new ResultSetHandler<Map<String, Long>>() {
            @Override
            public Map<String, Long> handle(ResultSet rs) throws SQLException {
                Map<String, Long> inventaireParCategorie = new HashMap<>();

                while (rs.next()) {
                    String categorie = rs.getString(1);
                    Long nbQuestion = rs.getLong(2);

                    inventaireParCategorie.put(categorie, nbQuestion);
                }
                return inventaireParCategorie;
            }
        };

        return executerRequete(requete, h);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void creerQuestion(QuestionFAFBdjDistante questionFaf) {
        // Création de la requête
        StringBuilder query = new StringBuilder();
        query.append(
                "INSERT INTO QUESTION_FAF (categorie,categorieRef,theme,question,reponse,difficulte,reference,club,dateReception,version,active) VALUES ('");
        query.append(DBUtils.escapeSql(questionFaf.getCategorieFAF()));
        query.append("',");
        query.append(questionFaf.getCategorieFAFRef());
        query.append(",'");
        query.append(DBUtils.escapeSql(questionFaf.getTheme()));
        query.append("','");
        query.append(DBUtils.escapeSql(questionFaf.getQuestion()));
        query.append("','");
        query.append(DBUtils.escapeSql(questionFaf.getReponse()));
        query.append("',");
        query.append(questionFaf.getDifficulte());
        query.append(",'");
        query.append(questionFaf.getReference());
        query.append("','");
        query.append(DBUtils.escapeSql(questionFaf.getClub()));
        query.append("','");
        query.append(questionFaf.getDateEnvoi());
        query.append("',");
        query.append(questionFaf.getVersion());
        query.append(",1);"); // question active

        executerUpdateOuInsert(query.toString());
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void corrigerQuestion(QuestionFAFBdjDistante questionFaf) {
        // Création de la requête
        String requete = String.format("UPDATE QUESTION_FAF SET categorie='%s', categorieRef=%d, theme='%s', question='%s', reponse='%s', difficulte=%d, club='%s', dateReception='%s', version=%d WHERE reference=%d;",questionFaf.getCategorieFAF(),questionFaf.getCategorieFAFRef(),DBUtils.escapeSql(questionFaf.getTheme()),DBUtils.escapeSql(questionFaf.getQuestion()),DBUtils.escapeSql(questionFaf.getReponse()),questionFaf.getDifficulte(),DBUtils.escapeSql(questionFaf.getClub()),questionFaf.getDateEnvoi(),questionFaf.getVersion(),questionFaf.getReference());

        executerUpdateOuInsert(requete);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbQuestion() {
        return compterNbQuestion("FAF");
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int compterNbQuestionLue() {
        return compterNbQuestionLue("FAF");
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void jouerQuestion(String referenceQuestion, String lecteur) {
        jouerQuestion("FAF", referenceQuestion, lecteur);
    }

    @Override
    public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur) {
        signalerAnomalie("FAF", reference, version, anomalie, lecteur);
    }

    @Override
    public void desactiverQuestion(String reference) {
        desactiverQuestion("FAF", reference);
    }

    @Override
    public List<Lecture> listerQuestionsLues(Long indexDebut) {
        return listerQuestionsLues("FAF", indexDebut);
    }

    @Override
    public List<Anomalie> listerAnomalies(Long indexDebut) {
        return listerAnomalies("FAF", indexDebut);
    }

    @Override
    public Long recupererIndexMaxAnomalie() {
        return recupererIndexMaxAnomalie("FAF");
    }

    @Override
    public Long recupererIndexMaxLecture() {
        return recupererIndexMaxLecture("FAF");
    }

    @Override
    public Long recupererReferenceMaxQuestion() {
        return recupererReferenceMaxQuestion("FAF");
    }

}
