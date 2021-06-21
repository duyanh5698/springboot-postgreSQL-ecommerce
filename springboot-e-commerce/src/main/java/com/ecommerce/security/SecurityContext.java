package com.ecommerce.security;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.model.User;
import com.ecommerce.service.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DucND
 * @date Apr 15, 2020
 */
@Slf4j
public class SecurityContext {

	public static User getCurrentUser() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				return ((UserDetailsImpl) authentication.getPrincipal()).getUserInfo();
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		return null;
	}

	public static String getLocal() {
		Locale localeResource = LocaleContextHolder.getLocaleContext().getLocale();
		return localeResource.toString();
	}

	public static long getCurrentUserId() {
		return SecurityContext.getCurrentUser().getId();
	}

	public static boolean isUserInRole(String role) {
		User userLogin = getCurrentUser();
		if (userLogin == null) {
			return false;
		}

//		for (Role roleOb: userLogin.getRoles()) {
//			if (roleOb.getName().equals(role)) {
//				return true;
//			}
//		}
		return true;
	}

	/**
	 * Check if user already logged via login page
	 */
	/*
	 * public static boolean isLoggedInAuthorizationServer() { Authentication
	 * authentication = SecurityContextHolder.getContext().getAuthentication(); if
	 * (authentication instanceof UserAuthenticationToken) { return true; } return
	 * false; }
	 */

}
