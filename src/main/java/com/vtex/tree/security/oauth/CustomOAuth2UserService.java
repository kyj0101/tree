package com.vtex.tree.security.oauth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.security.mapper.SecurityMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
	private final SecurityMapper mapper; 
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        MemberVO member  = saveOrUpdate(attributes);
        
        if(member == null) {
			throw new OAuth2AuthenticationException("user not found");
        }
        
        httpSession.setAttribute("loginMember", member);
        
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if("ADMIN".equals(member.getRoleCode())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		Map<String, Object> attributeMap = new HashMap<>();
		attributeMap = attributes.getAttributes();
		attributeMap.put("esntlId", member.getEsntlId());
		
        return new DefaultOAuth2User(
        		authorities,
                attributeMap,
                attributes.getNameAttributeKey());
    }

    private MemberVO saveOrUpdate(OAuthAttributes attributes) {
    
    	MemberVO user = mapper.login(attributes.getEmail());
        return user;
    }
}