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
@Named(value = "validator")
@RequestScoped
@FacesValidator("fooValidator")
public class joinGameValidator implements Validator{
	@EJB				
	LobbyDAO lobbyDAO;

	// TODO 
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object t) throws ValidatorException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
