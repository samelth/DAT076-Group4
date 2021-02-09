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
import model.database.entity.Player;

/**
 *
 * @author Karl Svensson
 */
@Stateless
public class PlayerDAO extends AbstractDAO<Player> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;

  public PlayerDAO(){
    super(Player.class);
  }
  
  public List<Player>findUsersMatchingName() {
    throw new UnsupportedOperationException("Not yet implemented");
  }
  
  
}
