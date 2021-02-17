package model.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import model.database.entity.Player;
import model.database.entity.Lobby;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
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
      .addClasses(PlayerDAO.class, Player.class, Lobby.class, LobbyDAO.class, GameSession.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private PlayerDAO playerDAO;
  @EJB
  private LobbyDAO lobbyDAO;
  
  @Inject
  private UserTransaction utx;

  
  private static final int NR_OF_INSERTED_PLAYERS = 3;
  private static final int SCORE_TOTAL = 600;
  private Lobby lobby;

  @Before
  public void init() throws Exception {
    utx.begin();
    
    lobby = new Lobby();
    Player p1 = new Player();
    p1.setUsername("player1");
    p1.setScore(100);
    p1.setLobby(lobby);
    Player p2 = new Player();
    p2.setUsername("player2");
    p2.setScore(200);
    p2.setLobby(lobby);
    Player p3 = new Player();
    p3.setUsername("player3");
    p3.setScore(300);
    p3.setLobby(lobby);
    
    lobbyDAO.create(lobby);
    playerDAO.create(p1);
    playerDAO.create(p2);
    playerDAO.create(p3);
    
    List<Lobby> k = lobbyDAO.findAll();
    k.forEach(lobbyDAO::refresh);
    
    utx.commit();
  }
  
  @After
  public void tearDown() {
    playerDAO.findAll().forEach(p -> {
      playerDAO.remove(p);
    });
    lobbyDAO.remove(lobby);
  }
  
  @Test
  public void checkNumberOfPlayers() {
    Assert.assertEquals(NR_OF_INSERTED_PLAYERS, playerDAO.count());
  }
  
  @Test
  public void checkTotalScore() {
    int t = 0;
    for (Player p : lobby.getPlayers()){
      t = t + p.getScore();
    }
    Assert.assertEquals(SCORE_TOTAL, t);
  }
  
  @Test
  public void checkUsernames() {
    final List<String> ss = new ArrayList<>();
    final List<Player> ps = lobby.getPlayers();
    for(int i = 0; i < NR_OF_INSERTED_PLAYERS; i++) {
      ss.add(ps.get(i).getUsername());
    }
    Assert.assertTrue(ss.contains("player1") && ss.contains("player2") && ss.contains("player3"));
  }
  
  @Test
  public void checkGetPlayer(){
    Player player = new Player();
    player.setUsername("userTest");
    playerDAO.create(player);
    Player p = playerDAO.getPlayer(player.getUser_id());
    Assert.assertEquals("userTest", p.getUsername());
  }
}
