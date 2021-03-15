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
import model.database.dao.ChatDAO;
import model.database.dao.GameDAO;
import model.database.dao.LobbyDAO;
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
public class ChatDAOTest {
  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
      .addClasses(ChatDAO.class, LobbyDAO.class, Pleb.class, Lobby.class, Game.class, Word.class, Picture.class, Chat.class, Message.class)
      .addAsResource("META-INF/persistence.xml")
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  
  @EJB private ChatDAO chatDAO;
  @EJB private LobbyDAO lobbyDAO;
  
  private Chat c1;
  private Chat c2;
  private Lobby l1;
  private Lobby l2;
  
  @Before
  public void init() {
    c1 = new Chat();
    c2 = new Chat();
    l1 = new Lobby();
    l2 = new Lobby();
  }
  
  @Test
  public void testFind() {
    chatDAO.create(c1);
    chatDAO.create(c2);
    Assert.assertEquals(c1, chatDAO.find(c1));
    Assert.assertEquals(c2, chatDAO.find(c2));
  }
  
  @Test
  public void testFindChatByLobby() {
    lobbyDAO.create(l1);
    lobbyDAO.create(l2);
    chatDAO.create(c1);
    chatDAO.create(c2);
    c1.setLobby(l1);
    c2.setLobby(l2);
    l1.setChat(c1);
    l2.setChat(c2);
    lobbyDAO.update(l1);
    lobbyDAO.update(l2);
    chatDAO.update(c1);
    chatDAO.update(c2);
    Assert.assertEquals(c1, chatDAO.findChatByLobby(l1));
    Assert.assertEquals(c2, chatDAO.findChatByLobby(l2));
  }
}
