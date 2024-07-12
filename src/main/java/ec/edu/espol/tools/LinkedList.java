/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.tools;

import ec.edu.espol.Interfaces.List;
import java.util.Iterator;


/**
 *
 * @author Alejandro Diez
 */
public class LinkedList<E> implements List<E>{
    private NodeList<E> header;
    private NodeList<E> last;
    private int size;

    public LinkedList() {
        header = null;
        last = null;
        size = 0;
    }

    public NodeList<E> getHeader() {
        return header;
    }

    public void setHeader(NodeList<E> header) {
        this.header = header;
    }

    public NodeList<E> getLast() {
        return last;
    }

    public void setLast(NodeList<E> last) {
        this.last = last;
    }

    public NodeList<E> getPrevius(NodeList<E> node){
        for(NodeList<E> n = header; n != null; n = n.getNext()){
            if(n.getNext().equals(node))return n;
        }
        return null;
    }
    
    @Override
    public boolean addFirst(E e) {
        if(e != null){
            NodeList<E> node = new NodeList(e);
            if(header == null){
                header = node;
                last = node;
                size++;
                return true;
            }
            else{
                node.setNext(header);
                header = node;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addLast(E e) {
        if(e != null){
            NodeList<E> node = new NodeList(e);
            if(header == null){
                header = node;
                last = node;
                size++;
                return true;
            }
            else{
                last.setNext(node);
                last = node;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public E removeFirst() {
        NodeList<E> node = header;
        if(node == null) return null;
        else{
            header = node.getNext();
            size--;
            return node.getContent();
        }
    }

    @Override
    public E removeLast() {
        if(getPrevius(last) == null) return null;
        else{
            last = getPrevius(last);
            last.setNext(null);
            size--;
            return last.getContent();
        }
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
        setHeader(null);
        setLast(null);
        size = 0;
    }

    @Override
    public void add(int index, E element) {
        if(element != null){
            int siz = 0;
            NodeList<E> newnode = new NodeList(element);
            for(NodeList<E> nod = header; nod!= null; nod = nod.getNext()){
                if(index == 0)addFirst(element);
                else if(index == size())addLast(element);
                else if(index == siz){
                    getPrevius(nod).setNext(newnode);
                    newnode.setNext(nod);
                    size++;
                }
            siz++;
            }
        }
    }

    @Override
    public E remove(int index) {
        int siz = 0;
        if(index == 0)return removeFirst();
        else if(index == size()-1)return removeLast();
        for(NodeList<E> nod = header; nod != null; nod = nod.getNext()){
            if(index == siz){
                getPrevius(nod).setNext(nod.getNext());
                size--;
                return nod.getContent();
            }
            siz++;    
        }
        return null;
    }

    @Override
    public E get(int index) {
        int siz = 0;
        for(E e : this){
            if(siz == index){
                return e;
            }
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        int siz = 0;
        for(NodeList<E> node = header; node != null; node = node.getNext()){
            if(siz == index){
                node.setContent(element);
                return node.getContent();
            }
            siz++;
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it=new Iterator<E>() {
        NodeList<E> cursor = header;
                
            @Override
            public boolean hasNext() {
                return cursor != null;
            }

            @Override
            public E next() {
                E e = cursor.getContent();
                cursor = cursor.getNext();
                return e;
            }
        };
        return it;
    }
    
    @Override
    public String toString(){
        String st = "";
        int con = 0;
        for(E e : this){
            if(con == 0) {con = 1; st=e.toString();}
            else st=st+":"+e.toString();
        }
        return st;
    }
    
}
