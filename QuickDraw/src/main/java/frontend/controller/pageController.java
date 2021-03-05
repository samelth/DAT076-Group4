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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Control of pageNavigation
 * @author lewiv
 */
@Named(value= "pagecontroller")
@RequestScoped
public class pageController {
	 public void jumpToDrawPage(){
    try {
      FacesContext.getCurrentInstance().getExternalContext().redirect("Drawpage.xhtml");
    } catch (IOException ex) {
      Logger.getLogger(pageController.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
}
