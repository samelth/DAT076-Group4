/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Karl Svensson <Svensson.Karl@iCloud.com>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lobby implements Serializable{
  @OneToMany(mappedBy = "lobby") private List<Player> players;
  @Id @GeneratedValue private int lid;
  private int round;
}
