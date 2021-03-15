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
import frontend.view.JoinView;
import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Pleb;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Samuel Local
 */
@Data
@Named("joinController")
@ViewScoped
public class JoinController implements Serializable {
  @Inject @Push private PushContext messageChannel;   
  @Inject       private PlebSession plebSession;
  @Inject       private JoinView joinView;

  @EJB private LobbyDAO lobbyDAO;
  @EJB private PlebDAO userDAO;

  public void joinLobby(){
    plebSession.setPleb(new Pleb());
    plebSession.setUsername(joinView.getInputUsername());
    plebSession.setLobby(lobbyDAO.findLobbyByHexLid(joinView.getInputLobbyHexLid()));
    userDAO.create(plebSession.getPleb());
    lobbyDAO.update(plebSession.getLobby());
    Collection<Pleb> recipients = userDAO.findPlebsInSameLobby(plebSession.getLobby());
    messageChannel.send("playerJoined", recipients);
  }
}
