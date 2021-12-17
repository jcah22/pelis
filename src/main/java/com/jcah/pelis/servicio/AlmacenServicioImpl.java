package com.jcah.pelis.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import excepciones.AlmacenExepcion;
import excepciones.FileNotFoundException;

@Service
public class AlmacenServicioImpl implements AlmacenServicio {

    @Value("$storage.location")
    private String storageLocation;

    @PostConstruct // esta sirve para indicar que este metodo se va a ejecutar cada vez que
    @Override
    public String iniciarAlmacenDeServicio() {
        try {

            Files.createDirectories(Paths.get(storageLocation));

        } catch (IOException e) {

            throw new AlmacenExepcion("Error al inicializar la ubicacion en el almacen de archivos ");

        }
        return null;
    }

    @Override
    public String almacenarArchivo(MultipartFile archivo) {
        
        String nombreArchivo = archivo.getOriginalFilename();
        if(archivo.isEmpty()){
            throw new AlmacenExepcion("No se puede Almacenar un archivo vacio");
        }
        try {

            InputStream inputStream = archivo.getInputStream();
            Files.copy(inputStream,Paths.get(storageLocation).resolve(nombreArchivo),StandardCopyOption.REPLACE_EXISTING);

        }catch(IOException exception){

            throw new AlmacenExepcion("Error al almacenar el archivo"+ nombreArchivo,exception);
        }
        return nombreArchivo;
    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {

        try{
            Path archivo = cargarArchivo(nombreArchivo);

            Resource recurso = new UrlResource(archivo.toUri());


            if(recurso.exists() || recurso.isReadable()){

                return recurso;
            }else{
                throw new FileNotFoundException("No se pudo encontrar el archivo"+ nombreArchivo);
            }
   

            

        }catch(MalformedURLException exception){

            throw new FileNotFoundException("no se pudo encontar el archivo " + nombreArchivo, exception);

        }

    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        Path archivo = cargarArchivo(nombreArchivo);
         try {
             FileSystemUtils.deleteRecursively(archivo);

         }catch(Exception e){

            System.out.println(e);
         }

    }

}
