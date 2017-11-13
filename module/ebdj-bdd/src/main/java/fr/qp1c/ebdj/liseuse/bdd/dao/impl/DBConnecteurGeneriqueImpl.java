package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.configuration.Configuration;
import fr.qp1c.ebdj.liseuse.bdd.dao.mapper.MapperQuestion;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;

public class DBConnecteurGeneriqueImpl {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurGeneriqueImpl.class);

    protected Long recupererIndexMaxAnomalie(String type) {
        return recupererIndexMax(donnerPrefixeTable(type) + "_ANOMALIE");
    }

    protected Long recupererIndexMaxLecture(String type) {
        return recupererIndexMax(donnerPrefixeTable(type) + "_LECTURE");
    }

    protected Long recupererIndexMax(String table) {
        // Création de la requête
        String requete = String.format("SELECT max(id) FROM %s;", table);

        ResultSetHandler<Long> h = new ResultSetHandler<Long>() {
            @Override
            public Long handle(ResultSet rs) throws SQLException {
                Long indexMax = Long.valueOf(0);

                if (rs.next()) {
                    indexMax = rs.getLong(1);
                }
                return indexMax;
            }
        };

        return executerRequete(requete, h);
    }

    protected Long recupererReferenceMaxQuestion(String type) {
        // Création de la requête
        String requete = String.format("SELECT max(reference) FROM %s;", donnerPrefixeTable(type));

        ResultSetHandler<Long> h = new ResultSetHandler<Long>() {
            @Override
            public Long handle(ResultSet rs) throws SQLException {
                Long referenceMax = Long.valueOf(0);

                if (rs.next()) {
                    referenceMax = rs.getLong(1);
                }
                return referenceMax;
            }
        };

        return executerRequete(requete, h);
    }

    private String donnerPrefixeTable(String type) {
        if ("QALS".equals(type)) {
            return "THEME_" + type;
        }
        return "QUESTION_" + type;
    }

    protected List<Anomalie> listerAnomalies(String type, Long indexDebut) {
        // Création de la requête
        String requete = String.format(
                "SELECT reference,version,date_anomalie,type_anomalie,cause,lecteur FROM %s_ANOMALIE WHERE id>%d ORDER BY id ASC;",
                donnerPrefixeTable(type), indexDebut);

        ResultSetHandler<List<Anomalie>> h = new ResultSetHandler<List<Anomalie>>() {
            @Override
            public List<Anomalie> handle(ResultSet rs) throws SQLException {
                List<Anomalie> listeAnomalies = new ArrayList<>();

                while (rs.next()) {
                    // Ajouter la question à la liste
                    listeAnomalies.add(MapperQuestion.convertirAnomalie(rs));
                }
                return listeAnomalies;
            }
        };

        return executerRequete(requete, h);
    }

    public List<Lecture> listerQuestionsLues(String type, Long indexDebut) {
        // Création de la requête
        String requete = String.format(
                "SELECT reference, date_lecture, lecteur FROM %s_LECTURE WHERE id>%d ORDER BY id ASC;",
                donnerPrefixeTable(type), indexDebut);

        ResultSetHandler<List<Lecture>> h = new ResultSetHandler<List<Lecture>>() {
            @Override
            public List<Lecture> handle(ResultSet rs) throws SQLException {
                List<Lecture> listeQuestionsLues = new ArrayList<>();

                while (rs.next()) {
                    // Ajouter la question à la liste
                    listeQuestionsLues.add(MapperQuestion.convertirLecture(rs));
                }
                return listeQuestionsLues;
            }
        };

        return executerRequete(requete, h);
    }

    public void desactiverQuestion(String type, String reference) {
        // Création de la requête
        String requete = String.format("UPDATE %s SET active=0 WHERE reference=%s;", donnerPrefixeTable(type),
                reference);

        executerUpdateOuInsert(requete);
    }

    public void jouerQuestion(String type, String referenceQuestion, String lecteur) {
        // Création de la requête
        String requete = String.format(
                "INSERT INTO %s_LECTURE (reference, date_lecture,lecteur) VALUES (%s,'%s','%s');",
                donnerPrefixeTable(type), referenceQuestion, Utils.recupererDateHeureCourante(), lecteur);

        executerUpdateOuInsert(requete);
    }

    public void signalerAnomalie(String type, String reference, Long version, SignalementAnomalie anomalie,
            String lecteur) {
        // Création de la requête
        String requete = String.format(
                "INSERT INTO %s_ANOMALIE (reference,version,date_anomalie,type_anomalie,cause,lecteur) VALUES ('%s',%d,'%s',%d,'%s','%s');",
                donnerPrefixeTable(type), reference, version, Utils.recupererDateHeureCourante(),
                anomalie.getTypeAnomalie().ordinal(), DBUtils.escapeSql(anomalie.getDescription()),
                DBUtils.escapeSql(lecteur));

        executerUpdateOuInsert(requete);
    }

    protected int compterNbQuestion(String type) {
        return compterNbQuestion(type, null);
    }

    protected int compterNbQuestion(String type, String complement) {
        // Création de la requête
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(1) FROM " + donnerPrefixeTable(type) + " WHERE active=1");
        if (complement != null) {
            query.append(complement);
        }
        query.append(";");

        ResultSetHandler<Integer> h = new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                int nbQuestion = 0;

                if (rs.next()) {
                    nbQuestion = rs.getInt(1);
                }
                return Integer.valueOf(nbQuestion);
            }
        };

        return executerRequete(query.toString(), h);
    }

    /**
     * {@inheritDoc}
     * 
     */
    protected int compterNbQuestionLue(String type) {
        return compterNbQuestionLue(type, null);
    }

    protected int compterNbQuestionLue(String type, String complement) {
        // Création de la requête
        StringBuilder query = new StringBuilder();
        query.append(String.format(
                "SELECT count(1) FROM %s Q WHERE EXISTS(SELECT DISTINCT * FROM %s_LECTURE Q_J WHERE Q.reference=Q_J.reference)",
                donnerPrefixeTable(type), donnerPrefixeTable(type)));
        if (complement != null) {
            query.append(complement);
        }
        query.append(";");

        ResultSetHandler<Integer> h = new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                int nbQuestion = 0;

                if (rs.next()) {
                    nbQuestion = rs.getInt(1);
                }
                return Integer.valueOf(nbQuestion);
            }
        };

        return executerRequete(query.toString(), h);
    }

    protected int compterNbQuestionPresente(String type, String complement) {
        // Création de la requête
        StringBuilder query = new StringBuilder();
        query.append(String.format(
                "SELECT count(1) FROM %s Q WHERE EXISTS(SELECT DISTINCT * FROM %s_PRESENTE Q_T WHERE Q.reference=Q_T.reference)",
                donnerPrefixeTable(type), donnerPrefixeTable(type)));
        if (complement != null) {
            query.append(complement);
        }
        query.append(";");

        ResultSetHandler<Integer> h = new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                int nbQuestion = 0;

                if (rs.next()) {
                    nbQuestion = rs.getInt(1);
                }
                return Integer.valueOf(nbQuestion);
            }
        };

        return executerRequete(query.toString(), h);
    }

    protected void executerUpdateOuInsert(String requete) {
        // Connexion à la base de données SQLite
        Connection connection = null;
        Statement stmt = null;
        try {
            if (Configuration.getInstance().isTest()) {
                Class.forName("org.h2.Driver");
            } else {
                Class.forName("org.sqlite.JDBC");
            }

            connection = DriverManager.getConnection(Configuration.getInstance().getUrl(),
                    Configuration.getInstance().getUser(), Configuration.getInstance().getPassword());

            stmt = connection.createStatement();

            LOGGER.debug(requete);

            // Executer la requête
            stmt.executeUpdate(requete);
        } catch (Exception e) {
            LOGGER.error("An error has occured :", e);
            throw new DBManagerException();
        } finally {
            if (stmt != null) {
                // Fermeture des connections.
                try {
                    stmt.close();
                } catch (SQLException e) {
                    LOGGER.error("An error has occured :", e);
                }
            }
            if (connection != null) {
                // Fermeture des connections.
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("An error has occured :", e);
                }
            }
        }
    }

    public <T> T executerRequete(String requete, ResultSetHandler<T> h) {

        T result = null;

        // Connexion à la base de données SQLite
        Connection connection = null;
        Statement stmt = null;
        try {
            if (Configuration.getInstance().isTest()) {
                Class.forName("org.h2.Driver");
            } else {
                Class.forName("org.sqlite.JDBC");
            }

            connection = DriverManager.getConnection(Configuration.getInstance().getUrl(),
                    Configuration.getInstance().getUser(), Configuration.getInstance().getPassword());

            LOGGER.trace("Connexion  avec succès à la base de données {}", Configuration.getInstance().getUrl());

            stmt = connection.createStatement();

            LOGGER.debug(requete);

            // Executer la requête
            ResultSet rs = stmt.executeQuery(requete);
            result = h.handle(rs);
        } catch (Exception e) {
            LOGGER.error("An error has occured :", e);
            throw new DBManagerException();
        } finally {
            if (stmt != null) {
                // Fermeture des connections.
                try {
                    stmt.close();
                } catch (SQLException e) {
                    LOGGER.error("An error has occured :", e);
                }
            }
            if (connection != null) {
                // Fermeture des connections.
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("An error has occured :", e);
                }
            }
        }

        return result;
    }

}
