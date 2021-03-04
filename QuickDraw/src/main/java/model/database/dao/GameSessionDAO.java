/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.GameSession;

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
  
  public GameSession find(GameSession g) {
    return getEntityManager().find(g.getClass(), g.getGame_id());
  }
}
