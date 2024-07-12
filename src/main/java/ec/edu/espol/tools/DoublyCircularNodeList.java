/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.tools;

import java.util.Iterator;

/**
 *
 * @author CltControl
 */
public class DoublyCircularNodeList<E>{
    E content;
    DoublyCircularNodeList<E> next;
    DoublyCircularNodeList<E> previus;

    public DoublyCircularNodeList(E content) {
        this.content = content;
        this.next = null;
        this.previus = null;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public DoublyCircularNodeList<E> getNext() {
        return next;
    }

    public void setNext(DoublyCircularNodeList<E> next) {
        this.next = next;
    }

    public DoublyCircularNodeList<E> getPrevius() {
        return previus;
    }

    public void setPrevius(DoublyCircularNodeList<E> previus) {
        this.previus = previus;
    }


    
}
