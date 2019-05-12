package com.example.financeiro.api.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.financeiro.api.modelo.Usuario;
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByEmail(String email);
}
