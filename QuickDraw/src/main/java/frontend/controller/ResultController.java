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
package frontend.controller;

import frontend.request.LobbyRequest;
import frontend.session.PlebSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.GameDAO;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Game;
import model.database.entity.Pleb;
import model.database.entity.Word;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author emps
 */
@Data
@Named("resultController")
@ViewScoped
public class ResultController implements Serializable {
  @Inject @Push private PushContext resultChannel;
  @Inject       private PlebSession plebSession;
  
  @EJB PlebDAO plebDAO;
  @EJB GameDAO gameDAO;
  @EJB LobbyDAO lobbyDAO;
  @Inject LobbyRequest lobbyRequest;
  
  public String startNextRound() {
    Game g = plebSession.getGame();
    Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getLobby()); // is this needed or should we just fetch from existing session?
    List<Pleb> plebs = new ArrayList<>(recipients);
    Pleb guesser = plebSession.getGuesser();
    plebs.remove(guesser);
    Collections.shuffle(plebs);
    List<Word> words = plebSession.getWords();
    words.remove(0);
    g.setRound(g.getRound() + 1);
    g.setGuesser(plebs.get(0));
    g.setWords(words);
    plebSession.getLobby().setGame(g);
    gameDAO.update(g);
    lobbyDAO.update(plebSession.getLobby());
    resultChannel.send("nextRound",recipients);
    plebSession.getLobby().setGame(gameDAO
            .findGameByLobby(plebSession.getLobby()));
    return lobbyRequest.hostJumpToGame();
  }
}
