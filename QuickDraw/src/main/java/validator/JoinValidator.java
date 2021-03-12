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
package validator;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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
 *
 * @author lewiv
 */
@RequestScoped
@FacesValidator(value = "joinValidator")
public class JoinValidator implements Validator {

  private static final String ERROR_MESSAGE_LOBBY_NOT_EXIST = "Lobby doesn't exist";
  private static final String ERROR_MESSAGE_USERNAME_ALREADY_IN_LOBBY = "Username taken, choose another";
  private static final String ERROR_MESSAGE_INCORRECT_FORMAT = "Please don't leave the fields empty";
  @EJB private LobbyDAO lobbyDAO;

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
  public void validate(FacesContext fc, UIComponent uic, Object usernameInput) throws ValidatorException {

    final UIInput lobbyIdComponent = (UIInput) fc.getViewRoot().findComponent("joinform:lobbyHexLidInput");

    //Check lobby exists
    if (lobbyIdComponent == null) {
      return;
    }
    final String lobbyHexLid = (String) lobbyIdComponent.getValue();
    if(lobbyHexLid == null){
      return; 
    }
    Lobby lobby = null;
    try {
      lobby = lobbyDAO.findLobbyByHexLid(lobbyHexLid);
    } catch (EJBException e) {
      throw new ValidatorException(new FacesMessage(ERROR_MESSAGE_LOBBY_NOT_EXIST));
    }
    final boolean lobbyNotExist = (null == lobby);
    if (lobbyNotExist) {
      throw new ValidatorException(new FacesMessage(ERROR_MESSAGE_LOBBY_NOT_EXIST));
    }
    
    //Check if username exists in lobby
    if (usernameInput == null) {
      return;
    }
    final String username = usernameInput.toString();
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
    return l.getPlebs()
            .stream()
            .anyMatch(player -> player.getUsername().equals(username));
  }
}
