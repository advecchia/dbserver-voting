package com.dbserver.voting.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
 
@Entity
@Table(name="winners")
public class Winner implements Serializable {
	
	private static final long serialVersionUID = 1563724508205921977L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("lunchDate")
    private Date lunchDate;
    
    @JsonProperty("restaurant")
	private Restaurant restaurant;

	public Winner() {
		super();
	}

	@JsonCreator(mode=JsonCreator.Mode.DELEGATING)
	public Winner(@JsonProperty("id") String id, @JsonProperty("lunchDate") Date lunchDate, 
			@JsonProperty("restaurant") Restaurant restaurant) {
		super();
		this.id = id;
		this.lunchDate = lunchDate;
		this.restaurant = restaurant;
	}

	@Id
	@NotNull
    @Column(name="id", nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotNull
    @Column(name="lunchDate", nullable=false)
	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	@ManyToOne
    @JoinColumn(name = "restaurantId")
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lunchDate == null) ? 0 : lunchDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Winner other = (Winner) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lunchDate == null) {
			if (other.lunchDate != null)
				return false;
		} else if (!lunchDate.equals(other.lunchDate))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Winner [id=" + id + ", lunchDate=" + lunchDate + ", restaurant=" + restaurant + "]";
	} 
    
    
    
}
