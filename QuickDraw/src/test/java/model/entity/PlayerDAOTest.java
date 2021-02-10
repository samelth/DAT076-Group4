package model.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import model.database.entity.Player;
import model.database.entity.Lobby;
import java.util.List;
import java.util.Map;
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
      .addClasses(PlayerDAO.class, Player.class, Lobby.class, LobbyDAO.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private PlayerDAO playerDAO;
  @EJB
  private LobbyDAO lobbyDAO;
  
  private static final int NR_OF_INSERTED_PLAYERS = 3;
  private static final int SCORE_TOTAL = 9006;

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
  
  @After
  public void tearDown() {
    playerDAO.findAll().forEach(p -> {
      playerDAO.remove(p);
    });
  }
  
  @Test
  public void checkPlayerCountAndAddWordsToMakeMethodNameConventionCompliant() {
    Assert.assertEquals(NR_OF_INSERTED_PLAYERS, playerDAO.count());
  }
  
  @Test
  public void checkScoreTotalAndAddWordsToMakeMethodNameConventionCompliant() {
    int t = 0;
    final List<Player> ps = playerDAO.findAll();
    for(int i = 0; i < NR_OF_INSERTED_PLAYERS; i++) {
      t += ps.get(i).getScore();
    }
    Assert.assertEquals(SCORE_TOTAL, t);
  }
  
  @Test
  public void checkUsernamesAndAddWordsToMakeMethodNameConventionCompliant() {
    final List<String> ss = new ArrayList<>();
    final List<Player> ps = playerDAO.findAll();
    for(int i = 0; i < NR_OF_INSERTED_PLAYERS; i++) {
      ss.add(ps.get(i).getUsername());
    }
    Assert.assertTrue(ss.contains("Karl") && ss.contains("Fawzi") && ss.contains("Samuel"));
  }

  @Test
  public void checkThatFawziIsTheSupremeArbiterOfJusticeAndTheOthersAreHisLoyalSubjects() {
    final Map<String, Boolean> bs = new HashMap<>();
    final List<Player> ps = playerDAO.findAll();
    for(int i = 0; i < NR_OF_INSERTED_PLAYERS; i++) {
      bs.put(ps.get(i).getUsername(), ps.get(i).isJudge());
    }
    bs.forEach((s, b) -> {
      Assert.assertEquals(b, s.equals("Fawzi") ? true : false);
    });
  }
}
