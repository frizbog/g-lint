/*
 * Copyright (c) 2016 Matthew R. Harrah
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.gedantic.analyzer.result;

import java.util.List;

import org.gedantic.analyzer.AResult;
import org.gedcom4j.model.Individual;
import org.gedcom4j.relationship.SimpleRelationship;

/**
 * An analysis result about a relationship between two people. Immutable.
 * 
 * @author frizbog
 */
public class RelationshipRelatedResult extends AResult {
    /**
     * The individual
     */
    private final Individual individual;

    /**
     * Constructor
     * 
     * @param individual
     *            the individual with the finding
     * @param factType
     *            the fact that the finding relates to - optional
     * @param value
     *            the value that was problematic - optional
     * @param problem
     *            a description of the problem - optional
     */
    public RelationshipRelatedResult(Individual individual, String factType, List<List<SimpleRelationship>> value, String problem) {
        super(factType, value, problem);
        this.individual = individual;
    }

    /**
     * Get the individual
     * 
     * @return the individual
     */
    public Individual getIndividual() {
        return individual;
    }
}