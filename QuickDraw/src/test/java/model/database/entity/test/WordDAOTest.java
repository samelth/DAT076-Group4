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
  
  private Word w1;
  private Word w2;
  private Word w3;
  private Word w4;
  
  @Before
  public void init() {
    w1 = new Word("a",1);
    w2 = new Word("b",2);
    w3 = new Word("c",3);
    w4 = new Word("d",3);
    wordDAO.create(w1);
    wordDAO.create(w2);
    wordDAO.create(w3);
    wordDAO.create(w4);
  }
  
  @After
  public void clean() {
    wordDAO.removeAll();
  }
  
  @Test
  public void testFind() {
    Assert.assertEquals(w1, wordDAO.find(w1));
    Assert.assertEquals(w2, wordDAO.find(w2));
    Assert.assertEquals(w3, wordDAO.find(w3));
    Assert.assertEquals(w4, wordDAO.find(w4));
  }
  
  @Test
  public void testFindWordsByLevel(){
    List<Word> l1 = wordDAO.findWordsByLevel(1);
    List<Word> l2 = wordDAO.findWordsByLevel(2);
    List<Word> l3 = wordDAO.findWordsByLevel(3);
    Assert.assertTrue(l1.contains(w1));
    Assert.assertTrue(l2.contains(w2));
    Assert.assertTrue(l3.contains(w3));
    Assert.assertTrue(l3.contains(w4));
    Assert.assertEquals(1, l1.size());
    Assert.assertEquals(1, l2.size());
    Assert.assertEquals(2, l3.size());
  }
  
}
