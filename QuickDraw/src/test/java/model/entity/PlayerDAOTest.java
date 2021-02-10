package model.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import model.database.entity.Player;
import model.database.entity.Lobby;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
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
      .addClasses(PlayerDAO.class, Player.class, Lobby.class, LobbyDAO.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private PlayerDAO playerDAO;
  @EJB
  private LobbyDAO lobbyDAO;
  
  private static final int NR_OF_INSERTED_PLAYERS = 3;

  @Before
  public void init() {
    
    Lobby lobby = new Lobby();
    Player p1 = new Player();
    p1.setUsername("Karl");
    p1.setLobby(lobby);
    p1.setScore(9001);
    p1.setJudge(false);
    Player p2 = new Player();
    p2.setUsername("Fawzi");
    p2.setLobby(lobby);
    p2.setScore(2);
    p2.setJudge(true);
    Player p3 = new Player();
    p3.setUsername("Samuel");
    p3.setLobby(lobby);
    p3.setScore(3);
    p3.setJudge(false);
    lobbyDAO.create(lobby);
    playerDAO.create(p1);
    playerDAO.create(p2);
    playerDAO.create(p3);
  }
  
  @Test
  public void checkPlayerCount() {
    Assert.assertEquals(NR_OF_INSERTED_PLAYERS, playerDAO.count());
  }

}
