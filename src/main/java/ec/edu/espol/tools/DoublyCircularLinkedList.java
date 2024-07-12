/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.tools;

import ec.edu.espol.Interfaces.List;
import java.util.Iterator;

/**
 *
 * @author CltControl
 */
public class DoublyCircularLinkedList<E> implements List<E>{
    DoublyCircularNodeList<E> first;
    DoublyCircularNodeList<E> last;
    int size;

    public DoublyCircularLinkedList() {
        size = 0;
    }

    @Override
    public boolean addFirst(E e) {
        if(e != null){
            DoublyCircularNodeList<E> node = new DoublyCircularNodeList(e);
            if(first == null){
                first = node;
                last = node;
                first.setNext(last);
                last.setPrevius(first);
                size++;
                return true;
            }
            else{
                node.setPrevius(last);
                node.setNext(first);
                last.setNext(node);
                first.setPrevius(node);
                first = node;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addLast(E e) {
        if(e != null){
            DoublyCircularNodeList<E> node = new DoublyCircularNodeList(e);
            if(first == null){
                first = node;
                last = node;
                first.setNext(last);
                last.setPrevius(first);
                size++;
                return true;
            }
            else{
                node.setPrevius(last);
                node.setNext(first);
                last.setNext(node);
                first.setPrevius(node);
                last = node;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public E removeFirst() {
        if(first != null){
            E elements;
            if(first == last){
                elements = first.getContent();
                clear();
                return elements;
            }
            else{
                elements = first.getContent();
                last.setNext(first.getNext());
                first.getNext().setPrevius(last);
                first = first.getNext();
                size--;
                return elements;
            }
        }
        return null;
    }

    @Override
    public E removeLast() {
        if(first != null){
            E elements;
            if(first == last){
                elements = first.getContent();
                clear();
                return elements;
            }
            else{
                elements = last.getContent();
                last.getPrevius().setNext(first);
                first.setPrevius(last.getPrevius());
                last = last.getPrevius();
                size--;
                return elements;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        first =  null;
        last = null;
        size = 0;
    }

    @Override
    public void add(int index, E element) {
        if(element != null){    
            if(index >= 0 && index < size){
                if(index == 0) addFirst(element);
                if(index == size() - 1) addLast(element);
                int con = 0;
                for(DoublyCircularNodeList<E> node = first; con < size ; node = node.getNext()){
                    if(con == index){
                        DoublyCircularNodeList<E> node2 = new DoublyCircularNodeList(element);
                        node2.setNext(node);
                        node2.setPrevius(node.getPrevius());
                        node.getPrevius().setNext(node2);
                        node.setPrevius(node2);
                        size++;
                    }
                    con++;
                }
            }
        }
    }

    @Override
    public E remove(int index) {   
        if(index >= 0 && index < size){
            E element;
            if(index == 0) {
                element = first.getContent();
                removeFirst();
                return element;
            }
            if(index == size() - 1) {
                element = last.getContent();
                removeLast();
                return element;
                }
            int con = 0;
            for(DoublyCircularNodeList<E> node = first; con < size ; node = node.getNext()){
                if(con == index){
                    node.getPrevius().setNext(node.getNext());
                    node.getNext().setPrevius(node.getPrevius());
                    size--;
                    return node.getContent();
                }
                con++;
            }
        }
        return null;
    }

    @Override
    public E get(int index) {
        if(index >= 0 && index < size){
            int con = 0;
            for(E e : this){
                if(con == index) return e;
                con++;
            }
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if(index >= 0 && index < size){
            int con = 0;
            for(DoublyCircularNodeList<E> node = first; con < size ; node = node.getNext()){
                if(con == index) {
                    node.setContent(element);
                    return node.getContent();
                }
                con++;
            }
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<>() {
            DoublyCircularNodeList<E> curso = first;
            int con = 0;
            @Override
            public boolean hasNext() {
                if(con < size){
                con++;
                return true;
                }
                return false;
            }

            @Override
            public E next() {
                E element = curso.getContent();
                curso = curso.getNext();
                return element;
            }
        };
        return it;       
    }

    @Override
    public String toString() {
        String st = "";
        int con = 0;
        for(E e : this){
            if(con == 0) {con = 1; st=e.toString();}
            else st=st+":"+e.toString();
        }
        return st;
    }

    public DoublyCircularNodeList<E> getFirst() {
        return first;
    }
}
