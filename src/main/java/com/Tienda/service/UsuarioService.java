package com.Tienda.service;

import com.Tienda.domain.Usuario;
import org.springframework.security.core.userdetails.*;

public interface UsuarioService {
    
    public UserDetails loadUSerByUsername(String username) throws UsernameNotFoundException;
    
    public Usuario getUsuarioPorUsername(String username);
    
}
