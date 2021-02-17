/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import model.database.entity.Player;
import model.database.entity.Lobby;
import java.util.List;
import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
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

/**
 *
 * @author Karl Svensson <Svensson.Karl@iCloud.com>
 */
@RunWith(Arquillian.class)
public class LobbyDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(LobbyDAO.class, Lobby.class, Player.class, GameSession.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private LobbyDAO lobbyDAO;
  private static final int NR_OF_INSERTED_LOBBIES = 3;
   
  @Before
  public void init() {
    for (int i = 0; i < NR_OF_INSERTED_LOBBIES; i++){
    lobbyDAO.create(new Lobby());
    }
  }
 
  @Test 
  public void checkThatLobbyDoesNotExistAfterRemove() {
    final List<Lobby> listBeforeRemove = lobbyDAO.findAll();
    final Lobby lobbyRemove = listBeforeRemove.get(0);
    lobbyDAO.remove(lobbyRemove); 
    final List<Lobby> listAfterRemove = lobbyDAO.findAll();
    Assert.assertFalse(listAfterRemove.contains(lobbyRemove));
  }
  
  @Test 
  public void checkThatNumberOfLobbiesInsertedEqualsTheCountOfTheTable() {
    Assert.assertEquals(NR_OF_INSERTED_LOBBIES , lobbyDAO.count());
  }
  
  @After
  public void tearDown() {
    lobbyDAO.findAll().forEach(lob -> {
      lobbyDAO.remove(lob);
    });
  }
}
