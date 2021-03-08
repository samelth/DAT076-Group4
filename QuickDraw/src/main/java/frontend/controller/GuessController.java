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

import frontend.request.DrawRequest;
import frontend.request.GuessRequest;
import frontend.session.PlebSession;
import frontend.view.GuessView;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.PictureDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Picture;
import model.database.entity.Pleb;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Karl Svensson
 */
@Data
@Named("guessController")
@ViewScoped
public class GuessController implements Serializable {
  @Inject private PlebSession plebSession;
  @Inject private GuessView guessView;
  @Inject private DrawRequest drawRequest;
  @Inject private GuessRequest guessRequest;
  @Inject @Push private PushContext scoreChannel;
  
  @EJB private PictureDAO pictureDAO;
  @EJB private PlebDAO plebDAO;
  
  private Queue<String> submissions;
  private boolean guessing;
  private Picture pic;
  private int count;
  
  @PostConstruct
  public void init() {
    submissions = new LinkedList<>();
    count = 1;
  }
  
  public void newPicture() {
    pic = pictureDAO.findDByRoundandGameSession(plebSession.getLobby().getGame());
    submissions.add(String.valueOf(pic.getUrl()));
    pictureDAO.remove(pic);
    count ++;
    showPicture();
  }
  
  public void showPicture(){
    if(submissions.isEmpty() && count == plebDAO.findPlebsInSameLobby(plebSession.getLobby()).size()){
      guessRequest.jumpToResult();
    }
    else {
      if(!guessing) {
        try{
          guessing = true;
          guessView.setImgURL(submissions.remove());
          FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
          PrimeFaces.current().executeScript("startProgressBar(\"#p1\");");
          PrimeFaces.current().executeScript("playTime(\"#countdown\");");
        } catch(NoSuchElementException e){
          guessView.setImgURL("");
          guessing = false;
        }
      }
    }
  }
  
  public void guess() {
    Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getLobby());
    String guessed = guessView.getGuessed();
    String correctWord = drawRequest.currentWord().getWord();
    if(guessed != null && guessed.equalsIgnoreCase(correctWord)) {
      Pleb p = pic.getPleb();
      p.setScore(100);
      plebDAO.update(p);
      scoreChannel.send("newScore",recipients);
      guessRequest.jumpToResult();
    }
    else{
      guessing = false;
      showPicture();
    }
  }
}
