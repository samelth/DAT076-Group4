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
import model.database.dao.ChatDAO;
import model.database.dao.MessageDAO;
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
public class MessageDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(MessageDAO.class, ChatDAO.class, Pleb.class, Lobby.class, Game.class, Word.class, Picture.class, Chat.class, Message.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  
  @EJB private MessageDAO messageDAO;
  @EJB private ChatDAO chatDAO;
  
  private Message m1;
  private Message m2;
  private Message m3;
  private Chat c1;
  private Chat c2;
  
  @Before
  public void init() {
    m1 = new Message();
    m2 = new Message();
    m3 = new Message();
    c1 = new Chat();
    c2 = new Chat();
  }
  
  @Test
  public void testFind() {
    messageDAO.create(m1);
    messageDAO.create(m2);
    messageDAO.create(m3);
    Assert.assertEquals(m1, messageDAO.find(m1));
    Assert.assertEquals(m2, messageDAO.find(m2));
    Assert.assertEquals(m3, messageDAO.find(m3));
  }
  
  @Test
  public void testFindMessagesByChat() {
    messageDAO.create(m1);
    messageDAO.create(m2);
    messageDAO.create(m3);
    chatDAO.create(c1);
    chatDAO.create(c2);
    c1.addMessage(m1);
    c2.addMessage(m2);
    c2.addMessage(m3);
    messageDAO.update(m1);
    messageDAO.update(m2);
    messageDAO.update(m3);
    chatDAO.update(c1);
    chatDAO.update(c2);
    List<Message> ms1 = messageDAO.findMessagesByChat(c1);
    List<Message> ms2 = messageDAO.findMessagesByChat(c2);
    Assert.assertTrue(ms1.contains(m1));
    Assert.assertEquals(1, ms1.size());
    Assert.assertTrue(ms2.contains(m2));
    Assert.assertTrue(ms2.contains(m3));
    Assert.assertEquals(2, ms2.size());
  }
  
}
