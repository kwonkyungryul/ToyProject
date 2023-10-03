/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.toyproject.security;

import com.example.toyproject.config.security.CustomUserDetails;
import com.example.toyproject.modules.user.enums.UserConst;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomAccountSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
		var user = UserConst.academyUser;
		var customUserDetails = new CustomUserDetails(user);
		var securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(
				new UsernamePasswordAuthenticationToken(
						customUserDetails,
						customUserDetails.getPassword(),
						customUserDetails.getAuthorities()
				)
		);
		return securityContext;
	}

}
