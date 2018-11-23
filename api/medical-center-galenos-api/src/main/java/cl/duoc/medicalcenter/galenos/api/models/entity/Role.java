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
@Table(name="ROLE")
@Proxy(lazy = false)
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ROLE_ID")
	private long roleId;
	
	@Column(name="ROLE_VALUE")
	private String roleValue;
	
	@Column(name="ROLE_DESCRIPTION")
	private String roleDescription;
	
	@Column(name="ROLE_STATUS")
	private char roleStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId", fetch = FetchType.LAZY)
    private List<ProfileRole> profileRoleList;
	
	public Role() {
		
	}

	public Role(long roleId) {
		this.roleId = roleId;
	}

	public Role(long roleId, String roleValue, String roleDescription, char roleStatus) {
		this.roleId = roleId;
		this.roleValue = roleValue;
		this.roleDescription = roleDescription;
		this.roleStatus = roleStatus;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public char getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(char roleStatus) {
		this.roleStatus = roleStatus;
	}

	public List<ProfileRole> getProfileRoleList() {
		return profileRoleList;
	}

	public void setProfileRoleList(List<ProfileRole> profileRoleList) {
		this.profileRoleList = profileRoleList;
	}
	
}
