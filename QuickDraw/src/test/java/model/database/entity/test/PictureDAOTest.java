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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import model.database.dao.GameDAO;
import model.database.dao.PictureDAO;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author emps
 */
@RunWith(Arquillian.class)
public class PictureDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(PictureDAO.class, PlebDAO.class, GameDAO.class, Pleb.class, Lobby.class, Game.class, Word.class, Picture.class, Chat.class, Message.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  
  @EJB private PictureDAO pictureDAO;
  @EJB private PlebDAO plebDAO;
  @EJB private GameDAO gameDAO;
  
  private Picture p1;
  private Picture p2;
  private Picture p3;
  private Pleb pleb1;
  private Pleb pleb2;
  private Pleb pleb3;
  private Game g1;
  private Game g2;
  
  @Before
  public void init() {
    p1 = new Picture();
    p2 = new Picture();
    p3 = new Picture();
    pleb1 = new Pleb();
    pleb2 = new Pleb();
    pleb3 = new Pleb();
    plebDAO.create(pleb1);
    plebDAO.create(pleb2);
    plebDAO.create(pleb3);
    p1.setPleb(pleb1);
    p2.setPleb(pleb2);
    p3.setPleb(pleb3);
    g1 = new Game();
    g2 = new Game();
  }
  
  @Test
  public void testFind() {
    pictureDAO.create(p1);
    pictureDAO.create(p2);
    pictureDAO.create(p3);
    Assert.assertEquals(p1, pictureDAO.find(p1));
    Assert.assertEquals(p2, pictureDAO.find(p2));
    Assert.assertEquals(p3, pictureDAO.find(p3));
  }
  
  @Test
  public void testFindPicturesByGame() {
    g1.setPictures(new ArrayList<>());
    g2.setPictures(new ArrayList<>());
    g1.addPicture(p1);
    g2.addPicture(p2);
    g2.addPicture(p3);
    gameDAO.create(g1);
    gameDAO.create(g2);
    List<Picture> ps1 = pictureDAO.findPicturesByGame(g1);
    List<Picture> ps2 = pictureDAO.findPicturesByGame(g2);
    Assert.assertTrue(ps1.contains(p1));
    Assert.assertEquals(1, ps1.size());
    Assert.assertTrue(ps2.contains(p2));
    Assert.assertTrue(ps2.contains(p3));
    Assert.assertEquals(2, ps2.size());
    Assert.assertTrue(true);
  }
}
