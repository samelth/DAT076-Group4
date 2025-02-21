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
import model.database.entity.Lobby;
import model.database.entity.QLobby;


/**
 *
 * @author karlsvensson
 */
@Stateless
public class LobbyDAO extends AbstractDAO<Lobby> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;

  public LobbyDAO() {
    super(Lobby.class);
  }

  public Lobby find(Lobby l) {
    return getEntityManager().find(l.getClass(), l.getLobby_id());
  }

  public Lobby findLobbyByHexLid(final String hexLid) {
    JPAQuery<Lobby> q = new JPAQuery<>(entityManager);
    QLobby lobby = QLobby.lobby;
    return q
            .select(lobby)
            .from(lobby)
            .where(lobby.lobby_id.eq(Integer.valueOf(hexLid, 16)))
            .fetchOne();
  }

}
