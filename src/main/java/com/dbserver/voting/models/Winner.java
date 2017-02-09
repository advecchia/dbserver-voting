package com.dbserver.voting.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;
 
@Entity
@Table(name="winners")
public class Winner implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563724508205921977L;

	@Id
	@NotNull
    @Column(name="restaurantId", nullable=false)
    private String restaurantId;

	@NotNull
    @Column(name="lunchDate", nullable=false)
    private Date lunchDate;

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lunchDate == null) ? 0 : lunchDate.hashCode());
		result = prime * result + ((restaurantId == null) ? 0 : restaurantId.hashCode());
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
		if (lunchDate == null) {
			if (other.lunchDate != null)
				return false;
		} else if (!lunchDate.equals(other.lunchDate))
			return false;
		if (restaurantId == null) {
			if (other.restaurantId != null)
				return false;
		} else if (!restaurantId.equals(other.restaurantId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Winner [restaurantId=" + restaurantId + ", lunchDate=" + lunchDate + "]";
	}
    
    
    
}
