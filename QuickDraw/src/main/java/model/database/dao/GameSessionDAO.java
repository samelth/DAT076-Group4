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
import model.database.entity.GameSession;
import model.database.entity.QGameSession;

/**
 *
 * @author karlsvensson
 */
@Stateless
public class GameSessionDAO extends AbstractDAO<GameSession> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
    
  public GameSessionDAO(){
    super(GameSession.class);
  }
  
  public GameSession findGameSessionByGameId(int gid) {
    JPAQuery<GameSession> q = new JPAQuery<>(entityManager);
    QGameSession gameSession = QGameSession.gameSession;
    return q
            .select(gameSession)
            .from(gameSession)
            .where(gameSession.game_id.eq(gid))
            .fetchOne();
  }
}
