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

import frontend.session.PlayerSessionBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
import model.database.entity.Player;

/**
 *
 * @author Samuel Local
 */
@Data
@Named("joingameController")
@ViewScoped
public class JoingameController implements Serializable {
  @Inject PlayerSessionBean playerSessionBean;
  
  @EJB
  private LobbyDAO lobbyDAO;
  @EJB
  private PlayerDAO playerDAO;
  
  private String inputHexLid;
  private String inputUsername;
  
  public void joinLobby(){
    playerSessionBean.setPlayer(new Player());
		playerSessionBean.getPlayer().setUsername(inputUsername);
		playerSessionBean.getPlayer().setLobby(lobbyDAO.findLobbyByHexLid(inputHexLid));
		playerDAO.create(playerSessionBean.getPlayer());
  }
}