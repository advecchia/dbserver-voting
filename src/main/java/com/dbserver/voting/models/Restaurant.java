package com.dbserver.voting.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="restaurants")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = 8822353414193879336L;

	@JsonProperty("id")
	private String id;
    
	@JsonProperty("name")
	private String name;

	private Set<Winner> winners;
	private Set<Vote> votes;

	public Restaurant() {
		super();
	}

	@JsonCreator(mode=JsonCreator.Mode.DELEGATING)
	public Restaurant(@JsonProperty("id") String id, @JsonProperty("name") String name, 
			@JsonProperty("winners") Set<Winner> winners, @JsonProperty("votes") Set<Vote> votes) {
		super();
		this.id = id;
		this.name = name;
		this.winners = winners;
		this.votes = votes;
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
    @Column(name="name", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	public Set<Winner> getWinners() {
		return winners;
	}

	public void setWinners(Set<Winner> winners) {
		this.winners = winners;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	public Set<Vote> getVotes() {
		return votes;
	}

	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Restaurant other = (Restaurant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (winners == null) {
			if (other.winners != null)
				return false;
		} else if (!winners.equals(other.winners))
			return false;
		if (votes == null) {
			if (other.votes != null)
				return false;
		} else if (!votes.equals(other.votes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", winners=" + winners + ", votes=" + votes + "]";
	}
    
}
