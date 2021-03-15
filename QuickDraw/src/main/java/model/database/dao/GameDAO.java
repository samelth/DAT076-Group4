/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.dao;

import com.querydsl.jpa.impl.JPAQuery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.Game;
import model.database.entity.Lobby;
import model.database.entity.QGame;

/**
 *
 * @author karlsvensson
 */
@Stateless
public class GameDAO extends AbstractDAO<Game> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;

  public GameDAO(){
    super(Game.class);
  }

  @Override
  public Game find(final Game g) {
    return getEntityManager().find(g.getClass(), g.getGame_id());
  }

  public Game findGameByLobby(final Lobby l) {
    JPAQuery<Game> query = new JPAQuery<>(entityManager);
    QGame game = QGame.game;
    return query
            .select(game)
            .from(game)
            .where(game.lobby.eq(l))
            .fetchOne();
  }
}
