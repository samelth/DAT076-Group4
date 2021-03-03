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
import java.util.Optional;
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

import static java.util.Optional.empty;

/**
 * user Bean 
 * @author lewiv
 */

@Data
@Named(value = "playerSession")
@SessionScoped
public class PlayerSessionBean implements Serializable {
	Player player;
  
  public boolean isHost(){
    // null check guarding from run time null point exception. 
    // Reason why its done this way :
    // Avoid multiple ifs to check every getter that it returns null. 
    Optional<Player> host = Optional.of(player)
            .map(Player::getLobby)
            .map(Lobby::getHost);

    if(host.isEmpty()){
      return false;
    }
    final boolean isPlayerTheHost = host.get().equals(player);
    if(isPlayerTheHost) {
      return true; 
    }
    return false;
  }
  
  public void setUsername(String username) {
    player.setUsername(username);
  }
  
  public String getUsername(String username) {
    return player.getUsername();
  }
  
  public void setScore(int score) {
    player.setScore(score); 
  }
  
  public int getScore(int score) {
    return player.getScore(); 
  }
  
  public void setLobby(Lobby l) {
    player.setLobby(l); 
  }
  
  public Lobby getLobby() {
    return player.getLobby(); 
  }
  
  public Player getHost() {
    if(player == null || player.getLobby() == null){
      return null; 
    }
    return player.getLobby().getHost(); 
  }
}
