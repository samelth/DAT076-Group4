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
package frontend.request;

import frontend.session.PlebSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.database.dao.GameDAO;

/**
 * Control of pageNavigation
 * @author lewiv
 */
@Named("lobbyRequest")
@RequestScoped
public class LobbyRequest {
  @Inject private PlebSession plebSession;
  
  @EJB private GameDAO gameDAO;
  
	 public void jumpToDrawPage(){
    try {
      plebSession.getLobby().setGame(gameDAO
              .findGameByLobby(plebSession.getLobby()));
      if(plebSession.isGuesser()) {
        FacesContext.getCurrentInstance().getExternalContext().redirect("guesspage.xhtml");
      } else {
        FacesContext.getCurrentInstance().getExternalContext().redirect("drawpage.xhtml");
      }
    } catch (IOException ex) {
      Logger.getLogger(LobbyRequest.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
}
