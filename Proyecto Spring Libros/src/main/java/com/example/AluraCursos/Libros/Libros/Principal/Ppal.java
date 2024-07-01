package com.example.AluraCursos.Libros.Libros.Principal;

import com.example.AluraCursos.Libros.Libros.Models.*;
import com.example.AluraCursos.Libros.Libros.Respository.IAutorRepository;
import com.example.AluraCursos.Libros.Libros.Respository.LibroRepository;
import com.example.AluraCursos.Libros.Libros.Services.ConsumoAPI;
import com.example.AluraCursos.Libros.Libros.Services.ConvierteDatos;

import java.util.*;

public class Ppal {
    private Scanner sc= new Scanner(System.in);
    private ConsumoAPI consumoAPI= new ConsumoAPI();
    private final String URL_BASE= "https://gutendex.com/books/";
    private ConvierteDatos conversor= new ConvierteDatos();
    Optional<Libro> libroBuscado;
    private LibroRepository libroRepository;
    private IAutorRepository autorRepository;
    private List<Libro> listaLibros;
    List<Autor> listarAutores;

    public Ppal(LibroRepository repository, IAutorRepository autorRepository) {
        this.libroRepository = repository;
        this.autorRepository= autorRepository;
    }

    public void mostrarMenu(){

        var option=-1;
        while (option!=0){
            var menu= """
                   ---------------------------------------
                                       
                    1.Buscar Libro Por Titulo
                    2.Listar Libros Registrados
                    3.Listar Autores Registrados
                    4.Listar autores vivos en un determinado año
                    5.Listar libro por Idiomas
                    6.Listar top 5 de libros mas descargados
                    7.Buscar Autor por nombre
                    0.Salir
                    
                    ---------------------------------------
                    """;

            System.out.println(menu);
            option= sc.nextInt();
            sc.nextLine();

            switch (option){
                case 1: buscarLibroPorTitulo();
                    break;
                case 2: listarLibrosRegistrados();
                    break;
                case 3: listarAutoresRegistrados();
                    break;
                case 4: listarAutoresVivosEnDeterminadoAño();
                    break;
                case 5: listarLibrosPorIdioma();
                    break;
                case 6: top5LibrosMasDescargados();
                    break;
                case 7:
                    buscarAutorPorNombre();
                    break;
                case 0:
                    System.out.println("Cerrando Aplicacion...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }

    }

    private Datos getDatos(){
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var nombreLibro= sc.nextLine();
        var json= consumoAPI.obtenerDatos(URL_BASE+ "?search="+nombreLibro.replace(" ", "%20").toLowerCase());
        var datos= conversor.obtenerDatos(json, Datos.class);
        return datos;

    }

    private void buscarLibroPorTitulo(){//si no escribimos todo el nombre, no muestra que esta cargado en la db
        Datos datos= getDatos();
        if (!datos.resultados().isEmpty()){
            DatosLibros datosLibros= datos.resultados().get(0);
            DatosAutor datosAutor= datosLibros.autor().get(0);
            System.out.println("""
                    EL libro encontrado fue: 
                    Nombre: %s
                    Autor: %s
                    Idioma: %s""".formatted(datosLibros.titulo(), datosAutor.nombre(), datosLibros.idiomas()));

            Libro libroBuscado= libroRepository.findByTituloContainsIgnoreCase(datosLibros.titulo());

            if (libroBuscado!=null){
                System.out.println("El libro ya existe");
            }else{
                Autor autorBuscado= autorRepository.findByNombreContainingIgnoreCase(datosAutor.nombre());
                if (autorBuscado==null){
                    Autor autor= new Autor(datosAutor);
                    autorRepository.save(autor);
                    Libro libro= new Libro(datosLibros, autor);
                    libroRepository.save(libro);
                }else{
                    Libro libro = new Libro(datosLibros, autorBuscado);
                    libroRepository.save(libro);
                }
            }
        }
        else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados(){
        listaLibros= libroRepository.findAll();
        listaLibros.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados(){
        listarAutores= autorRepository.findAll();
        listarAutores.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEnDeterminadoAño(){
        System.out.println("Ingrese el año en que desee buscar los autores vivos");
        var anio= sc.nextInt();

        listarAutores= autorRepository.autoresVivosPorAño(anio);

        if (listarAutores.isEmpty()) System.out.println("No hay autores registrados");
        listarAutores.stream().forEach(System.out::println);

    }

    private void listarLibrosPorIdioma(){
        ArrayList<String> idiomas=new ArrayList<>();
        idiomas.add("en");
        idiomas.add("es");
        idiomas.add("fr");
        idiomas.add("pt");
        System.out.println("""
                Ingrese el idioma en el que desea buscar sus libros
                Se aceptan: 
                en- English
                es- Español
                fr- Frances
                pt- Portugues
                """);
        var idioma= sc.nextLine();
        if(!idiomas.contains(idioma.toLowerCase())){
            System.out.println("Idioma incorrecto");}
        libroRepository.librosPorIdioma(idioma).forEach(System.out::println);

    }

    private void top5LibrosMasDescargados(){
        List<Libro> toplibro= libroRepository.findTop5ByOrderByNumeroDeDescargasDesc();
        toplibro.forEach(System.out::println);
    }

    private void buscarAutorPorNombre(){
        System.out.println("Ingrese el nombre del autor que desea buscar");
        var nombreAutor= sc.nextLine();
        Autor autorBuscado= autorRepository.findByNombreContainingIgnoreCase(nombreAutor);
        System.out.println(autorBuscado);
    }
}
