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
package frontend.controller;

import frontend.session.PlayerSessionBean;
import frontend.view.BackingBeanJudgePage;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.DrawingDAO;
import model.database.entity.Drawing;

/**
 *
 * @author Karl Svensson
 */
@Data
@Named("judgeController")
@ViewScoped
public class JudgepageController implements Serializable {
  @Inject PlayerSessionBean playerSessionBean;
  @Inject BackingBeanJudgePage backingBeanJudgePage;
  
  @EJB
  DrawingDAO drawingDAO;
  
  public void getDrawingUrl(){
    Drawing d = drawingDAO.findDByRoundandGameSession(playerSessionBean.getLobby().getGameSession());
    String url = String.valueOf(d.getUrl());
    backingBeanJudgePage.setImgURL(url);
    drawingDAO.remove(d);
  }
  
  
}
