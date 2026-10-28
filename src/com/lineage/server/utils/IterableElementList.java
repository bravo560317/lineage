package com.lineage.server.utils;

import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import java.util.Iterator;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class IterableElementList implements Iterable<Element>
{
    IterableNodeList _list;
    
    public IterableElementList(NodeList list) {
        this._list = new IterableNodeList(list);
    }
    
    @Override
    public Iterator<Element> iterator() {
        return new IterableElementList.MyIterator(this._list.iterator());
    }
    
    private class MyIterator implements Iterator<Element>
    {
        private Iterator<Node> _itr;
        private Element _next;
        
        public MyIterator(Iterator<Node> itr) {
            this._next = null;
            this._itr = itr;
            this.updateNextElement();
        }
        
        private void updateNextElement() {
            while (this._itr.hasNext()) {
                Node node = this._itr.next();
                if (node instanceof Element) {
                    this._next = (Element)node;
                    return;
                }
            }
            this._next = null;
        }
        
        @Override
        public boolean hasNext() {
            return this._next != null;
        }
        
        @Override
        public Element next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                Element result = this._next;
                this.updateNextElement();
                return result;
            }
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
