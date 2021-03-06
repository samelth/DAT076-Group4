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

import javax.ejb.EJB;
import model.database.dao.GameDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Picture;
import model.database.entity.Word;
import model.database.entity.Game;
import model.database.entity.Game;
import model.database.entity.Lobby;
import model.database.entity.Lobby;
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
public class GameDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(GameDAO.class, Lobby.class, Pleb.class, PlebDAO.class, Game.class, Word.class, Picture.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private GameDAO gameDAO;
  
  private Game g1;
  private Game g2;
  
  @Before
  public void init() {
    g1 = new Game();
    g2 = new Game();
  }
  
  @After
  public void clean() {
    gameDAO.removeAll();
  }
  
  @Test
  public void testFind() {
    gameDAO.create(g1);
    gameDAO.create(g2);
    Assert.assertEquals(g1, gameDAO.find(g1));
    Assert.assertEquals(g2, gameDAO.find(g2));
  }
}
