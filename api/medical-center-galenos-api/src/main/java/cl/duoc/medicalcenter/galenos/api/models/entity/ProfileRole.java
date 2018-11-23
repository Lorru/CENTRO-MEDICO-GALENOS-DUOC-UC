package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="PROFILE_ROLE")
@Proxy(lazy = false)
public class ProfileRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="PROFILE_ROLE_ID")
	private long profileRoleId;
	
    @JoinColumn(name = "PROFILE_ID", referencedColumnName = "PROFILE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profileId;
    
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Role roleId;

	public ProfileRole() {
		
	}

	public ProfileRole(long profileRoleId) {
		this.profileRoleId = profileRoleId;
	}

	public long getProfileRoleId() {
		return profileRoleId;
	}

	public void setProfileRoleId(long profileRoleId) {
		this.profileRoleId = profileRoleId;
	}

	public Profile getProfileId() {
		return profileId;
	}

	public void setProfileId(Profile profileId) {
		this.profileId = profileId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}
	
}
