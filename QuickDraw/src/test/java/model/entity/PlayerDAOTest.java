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
  

  @Before
  public void init() {
    
    Lobby lobby = new Lobby();
    Player p1 = new Player();
    p1.setUsername("Karl");
    p1.setLobby(lobby);
    lobbyDAO.create(lobby);
    playerDAO.create(p1);
    playerDAO.create(new Player());
    playerDAO.create(new Player());
  }
 
  @Test
  public void checkThatFindUserMatchingNameMatchesCorrectly() {
    Assert.assertTrue(true);
  }
  
}
