/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.edu.espol.Interfaces;

/**
 *
 * @author Alejandro Diez
 */
public interface List<E> extends Iterable<E>{
    public boolean addFirst(E e); //Inserta el elemento e al inicio
            
    public boolean addLast(E e); //Inserta el elemento e al final
            
    public E removeFirst(); //remueve el elemento al inicio de la lista
    
    public E removeLast(); // remueve el elemento al final de la lista
    
    public int size();
    
    public boolean isEmpty();
    
    public void clear();
            
    public void add(int index, E element); //Inserta el elemento en la posicion index
            
    public E remove(int index); // remueve y retorna el elemento en la posicion index
    
    public E get(int index); //retorna el elemento ubicado en la posicion index
    
    public E set(int index, E element); //setea elemento en la posicion index
    
    public String toString(); //Muestra todas los elementos de la lista en forma de String
}
