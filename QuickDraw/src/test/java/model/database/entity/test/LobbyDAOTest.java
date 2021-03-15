/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.entity.test;

import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Chat;
import model.database.entity.Game;
import model.database.entity.Lobby;
import model.database.entity.Message;
import model.database.entity.Picture;
import model.database.entity.Pleb;
import model.database.entity.Word;
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
      .addClasses(LobbyDAO.class, PlebDAO.class, Pleb.class, Lobby.class, Game.class, Word.class, Picture.class, Chat.class, Message.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private LobbyDAO lobbyDAO;
  @EJB
  private PlebDAO plebDAO;

  private Lobby l;
  private Pleb p;

  @Before
  public void init() {
    l = new Lobby();
    p = new Pleb();
  }

  @After
  public void clean() {
    lobbyDAO.removeAll();
    plebDAO.removeAll(); 
  }

  @Test
  public void checkFind() {
    lobbyDAO.create(l);
    Assert.assertEquals(l, lobbyDAO.find(l));
  }

  @Test
  public void checkFindLobbyByHexLid() {
    lobbyDAO.create(l);
    int lid = lobbyDAO.find(l).getLobby_id();
    String hexLid = Integer.toHexString(lid);
    Assert.assertEquals(l, lobbyDAO.findLobbyByHexLid(hexLid));
  }

}
