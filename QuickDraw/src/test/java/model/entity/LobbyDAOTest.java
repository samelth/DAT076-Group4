/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import model.database.entity.Player;
import model.database.entity.Lobby;
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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Karl Svensson <Svensson.Karl@iCloud.com>
 */
@RunWith(Arquillian.class)
public class LobbyDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(LobbyDAO.class, Lobby.class, Player.class, PlayerDAO.class, GameSession.class, DrawingWord.class, Drawing.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private LobbyDAO lobbyDAO;
  @EJB
  private PlayerDAO playerDAO;
  
  private Lobby l;
  private Player p;
   
  @Before
  public void init() {
    l = new Lobby();
    p = new Player();
  }
  
  @After
  public void clean() {
    lobbyDAO.removeAll();
    playerDAO.removeAll(); 
  }

  @Test
  public void checkFindLobby() {
    lobbyDAO.create(l);
    Assert.assertEquals(l, lobbyDAO.findLobby(l));
  }
  
  @Test
  public void checkFindLobbyByHexLid() {
    lobbyDAO.create(l);
    int lid = lobbyDAO.findLobby(l).getLid();
    String hexLid = Integer.toHexString(lid);
    Assert.assertEquals(l, lobbyDAO.findLobbyByHexLid(hexLid));
  }

  
  @Test 
  public void testLobbyCascade() {
    l.addPlayer(p); 
    lobbyDAO.create(l);
    
    final boolean lobbyListHasPlayer = lobbyDAO.findLobby(l).getPlayers().contains(p);
    final boolean playerInPlayerTable = playerDAO.findPlayer(p).equals(p);
    
    assertTrue(lobbyListHasPlayer);
    assertTrue(playerInPlayerTable);
  }
}
