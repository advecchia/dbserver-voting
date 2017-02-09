package com.dbserver.voting.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;
 
@Entity
@IdClass(VotePK.class)
@Table(name="votes")
public class Vote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3678719375872828397L;

	@Id
	@NotNull
    @Column(name="userId", nullable=false)
    private String userId;

	@Id
	@NotNull
	@Column(name="restaurantId", nullable=false)
    private String restaurantId;
 
	@Id
	@NotNull
    @Column(name="votingDate", nullable=false)
    private Date votingDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getVotingDate() {
		return votingDate;
	}

	public void setVotingDate(Date votingDate) {
		this.votingDate = votingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((restaurantId == null) ? 0 : restaurantId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((votingDate == null) ? 0 : votingDate.hashCode());
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
		Vote other = (Vote) obj;
		if (restaurantId == null) {
			if (other.restaurantId != null)
				return false;
		} else if (!restaurantId.equals(other.restaurantId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (votingDate == null) {
			if (other.votingDate != null)
				return false;
		} else if (!votingDate.equals(other.votingDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vote [userId=" + userId + ", restaurantId=" + restaurantId + ", votingDate=" + votingDate + "]";
	}

}