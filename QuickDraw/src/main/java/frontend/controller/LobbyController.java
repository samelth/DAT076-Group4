/*
 * Copyright (C) 2021 Samuel Local
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
package frontend.controller;

import frontend.session.PlebSession;
import frontend.view.LobbyView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jdk.javadoc.internal.tool.Main;
import lombok.Data;
import model.database.dao.ChatDAO;
import model.database.entity.Message;
import model.database.dao.WordDAO;
import model.database.dao.GameDAO;
import model.database.dao.LobbyDAO;
import model.database.dao.MessageDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Game;
import model.database.entity.Pleb;
import model.database.entity.Word;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Samuel Local
 */
@Data
@Named("lobbyController")
@ViewScoped
public class LobbyController implements Serializable{
  @Inject @Push private PushContext messageChannel;
  @Inject       private PlebSession plebSession;
  @Inject       private LobbyView lobbyView;
   
  @EJB private PlebDAO plebDAO;
  @EJB private GameDAO gameDAO;
  @EJB private WordDAO wordDAO;
  @EJB private LobbyDAO lobbyDAO;
  @EJB private MessageDAO messageDAO;
  @EJB private ChatDAO chatDAO;
  
  private String chatInput;
  
  public List<Pleb> plebsInLobby(){
    return plebDAO.findPlebsInSameLobby(plebSession.getPleb().getLobby());
  }
  
  public List<Message> messagesInLobbyChat(){
    return messageDAO.messages(plebSession.getLobby().getChat());
  }
   
  public String getHexLid(){
    return Integer.toHexString(plebSession.getPleb().getLobby().getLobby_id()).toUpperCase();
  }
  
  public void startNewGame() {
    Game g = new Game();
    Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getLobby());
    List<Pleb> plebs = new ArrayList<>(recipients);
    Collections.shuffle(plebs);
    List<Word> words = wordDAO.findWordsByLevel(1);
    Collections.shuffle(words);
    g.setLvl(1); //TODO: fetch level from input
    g.setRound(1);
    g.setGuesser(plebs.get(0));
    g.setLobby(plebSession.getLobby());
    g.setWords(words);
    plebSession.getLobby().setGame(g);
    gameDAO.create(g);
    lobbyDAO.update(plebSession.getLobby());
    
    messageChannel.send("jumpToGame",recipients);
  }
  
  public void onPostNewMessage(){
    final Message msg = new Message();
    msg.setChat(plebSession.getLobby().getChat());
    msg.setUsername(plebSession.getPleb().getUsername());
    msg.setContent(lobbyView.getNewMessage());
    messageDAO.create(msg);
    chatDAO.update(chatDAO.findChatByLobby(plebSession.getLobby()));
    Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getPleb().getLobby());
    lobbyView.setMessages(plebSession.getLobby().getChat().getMessages());
    messageChannel.send("newMsg",recipients);
    lobbyView.setNewMessage("");
  }
   
}
