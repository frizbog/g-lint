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
package org.gedantic.analyzer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;

/**
 * @author frizbog
 */
public class ChildrenWithDifferentSurnamesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {

        List<AnalysisResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            if (i.getFamiliesWhereChild() == null || i.getFamiliesWhereChild().isEmpty()) {
                continue;
            }
            Set<String> personSurnames = getSurnamesFromIndividual(i);
            Set<String> allParentSurnames = new TreeSet<>();
            for (FamilyChild fc : i.getFamiliesWhereChild()) {
                Family family = fc.getFamily();
                if (family.getHusband() != null) {
                    allParentSurnames.addAll(getSurnamesFromIndividual(family.getHusband() == null ? null
                            : family.getHusband().getIndividual()));
                }
                if (family.getWife() != null) {
                    allParentSurnames.addAll(getSurnamesFromIndividual(family.getWife() == null ? null
                            : family.getWife().getIndividual()));
                }
            }
            if (allParentSurnames.isEmpty()) {
                continue;
            }

            Set<String> commonSurnames = new TreeSet<>(allParentSurnames);
            commonSurnames.retainAll(personSurnames);
            if (commonSurnames.isEmpty()) {
                // Found a problemDescription
                AnalysisResult r = new AnalysisResult("Individual", i.getFormattedName(), "Name", null, "Individual has surnames "
                        + personSurnames + " and parents have surnames " + allParentSurnames);
                result.add(r);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Children who have no matching surnames with their parents";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Children with different surnames";
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES };
    }

}
