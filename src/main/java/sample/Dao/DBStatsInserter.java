package main.java.sample.Dao;

import main.java.sample.Models.GameStats;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DBStatsInserter implements IDBInserter {

    private int Id;
    private int PointsPlayer;
    private int PointsDealer;
    private String Winner;

    static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("JavaHelps");

    @Override
    public void addToDatabase() {
        GameStats gameStats = new GameStats();
        gameStats.setGameID(Id);
        gameStats.setPointsPlayer(PointsPlayer);
        gameStats.setPointsDealer(PointsDealer);
        gameStats.setWinner(Winner);

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(gameStats);
            transaction.commit();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }

            ex.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public DBStatsInserter(int id, int pointsPlayer, int pointsDealer, String winner) {
        this.Id = id;
        this.PointsPlayer = pointsPlayer;
        this.PointsDealer = pointsDealer;
        this.Winner = winner;
    }
}
