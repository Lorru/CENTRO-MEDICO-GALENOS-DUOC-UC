package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="PROFILE")
@Proxy(lazy = false)
public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="PROFILE_ID")
	private long profileId;
	
	@Column(name="PROFILE_DESCRIPTION")
	private String profileDescription;
	
	@JsonIgnore
	@Column(name="PROFILE_STATUS")
	private char profileStatus;
	
	@JsonIgnore
    @OneToMany(mappedBy = "profileId", fetch = FetchType.LAZY)
    private List<User> userList;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileId", fetch = FetchType.LAZY)
    private List<ProfileRole> profileRoleList;
	
	public Profile() {
		
	}
	public Profile(long profileId) {
		this.profileId = profileId;
	}
	public Profile(long profileId, String profileDescription, char profileStatus) {
		this.profileId = profileId;
		this.profileDescription = profileDescription;
		this.profileStatus = profileStatus;
	}
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public char getProfileStatus() {
		return profileStatus;
	}
	public void setProfileStatus(char profileStatus) {
		this.profileStatus = profileStatus;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<ProfileRole> getProfileRoleList() {
		return profileRoleList;
	}
	public void setProfileRoleList(List<ProfileRole> profileRoleList) {
		this.profileRoleList = profileRoleList;
	}
	
}
