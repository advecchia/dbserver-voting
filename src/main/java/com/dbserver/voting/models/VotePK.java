package com.dbserver.voting.models;

import java.io.Serializable;
import java.sql.Date;

public class VotePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8152337938457098805L;
	protected String userId;
    protected String restaurantId;
    protected Date votingDate;

    public VotePK() {}

    public VotePK(String userId, String restaurantId, Date votingDate) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.votingDate = votingDate;
    }

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
		VotePK other = (VotePK) obj;
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
		return "VotePK [userId=" + userId + ", restaurantId=" + restaurantId + ", votingDate=" + votingDate + " ]";
	}
}