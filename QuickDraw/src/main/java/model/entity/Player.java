/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 *
 * @author Karl Svensson
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {
	@Id private int user_id;
	private String username;
        private int score;
}