/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.DatosDTO;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author danyg
 */
public class ConexionDAO {
    
   Connection conexion; 
   List<DatosDTO> listadatos = new ArrayList<>();
   DatosDTO datos;
    
    public  void abrir() {
        String usuario= "root";
        String contra= "admin";
        String rutaconexion ="jdbc:mysql://localhost:3306/g5s21p?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try{
          Class.forName("com.mysql.cj.jdbc.Driver");
          conexion = DriverManager.getConnection(rutaconexion, usuario,contra);
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }
    
    public boolean Insertar(DatosDTO datos){
        boolean estado = true;
        try{ 
            abrir();
            PreparedStatement consulta = conexion.prepareStatement("insert into datos(ap,am,nombres,edad,correo) values(?,?,?,?,?)");
            consulta.setString(1, datos.getAp());
            consulta.setString(2, datos.getAm());
            consulta.setString(3, datos.getNombres());
            consulta.setInt(4, datos.getEdad());
            consulta.setString(5, datos.getCorreo());
            consulta.execute();
        } catch(SQLException ex){
           ex.printStackTrace();
           estado = false;
        }
        return estado;
    }
    
    public boolean actualizar(DatosDTO datos)  {
        boolean estado = true;
        try{ 
            abrir();
            PreparedStatement consulta = conexion.prepareStatement("update datos set ap = ?, am = ?, nombres = ?, edad = ?, correo = ? where id = ?");
            consulta.setString(1, datos.getAp());
            consulta.setString(2, datos.getAm());
            consulta.setString(3, datos.getNombres());
            consulta.setInt(4, datos.getEdad());
            consulta.setString(5, datos.getCorreo());
            consulta.setInt(6, datos.getId());
            consulta.execute();
        } catch (SQLException ex) {
           ex.printStackTrace();
           estado = false;
        }
        return estado;
    }
    
    public boolean eliminar(int idelimina){
        boolean estado = true;
        try{ 
            abrir();
            PreparedStatement consulta = conexion.prepareStatement("delete from Datos where id = ?");
            consulta.setInt(1, idelimina);
            consulta.execute();
        } catch (SQLException ex) {
           ex.printStackTrace();
           estado = false;
        }
        return estado;
    }
    
    public boolean cargar(){
        boolean estado = true;
        try{ 
            abrir();
            PreparedStatement consulta = conexion.prepareStatement("select * from datos");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                datos = new DatosDTO();
                datos.setId(resultado.getInt("id"));
                datos.setAp(resultado.getString("ap"));
                datos.setAm(resultado.getString("am"));
                datos.setNombres(resultado.getString("nombres"));
                datos.setEdad(resultado.getInt("edad"));
                datos.setCorreo(resultado.getString("correo"));
                listadatos.add(datos);
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
           estado = false;
        }
        return estado;
    }
 
    public List<DatosDTO> getDatos(){
         return listadatos;
     }
    
    public void cerrar() {
       try {
           conexion.close();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }    
}
