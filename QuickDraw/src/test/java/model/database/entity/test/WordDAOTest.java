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
package model.database.entity.test;

import java.util.List;
import javax.ejb.EJB;
import org.junit.Assert;
import model.database.dao.WordDAO;
import model.database.entity.Word;
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
public class WordDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(Word.class, WordDAO.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  
  @EJB
  private WordDAO wordDAO;
  
  
  @Before
  public void init(){
    Word w1 = new Word();
    Word w2 = new Word();
    Word w3 = new Word();
    Word w4 = new Word();
    w1.setWord("horse");
    w1.setLvl(1);
    w2.setWord("submarine");
    w2.setLvl(2);
    w3.setWord("car");
    w3.setLvl(3);
    w4.setWord("statue_of_liberty");
    w4.setLvl(4);
    
    wordDAO.create(w1);
    wordDAO.create(w2);
    wordDAO.create(w3);
    wordDAO.create(w4);
  }
  
  @Test
  public void checkFindByLevel(){
    int level = 3;
    String word = "car";
    List<Word> found = wordDAO.findWordsByLevel(level);
    Assert.assertEquals(found.get(0).getWord(), word);
  }
  
  @Test
  public void testFind() {
    Word w = new Word("apa",1);
    wordDAO.create(w);
    Assert.assertEquals(w, wordDAO.find(w));
  }
  
  @After
  public void tearDown() {
    wordDAO.findAll().forEach(p -> {
      wordDAO.remove(p);
    });
    
  }
}
