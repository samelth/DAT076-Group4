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

import javax.ejb.EJB;
import model.database.dao.GameSessionDAO;
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
public class GameSessionDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(GameSessionDAO.class, Lobby.class, Player.class, PlayerDAO.class, GameSession.class, DrawingWord.class, Drawing.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @EJB
  private GameSessionDAO gameSessionDAO;
  
  private GameSession gs1;
  private GameSession gs2;
  
  @Before
  public void init() {
    gs1 = new GameSession();
    gs2 = new GameSession();
  }
  
  @After
  public void clean() {
    gameSessionDAO.removeAll();
  }
  
  @Test
  public void testFind() {
    gameSessionDAO.create(gs1);
    gameSessionDAO.create(gs2);
    Assert.assertEquals(gs1, gameSessionDAO.find(gs1));
    Assert.assertEquals(gs2, gameSessionDAO.find(gs2));
  }
}
