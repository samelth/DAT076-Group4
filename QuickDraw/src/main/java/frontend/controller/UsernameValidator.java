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

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import net.bootsfaces.utils.FacesMessages;
import org.primefaces.validate.ClientValidator;

/**
 *
 * @author lewiv
 */
@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator, ClientValidator {
		
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object username) throws ValidatorException {
		if(fc == null || uic == null ){
			throw new NullPointerException();
		}
		String toValidate = username.toString(); 
		if(toValidate.isEmpty()){
			FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Empty string", "Please insert a username into the field");
			//fc.getCurrentInstance().addMessage(null, errMsg);
			throw new ValidatorException(errMsg);
		}
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "usernameValidator";
	}
	
}
