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
package model.database.entity.test;

import java.util.List;
import javax.ejb.EJB;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Chat;
import model.database.entity.Picture;
import model.database.entity.Word;
import model.database.entity.Game;
import model.database.entity.Game;
import model.database.entity.Lobby;
import model.database.entity.Lobby;
import model.database.entity.Message;
import model.database.entity.Picture;
import model.database.entity.Pleb;
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
 * @author emps
 */
@RunWith(Arquillian.class)
public class AbstractDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(PlebDAO.class, LobbyDAO.class, Pleb.class, Lobby.class, Game.class, Word.class, Picture.class, Chat.class, Message.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  @EJB
  private PlebDAO plebDAO;
  
  private Pleb p1;
  private Pleb p2;
  
  @Before
  public void init() {
    p1 = new Pleb();
    p2 = new Pleb();
  }
  
  @After
  public void clean() {
    plebDAO.removeAll();
  }
  
  @Test
  public void testCount() {
    long count = plebDAO.count();
    plebDAO.create(p1);
    plebDAO.create(p2);
    Assert.assertEquals(count + 2, plebDAO.count());
  }
  
  @Test
  public void testCreate() {
    final List<Pleb> ps1 = plebDAO.findAll();
    plebDAO.create(p1);
    plebDAO.create(p2);
    final List<Pleb> ps2 = plebDAO.findAll();
    Assert.assertFalse(ps1.contains(p1));
    Assert.assertFalse(ps1.contains(p2));
    Assert.assertTrue(ps2.contains(p1));
    Assert.assertTrue(ps2.contains(p2));
  }

  @Test
  public void testFindAll() {
    final List<Pleb> ps1 = plebDAO.findAll();
    plebDAO.create(p1);
    plebDAO.create(p2);
    final List<Pleb> ps2 = plebDAO.findAll();
    Assert.assertFalse(ps1.contains(p1));
    Assert.assertFalse(ps1.contains(p2));
    Assert.assertTrue(ps2.contains(p1));
    Assert.assertTrue(ps2.contains(p2));
  }

  @Test
  public void testRemove() {
    plebDAO.create(p1);
    plebDAO.create(p2);
    final List<Pleb> ps1 = plebDAO.findAll();
    plebDAO.remove(p1);
    plebDAO.remove(p2);
    final List<Pleb> ps2 = plebDAO.findAll();
    Assert.assertTrue(ps1.contains(p1));
    Assert.assertTrue(ps1.contains(p2));
    Assert.assertFalse(ps2.contains(p1));
    Assert.assertFalse(ps2.contains(p2));
  }
  
  @Test
  public void testRemoveAll() {
    plebDAO.create(p1);
    plebDAO.create(p2);
    plebDAO.removeAll();
    Assert.assertEquals(0, plebDAO.count());
  }
  
  @Test
  public void testUpdate() {
    p1.setScore(1);
    p2.setScore(2);
    plebDAO.create(p1);
    plebDAO.create(p2);
    Assert.assertEquals(1, plebDAO.find(p1).getScore());
    Assert.assertEquals(2, plebDAO.find(p2).getScore());
    p1.setScore(3);
    p2.setScore(4);
    plebDAO.update(p1);
    plebDAO.update(p2);
    Assert.assertEquals(3, plebDAO.find(p1).getScore());
    Assert.assertEquals(4, plebDAO.find(p2).getScore());
  }
  
  /*
  @Test
  public void testRefresh() {
    //wtf is this even supposed to do?
  }
  */
}
