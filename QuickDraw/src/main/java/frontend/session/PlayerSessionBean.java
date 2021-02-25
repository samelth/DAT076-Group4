/*
 * Copyright (C) 2021 lewiv
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
package frontend.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
import model.database.entity.Lobby;
import model.database.entity.Player;
import org.omnifaces.cdi.Param;

/**
 * user Bean 
 * @author lewiv
 */

@Data
@Named(value = "playerSession")
@SessionScoped
public class PlayerSessionBean implements Serializable {
	@EJB
	private PlayerDAO playerDAO;
	@EJB
	private LobbyDAO lobbyDAO;
	@Param
	private int lobbyId= 0; // assoicated with an lobby. 
	private String username;
	Player player;
	@PostConstruct
	public void init(){
	}
	public Lobby getLobby(){
		return lobbyDAO.find(lobbyId);
	}
	public void setLobby(int lobbyId){
		
	}
	public void joinLobby(){
		Player p = new Player();
		p.setUsername(username);
		p.setLobby(lobbyDAO.find(lobbyId));
    player=p;
		playerDAO.create(p);
	}
	
	public void hostNewLobby(){
		Lobby lob = new Lobby();
		lobbyDAO.create(lob);
    lobbyId=lob.getLid();
		joinLobby();
	}
  
  public List<Player> playersInLobby(){
    return playerDAO.findUsersInSameLobby(getLobby());
  }
}
