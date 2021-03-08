/*
 * Copyright (C) 2021 Karl Svensson
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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/**
 *
 * @author Karl Svensson
 */
@RequestScoped
@Named("guessRequest")
public class GuessRequest {

	 public void jumpToResult() {
    try {
      FacesContext.getCurrentInstance().getExternalContext().redirect("resultpage.xhtml");
    } catch (IOException ex) {
      Logger.getLogger(LobbyRequest.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
}