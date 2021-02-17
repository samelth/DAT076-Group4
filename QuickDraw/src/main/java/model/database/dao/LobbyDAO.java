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
import model.database.entity.Lobby;

/**
 *
 * @author karlsvensson
 */
@Stateless
public class LobbyDAO extends AbstractDAO<Integer,Lobby> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
    
  public LobbyDAO(){
    super(Lobby.class);
  }
  
  public List<Lobby>findUsersMatchingName() {
    throw new UnsupportedOperationException("Not yet implemented");
  }


}
