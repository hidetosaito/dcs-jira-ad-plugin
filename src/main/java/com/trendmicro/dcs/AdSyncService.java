package com.trendmicro.dcs;

import java.util.Set;

import com.atlassian.configurable.ObjectConfiguration;
import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.crowd.exception.GroupNotFoundException;
import com.atlassian.crowd.exception.OperationFailedException;
import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.UserNotFoundException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.extras.common.log.Logger;
import com.atlassian.extras.common.log.Logger.Log;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.jira.service.AbstractService;
import com.atlassian.jira.user.util.UserManager;

public class AdSyncService extends AbstractService {

	private static final String baseGroup = "trend-users";
	private static final Log log = Logger.getInstance(AdSyncService.class);
	
	@Override
	public ObjectConfiguration getObjectConfiguration() throws ObjectConfigurationException {
		return getObjectConfiguration("MYNEWSERVICE", "jira-ad-plugin.xml", null);
	}

	@Override
	public void run() {
		System.out.println("hello, this is service");
		GroupManager gm;
		UserManager um;
		Group group;
		Set<User> users;
		
		gm = ComponentAccessor.getGroupManager();
		um = ComponentAccessor.getUserManager();
		
		if(!gm.groupExists(baseGroup)){
			log.warn("creating group : " + baseGroup);
			try {
				gm.createGroup(baseGroup);
			} catch (OperationNotPermittedException e) {
				log.error("doesn't have a permission", e);
			} catch (InvalidGroupException e) {
				log.error("invalid group", e);
			}
		}
		
		group = gm.getGroup(baseGroup);
		users = um.getAllUsers();
		
		for(User u : users){
			if(gm.isUserInGroup(u, group)){
				
			}
			else{
				try {
					log.warn("adding user " + u.getDisplayName() + " to " + baseGroup);
					gm.addUserToGroup(u, group);
				} catch (GroupNotFoundException e) {
					log.error("no group", e);
				} catch (UserNotFoundException e) {
					log.error("no user", e);
				} catch (OperationNotPermittedException e) {
					log.error("doesn't have a permission", e);
				} catch (OperationFailedException e) {
					log.error("operation failed", e);
				}
			}
		}
	}
}
