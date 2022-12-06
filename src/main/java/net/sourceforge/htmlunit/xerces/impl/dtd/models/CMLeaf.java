/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sourceforge.htmlunit.xerces.impl.dtd.models;

import net.sourceforge.htmlunit.xerces.impl.dtd.XMLContentSpec;
import net.sourceforge.htmlunit.xerces.xni.QName;

/**
 * Content model leaf node.
 *
 */
public class CMLeaf
    extends CMNode {

    /** This is the element that this leaf represents. */
    private final QName fElement = new QName();

    /**
     * Part of the algorithm to convert a regex directly to a DFA
     * numbers each leaf sequentially. If its -1, that means its an
     * epsilon node. Zero and greater are non-epsilon positions.
     */
    private int fPosition = -1;

    // Constructs a content model leaf.
    public CMLeaf(QName element, int position)  {
        super(XMLContentSpec.CONTENTSPECNODE_LEAF);

        // Store the element index and position
        fElement.setValues(element);
        fPosition = position;
    }

    // Constructs a content model leaf.
    public CMLeaf(QName element)  {
        super(XMLContentSpec.CONTENTSPECNODE_LEAF);

        // Store the element index and position
        fElement.setValues(element);
    }

    final QName getElement()
    {
        return fElement;
    }

    final int getPosition()
    {
        return fPosition;
    }

    final void setPosition(int newPosition)
    {
        fPosition = newPosition;
    }

    @Override
    public boolean isNullable()
    {
        // Leaf nodes are never nullable unless its an epsilon node
        return (fPosition == -1);
    }

    @Override
    public String toString()
    {
        StringBuilder strRet = new StringBuilder(fElement.toString());
        strRet.append(" (");
        strRet.append(fElement.uri);
        strRet.append(',');
        strRet.append(fElement.localpart);
        strRet.append(')');
        if (fPosition >= 0) {
            strRet.append(" (Pos:")
                  .append(fPosition)
                  .append(')');
        }
        return strRet.toString();
    }
    @Override
    protected void calcFirstPos(CMStateSet toSet)
    {
        // If we are an epsilon node, then the first pos is an empty set
        if (fPosition == -1)
            toSet.zeroBits();

        // Otherwise, its just the one bit of our position
        else
            toSet.setBit(fPosition);
    }

    @Override
    protected void calcLastPos(CMStateSet toSet)
    {
        // If we are an epsilon node, then the last pos is an empty set
        if (fPosition == -1)
            toSet.zeroBits();

        // Otherwise, its just the one bit of our position
        else
            toSet.setBit(fPosition);
    }
}


