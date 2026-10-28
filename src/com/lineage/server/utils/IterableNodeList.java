package com.lineage.server.utils;

import java.util.NoSuchElementException;
import java.util.Iterator;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class IterableNodeList implements Iterable<Node>
{
    private final NodeList _list;
    
    public IterableNodeList(NodeList list) {
        this._list = list;
    }
    
    @Override
    public Iterator<Node> iterator() {
        return new IterableNodeList.MyIterator();
    }
    
    private class MyIterator implements Iterator<Node>
    {
        private int _idx;
        
        private MyIterator() {
            this._idx = 0;
        }
        
        @Override
        public boolean hasNext() {
            return this._idx < IterableNodeList.this._list.getLength();
        }
        
        @Override
        public Node next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                return IterableNodeList.this._list.item(this._idx++);
            }
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
