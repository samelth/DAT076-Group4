/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.dao;

import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.Player;
import model.database.entity.QPlayer;

/**
 *
 * @author Karl Svensson
 */
@Stateless
public class PlayerDAO extends AbstractDAO<Integer,Player> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
  

  public PlayerDAO(){
    super(Player.class);
  }
  
  public Player getPlayer(int user_id) {
    JPAQuery<?> query = new JPAQuery<Void>(entityManager);
    QPlayer player = QPlayer.player;
    return query
            .select(player)
            .from(player)
            .where(player.user_id.eq(user_id))
            .fetchOne();
  }
  
  public List<Player>findUsersInSameLobby(int lid) {
    JPAQuery<?> query = new JPAQuery<Void>(entityManager);
    QPlayer player = QPlayer.player;
    List<Player> inLobby = query
            .select(player)
            .from(player)
            .where(player.lobby.lid.eq(lid))
            .fetch();
    return inLobby;
  }  
}
