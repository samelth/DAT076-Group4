package model.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import model.database.entity.Player;
import model.database.entity.Lobby;
import java.util.List;
import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
import model.database.entity.Drawing;
import model.database.entity.DrawingWord;
import model.database.entity.GameSession;
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
public class PlayerDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(PlayerDAO.class, Player.class, Lobby.class, LobbyDAO.class, GameSession.class, DrawingWord.class, Drawing.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private PlayerDAO playerDAO;
  @EJB
  private LobbyDAO lobbyDAO;
  
  private Player p1;
  private Player p2;
  private Player p3;
  
  private Lobby l1;
  private Lobby l2;
  
  @Before
  public void init() {
    p1 = new Player();
    p2 = new Player();
    p3 = new Player();
    l1 = new Lobby();
    l2 = new Lobby();
  }
  
  @After
  public void clean() {
    playerDAO.removeAll();
    lobbyDAO.removeAll();
  }
  
  @Test
  public void testFindUsersInSameLobby() {
    p1.setLobby(l1);
    p2.setLobby(l1);
    p3.setLobby(l2);
    lobbyDAO.create(l1);
    lobbyDAO.create(l2);
    playerDAO.create(p1);
    playerDAO.create(p2);
    playerDAO.create(p3);
    final List<Player> ps1 = playerDAO.findUsersInSameLobby(l1);
    final List<Player> ps2 = playerDAO.findUsersInSameLobby(l2);
    Assert.assertTrue(ps1.contains(p1) && ps1.contains(p2));
    Assert.assertTrue(ps2.contains(p3));
    Assert.assertFalse(ps1.contains(p3) || ps2.contains(p1) || ps2.contains(p2));
  }
  
  @Test
  public void testFindUsersInSameLobbySortedByScore() {
    p1.setLobby(l1);
    p2.setLobby(l1);
    p3.setLobby(l1);
    p1.setScore(1);
    p2.setScore(2);
    p3.setScore(3);
    lobbyDAO.create(l1);
    playerDAO.create(p1);
    playerDAO.create(p2);
    playerDAO.create(p3);
    final List<Player> ps1 = playerDAO.findUsersInSameLobbySortedByScore(l1);
    for(int i = 0; i < ps1.size() - 1; i++) {
      Assert.assertTrue(ps1.get(i).getScore() > ps1.get(i + 1).getScore());
    }
    p1.setScore(6);
    p2.setScore(5);
    p3.setScore(4);
    playerDAO.update(p1);
    playerDAO.update(p2);
    playerDAO.update(p3);
    final List<Player> ps2 = playerDAO.findUsersInSameLobbySortedByScore(l1);
    for(int i = 0; i < ps2.size() - 1; i++) {
      Assert.assertTrue(ps2.get(i).getScore() > ps2.get(i + 1).getScore());
    }
  }
  
  @Test
  public void testFindPlayer() {
    playerDAO.create(p1);
    playerDAO.create(p2);
    playerDAO.create(p3);
    Assert.assertEquals(p1, playerDAO.findPlayer(p1));
    Assert.assertEquals(p2, playerDAO.findPlayer(p2));
    Assert.assertEquals(p3, playerDAO.findPlayer(p3));
  }
}
