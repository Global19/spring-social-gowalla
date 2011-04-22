/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.gowalla.connect;

import org.springframework.social.connect.ServiceApiAdapter;
import org.springframework.social.connect.ServiceProviderConnectionValues;
import org.springframework.social.connect.ServiceProviderUserProfile;
import org.springframework.social.connect.ServiceProviderUserProfileBuilder;
import org.springframework.social.gowalla.api.GowallaApi;
import org.springframework.social.gowalla.api.GowallaProfile;
import org.springframework.web.client.HttpClientErrorException;

public class GowallaServiceApiAdapter implements ServiceApiAdapter<GowallaApi> {

	public boolean test(GowallaApi serviceApi) {
		try {
			serviceApi.getUserProfile();
			return true;
		} catch (HttpClientErrorException e) {
			// TODO : Beef up Gowalla's error handling and trigger off of a more specific exception
			return false;
		}
	}

	public void setConnectionValues(GowallaApi serviceApi, ServiceProviderConnectionValues values) {
		GowallaProfile profile = serviceApi.getUserProfile();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
		values.setProfileUrl(serviceApi.getProfileUrl());
		values.setImageUrl(profile.getProfileImageUrl());
	}

	public ServiceProviderUserProfile fetchUserProfile(GowallaApi serviceApi) {
		GowallaProfile profile = serviceApi.getUserProfile();
		return new ServiceProviderUserProfileBuilder().setName(profile.getFirstName() + " " + profile.getLastName()).setUsername(profile.getId()).build();
	}
	
	public void updateStatus(GowallaApi serviceApi, String message) {
		// not supported
	}

}
