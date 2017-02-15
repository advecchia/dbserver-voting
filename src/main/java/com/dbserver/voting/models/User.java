package com.dbserver.voting.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Set;
 
@Entity
@Table(name="users")
public class User implements Serializable{

	private static final long serialVersionUID = 350167822640714333L;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("username")
	private String username;

	/**
	 * A base 64 encoded string
	 */
	@JsonProperty("password")
	private String password;

	private Set<Vote> votes;

	public User() {
		super();
	}

	@JsonCreator(mode=JsonCreator.Mode.DELEGATING)
	public User(@JsonProperty("id") String id, @JsonProperty("name") String name, 
			@JsonProperty("username") String username, @JsonProperty("password") String password, 
			@JsonProperty("votes") Set<Vote> votes) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
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

	@NotNull
    @Column(name="username", nullable=false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
    @Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
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
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", votes=" + votes + "]";
	}
    
}