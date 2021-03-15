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
package frontend.view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author lewiv
 */
@Data
@Named("joinView")
@ViewScoped
public class JoinView implements Serializable {

  @Size(max = 12,message = "Please choose a username that is not longer than 12 characters")
  @NotEmpty(message = "Please insert a username")
  @Pattern(regexp = "[a-zA-Z0-9]+", message = "Please insert only numbers and letters")
  private String inputUsername;

  @NotEmpty(message = "Please insert a lobbyId")
  private String inputLobbyHexLid;
}
