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
import frontend.view.IndexView;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.ChatDAO;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Chat;
import model.database.entity.Lobby;
import model.database.entity.Pleb;


/**
 *
 * @author Samuel Local
 */
@Data
@Named("indexController")
@ViewScoped
public class IndexController implements Serializable {
  @Inject private PlebSession plebSession;
  @Inject private IndexView indexView;
  
  @EJB private LobbyDAO lobbyDAO;
  @EJB private ChatDAO chatDAO;
  @EJB private PlebDAO userDAO;
    
  public void hostNewLobby(){
    final Pleb pleb = new Pleb();
    final Lobby lob = new Lobby();
    final Chat chat = new Chat();
    chatDAO.create(chat);
    pleb.setUsername(indexView.getInputUsername());
    lob.addPleb(pleb);
    lob.setHost(pleb);
    lob.setChat(chat);
    plebSession.setPleb(pleb);
    lobbyDAO.create(lob);
    chat.setLobby(lob);
    chatDAO.update(chat);
  }
}
