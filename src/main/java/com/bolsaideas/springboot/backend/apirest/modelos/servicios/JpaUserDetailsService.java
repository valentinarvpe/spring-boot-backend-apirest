package com.bolsaideas.springboot.backend.apirest.modelos.servicios;

import com.bolsaideas.springboot.backend.apirest.modelos.dao.IUsuarioDao;
import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao repository;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userOptional = repository.findByNombreUsuario(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Nombre de usuario %s no existe en el sistema!", username));
        }

        Usuario usuario = userOptional.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(usuario.getNombreUsuario(),
                usuario.getContrasena(),
                usuario.isActivo(),
                true,
                true,
                true,
                authorities);
    }
}
