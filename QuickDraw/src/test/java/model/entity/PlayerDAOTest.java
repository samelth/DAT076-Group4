package model.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ejb.EJB;
import model.dao.PlayerDAO;
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
      .addClasses(PlayerDAO.class, Player.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private PlayerDAO playerDAO;

  @Before
  public void init() {
    playerDAO.create(new Player(1, "Karl", 0));
    playerDAO.create(new Player(2, "Samuel", 0));
    playerDAO.create(new Player(3, "Fawzi", 0));
  }

  @Test
  public void checkThatFindUserMatchingNameMatchesCorrectly() {
    Assert.assertTrue(true); /* Some better condition */
  }
}
