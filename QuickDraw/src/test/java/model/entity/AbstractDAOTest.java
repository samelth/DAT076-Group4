/*
 * Copyright (C) 2021 emps
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model.entity;

import java.util.List;
import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
import model.database.entity.Drawing;
import model.database.entity.DrawingWord;
import model.database.entity.GameSession;
import model.database.entity.Lobby;
import model.database.entity.Player;
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
 * @author emps
 */
@RunWith(Arquillian.class)
public class AbstractDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(PlayerDAO.class, Player.class, Lobby.class, LobbyDAO.class, GameSession.class, DrawingWord.class, Drawing.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  @EJB
  private PlayerDAO playerDAO;
  
  private Player p1;
  private Player p2;
  
  @Before
  public void init() {
    p1 = new Player();
    p2 = new Player();
  }
  
  @After
  public void clean() {
    playerDAO.removeAll();
  }
  
  @Test
  public void testCount() {
    long count = playerDAO.count();
    playerDAO.create(p1);
    playerDAO.create(p2);
    Assert.assertEquals(count + 2, playerDAO.count());
  }
  
  @Test
  public void testCreate() {
    final List<Player> ps1 = playerDAO.findAll();
    playerDAO.create(p1);
    playerDAO.create(p2);
    final List<Player> ps2 = playerDAO.findAll();
    Assert.assertFalse(ps1.contains(p1));
    Assert.assertFalse(ps1.contains(p2));
    Assert.assertTrue(ps2.contains(p1));
    Assert.assertTrue(ps2.contains(p2));
  }

  @Test
  public void testFindAll() {
    final List<Player> ps1 = playerDAO.findAll();
    playerDAO.create(p1);
    playerDAO.create(p2);
    final List<Player> ps2 = playerDAO.findAll();
    Assert.assertFalse(ps1.contains(p1));
    Assert.assertFalse(ps1.contains(p2));
    Assert.assertTrue(ps2.contains(p1));
    Assert.assertTrue(ps2.contains(p2));
  }

  @Test
  public void testRemove() {
    playerDAO.create(p1);
    playerDAO.create(p2);
    final List<Player> ps1 = playerDAO.findAll();
    playerDAO.remove(p1);
    playerDAO.remove(p2);
    final List<Player> ps2 = playerDAO.findAll();
    Assert.assertTrue(ps1.contains(p1));
    Assert.assertTrue(ps1.contains(p2));
    Assert.assertFalse(ps2.contains(p1));
    Assert.assertFalse(ps2.contains(p2));
  }
  
  @Test
  public void testRemoveAll() {
    playerDAO.create(p1);
    playerDAO.create(p2);
    playerDAO.removeAll();
    Assert.assertEquals(0, playerDAO.count());
  }
  
  @Test
  public void testUpdate() {
    p1.setScore(1);
    p2.setScore(2);
    playerDAO.create(p1);
    playerDAO.create(p2);
    Assert.assertEquals(1, playerDAO.find(p1).getScore());
    Assert.assertEquals(2, playerDAO.find(p2).getScore());
    p1.setScore(3);
    p2.setScore(4);
    playerDAO.update(p1);
    playerDAO.update(p2);
    Assert.assertEquals(3, playerDAO.find(p1).getScore());
    Assert.assertEquals(4, playerDAO.find(p2).getScore());
  }
  
  /*
  @Test
  public void testRefresh() {
    //wtf is this even supposed to do?
  }
  */
}
