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

import frontend.request.LobbyRequest;
import frontend.session.PlebSession;
import frontend.view.LobbyView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;
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
  @Inject UserTransaction userTransaction;
  @Inject LobbyRequest lobbyRequest; 
  private String chatInput;


  public List<Pleb> plebsInLobby(){
    return plebDAO.findPlebsInSameLobby(plebSession.getPleb().getLobby());
  }

  public List<Message> messagesInLobbyChat(){
    return messageDAO.findMessagesByChat(plebSession.getLobby().getChat());
  }

  public String getHexLid(){
    return Integer.toHexString(plebSession.getPleb().getLobby().getLobby_id()).toUpperCase();
  }

  public void resetScore(){
    final List<Pleb> plebs = plebDAO.findPlebsInSameLobby(plebSession.getLobby());
    for (Pleb p : plebs) {
      p.setScore(0);
      plebDAO.update(p);
    }
  }

  public String startNewGame() throws Exception {
    resetScore();
    userTransaction.begin();
    final Game g = new Game();
    final Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getLobby());
    final List<Pleb> plebsInLobby = new ArrayList<>(recipients);
    Collections.shuffle(plebsInLobby);
    final Queue<Pleb> guessers = new LinkedList<>(plebsInLobby);
    plebSession.setGuessers(guessers);
    final List<Word> words = wordDAO.findWordsByLevel(lobbyView.getDifficulty());
    Collections.shuffle(words);
    g.setLvl(lobbyView.getDifficulty());
    g.setRound(1);
    g.setGuesser(plebSession.getGuessers().remove());
    g.setLobby(plebSession.getLobby());
    g.setWords(words);
    gameDAO.create(g);
    plebSession.getLobby().setGame(gameDAO
            .findGameByLobby(plebSession.getLobby()));
    lobbyDAO.update(plebSession.getLobby());
    lobbyDAO.getEntityManager().flush();
    userTransaction.commit();
    messageChannel.send("jumpToGame", recipients);
    return lobbyRequest.hostJumpToGame();
  }

  public void onPostNewMessage(){
    final Message msg = new Message();
    msg.setChat(plebSession.getLobby().getChat());
    msg.setUsername(plebSession.getPleb().getUsername());
    msg.setContent(lobbyView.getNewMessage());
    messageDAO.create(msg);
    chatDAO.update(chatDAO.findChatByLobby(plebSession.getLobby()));
    final Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getPleb().getLobby());
    lobbyView.setMessages(plebSession.getLobby().getChat().getMessages());
    messageChannel.send("newMsg", recipients);
    lobbyView.setNewMessage("");
  }

}
