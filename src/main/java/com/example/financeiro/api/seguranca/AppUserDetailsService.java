package com.example.financeiro.api.seguranca;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.financeiro.api.modelo.Usuario;
import com.example.financeiro.api.repositorio.UsuarioRepositorio;

@Service
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos!"));
		
		return new User(email, usuario.getSenha(), getPermisses(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermisses(Usuario usuario) {
		Set<SimpleGrantedAuthority> autorizacoes = new HashSet<>();
		usuario.getPermissoes().forEach(p -> autorizacoes.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		
		return autorizacoes;
	}
}
