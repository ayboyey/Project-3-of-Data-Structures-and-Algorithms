package lab03;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
class TwoWayLinkedListWithHead<E> implements IList<E>{
 
    private class Element{
        public Element(E e) {
            this.object = e;
        }
 
        public Element(E e, Element next, Element prev) {
            this(e);
            this.next = next;
            this.prev = prev;
        }
 
        E object;
        Element next=null;
        Element prev=null;
    }
 
    Element head;
    // can be realization with the field size or without
    int size;
 
    private class InnerIterator implements Iterator<E>{
        Element pos;
 
        boolean hasNext;
 
        public InnerIterator() {
            pos = head;
            hasNext = pos != null;
        }
        @Override
        public boolean hasNext() {
            return hasNext;
        }
 
        @Override
        public E next() {
            E object = pos.object;
            pos = pos.next;
            if (pos == null || pos == head) hasNext = false;
            return object;
        }
    }
 
    private class InnerListIterator implements ListIterator<E>{
        Element p;
 
        boolean wasNext = false;
        boolean wasPrev = false;
 
        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
 
        @Override
        public boolean hasNext() {
            if (wasPrev) return true;
            if (head != null) {
                if (p == null) return true;
                return p.next != head;
            }
            return false;
        }
 
        @Override
        public boolean hasPrevious() {
            if (wasNext) return true;
            if (head != null) {
                if (p == null) return true;
                return p.prev != head.prev;
            }
            return false;
        }
 
        @Override
        public E next() {
            wasNext = true;
 
            if (wasPrev) {
                wasPrev = false;
                return p.object;
            }
 
            if (head != null) {
                if (p == null) {
                    p = head;
                    return p.object;
                }
 
                p = p.next;
                return p.object;
            }
 
            return null;
        }
 
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }
 
        @Override
        public E previous() {
            wasPrev = true;
 
            if (wasNext) {
                wasNext = false;
                return p.object;
            }
 
            if (head != null) {
                if (p == null) {
                    p = head;
                    return p.object;
                }
 
                p = p.prev;
                return p.object;
            }
 
            return null;
        }
 
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }
 
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
 
        }
 
        @Override
        public void set(E e) {
            if (e == null) return;
            p.object = e; //TODO Probably wrong
        }
    }
 
    public TwoWayLinkedListWithHead() {
        // make a head
        head=null;
        size = -1;
    }
 
    @Override
    public boolean add(E e) {
        if (e == null) return false;
 
        if (head == null) {
            head = new Element(e);
            head.prev = head;
            head.next = head;
            size = 0;
        }
        else {
            Element element = new Element(e, head, head.prev);
            head.prev.next = element;
            head.prev = element;
        }
 
        size++;
        return true;
    }
 
    @Override
    public void add(int index, E element) {
        if (element == null) return;
        if (index < 0) throw new NoSuchElementException();
 
        if (head == null) {
            if (index == 0) {
                head = new Element(element);
                head.prev = head;
                head.next = head;
                size = 1;
                return;
            }
        } else if (index == size) {
            add(element);
            return;
        } else {
            if (index > size) throw new NoSuchElementException();
 
            if (index == 0) {
                Element e = new Element(element, head, head.prev);
                head.prev.next = e;
                head.prev = e;
                head = e;
                size++;
                return;
            }
 
            InnerIterator it = new InnerIterator();
 
            while (index > 0 && it.hasNext()) {
                it.next();
                index--;
            }
 
            Element e = new Element(element, it.pos, it.pos.prev);
            e.prev.next = e;
            size++;
        }
    }
 
    @Override
    public void clear() {
        head = null;
        size = -1;
    }
 
    @Override
    public boolean contains(E element) {
        if (element == null) return false;
 
        InnerIterator it = new InnerIterator();
 
        while (it.hasNext()) {
            if (Objects.equals(it.next(), element)) return true;
        }
 
        return false;
    }
 
    @Override
    public E get(int index) {
 
        if (isEmpty()) throw new NoSuchElementException();
        if (index < 0 || index >= size) throw new NoSuchElementException();
 
        InnerIterator it = new InnerIterator();
 
        while (index > 0 && it.hasNext()) {
            it.next();
            index--;
        }
 
        return it.pos.object;
    }
 
    @Override
    public E set(int index, E element) {
        if (element == null) return null;
        if (isEmpty()) throw new NoSuchElementException();
        if (index < 0 || index >= size) throw new NoSuchElementException();
 
        InnerIterator it = new InnerIterator();
 
        while (index > 0 && it.hasNext()) {
            it.next();
            index--;
        }
 
        E old = it.pos.object;
        it.pos.object = element;
        return old;
    }
 
    @Override
    public int indexOf(E element) {
 
        if (element == null) return -1;
 
        InnerIterator it = new InnerIterator();
 
        int index = -1;
 
        while (it.hasNext()) {
            index++;
            if (it.next().equals(element)) return index;
        }
 
        return -1;
    }
 
    @Override
    public boolean isEmpty() {
        return head == null;
    }
 
    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }
 
    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public E remove(int index) {
        if (isEmpty() || index < 0 || index >= size) throw new NoSuchElementException();
 
        if (size == 1) {
            Element old = head;
            clear();
            return old.object;
        }
 
        if (index == 0) {
            E old = head.object;
            head.prev.next = head.next;
            head.next.prev = head.prev;
            head = head.prev;
            size--;
            return old;
        } else {
            InnerIterator it = new InnerIterator();
 
            while (index > 0 && it.hasNext()) {
                it.next();
                index--;
            }
 
            Element e = it.pos;
 
            e.prev.next = e.next;
            e.next.prev = e.prev;
 
            size--;
            return e.object;
        }
    }
 
    @Override
    public boolean remove(E e) {
        if (e == null || isEmpty()) return false;
 
        if (size == 1 && head.object.equals(e)) {
            clear();
            return true;
        }
 
        InnerIterator it = new InnerIterator();
 
        int index = -1;
 
        while (it.hasNext()) {
            index++;
            if (Objects.equals(it.next(), e)) {
                Element el = it.pos.prev;
                el.prev.next = el.next;
                el.next.prev = el.prev;
                if (index == 0) head = el.next;
                size--;
                return true;
            }
        }
 
        return false;
    }
 
    @Override
    public int size() {
        return size;
    }
 
    public String toStringReverse() {
        ListIterator<E> iter=new InnerListIterator();
        while(iter.hasNext())
            iter.next();
        String retStr="";
 
        while (iter.hasPrevious()) {
            E obj = iter.previous();
            if (obj != null && obj instanceof Link) {
                Link link = (Link) obj;
                if (link.ref != null) retStr += "\n" + link.ref;
            }
        }
 
        return retStr;
    }
 
    public void add(TwoWayLinkedListWithHead<E> other) {
        if (other == null || other.isEmpty() || this == other) return;
 
        if (head == null) {
            head = other.head;
            size = other.size;
            other.clear();
            return;
        }
 
        head.prev.next = other.head;
        Element tail = other.head.prev;
        other.head.prev = head.prev;
        head.prev = tail;
        tail.next = head;
 
        size += other.size;
 
        other.clear();
    }
}
 