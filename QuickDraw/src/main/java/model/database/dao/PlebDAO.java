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
import model.database.entity.Pleb;
import model.database.entity.QPleb;

/**
 *
 * @author Karl Svensson
 */
@Stateless
public class PlebDAO extends AbstractDAO<Pleb> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;


  public PlebDAO(){
    super(Pleb.class);
  }

  public List<Pleb> findPlebsInSameLobby(final Lobby lobby) {
    final JPAQuery<List<Pleb>> query = new JPAQuery<>(entityManager);
    final QPleb pleb = QPleb.pleb;
    return query
            .select(pleb)
            .from(pleb)
            .where(pleb.lobby.lobby_id.eq(lobby.getLobby_id()))
            .fetch();
  }

  public List<Pleb> findPlebsInSameLobbySortedByScore(final Lobby lobby) {
    final JPAQuery<List<Pleb>> query = new JPAQuery<>(entityManager);
    final QPleb pleb = QPleb.pleb;
    return query
            .select(pleb)
            .from(pleb)
            .where(pleb.lobby.lobby_id.eq(lobby.getLobby_id()))
            .orderBy(pleb.score.desc())
            .fetch();
  }
  @Override
  public Pleb find(final Pleb p) {
    return getEntityManager().find(p.getClass(), p.getUser_id());
  }

}
