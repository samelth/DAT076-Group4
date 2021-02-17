/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.GameSession;
import model.database.entity.Lobby;

/**
 *
 * @author karlsvensson
 */
@Stateless
public class GameSessionDAO extends AbstractDAO {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
    
  public GameSessionDAO(){
    super(GameSession.class);
  }
  
  public List<Lobby>findUsersMatchingName() {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
