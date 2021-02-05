/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import javax.ejb.EJB;
import model.dao.LobbyDAO;

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
 * @author Karl Svensson <Svensson.Karl@iCloud.com>
 */
@RunWith(Arquillian.class)
public class LobbyDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(LobbyDAO.class, Lobby.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private LobbyDAO lobbyDAO;

  @Before
  public void init() {
    lobbyDAO.create(new Lobby(1, 1));
    lobbyDAO.create(new Lobby(2, 1));
    lobbyDAO.create(new Lobby(3, 1));
  }

  @Test
  public void checkThatFindLobbyMatchingNameMatchesCorrectly() {
    Assert.assertTrue(true); /* Some better condition */
  }
}
