package material.tree.binarytree;

import material.Position;
import material.tree.iterators.InorderBinaryTreeIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Realizado por Miguel Sierra
 * */
public class ArrayBinaryTree<E> implements BinaryTree<E> {
    //TODO: Practica 3 Ejercicio 2
    private class BTNode<T> implements Position<T> {

        private T element;
        private int index;

        public BTNode(T element, int index) {
            this.element = element;
            this.index = index;
        }

        public void setElement(T element) {
            this.element = element;
        }

        @Override
        public T getElement() {
            return this.element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    private final int MAX = 25;
    private BTNode<E>[] binaryArrayTree;
    private int size;

    public ArrayBinaryTree(){
        this.binaryArrayTree = new BTNode[MAX];
        this.binaryArrayTree[0] = null;
        this.size = 0;
    }

    public ArrayBinaryTree(int capacity){
        this.binaryArrayTree = new BTNode[capacity];
        this.binaryArrayTree[0] = null;
        this.size = 0;
    }

    @Override
    public Position<E> left(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        int indexLeft = 2 * node.getIndex();
        if (!hasLeft(v)) {
            throw new RuntimeException("No left child");
        }
        return this.binaryArrayTree[indexLeft];
    }

    @Override
    public Position<E> right(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        int indexRight = 2 * node.getIndex() + 1;
        if (!hasRight(v)) {
            throw new RuntimeException("No right child");
        }
        return this.binaryArrayTree[indexRight];
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        int indexLeft = 2 * node.getIndex();
        return (indexLeft<=this.binaryArrayTree.length) && (this.binaryArrayTree[indexLeft]!=null);
    }

    @Override
    public boolean hasRight(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        int indexRight = 2 * node.getIndex() + 1;
        return (indexRight<=this.binaryArrayTree.length) && (this.binaryArrayTree[indexRight]!=null);
    }

    @Override
    public E replace(Position<E> p, E e) {
        BTNode<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        int parentIndex = node.getIndex() / 2;
        Position<E> parentPos = this.binaryArrayTree[parentIndex];
        if (parentPos != null) {
            BTNode<E> sibPos;
            int leftIndex = 2 * parentIndex;
            BTNode<E> leftPos = this.binaryArrayTree[leftIndex];
            if (leftPos == node) {
                int rightIndex = 2 * parentIndex + 1;
                sibPos = this.binaryArrayTree[rightIndex];
            } else {
                sibPos = this.binaryArrayTree[leftIndex];
            }
            if (sibPos != null) {
                return sibPos;
            }
        }
        throw new RuntimeException("No sibling");
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        int leftIndex = 2 * node.getIndex();
        Position<E> leftPos = this.binaryArrayTree[leftIndex];
        if (leftPos != null) {
            throw new RuntimeException("Node already has a left child");
        }
        BTNode<E> newNode = new BTNode<>(e, leftIndex);
        this.binaryArrayTree[leftIndex] = newNode;
        this.size++;
        return newNode;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        int rightIndex = 2 * node.getIndex() + 1;
        Position<E> rightPos = this.binaryArrayTree[rightIndex];
        if (rightPos != null) {
            throw new RuntimeException("Node already has a right child");
        }
        BTNode<E> newNode = new BTNode<>(e, rightIndex);
        this.binaryArrayTree[rightIndex] = newNode;
        this.size++;
        return newNode;
    }

    @Override
    public E remove(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        BTNode<E> node1 = checkPosition(p1);
        BTNode<E> node2 = checkPosition(p2);
        int indexNode1 = node1.getIndex();
        int indexNode2 = node2.getIndex();

        node1.setIndex(indexNode2);
        node2.setIndex(indexNode1);
        indexNode1 = node1.getIndex();
        indexNode2 = node2.getIndex();

        this.binaryArrayTree[indexNode1] = node1;
        this.binaryArrayTree[indexNode2] = node2;
    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        return null;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        if (tree == this) {
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (this.hasLeft(p)) {
            throw new RuntimeException("Node already has a left child");
        }
        /**/
    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        if (tree == this) {
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (this.hasRight(p)) {
            throw new RuntimeException("Node already has a right child");
        }
        /**/
    }

    @Override
    public boolean isComplete() {
        if(this.isEmpty())
            throw new RuntimeException("The tree is empty!");
        Iterator<Position<E>> it = this.iterator();
        boolean result = false;
        while(it.hasNext()) {
            Position<E> actualNode = it.next();
            if ((this.hasLeft(actualNode) && (this.hasRight(actualNode)) || (this.isLeaf(actualNode))))
                result = true;
            else
                return false;
        }
        return  result;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        return this.binaryArrayTree[1];
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        int parentIndex = node.getIndex() / 2;
        Position<E> parentPos = this.binaryArrayTree[parentIndex];
        if (parentPos == null)
            throw new RuntimeException("No parent");

        return parentPos;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        List<Position<E>> children = new ArrayList<>();
        if (hasLeft(v)) {
            children.add(left(v));
        }
        if (hasRight(v)) {
            children.add(right(v));
        }
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public boolean isInternal(Position<E> v) {
        checkPosition(v);
        return (hasLeft(v) || hasRight(v));
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) {
        checkPosition(v);
        return (v == this.root());
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if (!isEmpty()) {
            throw new RuntimeException("Tree already has a root");
        }
        BTNode<E> root = new BTNode<>(e,  1);
        this.binaryArrayTree[1] = root;
        this.size++;
        return root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new InorderBinaryTreeIterator<>(this);
    }

    private BTNode<E> checkPosition(Position<E> p) {
        if (p == null || !(p instanceof BTNode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (BTNode<E>) p;
    }
}
