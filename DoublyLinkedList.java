public class DoublyLinkedList<E> {

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
    
        public Node(E e, Node<E> p, Node<E> n){
            element = e;
            prev = p;
            next = n;
        }
    
        public E getElement(){
            return element;
        }
    
        public Node<E> getNext(){
            return next;
        }

        public Node<E> getPrev(){
            return prev;
        }
    
        public void setNext(Node<E> n){
            next = n;
        }

        public void setPrev(Node<E> p){
            prev = p;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public DoublyLinkedList(){
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    
    public E first(){
        if (isEmpty()){
            return null;
        } 
        return header.getNext().getElement();
    }

    public E last(){
        if (isEmpty()){
            return null;
        }
        return trailer.getPrev().getElement();
    }

    public void addFirst(E e){ // new first element
        addBetween(e, header, header.getNext());
    }

    public void addLast(E e){ // new last element
        addBetween(e, trailer.getPrev(), trailer);
    }

    public E removeFirst(){ 
        if (isEmpty()){
            return null;
        }
        return remove(header.getNext());
    }

    public E removeLast(){
        if (isEmpty()){
            return null;
        }
        return remove(trailer.getPrev());
    }

    private void addBetween(E e, Node<E> predecessor, Node<E> successor){
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
    }

    private E remove(Node<E> node){
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();

        predecessor.setNext(successor); // link the nodes next to the one u r removing
        successor.setPrev(predecessor);
        size--;
        return node.getElement();        
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node<E> current = header.getNext();
        while (current != trailer) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    public void group(){
        Node<E> walker = header.getNext();
        Node<E> lastNull = header; // can tell which element is beside the last null

        while (walker != trailer){
            Node<E> next = walker.getNext();

            if (walker.getElement() == null){
                if (walker == lastNull.getNext()){
                    lastNull = walker; // alr in correct place
                } else {
                    // disconnect null first
                    Node<E> prev = walker.getPrev();

                    prev.setNext(walker.getNext());
                    next.setPrev(walker.getPrev());

                    // connect null to the front
                    Node<E> afterLastNull = lastNull.getNext();

                    // set prev and next for walker
                    walker.setNext(afterLastNull); 
                    walker.setPrev(lastNull);
                    
                    // set for nodes beside walker
                    lastNull.setNext(walker);
                    afterLastNull.setPrev(walker); 

                    lastNull = walker;
                }
                    
            }
            walker = next;
        }
    }
}