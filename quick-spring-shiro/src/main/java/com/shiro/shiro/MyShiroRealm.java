package com.shiro.shiro;

import com.shiro.entity.User;
import com.shiro.service.PermService;
import com.shiro.service.RoleService;
import com.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author wangxc
 * @date: 2019-12-01 20:53
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermService permService;


	//告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
	{
		//设置用于匹配密码的CredentialsMatcher
		HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
		hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		hashMatcher.setStoredCredentialsHexEncoded(false);
		hashMatcher.setHashIterations(1024);
		this.setCredentialsMatcher(hashMatcher);
	}

	//定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		User user = (User) getAvailablePrincipal(principals);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		System.out.println("获取角色信息："+user.getRoles());
		System.out.println("获取权限信息："+user.getPerms());
		info.setRoles(user.getRoles());
		info.setStringPermissions(user.getPerms());
		return info;
	}

	//定义如何获取用户信息的业务逻辑，给shiro做登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		if (StringUtils.isEmpty(username)) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		User userByName = userService.findUserByName(username);
		if (Objects.isNull(userByName)) {
			throw new UnknownAccountException("No account found for admin [" + username + "]");
		}

		//查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
		//SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
		Set<String> roles = roleService.getRolesByUserId(userByName.getUid());
		Set<String> perms = permService.getPermsByUserId(userByName.getUid());
		userByName.getRoles().addAll(roles);
		userByName.getPerms().addAll(perms);

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userByName, userByName.getPwd(), getName());
		if (userByName.getSalt() != null) {
			info.setCredentialsSalt(ByteSource.Util.bytes(userByName.getSalt()));
		}
		return info;
	}
}
