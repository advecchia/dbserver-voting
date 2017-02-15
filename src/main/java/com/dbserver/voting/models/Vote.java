package com.dbserver.voting.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
 
@Entity
@Table(name="votes")
public class Vote implements Serializable {
	
	private static final long serialVersionUID = 3678719375872828397L;
    
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("votingDate")
    private Date votingDate;
	
	@JsonProperty("user")
    private User user;
    
    @JsonProperty("restaurant")
    private Restaurant restaurant;

	public Vote() {
		super();
	}

	@JsonCreator(mode=JsonCreator.Mode.DELEGATING)
	public Vote(@JsonProperty("id") String id, @JsonProperty("votingDate") Date votingDate, 
			@JsonProperty("user") User user, @JsonProperty("restaurant") Restaurant restaurant) {
		super();
		this.id = id;
		this.votingDate = votingDate;
		this.user = user;
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
    @Column(name="votingDate", nullable=false)
	public Date getVotingDate() {
		return votingDate;
	}

	public void setVotingDate(Date votingDate) {
		this.votingDate = votingDate;
	}

	@ManyToOne
    @JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (votingDate == null) {
			if (other.votingDate != null)
				return false;
		} else if (!votingDate.equals(other.votingDate))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
		return "Vote [id=" + id + ", votingDate=" + votingDate + ", user=" + user + ", restaurant=" + restaurant + "]";
	}

}