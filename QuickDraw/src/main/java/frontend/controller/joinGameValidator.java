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
package frontend.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import model.database.dao.LobbyDAO;
import model.database.entity.Lobby;

/**
 * Validates user before joining a game.
 * @author lewiv
 */
@RequestScoped
@FacesValidator(value = "joinGameValidator")
public class joinGameValidator implements Validator {

  private static final String ERROR_MESSAGE_LOBBY_NOT_EXIST = "Lobby doesn't exists";
  private static final String ERROR_MESSAGE_USERNAME_ALREADY_IN_LOBBY = "Username already in lobby";

  @EJB
  private LobbyDAO lobbyDAO;

  /**
   * Validates user input for username and the lobby code. Checks if the lobby
   * exist and the username inputted is unique within the lobby.
   *
   * @param fc FacesContext.
   * @param uic UIComponent to grab other components
   * @param t Object to check for validation
   * @throws ValidatorException
   * @see javax.faces.validator.Validator
   */
  @Override
  public void validate(FacesContext fc, UIComponent uic, Object t) throws ValidatorException {
    final UIInput lidComponent = (UIInput) fc.getViewRoot().findComponent("joinform:lobbyHexLidInput");

    //Input to validate
    final String username = t.toString();
    final String lobbyHexLid = (String) lidComponent.getValue();

    final Lobby lobby = lobbyDAO.findLobbyByHexLid(lobbyHexLid);

    final boolean lobbyNotExist = (null == lobby);
    if (lobbyNotExist) {
      throw new ValidatorException(new FacesMessage(ERROR_MESSAGE_LOBBY_NOT_EXIST));
    }
    final boolean usernameExistinLobby = usernameExistinLobby(lobby, username);
    if (usernameExistinLobby) {
      throw new ValidatorException(new FacesMessage(ERROR_MESSAGE_USERNAME_ALREADY_IN_LOBBY));
    }
  }

  /**
   * @param l lobby
   * @param username username to check if it exist in the lobby.
   * @return True if the username already exists in a lobby. False otherwise.
   */
  private boolean usernameExistinLobby(final Lobby l, final String username) {
    return l.getPlayers()
            .stream()
            .anyMatch(player -> player.getUsername().equals(username));
  }
}
