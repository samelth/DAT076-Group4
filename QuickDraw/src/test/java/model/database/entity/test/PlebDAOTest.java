package model.database.entity.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import model.database.entity.Pleb;
import model.database.entity.Lobby;
import java.util.List;
import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Picture;
import model.database.entity.Word;
import model.database.entity.Game;
import model.database.entity.Game;
import model.database.entity.Lobby;
import model.database.entity.Picture;
import model.database.entity.Pleb;
import model.database.entity.Word;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Karl Svensson
 */
@RunWith(Arquillian.class)
public class PlebDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(PlebDAO.class, Pleb.class, Lobby.class, LobbyDAO.class, Game.class, Word.class, Picture.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private PlebDAO plebDAO;
  @EJB
  private LobbyDAO lobbyDAO;
  
  private Pleb p1;
  private Pleb p2;
  private Pleb p3;
  
  private Lobby l1;
  private Lobby l2;
  
  @Before
  public void init() {
    p1 = new Pleb();
    p2 = new Pleb();
    p3 = new Pleb();
    l1 = new Lobby();
    l2 = new Lobby();
  }
  
  @After
  public void clean() {
    plebDAO.removeAll();
    lobbyDAO.removeAll();
  }
  
  @Test
  public void testFindPlebsInSameLobby() {
    l1.addPleb(p1);
    l1.addPleb(p2);
    l2.addPleb(p3);
    lobbyDAO.create(l1);
    lobbyDAO.create(l2);
    final List<Pleb> ps1 = plebDAO.findPlebsInSameLobby(l1);
    final List<Pleb> ps2 = plebDAO.findPlebsInSameLobby(l2);
    Assert.assertTrue(ps1.contains(p1) && ps1.contains(p2));
    Assert.assertTrue(ps2.contains(p3));
    Assert.assertFalse(ps1.contains(p3) || ps2.contains(p1) || ps2.contains(p2));
  }
  
  @Test
  public void testFindPlebsInSameLobbySortedByScore() {
    p1.setScore(1);
    p2.setScore(2);
    p3.setScore(3);
    l1.addPleb(p1);
    l1.addPleb(p2);
    l1.addPleb(p3);
    lobbyDAO.create(l1);
    final List<Pleb> ps1 = plebDAO.findPlebsInSameLobbySortedByScore(l1);
    for(int i = 0; i < ps1.size() - 1; i++) {
      Assert.assertTrue(ps1.get(i).getScore() > ps1.get(i + 1).getScore());
    }
    p1.setScore(6);
    p2.setScore(5);
    p3.setScore(4);
    l1.updatePleb(p1);
    l1.updatePleb(p2);
    l1.updatePleb(p3);
    lobbyDAO.update(l1);
    final List<Pleb> ps2 = plebDAO.findPlebsInSameLobbySortedByScore(l1);
    for(int i = 0; i < ps2.size() - 1; i++) {
      Assert.assertTrue(ps2.get(i).getScore() > ps2.get(i + 1).getScore());
    }
  }
  
  @Test
  public void testFind() {
    plebDAO.create(p1);
    plebDAO.create(p2);
    plebDAO.create(p3);
    Assert.assertEquals(p1, plebDAO.find(p1));
    Assert.assertEquals(p2, plebDAO.find(p2));
    Assert.assertEquals(p3, plebDAO.find(p3));
  }
  
}
