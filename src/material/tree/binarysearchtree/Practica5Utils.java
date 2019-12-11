package material.tree.binarysearchtree;

import material.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *  Clase abstracta que engloba los metodos de los dos primeros ejercicios con fin de evitar duplicidad de código
 *
 *  Realizado por: Miguel Sierra Alonso
 *
 * */
public abstract class Practica5Utils<E>  implements BinarySearchTree<E>{

    public Iterable<Position<E>> findRange(E minValue, E maxValue) throws RuntimeException {
        //TODO: Practica 5 Ejercicio 1
        List<Position<E>> list = new ArrayList<>();
        int maxHashCode = maxValue.hashCode();
        int minHashCode = minValue.hashCode();
        if(maxHashCode < minHashCode)
            throw new RuntimeException("Invalid range. (min>max)");

        Iterator<Position<E>> it = this.iterator();
        Position<E> pos;
        int posHashCode;
        while(it.hasNext()){
            pos = it.next();
            posHashCode = pos.getElement().hashCode();
            if((posHashCode >= minHashCode) && (posHashCode <= maxHashCode))
                list.add(pos);
        }
        return list;
    }

    public Position<E> first() throws RuntimeException {
        //TODO: Practica 5 Ejercicio 2
        Position<E> first;
        try{
            first = this.iterator().next();
        }catch (RuntimeException e) {
            throw new RuntimeException("No first element.");
        }
        return first;
    }

    public Position<E> last() throws RuntimeException {
        //TODO: Practica 5 Ejercicio 2
        Position<E> last = null;
        Iterator<Position<E>> it = this.iterator();
        try{
            last = it.next();
            while(it.hasNext()) {
                last = it.next();
            }
        }catch (RuntimeException e) {
            throw new RuntimeException("No last element.");
        }
        return last;
    }

    public Iterable<Position<E>> successors(Position<E> pos) {
        //TODO: Practica 5 Ejercicio 2
        List<Position<E>> list = new ArrayList<>();
        Iterator<Position<E>> it = this.iterator();
        Position<E> actualPos;
        boolean since = false;
        //Unicamente para la primera posicion, ya que despues entrarán todos los sucesores
        while(it.hasNext() && !since){
            actualPos = it.next();
            if(actualPos.getElement().hashCode() >= pos.getElement().hashCode()){
                since = true;
                list.add(actualPos);
            }
        }
        while(it.hasNext()){
            list.add(it.next());
        }
        return list;
    }

    public Iterable<Position<E>> predecessors(Position<E> pos) {
        //TODO: Practica 5 Ejercicio 2
        List<Position<E>> list = new ArrayList<>();
        Iterator<Position<E>> it = this.iterator();
        Position<E> actualPos;
        boolean limit = false;
        while (it.hasNext() && !limit) {
            actualPos = it.next();
            if (actualPos.getElement().hashCode() <= pos.getElement().hashCode())
                list.add(actualPos);
            else
                limit = true;
        }
        //Invertimos la lista
        Collections.reverse(list);
        return list;
    }
}
