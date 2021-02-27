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
import model.database.entity.Lobby;
import model.database.entity.Player;
import model.database.entity.QPlayer;

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
  
  public List<Player> findUsersInSameLobby(Lobby lobby) {
    JPAQuery<List<Player>> query = new JPAQuery<>(entityManager);
    QPlayer player = QPlayer.player;
    return query
            .select(player)
            .from(player)
            .where(player.lobby.lid.eq(lobby.getLid()))
            .fetch();
  }
  
  public List<Player> findUsersInSameLobbySortedByScore(Lobby lobby){
    JPAQuery<List<Player>> query = new JPAQuery<>(entityManager);
    QPlayer player = QPlayer.player;
    return query
            .select(player)
            .from(player)
            .where(player.lobby.lid.eq(lobby.getLid()))
            .orderBy(player.score.desc())
            .fetch();
  }
  
  public Player findPlayer(Player p) {
    JPAQuery<Player> query = new JPAQuery<>(entityManager);
    QPlayer player = QPlayer.player;
    return query
            .select(player)
            .from(player)
            .where(player.user_id.eq(p.getUser_id()))
            .fetchOne();
  }
  
}
