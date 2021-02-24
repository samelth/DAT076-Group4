/*
 * Copyright (C) 2021 Karl Svensson
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
import org.junit.Assert;
import model.database.dao.DrawingWordDAO;
import model.database.dao.GameSessionDAO;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Karl Svensson
 */
@RunWith(Arquillian.class)
public class DrawingWordDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(DrawingWord.class, DrawingWordDAO.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  
  @EJB
  private DrawingWordDAO drawingWordDAO;
  
  
  @Before
  public void init(){
    DrawingWord w1 = new DrawingWord();
    DrawingWord w2 = new DrawingWord();
    DrawingWord w3 = new DrawingWord();
    DrawingWord w4 = new DrawingWord();
    w1.setWord("horse");
    w1.setLevel(1);
    w2.setWord("submarine");
    w2.setLevel(2);
    w3.setWord("car");
    w3.setLevel(3);
    w4.setWord("statue_of_liberty");
    w4.setLevel(4);
    
    drawingWordDAO.create(w1);
    drawingWordDAO.create(w2);
    drawingWordDAO.create(w3);
    drawingWordDAO.create(w4);
  }
  
  @Test
  public void checkFindByLevel(){
    int level = 3;
    String word = "car";
    List<DrawingWord> found = drawingWordDAO.getWordsByLevel(level);
    Assert.assertEquals(found.get(0).getWord(), word);
  }
  
  @After
  public void tearDown() {
    drawingWordDAO.findAll().forEach(p -> {
      drawingWordDAO.remove(p);
    });
    
  }
}
