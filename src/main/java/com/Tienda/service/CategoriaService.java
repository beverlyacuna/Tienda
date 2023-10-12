
package com.Tienda.service;

import com.Tienda.domain.Categoria;
import java.util.List;


public interface CategoriaService {
    
    //Retorna una lista de categorias (activas o todas)
    public List<Categoria> getCategorias(boolean activos);
    
    //Retorna una categoria por ID
    public Categoria getCategoria (Categoria categoria);
    
     // Se inserta un nuevo categoria si el id del categoria esta vacío
    // Se actualiza un categoria si el id del categoria NO esta vacío
    public void save(Categoria categoria);
    
    // Se elimina el categoria que tiene el id pasado por parámetro
    public void delete(Categoria categoria);
}
