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
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.DrawingWordDAO;
import model.database.dao.GameSessionDAO;
import model.database.dao.LobbyDAO;
import model.database.dao.PlayerDAO;
import model.database.entity.GameSession;
import model.database.entity.Player;

/**
 *
 * @author Samuel Local
 */
@Data
@Named("creategameController")
@ViewScoped
public class CreategameContoller implements Serializable{
  @Inject PlayerSessionBean playerSessionBean;
   
  @EJB
  private PlayerDAO playerDAO;
  @EJB
  private GameSessionDAO gameSessionDAO;
  @EJB
  private DrawingWordDAO drawingWordDAO;
  @EJB
  private LobbyDAO lobbyDAO;
  
   
  public List<Player> playersInLobby(){
    return playerDAO.findUsersInSameLobby(playerSessionBean.getPlayer().getLobby());
  }
   
  public String getHexLid(){
    return Integer.toHexString(playerSessionBean.getPlayer().getLobby().getLid()).toUpperCase();
  }
   
  public void startNewGame() {
    
    GameSession gs = new GameSession();
    gs.setLevel(1); //TODO: fetch level from input
    gs.setRound(1);
    gs.setJudgeId(playerSessionBean.getUser_id()); //TODO: random judge
    gs.setLobby(playerSessionBean.getLobby());
    gs.setDrawingWords(drawingWordDAO.getWordsByLevel(1)); //TODO: fetch level from input, shuffle words
    playerSessionBean.getLobby().setGameSession(gs);
    this.gameSessionDAO.create(gs);
    lobbyDAO.update(playerSessionBean.getLobby());
  }
   
}
