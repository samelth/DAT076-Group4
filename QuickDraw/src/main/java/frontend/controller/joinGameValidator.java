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

import javax.annotation.Nullable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import model.database.dao.LobbyDAO;
import model.database.entity.Lobby;

import static java.sql.Types.NULL;

/**
 * Validates user before joining a game.
 * @author lewiv
 */
@RequestScoped
@Named(value = "validator")
@FacesValidator("fooValidator")
public class joinGameValidator implements Validator{
	
	private final static String LOBBY_NOT_FOUND_MSG = "Lobby doesn't exist"; 
	private final static String INVALID_FORMAT_LOBBYID_MSG = "Please insert a valid lobbyID";
	private final static String USER_ALREADY_EXIST_MSG = "The user already exists"; 
	
	private  final Converter conv = new Converter();
	@EJB				
	LobbyDAO lobbyDAO;

	
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object t) throws ValidatorException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public String checkUserExistsInLob(String lobbyId, String username){
		@Nullable
		Lobby lob = null;
		int lobId;
		boolean isValid = false; 
		if (lobbyId == null){
			return LOBBY_NOT_FOUND_MSG;
		}
		try{
			 lobId = conv.textToLobbyID(lobbyId);
		}catch(NumberFormatException e){
			return INVALID_FORMAT_LOBBYID_MSG;
		}
		if(doesLobbyExist(lobId)){
		 lob = lobbyDAO.find(lobId);
		}else{
		 return LOBBY_NOT_FOUND_MSG;
		}
		
		boolean isUsernameInLobby =
						lob.getPlayers()
						.stream()
						.anyMatch(
							player -> player.getUsername() == username
						);
		if(isUsernameInLobby){
			return USER_ALREADY_EXIST_MSG;
		}
		return "";
	}

	private boolean doesLobbyExist(Integer id){
		if(id == null){
			return false;
		}
		if(lobbyDAO.find(id) == null ){
				return false;
		}
		return true;
	}
	
}
