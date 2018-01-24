package main.java.sample.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gamestats")
public class GameStats implements Serializable {
    @Id
    @Column(name = "GameID", unique = true)
    int GameID;

    @Column(name = "PointsPlayer")
    int PointsPlayer;

    @Column(name = "PointsDealer")
    int PointsDealer;

    @Column(name = "Winner", length = 10)
    String Winner;

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public int getPointsPlayer() {
        return PointsPlayer;
    }

    public void setPointsPlayer(int pointsPlayer) {
        PointsPlayer = pointsPlayer;
    }

    public int getPointsDealer() {
        return PointsDealer;
    }

    public void setPointsDealer(int pointsDealer) {
        PointsDealer = pointsDealer;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    @Override
    public String toString() {
        return "Game: " + GameID +
                "\tPointsPlayer: " + PointsPlayer +
                "\tPointsDealer: " + PointsDealer +
                "\tWinner: " + Winner;
    }
}
